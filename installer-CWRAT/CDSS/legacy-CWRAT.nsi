#############################################################
# AUTHOR: Kurt Tometich
# DATE: Aug 24, 2006
#
# BRIEF:
#  This NSIS script creates an executable
#  install file:"CWRAT_version_setup.exe"
#  This .exe install file will setup CWRAT
#  programs and allow the user to choose
#  from certain components to install
#
# COMPONENTS:
#  BaseComponents - base jar files in CDSS\bin
#  Documentation  - pdf's and text files for this release
#  CWRAT - installs CWRAT specific files (jars, bats, etc)
#  Start Menu Shortcuts - installs shortcuts to run CWRAT
#                          and uninstall CWRAT
#  DesktopShortcut - should be self explanatory
#
##############################################################

#################################
Name CWRAT
# Defines
!define REGKEY "Software\State of Colorado\CDSS\$(^Name)"
!define VERSION 4.00.00
!define COMPANY RTi
!define URL http://www.riverside.com
!define EXTERNALS_DIR "..\..\externals"
!define JRE_VERSION "142"
!define INSTALL_IS_CDSS "true"

# Included files
!include "UMUI.nsh"
!include "Registry.nsh"
!include "TextReplace.nsh"

# Reserved Files
#ReserveFile "${NSISDIR}\Plugins\StartMenu.dll"

### Use custom button text
MiscButtonText "Back" "Next" "Cancel" "Done"

# Variables
Var StartMenuGroup
Var myInstDir
Var choseCWRAT
Var choseDocs
Var choseJRE

# Installer attributes
OutFile "CWRAT_${VERSION}_Setup.exe"
InstallDir "C:\CDSS"
InstallDirRegKey HKLM "${REGKEY}" Path

# MUI defines
!define MUI_ICON "${NSISDIR}\Contrib\Graphics\Icons\modern-install.ico"
!define MUI_FINISHPAGE_NOAUTOCLOSE
!define MUI_STARTMENUPAGE_REGISTRY_ROOT HKLM
!define MUI_STARTMENUPAGE_NODISABLE
!define MUI_STARTMENUPAGE_REGISTRY_KEY "Software\State of Colorado\CDSS\$(^Name)"
!define MUI_STARTMENUPAGE_REGISTRY_VALUENAME StartMenuGroup
!define MUI_STARTMENUPAGE_DEFAULT_FOLDER CDSS
!define MUI_UNICON "${NSISDIR}\Contrib\Graphics\Icons\modern-uninstall.ico"
!define MUI_UNFINISHPAGE_NOAUTOCLOSE
!define MUI_ABORTWARNING

# MUI Overrides for Text
!define MUI_PAGE_HEADER_SUBTEXT "This wizard will guide you through the installation of $(^Name)"
!define MUI_WELCOMEPAGE_TEXT "It is recommended that you close all other CDSS applications before starting this Setup. This will make it possible to update relevant CDSS files without any conflicts.  Please close all CDSS specific applications, files and folders and click Next to continue."
!define MUI_COMPONENTSPAGE_TEXT_TOP "Select the components to install by checking the corresponding boxes.  Click Next to continue."
!define MUI_COMPONENTSPAGE_TEXT_DESCRIPTION_INFO "Position the mouse over a component to view its description."
!define MUI_DIRECTORYPAGE_TEXT_TOP "Setup will install $(^Name) in the following folder.  It is recommended that the main CDSS folder be specified.  To install in a different folder, click Browse and select another folder.  Click Next to continue."
!define MUI_STARTMENUPAGE_DEFAULTFOLDER "CDSS"

### Use custom button text
MiscButtonText "Back" "Next" "Cancel" "Done"

### Pages ###
!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_LICENSE "License.txt"
!insertmacro MUI_PAGE_COMPONENTS
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_STARTMENU Application $StartMenuGroup
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES
Page custom SetCustom

# Installer language
!insertmacro MUI_LANGUAGE English

ReserveFile "..\..\externals\CDSS\installer\server_name.ini"
!insertmacro MUI_RESERVEFILE_INSTALLOPTIONS

!include ..\..\externals\NSIS_Common\PathManipulation.nsh
!include ..\..\externals\NSIS_Common\Util.nsh
!include ..\..\externals\CDSS\installer\BaseComponents.nsh
!include ..\..\externals\CDSS\installer\server_name.nsh
!include ..\..\externals\NSIS_Common\JRE.nsh

