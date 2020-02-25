package enrichissement;

import java.util.ArrayList;
import java.util.List;

import ast.expressions.*;
import ast.expressions.operations.ASTDiv;
import ast.expressions.operations.ASTMult;
import ast.expressions.operations.ASTOp;
import ast.expressions.operations.ASTSous;
import ast.expressions.operations.ASTSum;
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
		if (a instanceof ASTVariable)  
			return true;
		if (a instanceof ASTAffect)
			return true;
		if (a instanceof ASTMalloc)
			return true;
		if (a instanceof ASTSum)
			return true;
		if (a instanceof ASTSous)
			return true;
		if (a instanceof ASTMult)
			return true;
		if (a instanceof ASTDiv)
			return true;
		return false;
		
	}
	
	
	//Stratégies d'enrichissement par AST
	
	public static void enrichissement() throws EnrichissementNotImplementedException, EnrichissementMissingException {
		System.out.println(enrichissables);
		enrichissement(enrichissables.get(RandomProvider.nextInt(enrichissables.size())));
	}
	public static void enrichissement(int a) throws EnrichissementNotImplementedException, EnrichissementMissingException {
		for (int i=0;i<a;i++) {
			enrichissement();
		}
	}
	
	public static void enrichissement(IAST a) throws EnrichissementNotImplementedException, EnrichissementMissingException{
		if (a instanceof ASTVariable)
			enrichissement((ASTVariable)a);
		if (a instanceof ASTAffect)
			enrichissement((ASTAffect)a);
		if (a instanceof ASTMalloc)
			enrichissement((ASTMalloc)a);
		//throw new EnrichissementNotImplementedException("L'enrichissement de cet AST n'est pas implémenté");
		
	}
	
	public static void enrichissement(ASTVariable a) throws EnrichissementMissingException{
		pop(a);
		switch (a.getType()) {
		case INT: 
			switch (RandomProvider.nextInt(nbEVar)) {
			case 0 : IAST o = a.getOwner();
					 o.enrichissement(a, ASTOp.getOperation(a.getValeur(), o));break;
			case 1 : IAST l = a.getOwner();
					 l.enrichissement(a, new ASTAffect(a.getType(),a.getNom(),a.getValeur(),l) );break;
			}
		}
		//new ASTVariable(null, null, a, a);
	}
	//Pour un affectation, on ne peut que enrichir la variable, puis on retire l'affectation de la liste d'enrichissable 
	public static void enrichissement(ASTAffect a) throws EnrichissementNotImplementedException, EnrichissementMissingException{
		enrichissement((IAST)a.getVar());
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
