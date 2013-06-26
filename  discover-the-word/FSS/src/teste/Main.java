package teste;

import cardume.AbsProblema;
import cardume.Cardume;

public class Main {
	public static void main(String[] args) {
		Cardume cardume = new Cardume(new AbsProblema());
		cardume.comecarPeixarada();
	}
}
