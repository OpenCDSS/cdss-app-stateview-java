package DWR.DMI.StateView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.swing.JApplet;

import DWR.DMI.CWRAT.CWRAT;

/**
Main class for StateView.  This calls the CWRAT main() with "-viewonly true" command line argument.
*/
public class StateView extends JApplet
{

/**
This function instantiates StateView as an applet.
*/
public void init()
{
}

// FIXME SAM 2011-07-16 It appears that the launch4J configuration is not calling this main program, even
// when java.main.class is set to this class in the product.properties file.  The work-around right now is
// to use -Dviewonly=true in the StateView.l4j.ini file and let CWRAT pick up on that when parsing arguments.
/**    
This function starts the application.  Because StateView is simply CWRAT with "-viewonly true" command line
argument, just manipulate the command-line and call the CWRAT main method.
@param args command line arguments.
*/
public static void main(String args[])
{
	// Loop through the arguments and make sure that -viewonly true is set
	boolean found = false; // -viewonly found?
	boolean debug = false; // use this to troubleshoot setup with StateView & CWRAT
	PrintWriter out = null;
	if ( debug ) {
		try {
			out = new PrintWriter(new FileOutputStream(new File("C:\\junkStateView.txt")));
		}
		catch ( Exception e ) {
			
		}
		out.println("This is a debug file for CWRAT/StateView development.");
		out.println("args[] length = " + args.length);
	}
	for (int i = 0; i < args.length; i++) {
		if ( debug ) {
			out.println("StateView.main:  args[" + i + "]=\"" + args[i] + "\"");
		}
		if (args[i].equalsIgnoreCase("-viewonly")) {
			found = true;
			if ((i + 1)== args.length) {
				throw new RuntimeException("No argument provided to '-viewonly'");
			}
			i++;
			// Make sure that -viewonly is true
			args[i] = "true";
			// Loop through all arguments just in case -viewonly is specified more than once
		}
	}
	String [] argsNew = new String[0];
	if ( found ) {
		// Use as is
		argsNew = args;
	}
	else {
		// Add new arguments -viewonly true
		argsNew = new String[args.length + 2];
		System.arraycopy(args, 0, argsNew, 0, args.length);
		argsNew[args.length] = "-viewonly";
		argsNew[args.length + 1] = "true";
	}
	// Now call the CWRAT method.
	if ( debug ) {
		for (int i = 0; i < argsNew.length; i++) {
			out.println("StateView.main:  argsNew[" + i + "]=\"" + argsNew[i] + "\"");
		}
		out.close();
	}
	CWRAT.main(argsNew);
}

}