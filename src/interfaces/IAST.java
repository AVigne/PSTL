package interfaces;

import exceptions.EnrichissementMissingException;

public interface IAST {
	
	
	
	/**
	 * Called by the visitor to build the input string of the C file
	 * 
	 * @param sb 
	 */
	public void visit(StringBuffer sb);
	
	
	/**
	 * Permet de remplacer une ast par une nouvelle après enrichissement
	 * @param old
	 * @param nouveau
	 */
	public void enrichissement(IAST old, IAST nouveau) throws EnrichissementMissingException;
}
