package ast.expressions;

import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Enrichissement;
import factories.RandomProvider;
import interfaces.IAST;

public class ASTConstante extends ASTExpr{
	
	
	public ASTConstante(VarType type, String nom, Object valeur) {
		this.typeretour = type;
		this.nom = nom;
		this.valeur = valeur;
		this.enrichissements=1;

	}
	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		switch (RandomProvider.nextInt(2)) {
			case 0: sb.append(this.valeur); break;
			case 1: 
				int borneinf = RandomProvider.nextInt((Integer)valeur+1);
				int bornsup = RandomProvider.nextInt((Integer)valeur+1)+ ((Integer)valeur);
				sb.append("(rand() % ("+bornsup+" - "+borneinf+" + 1))");
				break;
		}
	}

	@Override
	public String getNom() {
		return nom;
	}


}
