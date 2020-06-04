package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ast.*;
import enums.ErrorType;
import exceptions.CodeSupposedUnreachableException;
import exceptions.EnrichissementMissingException;
import exceptions.EnrichissementNotImplementedException;
import factories.*;
import interfaces.*;

public class PSTL {
	/*  Liste des erreurs reperees dans le code de Mopsa : 
	1 "Out of bound access"
	2 "Null pointer dereference"
	3 "Invalid pointer dereference"
	4 "Division by zero"
	5 "Integer overflow"
	6 "Illegal pointer difference"
	7 "Illegal pointer comparison"
	8 "Use after free"
	9 "Double free"
	10 "No next argument for va_arg"
	11 "Invald bit-shift"
	12 "Modification of a readonly memory"
	13 "Dangling pointer dereference"
	14 "Inufficient number of format arguments"
	15 "Incorrect type of format argument"
	
	
	On utilise les numeros dans le programme, la doc du script explique comment s'en servir
	le script parse les options pour donner un code au programme
	Pour le moment, on va tester uniquement l'erreur 9 pour demarrer la structure du projet
	et faire les premiers tests
	 */
	
	//Permet de récup le premier argument et de le transformer en entier pour récup l'id de l'erreur
	private static ErrorType genereError(String arg,ArrayList<ErrorType> errortypes) {
		ErrorType et;
		try{
			int i = Integer.parseInt(arg);
			if ((errortypes.size()>i) && (i>=0)){
				et= errortypes.get(i);
			}
			else {
				System.out.println("//L'argument donné n'est pas une erreur implémentée, Erreur tirée aléatoirement");
				et=errortypes.get(RandomProvider.nextInt(errortypes.size()));
			}
		}catch(NumberFormatException e) {
			System.out.println("//L'argument donné n'est pas un entier, Erreur tirée aléatoirement");
			et=errortypes.get(RandomProvider.nextInt(errortypes.size()));
		}
		return et;
	}
	
	//Permet de récup le second argument et de el transformer en entier pour récup le nombre d'enrichissements
	private static int genereEnr(String arg) {
		int nbenr;

		try{
			int j = Integer.parseInt(arg);
			if (j>0)
				nbenr=j;
			else
				nbenr=RandomProvider.nextInt(100);
			
		}catch(NumberFormatException e) {
			System.out.println("//L'argument donné n'est pas un entier, Nombre d'enrichissement tiré entre 0 et 100");
			nbenr=RandomProvider.nextInt(100);
		}
		return nbenr;
	}
	
	
	/**
	 * Prend en parametre deux entiers, le premier correspond a l'erreur a etendre, le second au nombre d'enrichissement
	 * @param args
	 * @throws IOException
	 * @throws EnrichissementMissingException
	 * @throws EnrichissementNotImplementedException
	 * @throws CodeSupposedUnreachableException
	 */
	public static void main(String[] args) 
			throws IOException, 
					EnrichissementMissingException, 
					EnrichissementNotImplementedException,
					CodeSupposedUnreachableException{
		Lexenv.init();
		RandomProvider.init();
		Enrichissement.init();
		
		StringBuffer sb;
		ArrayList<ErrorType> errortypes=new ArrayList<>();
		errortypes.add(ErrorType.DOUBLE_FREE);
		errortypes.add(ErrorType.DIVISION_BY_ZERO);
		ErrorType et=errortypes.get(0);
		int nbenr=0;
		switch (args.length) {
		case 2 : 
			et = genereError(args[0], errortypes);
			nbenr = genereEnr(args[1]);
			break;
		case 1 : 
			et = genereError(args[0], errortypes);
			System.out.println("//Enrichissement générés");
			nbenr = RandomProvider.nextInt(100);
			break;
		default:
			System.out.println("//Erreur et enrichisssement générés");
			et= errortypes.get(RandomProvider.nextInt(errortypes.size()));
			nbenr = RandomProvider.nextInt(100);

		}
		long time;
		/* 		
		int secu = 100000;
		do {
		*/
		// initialisation du lexenv et du builder de la premiere erreur
		
		ErrorBuilder errBuilder = new ErrorBuilder();
		
		
		ArrayList<IAST> errorsList=new ArrayList<>(); 
		errBuilder.setErrorType(et);
		ArrayList<AST> errors = errBuilder.build();
		for (AST a : errors) {
			errorsList.add(a);
		}
		
		
		//Timer 
		time = System.currentTimeMillis();
		
		//Creation du programe
		ProgramBuilder progBuilder = new ProgramBuilder(errorsList);
		IAST program = progBuilder.build(nbenr,0);
		Enrichissement.setProg((ASTProgram)program);
		
		sb = new StringBuffer();
		try {
			program.visit(sb);
		}catch (Exception e) {
			System.out.println("Seed amenant a des erreurs : ");
			System.out.println(RandomProvider.getSeed());
			System.out.println("\n");
			e.printStackTrace();
		}
		/* pour tester des conditions sur un grand nombre de generations
		} while (sb.toString().split("\n").length > 53 && secu-- > 0);
		System.out.println(secu+"\n\n\n\n");
		if (secu < 0) System.exit(0);
		*/
		//Affichage Console
		String output=sb.toString();
		System.out.println(output);
		//System.out.println(Lexenv.getVars());
		//System.out.println(Lexenv.getVars().size());
		
		//Ecriture dans un fichier
		File f = new File("test.c");
		FileWriter fw = new FileWriter(f);
		fw.write(output);
		fw.close();
		
		System.out.println("\n//Genere en "+(System.currentTimeMillis() - time)/1000.0+"s");
	}

}
