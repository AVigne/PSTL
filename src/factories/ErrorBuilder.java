package factories;

import ast.errors.ASTErreurDoubleFree;
import ast.expressions.ASTExpr;
import enums.ErrorType;
import enums.VarType;
import exceptions.EnrichissementMissingException;
import interfaces.IErrorBuilder;

public class ErrorBuilder implements IErrorBuilder{
	private ErrorType et;
	
	@Override
	public ASTExpr build() throws EnrichissementMissingException {

		switch(et) {
		case DOUBLE_FREE: 
			return new ASTErreurDoubleFree(VarType.ERROR, "edf", null,null); 
		default:
			return new ASTErreurDoubleFree(VarType.ERROR, "edf", null,null); //Pour que ça compile
		
		}
	}

	public boolean setErrorType(ErrorType et) {
		this.et = et;
		return true;
	}
}