##################################################################
# SECTION: -setSharedInstallDir
# 
# initializes some global variables
#   myInstDir - users chosen install directory
#   choseCWRAT - Used for dependencies in later sections
#                0 = user chose not to install CWRAT,
#                1 = user chose to install CWRAT
#  
# BRIEF:
#  The minus sign at the beginning of the section name
#  is used to make this a hidden section to the user
#  this means they cannot choose to not run it
###################################################################
Section -setSharedInstallDir

    strcpy $myInstDir $INSTDIR
    strcpy $choseCWRAT "0"
    strcpy $choseDocs "0"
    strcpy $choseJRE "0"
    strcpy $StartMenuGroup "CDSS"
    SetShellVarContext all
    
SectionEnd


########################################################
# SECTION: -Main
# 
# BRIEF:
#  used to write the Reg Key for the components
########################################################
Section -Main
    WriteRegStr HKLM "${REGKEY}\Components" Main 1
SectionEnd


##################################################
# SECTION: CWRAT
#
# BRIEF: 
#  Installs the CWRAT specific files.
#  These may change each release so
#  the files included may need to be 
#  updated.  Before each file is placed
#  on the users machine it is checked
#  to make sure we don't overwrite
#  a newer or current version.  Each
#  file is checked with a Utility function
#  located in Util.nsh:CompareFileModificationDates  
#
##################################################
Section "CWRAT" CWRAT

    # set choseCWRAT variable to true since it was chosen
    strcpy $choseCWRAT "1"

    # copy important bat/jar files specific to this product
    SetOverwrite ifnewer
    SetOutPath $INSTDIR\bin
    
    # check access date/time
    File ..\..\dist\CWRAT_142.jar
    
    # check access date/time
    File ..\..\scripts\cwrat.bat
    
    # check access date/time
    SetOutPath $INSTDIR\system
    File ..\..\graphics\CWRAT.gif
    
    # Insert the -home Directory into the .bat file
    # according to the user's install location
    ${textreplace::ReplaceInFile} "$INSTDIR\bin\CWRAT.bat" "$INSTDIR\bin\CWRAT.bat" "SET HOMED=\CDSS" "SET HOMED=$INSTDIR" "" $0
    
    # copy the NativeJ files used to create the executable
    SetOutPath $INSTDIR\bin
    File CWRAT.exe
    File CWRAT.ini
    
    # Replace argument for -home in NativeJ property file
    ${textreplace::ReplaceInFile} "$INSTDIR\bin\CWRAT.ini" \
    "$INSTDIR\bin\CWRAT.ini" "-home C:\CDSS" "-home $INSTDIR" "" $0
    
     # Write some registry keys
    WriteRegStr HKLM "${REGKEY}" Path $INSTDIR
    WriteRegStr HKLM "${REGKEY}" StartMenuGroup $StartMenuGroup
    SetOverwrite off
    CreateDirectory "$INSTDIR\uninstall"
    WriteUninstaller $INSTDIR\uninstall\$(^Name).exe
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayName "$(^Name)"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayVersion "${VERSION}"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" Publisher "${COMPANY}"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" URLInfoAbout "${URL}"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayIcon $INSTDIR\Uninstall_$(^Name).exe
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" UninstallString $INSTDIR\Uninstall_$(^Name).exe
    WriteRegDWORD HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" NoModify 1
    WriteRegDWORD HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" NoRepair 1
    
SectionEnd

#########################################
# SECTION: Documentation
#
# BRIEF:
#  Installs current documentation
#  for CWRAT to C:\CDSS\doc\CWRAT
#
# The /o stands for optional.  This
# allows the component page to uncheck
# this box by default.
#########################################
Section "Documentation" Docs

    # set boolean choseDocs since documentation was selected
    strcpy $choseDocs "1"

    # copy documentation
    SetOutPath $INSTDIR\doc\CWRAT
    SetOverwrite on
    File /r /x *svn* ..\..\doc\CWRAT\*

SectionEnd

