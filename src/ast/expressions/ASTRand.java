package ast.expressions;

import ast.expressions.operations.ASTOpBinaire;
import ast.expressions.operations.ASTSous;
import ast.expressions.operations.ASTSum;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Lexenv;
import factories.RandomProvider;

public class ASTRand extends ASTExpr{
	protected ASTOpBinaire rand;
	public ASTRand(VarType type, String nom, Object valeur) {
		this.typeretour = type;
		this.nom = nom;
		this.valeur = valeur;
		//Si valeur a 0, la borne supérieur est aléatoire
		if ((Integer) valeur == 0){
			ASTExpr inf = new ASTConstante(type, Lexenv.getNewName(), valeur);
			ASTExpr sup = new ASTConstante(type, Lexenv.getNewName(), RandomProvider.nextInt(RandomProvider.nbRandom));
			ASTConstante un = new ASTConstante(type, Lexenv.getNewName(), 1);
			ASTExpr inf2 = new ASTConstante(type, Lexenv.getNewName(), valeur);
			ASTSous sous= new ASTSous(sup,inf);
			ASTSum plusun = new ASTSum (sous,un);
			rand= new ASTSum(plusun,inf2);
		}
		else {
			//System.out.println(valeur);
			int valrand;
			int cpt=0;
			//On gère le potentiel overflow de la borne sup
			do {
				valrand = RandomProvider.nextInt((Integer)valeur-cpt);
				cpt++;
			}while(valrand>Integer.MAX_VALUE-(Integer)valeur);
			ASTExpr inf = new ASTConstante(type, Lexenv.getNewName(), valrand);
			ASTExpr sup = new ASTConstante(type, Lexenv.getNewName(), valrand+(Integer)valeur);//RandomProvider.nextInt(Integer.MAX_VALUE-(Integer) valeur)+(Integer) valeur);
			ASTConstante un = new ASTConstante(type, Lexenv.getNewName(), 1);
			ASTExpr inf2 = new ASTConstante(type, Lexenv.getNewName(), valrand);
			ASTSous sous= new ASTSous(sup,inf);
			ASTSum plusun = new ASTSum (sous,un);
			rand= new ASTSum(plusun,inf2);
		}
		this.enrichissements=rand.getEnrichissements();
	}
	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		sb.append("(rand() % ");
		rand.visit(sb);
		sb.append(")");
	}

	@Override
	public String getNom() {
		return this.getNom();
	}
	@Override
	public void addDeclaree(String n) {
		if (!declaree.contains(n))
			this.declaree.add(n);
		rand.addDeclaree(n);
	}
	@Override
	public void addUsable(String n) {
		if (!usable.contains(n))
			this.usable.add(n);
		rand.addUsable(n);
	}
	/***
	 * Retourne l'opération a la racine du random
	 * @return
	 */
	public ASTOpBinaire getRand() {
		return rand;
	}
	/***
	 * Permet de set l'opération a la racine du random. Utile pour l'enrichissement.
	 * @param i
	 */
	public void setRand(ASTOpBinaire i) {
		rand=i;
	}
}
