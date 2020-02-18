package ast;

import java.util.Random;

import ast.operations.*;
import enums.VarType;
import factories.Lexenv;

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
		if (explist.isEmpty()) {
			//Permet d'enrichir random entre +-*/
			int op = new Random().nextInt(4);
			
			
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
				return;

			}
		}
		else {
			explist.get(0).enrichissement();
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
