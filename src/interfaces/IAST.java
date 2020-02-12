package interfaces;

public interface IAST {
	
	
	
	/**
	 * called by the visitor to build the C file
	 * 
	 * @param sb sb
	 */
	public void visit(StringBuffer sb);

}
