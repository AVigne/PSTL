package ast.statement.memory;

import ast.AST;
import ast.expressions.ASTExpr;
import ast.statement.ASTStatement;
import enrichissement.Enrichissement;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import interfaces.IAST;

public class ASTFree extends ASTStatement {
	private IAST var;
	public ASTFree(IAST var, IAST owner) {
		this.owner=owner;
		this.var=var;
		Enrichissement.add(this);
	}
	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		sb.append("free("+var.getNom());
		sb.append(");\n");
	}

	@Override
	public void enrichissement(IAST old, IAST nouveau) throws EnrichissementMissingException {
		if(var==old) {
			var=nouveau;
		}
		else {
			throw new EnrichissementMissingException("L'expression enrichie n'est pas le pointeur du free");
		}
	}
	@Override
	public String getNom() {
		return this.nom;
	}

}
