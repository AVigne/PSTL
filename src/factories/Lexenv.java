package factories;

import java.util.ArrayList;

//import interfaces.ILexenv;

public class Lexenv{
	private static ArrayList<String> vars;
	public static void init() {
		vars= new ArrayList<String>();
	}
	
	public static String getNewName() {
		if (vars.isEmpty()) {
			vars.add("a");
			return "a";
			
		}
		return generateNew();
	}
	
	private static String generateNew() {
		//alphabet
		String alpha = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder();
		//génération de nom aleatoire, on ne se soucie pas de la taile du nom, seulement si il existe deja
		do {
			sb.append(alpha.charAt(RandomProvider.nextInt(26)));
		}while (vars.contains(sb.toString()));
		vars.add(sb.toString());
		return sb.toString();
	}
	
	public static ArrayList<String> getVars() {
		return vars;
	}
}
