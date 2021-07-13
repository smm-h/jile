from os import system as run
from sys import argv as args

me, package = args

print("Compiling " + package)

run("cd .. & javac -cp src/ -d bin/ src/" +
    package.replace(".", "/") + "/*.java")
