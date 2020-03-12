# include <stdlib.h>
# include <stdio.h>
# include <string.h>
/* 
nombre d'enrichissements des erreurs : 5
nombre d'enrichissements du bruit : 0
seed : 1584012140216
*/
int main(){
int j;
j = (221916304 - 221916294);
int r;
r = (j * 15);
int error_n;
error_n = r;
int*  error_c;
error_c = malloc( error_n * sizeof (int));
free(error_c);
free(error_c);
return 0;
}

//Genere en 0.013s
