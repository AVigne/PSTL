package structures;

import java.util.ArrayList;

import ast.statement.ASTDeclaration;
import interfaces.IAST;

public class ReturnEnrichissement {
	public ArrayList<IAST> preList = new ArrayList<>();
	public IAST a = null;
	public ArrayList<IAST> postList = new ArrayList<>();

	/***
	 * Cas où l'enrichissement n'a pas d'instructions avant ou après
	 * 
	 * @param ast
	 */
	public ReturnEnrichissement(IAST ast) {
		a = ast;
		if (a instanceof ASTDeclaration)
			a.addDeclaree(a.getNom());
	}

	/***
	 * Cas ou l'enrichissement renvoie des instructions a placer avant
	 * 
	 * @param pre
	 * @param ast
	 */
	public ReturnEnrichissement(ArrayList<IAST> pre, IAST ast) {
		a = ast;
		preList = pre;
		ArrayList<String> dec = new ArrayList<>();
		for (IAST i : preList) {
			if (i instanceof ASTDeclaration)
				dec.add(i.getNom());
			for (String s : dec) {
				i.addDeclaree(s);
			}
		}
		if (a instanceof ASTDeclaration)
			dec.add(a.getNom());
		for (String s : dec) {
			a.addDeclaree(s);
		}

	}

	/***
	 * Cas ou l'enrichissement renvoie des instructions a placer après
	 * 
	 * @param pre
	 * @param ast
	 */
	public ReturnEnrichissement(IAST ast, ArrayList<IAST> post) {
		a = ast;
		postList = post;
		ArrayList<String> dec = new ArrayList<>();
		if (a instanceof ASTDeclaration)
			dec.add(a.getNom());
		for (String s : dec) {
			a.addDeclaree(s);
		}
		for (IAST j : postList) {
			if (j instanceof ASTDeclaration)
				dec.add(j.getNom());
			for (String s : dec) {
				j.addDeclaree(s);
			}
		}
	}

	/***
	 * Cas où l'enrichissement donne des instructions a placer avant et après
	 * 
	 * @param pre
	 * @param ast
	 * @param post
	 */
	public ReturnEnrichissement(ArrayList<IAST> pre, IAST ast, ArrayList<IAST> post) {
		a = ast;
		preList = pre;
		postList = post;
		a = ast;
		preList = pre;
		ArrayList<String> dec = new ArrayList<>();
		for (IAST i : preList) {
			if (i instanceof ASTDeclaration)
				dec.add(i.getNom());
			for (String s : dec) {
				i.addDeclaree(s);
			}
		}
		if (a instanceof ASTDeclaration)
			dec.add(a.getNom());
		for (String s : dec) {
			a.addDeclaree(s);
		}
		for (IAST j : postList) {
			if (j instanceof ASTDeclaration)
				dec.add(j.getNom());
			for (String s : dec) {
				j.addDeclaree(s);
			}
		}
	}

	/***
	 * Renvoie la liste des instructions a rajouter avant
	 * 
	 * @return ArrayList des IAST a rajouter avant
	 */
	public ArrayList<IAST> getPreList() {
		return preList;
	}

	/***
	 * Renvoie l'IAST enrichi
	 * 
	 * @return IAST
	 */
	public IAST getIAST() {
		return a;
	}

	/***
	 * Renvoie la liste des instructions a rajouter après
	 * 
	 * @return ArrayList des IAST a rajouter après
	 */
	public ArrayList<IAST> getPostList() {
		return postList;
	}

	/***
	 * Pour le débugage, renvoie la taille, la classe et la taille
	 */
	public String toString() {
		return ("" + preList.size() + a.getClass() + postList.size());
	}

	/***
	 * Renvoie la liste des variables déclarées dans la liste des AST a rajouter
	 * avant
	 * 
	 * @return ArrayList String des noms des variables
	 */
	public ArrayList<String> getVardecPre() {
		ArrayList<String> vardec = new ArrayList<>();
		for (IAST i : preList) {
			if (i instanceof ASTDeclaration) {
				vardec.add(i.getNom());
			}
		}
		return vardec;
	}

	/***
	 * Renvoie la liste des variables déclarées dans la liste des AST a rajouter
	 * après
	 * 
	 * @return ArrayList String des noms des variables
	 */
	public ArrayList<String> getVardecPost() {
		ArrayList<String> vardec = new ArrayList<>();
		for (IAST j : postList) {
			if (j instanceof ASTDeclaration) {
				vardec.add(j.getNom());
			}
		}
		return vardec;
	}

	/***
	 * Renvoie la liste des variables déclarées par l'enrichissement global
	 * 
	 * @return
	 */
	public ArrayList<String> getVardec() {
		ArrayList<String> vardec = getVardecPre();
		if (a instanceof ASTDeclaration)
			vardec.add(a.getNom());
		vardec.addAll(getVardecPost());
		return vardec;
	}

	/***
	 * Renvoie le dernier AST, permet d'avoir les defUse du dernier element, et de
	 * les rajouter a tous
	 * 
	 * @return
	 */
	public IAST getLast() {
		if (postList.isEmpty())
			return a;
		else
			return postList.get(postList.size());
	}
}
