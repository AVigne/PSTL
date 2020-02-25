package ast.statement;

import ast.AST;
import ast.expressions.ASTVariable;
import enrichissement.Enrichissement;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import interfaces.IAST;

public class ASTDeclaration extends ASTStatement{

	protected IAST var;
	protected VarType type;
	
	public ASTDeclaration(VarType type, String nom, IAST owner) {
		this.nom=nom;
		this.type=type;
		var= new ASTVariable(type,nom, valeur,this);
		this.owner=owner;
		Enrichissement.add(this);
	}
	
	@Override
	public void visit(StringBuffer sb) {
		String t="";
		switch (this.type) {
		case INT: t="int";
		case PINT: t="int* ";
		}
		sb.append(t+" "+this.nom+";\n");
	}
	
	public void enrichissement(IAST old, IAST nouveau) throws EnrichissementMissingException{
		if (var==old) {
			var=nouveau;
		}
		else {
			throw new EnrichissementMissingException("La variable enrichie n'est pas celle déclarée");
		}
	}

	@Override
	public String getNom() {
		return this.nom;
	}
}
