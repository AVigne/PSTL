package ast.operations;


import ast.ASTExpression;
import enums.VarType;
import factories.RandomProvider;

public class ASTSomme extends ASTOperation{
	private boolean declaration;
	
	public ASTSomme(VarType type, String nom, Object valeur,Boolean dec) {
		super(type, nom, valeur);
		//System.out.println("sum "+valeur);
		int somme= (Integer)valeur;
		if (somme==0) {
			gauche=0;
			droite=0;
			declaration=dec;
		}
		else {
			//gauche et droite aléatoires
			gauche= RandomProvider.nextInt(somme);
			droite=somme-gauche;
			declaration=dec;
		}
		//System.out.println("sum "+gauche+" "+droite);
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
		sb.append(nom+" = "+ g+ " + "+d+";\n");
	}

}
