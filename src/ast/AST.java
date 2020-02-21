package ast;

import java.util.List;

import interfaces.IAST;

public abstract class AST implements IAST {
	//Liste des enrichissement possibles pour l'AST
	protected List<String> enrichissements;

}
