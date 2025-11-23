@echo off
setlocal

set "JAR="
for /f "delims=" %%F in ('dir /b /a:-d ".\target\*-shaded.jar" 2^>nul') do set "JAR=.\target\%%F"

if not defined JAR (
  if exist mvnw.cmd (
    echo [INFO] No shaded jar found. Building...
    call mvnw.cmd -q -DskipTests package
    if errorlevel 1 (
      echo [ERROR] Build failed.
      exit /b 1
    )
    for /f "delims=" %%F in ('dir /b /a:-d ".\target\*-shaded.jar" 2^>nul') do set "JAR=.\target\%%F"
    if not defined JAR (
      echo [ERROR] Build completed but shaded jar not found in .\target
      exit /b 1
    )
  ) else (
    echo [ERROR] No shaded jar found and mvnw.cmd is missing. Cannot build.
    exit /b 1
  )
)

set "JAVA_EXE=java"
if defined JAVA_HOME set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
where "%JAVA_EXE%" >nul 2>&1
if errorlevel 1 (
  echo [ERROR] Java not found. Install JDK 21+ or set JAVA_HOME.
  exit /b 1
)

echo Running %JAR% ...
"%JAVA_EXE%" -jar "%JAR%" %*
endlocal