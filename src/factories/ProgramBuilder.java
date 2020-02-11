package factories;

import java.util.ArrayList;

import ast.ASTError;
import ast.ASTProgram;
import interfaces.IAST;
import interfaces.ILexenv;
import interfaces.IProgramBuilder;

public class ProgramBuilder implements IProgramBuilder{

	private IAST[] listeErreurs;
	
	static final int NB_MAX_LIGNES = 100;
	
	
	public ProgramBuilder(IAST[] lerr) {
		listeErreurs = lerr;
	}
	
	
	@Override
	public IAST build(ILexenv le) {
		ASTProgram res =  new ASTProgram();
		
		//Recupération des variables du lexenv (pour plus tard)
		ArrayList<String> varerreur = le.getVars();
		
		
		// pour le moment on se contente de mettre les lignes d'erreurs uniquement
		
		for( IAST err :  listeErreurs) {
			while( ((ASTError) err).hasNext()) {
				res.addLine(((ASTError) err).pullLine());			
			}			
		}		
		
		return res;
	}

}
