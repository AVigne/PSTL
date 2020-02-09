package interfaces;

public interface IAST {
	
	
	
	/**
	 * called by the visitor to build the C file
	 * 
	 * @param sb jsp frere
	 */
	public void visit(StringBuffer sb);

}
