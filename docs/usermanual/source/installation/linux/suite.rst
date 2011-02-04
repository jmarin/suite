.. _installation.linux.suite:

Installing the OpenGeo Suite for Linux
======================================

This document describes how to install the OpenGeo Suite for Linux.

Prerequisites
-------------

The OpenGeo Suite has the following system requirements:

* Operating System: Ubuntu 10.04 and 10.10, CentOS 5
* Memory: 512MB minimum (1GB recommended)
* Disk space: 500MB minimum (plus extra space for any loaded data)
* Browser: Any modern web browser is supported (Internet Explorer 6+, Firefox 3+, Chrome 2+, Safari 3+)
* Permissions: Super user privileges are required for installation

Installation
------------

Packages for the OpenGeo Suite are currently available in both :ref:`RPM <rpm>` and :ref:`APT <apt>` (Debian) format. 

.. note:: The commands contained in the following installation instructions must be run as a user with root privileges, or prefixed with ``sudo``. 

.. _RPM:

RPM Installation
~~~~~~~~~~~~~~~~

.. warning:: The RPM packages are only available for CentOS 5 and above.

#. Begin by adding the OpenGeo Yum repository.

   For 32 bit systems:

   .. code-block:: bash

      cd /etc/yum.repos.d
      wget http://yum.opengeo.org/centos/5/i386/OpenGeo.repo

   For 64 bit systems:

   .. code-block:: bash

      cd /etc/yum.repos.d
      wget http://yum.opengeo.org/centos/5/x86_64/OpenGeo.repo

#. Search for packages:

   .. code-block:: bash

      yum search opengeo

   .. note:: If the search command does not return any results, the repository was not added properly. Examine the output of the ``yum`` command for any errors or warnings.

#. Install the OpenGeo Suite package (opengeo-suite):

   .. code-block:: bash

      yum install opengeo-suite

#. You can launch the OpenGeo Suite Dashboard (and verify the installation was successful) by navigating to the following URL::

      http://localhost:8080/dashboard/

Jump to the :ref:`installation.linux.suite.afterinstall` section.
 
.. _APT:

APT Installation
~~~~~~~~~~~~~~~~

.. warning:: The APT packages are only available for Ubuntu 10.04 and above.

#. Begin by importing the OpenGeo GPG key:

   .. code-block:: bash

      wget -qO- http://apt.opengeo.org/gpg.key | apt-key add -

#. Add the OpenGeo APT repository:

   .. code-block:: bash

      echo "deb http://apt.opengeo.org/ubuntu lucid main" >> /etc/apt/sources.list
      
#. Update APT:

   .. code-block:: bash

      apt-get update

#. Search for packages:

   .. code-block:: bash

      apt-cache search opengeo

   .. note:: If the search command does not return any results, the repository was not added properly. Examine the output of the ``apt`` commands for any errors or warnings.

#. Install the OpenGeo Suite package (opengeo-suite):

   .. code-block:: bash

      apt-get install opengeo-suite

#. You can launch the OpenGeo Suite Dashboard (and verify the installation was successful) by navigating to the following URL::

      http://localhost:8080/dashboard/

Jump to the :ref:`installation.linux.suite.afterinstall` section.

.. _installation.linux.suite.afterinstall:

After installation
------------------

Packages
~~~~~~~~

Once installed, you will have the following packages installed on your system:

.. list-table::
   :widths: 20 20 60 
   :header-rows: 1

   * - Package
     - Name
     - Description
   * - opengeo-suite
     - OpenGeo Suite
     - The full OpenGeo Suite and all its contents.  All packages listed below are installed as dependencies with this package.  Contains GeoExplorer, Styler, GeoEditor, Dashboard, Recipe Book, and more.
   * - opengeo-docs
     - OpenGeo Suite Documentation
     - Full documentation for the OpenGeo Suite.
   * - opengeo-geoserver
     - GeoServer
     - High performance, standards-compliant map and geospatial data server.
   * - opengeo-jai
     - Java Advanced Imaging (JAI)
     - Set of Java toolkits to provide enhanced image rendering abilities.
   * - opengeo-postgis
     - PostGIS
     - Robust, spatially-enabled object-relational database built on PostgreSQL.
   * - opengeo-suite-data
     - OpenGeo Suite Data
     - Sample data for use with the OpenGeo Suite
   * - pgadmin3
     - pgAdmin III
     - Graphical client for interacting with PostgreSQL/PostGIS.

Accessing web applications
~~~~~~~~~~~~~~~~~~~~~~~~~~

The easiest way to launch the web-based applications contained in the OpenGeo Sutie is via the Dashboard, located at::

  http://localhost:8080/dashboard/

.. note:: Please change the port number if your Tomcat installation is located on a different port.

All web applications are linked from this application.

.. list-table::
   :widths: 30 70
   :header-rows: 1

   * - Application
     - URL
   * - OpenGeo Suite Dashboard
     - ``http://localhost:8080/dashboard/``
   * - GeoServer
     - ``http://localhost:8080/geoserver/``
   * - OpenGeo Suite Documentation
     - ``http://localhost:8080/docs/``
   * - GeoExplorer
     - ``http://localhost:8080/geoexplorer/``
   * - Styler
     - ``http://localhost:8080/styler/``
   * - GeoEditor
     - ``http://localhost:8080/geoeditor/``
   * - OpenGeo Recipe Book
     - ``http://localhost:8080/recipes/``

Accessing PostGIS
~~~~~~~~~~~~~~~~~

You can access PostGIS in one of two ways:  via the command line :command:`psql`, or via the graphical interface :command:`pgadmin3`.  Both commands should be on the path and can be invoked from any Terminal window.  If unfamiliar with PostGIS, start with :command:`pgadmin3`.

.. note:: This version of PostGIS is running on port 5432.  The administrator account and password is **opengeo** / **opengeo**.

Starting/Stopping the OpenGeo Suite
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

GeoServer, GeoExplorer, the documentation, and all other web-based containers are installed into the currently installed Tomcat instance. Thus starting and stopping the servlets are accomplished by manging them through the standard Tomcat instance.  Tomcat is installed as a standard service, and can be managed accordingly:

.. note:: The commands contained in the following installation instructions must be run as a user with root privileges, or prefixed with ``sudo``. 

.. code-block:: bash

   /etc/init.d/tomcat5 start
   /etc/init.d/tomcat5 stop

PostGIS is installed as a standard service (under the name of :command:`postgresql`) and can be managed accordingly:

.. code-block:: bash

   /etc/init.d/postgresql start
   /etc/init.d/postgresql stop

For More Information
--------------------

Please visit http://opengeo.org/ or see the documentation included with this software.