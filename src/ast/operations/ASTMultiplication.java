package ast.operations;

import java.util.Random;

import ast.ASTExpression;
import enums.VarType;
import factories.Lexenv;

public class ASTMultiplication extends ASTOperation{

	private int gauche;
	private int droite;
	private boolean declaration;
	
	public ASTMultiplication(VarType type, String nom, Object valeur,Boolean dec) {
		super(type, nom, valeur);
		int somme= (Integer)valeur;
		if (somme==0) {
			gauche=0;
			droite=0;
			declaration=dec;
		} 
		else {
			//gauche et droite aléatoires
			int rand = new Random().nextInt(somme)+1;
			int cpt=0;
	
			//On tire un diviseur de la valeur, 100 fois au plus (afin de ne pas boucler jusqu'a trouver 1)
			//Regle le soucis des grands nombres premeiers entre autre
			while((somme%rand!=0)&&(cpt<100)){
				rand = new Random().nextInt(somme)+1;
				cpt++;
			}
			if (cpt==10) {
				gauche=somme;
				droite=1;
			}
			else {
				gauche=rand;
				droite=somme/rand;
			}
			declaration=dec;
		}
	}

	@Override
	public void enrichissement() {
		
		//Si la valeur est 0, on n'enrichit pas, pas de gestion des negatifs (pour le moment?)
		if((Integer)valeur == 0) {
			return;
		}
		
		//Permet d'enrichir random entre +-*/
		int op = new Random().nextInt(4);
		
		
		//Si jamais etendue, on étends gauche
		if ((explist.size()==0)) {
			switch (op) {
			case 0 : 			explist.add(new ASTSomme(VarType.INT,Lexenv.getNewName(),gauche,true));
								break;
			case 1 : 			explist.add(new ASTSoustraction(VarType.INT,Lexenv.getNewName(),gauche,true));
								break;
			case 2 : 			explist.add(new ASTMultiplication(VarType.INT,Lexenv.getNewName(),gauche,true));
								break;
			case 3 :			explist.add(new ASTDivision(VarType.INT,Lexenv.getNewName(),gauche,true));
								break;
			}
			return;

		}
		
		//Si gauche, on etends droit
		if((explist.size()>=0)) {
			switch (op) {
			case 0 : 			explist.add(new ASTSomme(VarType.INT,Lexenv.getNewName(),droite,true));
								break;
			case 1 : 			explist.add(new ASTSoustraction(VarType.INT,Lexenv.getNewName(),droite,true));
								break;
			case 2 : 			explist.add(new ASTMultiplication(VarType.INT,Lexenv.getNewName(),droite,true));
								break;
			case 3 :			explist.add(new ASTDivision(VarType.INT,Lexenv.getNewName(),droite,true));
								break;
			}
			return;
		}
		
		//Si déjà étendu 2 fois, on en etends un des deux aleatoirement
		if (explist.size()>1) {
			int indice = new Random().nextInt(2);
			explist.get(indice).enrichissement();
			return;
		}
			
	}

	@Override
	public void visit(StringBuffer sb) {
		//Si besoin de declarer
		if (declaration) {
			switch (type) {
			case INT:
				sb.append("int "+nom+";\n");
				break;
			}
		}
		for (ASTExpression e:explist) {
			e.visit(sb);
		}
		
		//Si gauche ou droits etendus, besoin de recuperer leurs noms
		String g=Integer.toString(gauche);
		String d=Integer.toString(droite);
		if (explist.size()>=1) {
			g=explist.get(0).getNom();
		}
		if (explist.size()==2 ) {
			d= explist.get(1).getNom();

		}
		sb.append(nom+" = "+ g+ " * "+d+";\n");
	}

}
