# spring-mvc-aws



## References
* http://docs.spring.io/docs/Spring-MVC-step-by-step/
* http://tomcat.apache.org/whichversion.html


##### Create a new vm
https://aws.amazon.com/ec2/

##### Step 1: Choose an Amazon Machine Image (AMI)
	Amazon Linux AMI 2015.09.1 (HVM), SSD Volume Type - ami-f0091d91

##### Step 2: Choose an Instance Type
	Family: General purpose
	Type: t2.micro
	vCPUs: 1
	Memory: 1
	Instance Storage: EBS only
	EBS-Optimized: -
	Network Performance: Low to Moderate

##### Step 3: Configure Instance Details
	Number of instances: 	1
	Network: (default)
	Subnet: No preference (default subnet in any Availability Zone)
	EBS-optimized: No
	Monitoring: No
	Termination protection: YES <---- UPDATE THIS
	Shutdown behavior: Stop
	IAM role: None
	Tenancy: default
	Host ID: 
	Affinity: Off
	Kernel ID: Use default
	RAM disk ID: Use default
	User data: 
	Assign Public IP: Use subnet setting (Enable)
	Network interfaces: 
	Purchasing option: On demand

##### Step 4: Add Storage
	Volume Type: Root
	Device: /dev/xvda
	Snapshot: snap-ad8e61f8
	Size (GiB): 8
	Volume Type: General Purpose SSD (GP2)
	IOPS: 24 / 3000
	Delete on Termination: Yes
	Encrypted: Not Encrypted

##### Step 5: Tag Instance
	Key: Name
	Value:

##### Step 6: Configure Security Group 
*Allow ICMP, SSH, and TCP traffic from your IP address*

    Type	    Protocol	Port Range	Source
    All TCP	    TCP	        0 - 65535	your ip address/32
    SSH	        TCP	        22	        your ip address/32
    All ICMP	All	        N/A         your ip address/32

##### Download the key pair and change the mode to 400
    chmod 400 pemfile.pem

##### Ping the IP address to confirm that ICMP traffic is allowed from your IP address
    ping [ec2.ipa.ddr.ess]

##### Connect via SSH
    ssh -i pemfile.pem ec2-user@[ec2.ipa.ddr.ess]

##### Switch to root 
    sudo su

##### Ask who am I?
	whoami
	
##### Update yum
    yum update

##### Create a user account for development
    useradd [your new account name]
    
##### Set the password for your development account
    passwd [your new account name]

##### Edit sudoers file
    vim /etc/sudoers

##### Add development account to sudoers file
    ## ALLOW {MYACCOUNT} TO SUDO
    [your new account name] ALL=(ALL) ALL

##### Switch to development user
    su [your new account name]
    
##### Ask who am I?
    whoami
    
##### Check the value of the home directory environment variable
    echo $HOME

##### Go home
    cd $HOME

##### Check the Linux distro version
    cat /proc/version

##### Check the hostname of the ec2 instance
    hostname

##### Check the DNS domain name of the ec2 instance
    dnsdomainname

##### Check the internal IP address
    ifconfig

##### Check the external IP address
    wget http://ipinfo.io/ip -qO -
	
##### Install git
    sudo yum install git
    
##### Make a project directory
    mkdir -p ~/git/projectdirecory

##### Initialize the git repository
    cd ~/git/projectdirectory
    git init

##### Make a remote repository
    http://github.com
    
##### Add the remote repository
    git add remote origin https://github.com/[username]/[reponame].git

##### Configure git
    git config user.name [username]
    git config user.email [your email address]
    
##### Fetch from the remote repository
    git fetch

##### Pull from the remote directory
    git pull origin master

##### Make a .gitignore file
    nano .gitignore
```
*.class

# Mobile Tools for Java (J2ME)
.mtj.tmp/

# Package Files #
*.jar
*.war
*.ear

# virtual machine crash logs, see http://www.java.com/en/download/help/error_hotspot.xml
hs_err_pid*

# AWS key
*.pem

# Tomcat build.properties file
build.properties

# Gradle folders
build/
.gradle/
```
##### Commit changes and push to the remote repository
    git status
    git add --all
    git commit -m "git ignore"
    git push --set-upstream origin master

##### Make a directory for source files
	mkdir src  

##### Make a directory for the web.xml file
	mkdir -p war/WEB-INF

##### Make an index file
	vim war/index.jsp
```html
<html>
  <head><title>Example :: Spring Application</title></head>
  <body>
    <h1>Example - Spring Application</h1>
    <p>This is my test.</p>
  </body>
</html>
```
##### Make the web.xml file
	vim war/WEB-INF/web.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="2.4"
         xmlns="http://java.sun.com/xml/ns/j2ee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee 
         http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd" >
  <welcome-file-list>
    <welcome-file>
      index.jsp
    </welcome-file>
  </welcome-file-list>
