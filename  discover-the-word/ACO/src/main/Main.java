package main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import mecanica.ACO;
import mecanica.Problema;
import textoutil.Escritor;
import util.ArquivoUtil;
import util.BancoDeDados;
import util.Util;


public class Main {
	
	static Escritor escritor = new Escritor();
	
	public static void main(String[] args) throws IOException {
		runProb3();
	}
	
	static void runProb3() throws IOException{
		Problema problema = new Problema();
		double[][] matriz = problema.carregarArquivo();
		ACO aco = new ACO(problema, matriz, 51);
		aco.iniciarColonia();
		escreverArqTexto(aco, "Problem3");
	}
	
	static void escreverArqTexto(ACO aco, String nameProblem) throws IOException{
		
		// melhores caminhos
		escritor.setPath(nameProblem + "_EvolucaoDasMelhoresRotas.txt");
		escritor.iniciarEscritor();
		ArrayList<Integer[]> melhoresRotas = aco.melhoresCaminhos;
		int tam = melhoresRotas.size();
		for (int i = 0; i < tam; i++) {
			Integer[] melhorRotaDaVez = melhoresRotas.get(i);
			escritor.escreva(Arrays.toString(melhorRotaDaVez));
			escritor.pularLinha();
		}
		escritor.fechar();
		
		escritor.setPath(nameProblem + "_EvolucaoDosComprimento.txt");
		escritor.iniciarEscritor();
		escritor.escreva("vetor = ");
		Object[] menoresDistancias = aco.menoresDistancias.toArray();
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
