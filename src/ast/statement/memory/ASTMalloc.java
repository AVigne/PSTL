package ast.statement.memory;

import ast.AST;
import ast.statement.ASTStatement;
import enrichissement.Enrichissement;
import exceptions.EnrichissementMissingException;
import interfaces.IAST;

public class ASTMalloc extends ASTStatement {
	private IAST pointeur;
	private IAST num;
	
	public ASTMalloc(IAST pointeur,IAST num, IAST owner) {
		this.pointeur=pointeur;
		this.num=num;
		this.owner=owner;
		Enrichissement.add(this);
	}
	
	public IAST getPointeur() {
		return pointeur;
	}
	public IAST getNum() {
		return num;
	}
	@Override
	public void visit(StringBuffer sb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enrichissement(IAST old, IAST nouveau) throws EnrichissementMissingException {
		if (pointeur==old) {
			pointeur = nouveau;
			return;
		}
		if (num==old) {
			num = nouveau;
			return;
		}
		throw new EnrichissementMissingException("Ni le pointeur ni l'entier ne sont l'expression enrichie");
	}

}
