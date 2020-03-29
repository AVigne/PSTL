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

	public ASTDiv(Object valeur) {
		super(valeur);
		}

	public ASTDiv(ASTExpr g, ASTExpr d) {
		super(g, d);
		if ((Integer)d.getValeur()==0)
			this.valeur=0;
		else
			this.valeur = (Integer) (g.getValeur()) / (Integer) d.getValeur();
	}

	@Override
	protected void initCotes(int div) {
		int g, d;
		if (div == 0) {
			g = 0;
			d = 1;
		} else {
			// gauche et droite aléatoires
			
			int rand = RandomProvider.nextInt((Integer.MAX_VALUE - 1)/ div) + 1;
			//System.out.println(rand+" "+somme+" "+(somme*rand));
			g = div * rand;
			d = rand;
		}
		gauche = new ASTConstante(VarType.INT, Lexenv.getNewName(), g);
		droite = new ASTConstante(VarType.INT, Lexenv.getNewName(), d);	
	}


}
