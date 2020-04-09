package ast.expressions.operations;

import ast.expressions.ASTConstante;
import ast.expressions.ASTExpr;
import enums.VarType;
import factories.Lexenv;
import factories.RandomProvider;

public class ASTSous extends ASTOpBinaire {

	public ASTSous(Object valeur) {
		super(valeur);
	}
	
	public ASTSous(ASTExpr g, ASTExpr d) {
		super(g,d);
		this.valeur=(Integer)g.getValeur()-(Integer)d.getValeur();
	}
	
	@Override
	protected void initCotes(int difference) {
		int g,d;
		if (difference==0) {
			int i = RandomProvider.nextInt(RandomProvider.nbRandom);
			g=i;
			d=i;
		}
		else{
			//gauche et droite aléatoires
			int rand = 0;
			int cpt=0;
			do {
				rand = RandomProvider.nextInt(difference);
				cpt++;
			}while((difference>Integer.MAX_VALUE-rand)&&(cpt<20));
			if (cpt>=20) {
				rand=0;
			}
			g = difference+rand;
			d = rand;
		}
		gauche = new ASTConstante(VarType.INT, Lexenv.getNewName(), g);
		droite = new ASTConstante(VarType.INT, Lexenv.getNewName(), d);
	}
}
