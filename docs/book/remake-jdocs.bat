javadoc -d jdocs lingu

mkdir jdocs

cd jdocs

dir /S/B *.html > files.txt

fart --verbose --remove files.txt "%cd%\\"

copy /Y ..\empty.book+files.txt lingu.book

del files.txt