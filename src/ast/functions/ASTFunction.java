package ast.functions;

import java.util.ArrayList;

import ast.AST;
import ast.expressions.ASTExpr;
import ast.expressions.operations.ASTOpBinaire;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Lexenv;
import interfaces.IAST;

public class ASTFunction extends AST{
	protected Object valeur;
	protected ArrayList<IAST> instr = new ArrayList<>();
	protected ArrayList<ASTExpr> params = new ArrayList<>();
	//Construit une fonction sans argument qui revoie valeur
	public ASTFunction(String nom, Object valeur) {
		Lexenv.toggleFun(false);
		this.nom=nom;
		this.valeur=valeur;
		Lexenv.toggleLocal(true);
		ASTReturn r = new ASTReturn(ASTOpBinaire.getRandomOperation(valeur));
		instr.add(r);
		Lexenv.toggleLocal(false);
		this.enrichissements=instr.get(0).getEnrichissements();
	}
	
	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		if (valeur instanceof Integer)
			sb.append("int ");
		sb.append(nom+"(");
		//Si parametres (pas implémenté pour le moment
		if (!params.isEmpty()) {
			for (ASTExpr i : params) {
				if (params.get(0)!=i) {
					sb.append(",");
				}
				switch (i.getType()) {
				case INT:
					sb.append("int "+i.getNom());
				default:
					break;
				}
			}
		}
		sb.append("){\n");

		for (IAST j : instr) {
			j.visit(sb);
		}
		sb.append("\n}\n");
	}
	

	@Override
	public String getNom() {
		return this.nom;
	}
	public Object getValeur() {
		return valeur;
	}
	public ArrayList<IAST> getInstr(){
		return instr;
	}
	@Override
	public void addDeclaree(String n) {
		if (!declaree.contains(n))
			this.declaree.add(n);
		for (IAST i : instr){
			i.addDeclaree(n);
		}
	}
	@Override
	public void addUsable(String n) {
		if (!usable.contains(n))
			this.usable.add(n);
		for (IAST i : instr){
			i.addUsable(n);
		}
	}
}
