package ast.operations;

import ast.ASTExpression;
import enums.VarType;
import factories.Lexenv;
import factories.RandomProvider;



public abstract class ASTOperation extends ASTExpression {
	protected int gauche;
	protected int droite;
	
	public ASTOperation(VarType type, String nom, Object valeur) {
		super(type, nom, valeur);
	}

	@Override
	public void enrichissement(int nb) {
		if(nb <= 0) return;
		
		//Si la valeur est 0, on n'enrichit pas, pas de gestion des negatifs (pour le moment?)
		if((Integer)valeur == 0) {
			return;
		}
		
		//Permet d'enrichir random entre +-*/
		int op = RandomProvider.nextInt(4);

		//Si déjà étendu 2 fois, on en etends un des deux aleatoirement
		if (explist.size()>1) {
			int indice = RandomProvider.nextInt(nb+1);
			explist.get(0).enrichissement(indice);
			explist.get(1).enrichissement(nb-indice);
			return;
		}
		
		//Si jamais etendue, on étends gauche, sinon droit, aopération aléatoire
		int cote = explist.size() > 0 ? droite : gauche;
			switch (op) {
			case 0 : 			explist.add(new ASTSomme(VarType.INT,Lexenv.getNewName(),cote,true));
								break;
			case 1 : 			explist.add(new ASTSoustraction(VarType.INT,Lexenv.getNewName(),cote,true));
								break;
			case 2 : 			explist.add(new ASTMultiplication(VarType.INT,Lexenv.getNewName(),cote,true));
								break;
			case 3 :			explist.add(new ASTDivision(VarType.INT,Lexenv.getNewName(),cote,true));
								break;
			}
			this.enrichissement(nb-1);
			return;			
	}


}
