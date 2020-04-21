package factories;

import java.util.ArrayList;
import ast.AST;
import ast.ASTProgram;
import ast.expressions.*;
import ast.expressions.operations.ASTOpBinaire;
import ast.expressions.operations.ASTSous;
import ast.expressions.operations.ASTSum;
import ast.statement.*;
import ast.statement.memory.ASTMalloc;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import interfaces.IAST;
import structures.ReturnEnrichissement;

public abstract class Enrichissement {
	public static int nbEnrConst = 3;
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
		ArrayList<IAST> expl= p.getexplist();
		ArrayList<IAST> temp = new ArrayList<>();
		//1/2 d'enrichir un des moins enrichissables, 1/2 d'en enrichir un aléatoirement
		switch (RandomProvider.nextInt(2)) {
		case 0 :
			for (IAST a : expl) {
				//On récupère une liste des moins enrichissables pour en enrichir un
				int e=a.getEnrichissements();
				//System.out.println(e);
				if ((e<enrich)&& (e!=0)) {
					temp= new ArrayList<>();
					temp.add(a);
					enrich=e;
				}
				if (e==enrich) {
					temp.add(a);
				}
			}
			break;
		case 1 :
			for (IAST a : expl) {
				int e=a.getEnrichissements();
				//System.out.println(e);
				if ((e!=0)) {
					temp.add(a);
					enrich=e;
				}
			}
			break;
		}
		
		if (temp.isEmpty()) System.out.println("Pas d'enrichissement possible");
		IAST choisi = temp.get(RandomProvider.nextInt(temp.size()));
		
		//System.out.println(choisi.getEnrichissements());
		ReturnEnrichissement re = enrichirV2(choisi);
		/*System.out.println(re.toString());
		System.out.println(re.getIAST().getClass());
		System.out.println("ICI");*/
		int index = p.getPosition((AST)choisi);
		
		//On récupère les def/use du sous arbre d'après, seulement s'il existe
		ArrayList<String> vardecpost= new ArrayList<>();
		ArrayList<String> varusepost = new ArrayList<>();
		//Récupère les def/use du prochain AST (après ceux a rajouter), pour les mettre a ceux que l'on rajoute
		/*if (expl.size()>index+1) {
			vardecpost = expl.get(index+1).getDeclaree();
			varusepost = expl.get(index+1).getUsable();
		}*/
		//Recupère les def/use du dernier AST rajouté, pour les mettre a tous les AST a rajouter
		//Utile maintenant car pas de postList, donc defUse de l'AST, mais pas sur que ça tienne plus tard
		vardecpost=re.getLast().getDeclaree();
		varusepost=re.getLast().getUsable();
		expl.remove(index);
		//index--;
		//Pour chaque AST du ReturnEnrichissement, on l'ajoute a la liste en mettant a jour ses def/use
		for (IAST i : re.getPreList()) {
			i.fuseDeclaree(vardecpost);
			i.fuseUsable(varusepost);
			expl.add(index, i);
			index++;
		}
		//index++;
		re.getIAST().fuseDeclaree(vardecpost);
		re.getIAST().fuseUsable(varusepost);
		expl.add(index, re.getIAST());
		//index++;
		
