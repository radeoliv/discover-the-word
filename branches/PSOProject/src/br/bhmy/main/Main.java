package br.bhmy.main;

import br.bhmy.challenges.Challenge1;
import br.bhmy.challenges.IChallenge;
import br.bhmy.mecanics.pso.PSO;

public class Main {
	public static void main(String[] args) {
		
		IChallenge challenge = new Challenge1();
		
		PSO pso = new PSO(challenge);
		pso.init();
		pso.start();
		
	}
}
