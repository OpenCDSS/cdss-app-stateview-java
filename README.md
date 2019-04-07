# cdss-app-stateview-java #

This repository contains the StateView main application source code and supporting files for the development environment.
Multiple other repositories are used to create the StateView application.
Eclipse is used for development and repositories currently contain Eclipse project files to facilitate
setting up the Eclipse development environment.

StateView is part of
[Colorado's Decision Support Systems (CDSS)](https://www.colorado.gov/cdss) and provides a desktop tool for viewing
the State of Colorado's HydroBase database, when HydroBase is installed on a desktop computer or server
with ODBC access.
See the following online resources:

* [Colorado's Decision Support Systems](https://www.colorado.gov/cdss)
* [OpenCDSS](http://learn.openwaterfoundation.org/cdss-emod-dev/) - currently
hosted on the Open Water Foundation website while the OpenCDSS server is configured

This README serves as the developer documentation.
See TSTool developer documentation for more expansive information for similar Eclipse/Java development environment.

* [Repository Status](#repository-status)
* [StateView and CWRAT Integration](#stateview-and-cwrat-integration)
* [Repository Folder Structure](#repository-folder-structure)
* [Repository Dependencies](#repository-dependencies)
* [Development Environment Folder Structure](#development-environment-folder-structure)
* [Configuring Eclipse Workspace](#configuring-eclipse-workspace)
* [Compiling](#compiling)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)

-----

## Repository Status ##

See the next section for background on the repository contents.
The most recent code has been moved into GitHub.
However, given that StateView is not actively developed,
some of the automated processes used to release the software have not been fully tested.
If a release needs to be made, the build processes will need to be fully tested.
These processes are similar to TSTool.
The software can be run from the development environment.

## StateView and CWRAT Integration ##

The code in this repository is a combination of previously separate code for
StateView and Colorado Water Rights Administration Tool (CWRAT).
The main repository previously focused on CWRAT, which includes the data viewing features of StateView
and features to perform water rights administration, including Water Information Sheet (WIS) features.
A smaller repository for StateView was previously maintained, mainly to store the configuration and installer
files to create separate StateView installer.
Running StateView and CWRAT is essentially, with `-viewonly` command-line option indicating which version is running.

Use of CWRAT was phased out in favor of other tools and mainly has historical value.
StateView continues to be used by some, especially modelers with local HydroBase installations,
because it provides faster access to data than online tools.
The code from both products was consolidated into this repository with `-CWRAT` added to folders that
are specific to CWRAT.
Going forward, work can focus on StateView, with CWRAT retained in the repository for historical reference.

TSTool also now provides many features comparable to StateView, such as being able to query
database tables as datastores (see ReadTableFromDataStore command).

## Repository Folder Structure ##

The following are the main folders and files in this repository, listed alphabetically.
See also the [Development Environment Folder Structure](#development-environment-folder-structure)
for overall folder structure recommendations.

```
cdss-app-stateview-java/      StateView source code and development working files.
  .classpath                  Eclipse configuration file.
  .git/                       Git repository folder (DO NOT MODIFY THIS except with Git tools).
  .gitattributes              Git configuration file for repository.
  .gitignore                  Git configuration file for repository.
  .project                    Eclipse configuration file.
  bin/                        Eclipse folder for compiled files (dynamic so ignored from repo).
  build-util/                 Utility scripts used in development environment.
  conf/                       Configuration files for StateView installer build tools.
  conf-CWRAT/                 Configuration files for CWRAT installer build tools.
  dist/                       Folder used to build distributable installer (ignored from repo).
  externals/                  Third-party libraries and tools for StateView (may remove/move in future).
  externals-CWRAT/            Third-party libraries and tools for CWRAT (may remove/move in future).
  graphics/                   Images (may remove/move in future).
  installer/                  StateView-specific files used to create installer.
  installer-CWRAT/            CWRAT-specific files used to create installer.
  lib/                        Third-party libraries.
  LICENSE.md                  StateView license file.
  README.md                   This file.
  resources/                  Additional StateView resources, such as runtime files for installer.
  resources-CWRAT/            Additional CWRAT resources, such as runtime files for installer.
  scripts/                    Eclipse run and external tools configurations.
  src/                        StateView (and CWRAT) main application source code.
  test/                       Test configuration for StateView.
  test-CWRAT/                 Test configuration for CWRAT.
```

## Repository Dependencies ##

Repository dependencies fall into three categories as indicated below.

### StateView Repository Dependencies ###

The main StateView code depends on other repositories
The following repositories are used to create the main StateView application.
Some repositories correspond to Eclipse projects and others are not used within Eclipse,
indicated as follows:

* Y - repository is included as Eclipse project.
* Y2 - repository is currently included as Eclipse project but may be phased out or
converted to a plugin because code is obsolete or is specific to third parties.
* y - repository is included as Eclipse project but does not need to be.  The project may have been added to Eclipse to use the Git client,
but files are often edited external to Eclipse.
* N - repository is managed outside if Eclipse,
such as documentation managed with command line Git or other Git tools.

|**Repository**|**Eclipse project?**|**Description**|
|-------------------------------------------------------------------------------------------------------------|--|----------------------------------------------------|
|`cdss-app-stateview-java`                                                                         |Y |StateView main application code (this repo).|
|[`cdss-archive-nsis-2.46`](https://github.com/OpenCDSS/cdss-archive-nsis-2.46)                    |N |Archive of NSIS 2.46, to set up development environment.|
|[`cdss-lib-common-java`](https://github.com/OpenCDSS/cdss-lib-common-java)                        |Y |Library of core utility code used by multiple repos.|
|[`cdss-lib-dmi-hydrobase-java`](https://github.com/OpenCDSS/cdss-lib-dmi-hydrobase-java)          |Y |Library to directly access Colorado's HydroBase database.|
|[`cdss-util-buildtools`](https://github.com/OpenCDSS/cdss-util-buildtools)                        |Y |Tools to create CDSS Java software installers.|

### Repositories that Depend on StateView Repository ###

This repository is not known to be a dependency for any other projects.

## Development Environment Folder Structure ##

The following folder structure is recommended for StateView development.
Top-level folders should be created as necessary.
Repositories are expected to be on the same folder level to allow cross-referencing
scripts in those repositories to work.

```
C:\Users\user\                               Windows user home folder (typical development environment).
/home/user/                                  Linux user home folder (not tested).
/cygdrive/C/Users/user                       Cygdrive home folder (not tested).
  cdss-dev/                                  Projects that are part of Colorado's Decision Support Systems.
    StateView/                               StateView product folder.
      eclipse-workspace/                     Folder for Eclipse workspace, which references Git repository folders.
                                             The workspace folder is not maintained in Git.
      git-repos/                             Git repositories for TSTool.
        cdss-app-stateview-java/
        cdss-archive-nsis-2.46/
        cdss-lib-common-java/
        cdss-lib-dmi-hydrobase-java/
        cdss-util-buildtools/
```

## Configuring Eclipse Workspace ##

1. The Eclipse software should be installed as per TSTool development environment.
2. In the `git-repos` folder, clone the main repository with:  `git clone https://github.com/OpenCDSS/cdss-app-stateview-java.git`.
3. For the resulting repository, use Git Bash to run `build-util/git-clone-all-stateview.sh`,
which will clone all other needed repositories (will skip any that are already cloned).
4. Create the `eclipse-workspace` folder as shown in the above folder structure.
5. In Eclipse, use the ***File / Import / General / Existing Projects into Eclipse***.
Browse to the `git-repos` folder and select all the projects that are listed and click on ***Finish***.
6. The projects should import and compile automatically.
7. The `build-util` git scripts are useful for checking the status on all repositories and performing common tasks.

## Compiling, Running, and Distributing ##

The software should compile automatically when the Eclipse workspace is opened.
Run using the ***Run Configurations***, for example ***StateView*** run configuration to launch StateView.

## Contributing ##

Contributions to this project can be submitted using the following options:

1. StateView software developers with commit privileges can write to this repository
as per normal OpenCDSS development protocols.
2. Post an issue on GitHub with suggested change.  Provide information using the issue template.
3. Fork the repository, make changes, and do a pull request.
Contents of the current master branch should be merged with the fork to minimize
code review before committing the pull request.

See also the [OpenCDSS / StateView protocols](http://learn.openwaterfoundation.org/cdss-website-opencdss/stateview/stateview/).

## License ##

Copyright Colorado Department of Natural Resources.

The software is licensed under GPL v3+. See the [LICENSE.md](LICENSE.md) file.

## Contact ##

See the [OpenCDSS TSTool information for product contacts](http://learn.openwaterfoundation.org/cdss-website-opencdss/stateview/stateview/#product-leadership).
