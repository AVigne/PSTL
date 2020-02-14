package ast.errors;

import ast.ASTAffectation;
import ast.ASTExpression;
import ast.ASTVar;
import enums.VarType;

public class ASTErreurDoubleFree extends ASTExpression{

	public ASTErreurDoubleFree(VarType type, String nom, Object valeur) {
		super(type, nom, valeur);
		ASTExpression pointeur = new ASTVar(VarType.PINT, "a","NULL");
		ASTExpression num = new ASTVar(VarType.INT,"nb",10);
		
		explist.add(pointeur);
		explist.add(num);
	}
	
	@Override
	public void enrichissement() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(StringBuffer sb) {
		
		ASTExpression pointeur = explist.get(0);
		ASTExpression num = explist.get(1);
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
