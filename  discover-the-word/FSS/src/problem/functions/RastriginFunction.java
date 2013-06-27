package problem.functions;

public class RastriginFunction extends AFunction{
	
	public RastriginFunction(){
		minPosition = -5;
		maxPosition = 5;
		numDimensoes = 30;
		numPeixes = 30;
		stepIndInicial = 0.1;
		stepIndFinal = 0.000001;
		pesoInicial = 500;
		numIteracoes = 10000;
		stepInd = stepIndInicial;
	}
	
	public double getFitness(double[] z){
		double result = 0;
		int size = z.length;
		for (int i = 0; i < size; i++) {
			double z2 = z[i]*z[i];
			double cos = -10 * Math.cos(2 * Math.PI * z[i]);
			result += z2 + cos;
		}
		return result + (10*numDimensoes);
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
		return "RastriginFunction";
	}
	
}
