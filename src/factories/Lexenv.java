package factories;

import java.util.ArrayList;

import interfaces.ILexenv;

public class Lexenv implements ILexenv {
	private ArrayList<String> vars;
	public Lexenv() {
		vars= new ArrayList<String>();
	}
	public void addVar(String v) {
		vars.add(v);
	}
	public ArrayList<String> getVars(){
		return vars;
	}
	
}
