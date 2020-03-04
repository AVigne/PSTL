package ast.statement.memory;

import java.util.ArrayList;

import ast.AST;
import ast.expressions.ASTExpr;
import ast.statement.ASTAffect;
import ast.statement.ASTDeclaration;
import ast.statement.ASTStatement;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Enrichissement;
import interfaces.IAST;

public class ASTMalloc extends ASTStatement {
	private ASTExpr num;
	private VarType pointeur;

	public ASTMalloc(IAST num, VarType pointeur, IAST owner) {
		this.num = (ASTExpr) num;
		this.owner = owner;
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
}
