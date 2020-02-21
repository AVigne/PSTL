package ast.statement;

import ast.AST;
import ast.expressions.ASTVariable;
import enrichissement.Enrichissement;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import interfaces.IAST;

public class ASTAffect extends ASTStatement {
	
	protected IAST var;
	protected VarType type;
	
	public ASTAffect(VarType type, String nom, Object valeur, IAST owner) {
		this.nom=nom;
		this.valeur=valeur;
		this.type=type;
		var= new ASTVariable(type,nom, valeur,this);
		this.owner=owner;
		Enrichissement.add(this);
	}
	
	public IAST getVar() {
		return var;
	}
	@Override
	public void visit(StringBuffer sb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enrichissement(IAST old, IAST nouveau) throws EnrichissementMissingException {
		if (var==old) {
			var=nouveau;
		}
		else {
			throw new EnrichissementMissingException("L'expression enrichie n'est pas celle de l'affectation");
		}
	}

}
