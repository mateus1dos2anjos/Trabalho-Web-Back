package com.ufc.br.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ufc.br.model.Pessoa;
import com.ufc.br.model.Role;
import com.ufc.br.service.PessoaService;


@Controller
@RequestMapping("/pessoa")
public class PessoaController {

	

	@Autowired
	private PessoaService pessoaService;
	
	
	@RequestMapping("/formularioCadastro")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("FormularioCadastro");
		mv.addObject("pessoa", new Pessoa());  
		return mv;
	}
	
	@PostMapping("/salvar")
	public ModelAndView salvar(@Validated Pessoa pessoa, BindingResult result ) {
		
		ModelAndView mv = new ModelAndView("redirect:/pessoa/login");
		
		if(result.hasErrors()) {
			return mv;
		}

		List<Role> roles = new ArrayList<Role>();
		Role role = new Role();
		role.setPapel("ROLE_USER");
		roles.add(role);
		pessoa.setRoles(roles);
		
		pessoaService.Cadastrar(pessoa);
		
		mv.addObject("mensagem", "Pessoa cadastrada com sucesso!");
		
		return mv;
	}
	
	@RequestMapping("/login")//url
	public ModelAndView logar() {
		ModelAndView mv = new ModelAndView("Login");//html
		return mv;
	}
	

//	@RequestMapping("/minhascompras")
//	public ModelAndView minhasCompras() {
//		//Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		//UserDetails user = (UserDetails) auth;
//		
//		Cliente cliente = clienteService.buscaPorLogin(user.getUsername());
//			
//		List<Pedido> pedido = cliente.getCcompras();
//				
//		ModelAndView mv = new ModelAndView("minhascompras");
//		mv.addObject("todasAsCompras", pedido);
//		return mv;
//	}	
	

	/*
	@GetMapping("/listar")
	public ModelAndView listarClientes() {
		List<Cliente> clientes = clienteService.listarTodos();
		ModelAndView mv = new ModelAndView("PaginaListagem");
		mv.addObject("listaClientes", clientes);
		
		return mv;
	}
	
	@RequestMapping("/excluir/{codigo}")
	public ModelAndView deletar(@PathVariable Long codigo) {
		pessoaService.excluirPessoa(codigo);
		ModelAndView mv = new ModelAndView("redirect:/pessoa/listar");
		return mv;
	}
	
	@RequestMapping("/atualizar/{codigo}")
	public ModelAndView atualizarPessoa(@PathVariable Long codigo) {
		Pessoa pessoa = pessoaService.buscarPorId(codigo);
		ModelAndView mv = new ModelAndView("Formulario");
		mv.addObject("pessoa", pessoa);
		return mv;
	}
	*/
}
