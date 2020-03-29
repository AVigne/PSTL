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

public class ASTMult extends ASTOpBinaire {

	public ASTMult(Object valeur) {
		super(valeur);
	}

	public ASTMult(ASTExpr g, ASTExpr d) {
		super(g, d);
		this.valeur = (Integer) g.getValeur() * (Integer) d.getValeur();
	}

	@Override
	protected void initCotes(int produit) {
		int g, d;
		if (produit == 0) {
			g = 0;
			d = 0;
		} else {
			// gauche et droite aléatoires
			int rand = ((int)Math.sqrt(produit)) + 1;
			int cpt = 0;
			int maxIter = 50;
			
			/* On regarde les nombres entre sqrt(somme) et sqrt(somme) - 50 
			 * on prend le premier diviseur de somme, si on en trouve aucun on multiplie somme par 1
			 */
			while ((produit % rand != 0) && (cpt < maxIter)) {
				rand--;
				cpt++;
			}
			if (cpt == maxIter) {
				g = produit;
				d = 1;
			} else {
				g = rand;
				d = produit / rand;
			}
		}
		gauche = new ASTConstante(VarType.INT, Lexenv.getNewName(), g);
		droite = new ASTConstante(VarType.INT, Lexenv.getNewName(), d);

	}


}
