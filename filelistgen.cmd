@echo Off
echo Starting...

set wp=%1%
echo wp
if dir %1% /b =="File Not Found"
dir . /b 
start java Duplicate
echo "Java Run"
rem dir /s /b > files.txt