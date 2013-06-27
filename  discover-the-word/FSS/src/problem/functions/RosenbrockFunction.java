package problem.functions;

public class RosenbrockFunction extends AFunction{
	
	public RosenbrockFunction(){
		pesoMin = 1;
		pesoMax = 1000;
		minPosition = -100;
		maxPosition = 100;
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
		for (int i = 0; i < size - 1; i++) {
			double fstPart = 100 * Math.pow( (z[i]*z[i]) - z[i+1] , 2);
			double sndPart = Math.pow((1 - z[i]), 2);
			result += fstPart + sndPart;
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

	@Override
	public String getFunctionName() {
		return "RosenbrockFunction";
	}
	
}
