package interfaces;

import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;

public interface IAST {

	/**
	 * Called by the visitor to build the input string of the C file
	 * 
	 * @param sb
	 */
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException;

	public String getNom();

	public boolean isaffectee();

	public void affectee();

	/**
	 * Permet de remplacer une ast par une nouvelle après enrichissement
	 * 
	 * @param old
	 * @param nouveau
	 */
	public void enrichissement(IAST old, IAST nouveau) throws EnrichissementMissingException;
}
