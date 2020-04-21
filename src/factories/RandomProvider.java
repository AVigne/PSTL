package factories;

import java.util.Random;

public class RandomProvider {
	public static final int nbRandom = 150;
	private static boolean isInit = false;
	private static Random theAlmighty;

	// if using the seed got in another iteration, note that the format of a long is
	// [digit]*L (ex : 42L)
	private static  long seed = 1587472808494L;//System.currentTimeMillis();

	public static void init() {
		if (!isInit) {
			theAlmighty = new Random();
			isInit = true;
			theAlmighty.setSeed(seed);
		}
	}

	public static long getSeed() {
		return seed;
	}

	/**
	 * @see java.util.Random#nextInt()
	 */
	public static int nextInt() {
		return theAlmighty.nextInt();
	}

	/**
	 * @see java.util.Random#nextInt(int)
	 */
	public static int nextInt(int i) {
		return theAlmighty.nextInt(i);
	}
	/***
	 * Retourne l'entier entre i et MAX_INT, pour limiter les overflow
	 * @param i
	 * @return
	 */
	public static int getInt(int i) {
		int m = Integer.MAX_VALUE;
		return (i / 2) + (m / 2) + (((i % 2) + (m % 2)) / 2);
	}

}
