package factories;

import ast.ASTError;
import ast.ASTLine;
import enums.ErrorType;
import interfaces.IAST;
import interfaces.ILexenv;
import interfaces.IProgramBuilder;

public class ErrorBuilder implements IProgramBuilder{
	private ILexenv le;
	private ErrorType et;
	
	@Override
	public IAST build(ILexenv le) {
		ASTError res = new ASTError();
		this.le=le;
		switch(et) {
		case DOUBLE_FREE: 
			buildDoubleFree(res); 
			break;
		
		default:
			break;
		
		}
		
		
		
		return res;
	}

	private void buildDoubleFree(ASTError res) {
		res.addLine(new ASTLine("int *a = malloc(10*sizeof(int));\n"));
		res.addLine(new ASTLine("free(a);\n"));
		res.addLine(new ASTLine("free(a);\n"));
		le.addVar("a");
	}

	public boolean setErrorType(ErrorType et) {
		this.et = et;
		return true;
	}
}
