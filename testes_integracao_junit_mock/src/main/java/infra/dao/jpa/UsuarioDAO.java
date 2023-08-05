package infra.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import infra.dao.RepositorioDeUsuarios;
import modelo.Usuario;

public class UsuarioDAO implements RepositorioDeUsuarios{

	private DAO<Usuario> dao;
	private EntityManager manager;
	
	public UsuarioDAO(EntityManager manager) {
		this.manager = manager;
		dao = new DAO<Usuario>(manager, Usuario.class);
	}

	@Override
	public Usuario porId(int id) {
		return dao.buscaPorId(id);
	}

	@Override
	public Usuario porNomeEEmail(String nome, String email) {
		String hql = "from Usuario u where u.nome = :nome and u.email = :email";
		TypedQuery<Usuario> query = manager.createQuery(hql,Usuario.class)
				.setParameter("nome", nome)
				.setParameter("email", email);
		
		Usuario usuario = null;
		
		try {
			usuario = query.getSingleResult();
		}catch (Exception e) {}
		
		return usuario; 
	}

	@Override
	public void salvar(Usuario usuario) {
		dao.adiciona(usuario);
	}

	public void remover(Usuario usuario) {
		dao.remove(usuario);
	}

	@Override
	public void atualizar(Usuario usuario) {
		dao.atualiza(usuario);
	}
}