		for (IAST i : re.getPostList()) {
			i.fuseDeclaree(vardecpost);
			i.fuseUsable(varusepost);
			expl.add(index, i);
			index++;
		}
		ArrayList<String> vardec = re.getVardec();
		//System.out.println(expl);
		//System.out.println(index);
		//Les variables déclarées dans l'enrenchissement ne sont utilisées que dans les AST rajoutés, elles sont donc libres et initialisées pour après
		for (int a = index; a<expl.size();a++) {
			expl.get(a).fuseDeclaree(vardec);
			expl.get(a).fuseUsable(vardec);
		}
	
		
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
		if (a instanceof ASTRand) {
			return enrichirV2((ASTRand) a);
		}
		if (a instanceof ASTVariable) {
			return enrichirV2((ASTVariable) a);
		}
		if (a instanceof ASTBoolOperation) {
			return enrichirV2((ASTBoolOperation) a);
		}
		if (a instanceof ASTIf) {
			return enrichirV2((ASTIf) a);
		}
		throw new EnrichissementNotImplementedException(
				"L'enrichissement n'est pas implémenté pour la classe : " + a.getClass());

	}

	/***
	 * Enrichissement de l'affectation, qui enrichit le membre droit, ou qui transforme l'affectation en If, 
	 * et place l'affectation dans la branche qui sera exécutée
	 * @param a
	 * @return
	 * @throws EnrichissementNotImplementedException
	 * @throws EnrichissementMissingException
	 */
	public static ReturnEnrichissement enrichirV2(ASTAffect a) throws EnrichissementNotImplementedException, EnrichissementMissingException {
		switch (RandomProvider.nextInt(10)) {
		//Affectation linéaire
		case 0 :
			Boolean[] bool = {true,false};
			boolean b = bool[RandomProvider.nextInt(bool.length)];
			ASTIf iff= new ASTIf(Lexenv.getNewName(), b, a );
			return new ReturnEnrichissement(iff);
		//Ajout d'un If
		default : 
			ReturnEnrichissement re = enrichirV2(a.getAffectation());
			a.setAffectation((AST)re.getIAST());
			a.setEnrichissements(re.getIAST().getEnrichissements());
			return new ReturnEnrichissement(re.getPreList(),a,re.getPostList());
		}
		//throw new EnrichissementMissingException(" Enrichissement de l'affectation ");


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
		//Test de remplacer les 0 par des valeurs plus interessantes (éviter les 0+0-0+0*0+0), mais fait parfois perdre la /0
		/*if ((Integer) a.getValeur()==0) {
			int temp= RandomProvider.nextInt(Integer.MAX_VALUE);
			return new ReturnEnrichissement(
					new ASTSous(new ASTConstante(a.getType(), a.getNom(), temp), new ASTConstante(a.getType(), Lexenv.getNewName(), temp))
					);
		}*/
		int i = RandomProvider.nextInt(nbEnrConst);
		//System.out.println(i);
		switch (i) {
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
		case 2 : 
			//cas d'un random
			return new ReturnEnrichissement(new ASTRand(a.getType(), a.getNom(), a.getValeur()));
		}
		throw new EnrichissementMissingException(" Enrichissement de la constante ");
	}
	/***
	 * Enrichissement d'un random, en enrichissant l'opération le composant
	 * @see #enrichirV2(ASTOpBinaire a)
	 * @param a
	 * @return
	 * @throws EnrichissementMissingException
	 * @throws EnrichissementNotImplementedException
	 */
	public static ReturnEnrichissement enrichirV2(ASTRand a) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		ReturnEnrichissement re = enrichirV2(a.getRand());
		a.setRand((ASTOpBinaire)re.getIAST());
		a.setEnrichissements(re.getIAST().getEnrichissements());
		return new ReturnEnrichissement(re.getPreList(),a,re.getPostList());
	}
	/***
	 * Enrichissement d'une opération, en enrichissant le membre gauche, ou droit 
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
			a.setEnrichissements(re.getIAST().getEnrichissements()+a.getDroite().getEnrichissements());
			return new ReturnEnrichissement(re.getPreList(),a,re.getPostList());
		case 1 : 
			ReturnEnrichissement re2 = enrichirV2(a.getDroite());
			a.setDroite((AST)re2.getIAST());
			a.setEnrichissements(re2.getIAST().getEnrichissements()+a.getGauche().getEnrichissements());
			return new ReturnEnrichissement(re2.getPreList(),a,re2.getPostList());
		}
		throw new EnrichissementMissingException("Operation");
	}
	/***
	 * Enrichissement de la variable en une expression de type (nom + VARLibre - VARLibre)
	 * @param a
	 * @return
	 * @throws EnrichissementMissingException
	 * @throws EnrichissementNotImplementedException
	 */
	public static ReturnEnrichissement enrichirV2(ASTVariable a) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		ASTOpBinaire sous;
		ASTOpBinaire sum;
		ASTVariable vg,vd;
		if (a.getUsable().size()!=0) {
			String n;
			boolean b;
			int cpt=0;
			//Recherche une variable utilisable et déclarée (normalement un tour de boucle suffit, mais dans le doute, 10 itermax)
			do {
				n=a.getUsable().get(RandomProvider.nextInt(a.getUsable().size()));
				b=a.getDeclaree().contains(n);
				cpt++;
			}while((!b) || (cpt<10));
			//Revérification
			if (a.getDeclaree().contains(n)&& a.getUsable().contains(n)) {
				vg=new ASTVariable(a.getType(),n,RandomProvider.nextInt(Integer.MAX_VALUE));
				vd=new ASTVariable(a.getType(),n,RandomProvider.nextInt(Integer.MAX_VALUE));
				vg.fuseDeclaree(a.getDeclaree());
				vg.fuseUsable(a.getUsable());
				vd.fuseDeclaree(a.getDeclaree());
				vd.fuseUsable(a.getUsable());
				sous = new ASTSous(vg,vd);
				sum = new ASTSum(a,sous);
				sous.fuseDeclaree(a.getDeclaree());
				sous.fuseUsable(a.getUsable());
				sum.fuseDeclaree(a.getDeclaree());
				sum.fuseUsable(a.getUsable());
				return new ReturnEnrichissement(sum);
			}
		}
		//Cas ou sous vide, soit pas de variable utilisable trouvée.
		//x + x-x
		vg=new ASTVariable(a.getType(),a.getNom(),RandomProvider.nextInt(Integer.MAX_VALUE));
		vd=new ASTVariable(a.getType(),a.getNom(),RandomProvider.nextInt(Integer.MAX_VALUE));
		vg.fuseDeclaree(a.getDeclaree());
		vg.fuseUsable(a.getUsable());
		vd.fuseDeclaree(a.getDeclaree());
		vd.fuseUsable(a.getUsable());
		sous = new ASTSous(vg,vd);
		sum = new ASTSum(a,sous);
		return new ReturnEnrichissement(sum);
	}
	/***
	 * Enrichissement de l'opération booléenne (condition du if) en enrichissant soit a gauche, soit a droite
	 * @param a
	 * @return
	 * @throws EnrichissementMissingException
	 * @throws EnrichissementNotImplementedException
	 */
	public static ReturnEnrichissement enrichirV2(ASTBoolOperation a) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		//Comme l'opération, on peut enrichir a gauche ou droite
		int rand = RandomProvider.nextInt(2);
		//System.out.println(""+a.getEnrichissements()+a.getGauche().getEnrichissements()+a.getDroite().getEnrichissements());
		if (a.getGauche().getEnrichissements()==0)
			rand=1;
		if (a.getDroite().getEnrichissements()==0)
			rand=0;
		switch (rand) {
		case 0 : 
			ReturnEnrichissement re = enrichirV2(a.getGauche());
			a.setGauche((ASTExpr)re.getIAST());
			a.setEnrichissements(re.getIAST().getEnrichissements()+a.getDroite().getEnrichissements());
			return new ReturnEnrichissement(re.getPreList(),a,re.getPostList());
		case 1 : 
			ReturnEnrichissement re2 = enrichirV2(a.getDroite());
			a.setDroite((ASTExpr)re2.getIAST());
			a.setEnrichissements(re2.getIAST().getEnrichissements()+a.getGauche().getEnrichissements());
			return new ReturnEnrichissement(re2.getPreList(),a,re2.getPostList());
		}
		throw new EnrichissementMissingException("Operation booleenne / Condition");
	}
	/***
	 * Enrichissement du If. Enrichie soit la condition, soit les instructions après le if, soit celles après le else. 
	 * @param a
	 * @return
	 * @throws EnrichissementMissingException
	 * @throws EnrichissementNotImplementedException
	 */
	public static ReturnEnrichissement enrichirV2(ASTIf a) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		int rand = RandomProvider.nextInt(3);
		int enrthen=0;
		int enrelse=0;
		for (IAST i : a.getThen()) {
			enrthen+=i.getEnrichissements();
		}
		for (IAST j : a.getElse()) {
			enrelse+=j.getEnrichissements();
		}
		//On gère le cas où un ou plusieurs sous arbres ne seraient pas enrichissables
		if (a.getCond().getEnrichissements()==0) {
			if (enrthen==0)
				rand=2;
			else {
				if (enrelse==0)
					rand=1;
				else
					rand=RandomProvider.nextInt(2)+1;
			}
		}
		if (enrthen==0) {
			if (a.getCond().getEnrichissements()==0)
				rand=2;
			else {
				if (enrelse==0)
					rand=0;
				else
					rand=RandomProvider.nextInt(2)*2;
			}
		}
		if (enrelse==0) {
			if (a.getCond().getEnrichissements()==0) 
				rand=1;
			else {
				if (enrthen==0)
					rand=0;
				else
					rand=RandomProvider.nextInt(2);
			}
		}
		switch (rand) {
		//cas Cond
		case 0 : 
			ReturnEnrichissement re = enrichirV2(a.getCond());
			//on enleve les enrichissements de la condition
			a.setEnrichissements(a.getEnrichissements()-a.getCond().getEnrichissements());
			a.setCond((ASTBoolOperation)re.getIAST());
			//Pour les rajouter après
			a.setEnrichissements(a.getEnrichissements()+re.getIAST().getEnrichissements());
			return new ReturnEnrichissement(re.getPreList(),a,re.getPostList());
		//cas Then
		//Code identique quasiment a la boucle principale. Moyen sans doute de le factoriser. 
		case 1 : 
			ArrayList<IAST> enrichissables= new ArrayList<>();
			for (IAST k : a.getThen()) {
				if (k.getEnrichissements()!=0)
					enrichissables.add(k);
			}
			IAST choisi = enrichissables.get(RandomProvider.nextInt(enrichissables.size()));
			int index = a.getThen().indexOf(choisi);
			re = enrichirV2(choisi);
			ArrayList<String> vardecpost= new ArrayList<>();
			ArrayList<String> varusepost = new ArrayList<>();
			
			vardecpost=re.getLast().getDeclaree();
			varusepost=re.getLast().getUsable();
			a.setEnrichissements(a.getEnrichissements()-a.getThen().get(index).getEnrichissements());
			a.getThen().remove(index);
			for (IAST i : re.getPreList()) {
				i.fuseDeclaree(vardecpost);
				i.fuseUsable(varusepost);
				a.getThen().add(index, i);
				index++;
			}
			re.getIAST().fuseDeclaree(vardecpost);
			re.getIAST().fuseUsable(varusepost);
			a.getThen().add(index, re.getIAST());
			for (IAST i : re.getPostList()) {
				i.fuseDeclaree(vardecpost);
				i.fuseUsable(varusepost);
				a.getThen().add(index, i);
				index++;
			}
			ArrayList<String> vardec = re.getVardec();
			for (int in = index; in<a.getThen().size();in++) {
				a.getThen().get(in).fuseDeclaree(vardec);
				a.getThen().get(in).fuseUsable(vardec);
			}
			a.setEnrichissements(a.getEnrichissements()+re.getIAST().getEnrichissements());
			return new ReturnEnrichissement(a);
		//cas Else
		case 2 : 
			enrichissables= new ArrayList<>();
			for (IAST k : a.getElse()) {
				if (k.getEnrichissements()!=0)
					enrichissables.add(k);
			}
			choisi = enrichissables.get(RandomProvider.nextInt(enrichissables.size()));
			index = a.getElse().indexOf(choisi);
			re = enrichirV2(choisi);
			vardecpost= new ArrayList<>();
			varusepost = new ArrayList<>();
			
			vardecpost=re.getLast().getDeclaree();
			varusepost=re.getLast().getUsable();
			a.setEnrichissements(a.getEnrichissements()-a.getElse().get(index).getEnrichissements());
			a.getElse().remove(index);
			for (IAST i : re.getPreList()) {
				i.fuseDeclaree(vardecpost);
				i.fuseUsable(varusepost);
				a.getElse().add(index, i);
				index++;
			}
			re.getIAST().fuseDeclaree(vardecpost);
			re.getIAST().fuseUsable(varusepost);
			a.getElse().add(index, re.getIAST());
			for (IAST i : re.getPostList()) {
				i.fuseDeclaree(vardecpost);
				i.fuseUsable(varusepost);
				a.getElse().add(index, i);
				index++;
			}
			vardec = re.getVardec();
			for (int in = index; in<a.getElse().size();in++) {
				a.getElse().get(in).fuseDeclaree(vardec);
				a.getElse().get(in).fuseUsable(vardec);
			}
			a.setEnrichissements(a.getEnrichissements()+re.getIAST().getEnrichissements());
			return new ReturnEnrichissement(a);
			
		}
		throw new EnrichissementMissingException("If");

	}
}
