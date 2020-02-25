package ast.expressions;

import ast.AST;
import enums.VarType;

public abstract class ASTExpr extends AST{
	protected VarType typeretour;
	protected Object valeur;
	protected VarType typepointee=null;
	protected boolean visitee=false;
	public VarType getType() {
		return typeretour;
	}
	public Object getValeur() {
		return valeur;
	}
}
