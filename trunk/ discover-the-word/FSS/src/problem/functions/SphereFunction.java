package problem.functions;

public class SphereFunction extends AFunction{
	
	public SphereFunction(){
		minPosition = -100;
		maxPosition = 100;
		numDimensoes = 50;
		numPeixes = 50;
		stepIndInicial = 0.1;
		stepIndFinal = 0.000001;
		pesoInicial = 500;
		numIteracoes = 10000;
		stepInd = stepIndInicial;
	}
	
	@Override
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
	@Override
	public boolean esteFitnessEhMelhor(double novoFit, double antigoFit){
		return novoFit < antigoFit;
	}
	
	@Override
	public void atualizarStepInd(){
		stepInd += (stepIndInicial - stepIndFinal) / (double) numIteracoes;
	}
	
	@Override
	public boolean ehParada(int numIteracoes){
		return numIteracoes >= this.numIteracoes;
	}

	@Override
	public String getFunctionName() {
		return "SphereFunction";
	}
	
}
