package factories;

import java.util.ArrayList;
import java.util.List;

import ast.ASTProgram;
import interfaces.IAST;
import interfaces.IProgramBuilder;

public class ProgramBuilder implements IProgramBuilder{

	private ArrayList<IAST> listeErreurs;
	
	
	public ProgramBuilder(ArrayList<IAST> lerr) {
		listeErreurs = lerr;
	}
	
	
	@Override
	public IAST build(int enrE,int enrB) {
		ASTProgram res =  new ASTProgram(enrE,enrB);
		
		// pour le moment on se contente de mettre les erreurs uniquement
		
		for( IAST err :  listeErreurs) {
				res.addExp(err);
		}			
		
		
		return res;
	}

}
