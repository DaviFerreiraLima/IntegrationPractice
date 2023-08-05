package infra.dao.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import builder.LeilaoBuilder;
import modelo.Leilao;

class LeilaoDAOTest {

	static EntityManager manager;
	LeilaoDAO leilaoDAO;
	
	@BeforeAll
	public static void beforeAll() {
		manager = TestEntityManagerBuilder.getEntityManager();
	}
	
	@BeforeEach
	public void before() {
		leilaoDAO = new LeilaoDAO(manager);
		manager.getTransaction().begin();
	}
	
	@AfterAll
	public static void afterAll() {
		manager.close();
	}
	
	@AfterEach
	public void after() {
		manager.getTransaction().rollback();
	}
	
	@Test
	public void deveContarLeiloesNaoEncerrados() {
		Leilao leilaoEncerrado = LeilaoBuilder.umLeilao().para("Playstation 5").encerrado().build();
		
		Leilao leilaoCorrente = LeilaoBuilder.umLeilao().para("PC Gamer").build();
		
		leilaoDAO.salva(leilaoEncerrado);
		leilaoDAO.salva(leilaoCorrente);
		
		assertEquals(1, leilaoDAO.total());
	}

	@Test
	public void deveRetornarZeroAoContarLeiloesNaoEncerrados() {
		Leilao leilao1 = LeilaoBuilder.umLeilao().para("Playstation 5").encerrado().build();
		
		Leilao leilao2 = LeilaoBuilder.umLeilao().para("PC Gamer").encerrado().build();
		
		leilaoDAO.salva(leilao1);
		leilaoDAO.salva(leilao2);
		
		assertEquals(0, leilaoDAO.total());
		assertEquals(2, leilaoDAO.encerrados().size());
	}
	
	@Test
	public void deveTrazerLeiloesNaoEncerradosNoPeriodo() {
		Calendar inicio = Calendar.getInstance();
		inicio.add(Calendar.DAY_OF_MONTH, -10);
		
		Leilao leilao1 = LeilaoBuilder.umLeilao()
										.para("Ferrari 2021")
										.diasAtras(5)
										.build();
		leilaoDAO.salva(leilao1);
		
		Leilao leilao2 = LeilaoBuilder.umLeilao()
										.para("Audi 2021")
										.diasDepois(1)
										.build();
		leilaoDAO.salva(leilao2);
		
		Leilao leilao3 = LeilaoBuilder.umLeilao()
										.para("Gol 2021")
										.diasAtras(11)
										.build();
		leilaoDAO.salva(leilao3);
		
		Leilao leilao4 = LeilaoBuilder.umLeilao()
										.para("Fiat Uno 2021")
										.diasAtras(10)
										.build();
		leilaoDAO.salva(leilao4);
		
		Leilao leilao5 = LeilaoBuilder.umLeilao()
										.para("Novo Fusca 2021")
										.build();
		leilaoDAO.salva(leilao5);
		
		Calendar fim = Calendar.getInstance();
		
		assertEquals(3, leilaoDAO.porPeriodo(inicio, fim).size());
	}
	
	
	@Test
	public void naoDeveTrazerLeiloesEncerradosNoPeriodo() {
		Calendar inicio = Calendar.getInstance();
		inicio.add(Calendar.DAY_OF_MONTH, -10);
		
		Leilao leilao1 = LeilaoBuilder.umLeilao()
										.para("Ferrari 2021")
										.diasAtras(5)
										.encerrado()
										.build();
		leilaoDAO.salva(leilao1);
		
		Leilao leilao4 = LeilaoBuilder.umLeilao()
										.para("Fiat Uno 2021")
										.diasAtras(10)
										.encerrado()
										.build();
		leilaoDAO.salva(leilao4);
		
		Leilao leilao5 = LeilaoBuilder.umLeilao()
										.para("Novo Fusca 2021")
										.encerrado()
										.build();
		leilaoDAO.salva(leilao5);
		
		Calendar fim = Calendar.getInstance();
		
		assertEquals(0, leilaoDAO.porPeriodo(inicio, fim).size());
		assertEquals(3, leilaoDAO.encerrados().size());
	}

}