##############################################
# SECTION: Start Menu Shortcuts
#
# BRIEF: 
#  This section creates the start -> apps
#  shortcuts as CDSS -> CWRAT -> uninstall
#                             -> run cwrat
#  
##############################################
Section "Start Menu Shortcuts" StartMenu

    # make sure user chose to install CWRAT
    strcmp $choseCWRAT "0" 0 +2
      Goto skipMenu
    
    !insertmacro MUI_STARTMENU_WRITE_BEGIN Application
    
    SetOutPath $SMPROGRAMS\$StartMenuGroup
    SetOutPath $INSTDIR\bin
    CreateShortCut "$SMPROGRAMS\$StartMenuGroup\CWRAT.lnk" "$INSTDIR\bin\CWRAT.exe"
    #CreateShortCut "$SMPROGRAMS\$StartMenuGroup\CWRAT.lnk" "$INSTDIR\jre_142\bin\javaw.exe" "-Xmx256m -cp $\"CWRAT_142.jar;HydroBaseDMI_142.jar;SatmonSysDMI_142.jar;mssqlall.jar;RTi_Common_142.jar$\" DWR.DMI.CWRAT.CWRAT -home ../" "C:\CDSS\graphics\waterMark.bmp"
    
    # Shortcut for uninstall of program
    SetOutPath $SMPROGRAMS\$StartMenuGroup\Uninstall
    CreateShortcut "$SMPROGRAMS\$StartMenuGroup\Uninstall\$(^Name).lnk" $INSTDIR\uninstall\$(^Name).exe
    
    skipMenu:
    
    # make sure user chose to install docs
    strcmp $choseDocs "0" 0 +2
      Goto Done
      
    # Shortcut for CWRAT documentation
    SetOutPath $SMPROGRAMS\$StartMenuGroup\Documentation
    CreateShortcut "$SMPROGRAMS\$StartMenuGroup\Documentation\$(^Name).lnk" $INSTDIR\doc\$(^Name)\UserManual\$(^Name).pdf

    !insertmacro MUI_STARTMENU_WRITE_END
      
    Done:
    
    
SectionEnd


############################################
# SECTION: DesktopShortcut
#
# BRIEF:
#  should be pretty obvious what this does.
#  If its not, you shouldn't be reading this
#  
############################################
Section /o "Desktop Shortcut" DesktopShortcut

    # make sure user chose to install CWRAT
    strcmp $choseCWRAT "0" 0 +2
      Goto skipShortcut
   
    # Installs shortcut on desktop
    SetOutPath $INSTDIR\bin
    CreateShortCut "$DESKTOP\CWRAT.lnk" "$INSTDIR\bin\CWRAT.exe"
    #CreateShortCut "$DESKTOP\CWRAT.lnk" "$INSTDIR\jre_142\bin\javaw.exe" "-Xmx256m -cp $\"CWRAT_142.jar;HydroBaseDMI_142.jar;SatmonSysDMI_142.jar;mssqlall.jar;RTi_Common_142.jar$\" DWR.DMI.CWRAT.CWRAT -home ../" "C:\CDSS\graphics\waterMark.bmp"

    skipShortcut:

SectionEnd


###########################################
# SECTION: Uninstall
#
# BRIEF: Deletes files and RegKeys
###########################################
Section Uninstall
    
    SetShellVarContext all

    # get the number of other CDSS installed programs
    #Push "HKEY_LOCAL_MACHINE\Software\State Of Colorado\CDSS"
    #Call un.getNumSubKeys
    #Pop $R1
    #DetailPrint "Number Installed Comp:$R1"

    # Get the StartMenuFolder
    !insertmacro MUI_STARTMENU_GETFOLDER Application $StartMenuGroup
    
    DeleteRegKey HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\Uninstall\$(^Name).lnk"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\Documentation\$(^Name).lnk"
    RmDir /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\Documentation"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\Uninstall\"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\$(^Name).lnk"
    Delete /REBOOTOK "$INSTDIR\$(^Name).lnk"
    Delete /REBOOTOK "$DESKTOP\$(^Name).lnk"
    Delete /REBOOTOK $INSTDIR\Uninstall_$(^Name).exe
    DeleteRegValue HKLM "${REGKEY}" StartMenuGroup
    DeleteRegValue HKLM "${REGKEY}" Path
    DeleteRegKey /IfEmpty HKLM "${REGKEY}\Components"
    DeleteRegKey /IfEmpty HKLM "${REGKEY}"
    RmDir /REBOOTOK $SMPROGRAMS\$StartMenuGroup
    RmDir /REBOOTOK $SMPROGRAMS\CDSS\Uninstall
    RmDir /REBOOTOK $SMPROGRAMS\CDSS
    Delete /REBOOTOK $INSTDIR\uninstall\$(^Name).exe
    
    Delete /REBOOTOK $INSTDIR\bin\CWRAT.bat
    # Don't uninstall CWRAT since other programs might depend on it
    #Delete /REBOOTOK $INSTDIR\bin\CWRAT_142.jar
    RmDir /r /REBOOTOK $INSTDIR\doc\CWRAT
    Delete /REBOOTOK $INSTDIR\system\CWRAT.gif
    Delete /REBOOTOK $INSTDIR\cwrat_version.txt
    Delete /REBOOTOK $INSTDIR\CWRAT_README.txt
    RmDir /r /REBOOTOK $INSTDIR\doc\CWRAT
    RmDir /REBOOTOK $INSTDIR\doc
    Delete /REBOOTOK $INSTDIR\bin\CWRAT.exe
    Delete /REBOOTOK $INSTDIR\bin\CWRAT.ini
    DeleteRegValue HKLM "${REGKEY}\Components" Main
    
    # uninstall base components
    Call un.BaseComponents
    
