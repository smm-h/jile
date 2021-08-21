@echo off
cd jarbuilder

set package=jile.lingu
set mainclass=Codestack

@REM set package=jile.vis
@REM set mainclass=ClassTree

@REM set package=jile.common
@REM set mainclass=PercentDecoder

python package-to-jar.py %package% %mainclass%
python jar-runner.py ../jar/%package%.jar

pause