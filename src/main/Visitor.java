package main;

import interfaces.IAST;

public class Visitor {

	
	public static StringBuffer visit(IAST ast, StringBuffer sb) {
		
		ast.visit(sb);
		return sb;
	}
	
}
