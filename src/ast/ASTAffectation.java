package ast;

import enums.VarType;

public class ASTAffectation extends ASTExpression{

	public ASTAffectation(VarType type, String nom, Object valeur) {
		super(type, nom, valeur);
	}

	@Override
	public void enrichissement(int nb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(StringBuffer sb) {
		//Juste une affectation, à la valeur donnée
		sb.append(nom+" = "+valeur.toString()+";\n");
	}

}
