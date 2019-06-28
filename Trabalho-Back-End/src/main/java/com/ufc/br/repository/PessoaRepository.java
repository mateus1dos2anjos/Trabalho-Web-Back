package com.ufc.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufc.br.model.Pessoa;
import com.ufc.br.model.Prato;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	
	Pessoa findByLogin (String username);
	
	
	
}
