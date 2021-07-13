from os import system as run
from sys import argv as args

me, package, main = args

run("python package-compiler.py " + package)

filename = package + ".jar"

print("Creating " + filename)


# Make a temporary file to use as the manifest.
manifestfilename = package + "-manifest.mf"
with open(manifestfilename, "w+") as f:
    f.write("Main-Class: " + package + "/" + main + "\n")


# If it exists, delete the previous JAR file.
run("if exist " + filename + " del " + filename)


# Delete all desktop.ini files created by Google Drive because
# they were causing the `jar` tool to complain about duplicate
# zip entries. Ignore the `stderr` output.
run("cd .. & del /A:H /S desktop.ini > nul 2>&1")


# Make the JAR using the temporary manifest file.
run("cd .. & jar cfm jar/" + filename + " jarbuilder/" +
    manifestfilename + " -C bin . -C src res")


# Make a JAR runner script for it
with open("../jar/" + filename + "-runner.bat", "w+") as f:
    f.write("java -jar " + filename + "\npause")


# Delete the temporary manifest file.
run("del " + manifestfilename)
