package interfaces;

import java.util.ArrayList;

import ast.AST;
import exceptions.CodeSupposedUnreachableException;
import exceptions.EnrichissementMissingException;

public interface IErrorBuilder {

	public ArrayList<AST> build() throws EnrichissementMissingException, CodeSupposedUnreachableException;
}
