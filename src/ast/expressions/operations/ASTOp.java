package ast.expressions.operations;

import java.util.ArrayList;

import ast.AST;
import ast.expressions.ASTAffect;
import ast.expressions.ASTExpr;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Enrichissement;
import factories.RandomProvider;
import interfaces.IAST;

public abstract class ASTOp extends ASTExpr {
	protected AST gauche;
	protected AST droite;

	public ASTOp(ASTExpr g, ASTExpr d, IAST owner) {
		gauche = g;
		droite = d;
		this.owner = owner;
		Enrichissement.add(gauche);
		Enrichissement.add(droite);
	}

	// Pour créer des op vides, permet de super, puis modifier les g,d
	public ASTOp() {
	}

	public IAST getGauche() {
		return gauche;
	}

	public IAST getDroite() {
		return droite;
	}

	public Object getValeur() {
		return valeur;
	}

	@Override
	public void enrichissement(IAST old, IAST nouveau) throws EnrichissementMissingException {
		/*
		 * System.out.println("OP"); System.out.println(gauche);
		 * System.out.println(droite); System.out.println(old);
		 */
		if (gauche == old) {
			Enrichissement.pop(gauche);
			gauche = (AST) nouveau;
			return;
		}
		if (droite == old) {
			Enrichissement.pop(droite);
			droite = (AST) nouveau;
			return;
		}
		throw new EnrichissementMissingException("L'expression enrichie n'est pas dans l'opération");
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
			return new ASTSum(valeur, owner);
		}
	}

	@Override
	public String getNom() {
		return this.getNom();
	}

	@Override
	/**
	 * Récupère les affectations dans les membres gauche et droits pour pouvoir les
	 * faire avant
	 */
	public ArrayList<AST> getAffect(ArrayList<AST> a) {
		a = gauche.getAffect(a);
		a = droite.getAffect(a);
		return a;
	}

}
