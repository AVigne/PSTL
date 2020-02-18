package factories;

import java.util.ArrayList;

import ast.ASTExpression;
import ast.ASTProgram;
import interfaces.IAST;
import interfaces.IProgramBuilder;

public class ProgramBuilder implements IProgramBuilder{

	private ArrayList<ASTExpression> listeErreurs;
	
	static final int NB_MAX_LIGNES = 100;
	
	
	public ProgramBuilder(ArrayList<ASTExpression> lerr) {
		listeErreurs = lerr;
	}
	
	
	@Override
	public IAST build() {
		ASTProgram res =  new ASTProgram();
		
		//Recupération des variables du lexenv (pour plus tard)
		ArrayList<String> varerreur = Lexenv.getVars();
		
		
		// pour le moment on se contente de mettre les lignes d'erreurs uniquement
		
		for( ASTExpression err :  listeErreurs) {
				res.addExp(err);
		}			
		
		
		return res;
	}

}
