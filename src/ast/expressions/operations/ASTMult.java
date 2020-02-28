package ast.expressions.operations;

import java.util.ArrayList;

import ast.AST;
import ast.expressions.ASTAffect;
import ast.expressions.ASTExpr;
import ast.expressions.ASTVariable;
import ast.statement.ASTDeclaration;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Enrichissement;
import factories.Lexenv;
import factories.RandomProvider;
import interfaces.IAST;

public class ASTMult extends ASTOp {

	public ASTMult(Object valeur, IAST owner) {
		super(valeur,owner);
	}

	public ASTMult(ASTExpr g, ASTExpr d, IAST owner) {
		super(g, d, owner);
		this.valeur = (Integer) g.getValeur() * (Integer) d.getValeur();
	}

	@Override
	protected void initCotes(int somme) {
		int g, d;
		if (somme == 0) {
			g = 0;
			d = 0;
		} else {
			// gauche et droite aléatoires
			int rand = ((int)Math.sqrt(somme)) + 1;
			int cpt = 0;
			int maxIter = 50;
			
			/* On regarde les nombres entre sqrt(somme) et sqrt(somme) - 50 
			 * on prend le premier diviseur de somme, si on en trouve aucun on multiplie somme par 1
			 */
			while ((somme % rand != 0) && (cpt < maxIter)) {
				rand--;
				cpt++;
			}
			if (cpt == maxIter) {
				g = somme;
				d = 1;
			} else {
				g = rand;
				d = somme / rand;
			}
		}
		gauche = new ASTVariable(VarType.INT, Lexenv.getNewName(), g, this);
		droite = new ASTVariable(VarType.INT, Lexenv.getNewName(), d, this);

	}


}
