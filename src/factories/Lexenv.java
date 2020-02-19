package factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ast.ASTExpression;

//import interfaces.ILexenv;

public class Lexenv{
	private static ArrayList<String> vars;
	//Pour plus tard, stocker les expressions et leur mutabilité -> creer du bruit dans les expressions
	private static Map<ASTExpression,Boolean> explist;
	private static Boolean error;
	
	public static void init() {
		vars= new ArrayList<String>();
		explist = new HashMap<ASTExpression,Boolean>();
	}
	
	public static String getNewName() {
		//alphabet
		String alpha = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder();
		if (error) {
			sb.append("error_");
		}
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
	
	public static void toggleError(Boolean b) {
		error=b;
	}
}
