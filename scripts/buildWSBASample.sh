#!/bin/sh

# COPYRIGHT LICENSE: This information contains sample code provided in 
# source code form. You may copy, modify, and distribute these sample 
# programs in any form without payment to IBM for the purposes of 
# developing, using, marketing or distributing application programs 
# conforming to the application programming interface for the operating 
# platform for which the sample code is written. Notwithstanding anything 
# to the contrary, IBM PROVIDES THE SAMPLE SOURCE CODE ON AN "AS IS" BASIS 
# AND IBM DISCLAIMS ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING, BUT NOT 
# LIMITED TO, ANY IMPLIED WARRANTIES OR CONDITIONS OF MERCHANTABILITY, 
# SATISFACTORY QUALITY, FITNESS FOR A PARTICULAR PURPOSE, TITLE, AND ANY 
# WARRANTY OR CONDITION OF NON-INFRINGEMENT. IBM SHALL NOT BE LIABLE FOR 
# ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL OR CONSEQUENTIAL DAMAGES ARISING 
# OUT OF THE USE OR OPERATION OF THE SAMPLE SOURCE CODE. IBM HAS NO 
# OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS OR 
# MODIFICATIONS TO THE SAMPLE SOURCE CODE.
#
# (C) Copyright IBM Corp. 2005, 2011.
# All Rights Reserved. Licensed Materials - Property of IBM.

# CONSOLE_ENCODING controls the output encoding used for stdout/stderr
#    console - encoding is correct for a console window
#    file    - encoding is the default file encoding for the system
#    <other> - the specified encoding is used.  e.g. Cp1252, Cp850, SJIS
# SET CONSOLE_ENCODING=-Dws.output.encoding=console

. "setupCmdLine.sh"

BASE_DIR_ARG=-Dbasedir="$PWD/.."
WAS_HOME_ARG=-Dwas_home="$WAS_HOME"

ANT_ARGS="$BASE_DIR_ARG $WAS_HOME_ARG"

while [ "$1" -ne "" ]
do
ANT_ARGS="$ANT_ARGS $1"
shift
done

all_plugins=`ls "$WAS_HOME"/plugins`
for plugin in "$all_plugins"
do
ANTCLASSPATH="$ANTCLASSPATH":"$WAS_HOME"/plugins/"$plugin"
done

# Additional jars needed by WSBASamples
ANTCLASSPATH=$ANTCLASSPATH:"$WAS_HOME/lib/j2ee.jar"

# Additional classpath elements needed by WSBASamples.

ANTCLASSPATH="$ANTCLASSPATH:../bld/WSBASample/WSBASampleEJB"
ANTCLASSPATH="$ANTCLASSPATH:../bld/WSBASample/WSBASampleWeb"

# Ant requires classpath elements to exist before beginning...
mkdir "../bld/WSBASample/WSBASampleEJB"
mkdir "../bld/WSBASample/WSBASampleWeb"

WAS_ENDORSED_DIRS="$WAS_HOME/endorsed_apis:$WAS_ENDORSED_DIRS"

export ANT_ARGS ANTCLASSPATH WAS_ENDORSED_DIRS

"$JAVA_HOME/bin/java" -Djava.endorsed.dirs="$WAS_ENDORSED_DIRS" $CONSOLE_ENCODING -Dwas.install.root="$WAS_HOME" -classpath $ANTCLASSPATH com.ibm.ws.bootstrap.WSLauncher org.apache.tools.ant.Main $ANT_ARGS
