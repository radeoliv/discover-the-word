package prisioneiro;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
	
	private List<Wrapper> outrosPrisioneiros;
	
	public Memoria(Prisioneiro prisioneiro, List<Prisioneiro> listaDePrisioneirosList){
		iniciarMemoria(prisioneiro, listaDePrisioneirosList);
	}

	private void iniciarMemoria(Prisioneiro prisioneiro, List<Prisioneiro> listaDePrisioneirosList) {
		outrosPrisioneiros = new ArrayList<Wrapper>();
		for (Prisioneiro p : listaDePrisioneirosList) {
			if(prisioneiro.getId() != p.getId()){
				outrosPrisioneiros.add(new Wrapper(p.getId(), 0)); // inicializando scores
			}
		}
	}
	
	public void atualizaPts(int prisioneiroID, int qtdDePontos){
		for (Wrapper prisioneiro : outrosPrisioneiros) {
			if(prisioneiro.getPrisioneiroID() == prisioneiroID){
				prisioneiro.atualizaScore(qtdDePontos);
				break;
			}
		}
	}

	private class Wrapper{
		
		private int prisioneiroID;
		private int score;
		
		/**
		 *  Objeto que guarda os ids e scores de outros prisioneiros em relação
		 *  ao prisioneiro que está sendo analisado
		 */
		public Wrapper(int prisioneiroID, int score) {
			this.prisioneiroID = prisioneiroID;
			this.score = score;
		}
		
		public int getPrisioneiroID() {
			return prisioneiroID;
		}
		
		public int getScore() {		// será utilizado talvez para o calculo da traição
			return score;
		}
		
		public void atualizaScore(int valor){
			score += valor;
		}
		
	}
}
