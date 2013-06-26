package cardume;

public class AbsProblema {
	
	public double minPosition = -100;
	public double maxPosition = 100;
	
	public int numDimensoes = 50;
	public int numPeixes = 50;
	
	public double stepIndInicial = 0.1;
	public double stepIndFinal = 0.00001;

	public double stepInd;
	
	public double pesoInicial = 1.0;
	
	public int numIteracoes = 10000;
	
	public AbsProblema(){
		stepInd = stepIndInicial;
	}
	
	public double getFitness(double[] posicao){
		double result = 0;
		for (double d : posicao) {
			result += Math.pow(d, 2.0);
		}
		return result;
	}
	
	/**
	 * O primeiro fitness é melhor que o segundo?
	 * @param novoFit
	 * @param antigoFit
	 * @return
	 */
	public boolean esteFitnessEhMelhor(double novoFit, double antigoFit){
		return novoFit < antigoFit;
	}
	
	public void atualizarStepInd(){
		stepInd += (stepIndInicial - stepIndFinal) / (double) numIteracoes;
	}
	
	public boolean ehParada(int numIteracoes){
		return numIteracoes >= this.numIteracoes;
	}
	
}
