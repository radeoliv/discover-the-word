package br.bhmy.main;

import br.bhmy.challenges.IChallenge;
import br.bhmy.challenges.functions.XAoQuadrado;
import br.bhmy.constants.Constants;
import br.bhmy.mecanics.pso.PSO;

public class Main {
	
	static int numberOfIterations = 30;
	
	public static void main(String[] args) {
		StartPSO.startChallenge(new XAoQuadrado());
	}
	
}

class StartPSO{
	
	public static void startChallenge(IChallenge challenge){
		PSO pso = new PSO(challenge, Constants.GLOBAL, true);
		pso.init();
		pso.start();
	}

}
