package ast;

import java.util.ArrayList;

import enums.VarType;
import interfaces.IAST;

public abstract class AST implements IAST {
	protected String nom = "";
	protected int enrichissements=0;
	protected ArrayList<String> declaree =new ArrayList<>();
	protected ArrayList<String> usable= new ArrayList<>();
	public VarType getType() {
		return null;
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
	public void addDeclaree(String n) {
		if (!declaree.contains(n))
			declaree.add(n);
	}
	public ArrayList<String> getDeclaree() {
		return declaree;
	}
	public void addUsable(String n) {
		if (!usable.contains(n))
			usable.add(n);
	}
	public ArrayList<String> getUsable(){
		return usable;
	}
	public void popUsable(String n) {
		usable.remove(n);
	}
}
