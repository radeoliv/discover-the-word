package mecanica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.Constantes;
import util.Util;

public class ACO {
	
	public double[][] distancias;
	public double[][] feromonios;

	public Problema problema;
	
	public Formiga[] listaFormigas;
	
	public Integer[] melhorCaminho;
	public double menorDistancia = Constantes.VALOR_GRANDE;
	
	public ArrayList<Integer[]> melhoresCaminhos = new ArrayList<Integer[]>();
	public ArrayList<Double> menoresDistancias = new ArrayList<Double>();
	
	public ACO(Problema problema, double[][] distancias, int numFormigas) {
		
		this.distancias = distancias;
		int distanciasTam = distancias.length;
		this.problema = problema;
		this.feromonios = new double[distanciasTam][distanciasTam];
		this.listaFormigas = new Formiga[numFormigas];
		
		// inicia feromonios
		for (int i = 0; i < feromonios.length; i++) {
			for (int j = 0; j < feromonios.length; j++) {
				feromonios[i][j] = 1.0f;
			}
		}
		
		// inicializa formigas
		for (int indiceIniFormiga = 0; indiceIniFormiga < numFormigas; indiceIniFormiga++) {
			listaFormigas[indiceIniFormiga] = new Formiga();
			listaFormigas[indiceIniFormiga].reset(problema.numCidades);
		}
	}

	public void iniciarColonia(){
		for (int i = 0; i < Problema.MAX_IT; i++) {
			iterarColonia();
			if(i % 200 == 0){
				saveResults();
			}
		}
	}

	private void saveResults() {
		melhoresCaminhos.add(melhorCaminho);
		menoresDistancias.add(menorDistancia);
	}

	private void iterarColonia() {
		int size = listaFormigas.length;
		Formiga ultimaFormiga = listaFormigas[size-1]; 
		for (int i = 0; i < size; i++) {
			if(ultimaFormiga.voltando){ // se a ultima formiga voltou atualizar feromonio!!
				volteFormiga(listaFormigas[i]);
			} else {
				iteraFormiga(listaFormigas[i]);
			}
		}
		evaporaFeromonio();
	}

	private void evaporaFeromonio() {
		int tam = feromonios.length;
		for (int i = 0; i < tam; i++) {
			for (int j = 0; j < tam; j++) {
				feromonios[i][j] = (1 - problema.P) * feromonios[i][j];
			}
		}
	}

	public void iteraFormiga(Formiga formiga){
		int novoDestino = retorneNovoDestino(formiga);
		if(novoDestino == -1){
			formiga.listaVisitados.add(formiga.posicaoInicial);
			formiga.voltando = true;
		} else {
			formiga.visitou(novoDestino);
		}
	}

	

	public Integer retorneNovoDestino(Formiga formiga){
		
		ArrayList<Integer> naoVisitadas = formiga.listaNaoVisitados;
		
		// caso não tenha mais cidades para visitar retorna -1
		if(formiga.listaNaoVisitados.size() == 0){
			return -1;
		}
		
		int posicaoAtual = formiga.posicaoAtual;
		
		// gerando vetor com probabilidades
		double[] vetorPossibilidades = new double[naoVisitadas.size()];
		
		// pegando distancias e qtd de feromonios das possibilidades
		double[] vetorFeromonio = feromonios[posicaoAtual];
		double[] vetorDistancias = distancias[posicaoAtual];
		
		// dados
		double alpha = problema.alpha;
		double beta = problema.beta;
		
		// calculando possibilidades
		int tamVetor= vetorPossibilidades.length;
		
		// sum(Tij * Nij)
		double sum = 0; 	
		
		for (int i = 0; i < tamVetor; i++) {
			int cidadeDaVez = naoVisitadas.get(i);
			double Tij = Math.pow(vetorFeromonio[cidadeDaVez], alpha);
			double Nij = Math.pow(1.0d / vetorDistancias[cidadeDaVez], beta);
			vetorPossibilidades[i] = Tij * Nij;
			sum += vetorPossibilidades[i];
		}
		
		// calculando probabilidades
		for (int i = 0; i < tamVetor; i++) {
			vetorPossibilidades[i] = vetorPossibilidades[i] / sum;
		}
		
		// calculando intervalos
		for (int i = 1; i < tamVetor; i++) {
			vetorPossibilidades[i] = vetorPossibilidades[i] + vetorPossibilidades[i-1];
		}
		
		// intervalo [0-1]
		double numRandom = Util.generateRandom();
		int indiceVetorNovaCidade = 0;
		
		// pegando indice do novo destino (cidade)
		for (int i = 0; i < tamVetor; i++) {
			if(numRandom <= vetorPossibilidades[i]){
				indiceVetorNovaCidade = i;
				break;
			}
		}
		
		return naoVisitadas.get(indiceVetorNovaCidade);
	}

	public void volteFormiga(Formiga formiga){
		
		List<Integer> cidadesVisitadasList = formiga.listaVisitados;
		int tam = cidadesVisitadasList.size();
		
		Integer[] cidadesVisitadas = new Integer[tam];
		for (int i = 0; i < tam; i++) {
			cidadesVisitadas[i] = cidadesVisitadasList.get(i);
		}		
		
		double distanciaTotal = 0.0;
		for (int i = 0; i < tam - 1; i++) {

			int cidadeUm = cidadesVisitadas[i];
			int cidadeDois = cidadesVisitadas[i+1];
			
			double distCidades = distancias[cidadeUm][cidadeDois];
			feromonios[cidadeUm][cidadeDois] += problema.Q;		// aumenta quantidade de feromonios
			feromonios[cidadeDois][cidadeUm] += problema.Q;		// aumenta quantidade de feromonios
			
			distanciaTotal += distCidades;
		}
		
		if(distanciaTotal < menorDistancia){
			menorDistancia = distanciaTotal;
			melhorCaminho = cidadesVisitadas;
		}
		
		formiga.reset(problema.numCidades);
		
	}

}
