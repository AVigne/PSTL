package main;

import ast.*;
import enums.ErrorType;
import factories.*;
import interfaces.*;

public class PSTL {
	/*  Liste des erreurs repérées dans le code de Mopsa : 
	1 "Out of bound access"
	2 "Null pointer dereference"
	3 "Invalid pointer dereference"
	4 "Division by zero"
	5 "Integer overflow"
	6 "Illegal pointer difference"
	7 "Illegal pointer comparison"
	8 "Use after free"
	9 "Double free"
	10 "No next argument for va_arg"
	11 "Invald bit-shift"
	12 "Modification of a readonly memory"
	13 "Dangling pointer dereference"
	14 "Inufficient number of format arguments"
	15 "Incorrect type of format argument"
	
	
	On utilise les numéros dans le programme, la doc du script explique comment s'en servir
	le script parse les options pour donner un code au programme
	Pour le moment, on va tester uniquement l'erreur 9 pour démarrer la structure du projet
	et faire les premiers tests
	 */
	public static void main(String[] args) {
		
		// création du lexenv et du builder de la première erreur
		ILexenv lv = new Lexenv();
		ErrorBuilder errBuilder = new ErrorBuilder();
		
		
		ASTError[] errorsList = new ASTError[1]; // 1 à remplacer par nombre d'arguments, ou autre chose selon la gestion des args
		errBuilder.setErrorType(ErrorType.DOUBLE_FREE);
		errorsList[0] = (ASTError) errBuilder.build(lv);
		
		ProgramBuilder progBuilder = new ProgramBuilder(errorsList);
		IAST program = progBuilder.build(lv);
		
		StringBuffer sb = new StringBuffer();
		Visitor.visit(program, sb);
		
		System.out.println(sb.toString());
		
	}

}
