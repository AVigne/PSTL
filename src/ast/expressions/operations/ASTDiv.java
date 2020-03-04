package ast.expressions.operations;

import java.util.ArrayList;

import ast.AST;
import ast.expressions.ASTConstante;
import ast.expressions.ASTExpr;
import ast.expressions.ASTVariable;
import ast.statement.ASTAffect;
import ast.statement.ASTDeclaration;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Enrichissement;
import factories.Lexenv;
import factories.RandomProvider;
import interfaces.IAST;

public class ASTDiv extends ASTOpBinaire {

	public ASTDiv(Object valeur, IAST owner) {
		super(valeur,owner);
		}

	public ASTDiv(ASTExpr g, ASTExpr d, IAST owner) {
		super(g, d, owner);
		this.valeur = (Integer) (g.getValeur()) / (Integer) d.getValeur();
	}

	@Override
	protected void initCotes(int somme) {
		int g, d;
		if (somme == 0) {
			g = 0;
			d = 1;
		} else {
			// gauche et droite aléatoires
			
			int rand = RandomProvider.nextInt((Integer.MAX_VALUE - 1)/ somme) + 1;
			//System.out.println(rand+" "+somme+" "+(somme*rand));
			g = somme * rand;
			d = rand;
		}
		gauche = new ASTConstante(VarType.INT, Lexenv.getNewName(), g, this);
		droite = new ASTConstante(VarType.INT, Lexenv.getNewName(), d, this);	
	}


}
