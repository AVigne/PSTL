package ast;

import java.util.ArrayList;

import exceptions.EnrichissementMissingException;
import factories.RandomProvider;
import interfaces.IAST;

public class ASTProgram implements IAST {
	
	
	private ArrayList<IAST> explist = new ArrayList<>();
	private int nbEnrichissementsErr;
	private int nbEnrichissementsBr;
	
	public void addExp(IAST exp) {
		
		this.explist.add(exp);
	}
	
	public ASTProgram(int enrE,int enrB) {
		this.nbEnrichissementsErr = enrE;
		this.nbEnrichissementsBr = enrB;
	}
	
	@Override
	public void visit(StringBuffer sb) {
		//Biblio a ajouter au fur et a mesure si besoin
		sb.append("# include <stdlib.h>\n");
		sb.append("# include <stdio.h>\n");
		sb.append("# include <string.h>\n");
		
		sb.append("/* \nnombre d'enrichissements des erreurs : "+nbEnrichissementsErr+"\n");
		sb.append("nombre d'enrichissements du bruit : "+nbEnrichissementsBr+"\n");
		sb.append("seed : "+RandomProvider.getSeed()+"\n*/\n");
		
		sb.append("int main(){\n");
		for(int i = 0; i < explist.size(); i++) {
			//sb.append("\t");
			explist.get(i).enrichissement(nbEnrichissementsErr);
			explist.get(i).visit(sb);
		}
		sb.append("return 0;\n}");
	}

	@Override
	public void enrichissement(IAST old, IAST nouveau) throws EnrichissementMissingException {
		// TODO Auto-generated method stub
		
	}

}
