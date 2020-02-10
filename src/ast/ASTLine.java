package ast;

import java.util.ArrayList;
import java.util.List;

import enums.LineType;
import interfaces.IASTLine;

public class ASTLine implements IASTLine {

	private final String codeCLine;
	private List<LineType> lineType = new ArrayList<>();
	
	public ASTLine(String s) {
		codeCLine = s;
	}
	
	public boolean addType(LineType lt) {
		
		return this.lineType.add(lt);
	}
	
	
	@Override
	public void visit(StringBuffer sb) {
		sb.append(codeCLine);
	}

}
