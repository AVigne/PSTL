package ast.errors;

import ast.ASTExpression;
import ast.ASTVar;
import enums.VarType;
import factories.Lexenv;

public class ASTErreurDoubleFree extends ASTExpression{
	
	int nbEnrichissement=10;
	
	ASTExpression pointeur;
	ASTExpression num;
	public ASTErreurDoubleFree(VarType type, String nom, Object valeur) {
		super(type, nom, valeur);
		pointeur = new ASTVar(VarType.PINT, Lexenv.getNewName(),"NULL");
		num = new ASTVar(VarType.INT,Lexenv.getNewName(),150);
	}
	
	@Override
	public void enrichissement(int nb) {
		//Que la gestion d'enrichissement d'entiers pour le moment
			num.enrichissement(nb);
	}

	@Override
	public void visit(StringBuffer sb) {
		pointeur.visit(sb);
		num.visit(sb);
		
		/*for (int i=2;i<explist.size();i++) {
			explist.get(i).visit(sb);
		} Si on choisi d'ajouter des expressions, pas sur si bon choix d'implementation*/ 
		
		
		String type="";
		switch (pointeur.getPointee()) {
			case INT: type="int"; break;
			case CHAR: type="char";break;
			default:
		}
		
		
		//A voir comment on gère le bruit entre les free, % de chance dans les explist d'avant?
		sb.append(pointeur.getNom()+" = malloc("+num.getNom()+"*sizeof("+type+"));\n");
		sb.append("free("+pointeur.getNom()+");\n");
		sb.append("free("+pointeur.getNom()+");\n");
		
	}
	
}
