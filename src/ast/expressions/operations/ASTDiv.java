package ast.expressions.operations;

import ast.expressions.ASTConstante;
import ast.expressions.ASTExpr;
import enums.VarType;
import factories.Lexenv;
import factories.RandomProvider;

public class ASTDiv extends ASTOpBinaire {

	public ASTDiv(Object valeur) {
		super(valeur);
	}

	public ASTDiv(ASTExpr g, ASTExpr d) {
		super(g, d);
		if ((Integer) d.getValeur() == 0)
			this.valeur = 0;
		else
			this.valeur = (Integer) (g.getValeur()) / (Integer) d.getValeur();
	}

	@Override
	protected void initCotes(int div) {
		int g, d;
		if (div == 0) {
			g = 0;
			d = RandomProvider.nextInt(RandomProvider.nbRandom) + 1;
		} else {
			int rand = 1;
			// Si pas d'overflow au carré, on tire un random jusqu'a div
			if (Integer.MAX_VALUE / div > div) {
				int cpt = 0;
				do {
					rand = RandomProvider.nextInt(div) + 1;
					cpt++;
					// Peu de chance de tirer div fois 0, donc normalement on a une valeur
				} while ((rand < 0) && (cpt < div));
				// Au pire des cas on prend 1
				if (cpt == div)
					rand = 1;
			}
			// Sinon on part de div/2 et on décrémente jusqu'a suprimer l'overflow
			else {
				int temp = div / 2;
				do {
					if (temp == -1)
						break;
					rand = RandomProvider.nextInt(temp) + 1;
					temp--;
				} while ((Integer.MAX_VALUE / rand < div) && (rand < 0));
				// Si on ne trouve pas, on prend 1
				if (temp < 0) {
					rand = 1;
				}
			}

			// System.out.println(rand+" "+somme+" "+(somme*rand));
			g = div * rand;
			d = rand;
		}
		gauche = new ASTConstante(VarType.INT, Lexenv.getNewName(), g);
		droite = new ASTConstante(VarType.INT, Lexenv.getNewName(), d);
	}

}
