package mecanica;

import util.ArquivoUtil;
import util.BancoDeDados;

public class Problema {
	
	// Constantes -------------------------------------------------------------------
	
	public static final double MAX_IT = 5000;			// numero máximo de iterações
	public static final double QTD_EXECUCOES = 30;		// numero de execuções
	
	
	// Variáveis do problema --------------------------------------------------------
	
	public int indiceInicial = 0;			// indice inicial da cidade
	public double alpha = 1.0d; 			// influencia do feromonio
	public double beta = 1.0d; 				// influencia da visibilidade 
	public double P = 0.3;					// taxa de evaporação do feromonio
	public double Q = 100;					// qtd de feromonio depositada
	
	
	// Nome do problema -------------------------------------------------------------
	
	public BancoDeDados nomeProblema = BancoDeDados.ATT48;
	public int numCidades = 48;
	
	// Métodos ----------------------------------------------------------------------
	
	public double[][] carregarArquivo(){
		return ArquivoUtil.carregarArquivoNaMatriz(nomeProblema);
	}

}
