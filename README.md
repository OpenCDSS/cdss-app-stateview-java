# cdss-app-stateview-java #

This repository contains the StateView main application source code and supporting files for the development environment.
Multiple other repositories are used to create the StateView application.
Eclipse is used for development and repositories currently contain Eclipse project files to facilitate
setting up the Eclipse development environment.

StateView is part of
[Colorado's Decision Support Systems (CDSS)](https://www.colorado.gov/cdss).
See the following online resources:

* [Colorado's Decision Support Systems](https://www.colorado.gov/cdss)
* [OpenCDSS](http://learn.openwaterfoundation.org/cdss-emod-dev/) - currently
hosted on the Open Water Foundation website while the OpenCDSS server is configured

This README serves as the developer documentation.
See TSTool developer documentation for more expansive information for similar Eclipse/Java development environment.

* [Repository Folder Structure](#repository-folder-structure)
* [Repository Dependencies](#repository-dependencies)
* [Development Environment Folder Structure](#development-environment-folder-structure)
* [Configuring Eclipse Workspace](#configuring-eclipse-workspace)
* [Compiling](#compiling)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)

-----

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
  conf/                       Configuration files for installer build tools.
  dist/                       Folder used to build distributable installer (ignored from repo).
  externals/                  Third-party libraries and tools (may remove/move in future).
  graphics/                   Images (may remove/move in future).
  installer/                  StateView-specific files used to create installer.
  lib/                        Third-party libraries.
  LICENSE.md                  StateView license file.
  README.md                   This file.
  resources/                  Additional resources, such as runtime files for installer.
  scripts/                    Eclipse run and external tools configurations.
  src/                        StateView main application source code.
  test/                       Unit tests using JUnit.
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
3. For the resulting repository, use Git Bash to run `build-util/git-clone-all-stateview.sh`.
4. Create the `eclipse-workspace` folder as shown in the above folder structure.
5. In Eclipse, use the ***File / Import / General / Existing Projects into Eclipse***.
Browse to the `git-repos` folder and select all the projects that are listed and click on ***Finish***.
6. The projects should import and compile automatically.

## Compiling ##

The software should compile automatically when the Eclipse workspace is opened.

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
