package infra.dao.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public class DAO<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final Class<T> classe;
	private EntityManager em;

	public DAO(EntityManager manager, Class<T> classe) {
		this.em = manager;
		this.classe = classe;
	}

	public T adiciona(T t) {
		em.persist(t);
		return t;
	}

	public void remove(T t) {
		em.remove(em.merge(t));
	}

	public T atualiza(T t) {
		T ax = em.merge(t);
		return ax;
		
	}
	
	public void beginTransaction() {
		em.getTransaction().begin();
	}
	
	public void rollbackTransaction() {
		em.getTransaction().rollback();
	}
	
	public void flushStatements() {
		em.flush();
	}
	
	public void commitTransaction() {
		em.getTransaction().commit();
	}
	
	public void close() {
		em.close();
	}

	public List<T> listaTodos() {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).getResultList();

		return lista;
	}

	public T buscaPorId(Integer id) {
		T instancia = em.find(classe, id);
		return instancia;
	}
}
