import sys
import json
#python3 error_checker.py `mopsa-c tests/test1.c -format=json`

list_errs = ["Double free","Division by zero"]

stmp = ' '.join(sys.argv[4:])
s = json.loads(stmp)["alarms"]


liste_err_mopsa = [t["title"] for t in s]
errline_mopsa = [t["range"]["start"]["line"] for t in s]

errline_recup = sys.argv[3]

fileName = sys.argv[2]

succes = False
for i in range(len(liste_err_mopsa)):
	if list_errs[int(sys.argv[1])] == liste_err_mopsa[i]:
		if int(errline_mopsa[i]) == int(errline_recup):
			succes = True

if succes:
	print("Success : ",fileName)
else:
	print("Fail : ",fileName)
print("Expected :",list_errs[int(sys.argv[1])],"Line",errline_recup)
print("Found :")
listePrint = [ str(liste_err_mopsa[i])+" Line "+str(errline_mopsa[i]) for i in range(len(s))]
for i in listePrint:
	print("\t"+i)

"""
suite : 
compter le nombre d'erreurs trouvées par mopsa
recuperer les erreurs directement dans le script 
comparer les erreurs trouvees/attendues
print la difference
"""
