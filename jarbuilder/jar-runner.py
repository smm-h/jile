from os import system as run
from sys import argv as args

me, filename = args

print("Running " + filename)

run("java -jar " + filename)
