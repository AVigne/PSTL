package ast.errors;

import java.util.ArrayList;

import ast.AST;
import ast.expressions.ASTConstante;
import ast.expressions.ASTVariable;
import ast.expressions.operations.ASTDiv;
import ast.statement.ASTAffect;
import ast.statement.ASTDeclaration;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import factories.Lexenv;
import factories.RandomProvider;
import interfaces.IAST;

public class ASTErreurDivZero {
	ASTAffect div;
	public ASTErreurDivZero(VarType type, String nom, Object valeur,IAST owner) throws EnrichissementMissingException {
		super();
		Lexenv.toggleError(true);
		ASTDiv d = new ASTDiv(new ASTConstante(VarType.INT, Lexenv.getNewName(), RandomProvider.nextInt(150),null),new ASTConstante(VarType.INT, Lexenv.getNewName(), 0, null),null);
		div = new ASTAffect(VarType.INT,Lexenv.getNewName(),d,null);
		d.setEnrichissements(2);
		div.setEnrichissements(2);
		Lexenv.toggleError(false);
	}
	public ArrayList<AST> getAst(){
		ArrayList<AST> a = new ArrayList<>();
		a.add(new ASTDeclaration(div.getType(),div.getNom(),div.getOwner()));
		a.add(div);
		return a;
	}
}
