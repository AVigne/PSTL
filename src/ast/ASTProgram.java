package ast;

import java.util.ArrayList;

import ast.functions.ASTFunction;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Enrichissement;
import factories.RandomProvider;
import interfaces.IAST;

public class ASTProgram implements IAST {

	private ArrayList<IAST> explist = new ArrayList<>();
	private ArrayList<ASTFunction> funct = new ArrayList<>();
	private int nbEnrichissementsErr;
	private int nbEnrichissementsBr;
	private int enrichissements=0;
	public void addExp(IAST exp) {

		this.explist.add(exp);
	}

	public ASTProgram(int enrE, int enrB) {
		this.nbEnrichissementsErr = enrE;
		this.nbEnrichissementsBr = enrB;
	}
	public ArrayList<IAST> getexplist(){
		return explist;
	}
	public ArrayList<ASTFunction> getfunct(){
		return funct;
	}
	@Override
	public void visit(StringBuffer sb) throws EnrichissementNotImplementedException, EnrichissementMissingException {
		// Biblio a ajouter au fur et a mesure si besoin
		sb.append("# include <stdlib.h>\n");
		sb.append("# include <stdio.h>\n");
		sb.append("# include <string.h>\n");

		sb.append("/* \nnombre d'enrichissements des erreurs : " + nbEnrichissementsErr + "\n");
		sb.append("nombre d'enrichissements du bruit : " + nbEnrichissementsBr + "\n");
		sb.append("seed : " + RandomProvider.getSeed() + "\n*/\n");

		sb.append("int main(){\n");
		Enrichissement.enrichirV2(nbEnrichissementsErr);

		for (int i = 0; i < explist.size(); i++) {
			explist.get(i).visit(sb);
		}
		sb.append("return 0;\n}\n");
		for (int j = 0; j < funct.size(); j++) {
			funct.get(j).visit(sb);
			sb.append("\n");
		}
	}

	@Override
	public String getNom() {
		return null;
	}


	@Override
	public void setEnrichissements(int i) {
		enrichissements=i;
	}

	@Override
	public int getEnrichissements() {
		return enrichissements;
	}
	
	public int getPosition(AST a) {
		return explist.indexOf(a);
	}

	@Override
	public void addDeclaree(String n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<String> getDeclaree() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUsable(String n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<String> getUsable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void popUsable(String n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fuseDeclaree(ArrayList<String> l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fuseUsable(ArrayList<String> l) {
		// TODO Auto-generated method stub
		
	}
	

}
