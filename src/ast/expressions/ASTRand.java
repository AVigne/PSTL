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
	protected Integer infval,supval;
	public ASTRand(VarType type, String nom, Object valeur) {
		this.typeretour = type;
		this.nom = nom;
		this.valeur = valeur;
		//Si valeur a 0, la borne supérieur est aléatoire
		if ((Integer) valeur <2){
			ASTExpr inf = new ASTConstante(type, Lexenv.getNewName(), valeur);
			infval=(Integer)valeur;
			int s = RandomProvider.nextInt(RandomProvider.nbRandom)+2;
			ASTExpr sup = new ASTConstante(type, Lexenv.getNewName(), s);
			supval=s;
			rand= new ASTSous(sup,inf);
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
			infval=valrand;
			ASTExpr sup = new ASTConstante(type, Lexenv.getNewName(), valrand+(Integer)valeur);//RandomProvider.nextInt(Integer.MAX_VALUE-(Integer) valeur)+(Integer) valeur);
			supval=valrand+(Integer)valeur;
			rand= new ASTSous(sup,inf);
			
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
	public int getInfval() {
		return infval;
	}
}
