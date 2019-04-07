//------------------------------------------------------------------------------
// CWRATMainJFrame - Main GUI for the CWRAT. 
//------------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//------------------------------------------------------------------------------
// Notes:
//     (1)Code from this level down, should be able to run in an
//		applet, or as an application. 
//------------------------------------------------------------------------------
// History: 
// 08 Jul 1997	MJR, RTi		Created initial class description
// 13 Dec 1997	Steven A. Malers, RTi	Add diagnostics menu.
// 06 Feb 1998	DLG, RTi		Added URLHelpGUI to menu
// 12 Feb 1998	DLG, RTi		Added Stations as a menu item view.
//					Update to java 1.1 event model
// 14 Apr 1998  DLG, RTi		Added javadoc comments.
// 15 Apr 1998  DLG, RTi		Changed menu layout and added "..."
//					after menu choices which show other
//					dialogs.
// 16 Apr 1998 	DLG, RTi		More menu changes.
// 11 Feb 1999	Steven A. Malers, RTi	Add try around main application.  Add
//					the database information dialog.
// 03 Apr 1999	SAM, RTi		Add HBDMI to queries.  Start adding
//					enhancements for new database design.
// 21 Apr 1999	SAM, RTi		Add TSUnits call to fill units.
// 02 Jun 1999	SAM, RTi		Add Tools...Database Administration
//					menu to delete old records.
// 03 Apr 2000	CEN, RTi		Add wells menu item.
// 01 May 2000	CEN, RTi		Added user level and 
//					enableMenusBasedOnUserLevel,
//					HBGroundWaterGUI;
// 22 May 2000	SAM, RTi		I asked Catherine to disable well menus
//					for the 2.02 release.  Enable them again
//					based on the user level.
// 06 Jun 2000	SAM, RTi		Change View Data to StateView.
// 03 Jul 2000	SAM, RTi		Change so ground water data is viewable
//					by level 1 user(view only).
// 18 Jul 2000	CEN, RTi		Changed so well permits is viewable
//					by level 1 user; commented out security
//					GUI;  Changed communications menu to
//					not display for view only and moved
//					login GUI to preferences
// 07 May 2001	SAM, RTi		Comment out
//					"Communications...Update Static Data",
//					"Tools...Diversion Record Transfer",
//					and "Tools...Historic Point Flow",
//					"View...Special Projects".
//					Change IO to IOUtil, GUI to GUIUtil.
//					Enable "Tools...Administration Number
//					Calculator" and "Tools...Time Series
//					Plot" for StateView.
// 01 Jul 2001	SAM, RTi		Add File...Show Map menu to start
//					enabling map features.
// 14 Jul 2001	SAM, RTi		Leave File...Show Map since this is a
//					way to get a blank map to build the map
//					but change to add the panel not a frame
//					and add setGeoViewProject, which is
//					called from HBOptionsGUI.  If at startup
//					a GeoView project file specified by
//					"Map.DefaultGeoViewProject" preference
//					is found, display the map as a panel.
// 01 Aug 2001	SAM, RTi		Add some helper methods to let the
//					displays interact with maps.
// 03 Oct 2001	SAM, RTi		Finalize map features.
// 2001-10-17	SAM, RTi		Update so that openGVP() handles "NONE"
//					map name to turn off map.  Take out the
//					"File...Show Map" menu since it is
//					handled under the preferences.
// 2001-11-01	SAM, RTi		Update to use
//					RTi.Util.GUI.HelpAboutDialog instead of
//					HBAboutCWRATGUI.  When a map is enabled
//					and is then disabled, set the main
//					window size to the original size so the
//					default image displays nicely.
//					Completely comment out the
//					HBLoadSpecialProjectGUI - currently not
//					used.
// 2001-12-12	SAM, RTi		Wrap URLHelp call with try/catch to
//					handle some problems that have been
//					seen.  Still working on a final
//					solution.
// 2002-02-22	SAM, RTi		As discussed with Ray Bennett, move
//					menus around to isolate administration,
//					remove menus, and make more consistent
//					with Microsoft Applications.  Rename
//					some variables to make easier to know
//					the type.  Add documentation to the
//					enableMenusBasedOnUserLevel()method
//					to clarify its use.
// 2002-06-04	SAM, RTi		Change so the well permit menu is
//					disabled unless logging in to the
//					remote server as a non-guest.
// 2002-08-19	SAM, RTi		Add a GeoViewListener so that when
//					selects occur in the map the associated
//					query window can be displayed if not
//					already displayed.
//------------------------------------------------------------------------------
// 2003-03-11	JTS, RTi		* Started Transitioning the GUI objects
//					  to Swing.
//					* Began changing the logic to 
//					  use HydroBaseDMI
// 2003-03-12	JTS, RTi		Cleaned up a lot of RE-VISITs
// 2003-03-14	JTS, RTi		Enabled the StructureQuery GUI.
// 2003-03-24	JTS, RTi		* Enabled the Station GUI.
//					* Revised, cleaned after SAM's review.
// 2003-03-25	JTS, RTi		Enabled the Options GUI.
// 2003-03-26	JTS, RTi		* Corrected error that made it unable
//					  to re-login after cancelling a login.
// 2003-03-31	JTS, RTi		Enabled the call chronology gui.
// 2003-04-08	JTS, RTi		* Enabled the Register User GUI.
//					* Enabled the Admin Num Calc GUI.
// 2003-05-05	JTS, RTi		* Added code to read DataUnits from
//					  DATAUNIT file
//					* Changed IOUtil.getFileSeparator to
//					  File.separator.
// 2003-05-15	JTS, RTi		Added getGeoViewJPanel().
// 2003-05-16	JTS, RTi		Enabled the map display.
// 2003-05-19	JTS, RTi		Cleaned up map and background image
//					display issues.
// 2003-05-21	JTS, RTi		* Added new status text fields to bottom
//					  of display
//					* Changed "View Data" to "Data"
// 2003-05-22	JTS, RTi		User preferences are now re-read from 
//					the database after a successful login.
// 2003-05-28	JTS, RTi		Enabled the ground water gui.
// 2003-05-28	JTS, RTi		Enabled the other query gui.
// 2003-06-03	JTS, RTI		TSUnits and TSUnitsDimension changed to
//					DataUnits and DataDimension.
// 2003-10-08	JTS, RTi		Added code to set an icon.
// 2003-11-19	JTS, RTi		Added the WIS Builder GUI.
// 2004-01-08	JTS, RTi		Added Data Dictionary code.
// 2004-01-11	SAM, RTi		* Set interface resizable - map is
//					  difficult to use if not.
//					* Call JGUIUtil.setAppNameForWindows().
//					* Comment out Administration menus:
//					  "Synchronize Data", "Register User",
//					  "HydroBase Administrator" and
//					  "Stream Network".
//					* Remove CWRAT.jtstest reference.
//					* Change some ResponseJDialog calls to
//					  Message.printWarning - no need to use
//					  the more complicated dialogs.
// 2004-06-22	JTS, RTi		Constructor now takes a parameter 
//					specifying whether the database dmi
//					should attempt to use stored procedures.
// 2004-07-27	JTS, RTi		Added setStatusText() so that status bar
//					text sets can be caught and the status
//					bar repainted instantly.
// 2004-07-29	JTS, RTi		* Added code for adding a summary layer
//					  to the GeoView display.
//					* Added __showingGVP boolean to 
//					  determine easily if the GeoView
//					  display is being displayed.
//					* Added code for adding real-time flow
//					  conditions to the map.
// 2005-02-02	JTS, RTi		Changed where setAppNameForWindows()
//					was being called because it was too
//					late for the app name to get into the
//					title of the DiagnosticsJFrame.
// 2005-03-08	JTS, RTi		Corrected error in call of 
//					readStructureGeolocMeasTypeList().
// 2005-04-26	JTS, RTi		Corrected class mis-cast where 
//					Station views should have been used 
//					when using stored procedures.
// 2005-06-02	JTS, RTi		When exiting the application, 
//					DMI.close() is now called.
// 2005-07-07	JTS, RTi		The well permit window was not being
//					closed properly between DMI instances,
//					so that reconnecting to the database
//					would result in the GUI using an old
//					(and now, null) DMI.  Corrected.
// 2005-07-20	SAM, RTi		Update the copyright from 2004 to 2005.
// 2006-01-06	JTS, RTi		Update the copyright from 2005 to 2006.
// 2007-02-18	SAM, RTi		Update to be consistent with HydroBaseDMI package.
//					A number of HydroBaseDMI GUI constructors have new
//					signatures to de-couple CWRAT from HydroBaseDMI.
//					GeoViewUI and OptionsUI are implemented.
//					Clean up code based on Eclipse feedback.
//------------------------------------------------------------------------------
// EndHeader

package DWR.DMI.CWRAT;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.MediaTracker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.io.File;

import java.net.URL;

import java.util.List;
import java.util.Vector;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import DWR.DMI.HydroBaseDMI.HydroBaseDMI;
import DWR.DMI.HydroBaseDMI.HydroBase_GUI_AdminNumCalculator;
import DWR.DMI.HydroBaseDMI.HydroBase_GUI_CallsQuery;
import DWR.DMI.HydroBaseDMI.HydroBase_GUI_GroundWaterQuery;
import DWR.DMI.HydroBaseDMI.HydroBase_GUI_LoadWIS;
import DWR.DMI.HydroBaseDMI.HydroBase_GUI_Options;
import DWR.DMI.HydroBaseDMI.HydroBase_GUI_OtherQuery;
import DWR.DMI.HydroBaseDMI.HydroBase_GUI_RegisterUser;
import DWR.DMI.HydroBaseDMI.HydroBase_GUI_SelectTSProduct;
import DWR.DMI.HydroBaseDMI.HydroBase_GUI_StationQuery;
import DWR.DMI.HydroBaseDMI.HydroBase_GUI_StructureQuery;
import DWR.DMI.HydroBaseDMI.HydroBase_GUI_Util;
import DWR.DMI.HydroBaseDMI.HydroBase_GUI_WaterRightsQuery;
import DWR.DMI.HydroBaseDMI.HydroBase_GUI_WellApplicationQuery;
import DWR.DMI.HydroBaseDMI.HydroBase_StationGeolocMeasType;
import DWR.DMI.HydroBaseDMI.HydroBase_StationView;
import DWR.DMI.HydroBaseDMI.GeoViewUI;
import DWR.DMI.HydroBaseDMI.SelectHydroBaseJDialog;
import DWR.DMI.HydroBaseDMI.OptionsUI;

import RTi.DMI.DMIUtil;

import RTi.GIS.GeoView.GeoRecord;
import RTi.GIS.GeoView.GeoViewJPanel;
import RTi.GIS.GeoView.GeoViewListener;

import RTi.GR.GRLimits;
import RTi.GR.GRPoint;
import RTi.GR.GRShape;

import RTi.TS.IrregularTS;
import RTi.TS.TS;

import RTi.Util.IO.DataUnits;
import RTi.Util.IO.DataDimension;

import RTi.Util.GUI.HelpAboutJDialog;
import RTi.Util.GUI.JFileChooserFactory;
import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.PictureJPanel;
import RTi.Util.GUI.ReportJFrame;
import RTi.Util.GUI.ResponseJDialog;
import RTi.Util.GUI.SimpleFileFilter;
import RTi.Util.GUI.SimpleJMenuItem;

import RTi.Util.IO.IOUtil;
import RTi.Util.IO.PropList;

import RTi.Util.Message.DiagnosticsJFrame;
import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Table.DataTable;
import RTi.Util.Table.TableField;
import RTi.Util.Table.TableRecord;

import RTi.Util.Time.DateTime;

