package ast.statement.memory;

import ast.statement.ASTStatement;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import interfaces.IAST;

public class ASTFree extends ASTStatement {
	private IAST var;
	public ASTFree(IAST var) {
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

	@Override
	public void addDeclaree(String n) {
		if (!declaree.contains(n))
			declaree.add(n);
		var.addDeclaree(n);
	} 
	@Override
	public void addUsable(String n) {
		if (!usable.contains(n))
			usable.add(n);
		var.addUsable(n);
	}
}
