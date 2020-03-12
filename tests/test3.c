# include <stdlib.h>
# include <stdio.h>
# include <string.h>
/* 
nombre d'enrichissements des erreurs : 5
nombre d'enrichissements du bruit : 0
seed : 1584012143200
*/
int main(){
int g;
g = 1;
int o;
o = g;
int x;
x = (284514750 * o);
int*  error_o;
error_o = malloc( (x / 1896765) * sizeof (int));
free(error_o);
free(error_o);
return 0;
}

//Genere en 0.012s
