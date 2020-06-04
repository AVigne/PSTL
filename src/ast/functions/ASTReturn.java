package ast.functions;

import ast.AST;
import ast.statement.ASTStatement;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import interfaces.IAST;

public class ASTReturn extends ASTStatement {
	protected IAST affectation;

	public ASTReturn(IAST aff) {
		affectation = aff;
		this.enrichissements = affectation.getEnrichissements();
	}

	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		sb.append("return ");
		affectation.visit(sb);
		sb.append(";");
	}

	@Override
	public String getNom() {
		return nom;
	}

	public IAST getAffectation() {
		return affectation;
	}

	public void setAffectation(IAST a) {
		affectation = a;
	}

	public void addDeclaree(String n) {
		if (!declaree.contains(n))
			this.declaree.add(n);
		affectation.addDeclaree(n);
	}

	@Override
	public void addUsable(String n) {
		if (!usable.contains(n))
			this.usable.add(n);
		affectation.addUsable(n);
	}
}
