package factories;

import java.util.Random;

public class RandomProvider {

	private static boolean isInit = false;
	private static Random theAlmighty;
	
	// if using the seed got in another iteration, note that the format of a long is [digit]*L  (ex : 42L)
	private static final long seed = System.currentTimeMillis();
	
	public static void init() {
		if(!isInit) {
			theAlmighty = new Random();
			isInit = true;
			theAlmighty.setSeed(seed);
		}
	}
	
	public static long getSeed() {
		return seed;
	}
	//Entier random
	public static int nextInt() {
		return theAlmighty.nextInt();
	}
	//Entier random avec borne i
	public static int nextInt(int i) {
		return theAlmighty.nextInt(i);
	}
	
	
}
