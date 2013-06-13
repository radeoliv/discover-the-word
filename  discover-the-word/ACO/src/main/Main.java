package main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import mecanica.ACO;
import mecanica.Problema;
import textoutil.Escritor;
import util.BancoDeDados;


public class Main {
	
	static Escritor escritor = new Escritor();
	
	public static void main(String[] args) throws Exception {
		runProb(BancoDeDados.OLIVER30);
		System.out.println("< Fim Oliver30 >");
		runProb(BancoDeDados.ATT48);
		System.out.println("< Fim Att48 >");
		runProb(BancoDeDados.EIL51);
		System.out.println("< Fim Eil51 >");
	}
	
	static void runProb(BancoDeDados enumProblema) throws Exception{
		
		Problema problema = new Problema(enumProblema);
		double[][] matriz = problema.carregarArquivo();
		ArrayList<ACO> execucoes = new ArrayList<>();
		
		for (int i = 0; i < Problema.QTD_EXECUCOES; i++) {
			ACO aco = new ACO(problema, matriz, 2*problema.numCidades);
			aco.iniciarColonia();
			execucoes.add(aco);
		}
		
		// 4.1
		ArrayList<Double> mediaMenoresDist = calcularMediasMenoresDist(execucoes);
		Main.escreverArqTexto(mediaMenoresDist, problema.nomeProblema + "_EvolucaoDosComprimentos.txt");
		
		// 4.2
		ArrayList<Double> mediaNodeBranchCont = calcularNodeBranchContMedia(execucoes);
		Main.escreverArqTexto(mediaNodeBranchCont, problema.nomeProblema + "_EvolucaoDoNodeBranching.txt");
		
		// 4.3
		ArrayList<Double> menoresDistFinais = pegarMenoresDistFinais(execucoes);
		pegarMediaEDesvioPadrao(menoresDistFinais, problema.nomeProblema + "_DesvioPadraoEMedia.txt");
		
	}
	
	private static ArrayList<Double> calcularNodeBranchContMedia(ArrayList<ACO> acos) {
		
		ArrayList<ArrayList<Integer>> contadoresBranch = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < acos.size(); i++) {
			ArrayList<Integer> contBranchDaSimul = acos.get(i).nodeBranching;
			contadoresBranch.add(contBranchDaSimul);
		}
		
		int qtdDeRodadasPor200It = contadoresBranch.get(0).size();
		ArrayList<Double> mediaContBranch = new ArrayList<Double>();
		for (int i = 0; i < qtdDeRodadasPor200It; i++) {
			double sum = 0.0;
			int contsBranchesSize = contadoresBranch.size();
			for (int j = 0; j < contsBranchesSize; j++) {
				sum += contadoresBranch.get(j).get(i);
			}
			mediaContBranch.add(sum / contsBranchesSize);
		}
		
		return mediaContBranch;
	}
	
	private static void pegarMediaEDesvioPadrao(ArrayList<Double> menoresDistFinais, String path) throws IOException {
		
		int tam = menoresDistFinais.size();
		
		escritor.atualizeCaminhoDoArquivo(path);
		escritor.inicie();
		escritor.escreva("media = ");

		double media = 0.0;
		for (int i = 0; i < tam; i++) {
			media += menoresDistFinais.get(i);
		}
		media /= tam;
		
		escritor.escreva(media + ";");
		escritor.pularLinha();
		
		double desvioPadrao = 0.0;
		double[] variancia = new double[tam];
		for (int i = 0; i < tam; i++) {
			variancia[i] = Math.pow(menoresDistFinais.get(i) - media, 2);
		}
		for (int i = 0; i < tam; i++) {
			desvioPadrao += variancia[i];
		}
		
		desvioPadrao = desvioPadrao / (tam - 1);
		desvioPadrao = Math.sqrt(desvioPadrao);
		escritor.escreva("desvioPadrao = "+ desvioPadrao+";");
		escritor.fechar();
	}

	private static ArrayList<Double> pegarMenoresDistFinais(ArrayList<ACO> acos) {
		
		ArrayList<ArrayList<Double>> menoresDistancias = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < acos.size(); i++) {
			ArrayList<Double> menorDistDaSimul = acos.get(i).menoresDistancias;
			menoresDistancias.add(menorDistDaSimul);
		}
		
		int qtdDeRodadasPor200It = menoresDistancias.get(0).size();
		ArrayList<Double> melhoresFinais = new ArrayList<Double>();
		for (int i = 0; i < qtdDeRodadasPor200It; i++) {
			melhoresFinais.add(menoresDistancias.get(i).get(qtdDeRodadasPor200It-1));
		}

		return melhoresFinais;
	}

	static ArrayList<Double> calcularMediasMenoresDist(ArrayList<ACO> acos) {
		
		ArrayList<ArrayList<Double>> menoresDistancias = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < acos.size(); i++) {
			ArrayList<Double> menorDistDaSimul = acos.get(i).menoresDistancias;
			menoresDistancias.add(menorDistDaSimul);
		}
		
		int qtdDeRodadasPor200It = menoresDistancias.get(0).size();
		ArrayList<Double> mediaDistancias = new ArrayList<Double>();
		for (int i = 0; i < qtdDeRodadasPor200It; i++) {
			double sum = 0.0;
			int menoresDistanciasSize = menoresDistancias.size();
			for (int j = 0; j < menoresDistanciasSize; j++) {
				sum += menoresDistancias.get(j).get(i);
			}
			mediaDistancias.add(sum / menoresDistanciasSize);
		}
		
		return mediaDistancias;
	}

	static void escreverArqTexto(ArrayList<Double> arraylist, String nameProblem) throws IOException{
		escritor.atualizeCaminhoDoArquivo(nameProblem);
		escritor.inicie();
		escritor.escreva("vetor = ");
		Object[] menoresDistancias = arraylist.toArray();
		escritor.escreva(Arrays.toString(menoresDistancias));
		escritor.escreva(";");
		escritor.fechar();
	}
	
	static void imprimirMatriz(double[][] matriz){
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				System.out.print(matriz[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
	static void imprimirMelhorCaminho(Integer[] caminho){
		for (int i = 0; i < caminho.length; i++) {
			System.out.print(caminho[i] + " ");
		}
	}
}
