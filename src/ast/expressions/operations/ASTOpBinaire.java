package ast.expressions.operations;

import java.util.ArrayList;

import ast.AST;
import ast.expressions.ASTExpr;
import ast.expressions.ASTVariable;
import ast.statement.ASTAffect;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Enrichissement;
import factories.Lexenv;
import factories.RandomProvider;
import interfaces.IAST;

public abstract class ASTOpBinaire extends ASTExpr {
	protected AST gauche;
	protected AST droite;

	public ASTOpBinaire(ASTExpr g, ASTExpr d, IAST owner) {
		gauche = g;
		droite = d;
		this.owner = owner;
		this.enrichissements=2;
	}

	public ASTOpBinaire(Object valeur, IAST owner) {
		this.valeur = valeur;
		this.owner = owner;
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
	public static IAST getRandomOperation(Object valeur, IAST owner) {
		switch (RandomProvider.nextInt(4)) {
		case 0:
			return new ASTSum(valeur, owner);
		case 1:
			return new ASTSous(valeur, owner);
		case 2:
			return new ASTMult(valeur, owner);
		case 3:
			return new ASTDiv(valeur, owner);
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

}
