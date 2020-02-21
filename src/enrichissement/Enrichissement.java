package enrichissement;

import java.util.ArrayList;
import java.util.List;

import ast.expressions.*;
import ast.statement.*;
import ast.statement.memory.ASTMalloc;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.RandomProvider;
import interfaces.IAST;

public class Enrichissement {
	public static List<IAST> enrichissables;
	public static int nbEVar=2;
	public static void init(){
		enrichissables= new ArrayList<IAST>();
	}
	
	//Permet de n'ajouter que les AST dont l'enrichissement est implémenté
	public static void add(IAST a) {
		if (isEnrichissable(a)) {
			enrichissables.add(a);
		}
	}
	
	public static void pop(IAST a) {
		enrichissables.remove(a);
	}
	
	//Rajouter a la liste les AST enrichissables
	//Permet d'en rajouter au fil de l'implémentatio
	public static boolean isEnrichissable(IAST a) {
		switch (a.getClass().getCanonicalName()) {
		case "ASTVariable" : return true;
		case "ASTAffect" : return true;
		case "ASTMalloc" : return true;
		case "ASTOp" : return true;
		case "ASTSum" : return true;
		case "ASTSous" : return true;
		case "ASTMult" : return true;
		case "ASTDiv" : return true;
		default : return false;
		}
	}
	
	
	//Stratégies d'enrichissement par AST
	
	public static void enrichissement() throws EnrichissementNotImplementedException, EnrichissementMissingException {
		enrichissement(enrichissables.get(RandomProvider.nextInt(enrichissables.size())));
	}
	
	public static void enrichissement(IAST a) throws EnrichissementNotImplementedException, EnrichissementMissingException{
		switch (a.getClass().getCanonicalName()) {
		case "ASTVariable" : enrichissement((ASTVariable)a);
		case "ASTAffect" : enrichissement((ASTAffect)a);
		case "ASTMalloc" : enrichissement((ASTMalloc)a);
		default : throw new EnrichissementNotImplementedException("L'enrichissement de cet AST n'est pas implémenté");
		}
	}
	
	public static void enrichissement(ASTVariable a){
		pop(a);
		switch (a.getType()) {
		case INT: 
			switch (RandomProvider.nextInt(nbEVar)) {
			case 0 : a.getOwner();
			}
		}
		new ASTVariable(null, null, a, a);
	}
	//Pour un affectation, on ne peut que enrichir la variable, puis on retire l'affectation de la liste d'enrichissable 
	public static void enrichissement(ASTAffect a){
		enrichissement((ASTVariable)a.getVar());
		pop(a);
	}
	//Pour le moment, on transforme juste le num en affectation, que l'on peu enrichir
	//Voir pour modifier plus tard pour gérer le pointeur
	public static void enrichissement(ASTMalloc a) throws EnrichissementMissingException{
		ASTVariable num = (ASTVariable)a.getNum();
		a.enrichissement(num, new ASTAffect(num.getType(),num.getNom(), num.getValeur(), num.getOwner()));
		pop(a);
	}


	
}
