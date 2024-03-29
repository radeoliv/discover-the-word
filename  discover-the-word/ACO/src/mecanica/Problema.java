package mecanica;

import util.ArquivoUtil;
import util.BancoDeDados;
import util.Constantes;

public class Problema {
	
	// Constantes -------------------------------------------------------------------
	
	public static final double MAX_IT = 5000;		// numero m�ximo de itera��es
	public static final int QTD_EXECUCOES = 30;		// numero de execu��es
	
	
	// Vari�veis do problema --------------------------------------------------------
	
	public int indiceInicial = 0;			// indice inicial da cidade
	public double alpha = 1.0; 			// influencia do feromonio
	public double beta = 1.0; 				// influencia da visibilidade 
	public double P = 0.3;					// taxa de evapora��o do feromonio
	public double Q = 100;					// qtd de feromonio depositada
	
	
	// Nome do problema -------------------------------------------------------------
	
	public int numCidades;
	public String nomeProblema;
	public BancoDeDados enumProblema;
	
	public Problema(BancoDeDados enumProblema) throws Exception{
		
		this.enumProblema = enumProblema;
		
		if (enumProblema == BancoDeDados.EIL51){
			this.numCidades = 51;
			this.nomeProblema = Constantes.NAME_EIL51;
		} else if (enumProblema == BancoDeDados.OLIVER30){
			this.numCidades = 30;
			this.nomeProblema = Constantes.NAME_OLIVER30;
		} else if (enumProblema == BancoDeDados.ATT48){
			this.numCidades = 48;
			this.nomeProblema = Constantes.NAME_ATT48;
		} else if (enumProblema == BancoDeDados.CUBO){
			this.numCidades = 4;
			this.nomeProblema = Constantes.CUBO;
		} else {
			throw new Exception("Banco de Dados n�o reconhecido!!");
		}
	}
	
	// M�todos ----------------------------------------------------------------------
	
	public double[][] carregarArquivo(){
		return ArquivoUtil.carregarArquivoNaMatriz(enumProblema);
	}

}
