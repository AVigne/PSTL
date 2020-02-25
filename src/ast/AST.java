package ast;

import java.util.ArrayList;
import java.util.List;

import enums.VarType;
import interfaces.IAST;

public abstract class AST implements IAST {
	protected IAST owner;
	protected boolean affectee=false;
	protected String nom="";
	public VarType getType() {
		return null;
	}
	public IAST getOwner() {
		return owner;
	}
	public ArrayList<AST> getAffect(ArrayList<AST> a) {
		return a;
	}
	public void affectee() {
		affectee=true;
	}
	public boolean isaffectee() {
		return affectee;
	}
}
