package ast.statement.memory;

import ast.expressions.ASTExpr;
import ast.statement.ASTStatement;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import interfaces.IAST;

public class ASTMalloc extends ASTStatement {
	private ASTExpr num;
	private VarType pointeur;

	public ASTMalloc(IAST num, VarType pointeur) {
		this.num = (ASTExpr) num;
		this.pointeur = pointeur;
		this.enrichissements=1;
	}

	public IAST getNum() {
		return num;
	}

	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {

		sb.append("malloc( ");
		num.visit(sb);
		sb.append(" * sizeof (");
		switch (pointeur) {
		case PINT:
			sb.append("int))");
			break;
		default:
			break;
		}

	}

	@Override
	public String getNom() {
		return this.nom;
	}
	public void setNum(ASTExpr num) {
		this.num=num;
	}
	
	@Override
	public void addDeclaree(String n) {
		if (!declaree.contains(n))
			declaree.add(n);
		num.addDeclaree(n);
	}
	
	@Override
	public void addUsable(String n) {
		if (!usable.contains(n))
			usable.add(n);
		num.addUsable(n);
	}
}
