package ast.statement;

import java.util.ArrayList;

import ast.AST;
import ast.expressions.ASTExpr;
import ast.expressions.ASTVariable;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Enrichissement;
import interfaces.IAST;

public class ASTAffect extends ASTStatement {

	protected ASTVariable var;
	protected AST affectation;
	protected VarType type;
	protected String nom;

	public ASTAffect(VarType type, String nom, AST affectation, IAST owner) {
		this.affectation = affectation;
		this.nom = nom;
		this.type = type;
		var = new ASTVariable(type, nom, valeur, this);
		this.owner = owner;
		this.enrichissements=1;
	}

	@Override
	public VarType getType() {
		return type;
	}

	public IAST getVar() {
		return var;
	}
	public void setVar(ASTVariable v) {
		var=v;
	}
	public IAST getAffectation() {
		return affectation;
	}
	public void setAffectation(AST a) {
		affectation=a;
	}
	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		var.visit(sb);
		sb.append(" = ");
		affectation.visit(sb);
		sb.append(";\n");

	}

	@Override
	public String getNom() {
		return this.nom;
	}

}
