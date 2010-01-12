#!/bin/bash
#
# OpenGeo Suite installation script for linux.
#

# function to help parse input
function parse() {
  in="$1"
  if [ "$in" == "" ] && [ "$2" != ""] ; then
    in=$2
  fi

  if [ "$in" != "" ]; then
    in=`echo $in | tr -s " "`
    in=`echo $in | tr [:upper:] [:lower:]`
    if [ "$in" == "y" ] || [ "$in" == "yes" ]; then 
      in="Yes"
    fi

    if [ "$in" == "n" ] || [ "$in" == "no" ]; then 
      in="No"
    fi
  fi 
  echo $in
}

# get version
VERSION=1.0-SNAPSHOT
#VERSION=`ls opengeosuite-*.tar.gz`
#len=`expr length VERSION - 20`
#VERSION=${VERSION:13:$len}

# default installation directory
INSTALL_DIR_DEF=""
if [ "`whoami`" == "root" ]; then 
  INSTALL_DIR_DEF="/opt"
else
  INSTALL_DIR_DEF="/home/`whoami`"
fi

#
# welcome
#
echo "Thank you for choosing the OpenGeo Suite."
echo ""
echo -n "Would you like to proceed with installing OpenGeo Suite $VERSION? [Y|n]: "
read PROCEED
PROCEED=`parse $PROCEED y`
if [ "$PROCEED" == "No" ]; then
  exit
fi

#
# license agreement
#
cat license.txt | less
echo -n "Do you accept the license agreement? [Y|n]: "
read ACCEPT_LICENSE
ACCEPT_LICENSE=`parse $ACCEPT_LICENSE y`
if [ "$ACCEPT_LICENSE" == "No" ]; then
  exit
fi

#
# installation directory
#
echo -n "Choose an installation directory [$INSTALL_DIR_DEF]: "
read INSTALL_DIR
INSTALL_DIR=`parse $INSTALL_DIR $INSTALL_DIR_DEF`

if [ -f "$INSTALL_DIR" ]; then
  echo "'$INSTALL_DIR' exists and is not a directory."
  exit
fi

if [ ! -e "$INSTALL_DIR" ]; then
  echo -n "The directory '$INSTALL_DIR' does not exist. Would you like to create it now? [Y|n]: "
  read MK_INSTALL_DIR
  MK_INSTALL_DIR=`parse $MK_INSTALL_DIR y`
  if [ "$MK_INSTALL_DIR" == "No" ]; then
    exit
  fi

  mkdir $INSTALL_DIR
  if [ "$?" != "0" ]; then
     echo "Could not create directory '$INSTALL_DIR'"  
     exit 1
  fi
fi

if [ ! -w $INSTALL_DIR ]; then 
  echo "Unable to write to '$INSTALL_DIR'"
  exit 1
fi

#
# include docs?
#
#echo -n "Would you like to install the OpenGeo Suite documentation? [Y|n]: "
#read INCLUDE_DOCS
#INCLUDE_DOCS=`parse $INCLUDE_DOCS y`
INCLUDE_DOCS="No"

#
# include ArcSDE support?
#
echo -n "Would you like to include additional support for ArcSDE? [y|N]: "
read INCLUDE_SDE
INCLUDE_SDE=`parse $INCLUDE_SDE n`

#
# include Oracle support?
#
echo -n "Would you like to include additional suppport for Oracle? [y|N]: "
read INCLUDE_ORACLE
INCLUDE_ORACLE=`parse $INCLUDE_ORACLE n` 

#
# create symlinks?
#
echo -n "Would you like to create links to executable files? [Y|n]: "
read CREATE_SYMLINKS
CREATE_SYMLINKS=`parse $CREATE_SYMLINKS y`

