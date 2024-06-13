@REM echo off
@setlocal

@REM COPYRIGHT LICENSE: This information contains sample code provided in 
@REM source code form. You may copy, modify, and distribute these sample 
@REM programs in any form without payment to IBM for the purposes of 
@REM developing, using, marketing or distributing application programs 
@REM conforming to the application programming interface for the operating 
@REM platform for which the sample code is written. Notwithstanding anything 
@REM to the contrary, IBM PROVIDES THE SAMPLE SOURCE CODE ON AN "AS IS" BASIS 
@REM AND IBM DISCLAIMS ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING, BUT NOT 
@REM LIMITED TO, ANY IMPLIED WARRANTIES OR CONDITIONS OF MERCHANTABILITY, 
@REM SATISFACTORY QUALITY, FITNESS FOR A PARTICULAR PURPOSE, TITLE, AND ANY 
@REM WARRANTY OR CONDITION OF NON-INFRINGEMENT. IBM SHALL NOT BE LIABLE FOR 
@REM ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL OR CONSEQUENTIAL DAMAGES ARISING 
@REM OUT OF THE USE OR OPERATION OF THE SAMPLE SOURCE CODE. IBM HAS NO 
@REM OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS OR 
@REM MODIFICATIONS TO THE SAMPLE SOURCE CODE.
@REM
@REM (C) Copyright IBM Corp. 2005, 2011.
@REM All Rights Reserved. Licensed Materials - Property of IBM.

@REM CONSOLE_ENCODING controls the output encoding used for stdout/stderr
@REM    console - encoding is correct for a console window
@REM    file    - encoding is the default file encoding for the system
@REM    <other> - the specified encoding is used.  e.g. Cp1252, Cp850, SJIS
@REM SET CONSOLE_ENCODING=-Dws.output.encoding=console

set USE_HIGHEST_AVAILABLE_SDK=true
call "setupCmdLine.bat"
set ANT_ARGS=-Dbasedir="%CD%\.." -Dwas_home="%WAS_HOME%"

:setArgs
if "%1"=="" goto doneArgs
set ANT_ARGS=%ANT_ARGS% %1
shift
goto setArgs

:doneArgs


set ANTCLASSPATH=%WAS_HOME%\plugins\*;%WAS_CLASSPATH%

REM Additional jars needed by WSBASamples
set ANTCLASSPATH=%ANTCLASSPATH%;"%WAS_HOME%\lib\j2ee.jar"

REM Additional classpath elements needed by WSBASamples.
set ANTCLASSPATH=%ANTCLASSPATH%;"..\bld\WSBASample\WSBASampleEJB"
set ANTCLASSPATH=%ANTCLASSPATH%;"..\bld\WSBASample\WSBASampleWeb"

REM Ant requires classpath elements to exist before beginning...
call md "..\bld\WSBASample\WSBASampleEJB"
call md "..\bld\WSBASample\WSBASampleWeb"

set WAS_ENDORSED_DIRS="%WAS_HOME%\endorsed_apis;%WAS_ENDORSED_DIRS%"


"%JAVA_HOME%\bin\java" -Djava.endorsed.dirs="%WAS_ENDORSED_DIRS%" %CONSOLE_ENCODING% -Dwas.install.root="%WAS_HOME%"  -classpath %ANTCLASSPATH% com.ibm.ws.bootstrap.WSLauncher org.apache.tools.ant.Main %ANT_ARGS%

@endlocal

