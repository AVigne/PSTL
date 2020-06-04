package ast.expressions;

import ast.AST;
import enums.VarType;

public abstract class ASTExpr extends AST {
	protected VarType typeretour;
	protected Object valeur;
	protected VarType typepointee = null;

	public VarType getType() {
		return typeretour;
	}

	public Object getValeur() {
		return valeur;
	}

}
