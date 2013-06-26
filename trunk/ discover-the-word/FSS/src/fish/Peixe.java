package fish;

import cardume.AbsProblema;
import util.Util;

public class Peixe {

	public double[] posAtual;
	public double fitAtual;

	public double pesoAtual;

	public double deltaF;
	public double[] deltaX;

	private AbsProblema problema;

	public Peixe(AbsProblema problema) {
		this.problema = problema;
		iniciarPeixe();
	}

	public void iniciarPeixe() {
		posAtual = Util.setInitState(problema.numDimensoes,
				problema.minPosition, problema.maxPosition);
		fitAtual = problema.getFitness(posAtual);
		pesoAtual = problema.pesoInicial;
	}

	// TODO verificar troca de sinal!!!!
	public void alimentar(double[] novaPosicao, double maiorVariacaoFitness) {
		double fitAntigo = problema.getFitness(posAtual);
		double fitNovo = problema.getFitness(novaPosicao);
		double segundoFator = (fitAntigo - fitNovo) / maiorVariacaoFitness;
		pesoAtual = pesoAtual + segundoFator;
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

		if (problema.esteFitnessEhMelhor(novoFit, fitAtual)) {

			calculaDeltaX(numDimensoes, novaPos);
			calculaDeltaF(novoFit, fitAtual);

			posAtual = null;
			posAtual = novaPos;
			fitAtual = novoFit;

		} else {

			deltaX = Util.vetorZero(numDimensoes);
			deltaF = 0.0;

		}

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

}
