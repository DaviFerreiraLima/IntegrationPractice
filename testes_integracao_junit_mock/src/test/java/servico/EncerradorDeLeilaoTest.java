package servico;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import builder.LeilaoBuilder;
import infra.EnviadorDeEmail;
import infra.Relogio;
import infra.dao.RepositorioDeLeiloes;
import modelo.Leilao;

/*
 * Testes
 * 
 * Uma lista com leilões a serem encerrados
 * Uma lista com nenhum leilão a ser encerrado
 * Uma lista com um leilão um dia antes de ser encerrado
 * Uma lista com um leilão no dia exato de ser encerrado
 */
public class EncerradorDeLeilaoTest {
	
	private RepositorioDeLeiloes daoFalso;
	private EnviadorDeEmail carteiroFalso;
	private EncerradorDeLeilao encerrador;
	
	@BeforeEach
	public void initTest() {
		daoFalso = mock(RepositorioDeLeiloes.class);
		carteiroFalso = mock(EnviadorDeEmail.class);
		encerrador = new EncerradorDeLeilao(daoFalso, carteiroFalso);
	}

	// Uma lista com leilões a serem encerrados	
	@Test
	public void deveEncerrarLeiloesQueComecaramUmaSemanaAtras() {
		
		Leilao leilao1 = LeilaoBuilder.umLeilao()
										.para("Playstation 5")
										.naData(26, 5, 2020)
										.build();
		
		
		Leilao leilao2 = LeilaoBuilder.umLeilao()
										.para("Playstation 4")
										.naData(6,1,2021)
										.build();
		
		when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));
		encerrador.encerra();
		
		assertEquals(2, encerrador.getTotalEncerrados());
		assertTrue(leilao1.isEncerrado());
		assertTrue(leilao2.isEncerrado());
		
		verify(daoFalso, times(1)).atualiza(leilao1);
		verify(daoFalso, times(1)).atualiza(leilao2);
		verify(carteiroFalso, times(1)).envia(leilao1);
		verify(carteiroFalso, times(1)).envia(leilao2);
	}
	
	// Uma lista com nenhum leilão a ser encerrado
	@Test
	public void deveEncerrarSemLeiloes() {
		when(daoFalso.correntes()).thenReturn(new ArrayList<Leilao>());
		encerrador.encerra();
		assertEquals(0, encerrador.getTotalEncerrados());
	}
	
	// Uma lista com um leilão um dia antes de ser encerrado
	@Test
	public void deveEncerrarComUmLeilaoDiaAntes() {
		Relogio relogioFalso = mock(Relogio.class);
		
		Leilao leilao1 = LeilaoBuilder.umLeilao()
										.para("Playstation 5")
										.naData(1, 5, 2020)
										.build();
		
		Calendar dataHoje = Calendar.getInstance();
		dataHoje.set(2020, 5, 7);
		
		encerrador.setRelogio(relogioFalso);
		
		when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1));
		when(relogioFalso.hoje()).thenReturn(dataHoje);
		
		encerrador.encerra();
		
		assertEquals(0, encerrador.getTotalEncerrados());
	}
	
	// Uma lista com um leilão no dia de ser encerrado
	@Test
	public void deveEncerrarComUmLeilaoNoDia() {
		Relogio relogioFalso = mock(Relogio.class);
		
		Leilao leilao1 = LeilaoBuilder.umLeilao()
										.para("Playstation 5")
										.naData(1, 5, 2020)
										.build();
		
		Calendar dataHoje = Calendar.getInstance();
		dataHoje.set(2020, 5, 8);
		
		encerrador.setRelogio(relogioFalso);
		
		when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1));
		when(relogioFalso.hoje()).thenReturn(dataHoje);
		
		encerrador.encerra();
		
		assertEquals(1, encerrador.getTotalEncerrados());
	}
	
	// Uma lista com um leilão um dia depois de ser encerrado
	@Test
	public void deveEncerrarComUmLeilaoDiaDepois() {
		Relogio relogioFalso = mock(Relogio.class);
		
		Leilao leilao1 = LeilaoBuilder.umLeilao()
										.para("Playstation 5")
										.naData(1, 5, 2020)
										.build();
		
		Calendar dataHoje = Calendar.getInstance();
		dataHoje.set(2020, 5, 9);
		
		encerrador.setRelogio(relogioFalso);
		
		when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1));
		when(relogioFalso.hoje()).thenReturn(dataHoje);
		
		encerrador.encerra();
		
		assertEquals(1, encerrador.getTotalEncerrados());
	}
}
