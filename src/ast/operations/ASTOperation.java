package ast.operations;

import ast.ASTExpression;
import enums.VarType;


//Utile si liste d'operation plus tard. Sinon juste extends ASTEXpression -> changement minime

public class ASTOperation extends ASTExpression {
	
	public ASTOperation(VarType type, String nom, Object valeur) {
		super(type, nom, valeur);
		
	}

	@Override
	public void enrichissement() {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StringBuffer sb) {
		// TODO Auto-generated method stub

	}

}
