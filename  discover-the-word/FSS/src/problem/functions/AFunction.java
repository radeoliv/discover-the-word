package problem.functions;

public abstract class AFunction {
	
	public double minPosition;
	public double maxPosition;
	public int numDimensoes;
	public int numPeixes;
	public double stepIndInicial;
	public double stepIndFinal;
	public double stepInd;
	public double pesoMin = 1;
	public double pesoMax = 1000;
	public double pesoInicial;
	public int numIteracoes;
	
	public abstract double getFitness(double[] posicao);
	public abstract boolean esteFitnessEhMelhor(double novoFit, double antigoFit);
	public abstract void atualizarStepInd();
	public abstract boolean ehParada(int numIteracoes);
	public abstract String getFunctionName();
	
}
