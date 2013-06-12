package main;
import mecanica.ACO;
import mecanica.Problema;
import util.ArquivoUtil;
import util.BancoDeDados;
import util.Util;


public class Main {
	
	public static void main(String[] args) {
		
	}
	
	static void runProb3(){
		Problema problema = new Problema();
		double[][] matriz = problema.carregarArquivo();
		ACO aco = new ACO(problema, matriz, 48);
		aco.iniciarColonia();
	}

	static void runProb31(){
		Problema problema = new Problema();
		double[][] matriz = problema.carregarArquivo();
		ACO aco = new ACO(problema, matriz, 5);
		aco.iniciarColonia();
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
