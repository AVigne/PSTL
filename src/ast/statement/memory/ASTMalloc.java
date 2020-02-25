package ast.statement.memory;

import java.util.ArrayList;

import ast.AST;
import ast.expressions.ASTAffect;
import ast.expressions.ASTExpr;
import ast.statement.ASTDeclaration;
import ast.statement.ASTStatement;
import enrichissement.Enrichissement;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import interfaces.IAST;

public class ASTMalloc extends ASTStatement {
	private ASTExpr num;
	private VarType pointeur;
	public ASTMalloc(IAST num, VarType pointeur, IAST owner) {
		this.num=(ASTExpr)num;
		this.owner=owner;
		this.pointeur=pointeur;
		Enrichissement.add(this);
	}
	
	public IAST getNum() {
		return num;
	}
	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		/*ArrayList<AST> affects = num.getAffect(new ArrayList<>());
		System.out.println(affects);
		for (AST i : affects) {
			if (!i.isaffectee()) {
				ASTDeclaration a = new ASTDeclaration(i.getType(),i.getNom(),i.getOwner());
				a.visit(sb);
				i.visit(sb);
			}
			i.affectee();
		}*/
		if (num instanceof ASTAffect) 
			System.out.println("on passe ici");

		sb.append("malloc( ");
		sb.append(num.getNom());
		sb.append(" * sizeof (");
		switch (pointeur) {
		case PINT: sb.append("int))"); break;
		default : break;
		}
		
	}

	@Override
	public void enrichissement(IAST old, IAST nouveau) throws EnrichissementMissingException {
		if (num==old) {
			Enrichissement.pop(num);
			num = (ASTExpr)nouveau;
			return;
		}
		throw new EnrichissementMissingException("L'entier n'est pas l'expression enrichie");
	}

	@Override
	public String getNom() {
		return this.nom;
	}
	
	@Override
	public ArrayList<AST> getAffect(ArrayList<AST> a){
		return num.getAffect(a);
	}

}
