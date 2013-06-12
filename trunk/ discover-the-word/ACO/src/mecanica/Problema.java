package mecanica;

import util.ArquivoUtil;
import util.BancoDeDados;

public class Problema {
	
	// Constantes -------------------------------------------------------------------
	
	public static final double MAX_IT = 5000;			// numero m�ximo de itera��es
	public static final double QTD_EXECUCOES = 30;		// numero de execu��es
	
	
	// Vari�veis do problema --------------------------------------------------------
	
	public int indiceInicial = 0;			// indice inicial da cidade
	public double alpha = 1.0d; 			// influencia do feromonio
	public double beta = 1.0d; 				// influencia da visibilidade 
	public double P = 0.3;					// taxa de evapora��o do feromonio
	public double Q = 100;					// qtd de feromonio depositada
	
	
	// Nome do problema -------------------------------------------------------------
	
	public BancoDeDados nomeProblema = BancoDeDados.ATT48;
	public int numCidades = 48;
	
	// M�todos ----------------------------------------------------------------------
	
	public double[][] carregarArquivo(){
		return ArquivoUtil.carregarArquivoNaMatriz(nomeProblema);
	}

}
