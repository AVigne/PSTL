import sys
import json
#python3 error_checker.py `mopsa-c tests/test1.c -format=json`

list_errs = ["Double free","Division by zero"]

stmp = ' '.join(sys.argv[3:])
s = json.loads(stmp)["alarms"]
s = s[0]["title"]

fileName = sys.argv[2]

if list_errs[int(sys.argv[1])] in s:
	print("Success : ",fileName)
else:
	print("Fail : ",fileName)
print("Expected : ",list_errs[int(sys.argv[1])]," \nFound : ",s,"\n")

"""
suite : 
compter le nombre d'erreurs trouvées par mopsa
recuperer les erreurs directement dans le script 
comparer les erreurs trouvees/attendues
print la difference
"""
