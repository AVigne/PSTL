package ast.statement;

import java.util.ArrayList;

import ast.AST;
import ast.expressions.ASTBoolOperation;
import ast.expressions.operations.ASTOpBinaire;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Lexenv;
import factories.RandomProvider;
import interfaces.IAST;

public class ASTIf extends ASTStatement {
	private ASTBoolOperation cond;
	private ArrayList<IAST> thn = new ArrayList<>();
	private ArrayList<IAST> els = new ArrayList<>();
	private String tab = "";
	/***
	 * Cas d'un If auquel on donnerait une condition, et deux listes.
	 * Non utilisé
	 * @param nom
	 * @param valeur
	 * @param c
	 * @param t
	 * @param e
	 */
	public ASTIf(String nom, Object valeur, ASTBoolOperation c, ArrayList<IAST> t, ArrayList<IAST> e) {
		this.nom = nom;
		this.valeur = valeur;
		cond=c;
		thn=t;
		els=e;
		int enr=cond.getEnrichissements();
		for (IAST i : thn) {
			enr+=i.getEnrichissements();
		}
		for (IAST j : els) {
			enr+=j.getEnrichissements();
		}
		this.enrichissements=enr;
	}
	/***
	 * A partir d'un bouléen et d'un AST, crée un If tel que l'AST sera exécuté
	 * @param nom
	 * @param valeur
	 * @param e
	 */
	public ASTIf(String nom, Object valeur, ASTStatement e) {
		this.nom = nom;
		this.valeur = valeur;
		//Créer nouvelle condition aléatoire
		cond = new ASTBoolOperation(VarType.BOOLEAN, Lexenv.getNewName(), valeur);
		//True -> dans le then pour qu'elle soit éxécutée, dans le else une nouvelle affectation, d'une opération random de valeurs randoms
		if ((Boolean)valeur) {
			thn.add(e);
			els.add(new ASTAffect(e.getType(), e.getNom(), (AST) ASTOpBinaire.getRandomOperation(RandomProvider.nextInt(RandomProvider.nbRandom))));
		}
		// False -> dans le else, dans le then, idem que le then
		else {
			els.add(e);
			thn.add(new ASTAffect(e.getType(), e.getNom(), (AST) ASTOpBinaire.getRandomOperation(RandomProvider.nextInt(RandomProvider.nbRandom))));

		}
		this.enrichissements=cond.getEnrichissements()+e.getEnrichissements();

	}
	/***
	 * Permet de gérer les tabulations, et donc l'indentation dans le visiteur
	 * @param s
	 */
	public void addSetTab(String s) {
		tab=s+"\t";
	}
	
	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		sb.append(tab);
		sb.append("if(");
		cond.visit(sb);
		sb.append("){\n");
		for (IAST i : thn) {
			//Si on rentre dans un if, on rajoute une tabulation 
			if (i instanceof ASTIf) {
				((ASTIf) i).addSetTab(tab);
				i.visit(sb);
			}
			else {
				sb.append("\t");
				sb.append(tab);
				i.visit(sb);
			}
		}
		sb.append(tab);
		sb.append("}\n");
		sb.append(tab);
		sb.append("else {\n");
		for (IAST j : els) {
			//Si if, tabulation supplémentaire
			if (j instanceof ASTIf) {
				((ASTIf) j).addSetTab(tab);
				j.visit(sb);
			}
			else {
				sb.append("\t");
				sb.append(tab);
				j.visit(sb);
			}
		}
		sb.append(tab);
		sb.append("}\n");
		
	}

	@Override
	public String getNom() {
		return nom;
	}
	public ASTBoolOperation getCond() {
		return cond;
	}
	public void setCond(ASTBoolOperation cond) {
		this.cond = cond;
	}
	public ArrayList<IAST> getThen() {
		return thn;
	}
	public void setThen(ArrayList<IAST> thn) {
		this.thn = thn;
	}
	public ArrayList<IAST> getElse() {
		return els;
	}
	public void setElse(ArrayList<IAST> els) {
		this.els = els;
	}
	
	@Override
	public void addDeclaree(String n) {
		if (!declaree.contains(n))
			this.declaree.add(n);
		for (IAST i : thn){
			i.addDeclaree(n);
		}
		for (IAST j : els) {
			j.addDeclaree(n);
		}
	}
	@Override
	public void addUsable(String n) {
		if (!usable.contains(n))
			this.usable.add(n);
		for (IAST i : thn){
			i.addUsable(n);
		}
		for (IAST j : els) {
			j.addUsable(n);
		}
	}

}
