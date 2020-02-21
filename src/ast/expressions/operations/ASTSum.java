package ast.expressions.operations;

import ast.AST;
import ast.expressions.ASTExpr;
import ast.expressions.ASTVariable;
import enrichissement.Enrichissement;
import enums.VarType;
import factories.Lexenv;
import factories.RandomProvider;
import interfaces.IAST;

public class ASTSum extends ASTOp{
	public ASTSum(Object valeur,IAST owner) {
		super();
		int g, d;
		int somme= (Integer)valeur;
		if (somme==0) {
			g=0;
			d=0;
		}
		else {
			//gauche et droite aléatoires
			g= RandomProvider.nextInt(somme);
			d=somme-g;
		}
		gauche = new ASTVariable(VarType.INT, Lexenv.getNewName(), g, this);
		droite = new ASTVariable(VarType.INT, Lexenv.getNewName(), d, this);
		this.owner=owner;
		this.valeur=valeur;
	}
	public ASTSum(ASTExpr g, ASTExpr d, IAST owner) {
		super(g,d,owner);
		this.valeur=(Integer)g.getValeur()+(Integer)d.getValeur();
	}
	@Override
	public void visit(StringBuffer sb) {
		// TODO Auto-generated method stub
		
	}
}
