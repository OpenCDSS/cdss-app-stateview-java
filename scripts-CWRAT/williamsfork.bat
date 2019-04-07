rem ---------------------------------------------------------------------------
rem Batch Program to run the StateView as a stand alone application
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
rem 2003-10-06	J. Thomas Sapienza, RTi	Uses 1.4.2 now.
rem 2004-05-26	JTS, RTi		Removed the una2000.jar and added the
rem 					ms*.jar files for connecting to 
rem 					SQLServer2000.
rem ---------------------------------------------------------------------------

rem The following are set during the installation process...
rem The names are hopefully unique enough that they do not conflict with other
rem software settings...

SET ARCHIVE_DBHOST=hbserver.riverside.com
rem SET ARCHIVE_DBHOST=greenmtn.state.co.us
SET BROWSER=%programfiles%\Internet Explorer\iexplore.exe
SET DBHOST=localpc
SET DOCHOME=http://cdss.state.co.us/manuals
SET HOMED=\\williamsfork\CDSS
SET JREHOMED=\\flatiron\user1\develop\jdk_142\jre

%JREHOMED%\bin\java -mx512m -cp \\hbserver\e\cdss\develop\apps\CWRAT\classes_142;\\flatiron\user1\develop\gis\libGeoViewJava\classes_142;\\flatiron\user1\develop\gr\libGRJava\classes_142;\\flatiron\user1\develop\TS\libTSJava\classes_142;\\flatiron\user1\develop\util\libUtilMessageJava\classes_142;\\flatiron\user1\develop\grts\libGRTSJava\classes_142;\\flatiron\user1\develop\dmi\libDMIJava\classes_142;\\flatiron\user1\develop\util\libUtilIOJava\classes_142;\\flatiron\user1\develop\util\libUtilGUIJava\classes_142;\\flatiron\user1\develop\classes_142;\\hbserver\e\cdss\develop\libHydroBaseDMIJava\classes_142;\\hbserver\e\cdss\develop\classes_142;\\flatiron\user1\develop\bin\msbase.jar;\\flatiron\user1\develop\bin\msutil.jar;\\flatiron\user1\develop\bin\mssqlserver.jar;\\flatiron\user1\develop\bin\RTi_142.jar DWR.DMI.CWRAT.CWRAT -home %HOMED% -viewonly false %1 %2 %3 %4

rem %JREHOMED%\bin\java -mx512m -cp j:\cdss\develop\apps\CWRAT\classes_142;i:\develop\gis\libGeoViewJava\classes_142;i:\develop\gr\libGRJava\classes_142;i:\develop\TS\libTSJava\classes_142;i:\develop\util\libUtilMessageJava\classes_142;i:\develop\grts\libGRTSJava\classes_142;i:\develop\dmi\libDMIJava\classes_142;i:\develop\util\libUtilIOJava\classes_142;i:\develop\util\libUtilGUIJava\classes_142;i:\develop\classes_142;j:\cdss\develop\libHydroBaseDMIJava\classes_142;j:\cdss\develop\classes_142;i:\develop\bin\msbase.jar;i:\develop\bin\msutil.jar;i:\develop\bin\mssqlserver.jar;i:\develop\bin\RTi_142.jar DWR.DMI.CWRAT.CWRAT -home %HOMED% -viewonly false %1 %2 %3 %4
@echo off

rem Clear the environment variables...

set ARCHIVE_DBHOST=
set BROWSER=
set DBHOST=
set DOCHOME=
set HOMED=
set JREHOMED=
