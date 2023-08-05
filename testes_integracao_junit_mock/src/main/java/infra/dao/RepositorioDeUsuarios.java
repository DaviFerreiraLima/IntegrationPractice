package infra.dao;

import modelo.Usuario;

public interface RepositorioDeUsuarios {
	
	public Usuario porId(int id);
	
	 public Usuario porNomeEEmail(String nome, String email);
	 
	 public void salvar(Usuario usuario);
	 
	 public void remover(Usuario usuario);
	 
	 public void atualizar(Usuario usuario);

}
