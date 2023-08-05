package infra.dao.jpa;

import javax.persistence.EntityManager;

import infra.dao.RepositorioDePagamentos;
import modelo.Pagamento;

public class PagamentoDAO implements RepositorioDePagamentos{
	
	private DAO<Pagamento> dao;
	@SuppressWarnings("unused")
	private EntityManager manager;
	
	public PagamentoDAO(EntityManager manager) {
		this.manager = manager;
		dao = new DAO<Pagamento>(manager, Pagamento.class);
	}

	@Override
	public void salva(Pagamento pagamento) {
		dao.adiciona(pagamento);
	}

}
