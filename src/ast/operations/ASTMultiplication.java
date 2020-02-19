package ast.operations;

import ast.ASTExpression;
import enums.VarType;
import factories.RandomProvider;

public class ASTMultiplication extends ASTOperation{

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
			int rand = RandomProvider.nextInt(somme)+1;
			int cpt=0;
	
			//On tire un diviseur de la valeur, 100 fois au plus (afin de ne pas boucler jusqu'a trouver 1)
			//Regle le soucis des grands nombres premeiers entre autre
			while((somme%rand!=0)&&(cpt<100)){
				rand = RandomProvider.nextInt(somme)+1;
				cpt++;
			}
			if (cpt==10 || (rand > (Integer.MAX_VALUE / somme))) {
				gauche=somme;
				droite=1;
			}
			else {
				gauche=rand;
				droite=somme/rand;
			}
			declaration=dec;
		}
		//System.out.println(gauche+" "+droite);
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
