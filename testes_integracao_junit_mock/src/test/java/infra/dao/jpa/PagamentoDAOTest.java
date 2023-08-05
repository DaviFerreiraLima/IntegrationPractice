package infra.dao.jpa;

import builder.LeilaoBuilder;
import modelo.Leilao;
import modelo.Pagamento;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PagamentoDAOTest {

    private PagamentoDAO pagamentoDAO;

    static EntityManager manager;

    @BeforeAll
    public static void beforeAll() {
        manager = TestEntityManagerBuilder.getEntityManager();
    }

    @BeforeEach
    public void before() {
        pagamentoDAO = new PagamentoDAO(manager);
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
    public void salvarSucesso() {

        Pagamento pagamento = new Pagamento(100, Calendar.getInstance());

        pagamentoDAO.salva(pagamento);

        assertNotNull(pagamento.getId());
    }
}
