@echo off
cd jarbuilder

set package=jile.nilex
set mainclass=Codestack

python package-to-jar.py %package% %mainclass%
python jar-runner.py ../jar/%package%.jar

pause