package fish;

import java.util.Arrays;

import problem.functions.SphereProblem;

import util.Util;


public class Cardume {
	
	Peixe[] cardume;
	SphereProblem problema;
	double pesoAtualCardume;
	double melhorFit = Double.MAX_VALUE;
	double melhorPos = Double.MAX_VALUE;
	
	public Cardume(SphereProblem problema){
		this.problema = problema;
		iniciarCardume();
	}
	
	public void iniciarCardume(){
		int tamCardume = problema.numPeixes;
		pesoAtualCardume = problema.pesoInicial * tamCardume;
		cardume = new Peixe[tamCardume];
		for (int i = 0; i < tamCardume; i++) {
			cardume[i] = new Peixe(problema);
		}
	}
	
	public void comecarPeixarada(){
		int numIteracoes = problema.numIteracoes;
		for (int i = 0; i < numIteracoes; i++) {
			iterarTodosPeixes();
		}
	}
	
	private void iterarTodosPeixes() {
		
		int numPeixes = problema.numPeixes;
		for (int i = 0; i < numPeixes; i++) {
			Peixe peixe = cardume[i];
			peixe.calcularFitness();			// evaluate fitness function
			
			if(peixe.fitAtual < melhorFit){
				melhorFit = peixe.fitAtual;
				System.out.println("Melhor fitness até o momento = " + melhorFit);
			}
			
		}
		
		for (int i = 0; i < numPeixes; i++) {
			Peixe peixe = cardume[i];
			peixe.nadar(); 						// individual movement by using (1)(2)(3)
		}
		
		double maiorDeltaF = -Double.MAX_VALUE;
		
		for (int i = 0; i < numPeixes; i++) {
			Peixe peixe = cardume[i];
			if(peixe.deltaF > maiorDeltaF){
				maiorDeltaF = peixe.deltaF;
			}
		}
		
		for (int i = 0; i < numPeixes; i++) {
			Peixe peixe = cardume[i];
			peixe.alimentar(maiorDeltaF);		// feeding by using (5)
			peixe.calcularFitness();			// evaluate fitness function again =D
		}
		
		iterarInstintivoColetivo();				// instinctive movement by using (6)(7) for each fish
		iterarColetivoVolitivo();				// volitive movement by using (9) or (10) for each fish
		
		problema.atualizarStepInd();			// update step using (4)
	}

	public void iterarInstintivoColetivo(){
		int numDimensoes = problema.numDimensoes;
		int numPeixes = problema.numPeixes;
		
		double[][] multDeltaX_DeltaF = new double[numPeixes][];
		double[] deltaF = new double[numPeixes];
		
		for (int i = 0; i < numPeixes; i++) {
			
			Peixe daVez = cardume[i];
			multDeltaX_DeltaF[i] = Arrays.copyOf(daVez.deltaX, numDimensoes);
			multDeltaX_DeltaF[i] = Util.multVetorPorEscalar(multDeltaX_DeltaF[i], daVez.deltaF);
			
			deltaF[i] = daVez.deltaF;
		}
		
		// calculando somatórios...
		double[] sumNumerador = Util.vetorZero(numDimensoes);
		for (int j = 0; j < numDimensoes; j++) {
			for (int i = 0; i < numPeixes; i++) {
				sumNumerador[j] += multDeltaX_DeltaF[i][j];
			}
		}
		
		double sumDenominador = 0.0;
		for (int i = 0; i < numPeixes; i++) {
			sumDenominador += deltaF[i];
		}
		
		double[] I = Util.multVetorPorEscalar(sumNumerador, 1.0 / sumDenominador);
		
		// iterar peixes
		for (int i = 0; i < numPeixes; i++) {
			Peixe peixe = cardume[i];
			peixe.posAtual[i] = peixe.posAtual[i] + I[i];
		}
	}
	
	public void iterarColetivoVolitivo(){
		
		int numPeixes = problema.numPeixes;
		double[] baricentro = calcularBaricentro();
		double stepVol = 2.0 * problema.stepInd;
		
		// calcular novo peso do cardume
		double novoPesoCardume = 0.0;
		for (int i = 0; i < numPeixes; i++) {
			Peixe peixe = cardume[i];
			novoPesoCardume += peixe.pesoAtual;
		}
		
		if(novoPesoCardume > pesoAtualCardume){
			stepVol = -stepVol;
		}
		
		for (int i = 0; i < numPeixes; i++) {
			
			Peixe peixe = cardume[i];
			
			double[] posAtualPeixe = peixe.posAtual;
			double distancia_xt_bt = Util.distEuclidiana(posAtualPeixe, baricentro);
			double[] xT_menos_bT = Util.subDeDoisVetores(posAtualPeixe, baricentro);
			double rand = Util.generateRandom();
			
			double parteEscalar = stepVol * rand * (1 / distancia_xt_bt);
			
			double[] fatorBaricentro = Util.multVetorPorEscalar(xT_menos_bT, parteEscalar);
			
			double[] novaPos = Util.somaDeDoisVetores(posAtualPeixe, fatorBaricentro);
			
			peixe.posAtual = novaPos;
		}
		
	}
	
	public double[] calcularBaricentro(){
		
		int numDimensoes = problema.numDimensoes;
		int numPeixes = problema.numPeixes;
		
		double[][] multDeltaX_W = new double[numPeixes][];
		double[] W = new double[numPeixes];
		
		for (int i = 0; i < numPeixes; i++) {
			
			Peixe daVez = cardume[i];
			multDeltaX_W[i] = Arrays.copyOf(daVez.deltaX, numDimensoes);
			multDeltaX_W[i] = Util.multVetorPorEscalar(multDeltaX_W[i], daVez.pesoAtual);
			
			W[i] = daVez.pesoAtual;
		}
		
		// calculando somatórios...
		double[] sumNumerador = Util.vetorZero(numDimensoes);
		for (int j = 0; j < numDimensoes; j++) {
			for (int i = 0; i < numPeixes; i++) {
				sumNumerador[j] += multDeltaX_W[i][j];
			}
		}
		
		double sumDenominador = 0.0;
		for (int i = 0; i < numPeixes; i++) {
			sumDenominador += W[i];
		}
		
		return Util.multVetorPorEscalar(sumNumerador, 1.0 / sumDenominador);
	}
	
}
