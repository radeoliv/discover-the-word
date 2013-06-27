package problem.functions;

public class GriewankFunction extends AFunction{
	
	public GriewankFunction(){
		minPosition = -512;
		maxPosition = 512;
		numDimensoes = 30;
		numPeixes = 30;
		stepIndInicial = 0.1;
		stepIndFinal = 0.00001;
		pesoInicial = 500;
		numIteracoes = 10000;
		stepInd = stepIndInicial;
	}
	
	public double getFitness(double[] posicao){
		double result = 0;
		int size = posicao.length;
		
		double resSum = 0;
		double resMult = 1;
		
		for (int i = 0; i < size; i++) {
			resSum += posicao[i] * posicao[i];
			resMult *= Math.cos(posicao[i] / Math.sqrt(i+1));
		}
		
		resSum /= 4000.0;
		result = 1 + resSum - resMult;
		
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

	@Override
	public String getFunctionName() {
		return "GriewankFunction";
	}
	
}
