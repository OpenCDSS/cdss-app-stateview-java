//------------------------------------------------------------------------------
// CWRAT -	Main program class which is responsible for starting either an
//		applet or stand alone GUIs.
//------------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//------------------------------------------------------------------------------
//  Notes:
//     (1)Code from this level down should be able to run in an
//      applet, or as an application.
//------------------------------------------------------------------------------
// History:
//
// 01 Aug 1997	DLG, RTi	 	Created initial class description.
// 14 Apr 1998	DLG, RTI		Added javadoc comments.
// 08 May 1998	DLG, RTi		Added TimeUtil.setLocalTimeZone("MST")
//					in the initialize routine.
// 05 Apr 1999	Steven A. Malers, RTi	Take out the call to TimeUtil since the
//					time zone works in Java 1.2.  Change
//					the main graphic from crdss.gif to
//					cdss.gif.
// 13 Apr 1999	SAM, RTi		Call IO.setProgramData.
// 21 Apr 1999	SAM, RTi		Initialize the data units for later
//					conversions and displays.
// 06 Jun 2000	SAM, RTi		Change so that cwrat.gif and
//					StateView.gif are used for the graphics
//					file names.
// 14 Jun 2001	SAM, RTi		Change IO to IOUtil.
// 2001-10-19	SAM, RTi		Update to version 02.06.00.
// 2001-11-01	SAM, RTi		Update to version 02.07.00.  Use
//					RTi.Util.GUI.HelpAboutDialog instead of
//					HBAboutCWRATGUI so move the version
//					here.
// 2002-04-30	SAM, RTi		Update to version 02.07.01.  Change the
//					login to show the database type only if
//					running in test mode.
// 2002-05-06	SAM, RTi		Update to version 02.07.02.  Change the
//					login to use footnotes as per Ray
//					Bennett request.  Open the log file
//					with a name that includes the user login
//					(from the system, not the login dialog).
//					Set the application name earlier so that
//					the log file header can reflect.
// 2002-06-04	SAM, RTi		Update to version 02.07.03.  Change so
//					that well permits are only enabled when
//					logging in as non-guest to remote
//					machine.
// 2002-06-19	SAM, RTi		Update to version 02.07.04.  Finalize
//					diversion coding.
// 2002-08-19	SAM, RTi		Update to version 02.08.00.  Fix so that
//					when doing a map select if a map window
//					is not visible, display it.  Also make
//					so that a point select on the map does
//					nothing(a box is required).  Add water
//					source for net rights.
//					Fix bug where county was not being
//					displayed correctly in net water right
//					detail.
// 2003-02-12	SAM, RTi		Update to version 02.09.00.
//					* Fix so well permit MultiList
//					  information, when exported, does
//					  contain the UTM and geographic
//					  coordinates.
//					* Stub in the on-line help to refer to
//					  the PDF document.
//					* Fix problem where a well permit with
//					  long comment was making its GUI too
//					  wide.
//					* Fix problem where threaded query
//					  progress bar had been disabled.
//------------------------------------------------------------------------------
// 2003-03-11	J. Thomas Sapienza, RTi	Moved to use Swing.
// 2003-03-14	JTS, RTi		Fixed so the background image loads.
// 2003-04-09	JTS, RTi		Fixed argument parsing.
// 2003-05-19	JTS, RTi		-test no longer takes an additional 
//					argument.
// 2003-10-08	JTS, RTi		Added code to set an icon.
// 2003-10-22	JTS, RTi		Log file now contains the user name.
// 2004-01-11	SAM, RTi		Update version to 03.00.00 Beta for
//					release to the State.
//					* Change the icon to not use the RTi
//					  icon but use the State's icon(s).
//					* Put a try around all the startup code
//					  to better handle startup problems.
// 2004-05-17	JTS, RTi		Updated to version 03.01.00.
// 2004-06-09	SAM, RTi		Updated to version 03.02.00.
//					* Include a number of changes based on
//					  internal review and review by the
//					  State.
//					* This is an official release for
//					  StateView and CWRAT.
// 2004-06-22	JTS, RTi		Added -usesp command-line flag.
// 2004-07-12	SAM, RTi		Update to version 03.03.00
//					* Includes stored procedure support.
//					* Review all data displays to make sure
//					  there are no busts due to stored
//					  procedure rework.
//					* Also are using new Microsoft SQL
//					  Server driver Jars.
// 2004-07-19	JTS, RTi		Added the -datasource command-line
//					argument.
// 2004-07-26	JTS, RTi		Added the -div command-line argument.
// 2004-07-28	SAM, RTi		Update to version 03.04.00 to reflect
//					recent changes.
//					* Fixed a number of minor issues based
//					  on documentation review.
// 2004-08-04	JTS, RTi		* Added the -release command-line
//					  argument.
// 2004-08-08	SAM, RTi		Update to version 03.05.00.
//					* Includes the above JTS change.
//					* Add tools to display current
//					  conditions on the map.
//					* Include features to select
//					  precipitation and temperature stations
//					  on the map.
// 2004-12-01	SAM, RTi		Update to version 03.05.01.
//					* HydroBaseDMI code now closes
//					  statements to minimize connections.
//					* Fixed bug in diversion coding display
//					  where the period was not getting
//					  computed properly.
// 2005-01-12	JTS, RTi		Updated to version 03.06.00.
//					* InputFilters implemented on query
//					  screens.
//					* Many small fixes on same screens.
// 2005-02-02	JTS, RTi		Added a flag to tell whether -useSP
//					was provided on the command line.
// 2005-06-02	SAM, RTi		Change version to 03.06.00 BETA for
//					initial release.
// 		JTS, RTI		* Message levels are now set the same
//					  as TSTool and StateDMI.
// 2005-06-28	SAM, RTi		Change version to 03.06.01 BETA for
//					another beta release.
// 2005-07-08	SAM, RTi		Change version to 03.06.02 BETA for
//					another beta release.
// 2005-07-20	SAM, RTi		Change version to 03.06.03 for an
//					initial release.
// 2005-07-28	SAM, RTi		Change version to 03.06.04 for final
//					release - should be able to put on CD.
// 2005-08-04	SAM, RTi		Change version to 03.06.05 for yet
//					another final release - fix TS plot and
//					WIS builder issues.
// 2005-08-16	SAM, RTi		Change version to 03.06.06.
//					* Fix minor problem copying calls.
// 2005-09-30	SAM, RTi		Change version to 03.06.07.
//					* Fix bug where diversion coding time
//					  series period always ended on day 1.
//					* Fix adjudication type output in the
//					  transaction section of the summary to
//					  match historical reports.
// 2005-11-15	JTS, RTi		Change version to 03.07.00.
//					* div field added to most displays.
//					* Entire state can now be searched at
//					  once on most displays.
//					* Database version is now 20051115.
// 2006-01-23	SAM, RTi		Change version to 03.08.00.
//					* JTS updated edit calls to allow a
//					  structure query for bypass structures.
// 2006-04-27	SAM, RTi		Change version to 03.08.01.
//					* Enable carry forward filling for
//					  daily diversion/reservoir records.
//					* Fix bug in WIS where dates for sheets
//					  were not being listed in the correct
//					  order.
//					* Other various maintenance.
// 2006-08-15	SAM, RTi		Change version to 03.08.02.
//					* Add -d command line option so that
//					  debug messages can be printed at
//					  startup to diagnose HydroBase logins.
// 2006-11-08	KAT, RTi		Changed how the -home parameter
//					  is handled by using getCanonicalPath().
// 2007-02-18	SAM, RTi		* Clean up based on Eclipse feedback.
//					* Change version to 04.00.00 to reflect major rework in
//					  development system, software packaging.
//					* Support new HydroBase SFUT(G) convention.
//					* Remove "Use Stored Procedures" checkbox from Hydrobase login.
//------------------------------------------------------------------------------
// EndHeader

