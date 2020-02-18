package interfaces;

public interface IAST {
	
	
	
	/**
	 * Called by the visitor to build the input string of the C file
	 * 
	 * @param sb 
	 */
	public void visit(StringBuffer sb);

}
