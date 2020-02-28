package factories;

import ast.errors.ASTErreurDoubleFree;
import ast.expressions.ASTExpr;
import enums.ErrorType;
import enums.VarType;
import exceptions.CodeSupposedUnreachableException;
import exceptions.EnrichissementMissingException;
import interfaces.IAST;
import interfaces.IErrorBuilder;

public class ErrorBuilder implements IErrorBuilder {
	private ErrorType et;

	@Override
	public ASTExpr build() throws EnrichissementMissingException, CodeSupposedUnreachableException {
		switch (et) {
		case DOUBLE_FREE:
			return new ASTErreurDoubleFree(VarType.ERROR, "edf", null, null);
		default:
			throw new CodeSupposedUnreachableException("Erreur non gérée");
		}
	}

	public boolean setErrorType(ErrorType et) {
		this.et = et;
		return true;
	}
}
