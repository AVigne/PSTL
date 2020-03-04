package interfaces;

import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import structures.ReturnEnrichissement;

public interface IAST {
	

	public void setEnrichissements(int i);

	public int getEnrichissements();
	/**
	 * Called by the visitor to build the input string of the C file
	 * 
	 * @param sb
	 */
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException;

	public String getNom();

}
