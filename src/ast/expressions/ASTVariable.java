package ast.expressions;

import ast.AST;
import ast.statement.ASTAffect;
import enrichissement.Enrichissement;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import interfaces.IAST;

public class ASTVariable extends ASTExpr{
	protected String nom;


	public ASTVariable(VarType type, String nom, Object valeur,IAST owner) {
		this.typeretour=type;
		this.nom=nom;
		this.valeur=valeur;
		this.owner=owner;
		Enrichissement.add(this);
		
	}
	public VarType getType() {
		return typeretour;
	}
	public String getNom() {
		return nom;
	}
	public Object getValeur() {
		return valeur;
	}
	
	public IAST toAffect() {
		return new ASTAffect(typeretour,nom,valeur,owner);
	}
	
	
	@Override
	public void visit(StringBuffer sb) {
		// TODO Auto-generated method stub
		
	}
	public void enrichissement(IAST old, IAST nouveau) throws EnrichissementMissingException{
		this.owner.enrichissement(old,nouveau);
	}
}
