package teste;

import problem.functions.SphereProblem;
import fish.Cardume;

public class Main {
	public static void main(String[] args) {
		Cardume cardume = new Cardume(new SphereProblem());
		cardume.comecarPeixarada();
	}
}
