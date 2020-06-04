# include <stdlib.h>
# include <stdio.h>
# include <string.h>
/* 
nombre d'enrichissements des erreurs : 1
nombre d'enrichissements du bruit : 0
seed : 1500
*/
int main(){
int error_b;
error_b = ((6 + 106) / 0/*erreur ici*/);
return 0;
}

//Genere en 0.008s
