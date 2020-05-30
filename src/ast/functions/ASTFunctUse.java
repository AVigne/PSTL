package ast.functions;

import java.util.ArrayList;

import ast.expressions.ASTExpr;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;

public class ASTFunctUse extends ASTExpr {
	protected ArrayList<ASTExpr> params = new ArrayList<>();
	protected ASTFunction f;

	public ASTFunctUse(String nom, Object valeur, ASTFunction f) {
		this.nom = nom;
		this.valeur = valeur;
		this.f = f;
		this.enrichissements = f.getEnrichissements();
	}

	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		sb.append(f.getNom() + "(");
		// Si parametres (pas implémenté pour le moment)
		if (!params.isEmpty()) {
			for (ASTExpr i : params) {
				if (params.get(0) != i) {
					sb.append(",");
				}
				switch (i.getType()) {
				case INT:
					sb.append("int " + i.getNom());
				default:
					break;
				}
			}
		}
		sb.append(")");

	}

	@Override
	public String getNom() {
		return nom;
	}

	public ASTFunction getF() {
		return f;
	}

	public void setF(ASTFunction fun) {
		f = fun;
	}

}
