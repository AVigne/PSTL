package ast.errors;

import java.util.ArrayList;
import java.util.List;

import interfaces.IAST;
import interfaces.IASTLine;

public class ASTError implements IAST {

	private List<IASTLine> lineList = new ArrayList<>();
	
	public void addLine(IASTLine line) {
		
		this.lineList.add(line);
	}
	
	public IASTLine pullLine() {
		if( hasNext())
			return this.lineList.remove(0);
		else 
			return null;
	}
	
	public boolean hasNext() {
		return this.lineList.size() > 0;
	}
	
	
	@Override
	public void visit(StringBuffer sb) {
		// TODO Auto-generated method stub

	}

}
