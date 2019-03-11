
[![Build
Status](https://travis-ci.org/CoatiSoftware/eSourcetrail.svg?branch=master)](https://travis-ci.org/CoatiSoftware/eSourcetrail)


# eSourcetrail

eSourcetrail is a plugin for Eclipse to synchronize the displayed content with [Sourcetrail](https://Sourcetrail.com).


## Install

To add this plugin to your Eclipse installation, just add the updatesite of this plugin to Eclipse via via **"Help" -> "Install New Software..."**

```
http://CoatiSoftware.github.io/eSourcetrail/updatesite
```


## Usage

To use the functionality of this plugin you need to have an open project in your workspace.


### Sourcetrail to Eclipse Synchronization

* With a source file displayed in Sourcetrail's code view, right click a location in the code view and from the context menu choose "Set IDE Cursor"
* Now Eclipse should open the respective file and place the cursor at the position sent form Sourcetrail.


### Eclipse to Sourcetrail Synchronization

* With a source file displayed in Eclipse, you can 
	* either right click a location in the code and from the context menu choose **"eSourcetrail" -> "Send Location to Sourcetrail"**
	* or position your text cursor at a location in the code and press the configured shortcut (See section [Preferences: Keyboard shortcuts](#keyboard-shortcut) below.
* Now Sourcetrail should open the respective file and place the cursor at the position sent form Eclipse.


## Preferences

In Eclipse you can find and change the Sourcetrail plugin settings at **"Window" -> "Preferences"**.


### Ports

In the category **"Sourcetrail Preferences"** you can change the ports that are used by Eclipse and Sourcetrail to communicate. Make sure that the same settings are applied to both, Eclipse and Sourcetrail.


### Keyboard shortcuts

In the category **"General" -> "Keys"** you can bind the keyboard shortcut for the action **ActivateToken** to configure the input that triggers Eclipse to send your current text cursor location to Sourcetrail.


## Building

This project is built using [Eclipse Tycho](https://www.eclipse.org/tycho/) and requires at least [maven 3.0](http://maven.apache.org/download.html) to be built via CLI.
From the root directory of the cloned repository simply run:

```
$ mvn install
```

The first run will take quite a while since maven will download all the dependencies that are required to build everything.

In order to use the generated eclipse plugins in Eclipse, you will need [m2e](https://www.eclipse.org/m2e)
and the [m2eclipse-tycho plugin](https://github.com/tesla/m2eclipse-tycho/). 

You can add these plugins to your Eclipse installation by adding these update sites:
* m2e stable update site: http://download.eclipse.org/technology/m2e/releases/
* m2eclipse-tycho dev update site: http://repo1.maven.org/maven2/.m2e/connectors/m2eclipse-tycho/0.7.0/N/0.7.0.201309291400/


## Development

### New Version number
```
mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=<newVersion>
```
