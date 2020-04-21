package ast.expressions;

import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.Lexenv;
import factories.RandomProvider;

public class ASTBoolOperation extends ASTExpr {
	private ASTExpr gauche, droite;
	private String s;
	/***
	 * Cas d'une Opération booleenne où on aurait la partie gauche, droite, ainsi que le signe (== par défault si non correct).
	 * Non utilisé
	 * @param type
	 * @param nom
	 * @param valeur
	 * @param g
	 * @param d
	 * @param s
	 */
	public ASTBoolOperation(VarType type, String nom, Object valeur, ASTExpr g, ASTExpr d, String s) {
		this.typeretour = type;
		this.nom = nom;
		this.valeur = valeur;
		gauche=g;
		droite=d;
		//Opérateur valide
		if (Lexenv.compop.contains(s))
			this.s=s;
		//Sinon == par défaut
		else
			this.s = "==";
		this.enrichissements=gauche.getEnrichissements()+droite.getEnrichissements();

	}
	/***
	 * Cas d'une opératon booléenne a laquelle on ne donnerais qu'un booléen, et qui s'occupe aléatoirement de créer un ast cohérent
	 * @param type
	 * @param nom
	 * @param valeur
	 */
	public ASTBoolOperation(VarType type, String nom, Object valeur) {
		this.typeretour = type;
		this.nom = nom;
		this.valeur = valeur;
		int rand = RandomProvider.nextInt(RandomProvider.nbRandom)+1;
		int r;
		//Nombre alétoire, et différent
		if (rand >=1) {
			r=rand-1;
		}
		else {
			r=rand+1;
		}
		//Cas du True
		if ((Boolean)valeur) {
			
			switch (RandomProvider.nextInt(2)) {
			case 0 : 
				gauche = new ASTConstante(VarType.INT, Lexenv.getNewName(), rand);
				droite = new ASTConstante(VarType.INT, Lexenv.getNewName(), rand);
				s= "==";
				break;
			case 1 : 
				gauche = new ASTConstante(VarType.INT, Lexenv.getNewName(), rand);
				droite = new ASTConstante(VarType.INT, Lexenv.getNewName(), r);
				s= "!=";
			}
		}
		//Cas du False
		else {
			switch (RandomProvider.nextInt(2)) {
			case 0 : 
				gauche = new ASTConstante(VarType.INT, Lexenv.getNewName(), rand);
				droite = new ASTConstante(VarType.INT, Lexenv.getNewName(), rand);
				s= "!=";
				break;
			case 1 : 
				gauche = new ASTConstante(VarType.INT, Lexenv.getNewName(), rand);
				droite = new ASTConstante(VarType.INT, Lexenv.getNewName(), r);
				s= "==";
			}
		}
		
		this.enrichissements=gauche.getEnrichissements()+droite.getEnrichissements();

	}
	public ASTExpr getGauche() {
		return gauche;
	}

	public void setGauche(ASTExpr gauche) {
		this.gauche = gauche;
	}

	public ASTExpr getDroite() {
		return droite;
	}

	public void setDroite(ASTExpr droite) {
		this.droite = droite;
	}

	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		gauche.visit(sb);
		sb.append(" "+s+" ");
		droite.visit(sb);
	}

	@Override
	public String getNom() {
		return nom;
	}
	@Override
	public void addDeclaree(String n) {
		if (!declaree.contains(n))
			this.declaree.add(n);
		gauche.addDeclaree(n);
		droite.addDeclaree(n);
	}
	@Override
	public void addUsable(String n) {
		if (!usable.contains(n))
			this.usable.add(n);
		gauche.addUsable(n);
		droite.addUsable(n);
	}

}
