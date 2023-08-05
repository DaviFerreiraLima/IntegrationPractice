package infra.dao;

import java.util.List;

import modelo.Leilao;

public interface RepositorioDeLeiloes {
	
	void salva(Leilao leilao);
    List<Leilao> encerrados();
    List<Leilao> correntes();
    void atualiza(Leilao leilao);

}
