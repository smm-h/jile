javadoc -d jdocs nilex

mkdir jdocs

cd jdocs

dir /S/B *.html > files.txt

fart --verbose --remove files.txt "%cd%\\"

copy /Y ..\empty.book+files.txt nilex.book

del files.txt