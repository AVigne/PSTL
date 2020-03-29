package ast.expressions;

import enums.VarType;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.RandomProvider;

public class ASTVarPM extends ASTExpr{
	ASTExpr vg,vd;

	public ASTVarPM(VarType type, String nom, Object valeur) {
		this.typeretour = type;
		this.nom = nom;
		this.valeur = valeur;
		this.enrichissements = 2;
	}

	@Override
	public void visit(StringBuffer sb) throws EnrichissementMissingException, EnrichissementNotImplementedException {
		/*System.out.println(this.nom);
		System.out.println(vg.getUsable());
		System.out.println(vd.getUsable());*/
		sb.append("("+this.nom+" + ");
		vg.visit(sb);
		sb.append(" - ");
		vd.visit(sb);
		sb.append(")");
	}

	@Override
	public String getNom() {
		return nom;
	}
	/***
	 * Récupère un nom, puis crée deux nouvelles variables portant ce nom, a des valeurs aléatoires.
	 * La valeur n'est pas utile pour le moment
	 * @param a
	 */
	public void setV(String a) {
		vg= new ASTVariable(this.getType(),a,RandomProvider.nextInt(Integer.MAX_VALUE));
		vd= new ASTVariable(this.getType(),a,RandomProvider.nextInt(Integer.MAX_VALUE));
		vg.fuseDeclaree(this.declaree);
		vg.fuseUsable(this.usable);
		vd.fuseDeclaree(this.declaree);
		vd.fuseUsable(this.usable);
	}
	
	/***
	 * Set un nom de variable parmis celles utilisables et déclarées. Idem que serV sinon
	 */
	public void setRandV() {
		String a;
		boolean b;
		int cpt=0;
		// Si pas de variables utilisables sera de la forme x + x -x
		if (usable.size()==0) {
			vg=new ASTVariable(this.getType(),nom,RandomProvider.nextInt(Integer.MAX_VALUE));
			vd=new ASTVariable(this.getType(),nom,RandomProvider.nextInt(Integer.MAX_VALUE));
			vg.fuseDeclaree(this.declaree);
			vg.fuseUsable(this.usable);
			vd.fuseDeclaree(this.declaree);
			vd.fuseUsable(this.usable);
		}
		else {
			//Recherche une variable utilisable et déclarée (normalement un tour de boucle suffit, mais dans le doute, 10 itermax)
			do {
				a=usable.get(RandomProvider.nextInt(usable.size()));
				b=declaree.contains(a);
				cpt++;
			}while((!b) || (cpt<10));
			//Revérification
			if (declaree.contains(a)&& usable.contains(a)) {
				vg=new ASTVariable(this.getType(),a,RandomProvider.nextInt(Integer.MAX_VALUE));
				vd=new ASTVariable(this.getType(),a,RandomProvider.nextInt(Integer.MAX_VALUE));
				vg.fuseDeclaree(this.declaree);
				vg.fuseUsable(this.usable);
				vd.fuseDeclaree(this.declaree);
				vd.fuseUsable(this.usable);
			}
			else {
				//x + x - x
				vg=new ASTVariable(this.getType(),nom,RandomProvider.nextInt(Integer.MAX_VALUE));
				vd=new ASTVariable(this.getType(),nom,RandomProvider.nextInt(Integer.MAX_VALUE));
				vg.fuseDeclaree(this.declaree);
				vg.fuseUsable(this.usable);
				vd.fuseDeclaree(this.declaree);
				vd.fuseUsable(this.usable);
			}
		}
	}
	public ASTExpr getGauche() {
		return vg;
	}
	public ASTExpr getDroite() {
		return vd;
	}
	/***
	 * Set la variable de gauche, et fusionne ses def/use
	 * @param a
	 */
	public void setGauche(ASTExpr a) {
		vg=a;
		vg.fuseDeclaree(this.declaree);
		vg.fuseUsable(this.usable);
	}
	/***
	 * Set la variable de droite et fusionne ses def/use
	 * @param a
	 */
	public void setDroite(ASTExpr a) {
		vd=a;
		vd.fuseDeclaree(this.declaree);
		vd.fuseUsable(this.usable);
	}
	@Override
	public void addDeclaree(String n) {
		if (!declaree.contains(n))
			this.declaree.add(n);
		if (vg != null)
			vg.addDeclaree(n);
		if (vd != null)
			vd.addDeclaree(n);
	}
	@Override
	public void addUsable(String n) {
		if (!usable.contains(n))
			this.usable.add(n);
		if (vg != null)
			vg.addUsable(n);
		if (vd != null)
			vd.addUsable(n);
	}
}
