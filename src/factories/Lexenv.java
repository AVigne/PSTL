package factories;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import ast.ASTExpression;

//import interfaces.ILexenv;

public class Lexenv{
	private static List<String> vars;
	//Pour plus tard, stocker les expressions et leur mutabilité -> creer du bruit dans les expressions
	private static Map<ASTExpression,Boolean> explist;
	private static Boolean error;
	private static List<String> languageKeywords;
	
	public static void init() {
		vars= new ArrayList<String>();
		explist = new HashMap<ASTExpression,Boolean>();
		// langage C
		languageKeywords = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader("Ckeywords.txt"))) {		
        	String line;
        	while((line = br.readLine()) != null) {
        		languageKeywords.add(line);
        	}
        
        }
        catch(IOException e) {
        	System.out.println("erreur lecture fichier keywords");
        	System.exit(1);
        }
    }
	
	public static String getNewName() {
		//alphabet
		String alpha = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder();
		if (error) {
			sb.append("error_");
		}
		//génération de nom aleatoire, on ne se soucie pas de la taile du nom,
		//Seulement s'il n'existe pas déjà et n'est pas un mot du language 
		do {
			sb.append(alpha.charAt(RandomProvider.nextInt(26)));
		}while (vars.contains(sb.toString()) || languageKeywords.contains(sb.toString()));
		vars.add(sb.toString());
		return sb.toString();
	}
	
	public static List<String> getVars() {
		return vars;
	}
	//Permet de nommer les variables en error_
	public static void toggleError(Boolean b) {
		error=b;
	}
}
