TD_maven — Tower Defense (Maven)

Overview
This is a simple Tower Defense game built with Java and Maven. The project produces a shaded (fat) JAR so you can run the game without manually managing dependencies.

Prerequisites
- Java 21 (JDK 21) is required to build and run.
  - Verify: java -version should report version 21.x
  - If java is not found, install JDK 21 and/or set JAVA_HOME to your JDK 21 installation.
- Windows (run.bat is a Windows batch script).

Quick start (recommended)
1) Clone or download the repository.
2) From the project root, double‑click run.bat or run it from a terminal (PowerShell/CMD):
   .\run.bat
3) First run: The script will build the shaded JAR (using the Maven Wrapper) if it is not present.
4) Second run: Launches the game.

Notes
- If you already see target\*-shaded.jar present, a single run will start the game.
- The script looks for JDK 21 on PATH or via JAVA_HOME. If JAVA_HOME is set, it will use %JAVA_HOME%\bin\java.exe.

Alternative: build and run manually (advanced)
If you prefer running commands yourself:
- Build shaded JAR using the Maven Wrapper (no global Maven required):
  .\mvnw.cmd -DskipTests package
  The shaded JAR will be created under target\TD_maven-<version>-shaded.jar.
- Run the game:
  java -jar .\target\TD_maven-1.0-SNAPSHOT-shaded.jar
  (Adjust the file name if the version changes.)

Troubleshooting
- "Java not found" or version is not 21:
  - Install JDK 21 from your preferred vendor (Oracle, Microsoft, Temurin, etc.).
  - Ensure JAVA_HOME points to the JDK 21 directory and that %JAVA_HOME%\bin is on PATH.
- "Build completed but shaded jar not found":
  - Ensure the build finished successfully. Re-run .\mvnw.cmd -DskipTests package and check the target folder.
- Permission issues running scripts:
  - If PowerShell blocks scripts, you can run from CMD or adjust execution policy temporarily with: powershell -ExecutionPolicy Bypass -File .\run.bat (or just double‑click run.bat from Explorer).

Project structure (high level)
- run.bat — helper script to build (if needed) and run the shaded JAR on Windows.
- mvnw / mvnw.cmd — Maven Wrapper scripts; used by run.bat to build.
- pom.xml — Maven configuration (including shaded JAR setup).
- src\main\java — game source code.
- target — build outputs, including the shaded JAR after packaging.
