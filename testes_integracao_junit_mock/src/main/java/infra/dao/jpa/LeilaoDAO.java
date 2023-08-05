package infra.dao.jpa;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import infra.dao.RepositorioDeLeiloes;
import modelo.Leilao;

public class LeilaoDAO implements RepositorioDeLeiloes{
	
	private DAO<Leilao> dao;
	private EntityManager manager;
	
	public LeilaoDAO(EntityManager manager) {
		this.manager = manager;
		dao = new DAO<Leilao>(manager, Leilao.class);
	}

	@Override
	public void salva(Leilao leilao) {
		dao.adiciona(leilao);
	}
	
	@Override
	public void atualiza(Leilao leilao) {
		dao.adiciona(leilao);
	}

	@Override
	public List<Leilao> encerrados() {
		String hql = "from Leilao l where l.encerrado = true";
		TypedQuery<Leilao> query = manager.createQuery(hql,Leilao.class);
		
		return query.getResultList();
	}

	@Override
	public List<Leilao> correntes() {
		String hql = "from Leilao l where l.encerrado = false";
		TypedQuery<Leilao> query = manager.createQuery(hql,Leilao.class);
		
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Leilao> porPeriodo(Calendar inicio, Calendar fim) {
		return manager.createQuery("from Leilao l where l.data " +
				"between :inicio and :fim and l.encerrado = false")
				.setParameter("inicio", inicio)
				.setParameter("fim", fim)
				.getResultList();
	}
	
	public Long total() {
		return (Long) manager.createQuery("select count(l) from Leilao l where l.encerrado = false")
				.getSingleResult();
	}
}