SectionEnd

### Section Descriptions ###
!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
  !insertmacro MUI_DESCRIPTION_TEXT ${Docs} "Enabling this component will install documentation to the CDSS\doc\CWRAT folder"
  !insertmacro MUI_DESCRIPTION_TEXT ${CWRAT} "Enabling this component will install all CWRAT software in the CDSS\bin folder."
  !insertmacro MUI_DESCRIPTION_TEXT ${StartMenu} "Enabling this component will install start menu folders and icons"
  !insertmacro MUI_DESCRIPTION_TEXT ${DesktopShortcut} "Enabling this component will install a desktop shortcut to run the CWRAT application"
  !insertmacro MUI_DESCRIPTION_TEXT ${BaseComponents} "Enabling this component will install the CDSS base components, including software and configuration files used by multiple CDSS software applications."
  !insertmacro MUI_DESCRIPTION_TEXT ${JRE} "Enabling this component will install the Java Runtime Environment (JRE), which is used to run CDSS software.  CDSS software uses its own copy of the JRE, regardless of what may already by installed elsewhere on the computer."
  
!insertmacro MUI_FUNCTION_DESCRIPTION_END


################################################
# FUNCTION: .onInstSuccess
#
# BRIEF: NSIS default function
#  When installation is successful and the
#  user clicks the close button, this function
#  is called.  It prompts the user to execute
#  the program and view the readme
################################################
Function .onInstSuccess

    SetOutPath $INSTDIR\bin
    
    # if 0 then user didn't choose to install CWRAT
    strcmp $choseCWRAT "0" 0 +2
      Goto skipThis
    
    
    # Run NativeJ to create the .exe file
    ExecWait '"$INSTDIR\bin\nativejc.exe" $INSTDIR\bin\CWRAT.njp'
    
    MessageBox MB_YESNO "Would you like to run the program?" IDYES true IDNO false
    true:
      Exec '"$INSTDIR\bin\CWRAT.exe"'
      #Exec '"$INSTDIR\jre_142\bin\javaw.exe" -Xmx256m -cp $\"CWRAT_142.jar;HydroBaseDMI_142.jar;SatmonSysDMI_142.jar;mssqlall.jar;RTi_Common_142.jar$\" DWR.DMI.CWRAT.CWRAT -home ../'
      Goto next
    false:
      DetailPrint "User chose to not start application"
    next:
    
    ##### Uncomment later to ask for view readme #####
    #MessageBox MB_YESNO "Would you like to view the README?" IDYES yes IDNO no
    #yes:
    #  Exec 'notepad.exe $INSTDIR\CWRAT_README.txt'
    #Goto next2
    #no:
    #  DetailPrint "Skipping README"
    #next2:
    
    skipThis:
    
FunctionEnd


########################################
# FUNCTION: .onInit
#
# BRIEF: NSIS default function
#  executes on Init of Outfile created
#
########################################
Function .onInit
    
    InitPluginsDir
    !insertmacro MUI_INSTALLOPTIONS_EXTRACT_AS "..\..\externals\CDSS\installer\server_name.ini" "server_name.ini"
    
    # check user privileges and abort if not admin
    ClearErrors
    UserInfo::GetName
    IfErrors Win9x
    Pop $0
    UserInfo::GetAccountType
    Pop $1
    StrCmp $1 "Admin" 0 +3
        #MessageBox MB_OK 'User "$0" is in the Administrators group'
        Goto done
    StrCmp $1 "Power" 0 +3
        #MessageBox MB_OK 'User "$0" is in the Power Users group'
        Goto InsufficientRights
    StrCmp $1 "User" 0 +3
        #MessageBox MB_OK 'User "$0" is just a regular user'
        Goto InsufficientRights
    StrCmp $1 "Guest" 0 +3
        #MessageBox MB_OK 'User "$0" is a guest'
        Goto InsufficientRights
    MessageBox MB_OK "Unknown error"
    Goto done

    Win9x:
        # This one means you don't need to care about admin or
        # not admin because Windows 9x doesn't either
        MessageBox MB_OK "Error! This DLL can't run under Windows 9x!"
        Abort
        
    InsufficientRights:
        MessageBox MB_OK "You must log on as an administrator to install this application"
        Abort
        
    done:
    
    # read the CDSS registry key
    ReadRegStr $0 HKLM SOFTWARE\CDSS "Path"
    
    # check if the RegKey exists
    strcmp "$0" "" 0 +2
    Goto noCDSSFound
    
    # change the $INSTDIR to the path to the previously installed  
    strcpy $INSTDIR $0
    
    noCDSSFound:
    
FunctionEnd