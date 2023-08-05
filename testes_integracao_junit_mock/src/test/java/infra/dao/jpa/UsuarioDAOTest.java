package infra.dao.jpa;

import modelo.Usuario;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDAOTest {

    static EntityManager manager;
    private UsuarioDAO usuarioDAO;

    @BeforeAll
    public static void beforeAll() {
        manager = TestEntityManagerBuilder.getEntityManager();
    }

    @BeforeEach
    public void before() {
        usuarioDAO = new UsuarioDAO(manager);
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
    public void testPorId() {

        Usuario usuario = new Usuario("Tiago Belo","tiago@example.com");

        usuarioDAO.salvar(usuario);

        Usuario foundUsuario = usuarioDAO.porId(usuario.getId());
        assertEquals(usuario, foundUsuario);
    }

    @Test
    public void testPorNomeEEmail() {
        Usuario usuario = new Usuario("Tiago Belo","tiago@example.com");

        usuarioDAO.salvar(usuario);

        Usuario foundUsuario = usuarioDAO.porNomeEEmail("Tiago Belo", "tiago@example.com");
        assertEquals(usuario, foundUsuario);
    }

    @Test
    public void testSalvar() {

        Usuario usuario = new Usuario("Tiago Belo","tiago@example.com");

        usuarioDAO.salvar(usuario);

        assertNotNull(usuario.getId());
    }

    @Test
    public void testRemover() {
        Usuario usuario = new Usuario("Tiago Belo","tiago@example.com");

        usuarioDAO.salvar(usuario);

        usuarioDAO.remover(usuario);

        Usuario foundUsuario = usuarioDAO.porNomeEEmail(usuario.getNome(),usuario.getEmail());
        assertNull(foundUsuario);
    }

    @Test
    public void testAtualizar() {
        Usuario usuario = new Usuario("Tiago Belo","tiago@example.com");

        usuarioDAO.salvar(usuario);

        usuario.setNome("Tiago Lindo");

        usuarioDAO.atualizar(usuario);

        Usuario UpdatedUsuario = usuarioDAO.porNomeEEmail("Tiago Lindo",usuario.getEmail());
        assertEquals("Tiago Lindo", UpdatedUsuario.getNome());
    }
}