</web-app>
```
##### Create a build.xml file. Change the springapp value to whatever you would like. 
	vim build.xml
```xml
<?xml version="1.0"?>

<project name="springapp" basedir="." default="usage">
    <property file="${user.home}/build.properties"/>

    <property name="src.dir" value="src"/>
    <property name="web.dir" value="war"/>
    <property name="build.dir" value="${web.dir}/WEB-INF/classes"/>
    <property name="name" value="springapp"/>

    <path id="master-classpath">
        <fileset dir="${web.dir}/WEB-INF/lib">
            <include name="*.jar"/>
        </fileset>
        <!-- We need the servlet API classes: -->
        <!--  * for Tomcat 5/6 use servlet-api.jar -->
        <!--  * for other app servers - check the docs -->
        <fileset dir="${appserver.lib}">
            <include name="servlet*.jar"/>
        </fileset>
        <pathelement path="${build.dir}"/>
    </path>

    <target name="usage">
        <echo message=""/>
        <echo message="${name} build file"/>
        <echo message="-----------------------------------"/>
        <echo message=""/>
        <echo message="Available targets are:"/>
        <echo message=""/>
        <echo message="build     --> Build the application"/>
        <echo message="deploy    --> Deploy application as directory"/>
        <echo message="deploywar --> Deploy application as a WAR file"/>
        <echo message="install   --> Install application in Tomcat"/>
        <echo message="reload    --> Reload application in Tomcat"/>
        <echo message="start     --> Start Tomcat application"/>
        <echo message="stop      --> Stop Tomcat application"/>
        <echo message="list      --> List Tomcat applications"/>
        <echo message=""/>
    </target>

    <target name="build" description="Compile main source tree java files">
        <mkdir dir="${build.dir}"/>
        <!-- SET THE SOURCE AND TARGET CORRECTLY -->
        <!-- ADD THE includeantruntime PROPERTY -->
        <javac destdir="${build.dir}" 
        	source="1.7" 
        	target="1.7" 
        	debug="true"
        	deprecation="false" 
        	optimize="false" 
        	failonerror="true"
        	includeantruntime="false"
        	>
            <src path="${src.dir}"/>
            <classpath refid="master-classpath"/>
        </javac>
    </target>

    <target name="deploy" depends="build" description="Deploy application">
        <copy todir="${deploy.path}/${name}" preservelastmodified="true">
            <fileset dir="${web.dir}">
                <include name="**/*.*"/>
            </fileset>
        </copy>
    </target>

    <target name="deploywar" depends="build" description="Deploy application as a WAR file">
        <war destfile="${name}.war"
             webxml="${web.dir}/WEB-INF/web.xml">
            <fileset dir="${web.dir}">
                <include name="**/*.*"/>
            </fileset>
        </war>
        <copy todir="${deploy.path}" preservelastmodified="true">
            <fileset dir=".">
                <include name="*.war"/>
            </fileset>
        </copy>
    </target>
    
<!-- ============================================================== -->
<!-- Tomcat tasks - remove these if you don't have Tomcat installed -->
<!-- ============================================================== -->

    <path id="catalina-ant-classpath">
        <!-- We need the Catalina jars for Tomcat -->
        <!--  * for other app servers - check the docs -->
        <fileset dir="${appserver.lib}">
            <include name="catalina-ant.jar"/>
        </fileset>
    </path>

    <taskdef name="install" classname="org.apache.catalina.ant.InstallTask">
        <classpath refid="catalina-ant-classpath"/>
    </taskdef>
    <taskdef name="reload" classname="org.apache.catalina.ant.ReloadTask">
        <classpath refid="catalina-ant-classpath"/>
    </taskdef>
    <taskdef name="list" classname="org.apache.catalina.ant.ListTask">
        <classpath refid="catalina-ant-classpath"/>
    </taskdef>
    <taskdef name="start" classname="org.apache.catalina.ant.StartTask">
        <classpath refid="catalina-ant-classpath"/>
    </taskdef>
    <taskdef name="stop" classname="org.apache.catalina.ant.StopTask">
        <classpath refid="catalina-ant-classpath"/>
    </taskdef>

    <target name="install" description="Install application in Tomcat">
        <install url="${tomcat.manager.url}"
                 username="${tomcat.manager.username}"
                 password="${tomcat.manager.password}"
                 path="/${name}"
                 war="${name}"/>
    </target>

    <target name="reload" description="Reload application in Tomcat">
        <reload url="${tomcat.manager.url}"
                 username="${tomcat.manager.username}"
                 password="${tomcat.manager.password}"
                 path="/${name}"/>
    </target>

    <target name="start" description="Start Tomcat application">
        <start url="${tomcat.manager.url}"
                 username="${tomcat.manager.username}"
                 password="${tomcat.manager.password}"
                 path="/${name}"/>
    </target>

    <target name="stop" description="Stop Tomcat application">
        <stop url="${tomcat.manager.url}"
                 username="${tomcat.manager.username}"
                 password="${tomcat.manager.password}"
                 path="/${name}"/>
    </target>

    <target name="list" description="List Tomcat applications">
        <list url="${tomcat.manager.url}"
                 username="${tomcat.manager.username}"
                 password="${tomcat.manager.password}"/>
    </target>

