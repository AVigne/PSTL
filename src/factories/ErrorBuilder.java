package factories;

import java.util.ArrayList;

import ast.AST;
import ast.errors.ASTErreurDivZero;
import ast.errors.ASTErreurDoubleFree;
import enums.ErrorType;
import enums.VarType;
import exceptions.CodeSupposedUnreachableException;
import exceptions.EnrichissementMissingException;
import interfaces.IErrorBuilder;

public class ErrorBuilder implements IErrorBuilder {
	private ErrorType et;

	@Override
	public ArrayList<AST> build() throws EnrichissementMissingException, CodeSupposedUnreachableException {
		switch (et) {
		case DOUBLE_FREE:
			return (new ASTErreurDoubleFree(VarType.ERROR, "edf", null, null)).getAst();
		case DIVISION_BY_ZERO:
			return (new ASTErreurDivZero(VarType.ERROR,"edpz",null,null)).getAst();
		default:
			throw new CodeSupposedUnreachableException("Erreur non gérée");
		}
	}

	public boolean setErrorType(ErrorType et) {
		this.et = et;
		return true;
	}
}
