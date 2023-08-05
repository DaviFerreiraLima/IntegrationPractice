package builder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import modelo.Lance;
import modelo.Leilao;
import modelo.Usuario;

public class LeilaoBuilder {
	
	private String descricao = "";
	private List<Lance> lances = new ArrayList<Lance>();
	private Calendar data = Calendar.getInstance();
	private boolean encerrado = false;
	
	private LeilaoBuilder() {}
	
	public static LeilaoBuilder umLeilao() {
		return new LeilaoBuilder();
	}
	
	public LeilaoBuilder para(String descricao) {
		this.descricao = descricao;
		return this;
	}
	
	public LeilaoBuilder naData(Calendar data) {
		this.data = data;
		return this;
	}
	
	public LeilaoBuilder naData(int dia, int mes, int ano) {
		this.data = Calendar.getInstance();
		this.data.set(ano, mes, dia);
		return this;
	}
	
	public LeilaoBuilder comLance(Lance lance) {
		this.lances.add(lance);
		return this;
	}
	
	public LeilaoBuilder comLance(Usuario usuario, double valor) {
		this.lances.add(new Lance(usuario, valor));
		return this;
	}
	
	public LeilaoBuilder encerrado() {
        this.encerrado = true;
        return this;
    }
	
	public LeilaoBuilder diasAtras(int dias) {
        Calendar data = Calendar.getInstance();
        data.add(Calendar.DAY_OF_MONTH, -dias);

        this.data = data;

        return this;
    }
	
	public LeilaoBuilder diasDepois(int dias) {
        Calendar data = Calendar.getInstance();
        data.add(Calendar.DAY_OF_MONTH, dias);

        this.data = data;

        return this;
    }
	
	public Leilao build() {
		Leilao leilao = new Leilao(this.descricao, this.data);
		leilao.setLances(this.lances);
		leilao.setEncerrado(this.encerrado);
		return leilao;
	}

}
