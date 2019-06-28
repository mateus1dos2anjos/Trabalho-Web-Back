package com.ufc.br.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ufc.br.security.UserDetailsServiceImplementacao;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsServiceImplementacao userDetailsImplementacao;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		
		//todo mundo pode acessar
		.antMatchers("/pessoa/formularioCadastro").permitAll()
		.antMatchers("/pessoa/salvar").permitAll()
		
		.antMatchers("/prato/formularioPrato").permitAll()
		.antMatchers("/prato/salvarPrato").permitAll()
		.antMatchers("/prato/listar").permitAll()
		.antMatchers("/carrinho/").hasRole("USER")
		//.antMatchers("/prato/excluir/*").hasRole("ADMIN")
		//.antMatchers("/prato/atualizar/*").hasRole("ADMIN")
		
		
		//qlqer outra a pessoa tem q ta autenticada
		.anyRequest().authenticated()
		
		
		//permissoes
		.and()
		.formLogin()
		.loginPage("/pessoa/login").permitAll().defaultSuccessUrl("/prato/listar")
		
		.and()
		.logout()
		.logoutSuccessUrl("/pessoa/login?logout")
		.permitAll();
		
	}

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsImplementacao).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	//ignorar esses arquivos para permitir que eles apareçam
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/imagens/**", "/img/**");
	}

	
	
	
}