package DWR.DMI.CWRAT;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.swing.JApplet;

import DWR.DMI.HydroBaseDMI.SelectHydroBaseJDialog;

import RTi.Util.GUI.JGUIUtil;

import RTi.Util.IO.DataUnits;
import RTi.Util.IO.IOUtil;

import RTi.Util.Message.Message; 
import RTi.Util.Message.MessageEventQueue;

import RTi.Util.String.StringUtil; 

public class CWRAT extends JApplet
{
/**
Install home for the application (e.g., C:\CDSS\StateView-Version), used to locate other files.
Should be supplied by the Java launcher.
*/
private static String __home;
/**
User that is running the program (from a system login standpoint - can be different than the
HydroBase user).
*/
private static String __user;
/**
Program command line arguments.
*/
private static String[] __args;
/**
Indicates whether StateView is being run (with -viewonly true command line parameter).
*/
private static boolean __runningStateView = false;
/**
Indicates whether stored procedures are being used for HydroBase.
*/
private static boolean __useSP = true;
/**
TODO SAM 2010-07-14 How is this used.
*/
protected static boolean _useSPProvided = false;
/**
Application name, will be changed if -viewonly true is specified.
*/
private static String __appName = "CWRAT";
/**
HydroBase data source provided on the command line.
TODO SAM 2010-07-14 Probably can delete but may be used in automated testing.
*/
private static String __datasource = null;
/**
Division provided on the command line.
TODO SAM 2010-07-14 Probably can delete but may be used in automated testing.
*/
private static String __div = null;
/**
The program version for Help...About.
*/
public final static String VERSION = "4.02.02 (2013-04-16)";

/**
Returns the datasource specified on the command line.
*/
public static String getDatasource() {
	return __datasource;
}

/**
Returns the div specified on the command line.
*/
public static String getDiv() {
	return __div;
}

/**
This function instantiates the CWRATMainJFrame object from an applet.
*/
public void init()
{	// Set these first so IOUtil gets initialized correctly...

	IOUtil.setApplet(this);
	IOUtil.setAppletContext(getAppletContext());
	IOUtil.testing(false);

	try {
		parseArgs(this);
	}
	catch (Exception e) {
		Message.printWarning(2, "init()", "Problem reading arguments, exiting gracefully.");
		Message.printWarning(2, "init()", e);
		System.exit(0);
	}	

	Message.printStatus(1, __appName + " applet", "Running applet version.");

	IOUtil.setProgramData(__appName, VERSION, __args);

	// More processing to detect the special -test option...

	try {
		// Instantiate Main GUI
		SelectHydroBaseJDialog.setDefaultOdbcDsn(__datasource);
		SelectHydroBaseJDialog.setDefaultDiv(__div);
		new CWRATMainJFrame(__runningStateView, __useSP, null);
	}
	catch (Exception e) {
		Message.printWarning(2, "init()", "Problem starting " + __appName + "; exiting gracefully.");
		Message.printWarning(2, "init()", e);
		System.exit(0);
	}
}

/**
Initialize important data relative to the installation home.
*/
private static void initializeAfterHomeIsKnown ()
{	String routine = __appName + ".initializeAfterHomeIsKnown";

	// Initialize the system data...

	String units_file = __home + File.separator + "system" + File.separator + "DATAUNIT";

	Message.printStatus ( 2, routine, "Reading the units file \"" +	units_file + "\"" );
	try {
        DataUnits.readUnitsFile( units_file );
	}
	catch ( Exception e ) {
		Message.printWarning ( 2, routine,
		"Error reading units file \"" + units_file + "\"\n" +
		"Some conversions will not be supported.\n" +
		"Default output precisions may not be appropriate (" + e + ")." );
	}
}

/**
Initialize important data and set message levels for application after startup.
*/
private static void initializeLoggingLevelsAfterLogOpened ()
{	// Initialize message levels...
    // FIXME SAM 2008-01-11 Need to have initialize2() reset message levels to not show on the console.
	Message.setDebugLevel ( Message.TERM_OUTPUT, 0 );
	Message.setDebugLevel ( Message.LOG_OUTPUT, 0 );
	Message.setStatusLevel ( Message.TERM_OUTPUT, 0 );
	Message.setStatusLevel ( Message.LOG_OUTPUT, 2 );
	Message.setWarningLevel ( Message.TERM_OUTPUT, 0 );
	Message.setWarningLevel ( Message.LOG_OUTPUT, 3 );

	// Indicate that message levels should be shown in messages, to allow
	// for a filter when displaying messages...

	Message.setPropValue ( "ShowMessageLevel=true" );
	Message.setPropValue ( "ShowMessageTag=true" );
}

/**
Initialize important data and set message levels for console startup.
*/
private static void initializeLoggingLevelsBeforeLogOpened ()
{   // Initialize message levels...
    // FIXME SAM 2008-01-11 Need to have initialize2() reset message levels to not show on the console.
    Message.setDebugLevel ( Message.TERM_OUTPUT, 0 );
    Message.setDebugLevel ( Message.LOG_OUTPUT, 0 );
    Message.setStatusLevel ( Message.TERM_OUTPUT, 2 );
    Message.setStatusLevel ( Message.LOG_OUTPUT, 2 );
    Message.setWarningLevel ( Message.TERM_OUTPUT, 3 );
    Message.setWarningLevel ( Message.LOG_OUTPUT, 3 );

    // Indicate that message levels should be shown in messages, to allow
    // for a filter when displaying messages...

    Message.setPropValue ( "ShowMessageLevel=true" );
    Message.setPropValue ( "ShowMessageTag=true" );
}

/**    
This function instantiates the HBCWRATMainGUI object from the command line.
@param args command line arguments.
*/
public static void main(String args[])
{	String routine = __appName + ".main";

	try { // Main try
		initializeLoggingLevelsBeforeLogOpened();
		setWorkingDirInitial ();
		// Defaults to CWRAT.  Set again after parsing arguments because -viewonly true will set to StateView
		IOUtil.setProgramData ( __appName, VERSION, args );
		JGUIUtil.setAppNameForWindows( __appName );
		
		// Set up handler for GUI event queue, for exceptions that may otherwise get swallowed by a JRE launcher
		new MessageEventQueue();
		
		// Note that messages will not be printed to the log file until the log file is opened below.

		initializeLoggingLevelsAfterLogOpened();
		
		try {
	        parseArgs ( args );
	        // The result of this is that the full path to the command file will be set.
	        // Or the GUI will need to start up in the current directory.
		}
		catch ( Exception e ) {
	        Message.printWarning ( 1, routine, "Error parsing command line arguments.  Exiting." );
			Message.printWarning ( 1, routine, e );
			quitProgram ( 1 );
		}

		// Set again since because command line -viewonly option indicates CWRAT or StateView
		IOUtil.setProgramData(__appName, VERSION, __args);
		JGUIUtil.setAppNameForWindows( __appName );
		__user = IOUtil.getProgramUser();
		
		// Read the data units...

		// Set the working directory to the home directory, as per legacy functionality
		IOUtil.setProgramWorkingDir(__home);
		initializeAfterHomeIsKnown ();

		// Set the program icon...

		try {
			if ( __runningStateView ) {
				JGUIUtil.setIconImage( "DWR/DMI/CWRAT/StateViewIcon32.gif");
			}
			else {
				JGUIUtil.setIconImage("DWR/DMI/CWRAT/CWRATIcon32.gif");
			}
		}
		catch ( Exception e ) {
			Message.printWarning(2, routine, e);
			Message.printWarning(2, routine, "Icon could not be loaded.");
		}
		// Instantiate Main GUI
		SelectHydroBaseJDialog.setDefaultOdbcDsn(__datasource);
		SelectHydroBaseJDialog.setDefaultDiv(__div);
		String s = System.getProperty("file.separator");
       	new CWRATMainJFrame(__runningStateView, __useSP, __home + s + "system" + s + __appName + ".gif");
	}
	catch ( Exception e2 ) {
		// Main catch.
		Message.printWarning ( 1, routine, "Error starting " + __appName + " (" + e2 + ")." );
		Message.printWarning ( 1, routine, e2 );
		quitProgram ( 1 );
	}
}

/**
Open the log file.  This should be done as soon as the application home
directory is known so that remaining information can be captured in the log file.
*/
private static void openLogFile ()
{	String routine = __appName + ".openLogFile", __logfile = "";
	String user = IOUtil.getProgramUser();

	if ( IOUtil.isApplet() ) {
		Message.printWarning ( 2, routine, "Running as applet - no " + __appName + " log file opened." );
	}
	else {
	    // FIXME SAM 2008-01-11 need to open log file in user space (e.g., /home/$USER/.StateView/.. on Linux)
	    if ( (__home == null) || (__home.length() == 0) || (__home.charAt(0) == '.')) {
			Message.printWarning ( 2, routine, "Home directory is not defined.  Not opening log file.");
		}
		else {
            if ( (user == null) || user.trim().equals("")) {
				__logfile = __home + File.separator + "logs" + File.separator + __appName + ".log";
			}
			else {
                __logfile = __home + File.separator + "logs" + File.separator + __appName + "_"
                	+ user + ".log";
			}
			Message.printStatus ( 1, routine, "Log file name: " + __logfile );
			try {
                Message.openLogFile ( __logfile );
			}
			catch (Exception e) {
				Message.printWarning ( 1, routine, "Error opening log file \"" + __logfile + "\"");
			}
		}
	}
}

/**
Parse the command line arguments.
@param args command line arguments to the program.
@throws Exception
*/
public static void parseArgs(String[] args)
throws Exception
{	String routine = __appName + ".parseArgs";
	boolean debug = false; // use this to troubleshoot setup with StateView & CWRAT
	PrintWriter out = null;
	if ( debug ) {
		out = new PrintWriter(new FileOutputStream(new File("C:\\junkCWRAT.txt")));
		out.println("This is a debug file for CWRAT/StateView development.");
		out.println("args[] length = " + args.length);
	}
	// Loop through the arguments one time to see if -viewonly true is set.  If so, then the app is
	// StateView.  This is needed to look up the system property for the home directory
	for (int i = 0; i < args.length; i++) {
		System.out.println("CWRAT.parseArgs:  argv[" + i + "]=\"" + args[i] + "\"");
		if ( debug ) {
			out.println("CWRAT.parseArgs:  argv[" + i + "]=\"" + args[i] + "\"");
		}
		if (args[i].equals("-viewonly")) {
			if ((i + 1)== args.length) {
				throw new Exception("No argument provided to '-viewonly'");
			}
			i++;
			System.out.println("CWRAT.parseArgs:  argv[" + i + "]=\"" + args[i] + "\"");
			if ( debug ) {
				out.println("CWRAT.parseArgs:  argv[" + i + "]=\"" + args[i] + "\"");
			}
			if (args[i].equalsIgnoreCase("true")) {
				__runningStateView = true;
				__appName = "StateView";
				System.out.println("CWRAT.parseArgs:  detected -viewonly true, therefore run as StateView");
				if ( debug ) {
					out.println("CWRAT.parseArgs:  detected -viewonly true, therefore run as StateView");
				}
			}
			else if (args[i].equalsIgnoreCase("false")) {
				__runningStateView = false;
				__appName = "CWRAT";
			}
			else {
				throw new Exception("Invalid argument (" + args[i] + ") provided for '-viewonly'");
			}
			break;
		}
	}
	// The Java launcher may not pass the command line arguments as desired so instead allow the application
	// type to be indicated with -Dstateview=true.
	// The following inserts the passed value into the args array to make sure that StateView is detected
	String appPropName = "stateview";
	String appPropValue = System.getProperty(appPropName);
    System.out.println ( "StateView indicator from -DStateView=" + appPropValue +
    	" - adding args -viewonly true" );
    Message.printStatus ( 1, routine, "StateView indicator from -DStateView=" + appPropValue +
    	" - adding args -viewonly true" );
    if ( debug ) {
    	out.println ( "StateView indicator from -DStateView=" + appPropValue + " - adding args -viewonly true" );
    }
	if ( appPropValue != null ) {
	    String[] extArgs = new String[args.length + 2];
	    System.arraycopy(args, 0, extArgs, 0, args.length);
	    extArgs[args.length] = "-viewonly";
	    extArgs[args.length + 1] = "true";
	    __appName = "StateView"; // Needed here because -viewonly true is not parsed below
	    __runningStateView = true;
	    args = extArgs;
	}
	// Allow setting of -home via system property "tstool.home". This
	// can be supplied by passing the -Dcwrat.home=HOME option (or -Dstateview.home=HOME) to the java vm.
	// Typically this is "xxx/bin/.." because the Java launcher knows that the executable is in the bin folder.
	// The following inserts the passed values into the front of the args array to
	// make sure that the install home can be considered by following parameters.
	String homePropName = __appName.toLowerCase() + ".home";
	String homePropValue = System.getProperty(homePropName);
    System.out.println ( "Home directory for " + __appName + " passed in from -D" +
    	homePropName + "=" + homePropValue );
    Message.printStatus ( 1, routine, "Home directory for " + __appName + " passed in from -D" +
    	homePropName + "=" + homePropValue );
    if ( debug ) {
    	out.println ( "Home directory for " + __appName + " passed in from -D" +
    	    homePropName + "=" + homePropValue );
    }
	if ( homePropValue != null ) {
	    String[] extArgs = new String[args.length + 2];
	    System.arraycopy(args, 0, extArgs, 2, args.length);
	    extArgs[0] = "-home";
	    extArgs[1] = homePropValue;
	    args = extArgs;
	}
	__args = args;
	for (int i = 0; i < __args.length; i++) {
		if (__args[i].equals("-datasource")) {
			if ((i + 1)== __args.length) {
				throw new Exception("No argument provided to '-datasource'");
			}
			i++;
			__datasource = __args[i];
		}
		else if (__args[i].equals("-div")) {
			if ((i + 1)== __args.length) {
				throw new Exception("No argument provided to '-div'");
			}
			i++;
			__div = __args[i];
		}
		else if ( args[i].regionMatches(true,0,"-d",0,2)) {
			// Set debug information...
			if ((i + 1)== args.length) {
				// No argument.  Turn terminal and log file debug on to level 1...
				Message.isDebugOn = true;
				Message.setDebugLevel ( Message.TERM_OUTPUT, 1);
				Message.setDebugLevel ( Message.LOG_OUTPUT, 1);
			}
			i++;
			if ( args[i].indexOf(",") >= 0 ) {
				// Comma, set screen and file debug to different levels...
				String token = StringUtil.getToken(args[i],",",0,0);
				if ( StringUtil.isInteger(token) ) {
					Message.isDebugOn = true;
					Message.setDebugLevel (Message.TERM_OUTPUT,StringUtil.atoi(token) );
				}
				token=StringUtil.getToken(args[i],",",0,1);
				if ( StringUtil.isInteger(token) ) {
					Message.isDebugOn = true;
					Message.setDebugLevel (Message.LOG_OUTPUT, StringUtil.atoi(token) );
				}
			}
			else {
				// No comma.  Turn screen and log file debug on to the requested level...
				if ( StringUtil.isInteger(args[i]) ) {
					Message.isDebugOn = true;
					Message.setDebugLevel (Message.TERM_OUTPUT,StringUtil.atoi(args[i]) );
					Message.setDebugLevel (Message.LOG_OUTPUT,StringUtil.atoi(args[i]) );
				}
			}
		}
		else if (__args[i].equals("-home")) {
		    // Should be specified in batch file or script that runs the software, or in properties for
	        // a executable launcher.  Therefore this should be processed before any user command line
	        // parameters and the log file should open up before much else is done.
			if ((i + 1)== args.length) {
				Message.printWarning(1,routine, "No argument provided to '-home'");
				throw new Exception("No argument provided to '-home'");
			}
			i++;
           
            //Changed __home since old way wasn't supporting relative paths __home = args[i];
            __home = (new File(args[i])).getCanonicalPath().toString();
           
			// Open the generic log file so that remaining messages will be seen in the log file...
			openLogFile();
			Message.printStatus ( 1, routine, "Home directory for " + __appName +
				" from -home command line parameter is \"" + __home + "\"" );
			// Don't call setProgramWorkingDir or setLastFileDialogDirectory this since we set to user.dir at startup
			//IOUtil.setProgramWorkingDir(__home);
			//JGUIUtil.setLastFileDialogDirectory(__home);
			IOUtil.setApplicationHomeDir(__home);
			// Also reset the java.library.path system property to include the application
			// home + "/bin" so that DLLs installed with the software (if any) are found
			String javaLibraryPath = System.getProperty ( "java.library.path" );
			System.setProperty( "java.library.path",
			         __home + "/bin" + System.getProperty("path.separator") + javaLibraryPath );
			Message.printStatus( 2, routine, "Reset java.library.path to \"" +
			        System.getProperty ( "java.library.path" ) + "\"" );
		}
		else if (__args[i].equals("-user")) {
			if ((i + 1)== __args.length) {
				throw new Exception("No argument provided to '-user'");
			}
			i++;
			__user = __args[i];
			IOUtil.setProgramUser(__user);			
		}
		else if (__args[i].equals("-test")) {
			IOUtil.testing(true);
		}
		else if (__args[i].equals("-usesp")) {
			__useSP = true;
			_useSPProvided = true;
		}
	}
	if ( debug ) {
		out.close();
	}
}

public static void parseArgs(JApplet a)
throws Exception {
	String home = a.getParameter("-home");
	String user = a.getParameter("-user");
	String test = a.getParameter("-test");
	String useSP = a.getParameter("-usesp");
	String viewonly = a.getParameter("-viewonly");
	String datasource = a.getParameter("-datasource");
	String div = a.getParameter("-div");

	if (home != null) {
		//__home = home;
		__home = (new File(home)).getCanonicalPath().toString();
		IOUtil.setProgramWorkingDir(__home);		
		// this next call looks weird, but if the working dir
		// does not have a drive letter IOUtil will provide one.
		__home = IOUtil.getProgramWorkingDir();		
		java.lang.System.setProperty("user.dir", __home);		
		JGUIUtil.setLastFileDialogDirectory(__home);
	}

	if (user != null) {
		__user = user;
		IOUtil.setProgramUser(__user);
	}

	if (test != null) {
		IOUtil.testing(true);
	}

	if (useSP != null) {
		__useSP = true;
		_useSPProvided = true;
	}

	if (viewonly != null) {
		if (viewonly.equalsIgnoreCase("true")) {
			__runningStateView = true;
			__appName = "StateView";
		}
		else if (viewonly.equalsIgnoreCase("false")) {
			__runningStateView = false;
			__appName = "CWRAT";
		}
		else {
			throw new Exception("Invalid argument(" + viewonly + ")provided for '-viewonly'");
		}
	}

	if (datasource != null) {
		__datasource = datasource;
	}

	if (div != null) {
		__div = div;
	}
}

/**
Clean up and quit the program.
@param status Program exit status.
*/
public static void quitProgram ( int status )
{	String	routine = __appName + ".quitProgram";

	Message.printStatus ( 1, routine, "Exiting with status " + status + "." );

	//System.out.print( "STOP " + status + "\n" );
	Message.closeLogFile ();
	System.exit ( status ); 
}

/**
Set the working directory as the system "user.dir" property.
*/
private static void setWorkingDirInitial()
{	String routine = __appName + ".setWorkingDirInitial";
    String working_dir = System.getProperty("user.dir");
    IOUtil.setProgramWorkingDir ( working_dir );
    // Set the dialog because if the running in batch mode and interaction with the graph
    // occurs, this default for dialogs should be the home of the command file.
    JGUIUtil.setLastFileDialogDirectory( working_dir );
    String message = "Setting working directory to user directory \"" + working_dir +"\".";
    Message.printStatus ( 1, routine, message );
    System.out.println(message);
}

}