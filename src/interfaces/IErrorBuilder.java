package interfaces;

import exceptions.CodeSupposedUnreachableException;
import exceptions.EnrichissementMissingException;

public interface IErrorBuilder {

	public IAST build() throws EnrichissementMissingException, CodeSupposedUnreachableException;
}
