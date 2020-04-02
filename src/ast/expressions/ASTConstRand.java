package ast.expressions;

import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Lexenv;
import factories.RandomProvider;

public class ASTConstRand extends ASTExpr{
	protected ASTExpr inf,sup;
	public ASTConstRand(VarType type, String nom, Object valeur) {
		this.typeretour = type;
		this.nom = nom;
		this.valeur = valeur;
		this.enrichissements=1;
		if ((Integer) valeur == 0){
			inf = new ASTConstante(type, Lexenv.getNewName(), valeur);
			sup = new ASTConstante(type, Lexenv.getNewName(), valeur);
		}
		else {
			//System.out.println(valeur);
			inf = new ASTConstante(type, Lexenv.getNewName(), RandomProvider.nextInt((Integer) valeur));
			sup = new ASTConstante(type, Lexenv.getNewName(), RandomProvider.nextInt(Integer.MAX_VALUE-(Integer) valeur)+(Integer) valeur);
		}
		this.setEnrichissements(2);
		

	}
	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		if ((Integer)this.getValeur()==0){
			sb.append("(");
			sup.visit(sb);
			sb.append(" - ");
			inf.visit(sb);
			sb.append(")");

		}
		else {
			sb.append("(rand() % (");
			sup.visit(sb);
			sb.append(" - ");
			inf.visit(sb);
			sb.append(" + 1) + ");
			inf.visit(sb);
			sb.append(")");
		}
	}

	@Override
	public String getNom() {
		return nom;
	}
	
	public ASTExpr getInf() {
		return inf;
	}
	
	public ASTExpr getSup() {
		return sup;
	}
	
	public void setInf(ASTExpr a) {
		inf=a;
	}
	
	public void setSup(ASTExpr a) {
		sup=a;
	}
	
	@Override
	public void addDeclaree(String n) {
		if (!declaree.contains(n))
			this.declaree.add(n);
		inf.addDeclaree(n);
		sup.addDeclaree(n);
	}
	@Override
	public void addUsable(String n) {
		if (!usable.contains(n))
			this.usable.add(n);
		inf.addUsable(n);
		sup.addUsable(n);
	}
}
