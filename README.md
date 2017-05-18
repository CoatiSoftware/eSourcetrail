
[![Build
Status](https://travis-ci.org/CoatiSoftware/eSourcetrail.svg?branch=master)](https://travis-ci.org/CoatiSoftware/eSourcetrail)

# eSourcetrail

eSourcetrail is a plugin for Eclipse to communicate with [Sourcetrail](https://Sourcetrail.com).

## Install

add the repository address: http://CoatiSoftware.github.io/eSourcetrail/updatesite

## Usage

To work with the plugin you need the project open in your workspace.

### Sourcetrail to Eclipse

Right click in Sourcetrail > Set IDE Curor | Eclipse should now open the file and put the cursor in the position form Sourcetrail.

### Eclipse to Sourcetrail

Navigate your textcursor to the location.

* Rightclik **eSourcetrail -> Sent Location to Sourcetrail**
* Keybinding (see preferences)

## Preferences

### Ports and Ip

In Eclipse in the menu **Window -> Preferences -> Sourcetrail Preferences** there is a Section for Sourcetrail.
You can change the ports and ip.
Make sure you use the same settings in Sourcetrail

### Keyboard shortcut

Keyboard shortcut for to send a location to Sourcetrail can be set at **Window -> Preferences -> General -> Keys**
Bind the ActivateToken command as you like.

## Building

This project is built using Eclipse Tycho (https://www.eclipse.org/tycho/) and requires at least maven 3.0 (http://maven.apache.org/download.html) to be built via CLI.
Simply run :

    mvn install

The first run will take quite a while since maven will download all the required dependencies in order to build everything.

In order to use the generated eclipse plugins in Eclipse, you will need m2e (https://www.eclipse.org/m2e)
and the m2eclipse-tycho plugin (https://github.com/tesla/m2eclipse-tycho/). Update sites to install these plugins :

* m2e stable update site : http://download.eclipse.org/technology/m2e/releases/
* m2eclipse-tycho dev update site : http://repo1.maven.org/maven2/.m2e/connectors/m2eclipse-tycho/0.7.0/N/0.7.0.201309291400/

## Development

### New Version number

mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=<newVersion>
