package ast;

import java.util.ArrayList;
import java.util.List;

import enums.VarType;
import interfaces.IAST;
import structures.ReturnEnrichissement;

public abstract class AST implements IAST {
	protected IAST owner;
	protected String nom = "";
	protected int enrichissements=0;
	public VarType getType() {
		return null;
	}

	public IAST getOwner() {
		return owner;
	}

	@Override
	public void setEnrichissements(int i) {
		this.enrichissements=i;
	}
	public void upEnrichissement(int i) {
		this.enrichissements+=i;
	}
	@Override
	public int getEnrichissements() {
		return enrichissements;
	}

	public void enrichirV2() {
		
	}
}
