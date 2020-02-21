package ast.expressions.operations;

import java.util.ArrayList;

import ast.AST;
import ast.expressions.ASTExpr;
import enrichissement.Enrichissement;
import exceptions.EnrichissementMissingException;
import factories.RandomProvider;
import interfaces.IAST;

public class ASTOp extends ASTExpr{
	protected IAST gauche;
	protected IAST droite;

	
	public ASTOp(ASTExpr g, ASTExpr d,IAST owner) {
		gauche=g;
		droite=d;
		this.owner=owner;
		Enrichissement.add(gauche);
		Enrichissement.add(droite);
	}
	
	//Pour créer des op vides, permet de super, puis modifier les g,d
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
	public void visit(StringBuffer sb) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enrichissement(IAST old, IAST nouveau) throws EnrichissementMissingException {
		if(gauche==old) {
			gauche=nouveau;
			return;
		}
		if (droite==old) {
			droite=nouveau;
			return;
		}
		throw new EnrichissementMissingException("L'expression enrichie n'est pas dans l'opération");
	}
	
	//Permet de récup la liste des variables pour pouvoir les initialiser avant
	public ArrayList<IAST> getVariables(ArrayList<IAST> res){
		if (this.gauche.getClass().getCanonicalName().equals("ASTVariable")) {
			res.add(gauche);
		}
		else {
			res = ((ASTOp)gauche).getVariables(res);
		}
		if (this.droite.getClass().getCanonicalName().equals("ASTVariable")) {
			res.add(droite);
		}
		else {
			res = ((ASTOp)droite).getVariables(res);
		}
		
		return res;
	}
	
	//Renvoie une des 4 opérations de manière random
	public static IAST getOperation(Object valeur,IAST owner) {
		switch (RandomProvider.nextInt(4)) {
		case 0 : return new ASTSum(valeur,owner);
		case 1 : return new ASTSous(valeur,owner);
		case 2 : return new ASTMult(valeur,owner);
		case 3 : return new ASTDiv(valeur,owner);
		default : return new ASTSum(valeur,owner);
		}
	}

}
