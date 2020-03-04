package ast.expressions;

import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Enrichissement;
import interfaces.IAST;

public class ASTConstante extends ASTExpr{
	
	
	public ASTConstante(VarType type, String nom, Object valeur, IAST owner) {
		this.typeretour = type;
		this.nom = nom;
		this.valeur = valeur;
		this.owner = owner;
		this.enrichissements=1;

	}
	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		sb.append(this.valeur);
	}

	@Override
	public String getNom() {
		return nom;
	}


}
