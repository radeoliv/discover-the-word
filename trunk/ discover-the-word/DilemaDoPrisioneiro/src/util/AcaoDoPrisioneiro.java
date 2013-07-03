package util;

/**
 * 
 * Aqui estão definidos os menores e maiores scores em caso de cooperação ou denuncia
 * (Lembrando que a lógica de chamar eles, dependenderá da ação dos prisioneiros)
 */
public enum AcaoDoPrisioneiro {
	
	COOPEROU(3, 5), 
	DENUNCIOU(-3, -5);
	
	private int menorScore;
	private int maiorScore;
	
	AcaoDoPrisioneiro(int menorScore, int maiorScore){
		this.menorScore = menorScore;
		this.maiorScore = maiorScore;
	}

	public int getMenorScore() {
		return menorScore;
	}

	public void setMenorScore(int menorScore) {
		this.menorScore = menorScore;
	}

	public int getMaiorScore() {
		return maiorScore;
	}

	public void setMaiorScore(int maiorScore) {
		this.maiorScore = maiorScore;
	}
}
