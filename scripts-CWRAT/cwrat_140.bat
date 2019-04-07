@echo off
rem ---------------------------------------------------------------------------
rem Batch Program to run the CWRAT as a stand alone application
rem ---------------------------------------------------------------------------
rem Notes:	(1)	This batch file is updated during the installation
rem			process, with the environment settings taken from
rem			the process, as follows:
rem ---------------------------------------------------------------------------
rem History:
rem
rem 01 May 1998	Steven A. Malers,	Update for CWRAT 01.00.00 release.
rem		Chad G. Bierbaum,
rem		Riverside Technology,
rem		inc.
rem 09 Feb 1999	SAM, RTi		Clear environment variables before
rem					exiting.
rem 26 Apr 1999	SAM, RTi		Update for Java 1.2.  Start using JAR
rem					files for java code.
rem 02 Jun 1999	SAM, RTI		Back down to Java 1.1.8 but still use
rem					Jar files.
rem ---------------------------------------------------------------------------

rem The following are set during the installation process...
rem The names are hopefully unique enough that they do not conflict with other
rem software settings...

SET ARCHIVE_DBHOST=hbserver.riverside.com
rem SET ARCHIVE_DBHOST=greenmtn.state.co.us
SET BROWSER=%programfiles%\Internet Explorer\iexplore.exe
SET DBHOST=localpc
SET DOCHOME=http://cdss.state.co.us/manuals
SET HOMED=j:\CDSS
SET JREHOMED=i:\develop\jdk_140\jre

rem On some Win 98 machines, if you get Access Denied errors, change the
rem jrew below to jre.

rem %JREHOMED%\bin\jrew -mx64m -cp %HOMED%\bin\CWRAT.jar;%HOMED%\bin\RTi.jar;%HOMED%\bin\HB.jar;%HOMED%\bin\HBGUI.jar;%HOMED%\bin\Informix.jar;%HOMED%\bin\RogueWave.jar;%HOMED%\bin\Symantec.jar;%HOMED%\bin\Visualizeinc.jar; DWR.DMI.CWRAT.CWRAT -home %HOMED% -helpindex %DOCHOME%\cwrat\CWRAT_help_index.txt -dbhost %DBHOST% -browser %BROWSER% -archive_dbhost %ARCHIVE_DBHOST% -datasource HYDROBASE_DIV1

rem %JREHOMED%\bin\jrew -mx256m -Djava.compiler=NONE -cp %HOMED%\bin\CWRAT.jar;%HOMED%\bin\RTi.jar;%HOMED%\bin\HB.jar;%HOMED%\bin\HBGUI.jar;%HOMED%\bin\Informix.jar;%HOMED%\bin\Una2000.jar;%HOMED%\bin\RogueWave.jar;%HOMED%\bin\Symantec.jar;%HOMED%\bin\Visualizeinc.jar; DWR.DMI.CWRAT.CWRAT -home %HOMED% -dbhost %DBHOST% -archive_dbhost %ARCHIVE_DBHOST%

@echo on
%JREHOMED%\bin\java -mx512m -Djava.compiler=NONE -cp \cdss\develop\apps\CWRAT\classes_140;i:\develop\gis\libGeoViewJava\classes_140;i:\develop\gr\libGRJava\classes_140;i:\develop\classes_140;\cdss\develop\classes_140;i:\develop\dmi\sql2000\lib\msbase.jar;i:\develop\bin\una2000.jar DWR.DMI.CWRAT.CWRAT -home %HOMED% -test %1 %2 %3 %4
@echo off

rem Clear the environment variables...

set ARCHIVE_DBHOST=
set BROWSER=
set DBHOST=
set DOCHOME=
set HOMED=
set JREHOMED=
