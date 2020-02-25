package ast.expressions;

import ast.AST;
import enrichissement.Enrichissement;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import interfaces.IAST;

public class ASTVariable extends ASTExpr{
	protected String nom;
	protected VarType pointee;

	public ASTVariable(VarType type, String nom, Object valeur,IAST owner) {
		this.typeretour=type;
		this.nom=nom;
		this.valeur=valeur;
		this.owner=owner;
		Enrichissement.add(this);
		switch (type) {
		case PINT : pointee = VarType.INT; break;
		}
		
		
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
	public VarType getPointee() {
		return pointee;
	}
	public IAST toAffect() {
		return new ASTAffect(typeretour,nom,valeur,owner);
	}
	
	
	@Override
	public void visit(StringBuffer sb) {
		sb.append(this.valeur);
	}
	public void enrichissement(IAST old, IAST nouveau) throws EnrichissementMissingException{
		this.owner.enrichissement(old,nouveau);
	}
}
