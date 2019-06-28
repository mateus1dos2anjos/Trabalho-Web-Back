package com.ufc.br.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ufc.br.model.Pessoa;
import com.ufc.br.model.Prato;
//import com.ufc.br.util.AulaFileUtils;
import com.ufc.br.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepo;
	
	public void Cadastrar(Pessoa pessoa /*, MultipartFile imagem*/ ) {
		
		//String caminho = "imagens/" + pessoa.getNome() + ".png";
		//AulaFileUtils.salvarImagem(caminho, imagem);
		
		pessoa.setSenha(new BCryptPasswordEncoder().encode(pessoa.getSenha()));
		pessoaRepo.save(pessoa);	
	}

	public List<Pessoa> listarTodos() {
		return pessoaRepo.findAll();
	}
	
	public Pessoa buscarPorLogin(String username) {
		return pessoaRepo.findByLogin(username);
	}
	
	/*
	public void excluirPessoa(Long codigo) {
		pessoaRepo.deleteById(codigo);
	}

	public Pessoa buscarPorId(Long codigo) {
		return pessoaRepo.getOne(codigo);
	}
	*/
	//public (){
		
	//}
	
}
