package teste;

import java.io.IOException;
import java.util.Scanner;

import problem.functions.AFunction;
import problem.functions.GriewankFunction;
import problem.functions.RastriginFunction;
import problem.functions.RosenbrockFunction;
import problem.functions.SphereFunction;
import textoutil.Escritor;
import fish.Cardume;

public class Main {
	
	public static final int NUM_SIMULACOES = 30;
	static Cardume[] simulacoes = new Cardume[NUM_SIMULACOES];
	static Escritor logger = new Escritor();
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("1 -> Griewank");
		System.out.println("2 -> Rastrigin");
		System.out.println("3 -> Rosenbrock");
		System.out.println("4 -> Sphere");
		
		Scanner in = new Scanner(System.in);
		System.out.print("Digite um número: ");
		int escolha = in.nextInt();
		in.close();
		
		for (int i = 0; i < NUM_SIMULACOES; i++) {
			Cardume cardume = new Cardume(getFunction(escolha));
			cardume.comecarPeixarada();
			simulacoes[i] = cardume;
			System.out.println("Simulação ["+ (i+1) + "] ---> terminou!");
		}
		
		int numIteracoes = simulacoes[0].problema.numIteracoes;
		
		double[] medias = new double[numIteracoes];
		for (int j = 0; j < numIteracoes; j++) {
			double media = 0.0;
			for (int i = 0; i < NUM_SIMULACOES; i++) {
				media += simulacoes[i].melhoresFitness.get(j);
			}
			medias[j] = media/(double)NUM_SIMULACOES;
		}
		
		String functionName = simulacoes[0].problema.getFunctionName();
		logger.atualizeCaminhoDoArquivo(functionName + "_FSS.txt");
		logger.inicie(false);
		
		logger.escreva("fss_" + functionName + " = [");
		int tam = medias.length;
		for (int i = 0; i < tam; i++) {
			if(i != tam - 1 ){
				logger.escreva(medias[i] +", ");
			} else  {
				logger.escreva(medias[i] +"];");
			}
		}
		logger.fechar();
		
		System.out.println("[ "+functionName + "_FSS.txt ] ----> Arquivo criado com sucesso!");
		
	}

	private static AFunction getFunction(int escolha) {
		AFunction aFunction = null;
		switch (escolha) {
		case 1:
			aFunction = new GriewankFunction();
			break;
		case 2:
			aFunction = new RastriginFunction();
			break;
		case 3:
			aFunction = new RosenbrockFunction();
			break;
		case 4:
			aFunction = new SphereFunction();
			break;
		default:
			System.out.println("Valor desconhecido!");
			break;
		}
		return aFunction;
	}
	
	
}
