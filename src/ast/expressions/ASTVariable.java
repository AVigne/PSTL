package ast.expressions;

import enums.VarType;

public class ASTVariable extends ASTExpr {
	protected VarType pointee;

	public ASTVariable(VarType type, String nom, Object valeur) {
		this.typeretour = type;
		this.nom = nom;
		this.valeur = valeur;
		switch (type) {
		case PINT:
			pointee = VarType.INT;
			break;
		default:
			break;
		}
		this.enrichissements = 1;

	}

	public VarType getType() {
		return typeretour;
	}

	public String getNom() {
		return nom;
	}

	public Object getValeur() {
		return valeur;
	}

	public VarType getPointee() {
		return pointee;
	}

	@Override
	public void visit(StringBuffer sb) {
		sb.append(this.nom);
	}

}
