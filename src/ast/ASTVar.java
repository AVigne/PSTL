package ast;

import enums.VarType;

public class ASTVar extends ASTExpression{
	
	public ASTVar (VarType type, String nom, Object valeur) {
		super(type,nom,valeur);
	}
	

	@Override
	public void enrichissement() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void visit(StringBuffer sb) {
		for (ASTExpression e : explist) {
			e.visit(sb);
		}
		switch (type) {
		case INT:
			sb.append("int "+nom+";\n");
			break;
		case PINT:
			sb.append("int* "+nom+";\n");
		default:
			break;
		}
		
	}

}
