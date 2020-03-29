package interfaces;

import java.util.ArrayList;

import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import structures.ReturnEnrichissement;

public interface IAST {
	

	public void setEnrichissements(int i);

	public int getEnrichissements();
	/**
	 * Called by the visitor to build the input string of the C file
	 * 
	 * @param sb 
	 */
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException;
	/***
	 * Recupère le nom de l'AST
	 * @return String nom
	 */
	public String getNom();
	/***
	 * Permet d'ajouter une variable déjà déclarée
	 * @param n (nom de la variable)
	 */
	public void addDeclaree(String n);
	/***
	 * Recupère les variables déjà déclarées
	 * @return ArrayList des varialbes déclarées
	 */
	public ArrayList<String> getDeclaree();
	/***
	 * Permet de rajouter une variable utilisable
	 * @param n (nom de la variable)
	 */
	public void addUsable(String n);
	/***
	 * Récupère les variables utilisables
	 * @return ArrayList des variables utilisables
	 */
	public ArrayList<String> getUsable();
	/***
	 * Permet de supprimer une varible, la rendant non utilisable
	 * @param n (nom de la variable a supprimer)
	 */
	public void popUsable(String n) ;
	/***
	 * Ajoute une liste de variables déclarées
	 * @param l
	 */
	public void fuseDeclaree(ArrayList<String> l);
	/***
	 * Ajoute une liste de variables utilisables
	 * @param l
	 */
	public void fuseUsable(ArrayList<String> l);
}
