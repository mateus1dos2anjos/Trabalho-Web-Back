
package com.ufc.br.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Item;
import com.ufc.br.model.Pessoa;
import com.ufc.br.model.Prato;
import com.ufc.br.service.PessoaService;
import com.ufc.br.service.PratoService;

@Controller
@RequestMapping("/prato")
public class PratoController {

	@Autowired
	private PratoService pratoService;
	
	@Autowired
	private PessoaService pessoaService;
	
	private List<Item> itens = new ArrayList<Item>();
	
	@RequestMapping("/formularioPrato")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("FormularioPrato");
		mv.addObject("prato", new Prato());
		return mv;
	}
	
	@PostMapping("/salvarPrato")
	public ModelAndView cadastrar(@Validated Prato prato, BindingResult result,
			@RequestParam(value = "img") MultipartFile imagem) {
		ModelAndView mv = new ModelAndView("FormularioPrato");
		if (result.hasErrors()) {
			return mv;
		}
		pratoService.cadastrar(prato, imagem);
		mv.addObject("Mensagem", "Prato cadastrado com sucesso!");
		return mv;
	}

	@GetMapping("/listar")
	public ModelAndView listarPratos() {
//		Object auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//		UserDetails user = (UserDetails) auth;
//		Pessoa pessoa = pessoaService.buscarPorLogin(user.getUsername());
//		System.out.println(pessoa.getNome());
		List<Prato> pratos = pratoService.listarPratos();
		ModelAndView mv = new ModelAndView("GaleriaPratos");
		mv.addObject("listaDePratos", pratos);
		return mv;
	}

	@RequestMapping("/excluir/{codigo}")
	public ModelAndView excluirPrato(@PathVariable Long codigo) {
		pratoService.excluirPrato(codigo);
		ModelAndView mv = new ModelAndView("redirect:/prato/listar");
		return mv;
	}

	@RequestMapping("/atualizar/{codigo}")
	public ModelAndView atualizarPrato(@PathVariable Long codigo) {
		Prato prato = pratoService.buscarPorId(codigo);
		ModelAndView mv = new ModelAndView("FormularioPrato");
		mv.addObject("prato", prato);
		return mv;
	}
	
	
}
