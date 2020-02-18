package factories;

import ast.ASTExpression;
import ast.errors.ASTErreurDoubleFree;
import enums.ErrorType;
import enums.VarType;
import interfaces.IErrorBuilder;

public class ErrorBuilder implements IErrorBuilder{
	private ErrorType et;
	
	@Override
	public ASTExpression build() {

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
