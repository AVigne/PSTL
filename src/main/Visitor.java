package main;

import ast.ASTExpression;
import interfaces.IAST;

public class Visitor {

	
	public static StringBuffer visit(ASTExpression ast, StringBuffer sb) {
		
		ast.visit(sb);
		return sb;
	}
	
}
