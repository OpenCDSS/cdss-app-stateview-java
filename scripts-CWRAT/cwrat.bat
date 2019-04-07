@echo off
rem The HOMED and JREHOMED variables are set during the installation process.
rem The names are hopefully unique enough that they do not conflict with other
rem software settings.
rem 
rem Guidelines for setting HOMED and JREHOMED:
rem
rem 1)If software is installed on a server and the location is consistently
rem   mapped to the same drive letter for all users and computers, specify an
rem   absolute path to the location.  This allows the software to be run from
rem   other drives and still find the install location for configuration and
rem   log files.  For example, use:
rem
rem      HOMED=P:\CDSS
rem      JREHOMED=P:\CDSS\jre_142
rem
rem   It is also possible to use a UNC (Universal Naming Convention) to indicate
rem   the installation point.  This can be used when a drive letter is not
rem   always mapped but a machine provides access to the software files.  For
rem   example:
rem
rem      HOMED=\\CDSSServer\CDSS
rem      JREHOMED=\\CDSSServer\CDSS\jre_142
rem
rem 2)If software is installed on a local drive (e.g., C:) or a CD drive
rem   (e.g., D:), and the software will ALWAYS be run from the drive where the
rem   software are installed, the drive letter can be omitted from the
rem   variables.  This allows the software to be installed/copied generically
rem   without further configuration.  If the software is run from a different
rem   drive, the software may not be able to find supporting files or open a
rem   log file.  For example, use:
rem
rem	HOMED=\CDSS
rem	JREHOMED=\CDSS\jre_142
rem
rem 3)If the software is installed on a local drive (e.g., C:) but may be run
rem   from a different drive, use approach (1).

SET HOMED=\CDSS
SET JREHOMED=%HOMED%\jre_142

rem Run the Java Runtime Environment (JRE), which runs the CWRAT software.
rem If there is an error, try using "java" instead of "javaw" below.  Using
rem "java" will not hide the command shell window and therefore allows more
rem troubleshooting.

rem Available command-line options that can be typed at run-time are:
rem
rem   -datasource OdbcDsn	Specify the ODBC DSN to be selected in the
rem				Select HydroBase dialog for logins.  Access will
rem				be selected as the default database engine.
rem   -home CDSSInstallDir	Normally \cdss or a variation as discussed
rem				above - included below but can be overridden
rem				on the command line.
rem   -user ID			The user identifier that will default in the
rem				Select HydroBase dialog.
rem   -usesp			Indicates that stored procedures will be used
rem				if available - use to test and phase in
rem				stored procedures.
rem   -viewonly true|false      If true, then StateView is run.  If false, then
rem				CWRAT is run.

"%JREHOMED%\bin\javaw" -Xmx256m -cp "%HOMED%\bin\CWRAT_142.jar;%HOMED%\bin\HydroBaseDMI_142.jar;%HOMED%\bin\SatmonSysDMI_142.jar;%HOMED%\bin\msbase.jar;%HOMED%\bin\mssqlserver.jar;%HOMED%\bin\msutil.jar;%HOMED%\bin\RTi_Common_142.jar;TS_Services.jar" DWR.DMI.CWRAT.CWRAT -home "%HOMED%" %1 %2 %3 %4 %5 %6 %7 %8 %9

rem Clean up the temporary environment variables so that memory is freed for
rem other applications...

set HOMED=
set JREHOMED=
