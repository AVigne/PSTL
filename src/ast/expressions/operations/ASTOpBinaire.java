package ast.expressions.operations;

import ast.AST;
import ast.expressions.ASTExpr;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.RandomProvider;
import interfaces.IAST;

public abstract class ASTOpBinaire extends ASTExpr {
	protected AST gauche;
	protected AST droite;

	public ASTOpBinaire(ASTExpr g, ASTExpr d) {
		gauche = g;
		droite = d;
		this.enrichissements=gauche.getEnrichissements()+droite.getEnrichissements();
	}

	public ASTOpBinaire(Object valeur) {
		this.valeur = valeur;
		int somme = (Integer) valeur;
		this.enrichissements=2;
		this.initCotes(somme);

	}

	protected abstract void initCotes(int somme);

	// Pour créer des op vides, permet de super, puis modifier les g,d
	public ASTOpBinaire() {
	}

	public IAST getGauche() {
		return gauche;
	}
	public void setGauche(AST g) {
		gauche=g;
	}
	public void setDroite(AST d) {
		droite=d;
	}
	public IAST getDroite() {
		return droite;
	}

	public Object getValeur() {
		return valeur;
	}

	// Renvoie une des 4 opérations de manière random
	public static IAST getRandomOperation(Object valeur) {
		int rand = RandomProvider.nextInt(4);
		switch (rand) {
		case 0:
			return new ASTSum(valeur);
		case 1:
			return new ASTSous(valeur);
		case 2:
			return new ASTMult(valeur);
		case 3:
			return new ASTDiv(valeur);
		default:
			return null;
		}
	}

	@Override
	public String getNom() {
		return this.getNom();
	}


	private void addOperator(StringBuffer sb) {
		if (this instanceof ASTMult) {
			sb.append(" * ");
			return;
		}
		if (this instanceof ASTDiv) {
			sb.append(" / ");
			return;
		}
		if (this instanceof ASTSum) {
			sb.append(" + ");
			return;
		}
		if (this instanceof ASTSous) {
			sb.append(" - ");
			return;
		}
	}

	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		sb.append("(");
		gauche.visit(sb);
		this.addOperator(sb);
		droite.visit(sb);
		sb.append(")");
	}
	
	@Override
	public void addDeclaree(String n) {
		if (!declaree.contains(n))
			this.declaree.add(n);
		gauche.addDeclaree(n);
		droite.addDeclaree(n);
	}
	@Override
	public void addUsable(String n) {
		if (!usable.contains(n))
			this.usable.add(n);
		gauche.addUsable(n);
		droite.addUsable(n);
	}

}
