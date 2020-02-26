package ast.statement;

import ast.AST;
import ast.expressions.ASTVariable;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import factories.Enrichissement;
import factories.Lexenv;
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
		int index = Lexenv.getVars().indexOf(this.nom);
		if (!Lexenv.getDeclarations().get(index)) {
			String t="";
			switch (this.type) {
			case INT: t="int";break;
			case PINT: t="int* ";break;
			}
			sb.append(t+" "+this.nom+";\n");
			Lexenv.declaration(index);
		}
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
