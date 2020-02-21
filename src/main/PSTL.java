package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ast.*;
import enums.ErrorType;
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
	public static void main(String[] args) throws IOException {

		// creation du lexenv et du builder de la premiere erreur
		Lexenv.init();
		RandomProvider.init();
		
		ErrorBuilder errBuilder = new ErrorBuilder();
		
		
		ArrayList<IAST> errorsList=new ArrayList<>(); 
		errBuilder.setErrorType(ErrorType.DOUBLE_FREE);
		errorsList.add(errBuilder.build());
		
		//Timer 
		long time = System.currentTimeMillis();
		
		//Creation du programe
		ProgramBuilder progBuilder = new ProgramBuilder(errorsList);
		IAST program = progBuilder.build(1000,0);
		
		StringBuffer sb = new StringBuffer();
		program.visit(sb);
		
		//Affichage Console
		String output=sb.toString();
		System.out.println(output);
		
		//Ecriture dans un fichier
		File f = new File("test.c");
		FileWriter fw = new FileWriter(f);
		fw.write(output);
		fw.close();
		
		System.out.println("\nGenere en "+(System.currentTimeMillis() - time)/1000.0+"s");
	}

}
