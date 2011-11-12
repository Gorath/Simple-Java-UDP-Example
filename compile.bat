@echo off
echo 	======== Java batch compile for UDP Example ========
echo 	========      (c) Gareth Jones 2011         ========

rem Defines preferred java path 
if defined JAVA_HOME (
if exist "%JAVA_HOME%\bin\javac.exe" goto okJavac
)

rem No java version found, exit.


rem Java version found, continue as normal
:okJavac
echo Java path "%JAVA_HOME%"
"%JAVA_HOME%\bin\javac.exe" -version

rem Compile .\src\\*.java into .\bin\\*.class using libraries in .\lib\\*.jar
subst m: %0\..
rem "%0\.." is the real path of the batch file
pushd m:
cd \
echo Search java sources in .\src\...
dir src\*.java /B/S > javasrc.tmp~
if ERRORLEVEL 1 (
echo Cannot find Java source files in .\src\
goto abort
)
echo Search jar libraries in .\lib\...
if exist lib for /F "usebackq delims==" %%l in (`dir lib\*.jar /B/S`) do echo -classpath %%l >> javasrc.tmp~
echo Compile in .\bin\...
if exist bin rmdir /S /Q bin
mkdir bin
echo on
@"%JAVA_HOME%\bin\javac.exe" -d bin @javasrc.tmp~
@echo off
echo Done compile.

:abort
del javasrc.tmp~
popd
subst m: /d

:end
set JAVA_ROOT=
echo on