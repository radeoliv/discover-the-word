package br.bhmy.main;

import br.bhmy.challenges.Challenge1;
import br.bhmy.challenges.IChallenge;
import br.bhmy.constants.Constants;
import br.bhmy.mecanics.pso.PSO;

public class Main {
	public static void main(String[] args) {
		Main.startChallenge(new Challenge1());
	}
	
	public static void startChallenge(IChallenge challenge){
		PSO pso = new PSO(challenge, Constants.FOCAL, true);
		pso.init();
		pso.start();
	}
}
