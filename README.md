# PSTL

### Il existe deux scripts (necessitant MOPSA):

	1 - Le premier, pstl_script, prend en paramètres l'erreur (1 pour la division par 0),
	    le nombre d'enrichissements souhaités et le nombre de fichiers à créer
	    Ce script va créer autant de fichiers que spécifié, du nombre d'enrichissements donné.

	2 - Le second, pstl_fixed_seed, prend l'erreur, une seed, puis deux bornes d'enrichissements
	    Ce script va créer tout les fichiers avec un nombre d'enrichissements compris entre les bornes,
	    pour la seed donnée.

	
### Pour utiliser le générateur seul

	1- PSTL.jar prend en paramètres l'erreur (toujours 1 pour la division par 0), ainsi que le nombre d'enrichissements.
	   Il va créer un fichier test.c du nombre d'enrichissements spécifié.
	   
	2- PSTLFixedSeed.jar prend en paramères l'erreur, le nombre d'enrichissement souhaité, ainsi que la seed. 
	   Il va créer l'unique fichier d'enrichissement donné, en utilisant la seed passée en paramètre. 

