package factories;

import java.util.ArrayList;
import java.util.List;

import ast.AST;
import ast.ASTProgram;
import ast.expressions.*;
import ast.expressions.operations.ASTDiv;
import ast.expressions.operations.ASTMult;
import ast.expressions.operations.ASTOpBinaire;
import ast.expressions.operations.ASTSous;
import ast.expressions.operations.ASTSum;
import ast.statement.*;
import ast.statement.memory.ASTMalloc;
import enums.VarType;
import exceptions.CodeSupposedUnreachableException;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import interfaces.IAST;
import structures.ReturnEnrichissement;

public abstract class Enrichissement {
	public static int nbEnrVar = 2;
	public static int nbEnrConst = 2;
	public static ASTProgram p ;
	public static StringBuffer sn = new StringBuffer();
	public static void init() {
	}
	/***
	 * Permet de mettre en mémoire un programme qui sera enrichi
	 * @param prog
	 */
	public static void setProg(ASTProgram prog) {
		p=prog;
	}

	
	/***
	 * Va appeler enrichir nb fois
	 * @param nb
	 * @throws EnrichissementNotImplementedException
	 * @throws EnrichissementMissingException
	 */
	public static void enrichirV2(int nb) throws EnrichissementNotImplementedException, EnrichissementMissingException {
		for(int i=0;i<nb;i++) {
			enrichirV2();
		}
	}
	/***
	 * Enrichissement principal. 
	 *  Prend un AST, l'enrichit, rajoute dans la liste les AST à rajouter, et gère les def/use de tous les AST
	 * @throws EnrichissementNotImplementedException
	 * @throws EnrichissementMissingException
	 */
	public static void enrichirV2() throws EnrichissementNotImplementedException, EnrichissementMissingException {
		int enrich=Integer.MAX_VALUE;
		IAST choisi=null;
		ArrayList<IAST> expl= p.getexplist();
		for (IAST a : expl) {
			//On récupère l'expression qui a le moins d'enrichissement possible pour l'enrichir
			//Permet d'avoir quelque chose d'uniforme, paramètre modifiable
			int e=a.getEnrichissements();
			//System.out.println(e);
			if ((e<=enrich)&& (e!=0)) {
				enrich=e;
				choisi=a;
			}
		}
		//System.out.println(choisi.getEnrichissements());
		ReturnEnrichissement re = enrichirV2(choisi);
		/*System.out.println(re.toString());
		System.out.println(re.getIAST().getClass());
		System.out.println("ICI");*/
		int index = p.getPosition((AST)choisi);
		
		//On récupère les def/use du sous arbre d'après, seulement s'il existe
		ArrayList<String> vardecpost= new ArrayList<>();
		ArrayList<String> varusepost = new ArrayList<>();
		if (expl.size()>index+1) {
			vardecpost = expl.get(index+1).getDeclaree();
			varusepost = expl.get(index+1).getUsable();
		}
		
		expl.remove(index);
		//index--;
		//Pour chaque AST du ReturnEnrichissement, on l'ajoute a la liste en mettant a jour ses def/use
		for (IAST i : re.getPreList()) {
			for (String s : vardecpost) {
				i.addDeclaree(s);
			}
			for (String s : varusepost) {
				i.addUsable(s);
			}
			expl.add(index, i);
			index++;
		}
		//index++;
		for (String s : vardecpost) {
			re.getIAST().addDeclaree(s);
		}
		for (String s : varusepost) {
			re.getIAST().addUsable(s);
		}
		expl.add(index, re.getIAST());
		index++;
		
		for (IAST i : re.getPostList()) {
			for (String s : vardecpost) {
				i.addDeclaree(s);
			}
			for (String s : varusepost) {
				i.addUsable(s);
			}
			expl.add(index, i);
			index++;
		}
		ArrayList<String> vardec = re.getVardec();
		for (int a = index; a<expl.size();a++) {
			for (String s : vardec) {
				expl.get(a).addDeclaree(s);
				expl.get(a).addUsable(s);
			}
		}
		/*System.out.println("FIN DE BOUCLE");
		System.out.println(expl);
		System.out.println(p.getexplist());*/
		
	}
	/***
	 * Methode d'enrichissement d'un IAST, qui va déléger l'enrichissement a la méthode prenant en charge la classe réelle de l'AST
	 * @param a
	 * @return
	 * @throws EnrichissementNotImplementedException
	 * @throws EnrichissementMissingException
	 */
	public static ReturnEnrichissement enrichirV2(IAST a)
			throws EnrichissementNotImplementedException, EnrichissementMissingException {
		/*System.out.println(a.getClass());
		System.out.println(p.getexplist());*/
		if (a instanceof ASTAffect) {
			return enrichirV2((ASTAffect) a);
		}
		if (a instanceof ASTMalloc) {
			return enrichirV2((ASTMalloc) a);
		}
		if (a instanceof ASTConstante) {
			return enrichirV2((ASTConstante) a);
		}
		if (a instanceof ASTOpBinaire) {
			return enrichirV2((ASTOpBinaire) a);
		}
		throw new EnrichissementNotImplementedException(
				"L'enrichissement n'est pas implémenté pour la classe : " + a.getClass());

	}
	/*
	public static ReturnEnrichissement enrichirV2(ASTVariable a) throws EnrichissementMissingException {
		pop(a);
		switch (a.getType()) {
		case INT:
			// On enrichit aléatoirement entre une opération aléatoire et une affectation
			switch (RandomProvider.nextInt(nbEnrVar)) {
			case 0:
				IAST o = a.getOwner();
				o.enrichir(a, ASTOpBinaire.getRandomOperation(a.getValeur(), o));
				pop(a);
				break;
			case 1:
				IAST l = a.getOwner();
				l.enrichir(a, new ASTAffect(a.getType(), a.getNom(), a.getValeur(), l));
				pop(a);
				break;
			}
		}
	}*/
	/***
	 * Enrichissement de l'affectation, qui enrichit le membre droit
	 * @param a
	 * @return
	 * @throws EnrichissementNotImplementedException
	 * @throws EnrichissementMissingException
	 */
	public static ReturnEnrichissement enrichirV2(ASTAffect a) throws EnrichissementNotImplementedException, EnrichissementMissingException {
		ReturnEnrichissement re = enrichirV2(a.getAffectation());
		a.setAffectation((AST)re.getIAST());
		a.setEnrichissements(re.getIAST().getEnrichissements());
		return new ReturnEnrichissement(re.getPreList(),a,re.getPostList());

	}
	/***
	 * Enrichissement du malloc, qui enrichit la valeur numérique pour la taille (pour le moment)
	 * @param a
	 * @return
	 * @throws EnrichissementMissingException
	 * @throws EnrichissementNotImplementedException
	 */
	public static ReturnEnrichissement enrichirV2(ASTMalloc a) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		IAST num = ((IAST) a.getNum());
		ReturnEnrichissement re = enrichirV2(num);
		a.setNum((ASTExpr)re.getIAST());
		a.setEnrichissements(re.getIAST().getEnrichissements());
		return new ReturnEnrichissement(re.getPreList(),a,re.getPostList());
	}
	
	/***
	 * Enrichissement de la constante, en opération ou en variable (en ajoutant déclaration et affectation)
	 * @param a
	 * @return
	 * @throws EnrichissementMissingException
	 */
	public static ReturnEnrichissement enrichirV2(ASTConstante a) throws EnrichissementMissingException{
		switch (RandomProvider.nextInt(nbEnrConst)) {
		//switch (0) {
		case 0:
			//cas d'une constante qui devient une opération
			return new ReturnEnrichissement(ASTOpBinaire.getRandomOperation(a.getValeur()));
		case 1:
			//Cas d'une constante qui devient une variable, donc besoin affectation et déclaration avant
			ArrayList<IAST> array = new ArrayList<>();

			array.add(new ASTDeclaration(a.getType(),a.getNom()));
			array.add(new ASTAffect(a.getType(), a.getNom(), new ASTConstante(a.getType(),Lexenv.getNewName(),a.getValeur())));
			return new ReturnEnrichissement(array, 	new ASTVariable(a.getType(),a.getNom(),a.getValeur()));
		}
		throw new EnrichissementMissingException(" Enrichissement de la constante ");
	}
	/***
	 * Enrichissement d'une opération, en enrichissant le memebre gauche, ou droit 
	 * @param a
	 * @return
	 * @throws EnrichissementMissingException
	 * @throws EnrichissementNotImplementedException
	 */
	public static ReturnEnrichissement enrichirV2(ASTOpBinaire a) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		int rand = RandomProvider.nextInt(2);
		//System.out.println(""+a.getEnrichissements()+a.getGauche().getEnrichissements()+a.getDroite().getEnrichissements());
		if (a.getGauche().getEnrichissements()==0)
			rand=1;
		if (a.getDroite().getEnrichissements()==0)
			rand=0;
		switch (rand) {
		case 0 : 
			ReturnEnrichissement re = enrichirV2(a.getGauche());
			a.setGauche((AST)re.getIAST());
			a.setEnrichissements(re.getIAST().getEnrichissements());
			return new ReturnEnrichissement(re.getPreList(),a,re.getPostList());
		case 1 : 
			ReturnEnrichissement re2 = enrichirV2(a.getDroite());
			a.setDroite((AST)re2.getIAST());
			a.setEnrichissements(re2.getIAST().getEnrichissements());
			return new ReturnEnrichissement(re2.getPreList(),a,re2.getPostList());
		}
		throw new EnrichissementMissingException("Operation");
	}
}
