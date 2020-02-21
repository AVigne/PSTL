package ast.expressions.operations;

import ast.AST;
import ast.expressions.ASTExpr;
import ast.expressions.ASTVariable;
import enrichissement.Enrichissement;
import enums.VarType;
import factories.Lexenv;
import factories.RandomProvider;
import interfaces.IAST;

public class ASTDiv extends ASTOp {

	public ASTDiv(Object valeur, IAST owner) {
		super();
		this.valeur=valeur;
		this.owner=owner;
		int somme= (Integer)valeur;
		int g,d;
		if (somme==0) {
			g=0;
			d=1;
		}
		else {
			//gauche et droite aléatoires
			int rand = RandomProvider.nextInt(somme)+1;
			if ((rand > (Integer.MAX_VALUE / somme))) {
				g=somme;
				d=1;
			}
			else {
				g=somme*rand;
				d=rand;
			}
		}

		gauche = new ASTVariable(VarType.INT, Lexenv.getNewName(), g, this);
		droite = new ASTVariable(VarType.INT, Lexenv.getNewName(), d, this);
	}
	public ASTDiv(ASTExpr g, ASTExpr d, IAST owner) {
		super(g,d,owner);
		this.valeur=(Integer)(g.getValeur())/(Integer)d.getValeur();
	}
	@Override
	public void visit(StringBuffer sb) {
		// TODO Auto-generated method stub
		
	}
}
