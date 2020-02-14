# include stdlib.h
# include string.h
int main(){
int* a;
a = null;
int nb;
nb = 10;
a = malloc(nb*sizeof(int));
free(a);
free(a);
return 0;
}