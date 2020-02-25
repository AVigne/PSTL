package ast.expressions;

import java.util.ArrayList;

import ast.AST;
import ast.statement.ASTDeclaration;
import enrichissement.Enrichissement;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import interfaces.IAST;

public class ASTAffect extends ASTExpr {
	
	protected AST var;
	protected VarType type;
	protected String nom;
	
	public ASTAffect(VarType type, String nom, Object valeur, IAST owner) {
		this.valeur=valeur;
		this.nom=nom;
		this.type=type;
		var= new ASTVariable(type,nom, valeur,this);
		this.owner=owner;
		Enrichissement.add(this);
	}
	@Override
	public VarType getType() {
		return type;
	}
	public IAST getVar() {
		return var;
	}
	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		/*System.out.println(this.type);
		switch (this.type) {
			case PINT: sb.append("int* "); break;
			case INT : sb.append("int "); break;
		}*/
		ArrayList<AST> affects = getAffect(new ArrayList<AST>());
		for (AST i : affects) {
			
			if (!i.isaffectee()) {
				ASTDeclaration a = new ASTDeclaration(i.getType(),i.getNom(),i.getOwner());
				a.visit(sb);
				i.affectee();
				i.visit(sb);

				if (i.equals(this))
					visitee=true;
			}
		}
		if(!visitee) {
			sb.append(this.nom+" = ");
			var.visit(sb);
			sb.append(";\n");
		}
		
	}

	@Override
	public void enrichissement(IAST old, IAST nouveau) throws EnrichissementMissingException {
		System.out.println("TEST");
		System.out.println(var);
		System.out.println(old);
		if (var.equals(old)) {
			Enrichissement.pop(var);
			var=(AST)nouveau;
		}
		else {
			throw new EnrichissementMissingException("L'expression enrichie n'est pas celle de l'affectation");
		}
		Enrichissement.pop(this);
	}

	@Override
	public String getNom() {
		return this.nom;
	}
	
	@Override
	public ArrayList<AST> getAffect(ArrayList<AST> a){
		if (!this.isaffectee())
			a.add(this);
		if (!var.isaffectee())
			return var.getAffect(a);
		return a;
	}

}