<!-- End Tomcat tasks -->

</project>
```
##### Make a build properties file in your home directory
	vim ~/build.properties
```
# Ant properties for building the springapp

appserver.home=/usr/share/tomcat6
appserver.lib=${appserver.home}/lib
deploy.path=${appserver.home}/webapps

tomcat.manager.url=http://localhost:8080/manager
tomcat.manager.username=tomcat
tomcat.manager.password=s3cret
```
##### Install Tomcat 6
	sudo yum install tomcat6 tomcat6-webapps tomcat6-admin-webapps

##### Ask where is Tomcat?
	whereis tomcat6

##### Get a listing of the Catalina Base directory
	ls -l /usr/share/tomcat6/
```
total 4
drwxr-xr-x 2 root root   4096 Feb  1 22:01 bin
lrwxrwxrwx 1 root tomcat   12 Feb  1 22:01 conf -> /etc/tomcat6
lrwxrwxrwx 1 root root     23 Feb  1 22:01 lib -> /usr/share/java/tomcat6
lrwxrwxrwx 1 root root     16 Feb  1 22:01 logs -> /var/log/tomcat6
lrwxrwxrwx 1 root root     23 Feb  1 22:01 temp -> /var/cache/tomcat6/temp
lrwxrwxrwx 1 root root     24 Feb  1 22:01 webapps -> /var/lib/tomcat6/webapps
lrwxrwxrwx 1 root root     23 Feb  1 22:01 work -> /var/cache/tomcat6/work

```
##### See what is listening on port 8080
	sudo lsof -ni:8080

##### Create Tomcat user named 'tomcat' with 's3cret' as their password
	sudo vim /usr/share/tomcat6/conf/tomcat-users.xml
```xml
<?xml version='1.0' encoding='utf-8'?>
<tomcat-users>
  <role rolename="manager"/>
  <user username="tomcat" password="s3cret" roles="manager"/>
</tomcat-users>
```
##### Start the Tomcat service
	sudo service tomcat6 status
	sudo service tomcat6 start
	sudo service tomcat6 status
	
##### See what is listening on port 8080
	sudo lsof -ni:8080

##### Check the external IP address
	wget http://ipinfo.io/ip -qO -

##### Open a web browser on your local machine and go to http://[ec2 ip address]:8080
	If you're seeing this page via a web browser, it means you've setup Tomcat successfully. Congratulations!
  
##### Commit changes and push to the remote repository
	git status
	git add --all
	git commit -m "add config files"
	git config --global push.default simple
	git push --set-upstream origin master

##### Install Ant
	sudo yum install ant

##### Check the Ant version
	ant -version

##### Run ant
	ant
```
usage:
     [echo] 
     [echo] springapp build file
     [echo] -----------------------------------
     [echo] 
     [echo] Available targets are:
     [echo] 
     [echo] build     --> Build the application
     [echo] deploy    --> Deploy application as directory
     [echo] deploywar --> Deploy application as a WAR file
     [echo] install   --> Install application in Tomcat
     [echo] reload    --> Reload application in Tomcat
     [echo] start     --> Start Tomcat application
     [echo] stop      --> Stop Tomcat application
     [echo] list      --> List Tomcat applications
     [echo] 

BUILD SUCCESSFUL
Total time: 0 seconds
```
##### Show all of the environment variables
	declare

##### Show the current JAVA_HOME
	env | grep JAVA_HOME

##### Ask where is Java?
	whereis java

##### Check the Java version
	java -version

##### See if the Java compiler is installed
	whereis javac
	javac -version

##### Search yum for openjdk
	yum search openjdk
	
##### Install the Open JDK version 1.7
	sudo yum install java-1.7.0-openjdk-devel.x86_64

##### Check the Java compiler version
	javac -version
	
##### Set the JAVA_HOME environment variable to the Open JDK directory
	export JAVA_HOME='/usr/lib/jvm/java-1.7.0-openjdk-1.7.0.91.x86_64/'

##### Tell Linux to use the Java interpreter in the JDK 1.7
	sudo /usr/sbin/alternatives --config java

##### List the intstalled Tomcat applications
	ant list

##### Set permissions on your home folder
	ls -l $HOME
	chmod -R 775 $HOME
	ls -l $HOME
	
##### Install the springapp
	sudo ant deploy

##### Test the web page
	lynx localhost:8080/springapp
