package ast.statement.memory;

import ast.AST;
import ast.expressions.ASTExpr;
import ast.statement.ASTStatement;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Enrichissement;
import interfaces.IAST;

public class ASTFree extends ASTStatement {
	private IAST var;
	public ASTFree(IAST var, IAST owner) {
		this.owner=owner;
		this.var=var;
		this.enrichissements=0;
	}
	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		sb.append("free(");
		var.visit(sb);
		sb.append(");\n");
	}


	@Override
	public String getNom() {
		return this.nom;
	}

}
