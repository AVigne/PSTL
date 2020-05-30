package ast.errors;

import java.util.ArrayList;

import ast.AST;
import ast.expressions.ASTConstante;
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

	public ASTErreurDivZero(VarType type, String nom, Object valeur, IAST owner) throws EnrichissementMissingException {
		super();
		Lexenv.toggleError(true);
		ASTDiv d = new ASTDiv(
				new ASTConstante(VarType.INT, Lexenv.getNewName(), RandomProvider.nextInt(RandomProvider.nbRandom)),
				new ASTConstante(VarType.INT, Lexenv.getNewName(), 0));
		div = new ASTAffect(VarType.INT, Lexenv.getNewName(), d);
		d.setEnrichissements(2);
		div.setEnrichissements(2);
		d.setTextError("/*erreur ici*/");
		Lexenv.toggleError(false);
	}

	public ArrayList<AST> getAst() {
		ArrayList<AST> a = new ArrayList<>();
		ASTDeclaration dec = new ASTDeclaration(div.getType(), div.getNom());
		dec.addDeclaree(dec.getNom());
		a.add(dec);
		div.addDeclaree(dec.getNom());
		a.add(div);
		return a;
	}
}
