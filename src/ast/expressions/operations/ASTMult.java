package ast.expressions.operations;

import java.util.ArrayList;

import ast.AST;
import ast.expressions.ASTExpr;
import ast.expressions.ASTVariable;
import ast.statement.ASTDeclaration;
import enrichissement.Enrichissement;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Lexenv;
import factories.RandomProvider;
import interfaces.IAST;

public class ASTMult extends ASTOp {

	public ASTMult(Object valeur, IAST owner) {
		super();
		this.valeur=valeur;
		this.owner=owner;
		int somme= (Integer)valeur;
		int g,d;
		if (somme==0) {
			g=0;
			d=0;
		} 
		else {
			//gauche et droite aléatoires
			int rand = RandomProvider.nextInt(somme)+1;
			int cpt=0;
			int maxIter = 10;
	
			//On tire un diviseur de la valeur, 100 fois au plus (afin de ne pas boucler jusqu'a trouver 1)
			//Regle le soucis des grands nombres premeiers entre autre
			while((somme%rand!=0)&&(cpt<maxIter)){
				rand = RandomProvider.nextInt(somme)+1;
				cpt++;
			}
			if (cpt==maxIter || (rand > (Integer.MAX_VALUE / somme))) {
				g=somme;
				d=1;
			}
			else {
				g=rand;
				d=somme/rand;
			}
		}
		gauche = new ASTVariable(VarType.INT, Lexenv.getNewName(), g, this);
		droite = new ASTVariable(VarType.INT, Lexenv.getNewName(), d, this);
		
	}
	public ASTMult(ASTExpr g, ASTExpr d, IAST owner) {
		super(g,d,owner);
		this.valeur=(Integer)g.getValeur()*(Integer)d.getValeur();
	}
	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		ArrayList<AST> affects = getAffect(new ArrayList<AST>());
		for (AST i : affects) {
			
			if (!i.isaffectee()) {
				ASTDeclaration a = new ASTDeclaration(i.getType(),i.getNom(),i.getOwner());
				a.visit(sb);
				i.affectee();
				i.visit(sb);

				if (i.equals(this))
					visitee=true;
			}
		}
		if (!visitee) {
			sb.append("(");
			gauche.visit(sb);
			sb.append(" * ");
			droite.visit(sb);
			sb.append(")");
		}
	}
}
