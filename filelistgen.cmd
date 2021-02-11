echo Starting...
set wp=%1%
echo wp
if dir %1% /b =="File Not Found"
dir . /b 
rem dir /s /b > files.txt