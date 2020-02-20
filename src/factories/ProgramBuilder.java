package factories;

import java.util.ArrayList;
import java.util.List;

import ast.ASTExpression;
import ast.ASTProgram;
import interfaces.IAST;
import interfaces.IProgramBuilder;

public class ProgramBuilder implements IProgramBuilder{

	private ArrayList<ASTExpression> listeErreurs;
	
	
	public ProgramBuilder(ArrayList<ASTExpression> lerr) {
		listeErreurs = lerr;
	}
	
	
	@Override
	public IAST build(int enrE,int enrB) {
		ASTProgram res =  new ASTProgram(enrE,enrB);
		
		//Recupération des variables du lexenv (pour plus tard)
		List<String> varerreur = Lexenv.getVars();
		
		
		// pour le moment on se contente de mettre les erreurs uniquement
		
		for( ASTExpression err :  listeErreurs) {
				res.addExp(err);
		}			
		
		
		return res;
	}

}
