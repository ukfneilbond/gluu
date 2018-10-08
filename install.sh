# PRE_REQUISITES!!!!!
# 1) oxauth-ORIGINAL.war must be in the ubuntu home directory on the server
# 1) identity-ORIGINAL.war must be in the ubuntu home directory on the server
# 2) Customisation archive must be in the ubuntu home directory on the server

echo ""
echo "Installing customisations..."
echo "-------------------------------------"
cd ~/

echo ""
echo "Stopping Gluu services..."
echo "-------------------------"
sudo service gluu-server-3.0.0 stop

echo ""
echo "Archiving OXATUH logs..."
echo "------------------------"
# This archives all the current oxauth application logs so that Gluu can start up with clean logs for ease of debugging
sudo rm -vr /opt/gluu-server-3.0.0/opt/gluu/jetty/oxauth/logs/archive/
sudo mkdir /opt/gluu-server-3.0.0/opt/gluu/jetty/oxauth/logs/archive/
sudo cp -v /opt/gluu-server-3.0.0/opt/gluu/jetty/oxauth/logs/* /opt/gluu-server-3.0.0/opt/gluu/jetty/oxauth/logs/archive/ 
sudo rm -v /opt/gluu-server-3.0.0/opt/gluu/jetty/oxauth/logs/*

echo ""
echo "Archiving IDENTITY>OXTRUST logs..."
echo "----------------------------------"
# This archives all the current identity application logs so that Gluu can start up with clean logs for ease of debugging
sudo rm -vr /opt/gluu-server-3.0.0/opt/gluu/jetty/identity/logs/archive/
sudo mkdir /opt/gluu-server-3.0.0/opt/gluu/jetty/identity/logs/archive/
sudo cp -v /opt/gluu-server-3.0.0/opt/gluu/jetty/identity/logs/* /opt/gluu-server-3.0.0/opt/gluu/jetty/identity/logs/archive/ 
sudo rm -v /opt/gluu-server-3.0.0/opt/gluu/jetty/identity/logs/*

echo ""
echo "Extracting customisation archive..."
echo "-----------------------------------"
# Unzips the deployable archive into a working directory in preparation for the installation.
rm -r ~/inst/
mkdir ~/inst/
unzip secureia-gluu-*-deployable.zip -d ~/inst

echo ""
echo "Installing custom libs to OXAUTH (Re-packing war)..."
echo "----------------------------------------------------"
# Unpacks the ORIGINAL oxauth war file into a new temp directory. 
# Copies the necessary custom libs onto the /WEB-INF/lib directory. 
# Repacks the war file and copies to the required location in the Gluu installation.
mkdir ~/inst/tmp/
cd ~/inst/tmp/
/opt/gluu-server-3.0.0/opt/jdk1.8.0_112/bin/jar xvf ~/oxauth-ORIGINAL.war  
cp ~/inst/secureia-gluu-*/oxauth/lib/* WEB-INF/lib/
cp ~/inst/secureia-gluu-*/common/lib/* WEB-INF/lib/
/opt/gluu-server-3.0.0/opt/jdk1.8.0_112/bin/jar cvf oxauth.war *
sudo cp -v oxauth.war /opt/gluu-server-3.0.0/opt/gluu/jetty/oxauth/webapps/

echo ""
echo "Installing custom libs to IDENTITY/OXTRUST (Re-packing war)..."
echo "--------------------------------------------------------------"
# Unpacks the ORIGINAL identity war file into a new temp directory. 
# Copies the necessary custom libs onto the /WEB-INF/lib directory. 
# Repacks the war file and copies to the required location in the Gluu installation.
mkdir ~/inst/tmp2/
cd ~/inst/tmp2/
/opt/gluu-server-3.0.0/opt/jdk1.8.0_112/bin/jar xvf ~/identity-ORIGINAL.war  
cp ~/inst/secureia-gluu-*/identity/lib/* WEB-INF/lib/
cp ~/inst/secureia-gluu-*/common/lib/* WEB-INF/lib/
/opt/gluu-server-3.0.0/opt/jdk1.8.0_112/bin/jar cvf identity.war *
sudo cp -v identity.war /opt/gluu-server-3.0.0/opt/gluu/jetty/identity/webapps/

echo ""
echo "Installing custom pages and static content..."
echo "---------------------------------------------"
# Removes and then recopies the static content to oxauth application.
cd ~/inst/secureia-gluu-*
sudo rm -vr /opt/gluu-server-3.0.0/opt/gluu/jetty/oxauth/custom/pages/
sudo cp -vr oxauth/custom/pages /opt/gluu-server-3.0.0/opt/gluu/jetty/oxauth/custom/
sudo rm -vr /opt/gluu-server-3.0.0/opt/gluu/jetty/oxauth/custom/static/
sudo cp -vr oxauth/custom/static /opt/gluu-server-3.0.0/opt/gluu/jetty/oxauth/custom/

echo ""
echo "Updating custom LDAP schema..."
echo "-------------------------------"
# Updates the custom person schema in OpenLDAP
cd ~/inst/secureia-gluu-*
sudo cp -vr openldap/custom.schema /opt/gluu-server-3.0.0/opt/gluu/schema/openldap/

echo ""
echo "Starting Gluu services..."
echo "-------------------------"
cd ~/
sudo service gluu-server-3.0.0 start

echo "Done :)"
