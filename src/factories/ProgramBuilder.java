package factories;

import java.util.List;

import interfaces.IAST;
import interfaces.ILexenv;
import interfaces.IProgramBuilder;

public class ProgramBuilder implements IProgramBuilder{

	
	private List<IAST> listeErreurs;
	
	
	
	public ProgramBuilder(List<IAST> lerr) {
		listeErreurs = lerr;
	}
	
	
	@Override
	public IAST build(ILexenv le) {
		// TODO Auto-generated method stub
		return null;
	}

}
