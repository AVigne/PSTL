package ast;

import java.util.ArrayList;

import enums.VarType;
import interfaces.IASTExpression;

public abstract class ASTExpression implements IASTExpression{
	protected VarType type;
	protected String nom;
	protected Object valeur;
	protected ArrayList<ASTExpression> explist= new ArrayList<>();;
	
	public ASTExpression (VarType type, String nom,Object valeur) {
		this.type=type;
		this.nom=nom;
		this.valeur=valeur;
	}
	
	public VarType getType() {
		return type;
	}
	public String getNom() {
		return nom;
	}
	
	public Object getValeur() {
		return valeur;
	}
	
}
