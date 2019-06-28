package com.ufc.br.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity	
public class Pessoa implements UserDetails {
	

//	@ManyToOne()
//	@JoinColumn(name="pessoaId")
//	private Pessoa pessoa;
	
	@OneToMany(mappedBy="pessoa")
	private List<Pedido> pedido;
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long codigo;
	
	@NotBlank(message = "Preencha o campo nome")
	private String nome;
	
	@NotBlank(message = "Preencha o campo cpf")
	private String cpf;
	
	@NotNull(message = "o campo data não pode ser nulo")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@NotBlank(message = "Preencha o campo endereco")
	private String endereco;
	
	@NotBlank(message = "Preencha o campo login")
	private String login;
	
	@NotBlank(message = "Preencha o campo senha")
	private String senha;
	
	@NotBlank(message = "Preencha o campo email")
	private String email;
	
//	insert into pessoa (nome, cpf, dataNascimento, endereco, login, senha, email) values ('getente', '125555', '12/02/1990', 'rua a', 'gerente', 'gerente', 'mat@hotmail.com');  
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "pessoa_roles",
			joinColumns = @JoinColumn(
			  name = "pessoa_codigo", referencedColumnName = "codigo"),
			inverseJoinColumns = @JoinColumn(
			  name = "role_codigo", referencedColumnName = "papel"))
	private List<Role> roles;
			
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return (Collection<? extends GrantedAuthority>) this.roles;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.senha;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.login;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	public List<Pedido> getPedido() {
		return pedido;
	}
	public void setPedido(List<Pedido> pedido) {
		this.pedido = pedido;
	}
	


}
