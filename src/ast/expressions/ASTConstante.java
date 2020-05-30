package ast.expressions;

import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;

public class ASTConstante extends ASTExpr {

	public ASTConstante(VarType type, String nom, Object valeur) {
		this.typeretour = type;
		this.nom = nom;
		this.valeur = valeur;
		this.enrichissements = 1;
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
