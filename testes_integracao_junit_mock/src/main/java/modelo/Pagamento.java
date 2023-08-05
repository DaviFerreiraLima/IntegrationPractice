package modelo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PAGAMENTO")
public class Pagamento implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "VALOR")
	private double valor;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA")
	private Calendar data;
	
	public Pagamento() {}

	public Pagamento(double valor, Calendar data) {
		this.valor = valor;
		this.data = data;
	}
	public double getValor() {
		return valor;
	}
	public Calendar getData() {
		return data;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public void setData(Calendar data) {
		this.data = data;
	}
}
