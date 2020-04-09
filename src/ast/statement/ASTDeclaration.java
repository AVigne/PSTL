package ast.statement;

import ast.expressions.ASTVariable;
import enums.VarType;
import interfaces.IAST;

public class ASTDeclaration extends ASTStatement{

	protected IAST var;
	protected VarType type;
	
	public ASTDeclaration(VarType type, String nom) {
		this.nom=nom;
		this.type=type;
		var= new ASTVariable(type,nom, valeur);
		this.enrichissements=0;
		this.addDeclaree(nom);
	}
	
	@Override
	public void visit(StringBuffer sb) {
		String t="";
		switch (this.type) {
		case INT: t="int";break;
		case PINT: t="int* ";break;
		default:
		}
		sb.append(t+" "+this.nom+";\n");		
	}
	

	@Override
	public String getNom() {
		return this.nom;
	}
	
}
