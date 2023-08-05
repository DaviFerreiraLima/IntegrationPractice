package modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import builder.LeilaoBuilder;

class AvaliadorTest {
	
	static Usuario joao; 
	static Usuario maria; 
	static Usuario jose;
	
	Avaliador avaliador;
	
	@BeforeAll
	static void criaUsuariosLances() {
		joao = new Usuario("João");
		maria = new Usuario("Maria");
		jose = new Usuario("José");
		
		System.out.println("[BeforeAll] Executa apenas uma vez, na criação da classe.");
	}
	
	@AfterAll
	static void afterClass() {
		System.out.println("[AfterAll] Executado apenas uma vez, antes da destruição da classe.");
	}
	
	@BeforeEach
	void criaAvaliador() {
		avaliador = new Avaliador("Tiago");
		
		System.out.println("[BeforeEach] Executado antes de cada método de teste.");
	}
	
	@AfterEach
	void after() {
		System.out.println("[AfterEach] Executado depois de cada método de teste.");
	}
	

	@Test
	void avaliaSemLance() {
		Leilao leilao = LeilaoBuilder.umLeilao().para("Playstation 5").build();
		assertThrows(RuntimeException.class, () -> {
			avaliador.avalia(leilao);
		});
	}
	
	@Test 
	void avaliaUmLance(){
		Leilao leilao = LeilaoBuilder.umLeilao()
										.para("Playstation 5")
										.comLance(joao, 1000.0)
										.build();
		avaliador.avalia(leilao);
		assertEquals(1000.0, avaliador.getMaiorLance());
		assertEquals(1000.0, avaliador.getMenorLance());
	}
	
	@Test 
	void avaliaUmLanceZero(){
		Leilao leilao = LeilaoBuilder.umLeilao()
										.para("Playstation 5")
										.comLance(joao, 0.0)
										.build();
		avaliador.avalia(leilao);
		assertEquals(Double.NEGATIVE_INFINITY, avaliador.getMaiorLance());
		assertEquals(Double.POSITIVE_INFINITY, avaliador.getMenorLance());
	}
	
	@Test 
	void avaliaUmLanceNegativo(){
		Leilao leilao = LeilaoBuilder.umLeilao()
										.para("Playstation 5")
										.comLance(joao, -1000.0)
										.build();
		avaliador.avalia(leilao);
		assertEquals(Double.NEGATIVE_INFINITY, avaliador.getMaiorLance());
		assertEquals(Double.POSITIVE_INFINITY, avaliador.getMenorLance());
	}
	
	@Test 
	void avaliaDoisLancesCrescente(){
		Leilao leilao = LeilaoBuilder.umLeilao()
										.para("Playstation 5")
										.comLance(joao, 5000.0)
										.comLance(maria, 6000.0)
										.build();
		avaliador.avalia(leilao);
		assertEquals(6000.0, avaliador.getMaiorLance());
		assertEquals(5000.0, avaliador.getMenorLance());
	}
	
	@Test
	void avaliaDoisLancesCrescenteNegativo() {
		Leilao leilao = LeilaoBuilder.umLeilao()
										.para("Playstation 5")
										.comLance(joao, -2000.0)
										.comLance(maria, 3000.0)
										.build();
		avaliador.avalia(leilao);
		assertEquals(3000.0, avaliador.getMaiorLance());
		assertEquals(3000.0, avaliador.getMenorLance());
	}
	
	@Test
	void avaliaDoisLancesCrescenteZero() {
		Leilao leilao = LeilaoBuilder.umLeilao()
										.para("Playstation 5")
										.comLance(joao, 0.0)
										.comLance(maria, 2000.0)
										.build();
		avaliador.avalia(leilao);
		assertEquals(2000.0, avaliador.getMaiorLance());
		assertEquals(2000.0, avaliador.getMenorLance());
	}
	
	@Test 
	void avaliaDoisLancesDecrescente(){
		Leilao leilao = LeilaoBuilder.umLeilao()
										.para("Playstation 5")
										.comLance(joao, 1500.0)
										.comLance(maria, 1000.0)
										.build();
		avaliador.avalia(leilao);
		assertEquals(1500.0, avaliador.getMaiorLance());
		assertEquals(1000.0, avaliador.getMenorLance());
	}
	
	@Test
	void avaliaDoisLancesDecrescenteNegativo() {
		Leilao leilao = LeilaoBuilder.umLeilao()
										.para("Playstation 5")
										.comLance(joao, 1000.0)
										.comLance(maria, -5000.0)
										.build();
		avaliador.avalia(leilao);
		assertEquals(1000.0, avaliador.getMaiorLance());
		assertEquals(1000.0, avaliador.getMenorLance());
	}
	
	@Test
	void avaliaDoisLancesDecrescenteZero() {
		Leilao leilao = LeilaoBuilder.umLeilao()
										.para("Playstation 5")
										.comLance(joao, 2500.0)
										.comLance(maria, 0.0)
										.build();
		avaliador.avalia(leilao);
		assertEquals(2500.0, avaliador.getMaiorLance());
		assertEquals(2500.0, avaliador.getMenorLance());
	}
	
	@Test
	void avaliaTresLancesCrescente() {
		Leilao leilao = LeilaoBuilder.umLeilao()
										.para("Playstation 5")
										.comLance(joao, 1000.0)
										.comLance(maria, 1500.0)
										.comLance(jose, 2000.0)
										.build();
		avaliador.avalia(leilao);
		assertEquals(2000.0, avaliador.getMaiorLance());
		assertEquals(1000.0, avaliador.getMenorLance());
	}
	
	@Test
	void avaliaTresLancesDecrescente() {
		Leilao leilao = LeilaoBuilder.umLeilao()
										.para("Playstation 5")
										.comLance(joao, 2000.0)
										.comLance(maria, 1500.0)
										.comLance(jose, 1000.0)
										.build();
		avaliador.avalia(leilao);
		assertEquals(2000.0, avaliador.getMaiorLance());
		assertEquals(1000.0, avaliador.getMenorLance());
	}
	
	@Test
	void avaliaTresLancesMisturado() {
		Leilao leilao = LeilaoBuilder.umLeilao()
										.para("Playstation 5")
										.comLance(joao, 2000.0)
										.comLance(maria, 1000.0)
										.comLance(jose, 1500.0)
										.build();
		avaliador.avalia(leilao);
		assertEquals(2000.0, avaliador.getMaiorLance());
		assertEquals(1000.0, avaliador.getMenorLance());
	}
	
	/*
	 * Vers�o JUnit 4
	@Test(expected=NullPointerException.class)
	void exceptionTest() {
		throw(new NullPointerException());
	}
	*/
	@Test
	void exceptionTest() {
		assertThrows(NullPointerException.class, () -> {
			//c�digo que deve ser executado
			throw(new NullPointerException());
		});
	}
	
	/*
	 * Vesr�o JUnit 4
	@Test(timeout=500)
	void timeoutTest() {
		Thread.sleep(600);
		System.out.println("Oi");
	}
	*/
	@Test
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	void timeoutAnotationTest() {
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {}
		
		System.out.println("Oi");
	}
	
	@Test
	void timeoutAssertionTest() {
		assertTimeout(Duration.ofMillis(500), () -> {
			//c�digo que deve ser executado
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {}
			
			System.out.println("Oi");
		});
	}

}
