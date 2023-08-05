package modelo;

public class Avaliador {
	
	private double maiorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorDeTodos = Double.POSITIVE_INFINITY;
	private String nome;
	
	public Avaliador(String nome){
		this.nome = nome;
	}
	
	public void avalia(Leilao leilao){
		if(!leilao.temLance()) {
			throw new RuntimeException("Leilão não possui lance!");
		}
		for(Lance lance : leilao.getLances()){
			if(lance.getValor() > 0){
				if(lance.getValor() > maiorDeTodos){
					maiorDeTodos = lance.getValor();
				}
				if(lance.getValor() < menorDeTodos){
					menorDeTodos = lance.getValor();
				}
			}
		}
	}
	
	public double getMaiorLance(){
		return maiorDeTodos;
	}
	
	public double getMenorLance(){
		return menorDeTodos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
