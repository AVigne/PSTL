package ast;

import enums.VarType;

public class ASTVar extends ASTExpression{
	
	public ASTVar (VarType type, String nom, Object valeur) {
		super(type,nom,valeur);
		
		//Dans le cadre des pointeurs, potentiellement besoin de récup le type pointé, pour les mallocs par exemple
		switch (type) {
		case PINT: pointée=VarType.INT;
			break;
		case PCHAR:pointée=VarType.CHAR;
			break;
		default:
		}
	}
	

	@Override
	public void enrichissement() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void visit(StringBuffer sb) {
		//Déclaration
		switch (type) {
		case INT:
			sb.append("int "+nom+";\n");
			break;
		case PINT:
			sb.append("int* "+nom+";\n");
		default:
			break;
		}
		//Si pas d'expression, donc pas de bruit ni enrichi, on affecte juste la valeur donnée
		if (explist.size()==0) {
			ASTExpression aff= new ASTAffectation(type,this.nom,this.valeur);
			aff.visit(sb);
		}
		//Sinon on parcours toutes les expressions
		else {
			for (ASTExpression e : explist) {
				e.visit(sb);
			}
		}
	}

}
