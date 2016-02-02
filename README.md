# spring-mvc-aws



## References
* http://docs.spring.io/docs/Spring-MVC-step-by-step/


##### Create a new vm
https://aws.amazon.com/ec2/

##### Step 1: Choose an Amazon Machine Image (AMI)
	Amazon Linux AMI 2015.09.1 (HVM), SSD Volume Type - ami-f0091d91

##### Step 2: Choose an Instance Type
	
| Family        |  Type  |vCPUs|Memory|Instance Storage|EBS-Optimized|Network Performance|
|---------------|--------|-----|------|----------------|-------------|-------------------|
|General purpose|t2.micro|1    |1     |EBS only        |-            |Low to Moderate    |

##### Step 3: Configure Instance Details
	Protect against accidental termination

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
####### Allow ICMP, SSH, and TCP traffic from your IP address
    Type	    Protocol	Port Range	Source
    All TCP	    TCP	        0 - 65535	your ip address/32
    SSH	        TCP	        22	        your ip address/32
    All ICMP	All	        N/A         your ip address/32

##### Download the key pair and change the mode to 400
    chmod 400 pemfile.pem

##### Ping the IP address to confirm that ICMP traffic is allowed from your IP address
    ping [ec2.ipa.ddr.ess]

##### Update the DNS record at the domain registrar to point the domain name to the new IP address
    ; A Records
    @ 600 IN A [000.000.000.000] <-- The EC2 IP address
    mail 600 IN A [000.000.000.000] <-- The EC2 IP address
    
    ; CNAME Records
    ftp 600 IN CNAME @
    www 600 IN CNAME @
    
    ; MX Records
    @ 600 IN MX 1 [mail.somedomain.whatever]

##### Connect via SSH
    ssh -i pemfile.pem ec2-user@[ec2.ipa.ddr.ess]

##### Switch to root 
    sudo su

##### Check the Linux distro version
    cat /proc/version

##### Edit the sysconfig network file
    nano /etc/sysconfig/network
    
##### Change the hostname and save the file
    HOSTNAME=[subddomain?].[somedomain.whatever]

##### Change the command prompt to show the full hostname
    export PS1='[\u@\H \W]\$'

##### Reboot
    reboot

##### Ping the domain name to confirm the DNS update.
    ping [somedomain.whatever]

##### Reconnect
    ssh -i [pem file] ec2-user@[somedomain.whatever]

##### Check the hostname of the ec2 instance
    hostname

##### Check the DNS domain name of the ec2 instance
    dnsdomainname

##### Switch to root
    sudo su

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
        <javac destdir="${build.dir}" source="1.5" target="1.5" debug="true"
               deprecation="false" optimize="false" failonerror="true">
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
	nano ~/build.properties
```
# Ant properties for building the springapp

# appserver.home=${user.home}/apache-tomcat-6.0.14
appserver.home=/usr/share/tomcat6
# for Tomcat 5 use $appserver.home}/server/lib
# for Tomcat 6 use $appserver.home}/lib
appserver.lib=${appserver.home}/lib

deploy.path=${appserver.home}/webapps

tomcat.manager.url=http://localhost:8080/manager
tomcat.manager.username=tomcat
tomcat.manager.password=s3cret
```
##### Create Tomcat user named 'tomcat' with 's3cret' as their password
	sudo nano /usr/share/tomcat6/conf/tomcat-users.xml
```xml
<?xml version='1.0' encoding='utf-8'?>
<tomcat-users>
  <role rolename="manager"/>
  <user username="tomcat" password="s3cret" roles="manager"/>
</tomcat-users>
```

##### Set permissions on your home folder
	ls -l $HOME
	chmod -R 775 $HOME
	ls -l $HOME
  
##### Commit changes and push to the remote repository
	git status
	git add --all
	git commit -m "add config files"
	git config --global push.default simple

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

##### Start the Tomcat service
	sudo service tomcat6 status
	
##### See what is listening on port 8080
	sudo lsof -ni:8080

##### Check the external IP address
	wget http://ipinfo.io/ip -qO -

##### Open a web browser on your local machine and go to http://[ec2 ip address]:8080
	If you're seeing this page via a web browser, it means you've setup Tomcat successfully. Congratulations!

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
##### Tell Linux to use the Java interpreter in the JDK 1.7
	sudo /usr/sbin/alternatives --config java

##### List the intstalled Tomcat applications
	ant list

##### Install the springapp
	sudo ant deploy

##### Test the web page
	lynx localhost:8080/springapp