if [ "$CREATE_SYMLINKS" == "Yes" ]; then
  echo -n "What directory should links be created in? [/usr/local/bin]: "
  read SYMLINK_DIR 
  SYMLINK_DIR=`parse $SYMLINK_DIR /usr/local/bin`

  if [ ! -e "$SYMLINK_DIR" ]; then
    echo -n "$SYMLINK_DIR does not exist. Would you like to create it now? [Y|n]: "
    read MK_SYMLINK_DIR
    MK_SYMLINK_DIR=`parse $MK_SYMLINK_DIR y`
    
    if [ "$MK_SYMLINK_DIR" == "Yes" ]; then
      mkdir $SYMLINK_DIR
      if [ "$?" != "0" ]; then
         echo "Could not create directory '$SYMLINK_DIR'"  
         exit 1
      fi
    fi
  fi

  if [ ! -w $SYMLINK_DIR ]; then 
    echo "Unable to write to '$SYMLINK_DIR'"
    exit 1
  fi
  
fi

echo "Installation summary: "
echo 
echo -e "\t Installation directory: \t $INSTALL_DIR/opengeosuite-$VERSION"
echo -e "\t Install documentation: \t $INCLUDE_DOCS"
echo -e "\t Install ArcSDE support: \t $INCLUDE_SDE"
echo -e "\t Install Oracle support: \t $INCLUDE_ORACLE"

if [ "$CREATE_SYMLINKS" == "Yes" ]; then
  echo -e "\t Create symlinks: \t $SYMLINK_DIR"
else
  echo -e "\t Create symlinks: \t No"
fi

echo
echo -n "Proceed with installation? [Y|n]: "
read PROCEED
PROCEED=`parse $PROCEED y` 
if [ "$PROCEED" == "No" ]; then
  exit
fi

SUITE_DIR=$INSTALL_DIR/opengeosuite-$VERSION
echo "Installing OpenGeo Suite..." &&
tar xzf opengeosuite-$VERSION-bin.tar.gz -C "$INSTALL_DIR" &&

echo "Installing OpenGeo Dashboard..." &&
tar xzf opengeodashboard-$VERSION.tgz -C "$SUITE_DIR" &&

echo "Creating symlinks..." &&
ln -sf "`find "$SUITE_DIR" -type f -name "OpenGeo Dashboard"`" "$SUITE_DIR/opengeo-dashboard" &&
sed -i "s#@SUITE_DIR@#$SUITE_DIR#g" "`find "$SUITE_DIR" -type f -name config.ini`" &&
sed -i "s#@SUITE_EXE@#$SUITE_DIR/opengeo-suite#g" "`find "$SUITE_DIR" -type f -name config.ini`" &&

if [ "$?" != "0" ]; then
  exit 1
fi

if [ "$CREATE_SYMLINKS" == "Yes" ]; then
  echo "#!/bin/bash" > "$SYMLINK_DIR/opengeo-suite"
  echo "pushd $SUITE_DIR > /dev/null" >> "$SYMLINK_DIR/opengeo-suite"
  echo "./opengeo-suite \$1" >> "$SYMLINK_DIR/opengeo-suite"
  echo "popd > /dev/null" >> "$SYMLINK_DIR/opengeo-suite"
  chmod +x "$SYMLINK_DIR/opengeo-suite"

  echo "#!/bin/bash" > "$SYMLINK_DIR/opengeo-dashboard"
  echo "pushd $SUITE_DIR >> /dev/null" > "$SYMLINK_DIR/opengeo-dashboard"
  echo "./opengeo-dashboard" >> "$SYMLINK_DIR/opengeo-dashboard"
  echo "popd >> /dev/null" >> "$SYMLINK_DIR/opengeo-dashboard"
  chmod +x "$SYMLINK_DIR/opengeo-dashboard"
fi

#if [ "$INCLUDE_DOCS" == "Yes" ]; then
#  
#fi

if [ "$INCLUDE_SDE" == "Yes" ]; then
  tar xzvf opengeosuite-$VERSION-ext.tar.gz -C "$SUITE_DIR/webapps/geoserver/WEB-INF/lib" "ext/arcsde"

  echo -n "The ArcSDE extension has been selected which requires additional libraries to function. Where are the libraries located on your system? [Leave blank to skip]: "

fi

if [ "$INCLUDE_ORACLE" == "Yes" ]; then
  tar xzvf opengeosuite-$VERSION-doc.tar.gz -C "$SUITE_DIR/webapps/geoserver/WEB-INF/lib" "ext/oracle"

  echo -n "The Oracle extension has been selected which requires the Oracle JDBC driver to function. Where is ojdbc jar located on your system? [Leave blank to skip]: "

fi