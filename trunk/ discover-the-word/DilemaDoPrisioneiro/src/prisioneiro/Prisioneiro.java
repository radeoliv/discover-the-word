package prisioneiro;

import java.util.List;

import util.AcaoDoPrisioneiro;
import util.Util;

public class Prisioneiro {
	
	private Memoria memoriaDosEncontros;
	private int id;

	private int scoreGeral;
	
	double indiceDeTraicao; 
	
	public Prisioneiro(List<Prisioneiro> listaDePrisioneiros){
		memoriaDosEncontros = new Memoria(this, listaDePrisioneiros);
		indiceDeTraicao = Util.generateRandom(); // TODO definir como será feito o calculo dessa probabilidade
	}
	
	// TODO alterar a lógica para definir a ação do prisioneiro
	public void iteragirPrisioneiro(Prisioneiro prisioneiro){
		
	}
	
	/**
	 *  Atualiza Scores gerais e Scores parciais (prisioneiro <-> prisioneiro)
	 * @param prisioneiro Prisioneiro que irá interagir com este prisioneiro 
	 */
	public void interagirComOutroPrisioneiro(Prisioneiro prisioneiro) {
		
		AcaoDoPrisioneiro acaoDestePrisioneiro = this.getAcao();
		AcaoDoPrisioneiro acaoDoOutroPrisioneiro = prisioneiro.getAcao();
		
		if(acaoDestePrisioneiro == AcaoDoPrisioneiro.COOPEROU){

			if(acaoDoOutroPrisioneiro == AcaoDoPrisioneiro.COOPEROU){	// cooperou - cooperou
				
				this.scoreGeral += AcaoDoPrisioneiro.COOPEROU.getMenorScore();
				prisioneiro.aumentarOuDiminuirScoreGeral(AcaoDoPrisioneiro.COOPEROU.getMenorScore());
				
				memoriaDosEncontros.atualizaPts(prisioneiro.getId(), AcaoDoPrisioneiro.COOPEROU.getMenorScore());
				prisioneiro.getMemoriaDosEncontros().atualizaPts(this.id, AcaoDoPrisioneiro.COOPEROU.getMenorScore());
		
			} else { 													// cooperou - denunciou
				
				this.scoreGeral += AcaoDoPrisioneiro.DENUNCIOU.getMaiorScore();
				prisioneiro.aumentarOuDiminuirScoreGeral(AcaoDoPrisioneiro.COOPEROU.getMaiorScore());
			
				memoriaDosEncontros.atualizaPts(prisioneiro.getId(), AcaoDoPrisioneiro.DENUNCIOU.getMaiorScore());
				prisioneiro.getMemoriaDosEncontros().atualizaPts(this.id, AcaoDoPrisioneiro.COOPEROU.getMaiorScore());

			}
		
		} else {
			
			if(acaoDoOutroPrisioneiro == AcaoDoPrisioneiro.COOPEROU){	// denunciou - cooperou
				
				this.scoreGeral += AcaoDoPrisioneiro.COOPEROU.getMaiorScore();
				prisioneiro.aumentarOuDiminuirScoreGeral(AcaoDoPrisioneiro.DENUNCIOU.getMaiorScore());

				memoriaDosEncontros.atualizaPts(prisioneiro.getId(), AcaoDoPrisioneiro.COOPEROU.getMaiorScore());
				prisioneiro.getMemoriaDosEncontros().atualizaPts(this.id, AcaoDoPrisioneiro.DENUNCIOU.getMaiorScore());
				
			} else {													// denunciou - denunciou
				
				this.scoreGeral += AcaoDoPrisioneiro.DENUNCIOU.getMenorScore();
				prisioneiro.aumentarOuDiminuirScoreGeral(AcaoDoPrisioneiro.DENUNCIOU.getMenorScore());

				memoriaDosEncontros.atualizaPts(prisioneiro.getId(), AcaoDoPrisioneiro.DENUNCIOU.getMenorScore());
				prisioneiro.getMemoriaDosEncontros().atualizaPts(this.id, AcaoDoPrisioneiro.DENUNCIOU.getMenorScore());
			}
			
		}
	}
	
	// TODO Definir lógica!!!
	public AcaoDoPrisioneiro getAcao(){
		return null;
	}

	public Memoria getMemoriaDosEncontros() {
		return memoriaDosEncontros;
	}


	public void setMemoriaDosEncontros(Memoria memoriaDosEncontros) {
		this.memoriaDosEncontros = memoriaDosEncontros;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getScoreGeral() {
		return scoreGeral;
	}

	public void aumentarOuDiminuirScoreGeral(int scoreGeral) {
		this.scoreGeral += scoreGeral;
	}
	
}
