package factories;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


//import interfaces.ILexenv;

public abstract class Lexenv {
	private static List<String> vars;
	
	private static Boolean error=false;
	private static List<String> languageKeywords;
	//Liste des opérateurs booléens
	public static List<String> compop= new ArrayList<>();
	/***
	 * Initialisation de l'environnement lexical avec notamment les mots du langage C
	 */
	public static void init() {
		vars = new ArrayList<String>();

		for(String s : new String[] {"==","!=",">","<",">=","<="}) compop.add(s);

		// langage C
		languageKeywords = new ArrayList<String>();
		try (
				//Pour le .jar, besoin de stream pour ouvrir le fichier
			InputStream inputStream = Lexenv.class.getClassLoader().getResourceAsStream("Ckeywords.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			//BufferedReader br = new BufferedReader(new FileReader("Ckeywords.txt"))){
			String line;
			while ((line = br.readLine()) != null) {
				languageKeywords.add(line);
			}

		} catch (IOException e) {
			System.out.println(e.toString());
			System.out.println("erreur lecture fichier keywords");
			System.exit(1);
		}
	}
	/***
	 * Renvoit un nouveau nom, pas encore utilisé
	 * @return String nom
	 */
	public static String getNewName() {
		// alphabet
		String alpha = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder();
		if (error) {
			sb.append("error_");
		}
		// génération de nom aleatoire, on ne se soucie pas de la taile du nom,
		// Seulement s'il n'existe pas déjà et n'est pas un mot du language
		do {
			sb.append(alpha.charAt(RandomProvider.nextInt(26)));
		} while (vars.contains(sb.toString()) || languageKeywords.contains(sb.toString()));
		vars.add(sb.toString());
		return sb.toString();
	}
	/***
	 * Renvoie la liste des noms des variables
	 * @return List des noms
	 */
	public static List<String> getVars() {
		return vars;
	}
	
	/***
	 * _Si True, les appels a getNewName renverront des noms sous la forme error_XXXX
	 * @param b
	 */
	public static void toggleError(Boolean b) {
		error = b;
	}
}
