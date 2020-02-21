package ast.errors;

import ast.ASTExpression;
import ast.ASTVar;
import ast.expressions.ASTExpr;
import ast.expressions.ASTVariable;
import ast.statement.memory.ASTFree;
import ast.statement.memory.ASTMalloc;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import factories.Lexenv;
import interfaces.IAST;

public class ASTErreurDoubleFree extends ASTExpr{
	IAST malloc;
	IAST free1;
	IAST free2;
	IAST owner;
	public ASTErreurDoubleFree(VarType type, String nom, Object valeur,IAST owner) {
		super();
		//nomme les deux variables avec des error_
		Lexenv.toggleError(true);
		IAST pointeur =new ASTVariable(VarType.PINT, Lexenv.getNewName(),"NULL",this);
		IAST num = new ASTVariable(VarType.INT,Lexenv.getNewName(),150,this);
		malloc= new ASTMalloc(pointeur, num, this);
		free1 = new ASTFree(pointeur, this);
		free2 = new ASTFree(pointeur,this);
		Lexenv.toggleError(false);
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

	@Override
	public void enrichissement(IAST old, IAST nouveau) throws EnrichissementMissingException {
				
	}
	
}
