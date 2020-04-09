package ast.errors;

import java.util.ArrayList;

import ast.AST;
import ast.expressions.ASTConstante;
import ast.expressions.ASTExpr;
import ast.expressions.ASTVariable;
import ast.statement.ASTAffect;
import ast.statement.ASTDeclaration;
import ast.statement.ASTStatement;
import ast.statement.memory.ASTFree;
import ast.statement.memory.ASTMalloc;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Lexenv;
import factories.RandomProvider;
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
		ASTVariable pointeur =new ASTVariable(VarType.PINT, Lexenv.getNewName(),"NULL");
		ASTConstante num = new ASTConstante(VarType.INT,Lexenv.getNewName(),RandomProvider.nbRandom);
		//Le malloc est une affectation
		ASTStatement malloc= new ASTMalloc(num, VarType.PINT);
		affect_malloc = new ASTAffect(pointeur.getType(),pointeur.getNom(),malloc);
		free1 = new ASTFree(pointeur);
		free2 = new ASTFree(pointeur);
		free1.setEnrichissements(0);
		free2.setEnrichissements(0);
		affect_malloc.setEnrichissements(1);
		Lexenv.toggleError(false);
	}
	public ArrayList<AST> getAst(){
		ArrayList<AST> a = new ArrayList<>();
		ASTDeclaration dec = new ASTDeclaration(affect_malloc.getType(),affect_malloc.getNom());
		dec.addDeclaree(dec.getNom());
		a.add(dec);
		affect_malloc.addDeclaree(dec.getNom());
		a.add(affect_malloc);
		free1.addDeclaree(dec.getNom());
		a.add(free1);
		free2.addDeclaree(dec.getNom());
		a.add(free2);
		return a;
	}

	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		System.out.println("JAMAIS ICI");
		affect_malloc.visit(sb);
		free1.visit(sb);
		free2.visit(sb);
		
		
	}



	@Override
	public String getNom() {
		return null;
	}
	
}
