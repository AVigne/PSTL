package ast;


import ast.operations_old.*;
import enums.VarType;
import factories.RandomProvider;

public class ASTVar extends ASTExpression{
	
	public ASTVar (VarType type, String nom, Object valeur) {
		super(type,nom,valeur);
		
		//Dans le cadre des pointeurs, potentiellement besoin de récup le type pointé, pour les mallocs par exemple
		switch (type) {
		case PINT: pointee=VarType.INT;
			break;
		case PCHAR:pointee=VarType.CHAR;
			break;
		default:
		}
	}
	

	@Override
	public void enrichissement(int nb) {
		if (explist.isEmpty()) {
			//Permet d'enrichir random entre +-*/
			int op = RandomProvider.nextInt(4);
			
			
			//Si jamais etendue, on étends gauche
			if (explist.size()==0) {
				switch (op) {
				case 0 : 			explist.add(new ASTSomme(VarType.INT,this.nom,this.valeur,false));
									break;
				case 1 : 			explist.add(new ASTSoustraction(VarType.INT,this.nom,this.valeur,false));
									break;
				case 2 : 			explist.add(new ASTMultiplication(VarType.INT,this.nom,this.valeur,false));
									break;
				case 3 :			explist.add(new ASTDivision(VarType.INT,this.nom,this.valeur,false));
									break;
				}
				explist.get(0).enrichissement(nb-1);				
				return;
			}
		}
		else {
			explist.get(0).enrichissement(nb);
		}
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
