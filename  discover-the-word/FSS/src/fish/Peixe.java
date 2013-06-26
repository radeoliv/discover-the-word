package fish;

import problem.functions.SphereProblem;
import util.Util;

public class Peixe {

	public double[] posAtual;
	public double fitAtual;

	public double pesoAtual;

	public double deltaF;
	public double[] deltaX;

	private SphereProblem problema;

	public Peixe(SphereProblem problema) {
		this.problema = problema;
		iniciarPeixe();
	}

	public void iniciarPeixe() {
		posAtual = Util.setInitState(problema.numDimensoes,
				problema.minPosition, problema.maxPosition);
		pesoAtual = problema.pesoInicial;
	}
	
	public void calcularFitness(){
		fitAtual = problema.getFitness(posAtual);
	}
	
	public void nadar() {

		int numDimensoes = problema.numDimensoes;

		double[] novaPos = new double[numDimensoes];

		double maxPos = problema.maxPosition;
		double minPos = problema.minPosition;
		
		for (int i = 0; i < numDimensoes; i++) {
			novaPos[i] = posAtual[i]
					+ (Util.generateRandom(-1, 1) * problema.stepInd);
			
			if(novaPos[i] > maxPos){
				novaPos[i] = maxPos;
			}
			if(novaPos[i] < minPos){
				novaPos[i] = minPos;
			}
			
		}
		
		double novoFit = problema.getFitness(novaPos);
		
		calculaDeltaF(novoFit, fitAtual);
		calculaDeltaX(numDimensoes, novaPos);
		
		posAtual = novaPos;
	
	}

	private void calculaDeltaF(double novoFit, double fitAtual) {
		deltaF = novoFit - fitAtual;
	}

	private void calculaDeltaX(int numDimensoes, double[] novaPos) {

		if (deltaX == null) {
			deltaX = new double[numDimensoes];
		}

		for (int i = 0; i < numDimensoes; i++) {
			deltaX[i] = novaPos[i] - posAtual[i];
		}

	}

	public void alimentar(double maiorVariacaoFitness) {
		pesoAtual = pesoAtual + (deltaF / maiorVariacaoFitness);
	}
}

