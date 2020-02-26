package ast.expressions;

import java.util.ArrayList;

import ast.AST;
import ast.statement.ASTDeclaration;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Enrichissement;
import interfaces.IAST;

public class ASTAffect extends ASTExpr {

	protected AST var;
	protected VarType type;
	protected String nom;

	public ASTAffect(VarType type, String nom, Object valeur, IAST owner) {
		this.valeur = valeur;
		this.nom = nom;
		this.type = type;
		var = new ASTVariable(type, nom, valeur, this);
		this.owner = owner;
		Enrichissement.add(this);
	}

	@Override
	public VarType getType() {
		return type;
	}

	public IAST getVar() {
		return var;
	}

	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		// On récupère la litse des affectations a l'interieur
		ArrayList<AST> affects = getAffect(new ArrayList<AST>());
		for (AST i : affects) {
			// Si l'affectation n'as pas encore été ajoutée a sb
			if (!i.isaffectee()) {
				// On la déclare (déclarations multiples gérées dans l'ASTDeclaration
				ASTDeclaration a = new ASTDeclaration(i.getType(), i.getNom(), i.getOwner());
				a.visit(sb);
				// On dit qu'elle a été affectée, puis on la visit, permet d'éviter les visites
				// multiples
				i.affectee();
				i.visit(sb);
			}
			// Si on se visite soi même, on set un boolean pour éviter le rajout multiple a
			// sb
			if (i.equals(this)) {
				visitee = true;
			}

		}
		// si on a déjà été visité, on ne rajoute pas une deuxieme fois
		if (!visitee) {
			if (!var.isaffectee()) {
				sb.append(this.nom + " = ");
				if (var instanceof ASTAffect)
					sb.append(var.getNom());
				else
					var.visit(sb);
				sb.append(";\n");
			}
		}

	}

	@Override
	public void enrichissement(IAST old, IAST nouveau) throws EnrichissementMissingException {
		/*
		 * System.out.println("Affect"); System.out.println(var);
		 * System.out.println(old);
		 */
		if (var.equals(old)) {
			Enrichissement.pop(var);
			var = (AST) nouveau;
		} else {
			throw new EnrichissementMissingException("L'expression enrichie n'est pas celle de l'affectation");
		}
		Enrichissement.pop(this);
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	// Récupère la liste des affectations récursivement
	public ArrayList<AST> getAffect(ArrayList<AST> a) {
		if (!this.isaffectee())
			a.add(this);
		if (!var.isaffectee())
			return var.getAffect(a);
		return a;
	}

}
