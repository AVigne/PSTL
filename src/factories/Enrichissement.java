package factories;

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
import interfaces.IAST;

public abstract class Enrichissement {
	public static List<IAST> enrichissables;
	public static int nbEnrVar = 2;

	public static void init() {
		enrichissables = new ArrayList<IAST>();
	}

	// Permet de n'ajouter que les AST dont l'enrichissement est implémenté
	public static void add(IAST a) {
		if (isEnrichissable(a)) {
			enrichissables.add(a);
		}
	}

	public static void pop(IAST a) {
		enrichissables.remove(a);
	}

	// Rajouter a la liste les AST enrichissables
	// Permet d'en rajouter au fil de l'implémentatio
	public static boolean isEnrichissable(IAST a) {
		return (a instanceof ASTVariable) || (a instanceof ASTAffect) || (a instanceof ASTMalloc);
	}

	// Stratégies d'enrichissement par AST

	public static void enrichissement() throws EnrichissementNotImplementedException, EnrichissementMissingException {
		// System.out.println(enrichissables);
		//System.out.println(enrichissables.size());
		enrichissement(enrichissables.get(RandomProvider.nextInt(enrichissables.size())));
	}

	public static void enrichissement(int a)
			throws EnrichissementNotImplementedException, EnrichissementMissingException {
		for (int i = 0; i < a; i++) {
			if (i == 0 && false)
				System.out.println(enrichissables);
			enrichissement();
		}
	}

	public static void enrichissement(IAST a)
			throws EnrichissementNotImplementedException, EnrichissementMissingException {
		// System.out.println(a.getClass());
		if (a instanceof ASTVariable) {
			enrichissement((ASTVariable) a);
			return;
		}
		if (a instanceof ASTAffect) {
			enrichissement((ASTAffect) a);
			return;
		}
		if (a instanceof ASTMalloc) {
			enrichissement((ASTMalloc) a);
			return;
		}
		throw new EnrichissementNotImplementedException(
				"L'enrichissement n'est pas implémenté pour la classe : " + a.getClass());

	}

	public static void enrichissement(ASTVariable a) throws EnrichissementMissingException {
		pop(a);
		switch (a.getType()) {
		case INT:
			// On enrichit aléatoirement entre une opération aléatoire et une affectation
			switch (RandomProvider.nextInt(nbEnrVar)) {
			case 0:
				IAST o = a.getOwner();
				o.enrichissement(a, ASTOp.getRandomOperation(a.getValeur(), o));
				pop(a);
				break;
			case 1:
				IAST l = a.getOwner();
				l.enrichissement(a, new ASTAffect(a.getType(), a.getNom(), a.getValeur(), l));
				pop(a);
				break;
			}
		}
	}

	// Pour un affectation, on ne peut que enrichir la variable, puis on retire
	// l'affectation de la liste d'enrichissable
	public static void enrichissement(ASTAffect a)
			throws EnrichissementNotImplementedException, EnrichissementMissingException {
		enrichissement((IAST) a.getVar());
		pop(a);

	}

	// Pour le moment, on transforme juste le num en affectation, que l'on peu
	// enrichir
	// Voir pour modifier plus tard pour gérer le pointeur
	public static void enrichissement(ASTMalloc a) throws EnrichissementMissingException {
		ASTVariable num = (ASTVariable) a.getNum();
		a.enrichissement(num, new ASTAffect(num.getType(), num.getNom(), num.getValeur(), num.getOwner()));
		pop(a);
	}

}
