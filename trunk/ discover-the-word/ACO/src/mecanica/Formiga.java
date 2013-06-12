package mecanica;

import java.util.ArrayList;

public class Formiga {
	
	public int posicaoInicial = 0;
	
	public int posicaoAtual;
	
	public ArrayList<Integer> listaVisitados;
	public ArrayList<Integer> listaNaoVisitados;
	
	public boolean voltando = false;
	
	public Formiga(){
		// DO NOTHING
		listaVisitados = new ArrayList<Integer>();
		listaNaoVisitados = new ArrayList<Integer>();
	}
	
	public void visitou(Integer indiceDaCidade){
		listaNaoVisitados.remove(indiceDaCidade);
		listaVisitados.add(indiceDaCidade);
		posicaoAtual = indiceDaCidade;
	}
	
	public void reset(int numCidades){
		
		// limpa as listas
		listaVisitados.clear();
		listaNaoVisitados.clear();
		
		listaVisitados.add(posicaoInicial);
		
		// re-popula nao visitados
		for (int i = 0; i < numCidades; i++) {
			if(i == posicaoInicial){
				continue;
			} else {
				listaNaoVisitados.add(i);
			}
		}
		
		// retorna ao estado inicial
		posicaoAtual = posicaoInicial;
		voltando = false;
	}

}
