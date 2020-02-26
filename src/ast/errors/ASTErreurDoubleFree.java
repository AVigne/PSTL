package ast.errors;

import ast.AST;
import ast.expressions.ASTAffect;
import ast.expressions.ASTExpr;
import ast.expressions.ASTVariable;
import ast.statement.ASTStatement;
import ast.statement.memory.ASTFree;
import ast.statement.memory.ASTMalloc;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Enrichissement;
import factories.Lexenv;
import interfaces.IAST;

public class ASTErreurDoubleFree extends ASTExpr{
	ASTAffect affect_malloc;
	ASTStatement free1;
	ASTStatement free2;
	AST owner;
	public ASTErreurDoubleFree(VarType type, String nom, Object valeur,IAST owner) throws EnrichissementMissingException {
		super();
		//nomme les deux variables avec des error_
		Lexenv.toggleError(true);
		ASTVariable pointeur =new ASTVariable(VarType.PINT, Lexenv.getNewName(),"NULL",this);
		ASTVariable num = new ASTVariable(VarType.INT,Lexenv.getNewName(),150,this);
		ASTStatement malloc= new ASTMalloc(num, VarType.PINT, this);
		affect_malloc = new ASTAffect(pointeur.getType(),pointeur.getNom(),pointeur.getValeur(),this);
		Enrichissement.pop(affect_malloc.getVar());
		Enrichissement.pop(affect_malloc);
		affect_malloc.enrichissement(affect_malloc.getVar(), malloc);
		free1 = new ASTFree(pointeur, this);
		free2 = new ASTFree(pointeur,this);
		Lexenv.toggleError(false);
	}


	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		
		affect_malloc.visit(sb);
		sb.append("printf(\"%d\\n\","+((ASTMalloc)affect_malloc.getVar()).getNum().getNom()+");\n");
		free1.visit(sb);
		free2.visit(sb);
		
		
	}

	@Override
	public void enrichissement(IAST old, IAST nouveau) throws EnrichissementMissingException {
				
	}


	@Override
	public String getNom() {
		return null;
	}
	
}
