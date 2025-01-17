package ast.expressions.operations;

import ast.expressions.ASTConstante;
import ast.expressions.ASTExpr;
import enums.VarType;
import factories.Lexenv;
import factories.RandomProvider;

public class ASTSum extends ASTOpBinaire {

	public ASTSum(Object valeur) {
		super(valeur);
	}

	public ASTSum(ASTExpr g, ASTExpr d) {
		super(g, d);
		this.valeur = (Integer) g.getValeur() + (Integer) d.getValeur();
	}

	@Override
	protected void initCotes(int somme) {
		int g, d;
		if (somme == 0) {
			g = 0;
			d = 0;
		} else {
			// gauche et droite aléatoires
			g = RandomProvider.nextInt(somme);
			d = somme - g;
		}
		gauche = new ASTConstante(VarType.INT, Lexenv.getNewName(), g);
		droite = new ASTConstante(VarType.INT, Lexenv.getNewName(), d);
	}

}
