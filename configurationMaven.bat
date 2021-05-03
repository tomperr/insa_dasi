@echo off
set DESTINATION=%userprofile%\.m2
@mkdir %DESTINATION%
echo Copie du fichier XML de configuration Maven vers %DESTINATION%
copy configurationMaven.xml %DESTINATION%\settings.xml

pause
