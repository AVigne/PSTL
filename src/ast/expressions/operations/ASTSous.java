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

public class ASTSous extends ASTOp {

	public ASTSous(Object valeur, IAST owner) {
		super(valeur,owner);
	}
	
	public ASTSous(ASTExpr g, ASTExpr d, IAST owner) {
		super(g,d,owner);
		this.valeur=(Integer)g.getValeur()-(Integer)d.getValeur();
	}
	
	@Override
	protected void initCotes(int somme) {
		int g,d;
		if (somme==0) {
			g=0;
			d=0;
		}
		else{
			//gauche et droite aléatoires
			int rand = 0;
			if(somme < Integer.MAX_VALUE )
				rand = RandomProvider.nextInt(Integer.MAX_VALUE - somme);
			g = somme+rand;
			d = rand;
		}
		gauche = new ASTVariable(VarType.INT, Lexenv.getNewName(), g, this);
		droite = new ASTVariable(VarType.INT, Lexenv.getNewName(), d, this);
	}
}
