package factories;

import ast.ASTExpression;
import ast.errors.ASTErreurDoubleFree;
import enums.ErrorType;
import enums.VarType;
import interfaces.IAST;
import interfaces.ILexenv;
import interfaces.IProgramBuilder;

public class ErrorBuilder implements IProgramBuilder{
	private ILexenv le;
	private ErrorType et;
	
	@Override
	public ASTExpression build(ILexenv le) {
		this.le=le;
		switch(et) {
		case DOUBLE_FREE: 
			return new ASTErreurDoubleFree(VarType.ERROR, "edf", null); 
		default:
			return new ASTErreurDoubleFree(VarType.ERROR, "edf", null); //Pour que ça compile
		
		}
	}

	public boolean setErrorType(ErrorType et) {
		this.et = et;
		return true;
	}
}
