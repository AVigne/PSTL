package interfaces;

import exceptions.EnrichissementMissingException;

public interface IErrorBuilder {

	public IAST build() throws EnrichissementMissingException;
}
