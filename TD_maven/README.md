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
  - Install JDK 21 from your preferred vendor (Oracle jdk-21: https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.exe).
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


Setting JAVA_HOME and PATH on Windows (step‑by‑step)
This project requires JDK 21. The run.bat script will use %JAVA_HOME% if set; otherwise it falls back to java on PATH. You can satisfy either of the following:
- Option A (recommended): Set JAVA_HOME to your JDK 21 installation and ensure %JAVA_HOME%\bin is on PATH.
- Option B: Put the JDK 21 bin directory directly on your PATH.

Identify your JDK folder
- Typical location: C:\Program Files\Java\jdk-21 (or jdk-21.x.x)
- The folder must contain bin\java.exe, lib, include, etc. Do NOT point to a JRE folder.

Option A — GUI (Environment Variables)
1) Open: Start → type "environment variables" → choose "Edit the system environment variables" → click "Environment Variables…".
2) Under System variables (or User variables if you don’t have admin rights):
   - Click New… → Variable name: JAVA_HOME → Variable value: C:\Program Files\Java\jdk-21
   - Click OK.
3) Still in Environment Variables, find the Path variable → Edit… → Add a new entry: %JAVA_HOME%\bin → Move it near the top if you want it to take precedence.
4) Click OK on all dialogs.
5) Close and reopen your terminal/IDE so changes take effect.
6) Verify:
   - In a new terminal, run: echo %JAVA_HOME%
     It should print C:\Program Files\Java\jdk-21
   - Run: java -version
     It should report version 21.x
   - Optional: where java
     Ensure the first entry is from %JAVA_HOME%\bin.

Option B — Command line (PowerShell or CMD)
Note: You must open PowerShell/CMD as Administrator for system-wide changes. User-scope changes don’t require admin but affect only your account. After running these, open a NEW terminal.

- Set JAVA_HOME (System scope):
  PowerShell:
    [Environment]::SetEnvironmentVariable('JAVA_HOME','C:\\Program Files\\Java\\jdk-21',[EnvironmentVariableTarget]::Machine)
  CMD (Admin):
    setx JAVA_HOME "C:\\Program Files\\Java\\jdk-21" /M

- Append %JAVA_HOME%\bin to PATH (System scope, safe append):
  PowerShell:
    $old = [Environment]::GetEnvironmentVariable('Path','Machine')
    if ($old -notmatch 'JAVA_HOME\\\\bin') {
      [Environment]::SetEnvironmentVariable('Path',"$old;%JAVA_HOME%\\bin",'Machine')
    }
  CMD (Admin):
    setx PATH "%PATH%;%JAVA_HOME%\bin" /M
  Tip: If PATH grows too long, prefer the PowerShell approach or use the GUI editor to add a single %JAVA_HOME%\bin entry.

How to switch if multiple JDKs are installed
- Ensure JAVA_HOME points to the desired JDK (e.g., change it from jdk-17 to jdk-21).
- Keep only one Java bin early in PATH, ideally %JAVA_HOME%\bin.
- Verify with: java -version and where java.

If run.bat still cannot find Java
- Make sure you opened a NEW terminal after changing environment variables.
- Confirm that %JAVA_HOME%\bin\java.exe exists and is accessible.
- If you’re using an IDE, restart the IDE so it picks up new environment variables.
