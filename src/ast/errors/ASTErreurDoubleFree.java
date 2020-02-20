package ast.errors;

import ast.ASTExpression;
import ast.ASTVar;
import enums.VarType;
import factories.Lexenv;

public class ASTErreurDoubleFree extends ASTExpression{
	ASTExpression pointeur;
	ASTExpression num;
	public ASTErreurDoubleFree(VarType type, String nom, Object valeur) {
		super(type, nom, valeur);
		//nomme les deux variables avec des error_
		Lexenv.toggleError(true);
		pointeur = new ASTVar(VarType.PINT, Lexenv.getNewName(),"NULL");
		num = new ASTVar(VarType.INT,Lexenv.getNewName(),150);
		Lexenv.toggleError(false);
	}
	
	@Override
	public void enrichissement(int nb) {
		//Que la gestion d'enrichissement d'entiers pour le moment
		num.enrichissement(nb);
	}

	@Override
	public void visit(StringBuffer sb) {
		//Permet d'initialiser et de visiter les eventuels enrichissements
		pointeur.visit(sb);
		num.visit(sb);
		
		//Pour vister la liste d'ASTExpression -> Bruit, pour plus tard
		/*for (int i=2;i<explist.size();i++) {
			explist.get(i).visit(sb);
		}*/ 
		
		//Permet de récuperer le type a utliser dans le malloc
		String type="";
		switch (pointeur.getPointee()) {
			case INT: type="int"; break;
			case CHAR: type="char";break;
			default:
		}
		
		
		//Bruit a générer de manière aléatoire avant et entre les free -> a gerer plus tard
		sb.append(pointeur.getNom()+" = malloc("+num.getNom()+"*sizeof("+type+"));\n");
		sb.append("free("+pointeur.getNom()+");\n");
		sb.append("free("+pointeur.getNom()+");\n");
		
	}
	
}
