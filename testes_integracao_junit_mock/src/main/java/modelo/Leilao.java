package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="LEILAO")
public class Leilao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	@Column(name = "ENCERRADO")
	private boolean encerrado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA")
	private Calendar data;
	
	@OneToMany(mappedBy = "leilao", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
	private List<Lance> lances;
	
	@Transient
	private final int MAXIMO_NUM_LANCES = 5;
	
	/*
	@Transient
	private Relogio relogio = new RelogioDoSistema();
	*/
	
	public Leilao() {}
	
	/*
	public Leilao(String descricao){
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
		this.data = relogio.hoje();
		this.encerrado = false;
	}
	*/
	
	public Leilao(String descricao, Calendar data){
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
		this.encerrado = false;
		this.data = data;
	}
	
	public void propoe(Lance lance){
		if(this.lances.isEmpty() || podeDarLance(lance.getUsuario())) {
			lance.setLeilao(this);
			this.lances.add(lance);
		}
	}
	
	public int getTotalLances(Usuario usuario) {
		int qtd = 0;
		for(Lance lc : this.lances) {
			if(lc.getUsuario().equals(usuario)) {
				qtd++;
			}
		}
		
		return qtd;
	}
	
	public boolean podeDarLance(Usuario usuario) {
		return !isLanceSeguido(usuario) && getTotalLances(usuario) < MAXIMO_NUM_LANCES;
	}
	
	public boolean isLanceSeguido(Usuario usuario) {
		return ultimoLanceDado().getUsuario().equals(usuario);
	}
	
	public Lance ultimoLanceDado() {
		return this.lances.get(this.lances.size() - 1);
	}
	
	public List<Lance> getLances(){
		return Collections.unmodifiableList(this.lances);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setLances(List<Lance> lances) {
		this.lances = lances;
	}

	public void removeProposta(Usuario usuario) {
		List<Lance> lancesToRemove = new ArrayList<Lance>();
		
		for(Lance lance : this.lances){
			if(lance.getUsuario().equals(usuario)){
				lancesToRemove.add(lance);
			}
		}
		
		for(Lance lance : lancesToRemove){
			lance.setLeilao(null);
			this.lances.remove(lance);
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isEncerrado() {
		return encerrado;
	}

	public void encerra() {
		this.encerrado = true;
	}

	public Calendar getData() {
		return (Calendar)data.clone();
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public boolean temLance() {
		return !(this.getLances() == null || this.getLances().isEmpty());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Leilao other = (Leilao) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		return true;
	}

	public void setEncerrado(boolean encerrado) {
		this.encerrado = encerrado;
	}
	
	/*

	public Relogio getRelogio() {
		return relogio;
	}

	public void setRelogio(Relogio relogio) {
		this.relogio = relogio;
	}
	
	*/
}
