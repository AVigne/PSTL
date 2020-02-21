package ast;

import enums.VarType;

public class ASTDeclaration extends ASTStatement{
	//VARNEW
	protected ASTVar var;
	protected String name;
	protected Object valeur;
	protected VarType type;
	
	public ASTDeclaration(VarType type, String nom, Object valeur) {
		this.nom=nom;
		this.valeur=valeur;
		this.type=type;
		var= new ASTVar(type,nom, valeur);
	}
	
	@Override
	public void visit(StringBuffer sb) {
		// TODO Auto-generated method stub
		
	}

}
