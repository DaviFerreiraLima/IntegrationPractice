package infra.dao;

import modelo.Pagamento;

public interface RepositorioDePagamentos {
    void salva(Pagamento pagamento);
}
