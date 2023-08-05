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

        Usuario usuarioEncontrado = usuarioDAO.porId(usuario.getId());
        assertEquals(usuario, usuarioEncontrado);
    }

    @Test
    public void testPorNomeEEmail() {
        Usuario usuario = new Usuario("Tiago Belo","tiago@example.com");

        usuarioDAO.salvar(usuario);

        Usuario usuarioEncontrado = usuarioDAO.porNomeEEmail("Tiago Belo", "tiago@example.com");
        assertEquals(usuario, usuarioEncontrado);
    }

    @Test
    public void testSalvarUsuario() {

        Usuario usuario = new Usuario("Tiago Belo","tiago@example.com");

        usuarioDAO.salvar(usuario);

        assertNotNull(usuario.getId());
    }

    @Test
    public void testRemoverUsuario() {
        Usuario usuario = new Usuario("Tiago Belo","tiago@example.com");

        usuarioDAO.salvar(usuario);

        usuarioDAO.remover(usuario);

        Usuario usuarioEncontrado = usuarioDAO.porNomeEEmail(usuario.getNome(),usuario.getEmail());
        assertNull(usuarioEncontrado);
    }

    @Test
    public void testAtualizarUsuario() {
        Usuario usuario = new Usuario("Tiago Belo","tiago@example.com");

        usuarioDAO.salvar(usuario);

        usuario.setNome("Tiago Lindo");

        usuarioDAO.atualizar(usuario);

        Usuario usuarioAtualizado = usuarioDAO.porNomeEEmail("Tiago Lindo",usuario.getEmail());
        assertEquals("Tiago Lindo", usuarioAtualizado.getNome());
    }
}
