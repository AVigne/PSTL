package ast;

import java.util.ArrayList;

import interfaces.IAST;

public class ASTProgram implements IAST {
	
	
	private ArrayList<ASTExpression> explist = new ArrayList<>();
	
	public void addExp(ASTExpression exp) {
		
		this.explist.add(exp);
	}
	
	
	@Override
	public void visit(StringBuffer sb) {
		//Biblio a ajouter au fur et a mesure si besoin
		sb.append("# include <stdlib.h>\n");
		sb.append("# include <string.h>\n");
		sb.append("int main(){\n");
		for(int i = 0; i < explist.size(); i++) {
			//sb.append("\t");
			explist.get(i).visit(sb);
		}
		sb.append("return 0;\n}");
	}

}
