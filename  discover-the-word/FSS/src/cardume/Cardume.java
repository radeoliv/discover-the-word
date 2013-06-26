package cardume;

import java.util.Arrays;

import util.Util;

import fish.Peixe;

public class Cardume {
	
	Peixe[] cardume;
	AbsProblema problema;
	double pesoAtualCardume;
	
	public Cardume(AbsProblema problema){
		this.problema = problema;
	}
	
	public void iniciarCardume(){
		int tamCardume = problema.numPeixes;		
		cardume = new Peixe[tamCardume];
		for (int i = 0; i < tamCardume; i++) {
			cardume[i] = new Peixe(problema);
		}
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
