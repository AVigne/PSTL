package ast;

import java.util.ArrayList;
import java.util.List;

import enums.VarType;
import interfaces.IAST;

public abstract class AST implements IAST {
	protected IAST owner;
	protected boolean affectee = false;
	protected String nom = "";
	protected boolean visitee = false;

	public VarType getType() {
		return null;
	}

	public IAST getOwner() {
		return owner;
	}

	public ArrayList<AST> getAffect(ArrayList<AST> a) {
		return a;
	}

	/**
	 * Permet de dire qu'un AST a été affecté
	 */
	public void affectee() {
		affectee = true;
	}

	/**
	 * Retourne si un AST a été affecté
	 *
	 */
	public boolean isaffectee() {
		return affectee;
	}

	/**
	 * Retourne si un AST a été visité (évite les doublons)
	 * 
	 * @return
	 */
	public boolean isvisitee() {
		return visitee;
	}

	/**
	 * Permet de dire qu'un AST a été visité
	 */
	public void visiter() {
		visitee = true;
	}
}
