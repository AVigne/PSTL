package ast;

import java.util.ArrayList;
import java.util.List;

import interfaces.IAST;
import interfaces.IASTLine;

public class ASTProgram implements IAST {
	
	
	private List<IASTLine> lineList = new ArrayList<>();
	
	public void addLine(IASTLine line) {
		
		this.lineList.add(line);
	}
	
	
	@Override
	public void visit(StringBuffer sb) {
		sb.append("int main(){\n");
		for(int i = 0; i < lineList.size(); i++) {
			sb.append("\t");
			lineList.get(i).visit(sb);
		}
		sb.append("\treturn 0;\n}");
	}

}