/**
Main JFrame for running the CWRAT/StateView application.
*/
public class CWRATMainJFrame extends JFrame 
implements ActionListener, GeoViewListener,
GeoViewUI,	// To listen to map selects in child windows.
OptionsUI,	// To handle HydroBase options changes
WindowListener {

/**
Menu Strings.
*/
private final String
	__FILE = "File",
    __FILE_OPEN = "Open...",
	__FILE_PROPERTIES = "Properties...",
	__FILE_SP = "Stored Procedures",
	__FILE_EXIT = "Exit",

__VIEWDATA = "Data",
	__VIEWDATA_CALLS = "Call Chronology...",
	__VIEWDATA_GROUNDWATER = "Ground Water (e.g., pumping test) ...",
	__VIEWDATA_OTHER = "Other (e.g., crop growth) ...",
	__VIEWDATA_STATIONS = "Stations (e.g., stream gage) ...",
	__VIEWDATA_STRUCTURES = "Structures (e.g., diversions) ...",
	__VIEWDATA_WATER_RIGHTS = "Water Rights...",
	__VIEWDATA_WELLS = "Well Permits...",

__ADMINISTRATION = "Administration",
	__ADMINISTRATION_EDIT_CALLS = "Edit Calls...",
	__ADMINISTRATION_LOAD_WIS = "Water Information Sheet...",

	__ADMINISTRATION_DATA_SYNC = "Synchronize Data...",
	__ADMINISTRATION_REGISTER = "Register User...",

	__ADMINISTRATION_DATABASE_ADMIN = "HydroBase Administrator...",
	__ADMINISTRATION_STREAM_NETWORK = "Stream Network...",
	__ADMINISTRATION_WIS_BUILDER = "Water Information Sheet Builder...",

__TOOLS = "Tools",
	__TOOLS_ADMIN_NUM_CALC = "Administration Number Calculator...",
	__TOOLS_DIV_TRANSFER = "Diversion Record Transfer...",
	__TOOLS_POINT_FLOW = "Historic Point Flow...",
	__TOOLS_TS_PRODUCT = "Time Series Plots...",

	__TOOLS_ADD_SUMMARY = "Add Summary Layer to Map ...",
	__TOOLS_ADD_FLOW = "Add Current Flow Conditions to Map ...",

	__TOOLS_OPTIONS	= "Options...",
	__TOOLS_DATA_DICTIONARY = "Create HydroBase Data Dictionary...",

__HELP = "Help",
	__HELP_ABOUT_CWRAT = "About CWRAT...",
	__HELP_ABOUT_STATEVIEW = "About StateView...",
	__Help_ViewDocumentation_String = "View Documentation",
	__Help_ViewTrainingMaterials_String = "View Training Materials";

/**
Whether the currently-logged in user is the guest user or not.
*/
private boolean __isGuest = true;

/**
Whether the map is visible or not.
*/
private boolean __mapVisible = false;

/**
Whether the app is running as StateView (true) or CWRAT (false).
*/
private boolean __runningStateView;

/**
Whether the GeoView display is being shown.
*/
private boolean __showingGVP = false;

/**
Whether the DMI should use stored procedures or not.
*/
private boolean __useSP = false;

/**
JPanel for showing the geo view information.
*/
private GeoViewJPanel __geoViewJPanel;

/**
Reference to the dmi used for communicating with the database.
*/
private HydroBaseDMI __dmi;

/**
Calls GUI.
*/
private HydroBase_GUI_CallsQuery __callsGUI;

/**
Edit calls GUI.
*/
private HydroBase_GUI_CallsQuery __editCallsGUI;

/**
GUI for querying ground water data.
*/
private HydroBase_GUI_GroundWaterQuery __hydroBaseGroundWaterGUI;

/**
Options GUI.
*/
private HydroBase_GUI_Options __hydroBaseOptionsGUI;

/**
GUI for querying other data.
*/
private HydroBase_GUI_OtherQuery __hydroBaseOtherGUI;

/**
GUI for registering a user.
*/
private HydroBase_GUI_RegisterUser __registerGUI;		// Register GUI

/**
GUI for running Station queries.
*/
private HydroBase_GUI_StationQuery __hydroBaseStationQueryGUI;

/**
GUI for running structure queries.
*/
private HydroBase_GUI_StructureQuery __hydroBaseStructureGUI;

/**
GUI for doing water rights queries.
*/
private HydroBase_GUI_WaterRightsQuery __hydroBaseWaterRightsQueryGUI; 

/**
GUI for querying well applications.
*/
private HydroBase_GUI_WellApplicationQuery __hydroBaseWellApplicationGUI;

/*
private HBFlowDataGUI _hbFlowDataGUI;         // Flow Data GUI Object
*/

/**
The background image that is displayed.
*/
private Image __backgroundImage;

/**
User level that indicates how menus should be turned on.<p>
1 - view only<br>
2 - advanced view only<br>
3 - administration<br>
4 - developer
*/
private int __userLevel = 1;

/**
GUI Menus.
*/
private JMenu 
	__administrationJMenu,
	__fileJMenu,
	__helpJMenu,
	__toolsJMenu,
	__viewDataJMenu;

/**
GUI menu bar.
*/
private JMenuBar __appJMenuBar;

/**
GUI Menu items.
*/
private JMenuItem 
	__aboutJMenuItem,
	__adminNumCalcJMenuItem,
	__addFlowJMenuItem,
	__addSummaryJMenuItem,
	__tsProductJMenuItem,
	__callChronologyJMenuItem,
	__dataDictionaryJMenuItem,
	__divRecordsTransferJMenuItem,
	__editCallsJMenuItem,
	__editWISJMenuItem,
	__exitJMenuItem,
	__groundwaterJMenuItem,
	__openJMenuItem,
	__otherJMenuItem,
	__optionsJMenuItem,
	__pointFlowJMenuItem,
	__propertiesJMenuItem,
	__stationJMenuItem,
	__structureJMenuItem,
	__waterRightsJMenuItem,
	__wellJMenuItem,
	__wisBuilderJMenuItem,
	__Help_ViewDocumentation_JMenuItem = null,
	__Help_ViewTrainingMaterials_JMenuItem = null;

/**
Status bar.
*/
private JTextField 
	__statusJTextField,
	__trackerJTextField,
	__tinyStatusJTextField;

/**
Background image when the map is not shown.
*/
private PictureJPanel __backgroundJPanel;

/**
The name of the map file to use.
*/
private String __mapFile = "";

/**
CWRATMainJFrame Constructor. JMenu items are enabled only upon a successful
login. At that point, functionality may be limited based on the user.
*/
public CWRATMainJFrame(boolean runningStateView, boolean useSP, String imageFile) 
throws Exception {
	super();
	Message.setTopLevel(this);
	String routine = "CWRATMainJFrame";
	int dl = 1;

	setMapFile(imageFile);

	if (Message.isDebugOn) {
		Message.printDebug(dl, routine, "Starting GUI creation...");
	}
	
	__runningStateView = runningStateView;
	__useSP = useSP;

	// Main try
	setupGUI();
}

/**
The event handler manages instantiation of the HydroBase*GUI* classes if null.
Otherwise, the JFrames are re-shown. The actual string that is displayed
on the component, is used in the event handler, if one of these is changed, 
make sure to adjust the text in the handleEvent()routine.
@param evt Event identifier.
*/
public void actionPerformed(ActionEvent evt) {
	String routine = "CWRATMainJFrame.actionPerformed";
	String s = evt.getActionCommand().trim();
	int dl = 10;

	JGUIUtil.setWaitCursor(this, true);
	boolean turnOffWaitCursor = true;

	// List in order that menus are in GUI...
	try {
	// File...
	if (s.equals(__FILE_OPEN)) { 
		if (__dmi != null) {
			// Save preferences from the old login...
	
			setStatusText( "Please Wait... Saving User Preferences");
	
			// save user Preferences
			__dmi.saveUserPreferences();
			   
		}
        setStatusText("Connecting to HydroBase.");
		if (login()) {
			if (Message.isDebugOn) {
				Message.printDebug(dl, routine,
				"Login successful.  Back in CWRATMainJFrame.");
			}
			// The login GUI resets the parent GUI(nulls dialogs,
			// etc.)by calling CWRATMainJFrame.resetForNewUser().
			// However, also need to reset the menu preferences...
        	enableMenusBasedOnHydroBase(true);
			enableMenusBasedOnUserLevel(determineUserLevel());
            __hydroBaseOptionsGUI = 
				new HydroBase_GUI_Options(
				this,	// To be parent windows
				this,	// GeoViewUI
				this,	// OptionsUI
				__dmi, false);
		}
		else {
			setStatusText("Login cancelled.");
			if (Message.isDebugOn) {
				Message.printDebug(dl, routine,
				"Login cancelled.  Back in CWRATMainJFrame.");
			}
			enableMenusBasedOnHydroBase(false);
		}
	}
    else if (s.equals(__FILE_PROPERTIES)) {
		// Simple text display of HydroBase properties.
		PropList reportProp = new PropList ("HydroBase Properties");
		reportProp.set ( "TotalWidth", "600" );
		reportProp.set ( "TotalHeight", "500" );
		reportProp.set ( "DisplayFont", "Courier" );
		reportProp.set ( "DisplaySize", "11" );
		reportProp.set ( "PrintFont", "Courier" );
		reportProp.set ( "PrintSize", "7" );
		reportProp.set ( "Title", "HydroBase Properties" );
		List v = new Vector();
		if ( __dmi == null ) {
			v.add ( "StateDMI HydroBase Properties" );
			v.add ( "" );
			v.add ( "No HydroBase database is available." );
		}
		else {	
			v = __dmi.getDatabaseProperties();
		}
		new ReportJFrame ( v, reportProp );
	}
	else if (s.equals(__FILE_SP)){ 
		if (__spcbmi.isSelected()) {	
			__dmi.setUseStoredProcedures(true);
		}
		else {
			__dmi.setUseStoredProcedures(false);
		}
	}
	else if (s.equals(__FILE_EXIT)) {
		closeClicked();
	}
	// View Data...
	else if (s.equals(__VIEWDATA_CALLS)) {
		setStatusText("Viewing Call Chronology");
		if (__callsGUI == null) {
            __callsGUI = new HydroBase_GUI_CallsQuery(this, // JFrame
    		this,	// GeoViewUI
    		__dmi,
    		HydroBase_GUI_CallsQuery.CALLS);
        }
		else {	
			__callsGUI.setVisible(true);
		}
	}
	else if (s.equals(__VIEWDATA_GROUNDWATER)) {
		setStatusText("Viewing Ground Water Data");
		if (__hydroBaseGroundWaterGUI == null) {
            __hydroBaseGroundWaterGUI = new HydroBase_GUI_GroundWaterQuery(__dmi);
		}
        else {	
			__hydroBaseGroundWaterGUI.setVisible(true);
        }
	}
	else if (s.equals(__VIEWDATA_OTHER)) {
		setStatusText("Viewing Other Data");
		if (__hydroBaseOtherGUI == null) {
            __hydroBaseOtherGUI=new HydroBase_GUI_OtherQuery(__dmi);
		}
        else {	
        	__hydroBaseOtherGUI.setVisible(true);
        }
	}
	else if (s.equals(__VIEWDATA_STATIONS)) {
		setStatusText("Viewing Station Data");
		if (__hydroBaseStationQueryGUI == null) {
            __hydroBaseStationQueryGUI = 
			new HydroBase_GUI_StationQuery(
				__dmi, this,	// JFrame
				this );			// GeoViewUI
		}
        else {	
			__hydroBaseStationQueryGUI.setVisible(true);
        }
	}
	else if (s.equals(__VIEWDATA_STRUCTURES)) {
		setStatusText("Viewing Structure Data");
		JGUIUtil.setWaitCursor(this, true);
		if (__hydroBaseStructureGUI == null) {
            __hydroBaseStructureGUI = new HydroBase_GUI_StructureQuery(__dmi,
			this,	// JFrame
			this );	// GeoViewUI
		}
        else {	
			__hydroBaseStructureGUI.setVisible(true);
			__geoViewJPanel.setWaitCursorAfterRepaint(false);
        }
		if (!isMapVisible()) {
			JGUIUtil.setWaitCursor(this, false);
		}
		turnOffWaitCursor = false;
	}
	else if (s.equals(__VIEWDATA_WATER_RIGHTS)) {
		setStatusText("Viewing Water Rights Data");
        if (__hydroBaseWaterRightsQueryGUI == null) {
            __hydroBaseWaterRightsQueryGUI = new HydroBase_GUI_WaterRightsQuery(__dmi, 
			this,	// Parent
			this );	// GeoViewUI
		}
        else {	
			__hydroBaseWaterRightsQueryGUI.setVisible(true);
        }
	}
	else if (s.equals(__VIEWDATA_WELLS)) {
		setStatusText("Viewing Well Permit Data");		
		if (__dmi.isDatabaseVersionAtLeast(HydroBaseDMI.VERSION_20000301)) {
			if (__hydroBaseWellApplicationGUI == null) {
            	__hydroBaseWellApplicationGUI = new 
					HydroBase_GUI_WellApplicationQuery(
					__dmi, this,	// JFrame
					this );			// GeoViewUI
			}
        	else {	
				__hydroBaseWellApplicationGUI.setVisible(true);
        	}
		}
		else {	
			Message.printWarning(1, routine, 
				"The database you are accessing does not contain well permit data.");
		}
	}
	// Administration...
	else if (s.equals(__ADMINISTRATION_EDIT_CALLS)) {
		setStatusText("Edit Calls");
		if (__editCallsGUI == null) {
			__editCallsGUI = new HydroBase_GUI_CallsQuery(
				this, // JFrame
				this,	// GeoViewUI
				__dmi, HydroBase_GUI_CallsQuery.EDIT_CALLS);
		}
		else {	
			__editCallsGUI.setVisible(true);
		} 
	}
	else if (s.equals(__ADMINISTRATION_LOAD_WIS)) {
		setStatusText("Water Information Sheet");
       	new HydroBase_GUI_LoadWIS(
   			this,	// JFrame
   			this,	// GeoViewUI
   			__dmi,
			HydroBase_GUI_LoadWIS.DISTRICT_WIS);
	}
	else if (s.equals(__ADMINISTRATION_DATA_SYNC)) {
		setStatusText("Synchronizing Database");
		/*
		TODO (JTS - 2003-03-11) Uncomment when this is opened
		new HBSyncDataGUI(this);
		*/
	}
	else if (s.equals(__ADMINISTRATION_REGISTER)) {
		setStatusText("Register User");
		if (__registerGUI == null) {
			__registerGUI = new HydroBase_GUI_RegisterUser(
				__dmi, 
				this,	// JFrame
				this );	// OptionsUI
		}
		else {	
			__registerGUI.setVisible(true);
		}
	}
	else if (s.equals(__ADMINISTRATION_DATABASE_ADMIN)) {
		// Open the database administrator.  It needs a frame and a
		// DMI instance.  Only allow if the database connection is
		// to a local database.
		if (__dmi.getDatabaseEngine().equals("Access")) {
			/*
			TODO (JTS - 2003-03-12)
			Uncomment when this has been opened.
			new HBDatabaseAdministratorGUI(this, getHBDMI());
			*/
		}
		else {	Message.printWarning(1, routine,
			"Database administration can only be performed on a local database.");
		}
	}
	else if (s.equals(__ADMINISTRATION_STREAM_NETWORK)) {
		setStatusText("Stream Network");
		displayStreamNetwork();
	}
	else if (s.equals(__ADMINISTRATION_WIS_BUILDER)) {
		setStatusText("WIS Builder");
       	new HydroBase_GUI_LoadWIS(
   			this,	// JFrame
   			this,	// GeoViewUI
   			__dmi,
			HydroBase_GUI_LoadWIS.WIS_BUILDER);
	}
	// Tools...
	else if (s.equals(__TOOLS_ADMIN_NUM_CALC)) {
       	new HydroBase_GUI_AdminNumCalculator(__dmi, this);
	}
	else if (s.equals(__TOOLS_DIV_TRANSFER)) {
        setStatusText("Diversion Records Transfer.");
	}
	else if (s.equals(__TOOLS_POINT_FLOW)) {
		setStatusText("Point Flow.");
	}
	else if (s.equals(__TOOLS_TS_PRODUCT)) {
		new HydroBase_GUI_SelectTSProduct(__dmi);
	}
	else if (s.equals(__TOOLS_ADD_SUMMARY)) {
		setStatusText("Adding summary layer to GeoView");
		JGUIUtil.setWaitCursor(this, true);
		// File dialog to select the delimited file...
		JFileChooser fc = JFileChooserFactory.createJFileChooser(
			JGUIUtil.getLastFileDialogDirectory());
		fc.setDialogTitle("Select Comma-delimited Summary File");
		SimpleFileFilter cff = new SimpleFileFilter("txt", "Comma-delimited Files");
		SimpleFileFilter cdff = new SimpleFileFilter("csv", "Comma-delimited Files");			
		fc.addChoosableFileFilter(cff);
		fc.addChoosableFileFilter(cdff);
		fc.setFileFilter(cff);
		JGUIUtil.setWaitCursor(this, false);
		if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
	    	setStatusText("Summary layer add cancelled");
			JGUIUtil.setWaitCursor(this, false);
			return;
		}

		File file = fc.getSelectedFile();
		JGUIUtil.setWaitCursor(this, true);

		String fileName = file.getPath();
		JGUIUtil.setLastFileDialogDirectory(file.getParent());

		// Now add to the map...
		__geoViewJPanel.addSummaryMapLayer(fileName);
		setStatusText("Summary layer add complete");
		JGUIUtil.setWaitCursor(this, false);
	}
	else if (s.equals(__TOOLS_ADD_FLOW)) {
		addFlowConditions();	
	}
	else if (s.equals(__TOOLS_OPTIONS)) {
		try {
			setStatusText("Options.");
            if (__hydroBaseOptionsGUI == null) {
                __hydroBaseOptionsGUI = new HydroBase_GUI_Options(
					this,	// JFrame
					this,	// GeoViewUI
					this,	// OptionsUI
					__dmi);
            }
			else {	
				__hydroBaseOptionsGUI.setVisible(true);
			}    
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	else if (s.equals(__TOOLS_DATA_DICTIONARY)) {
		JGUIUtil.setWaitCursor(this, true);
		String lastDirectorySelected = JGUIUtil.getLastFileDialogDirectory();
	
		JFileChooser fc = JFileChooserFactory.createJFileChooser (
			lastDirectorySelected );

		fc.setDialogTitle("Create Data Dictionary");
		SimpleFileFilter htmlFF = new SimpleFileFilter("html", "HTML Files");
		fc.addChoosableFileFilter(htmlFF);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(htmlFF);
		fc.setDialogType(JFileChooser.SAVE_DIALOG);	

		JGUIUtil.setWaitCursor(this, false);
		int retVal = fc.showSaveDialog(this);
		if (retVal != JFileChooser.APPROVE_OPTION) {
			return;
		}
	
		String currDir = (fc.getCurrentDirectory()).toString();
	
		if (!currDir.equalsIgnoreCase(lastDirectorySelected)) {
			JGUIUtil.setLastFileDialogDirectory(currDir);
		}
		String path = fc.getSelectedFile().getPath();

		JGUIUtil.setWaitCursor(this, true);

		String[] refs = new String[14];
		refs[0] = "basin";
		refs[1] = "county_ref";		
		refs[2] = "crop_ref";		
		refs[3] = "db_version";
		refs[4] = "irr_status_ref";
		refs[5] = "irr_type_ref";		
		refs[6] = "loc_type";
		refs[7] = "measurement_type";		
		refs[8] = "month_ref";		
		refs[9] = "str_type";
		refs[10] = "tab_trib";		
		refs[11] = "use";
		refs[12] = "water_district";
		refs[13] = "water_division";

		//DMIUtil.createHTMLDataDictionary(__dmi, path, refs, null);
		DMIUtil.createDataDictionary(__dmi, path, refs);

		JGUIUtil.setWaitCursor(this, false);
	}
	// Help...
	else if (s.equals(__HELP_ABOUT_CWRAT)) {
		setStatusText("About CWRAT.");
		new HelpAboutJDialog(this, "About CWRAT",
		"Colorado Water Rights Administration Tool (CWRAT)\n" +
		IOUtil.getProgramVersion()+ "\n" +
		"Copyright 1997-2013\n" +
		"Colorado Division of Water Resources\n" +
		"Colorado Water Conservation Board\n" +
		"Developed by Riverside Technology, inc.\n" +
		"This application is an administrative tool for the " +
		"Division of Water Resources.\n" +
		"All data entered in the Daily Water Information Sheets are unverified\n" +
		"spot estimate data, and should not be considered the legal diversion record.\n" +
		"Use of these data for modelling purposes should be done with caution.\n" +
		"Send comments about this interface to:\n" +
		"cdss@state.co.us\n" +
		"http://cdss.state.co.us", true);
	}
	else if (s.equals(__HELP_ABOUT_STATEVIEW)) {
		setStatusText("About StateView.");
		new HelpAboutJDialog(this, "About StateView",
			"StateView\n" +
			IOUtil.getProgramVersion()+ "\n" +
			"Copyright 1997-2013\n" +
			"Colorado Division of Water Resources\n" +
			"Colorado Water Conservation Board\n" +
			"Developed by Riverside Technology, inc.\n" +
			"Send comments about this interface to:\n" +
			"cdss@state.co.us\n" +
			"http://cdss.state.co.us", true);
	}
	else if ( s.equals ( __Help_ViewDocumentation_String )) {
        uiAction_ViewDocumentation ();
    }
    else if ( s.equals ( __Help_ViewTrainingMaterials_String )) {
        uiAction_ViewTrainingMaterials ();
    }
	}
	catch (Exception e) {
		// Assume some bug will be reported since the user is probably
		// not going to know what the error is(and specific errors
		// should be caught above).  At least print to the diagnostics
		// window at level 2...
		Message.printWarning(1, routine,
			"Exception caught in event handler.  See log file.");
		Message.printWarning(1, routine, e);
	}
	if (turnOffWaitCursor) {
		JGUIUtil.setWaitCursor(this, false);
	}
}

/**
Adds real-time flow conditions to the map.
*/
private void addFlowConditions() {
	String routine = "CWRATMainJFrame.addFlowConditions";

	String divS = HydroBase_GUI_Util.getActiveWaterDivision();
	if (divS == null) {
		Message.printWarning(1, routine, "Invalid water division (null)");
		return;
	}

	divS = divS.trim();

	if (!StringUtil.startsWithIgnoreCase(divS, "DIV")) {
		Message.printWarning(2, routine, "Invalid water division: " + divS);
		return;
	}

	String divIS = divS.substring(3);

	int div = -1;
	try {
		div = (Integer.decode(divIS)).intValue();
	}
	catch (Exception e) {
		Message.printWarning(1, routine, 
			"Error parsing division number from: " + divS);
		Message.printWarning(2, routine, e);
		return;
	}
	
	JGUIUtil.setWaitCursor(this, true);

	List stations = null;
	try {
		
		String[] where = new String[2];
		where[0] = "geoloc.div = " + div;
		where[1] = "div " + div;
		stations = __dmi.readStationGeolocMeasTypeList(
			null, where, null, "RT_rate", "Real-time", "DISCHRG", 
			null, null, false);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading list of stations for layer.");
		Message.printWarning(2, routine, e);
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	int size = stations.size();
	String plural = "s";
	if (size == 1) {
		plural = "";
	}

	setStatusText("" + size + " station" + plural + " read from DB");

	DateTime today = new DateTime(DateTime.DATE_CURRENT);
	DateTime yesterday = new DateTime(DateTime.DATE_CURRENT);
	yesterday.addDay(-1);
	HydroBase_StationGeolocMeasType station = null;
	HydroBase_StationView view = null;
	int count = 0;
	String ident = null;
	TS ts = null;
	Vector tsVector = new Vector();
	for (int i = 0; i < size; i++) {
		if (__dmi.useStoredProcedures()) {
			view = (HydroBase_StationView)stations.get(i);
			ident = view.getStation_id() + "." + view.getSource() 
				+ ".Streamflow-DISCHRG.Irregular~HydroBase";
		}
		else {
			station = (HydroBase_StationGeolocMeasType)stations.get(i);
			ident = station.getStation_id() + "." + station.getSource() 
				+ ".Streamflow-DISCHRG.Irregular~HydroBase";
		}

		try {
			ts = __dmi.readTimeSeries(ident, yesterday, today, null, true, null);
			tsVector.add(ts);
			count++;
		}
		catch (Exception e) {
			tsVector.add(null);
			Message.printWarning(2, routine, e);
		}
	}

	if (count != size) {
		plural = "s";
		int diff = size - count;
		String plural2 = "were";
		if (diff == 1) {
			plural = "";
			plural2 = "was an";
		}
		Message.printWarning(1, routine, 
			"There " + plural2 + " error" + plural
			+ " reading " + diff + " of " + size + " time series from the database.");
	}

	JGUIUtil.setWaitCursor(this, false);

	Vector fields = new Vector();
	fields.add(new TableField(TableField.DATA_TYPE_STRING, "Identifier", 20));
	fields.add(new TableField(TableField.DATA_TYPE_STRING, "Name", 40));
	fields.add(new TableField(TableField.DATA_TYPE_STRING, "Flow_date", 20));
	fields.add(new TableField(TableField.DATA_TYPE_DOUBLE, "Streamflow", 11, 3));

	DataTable table = new DataTable(fields);
	TableRecord record = null;

	double value = 0;
	IrregularTS its = null;

	boolean error = false;

	for (int i = 0; i < size; i++) {
		if (__dmi.useStoredProcedures()) {
			view = (HydroBase_StationView)stations.get(i);
			its = (IrregularTS)tsVector.elementAt(i);
			record = new TableRecord(4);
			record.addFieldValue(view.getStation_id());
			record.addFieldValue(view.getStation_name());
		}
		else {
			station = (HydroBase_StationGeolocMeasType)stations.get(i);
			its = (IrregularTS)tsVector.elementAt(i);
			record = new TableRecord(4);
			record.addFieldValue(station.getStation_id());
			record.addFieldValue(station.getStation_name());
		}

		if (its == null) {
			record.addFieldValue("N/A");
			record.addFieldValue(new Double(-1));
			try {
				table.addRecord(record);
			}
			catch (Exception e) {
				Message.printWarning(2, routine, e);
				error = true;
			}
		}
		else {
			value = its.getDataValue(its.getDate2());

			if (DMIUtil.isMissing(value)) {
				record.addFieldValue("N/A");
				record.addFieldValue(new Double(-1));
			}
			else {
				record.addFieldValue(its.getDate2().toString(
					DateTime.FORMAT_YYYY_MM_DD_HH_mm));
				record.addFieldValue(new Double(value));
			}

			try {
				table.addRecord(record);
			}
			catch (Exception e) {
				Message.printWarning(2, routine, e);
				error = true;
			}
		}
	}

	if (error) {
		Message.printWarning(1, routine,
			"Errors were encountered adding records to an internal data table.");
	}

	Vector avail = new Vector();
	avail.add("Streamflow");
	try {
		__geoViewJPanel.addSummaryLayerView(table, 
			"Current Flow Conditions", 0, 3, avail, false);
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	setStatusText("Ready");
}

/**
This function is prompts the user for confirmation of exit.
*/
public void closeClicked() {
	String app = null;

	if (__runningStateView) {
	        app = "StateView";
	}
	else {
		app = "Colorado Water Rights Administration Tool";
	}

	int r = new ResponseJDialog(this, app, 
		"OK to Exit " + app + "?",
		ResponseJDialog.YES | ResponseJDialog.NO, 
		GridBagConstraints.WEST).response();
    if (r == ResponseJDialog.YES) {
		setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		exit();
	}
	else {
		setDefaultCloseOperation (WindowConstants.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
}

/**
This function hides and disposes any remaining windows when exiting the main
GUI or when logging in again.
*/
private void closeWindows() {
	destroyJFrame(__hydroBaseOptionsGUI);
	destroyJFrame(__callsGUI);
	destroyJFrame(__hydroBaseOtherGUI);
	destroyJFrame(__hydroBaseGroundWaterGUI);	
	destroyJFrame(__hydroBaseStructureGUI);
	destroyJFrame(__hydroBaseStationQueryGUI);
	destroyJFrame(__editCallsGUI);
	destroyJFrame(__registerGUI);
	destroyJFrame(__hydroBaseWaterRightsQueryGUI);
	destroyJFrame(__hydroBaseWellApplicationGUI);
	__hydroBaseOptionsGUI = null;
	__callsGUI = null;
	__hydroBaseOtherGUI = null;
	__hydroBaseGroundWaterGUI = null;
	__hydroBaseStructureGUI = null;
	__hydroBaseStationQueryGUI = null;
	__editCallsGUI = null;
	__registerGUI = null;
	__hydroBaseWaterRightsQueryGUI = null;
	__hydroBaseWellApplicationGUI = null;
}

/**
This function hides and disposes a JFrame Object.
@param frame JFrame object to hide.
*/
public void destroyJFrame(JFrame frame) {
	if (frame != null) {
		if (frame != null && frame instanceof GeoViewListener) {
			getGeoViewJPanel().getGeoView().removeGeoViewListener(
				(GeoViewListener)frame);
		}
		frame.setVisible(false);
		frame.dispose();
        }
}

/**
Determine the user level for the application.
*/
private String determineUserLevel() {
	String user_level = "3";	// administrator default value
	if (__runningStateView) {
		user_level = "1"; // View only
	}
	else {	
		if (__dmi != null) {
			user_level = __dmi.getPreferenceValue("General.UserLevel");
		}
	}
	// Make sure something is defined...
	if ( (user_level == null)|| user_level.equals("")||
		user_level.equalsIgnoreCase("NONE")) {
		user_level = "3"; // Administrator
	}
	return user_level;
}

/**
Display the stream network starting with a stream.
*/
private void displayStreamNetwork() {
/*
	TODO (JTS - 2003-03-11)
	HBNodeNetwork wasn't migrated over into HydroBaseDMI yet.  Uncomment
	when it is opened.

	HBNodeNetwork network = new HBNodeNetwork();
	network.readHydroBaseStreamNetwork(getHBDMI(), 3865);
	Vector v = network.createIndentedRiverNetworkStrings();
	int size = 0;
	if (v != null) {
		size = v.size();
	}
	Message.printStatus(1, "", "SAMX network size is " + size);
	for (int i = 0; i < size; i++) {
		Message.printStatus(1, "", "SAMX " +(String)v.elementAt(i));
	}
*/	
}

/**
Enables/disable the main GUI menus based on whether a connection to HydroBase
has been made.  Some menus like File->Exit, File->Open, and all Help items are
always enabled regardless of whether a login is successful or not.
@param state true if enabling the menu items, false otherwise.
*/
public void enableMenusBasedOnHydroBase(boolean state) {
	// File dialogs
	// Disable the open menu if an applet because it should be configured
	// correctly at setup...

	if (IOUtil.isApplet()) {
        __openJMenuItem.setEnabled(false);
	}
	else {	
		__openJMenuItem.setEnabled(true);
	}

	if (__dmi == null) {
		// Disable the database information...
		__propertiesJMenuItem.setEnabled(false);
	}
	else {	
		// Enable the database information...
		__propertiesJMenuItem.setEnabled(true);
	}

    __exitJMenuItem.setEnabled(true);
        
    // View dialogs

	if (__dmi == null) {
		__callChronologyJMenuItem.setEnabled(false);
		__groundwaterJMenuItem.setEnabled(false);
		__otherJMenuItem.setEnabled(false);
		//_loadSpecialProject.setEnabled(false);
		__stationJMenuItem.setEnabled(false);
		__structureJMenuItem.setEnabled(false);
		__waterRightsJMenuItem.setEnabled(false);
		__wellJMenuItem.setEnabled(false);
	}
	else {	
		__callChronologyJMenuItem.setEnabled(state);
		__groundwaterJMenuItem.setEnabled(state);
		__otherJMenuItem.setEnabled(state);
		//_loadSpecialProject.setEnabled(state);
		__stationJMenuItem.setEnabled(state);
		__structureJMenuItem.setEnabled(state);
		__waterRightsJMenuItem.setEnabled(state);
		__wellJMenuItem.setEnabled(state);
	}
 
    // Administration dialogs

	if (__administrationJMenu != null) {
	//if (!__runningStateView) {
		if (__dmi == null) {
			__editCallsJMenuItem.setEnabled(false);
    		__editWISJMenuItem.setEnabled(false);
//			__dbAdminJMenuItem.setEnabled(false);
//			__syncDataJMenuItem.setEnabled(false);
//			__registerJMenuItem.setEnabled(false);
			__wisBuilderJMenuItem.setEnabled(false);
		}
		else {	
			if (__dmi.getUserLogin().equals(
				SelectHydroBaseJDialog.getDefaultLogin())) {
				__editCallsJMenuItem.setEnabled(false);
			}
			else {
				__editCallsJMenuItem.setEnabled(state);
			}
        		__editWISJMenuItem.setEnabled(state);
//			__dbAdminJMenuItem.setEnabled(state);

			if (__dmi.getDatabaseServer().equals("Local")) {
				if (__dmi.getDatabaseEngine().equals("Access")){
//			        	__syncDataJMenuItem.setEnabled(state);
				}
				else {	
//					__syncDataJMenuItem.setEnabled(false);
				}
			}
			else {	
//				__syncDataJMenuItem.setEnabled(false);
			}

			// In order to enable the register must be logged in as
			// guest and running the application using the local
			// database with the archive database different from the local database
			/*
			TODO (JTS - 2003-03-12) Not sure at all what's going on here.

			(2003-03-24)
			Remind SAM of this code after all the other code is
			ported -- it's probably not needed anymore.

			if (	!HBGUIApp.getRemote()&&
				HBSource.getDBHost().equals(
				HBSource.DEFAULT_DB_HOST)&&
				!HBSource.getArchiveDBHost().equals(
				HBSource.getDBHost())) {
				if (	getLogin().equals(getDefaultLogin())
					&& getPassword().equals(
					getDefaultPassword())) {
					__registerJMenuItem.setEnabled(true);
				}
				else {	
					__registerJMenuItem.setEnabled(false);
				}
			}
			else {	
				__registerJMenuItem.setEnabled(false);
			}
			*/
			if (__isGuest) {
				__wisBuilderJMenuItem.setEnabled(false);
			}
			else {
				__wisBuilderJMenuItem.setEnabled(state);
			}
		}
	}

    // Tools dialogs

	if (__dmi == null) {
		__divRecordsTransferJMenuItem.setEnabled(false);
		__tsProductJMenuItem.setEnabled(false);
        __optionsJMenuItem.setEnabled(false);
		__dataDictionaryJMenuItem.setEnabled(false);
	}
	else {	
		__divRecordsTransferJMenuItem.setEnabled(state);
		__tsProductJMenuItem.setEnabled(state);
        __optionsJMenuItem.setEnabled(state);
		__dataDictionaryJMenuItem.setEnabled(false);
	}

	// Help menu...

	__aboutJMenuItem.setEnabled(true);

	if (!__runningStateView) {
		if (!IOUtil.isApplet()) {
//			__syncDataJMenuItem.setEnabled(false);
		}
//		__dbAdminJMenuItem.setEnabled(false);
	}	
}

/**
Enable/disable menu items based on the user level.  A string is passed in so the
HBGUIApp.getValue("General.UserLevel")routine can be used.  The
enableMenusBasedOnHydroBase()method should be called first to enable/disable
menus when a HydroBase connection has been established.
@param userLevel User level(1 = view only, 2 = advanced view only,
3 = administration, 4 = developer).
*/
public void enableMenusBasedOnUserLevel(String userLevel) {
	String rtn = "enableMenusBasedOnUserLevel";

	if (__dmi == null) {
		// No need to do anything because having no database connection
		// controls the menus...
		return;
	}

	if (userLevel.equalsIgnoreCase("NONE")) {
		// Default to administrator...
		__userLevel = 3;
	}
	else {	
		__userLevel = StringUtil.atoi(userLevel);
	}
	Message.printStatus(1, rtn, "User level is " + __userLevel);

	boolean	groundwater_bool = false,		// View Data
	well_bool = false,

	administrationJMenu_bool = false,	// Administration
	edit_calls_bool = false,
	edit_wis_bool = false,
	wis_builder_bool = false,

	toolsJMenu_bool = false,		// Tools
	admin_num_calc_bool = false,
	div_records_transfer_bool = false,
    point_flow_bool = false,
	ts_plot_bool = false,
	options_bool = false,
	dictionary_bool = false;
			
	// Don't use "else if" here.  The settings are incremental...

	if (__userLevel >= 1) {	// view only	
		// must allow preferences and options menu or user
		// would not be able to access user level again
		groundwater_bool = true;
   		options_bool = true;
		well_bool = true;
		toolsJMenu_bool = true;
		admin_num_calc_bool = true;
		ts_plot_bool = true;
		dictionary_bool = true;
	}
	if (__userLevel >= 2) {	// advanced user
		toolsJMenu_bool = true;
		ts_plot_bool = true;
	}
	if (__userLevel >= 3) {	// administrative user
		administrationJMenu_bool = true;
		edit_calls_bool = true;
		edit_wis_bool = true;
		wis_builder_bool = true;
	}
	if (__userLevel >= 4) {	// other,(e.g., developer)
		// Currently no additional features
	}

	// Now enable/disable menus according to booleans set above.  Put checks
	// around some menus that are only available for administration.

	__groundwaterJMenuItem.setEnabled(groundwater_bool);
	__wellJMenuItem.setEnabled(well_bool);
	// All other view data menus are enabled by default.

	if (__administrationJMenu != null) {
		__administrationJMenu.setEnabled(administrationJMenu_bool);
		if (__dmi.getUserLogin().equals(SelectHydroBaseJDialog.getDefaultLogin())) {
			__editCallsJMenuItem.setEnabled(false);
		}
		else {
			__editCallsJMenuItem.setEnabled(edit_calls_bool);
		}
		__editWISJMenuItem.setEnabled(edit_wis_bool);
//		__syncDataJMenuItem.setEnabled(sync_data_bool);
//		__registerJMenuItem.setEnabled(register_bool);
//		__dbAdminJMenuItem.setEnabled(db_admin_bool);
		if (__isGuest) {
			__wisBuilderJMenuItem.setEnabled(false);
		}
		else {
			__wisBuilderJMenuItem.setEnabled(wis_builder_bool);
		}
	}

	__toolsJMenu.setEnabled(toolsJMenu_bool);
	__adminNumCalcJMenuItem.setEnabled(admin_num_calc_bool);
	__divRecordsTransferJMenuItem.setEnabled(div_records_transfer_bool);
	__pointFlowJMenuItem.setEnabled(point_flow_bool);
	__tsProductJMenuItem.setEnabled(ts_plot_bool);
	__optionsJMenuItem.setEnabled(options_bool);
	__dataDictionaryJMenuItem.setEnabled(dictionary_bool);

	// Final test, well permits are only enabled if the main database and
	// not a guest login...
	if ( (__dmi.getDatabaseEngine().equals("Access") && !IOUtil.isApplet())
		|| (!IOUtil.isApplet() && !__dmi.getUserLogin().equals(
			SelectHydroBaseJDialog.getDefaultLogin()))) {
		__wellJMenuItem.setEnabled(true);
	}
	else {
		__wellJMenuItem.setEnabled(false);
	}

	if (!__runningStateView) {
		if (!IOUtil.isApplet()) {
//			__syncDataJMenuItem.setEnabled(false);
		}
//		__dbAdminJMenuItem.setEnabled(false);
	}	
}

/**
This function is responsible for maintaining exit procedures.
*/
public void exit() {
	setStatusText("Saving user preferences...");
	JGUIUtil.setWaitCursor(this, true);
	__backgroundJPanel.repaint();
	if (__dmi != null && __dmi.isOpen()) {
		__dmi.saveUserPreferences();
		try {
			__dmi.close();
		}
		catch (java.sql.SQLException e) {
			Message.printWarning(2, "exit", e);
			Message.printWarning(2, "exit",
				"Could not shut down DMI connection properly.");
		}
	}
	JGUIUtil.setWaitCursor(this, false);
	setVisible(false);
	closeWindows();
	dispose();
	Message.closeLogFile();
	if (IOUtil.isApplet()) {
		IOUtil.getApplet().stop();
	}
	else {	
		System.exit(0);
	}
}

/**
Handle the label redraw event from another GeoView(likely a ReferenceGeoView).
Do not do anything here because we assume that application code is setting the labels.
@param record Feature being draw.
*/
public String geoViewGetLabel(GeoRecord record) {
	return null;
}

/**
Handle the mouse motion event from another GeoView(likely a ReferenceGeoView).
Currently this does nothing.
@param devpt Coordinates of mouse in device coordinates(pixels).
@param datapt Coordinates of mouse in data coordinates.
*/
public void geoViewMouseMotion(GRPoint devpt, GRPoint datapt)
{
}

/**
Do nothing.  Should this do the same as a select?
Currently this does nothing.
@param devlimits Limits of select in device coordinates(pixels).
@param datalimits Limits of select in data coordinates.
@param selected Vector of selected GeoRecord.  Currently ignored.
*/
public void geoViewInfo( GRShape devlimits, GRShape datalimits, List selected)
{
}

/**
Do nothing.  Should this do the same as a select?  Currently this does nothing.
@param devlimits Limits of select in device coordinates(pixels).
@param datalimits Limits of select in data coordinates.
@param selected list of selected GeoRecord.  Currently ignored.
*/
public void geoViewInfo ( GRPoint devlimits, GRPoint datalimits, List selected )
{
}

/**
Do nothing.  Should this do the same as a select?  Currently this does nothing.
@param devlimits Limits of select in device coordinates(pixels).
@param datalimits Limits of select in data coordinates.
@param selected Vector of selected GeoRecord.  Currently ignored.
*/
public void geoViewInfo(GRLimits devlimits, GRLimits datalimits, List selected)
{
}

/**
If a selection is made from the map, make sure the appropriate window is
displayed and move it to the front.  Note that this code could be put in for
each separate window but do it here for now to see if it makes sense.
@param devlimits Limits of select in device coordinates(pixels).
@param datalimits Limits of select in data coordinates.
@param selected Vector of selected GeoRecord.  Currently ignored.
*/
public void geoViewSelect( GRShape devlimits, GRShape datalimits,
				List selected, boolean append) {
	int size = 0;
	if (selected != null) {
		size = selected.size();
	}
	// Evaluate this.  It may be faster to check the GeoView legend for
	// selected layers.
	GeoRecord record = null;
	String app_layer_type;
	boolean stations_visible = false;
	boolean structures_visible = __hydroBaseStructureGUI.isVisible();
	for (int i = 0; i < size; i++) {
		record = (GeoRecord)selected.get(i);
		app_layer_type = record.getLayer().getAppLayerType();
		if (app_layer_type.equalsIgnoreCase("Streamflow")) {
			// A map layer for the Station window is selected.
			// Make sure that the station window is displayed...
			if (!stations_visible) {
				__hydroBaseStationQueryGUI.setVisible(true);
			}
			stations_visible = true;
		}
		else if (app_layer_type.equalsIgnoreCase("Diversion")||
			app_layer_type.equalsIgnoreCase("InstreamFlow")||
			app_layer_type.equalsIgnoreCase("Reservoir")) {
			// A map layer for the Structure window is selected.
			// Make sure that the station window is displayed... 
			if (!structures_visible) {
				Message.printStatus(2, "", "Setting structures GUI visible.");
				__hydroBaseStructureGUI.setVisible(true);
			}
			structures_visible = true;
		}
		/* Ignore wells for now since it is hard to tell if a well has
		   a WDID or a permit.
		else if () {
			// A map layer for the well permits window is
			// selected.  Make sure that the well permits window is
			// displayed...
			if (!wells_visible) {
				_hbWellsQueryGUI.setVisible(true);
			}
			wells_visible = true;
		}
		*/
	}
}

/**
If a selection is made from the map, make sure the appropriate window is
displayed and move it to the front.  Note that this code could be put in for
each separate window but do it here for now to see if it makes sense.
@param devlimits Limits of select in device coordinates(pixels).
@param datalimits Limits of select in data coordinates.
@param selected list of selected GeoRecord.  Currently ignored.
*/
public void geoViewSelect(GRPoint devlimits, GRPoint datalimits, 
List selected, boolean append) {}

/**
If a selection is made from the map, make sure the appropriate window is
displayed and move it to the front.  Note that this code could be put in for
each separate window but do it here for now to see if it makes sense.
@param devlimits Limits of select in device coordinates(pixels).
@param datalimits Limits of select in data coordinates.
@param selected list of selected GeoRecord.  Currently ignored.
*/
public void geoViewSelect(GRLimits devlimits, GRLimits datalimits, 
List selected, boolean append) {}

/**
Handle the zoom event from another GeoView(likely a ReferenceGeoView).
This resets the data limits for this GeoView to those specified(if not
null)and redraws the GeoView.
@param devlimits Limits of zoom in device coordinates(pixels).
@param datalimits Limits of zoom in data coordinates.
*/
public void geoViewZoom(GRShape devlimits, GRShape datalimits) {}

/**
Handle the zoom event from another GeoView(likely a ReferenceGeoView).
This resets the data limits for this GeoView to those specified(if not
null)and redraws the GeoView.
@param devlimits Limits of zoom in device coordinates(pixels).
@param datalimits Limits of zoom in data coordinates.
*/
public void geoViewZoom(GRLimits devlimits, GRLimits datalimits) {}

/**
Returns the GeoViewJPanel used in the display.
@return the GeoViewJPanel used in the display.
*/
public GeoViewJPanel getGeoViewJPanel() {
	return __geoViewJPanel;
}

/**
Returns the map file being displayed.
@return the map file being displayed.
*/
public String getMapFile() {
	return __mapFile;
}

/**
Returns whether the program is running StateView (true) or not.
@return whether the program is running StateView (true) or not.
*/
public boolean getViewOnly() {
	return __runningStateView;
}

/**
Initializes variables, perform an initial login and set menus accordingly...
*/
private void initialize()
throws Exception {
	String routine = "CWRATMainJFrame.initialize";
                          
	if (Message.isDebugOn) {
		Message.printDebug(1, routine, "Initializing CWRAT variables.");
	}

	// Read the units data.  Put here so dialog will warn user if not found
	// String	s = IOUtil.getFileSeparator();
	String s = File.separator;
	String units_file = "";
	if (IOUtil.isApplet()) {
		units_file = IOUtil.getApplicationHomeDir() + "/system/DATAUNIT";
	}
	else {
		// File on system...
		units_file = IOUtil.getApplicationHomeDir() + s + "system" + s + "DATAUNIT";
	}
	try {	
		DataUnits.readUnitsFile(units_file);
	}
	catch(Exception e) {
		Message.printWarning(1, routine,
			"Error reading units file \"" + units_file + "\"\n"
			+ "Some conversions will not be supported.\n"
			+ "Default output precisions may not be appropriate.");
		try {
			DataDimension dim = DataDimension.lookupDimension ( "LENGTH");
			String dim_abbrev = dim.getAbbreviation();
			DataUnits.addUnits( new DataUnits ( dim_abbrev,
				1, "MM", "Millimeter", 1, 1, 0 ) );
			DataUnits.addUnits( new DataUnits ( dim_abbrev,
				0, "CM", "Centimeter", 2, 10, 0 ) );
			DataUnits.addUnits( new DataUnits ( dim_abbrev,
				0, "M", "Meter", 2, 1000, 0 ) );
			DataUnits.addUnits( new DataUnits ( dim_abbrev,
				0, "KM", "Kilometer", 1, 1000000, 0 ) );
			DataUnits.addUnits( new DataUnits ( dim_abbrev,
				0, "IN", "Inch", 2, 25.4, 0 ) );
			DataUnits.addUnits( new DataUnits ( dim_abbrev,
				0, "FT", "Feet", 2, 304.8, 0 ) );
			DataUnits.addUnits( new DataUnits ( dim_abbrev,
				0, "MI", "Mile", 1, 1609344, 0 ) );
		
			dim = DataDimension.lookupDimension ("VOLUME");
			DataUnits.addUnits( new DataUnits ( dim_abbrev,
				1, "ACFT", "Acre-feet", 1, 1, 0 ) );
			DataUnits.addUnits( new DataUnits ( dim_abbrev,
				0, "AF", "Acre-feet", 1, 1, 0 ) );
			DataUnits.addUnits( new DataUnits ( dim_abbrev,
				0, "AF/M", "AF/Month", 1, 1, 0 ) );
		}
		catch ( Exception e2 ) {
			// Trouble configuring units.
			Message.printWarning ( 2, routine,
			"Trouble setting default units conversions." );
			Message.printWarning ( 2, routine, e );
		}			
	}

	if (! IOUtil.isApplet()) {
		if (Message.isDebugOn) {
			Message.printDebug(1, routine, "Creating login GUI...");
		}

		if (login()) {
			if (Message.isDebugOn) {
				Message.printDebug(5, routine, "Login successful.  Back in CWRATMainJFrame.");
			}
			// The login GUI resets the parent GUI(nulls dialogs,
			// etc.)by calling CWRATMainJFrame.resetForNewUser().
			// However, also need to reset the menu preferences...
    		enableMenusBasedOnHydroBase(true);
			enableMenusBasedOnUserLevel(determineUserLevel());
		}
		else {
			setStatusText("Login unsuccessful.");
			if (Message.isDebugOn) {
				Message.printDebug(5, routine,
				"Login unsuccessful.  Back in CWRATMainJFrame.");
			}
			enableMenusBasedOnHydroBase(false);
		}
	}
	// login as guest without using the login GUI.
	else {	
		JGUIUtil.setWaitCursor(this, true);

		__dmi = null;
        setStatusText("Please wait...retrieving preferences...");

		if (__dmi != null) {
			if (Message.isDebugOn) {
				Message.printDebug(5, routine,
				"Login successful.  Back in CWRATMainJFrame.");
			}
			// The login GUI resets the parent GUI(nulls dialogs,
			// etc.)by calling CWRATMainJFrame.resetForNewUser().
			// However, also need to reset the menu preferences...
    		enableMenusBasedOnHydroBase(true);
			openGVP();
		} 
		else {
			setStatusText("Login unsuccessful.");
			if (Message.isDebugOn) {
				Message.printDebug(5, routine,
				"Login unsuccessful.  Back in CWRATMainJFrame.");
			}
			enableMenusBasedOnHydroBase(false);
			__addSummaryJMenuItem.setEnabled(false);
			__addFlowJMenuItem.setEnabled(false);
		}

        resetForNewUser();
		String user_level = "3";	// administrator default value
		if (__runningStateView) {
			user_level = "1";
		}
		else {	
			user_level = __dmi.getPreferenceValue("General.UserLevel");
		}
		// Make sure something is defined...
		if ( (user_level == null)|| user_level.equals("")||
			user_level.equalsIgnoreCase("NONE")) {
			user_level = "3";
		}
		enableMenusBasedOnUserLevel(user_level);
		setStatusText("Ready");
		JGUIUtil.setWaitCursor(this, false);
	}

	if (!__runningStateView) {
		if (!IOUtil.isApplet()) {
//			__syncDataJMenuItem.setEnabled(false);
		}
//		__dbAdminJMenuItem.setEnabled(false);
	}		
}

/**
Returns whether the map is visible or not.
@return whether the map is visible or not.
*/
public boolean isMapVisible() {
	return __mapVisible;
}

/**
Load the __backgroundImage object into memory, which is displayed until a valid
map is specified.
*/
private void loadBackgroundImage() {
	String function,	// function name
			error;		// loading error

	// initialize variables
	function = "CWRATMainJFrame.loadBackgroundImage";
	error = "Error loading map image. \"" + __mapFile + "\" not found.";

	if (__mapFile == null) {
		Message.printWarning(1, function, "Image file not specified.");
		return;
	}

	// get the __backgroundImage for applets, or stand alone sessions accordingly.
	if (IOUtil.isApplet()) {
		try {
			URL url = new URL(__mapFile);
			__backgroundImage = IOUtil.getAppletContext().getImage(url);
		}
		catch(Exception url) {
			Message.printWarning(1, function, error);
			return;
		}
	}	
	else {	
		__backgroundImage = this.getToolkit().getImage(__mapFile);
	}

	// Use the MediaTracker object to halt processing
	// until the __backgroundImage is completely loaded.
	MediaTracker mt = new MediaTracker(this);
	mt.addImage(__backgroundImage, 0);
	try {	
		__backgroundJPanel.setImage(__backgroundImage);
		mt.waitForID(0);
		if (mt.isErrorID(0)) {
			Message.printWarning(1, function, error);
		}
	}
	catch(Exception e) {
		Message.printWarning(1, function, error);
	}
	if (Message.isDebugOn) {
		Message.printDebug(1, function, "Done loading background image.");
	}
}

/**
Opens a login screen and attempts to log into the database.
*/
private boolean login() {
	if (!__runningStateView) {
		if (!IOUtil.isApplet()) {
//			__syncDataJMenuItem.setEnabled(false);
		}
//		__dbAdminJMenuItem.setEnabled(false);
	}	


	String routine = "CWRATMainJFrame.login";
	PropList props = new PropList ( "SelectHydroBase" );
	props.set ( "ValidateLogin", "true" );
	props.set ( "ShowWaterDivisions", "true" );		

	try {	
		SelectHydroBaseJDialog shbj = new SelectHydroBaseJDialog(this, __dmi, props);
		
		// After getting to here, the dialog has been closed.
		// The HydroBaseDMI from the dialog can be retrieved and used...

		if (shbj.wasCancelled()) {
			if (__dmi == null) {
				Message.printWarning ( 1, routine,
				"Database login was canceled and no previous connection was made.\n" +
				"HydroBase features will be disabled.", this );
				return false;		
			}
			else {
				// The login WAS canceled, but the old login
				// remains in effect -- so treat it as a successful login.
				return true;
			}
		}

		if (__dmi != null && __dmi.isOpen()) {
			try {
				__dmi.close();
			}
			catch (java.sql.SQLException e) {
				Message.printWarning(2, routine, e);
			}
		}

		__dmi = shbj.getHydroBaseDMI();
		if (CWRAT._useSPProvided) {
			__dmi.setUseStoredProcedures(__useSP);
		}
/*
	// test for readParcelUseTSDistinctCalYearsList() method
	try {
		Class[] paramTypes = { Integer.TYPE };
		java.lang.reflect.Method method = __dmi.getClass()
			.getDeclaredMethod(
			"readParcelUseTSDistinctCalYearsList", paramTypes);
	}
	catch (NoSuchMethodException e) {
		// handle here
	}

	// test for readParcelUseTSList() method
	try {
		String s = "";
		Class[] paramTypes = { Integer.TYPE, Integer.TYPE, Integer.TYPE,
			s.getClass(), s.getClass(), s.getClass(), s.getClass()};
		java.lang.reflect.Method method = __dmi.getClass()
			.getDeclaredMethod(
			"readParcelUseTSList", paramTypes);
	}
	catch (NoSuchMethodException e) {
		// handle here
	}
*/
		
		if (__dmi.useStoredProcedures()) {
			__spcbmi.setSelected(true);
		}
		else {
			__spcbmi.setSelected(false);
		}

		__dmi.readUserPreferencesPropList ( __dmi.getUserLogin(),
			__dmi.getUserPassword());

		closeWindows();

		if (__dmi.isGuestLoggedIn()) {
			__isGuest = true;
		}
		else {
			__isGuest = false;
		}
	}
	catch (Exception e) {
		Message.printWarning ( 1, routine, "HydroBase login failed.\n" +
			"HydroBase features will be disabled.", this );
		Message.printWarning ( 2, routine, e );
		__dmi = null;
		return false;
	}

	// Open the GeoView project file...

	if ( __dmi != null ) {
		openGVP();	// Errors are handled in its code.
	}
	else {
		__addSummaryJMenuItem.setEnabled(false);
		__addFlowJMenuItem.setEnabled(false);
	}

	return true;
}

/**
This method should be called after a successful login.  If the
"Map.DefaultGeoViewProject" preference is not "NONE" and is set to a readable
file, the GeoView panel reads the project.  This method should not be called
from other than the login window.  If a second login occurs under a different
name, then the map may be turned off.
This method also temporarily disables the menus while the map is being read to
prevent GUI events from occurring and confusing the application.
*/
private void openGVP() {
	// Make sure to turn off the map first...
	Message.printStatus(1, "", "Disabled the map display.");

	setMapVisible(false);
	// Now try to open a map based on the preference.  If a map is already
	// being used, the openGVP()call will cause the old contents to be removed.

	String pref_val = __dmi.getPreferenceValue(
		"Map." + HydroBase_GUI_Util.getActiveWaterDivision()+ ".GeoViewProject");
	if ((pref_val != null)&& !pref_val.equalsIgnoreCase("NONE")) {
		openGVP(pref_val);
	}
	else {
		// Set the interface size back to the default so the image will look nice...
		__backgroundJPanel.setVisible(true);
		__geoViewJPanel.setVisible(false);
		__mapVisible = false;
		if (!IOUtil.isApplet()) {
			setSize(650, 550);
		}
		else {
			setSize(300, 150);
		}
		setStatusText("Map interface turned off.");
		invalidate();
		repaint();
		__addSummaryJMenuItem.setEnabled(false);
		__addFlowJMenuItem.setEnabled(false);
	}

	if (!__runningStateView) {
		if (!IOUtil.isApplet()) {
//			__syncDataJMenuItem.setEnabled(false);
		}
//		__dbAdminJMenuItem.setEnabled(false);
	}		
}

/**
This method can be called externally(e.g., from HBOptionsGUI)when a new
GeoView project file has been selected.  The map interface will update to use
the new file, regardless of whether the file name has changed or not(it is
assumed that the options user interface will call this if the GeoView project
file preference has changed).
@param gvp_file GeoView project file to use.
*/
public void openGVP(String gvp_file) {
	if (gvp_file.equalsIgnoreCase("NONE")) {
		// Disable the map interface...
		setMapVisible(false);
		// Clear out the map resources so they don't take as much memory...
		__geoViewJPanel.removeAllLayerViews();
		// Set the interface size back to the default so the image will look nice...
		if (!IOUtil.isApplet()) {
			setSize(650, 550);
		}
		else {
			setSize(300, 150);
		}
		// Do this to get the status JTextField to refresh...
		// Can't seem to get this to work!!!!
		setStatusText("Map interface turned off.");
		__statusJTextField.invalidate();
		__statusJTextField.repaint();
		invalidate();
		repaint();
		__showingGVP = false;
		__addSummaryJMenuItem.setEnabled(false);
		__addFlowJMenuItem.setEnabled(false);
		return;
	}
	setStatusText("Opening GeoView Project and initializing map interface.");
	try {	
		Message.printStatus(1, "", "Opening GVP \"" +gvp_file+"\"...");
		// Disable menus...
		__fileJMenu.setEnabled(false);
		__viewDataJMenu.setEnabled(false);
		if (!__runningStateView) {
			__administrationJMenu.setEnabled(false);
		}
		__toolsJMenu.setEnabled(false);
		__helpJMenu.setEnabled(false);
		// Now open the GVP file...
		__geoViewJPanel.openGVP(gvp_file);

		// Set the size to something that shows the legend and all buttons...
		if (!IOUtil.isApplet()) {
			setSize(720, 550);
			// Need these to force the resize to happen visually!
			invalidate();
			validate();
		}

		// Hide the image canvas so the GeoView panel shows...
		Message.printStatus(1, "", "Opened GVP \"" + gvp_file + "\"");
		setMapVisible(true);
		setStatusText("Ready.");
		__showingGVP = true;
	}
	catch(Exception e) {
		Message.printWarning(1, "CWRATMainJFrame.openGVP", e);
		Message.printWarning(1, "",
		"Error reading GeoView project file \"" + gvp_file +
		"\".\nThe map interface is disabled.");
		// This can be a problem if the screen was resized.
		setMapVisible(false);
		setStatusText( "Could not initialize map interface.  Ready.");
		if (!IOUtil.isApplet()) {
			setSize(650, 550);
		}
		else {
			setSize(300, 150);
		}
		__showingGVP = false;
	}
	// Re-enable menus...
	__fileJMenu.setEnabled(true);
	__viewDataJMenu.setEnabled(true);
	if (!__runningStateView && (__userLevel >= 3)) {
		__administrationJMenu.setEnabled(true);
	}
	__toolsJMenu.setEnabled(true);
	__helpJMenu.setEnabled(true);

	if (__showingGVP) {
		if ( (__dmi == null) || __dmi.getDatabaseEngine().equals("Access")) {
			__addFlowJMenuItem.setEnabled(false);
		}
		else {
			__addSummaryJMenuItem.setEnabled(true);
			__addFlowJMenuItem.setEnabled(true);
		}
	}
	else {
		__addSummaryJMenuItem.setEnabled(false);
		__addFlowJMenuItem.setEnabled(false);
	}

	if (!__runningStateView) {
		if (!IOUtil.isApplet()) {
//			__syncDataJMenuItem.setEnabled(false);
		}
//		__dbAdminJMenuItem.setEnabled(false);
	}	
}

/**
This function resets the appropriate variables that are required when
a new user logs in.
*/
public void resetForNewUser() {
	String routine = "CWRATMainJFrame.resetForNewUser";
	Message.printStatus(1, routine, "Initializing GUIs to null");
	// reset HBGUI objects
	/*
	TODO (JTS - 2003-03-11) Uncomment when these are opened.
    	_hbDatabaseInformationGUI = null;
    	_hbFlowDataGUI = null;
	*/
	__hydroBaseOtherGUI = null;
	__hydroBaseWellApplicationGUI = null;
	__hydroBaseGroundWaterGUI = null;
	__hydroBaseOptionsGUI = null;
	__callsGUI = null;
	__hydroBaseStructureGUI = null;
	__hydroBaseStationQueryGUI = null;
	__editCallsGUI = null;
	__registerGUI = null;	
	__hydroBaseWaterRightsQueryGUI = null;
}

/**
This function must be called each time a user changes preferences
settings. For now it calls resetForNewUser which performs all the
necessary tasks when changing preferences.  However, this function should be
modified when new concerns are identified.
*/
public void resetPreferences() {
	resetForNewUser();
}

/**
This is abstract in HBGUIApp and is called by HBLoginGUI.  It has been replaced
internally by enableMenusBasedOnHydroBase().
@param state Indicates whether menus should be enabled due to a successful login.
*/
public void setJMenuItemState(boolean state) {
	enableMenusBasedOnHydroBase(state);
}

/**
Sets the map file to be used.
@param mapFile the mapfile to be used.
*/
public void setMapFile(String mapFile) {
	__mapFile = mapFile;
}

/**
Sets the map as visible or not, and the background image panel as the 
opposite of the visible parameter.  map visibility = visible, backgroundPanel
visibility = !visible.
@param visible whether the map is visible or not.
*/
public void setMapVisible(boolean visible) {
	__mapVisible = visible;
	if (visible) { 
		__geoViewJPanel.setVisible(visible);
		__backgroundJPanel.setVisible(!visible);
	}
	else {
		__backgroundJPanel.setVisible(!visible);
		__geoViewJPanel.setVisible(visible);
	}		
}

/**
Sets the text to display in the status bar and repaints the status bar instantly.
@param text the text to display in the status bar.
*/
public void setStatusText(String text) {
	__statusJTextField.setText(text);
	JGUIUtil.forceRepaint(__statusJTextField);
}

/**
Sets up the GUI display.
*/
private void setupGUI() {
	String routine = "setupGUI";
	
	try {
		UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	}
	catch (Exception e) {
		Message.printStatus ( 2, routine, e.toString() );
	}

	try {

	if (__runningStateView) {
		JGUIUtil.setAppNameForWindows("StateView");
	}
	else {	
		JGUIUtil.setAppNameForWindows("Colorado Water Rights Administration Tool");
	}

	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());

	addWindowListener(this);

	// objects used throughout the GUI layout
	GridBagLayout gbl = new GridBagLayout();
    BorderLayout    bl = new BorderLayout();
    getContentPane().setLayout(bl);

    // Set up the menu first.

	__appJMenuBar = new JMenuBar();

    // File menu...

	__fileJMenu = new JMenu(__FILE);
	__openJMenuItem = new SimpleJMenuItem(__FILE_OPEN, __FILE_OPEN, this);
	__fileJMenu.add(__openJMenuItem);

	__propertiesJMenuItem = new SimpleJMenuItem(__FILE_PROPERTIES, 
		__FILE_PROPERTIES, this);
	__fileJMenu.add(__propertiesJMenuItem);

	__spcbmi = new JCheckBoxMenuItem(__FILE_SP);
	__spcbmi.addActionListener(this);

	if (IOUtil.testing()) {
		__fileJMenu.addSeparator();
		__fileJMenu.add(__spcbmi);
	}

	__fileJMenu.addSeparator();         
	__exitJMenuItem = new SimpleJMenuItem(__FILE_EXIT, __FILE_EXIT, this);
	__fileJMenu.add(__exitJMenuItem);         
	__appJMenuBar.add(__fileJMenu);

    // View Data menu...
	__viewDataJMenu = new JMenu(__VIEWDATA);
	__appJMenuBar.add(__viewDataJMenu);
	__callChronologyJMenuItem = new SimpleJMenuItem(__VIEWDATA_CALLS,
				__VIEWDATA_CALLS, this);
	__viewDataJMenu.add(__callChronologyJMenuItem);
	__groundwaterJMenuItem = new SimpleJMenuItem(__VIEWDATA_GROUNDWATER,
				__VIEWDATA_GROUNDWATER, this);
	__viewDataJMenu.add(__groundwaterJMenuItem);	
	__otherJMenuItem = new SimpleJMenuItem(__VIEWDATA_OTHER,
				__VIEWDATA_OTHER, this);
	__viewDataJMenu.add(__otherJMenuItem);	

   	__stationJMenuItem = new SimpleJMenuItem(__VIEWDATA_STATIONS,
		__VIEWDATA_STATIONS, this);
	__viewDataJMenu.add(__stationJMenuItem);

	__structureJMenuItem = new SimpleJMenuItem(__VIEWDATA_STRUCTURES,
		__VIEWDATA_STRUCTURES, this);
	__viewDataJMenu.add(__structureJMenuItem);	

	__waterRightsJMenuItem = new SimpleJMenuItem(__VIEWDATA_WATER_RIGHTS,
		__VIEWDATA_WATER_RIGHTS, this);
	__viewDataJMenu.add(__waterRightsJMenuItem);

	__wellJMenuItem = new SimpleJMenuItem(__VIEWDATA_WELLS, __VIEWDATA_WELLS, this);
	__viewDataJMenu.add(__wellJMenuItem);
        
	// Administration menu...

	if (!__runningStateView) {
		// Running CWRAT, so add the administration menus...
		__administrationJMenu = new JMenu(__ADMINISTRATION);
		__appJMenuBar.add(__administrationJMenu);

		__editCallsJMenuItem = new SimpleJMenuItem(
			__ADMINISTRATION_EDIT_CALLS, __ADMINISTRATION_EDIT_CALLS, this);
		__administrationJMenu.add(__editCallsJMenuItem);

		__editWISJMenuItem = new SimpleJMenuItem(
			__ADMINISTRATION_LOAD_WIS, __ADMINISTRATION_LOAD_WIS, this);
		// TODO (JTS - 2003-03-24) when opened
		__editWISJMenuItem.setEnabled(false);
		__administrationJMenu.add(__editWISJMenuItem);

		/* TODO SAM 2004-01-11 - these menus are not currently supported.
		if (!IOUtil.isApplet()) {
			__administrationJMenu.addSeparator();
        		__syncDataJMenuItem = new SimpleJMenuItem(
				__ADMINISTRATION_DATA_SYNC,
				__ADMINISTRATION_DATA_SYNC, this);
			// REVISIT (JTS - 2003-03-24)
			// when opened
			__syncDataJMenuItem.setEnabled(false);
        		__administrationJMenu.add(__syncDataJMenuItem);

			__registerJMenuItem = new SimpleJMenuItem(
				__ADMINISTRATION_REGISTER,
				__ADMINISTRATION_REGISTER, this);
			// TODO (JTS - 2003-03-24) when opened
			__registerJMenuItem.setEnabled(false);
	       		__administrationJMenu.add(__registerJMenuItem);
		}

		__administrationJMenu.addSeparator();
		__dbAdminJMenuItem = new SimpleJMenuItem(
			__ADMINISTRATION_DATABASE_ADMIN,
			__ADMINISTRATION_DATABASE_ADMIN, this);
		// TODO (JTS - 2003-03-24) when opened
		// (JTS - 2004-01-21)
		// also uncomment the __dbAdminJMenuItem.setEnabled() calls
		// throughout the code
		__dbAdminJMenuItem.setEnabled(false);
	       	__administrationJMenu.add(__dbAdminJMenuItem);
		if (IOUtil.testing()) {
			__dbAdminJMenuItem = new SimpleJMenuItem(
				__ADMINISTRATION_STREAM_NETWORK,
				__ADMINISTRATION_STREAM_NETWORK, this);
	       		__administrationJMenu.add(__dbAdminJMenuItem);
		}
		END SAM REVISIT */
		__wisBuilderJMenuItem = new SimpleJMenuItem(
			__ADMINISTRATION_WIS_BUILDER, __ADMINISTRATION_WIS_BUILDER, this);
		// TODO (JTS - 2003-03-24) when opened
		__wisBuilderJMenuItem.setEnabled(false);		
       	__administrationJMenu.add(__wisBuilderJMenuItem);
	}

    // Tools menu...
	__toolsJMenu = new JMenu(__TOOLS);
	__appJMenuBar.add(__toolsJMenu);
	__adminNumCalcJMenuItem = new SimpleJMenuItem(__TOOLS_ADMIN_NUM_CALC,
		__TOOLS_ADMIN_NUM_CALC, this);
	__toolsJMenu.add(__adminNumCalcJMenuItem);

	// Not currently enabled...
	__divRecordsTransferJMenuItem = 
		new SimpleJMenuItem(__TOOLS_DIV_TRANSFER, __TOOLS_DIV_TRANSFER, this);
	// Not currently enabled...
	__pointFlowJMenuItem = new SimpleJMenuItem(__TOOLS_POINT_FLOW,
		__TOOLS_POINT_FLOW, this);

	__tsProductJMenuItem = new SimpleJMenuItem(__TOOLS_TS_PRODUCT,
		__TOOLS_TS_PRODUCT, this);
	__tsProductJMenuItem.setEnabled(false);
	__toolsJMenu.add(__tsProductJMenuItem);

	__toolsJMenu.addSeparator();

	__addSummaryJMenuItem = new SimpleJMenuItem(
		__TOOLS_ADD_SUMMARY, __TOOLS_ADD_SUMMARY, this);
	__addFlowJMenuItem = new SimpleJMenuItem(
		__TOOLS_ADD_FLOW, __TOOLS_ADD_FLOW, this);
	__toolsJMenu.add(__addSummaryJMenuItem);
	__toolsJMenu.add(__addFlowJMenuItem);
	__toolsJMenu.addSeparator();
	__addSummaryJMenuItem.setEnabled(false);
	__addFlowJMenuItem.setEnabled(false);

	__dataDictionaryJMenuItem = new SimpleJMenuItem(
		__TOOLS_DATA_DICTIONARY, __TOOLS_DATA_DICTIONARY, this);
	if (IOUtil.testing()) {
		__toolsJMenu.add(__dataDictionaryJMenuItem);
		__toolsJMenu.addSeparator();		
	}
	
   	__optionsJMenuItem = new SimpleJMenuItem(__TOOLS_OPTIONS,
		__TOOLS_OPTIONS, this);
	__toolsJMenu.add(__optionsJMenuItem);
	if (! IOUtil.isApplet()) {
		__toolsJMenu.addSeparator();
		DiagnosticsJFrame diagnosticsJFrame = new DiagnosticsJFrame();
		diagnosticsJFrame.attachMainMenu(__toolsJMenu);
	}
            
    // Help Dialogs

	__helpJMenu = new JMenu(__HELP);
        __appJMenuBar.add(__helpJMenu);
	// As of 2003-03-12, JMenuBar.setHelpMenu was not implemented
	// and threw an exception.
    //__appJMenuBar.setHelpMenu(__helpJMenu);
	if (__runningStateView) {
        __aboutJMenuItem = new SimpleJMenuItem(__HELP_ABOUT_STATEVIEW,
			__HELP_ABOUT_STATEVIEW, this);
	}
	else {
		__aboutJMenuItem = new SimpleJMenuItem(__HELP_ABOUT_CWRAT,
			__HELP_ABOUT_CWRAT, this);
	}
    __helpJMenu.add(__aboutJMenuItem);
	__Help_ViewDocumentation_JMenuItem = new SimpleJMenuItem(
		__Help_ViewDocumentation_String,__Help_ViewDocumentation_String, this);
    __helpJMenu.add(__Help_ViewDocumentation_JMenuItem);
	__helpJMenu.add ( __Help_ViewTrainingMaterials_JMenuItem = new SimpleJMenuItem(__Help_ViewTrainingMaterials_String,this));

    setJMenuBar(__appJMenuBar);

	// Create the panel that holds the body of the interface...

	JPanel centerJPanel = new JPanel(); 
	centerJPanel.setLayout(gbl);
    getContentPane().add("Center", centerJPanel);

	// The background image is shown by default initially.  If a GeoView
	// project file is specified in the preferences, the map interface will
	// come to the front of the image using setVisible(true).

	__backgroundJPanel = new PictureJPanel();
	JGUIUtil.addComponent(centerJPanel, __backgroundJPanel, 
		0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
	PropList props = new PropList("geoview");
	// Allow layers with an AppLayerType of "" or "BaseLayer" to be displayed...
	props.set("EnabledAppLayerTypes=BaseLayer");

    __statusJTextField = new JTextField(40);
	__trackerJTextField = new JTextField(30);

    __geoViewJPanel = new GeoViewJPanel(this, props, null, 
	__statusJTextField, __trackerJTextField);
	// The geoview panel shouldn't be visible until a map is opened
	__geoViewJPanel.setVisible(false);
	__mapVisible = false;
	// SAMX - disable while evaluating whether checks should be in individual windows.
	//__geoViewJPanel.getGeoView().addGeoViewListener(this);
    JGUIUtil.addComponent(centerJPanel, __geoViewJPanel, 
		0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
	props = null;

	__statusJTextField.setEditable(false);
	__trackerJTextField.setEditable(false);

	JPanel textFields = new JPanel();
	textFields.setLayout(new GridBagLayout());
	JGUIUtil.addComponent(textFields, __statusJTextField,
		0, 0, 1, 1, .5, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	
	JGUIUtil.addComponent(textFields, __trackerJTextField,
		1, 0, 1, 1, .35, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__tinyStatusJTextField = new JTextField(10);
	__tinyStatusJTextField.setEditable(false);
	JGUIUtil.addComponent(textFields, __tinyStatusJTextField,
		2, 0, 1, 1, .15, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);
	
    getContentPane().add("South", textFields);

    pack();
	if (!IOUtil.isApplet()) {
		loadBackgroundImage();
		setSize(650, 550);
	}
	else {
		setSize(300, 150);
	}

	if (__runningStateView) {
		setTitle("StateView");
	}
	else {	
		setTitle("Colorado Water Rights Administration Tool");
	}
	JGUIUtil.center(this);

    setVisible(true);

	// Disable menus that require HydroBase until login is complete...
    enableMenusBasedOnHydroBase(false);
    initialize(); 

/*
	this is done in the initialize above -- was resulting in two
	printings of "User level is " ...
	
	String user_level = determineUserLevel();

	enableMenusBasedOnUserLevel(user_level);
*/

	// Create hidden interfaces.  This is necessary to ensure that the
	// map interface listeners are in place.  Create the interfaces AFTER
	// creating the GeoViewJPanel and adding this main interface as a
	// GeoViewListener

	if (__dmi != null) {
		__hydroBaseStructureGUI = new HydroBase_GUI_StructureQuery(
			__dmi, this, this, false);
		__hydroBaseStationQueryGUI = new HydroBase_GUI_StationQuery(
			__dmi, this,	// JFrame
			this,			// GeoViewUI
			false);
		// __hydroBaseWellApplicationGUI = new HBWellQueryGUI(this, 
		//	false);
	}
	}
	catch(Exception e) {
		if (__runningStateView) {
			Message.printWarning(1, routine, "Error running "
				+ "StateView.  See the log file for details.");
			Message.printWarning(1, routine, e);
		}
		else {
			Message.printWarning(1, routine,
				"Error running CWRAT.  See the log file for details.");
			Message.printWarning(1, routine, e);
		}
	}

	if (!__runningStateView) {
		if (!IOUtil.isApplet()) {
//			__syncDataJMenuItem.setEnabled(false);
		}
//		__dbAdminJMenuItem.setEnabled(false);
	}	
}

/**
Called by HydroBase_GUI_RegisterUser to login and setup a new user (one 
different from the one that initially logged in)
@param login the user login
@param password the user password.
*/
public void setupNewUser(String login, String password)
{
	String routine = "CWRATMainJFrame.setupNewUser";
	resetForNewUser();
	__dmi.setUserLogin(login);
	__dmi.setUserPassword(password);
	try {
		__dmi.readUserPreferencesPropList(login, password);
	}
	catch (Exception e) {
		Message.printWarning(2, routine, "Error reading user preferences.");
		Message.printWarning(2, routine, e);
	}
	enableMenusBasedOnUserLevel(determineUserLevel());	
}

/**
View the documentation by displaying using the desktop application.
*/
private void uiAction_ViewDocumentation ()
{   String routine = getClass().getName() + ".uiAction_ViewDocumentation";
    // The location of the documentation is relative to the application home
	String docFileName;
	if ( this.__runningStateView ) {
		docFileName = IOUtil.getApplicationHomeDir() + "/doc/UserManual/StateView.pdf";
	}
	else {
		docFileName = IOUtil.getApplicationHomeDir() + "/doc/UserManual/CWRAT.pdf";
	}
    // Convert for the operating system
    docFileName = IOUtil.verifyPathForOS(docFileName, true);
    // Now display using the default application for the file extension
    Message.printStatus(2, routine, "Opening documentation \"" + docFileName + "\"" );
    try {
        Desktop desktop = Desktop.getDesktop();
        desktop.open ( new File(docFileName) );
    }
    catch ( Exception e ) {
        Message.printWarning(1, "", "Unable to display documentation at \"" + docFileName + "\" (" + e + ")." );
    }
}

/**
View the training materials by displaying in file browser.
*/
private void uiAction_ViewTrainingMaterials ()
{   String routine = getClass().getName() + ".uiAction_ViewTrainingMaterials";
    // The location of the documentation is relative to the application home
    String trainingFolderName = IOUtil.getApplicationHomeDir() + "/doc/Training";
    // Convert for the operating system
    trainingFolderName = IOUtil.verifyPathForOS(trainingFolderName, true);
    // Now display using the default application for the file extension
    Message.printStatus(2, routine, "Opening training material folder \"" + trainingFolderName + "\"" );
    try {
        Desktop desktop = Desktop.getDesktop();
        desktop.open ( new File(trainingFolderName) );
    }
    catch ( Exception e ) {
        Message.printWarning(1, "", "Unable to display training materials at \"" +
            trainingFolderName + "\" (" + e + ")." );
    }
}

/**
Responds to window activated events; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowActivated(WindowEvent evt) {}

/**
Responds to window closed events; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowClosed(WindowEvent evt) {}

/**
Responds to window closing events; closes the application.
@param evt the WindowEvent that happened.
*/
public void windowClosing(WindowEvent evt) {
	// Only handle for this window...
	Component c = evt.getComponent();
	if (c.equals(this)) {
		c = null;
		closeClicked();
	}
}

/**
Responds to window deactivated events; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowDeactivated(WindowEvent evt) {}

/**
Responds to window deiconified events; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowDeiconified(WindowEvent evt) {}

/**
Responds to window iconified events; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowIconified(WindowEvent evt) {}

/**
Responds to window opened events; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowOpened(WindowEvent evt) {}

// TODO later.  
JCheckBoxMenuItem __spcbmi = null;

}