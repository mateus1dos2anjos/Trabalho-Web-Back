package com.ufc.br.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Item;
import com.ufc.br.model.Pedido;
import com.ufc.br.model.Pessoa;
import com.ufc.br.model.Prato;
import com.ufc.br.service.ItemService;
import com.ufc.br.service.PedidoService;
import com.ufc.br.service.PessoaService;
import com.ufc.br.service.PratoService;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

	@Autowired
	private PratoService pratoService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private PedidoService pedidoService;
	
	@RequestMapping("/adicionar/{codigo}")
	public ModelAndView adicionarPrato(@PathVariable Long codigo, HttpSession session) {
		Prato prato = pratoService.buscarPorId(codigo);
		session.setAttribute("prato", prato);
		ModelAndView mv = new ModelAndView("AdicionarPratos");
		mv.addObject("prato", prato);
		return mv;
	}
	
	
	@RequestMapping("/salvarItem")
	public ModelAndView salvarItem(int quantidade, HttpSession session) {
		Prato prato = (Prato) session.getAttribute("prato");
		Item item = new Item();
		item.setPrato(prato);
		item.setQuantidade(quantidade);
		List<Item> itens = new ArrayList<Item>();
		if(session.getAttribute("itens") != null) {
			int count = 0;
			itens = (List<Item>) session.getAttribute("itens");
			for(Item i: itens) {
				if(i.getPrato().getCodigo() == prato.getCodigo()) {
					i.setQuantidade(item.getQuantidade() + i.getQuantidade());
					count++;
				}
			}
			if(count == 0) {
				itens.add(item);
			}
		}else {
			itens.add(item);
		}
		session.setAttribute("itens", itens);
		ModelAndView mv = new ModelAndView("redirect:/prato/listar");
		return mv;
	}
	
	@RequestMapping("/removerItem/{codigo}")
	public ModelAndView removerItem(@PathVariable Long codigo, HttpSession session) {
		List<Item> itens = new ArrayList<Item>();
		if(session.getAttribute("itens") != null) {
			itens = (List<Item>) session.getAttribute("itens");
			for(int i = 0; i < itens.size(); i++) {
				if(itens.get(i).getPrato().getCodigo() == codigo) {
					itens.remove(i);
				}
			}
		}
		session.setAttribute("itens", itens);
		ModelAndView mv = new ModelAndView("redirect:/carrinho/");
		return mv;
	}
	
	@RequestMapping("/")
	public ModelAndView carrinho(HttpSession session) {
		ModelAndView mv = new ModelAndView("Carrinho");
//		System.out.println(carrinhoService.getItens().get(0));
		double valor = 0;
		if((List<Item>) session.getAttribute("itens") != null) {
			mv.addObject("listaDeItens", (List<Item>) session.getAttribute("itens"));
			for (Item item: (List<Item>) session.getAttribute("itens")) {
				valor += item.getPrato().getPreco() * item.getQuantidade();
			}
		}else {
			mv.addObject("listaDeItens", new ArrayList<Item>());
		}
		mv.addObject("valorTotal", valor);
		return mv;
	}
	
	@RequestMapping("/confirmarCompra")
	public ModelAndView confirmarCompras(String endereco, HttpSession session) {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		Pessoa pessoa = pessoaService.buscarPorLogin(user.getUsername());
		
		Pedido pedido = new Pedido();
		pedido.setPessoa(pessoa);
		pedido.setEndereco(endereco);
		pedidoService.Cadastrar(pedido);
		
		List<Item> itens = (List<Item>) session.getAttribute("itens");
		for (Item item : itens) {
			item.setPedido(pedido);
			itemService.cadastrar(item);
		}
		session.removeAttribute("itens");
		ModelAndView mv = new ModelAndView("redirect:/carrinho/");
		return mv;
	}
	
}