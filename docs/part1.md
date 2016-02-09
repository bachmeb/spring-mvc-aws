# Spring MVC on AWS
[README](/README.md)
## Part 1

### References
* http://docs.spring.io/docs/Spring-MVC-step-by-step/part1.html
* http://stackoverflow.com/questions/8611777/java-lang-nosuchmethoderror-javax-servlet-servletcontext-getcontextpathljava
* http://wiki.metawerx.net/wiki/JARFilesYouShouldNeverIncludeInYourWebapp
* https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html
* http://docs.spring.io/spring-framework/docs/2.0.8/reference/mvc.html
* http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/set-time.html

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

##### Check the time
	date
	
##### List the available time zones
	ls /usr/share/zoneinfo/

##### Update the /etc/sysconfig/clock file with your time zone
	sudo vim /etc/sysconfig/clock
```
ZONE="America/New_York"
UTC=false
```

##### Create a symbolic link between /etc/localtime and your time zone file
	sudo ln -sf /usr/share/zoneinfo/America/New_York /etc/localtime

##### Reboot the system to pick up the new time zone information in all services and applications
	sudo reboot

##### Connect via SSH
	ssh -i pemfile.pem ec2-user@[ec2.ipa.ddr.ess]

##### Check the time
	date
	
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
	## LET ME SUDO
	[your new account name] ALL=(ALL) ALL

##### Switch to development user
	su [your new account name]
    
##### Ask who am I?
	whoami
    
##### Check the value of the home directory environment variable
	echo $HOME

##### Go home
	cd ~

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

##### Show all of the environment variables
	declare

##### Look for JAVA in the list of environmental variables
	env | grep JAVA

##### Echo the current JAVA_HOME
	echo $JAVA_HOME

##### Ask where is Java?
	whereis java

##### Check the Java version
	java -version

##### See if the Java compiler is installed
	whereis javac
	javac -version

##### Search yum for openjdk
	yum search openjdk
	
##### Install the Open JDK version 1.6
	sudo yum install java-1.6.0-openjdk-devel

##### Check the Java compiler version
	javac -version
```
javac 1.6.0_37
```
##### Tell Linux to use the Java interpreter in the JDK 1.6
	sudo alternatives --config java

##### Read the symlinks in /usr/lib/jvm/
	ls -l /usr/lib/jvm/

##### Confirm that /usr/lib/jvm/java points to etc/alternatives/java_sdk
	ls -l /usr/lib/jvm/java

##### Confirm that /etc/alternatives/java_sdk points to /usr/lib/jvm/java-1.7.0-openjdk.x86_64
	ls -l /etc/alternatives/java_sdk

##### Confirm that /usr/lib/jvm/java-1.7.0-openjdk.x86_64 points to /usr/lib/jvm/java-1.7.0-openjdk-1.7.0.91.x86_64
	ls -l /usr/lib/jvm/java-1.7.0-openjdk.x86_64

##### Confirm that /usr/lib/jvm/java-1.7.0-openjdk-1.7.0.91.x86_64 is the installation directory for Java SDK 1.7
	ls -l /usr/lib/jvm/java-1.7.0-openjdk-1.7.0.91.x86_64

##### Echo the $JAVA_HOME environment variable
	echo $JAVA_HOME

##### Set the JAVA_HOME environment variable to the Open JDK directory
	export JAVA_HOME='/usr/lib/jvm/java'

##### Echo the $JAVA_HOME environment variable
	echo $JAVA_HOME

##### Install git
	sudo yum install git
    
##### Make a project directory
	pwd
	mkdir -p ~/git/spring-mvc

##### Make a .gitignore file
	nano ~/git/spring-mvc.gitignore
```
# .GITIGNORE FOR SPRING MVC ON AWS

# Java
*.class

# Mobile Tools for Java (J2ME)
.mtj.tmp/

# Package Files #
*.jar
*.war
*.ear

# virtual machine crash logs, see http://www.java.com/en/download/help/error_hotspot.xml
hs_err_pid*

# AWS
*.pem

# Tomcat
build.properties
build/
dist/
```

##### Initialize the git repository
	cd ~/git/spring-mvc
	git init

##### Make a remote repository
	http://github.com
    
##### Add the remote repository
	git remote add origin http://github.com/[username]/[reponame].git

##### Configure git
	git config user.name [username]
	git config user.email [your email address]

##### Pull from the remote directory
	git pull origin master

##### Check the permissions on the files in the project folder and make sure that you can read, write, and execute them
	ls -l ~/git/spring-mvc
	chmod -R 775 ~/git/spring-mvc

##### Commit changes and push to the remote repository
	git status
	git add --all
	git commit -m "git ignore"
	git push --set-upstream origin master
	
##### Search yum for Tomcat
	sudo yum search tomcat
	
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
	sudo service tomcat6 start
	sudo service --status-all | grep tomcat

##### Use the process id to see which files tomcat has open 
	lsof -p [process id]
```
COMMAND   PID   USER   FD   TYPE DEVICE  SIZE/OFF   NODE NAME
java    28171 tomcat  cwd    DIR  202,1      4096  17757 /usr/share/tomcat6
java    28171 tomcat  rtd    DIR  202,1      4096      2 /
java    28171 tomcat  txt    REG  202,1     37856 144207 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/bin/java
java    28171 tomcat  mem    REG  202,1     89768 135342 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/amd64/libnet.so
java    28171 tomcat  mem    REG  202,1     25232 133052 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/amd64/headless/libmawt.so
java    28171 tomcat  mem    REG  202,1    703616 133065 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/amd64/libawt.so
java    28171 tomcat  mem    REG  202,1     27736 135340 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/amd64/libmanagement.so
java    28171 tomcat  mem    REG  202,1 106065056 408306 /usr/lib/locale/locale-archive
java    28171 tomcat  mem    REG  202,1   3534509 141580 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/charsets.jar
java    28171 tomcat  mem    REG  202,1    215355 143735 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/ext/sunjce_provider.jar
java    28171 tomcat  mem    REG  202,1    248596 143736 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/ext/sunpkcs11.jar
java    28171 tomcat  mem    REG  202,1     98229 143756 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/jce.jar
java    28171 tomcat  mem    REG  202,1    501379 143733 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/ext/localedata.jar
java    28171 tomcat  mem    REG  202,1    259156 144417 /usr/share/java/mx4j/mx4j-jmx-3.0.1.jar
java    28171 tomcat  mem    REG  202,1    155739 144324 /usr/share/java/apache-commons-dbcp.jar
java    28171 tomcat  mem    REG  202,1     53900 144456 /usr/share/java/tomcat6/tomcat-i18n-ja-6.0.44.jar
java    28171 tomcat  mem    REG  202,1     97002 144296 /usr/share/java/apache-commons-pool-1.5.6.jar
java    28171 tomcat  mem    REG  202,1    799981 144450 /usr/share/java/tomcat6/tomcat-coyote-6.0.44.jar
java    28171 tomcat  mem    REG  202,1    133917 144419 /usr/share/java/mx4j/mx4j-remote-3.0.1.jar
java    28171 tomcat  mem    REG  202,1     76583 144349 /usr/share/java/tomcat6-jsp-2.1-api-6.0.44.jar
java    28171 tomcat  mem    REG  202,1     34604 144378 /usr/share/java/tomcat6-el-2.1-api-6.0.44.jar
java    28171 tomcat  mem    REG  202,1    131802 144337 /usr/share/java/tomcat6-servlet-2.5-api-6.0.44.jar
java    28171 tomcat  mem    REG  202,1    400811 144335 /usr/share/java/log4j-1.2.16.jar
java    28171 tomcat  mem    REG  202,1     51673 144454 /usr/share/java/tomcat6/tomcat-i18n-fr-6.0.44.jar
java    28171 tomcat  mem    REG  202,1     54649 144432 /usr/share/java/tomcat6/catalina-ant-6.0.44.jar
java    28171 tomcat  mem    REG  202,1     70507 144452 /usr/share/java/tomcat6/tomcat-i18n-es-6.0.44.jar
java    28171 tomcat  mem    REG  202,1    546385 144442 /usr/share/java/tomcat6/jasper-6.0.44.jar
java    28171 tomcat  mem    REG  202,1    131986 144434 /usr/share/java/tomcat6/catalina-ha-6.0.44.jar
java    28171 tomcat  mem    REG  202,1   1247327 144431 /usr/share/java/tomcat6/catalina-6.0.44.jar
java    28171 tomcat  mem    REG  202,1    238835 144436 /usr/share/java/tomcat6/catalina-tribes-6.0.44.jar
java    28171 tomcat  mem    REG  202,1    594048 144340 /usr/share/java/apache-commons-collections.jar
java    28171 tomcat  mem    REG  202,1   1521592 144344 /usr/share/java/ecj.jar
java    28171 tomcat  mem    REG  202,1    285423 143758 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/jsse.jar
java    28171 tomcat  mem    REG  202,1  29116948 143773 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/rt.jar
java    28171 tomcat  mem    REG  202,1     31904 139128 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/amd64/libzip.so
java    28171 tomcat  mem    REG  202,1     61920 396039 /lib64/libnss_files-2.17.so
java    28171 tomcat  mem    REG  202,1     15244 144429 /usr/share/java/tomcat6/annotations-api-6.0.44.jar
java    28171 tomcat  mem    REG  202,1    113320 396031 /lib64/libnsl-2.17.so
java    28171 tomcat  mem    REG  202,1    205304 134197 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/amd64/libjava.so
java    28171 tomcat  mem    REG  202,1     69864 139127 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/amd64/libverify.so
java    28171 tomcat  mem    REG  202,1     44088 396051 /lib64/librt-2.17.so
java    28171 tomcat  mem    REG  202,1     89312 395976 /lib64/libgcc_s-4.8.3-20140911.so.1
java    28171 tomcat  mem    REG  202,1   1141552 396029 /lib64/libm-2.17.so
java    28171 tomcat  mem    REG  202,1    983552 396536 /usr/lib64/libstdc++.so.6.0.19
java    28171 tomcat  mem    REG  202,1  11239440 140749 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/amd64/server/libjvm.so
java    28171 tomcat  mem    REG  202,1     18232 396591 /lib64/libattr.so.1.1.0
java    28171 tomcat  mem    REG  202,1   2112376 396021 /lib64/libc-2.17.so
java    28171 tomcat  mem    REG  202,1     19512 396027 /lib64/libdl-2.17.so
java    28171 tomcat  mem    REG  202,1     17328 144253 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/lib/amd64/jli/libjli.so
java    28171 tomcat  mem    REG  202,1    142296 396047 /lib64/libpthread-2.17.so
java    28171 tomcat  mem    REG  202,1     88608 396431 /lib64/libz.so.1.2.8
java    28171 tomcat  mem    REG  202,1     16600 396696 /lib64/libcap.so.2.16
java    28171 tomcat  mem    REG  202,1    164432 396014 /lib64/ld-2.17.so
java    28171 tomcat  mem    REG  202,1    112407 144443 /usr/share/java/tomcat6/jasper-el-6.0.44.jar
java    28171 tomcat  mem    REG  202,1     32768   4958 /tmp/hsperfdata_tomcat/28171
java    28171 tomcat  mem    REG  202,1     21528 144382 /usr/share/java/apache-commons-daemon.jar
java    28171 tomcat  mem    REG  202,1     32355  17767 /usr/share/tomcat6/bin/tomcat-juli-6.0.44.jar
java    28171 tomcat  mem    REG  202,1     22755  17764 /usr/share/tomcat6/bin/bootstrap-6.0.44.jar
java    28171 tomcat    0r   CHR    1,3       0t0   5666 /dev/null
java    28171 tomcat    1w   REG  202,1      1986 264235 /var/log/tomcat6/catalina.out
java    28171 tomcat    2w   REG  202,1      1986 264235 /var/log/tomcat6/catalina.out
java    28171 tomcat    3r   REG  202,1  29116948 143773 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/rt.jar
java    28171 tomcat    4r   REG  202,1     22755  17764 /usr/share/tomcat6/bin/bootstrap-6.0.44.jar
java    28171 tomcat    5r   REG  202,1    285423 143758 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/jsse.jar
java    28171 tomcat    6r   CHR    1,8       0t0   5670 /dev/random
java    28171 tomcat    7r   CHR    1,9       0t0   5671 /dev/urandom
java    28171 tomcat    8r   REG  202,1     32355  17767 /usr/share/tomcat6/bin/tomcat-juli-6.0.44.jar
java    28171 tomcat    9r   REG  202,1     21528 144382 /usr/share/java/apache-commons-daemon.jar
java    28171 tomcat   10w   REG  202,1      1986 265017 /var/log/tomcat6/catalina.2016-02-09.log
java    28171 tomcat   11w   REG  202,1       230 265018 /var/log/tomcat6/localhost.2016-02-09.log
java    28171 tomcat   12w   REG  202,1         0 265019 /var/log/tomcat6/manager.2016-02-09.log
java    28171 tomcat   13w   REG  202,1         0 265020 /var/log/tomcat6/host-manager.2016-02-09.log
java    28171 tomcat   14r   REG  202,1   1521592 144344 /usr/share/java/ecj.jar
java    28171 tomcat   15r   REG  202,1    594048 144340 /usr/share/java/apache-commons-collections.jar
java    28171 tomcat   16r   REG  202,1    238835 144436 /usr/share/java/tomcat6/catalina-tribes-6.0.44.jar
java    28171 tomcat   17r   REG  202,1    112407 144443 /usr/share/java/tomcat6/jasper-el-6.0.44.jar
java    28171 tomcat   18r   REG  202,1   1247327 144431 /usr/share/java/tomcat6/catalina-6.0.44.jar
java    28171 tomcat   19r   REG  202,1    131986 144434 /usr/share/java/tomcat6/catalina-ha-6.0.44.jar
java    28171 tomcat   20r   REG  202,1    546385 144442 /usr/share/java/tomcat6/jasper-6.0.44.jar
java    28171 tomcat   21r   REG  202,1     70507 144452 /usr/share/java/tomcat6/tomcat-i18n-es-6.0.44.jar
java    28171 tomcat   22r   REG  202,1     54649 144432 /usr/share/java/tomcat6/catalina-ant-6.0.44.jar
java    28171 tomcat   23r   REG  202,1     51673 144454 /usr/share/java/tomcat6/tomcat-i18n-fr-6.0.44.jar
java    28171 tomcat   24r   REG  202,1    400811 144335 /usr/share/java/log4j-1.2.16.jar
java    28171 tomcat   25r   REG  202,1     15244 144429 /usr/share/java/tomcat6/annotations-api-6.0.44.jar
java    28171 tomcat   26r   REG  202,1    131802 144337 /usr/share/java/tomcat6-servlet-2.5-api-6.0.44.jar
java    28171 tomcat   27r   REG  202,1     34604 144378 /usr/share/java/tomcat6-el-2.1-api-6.0.44.jar
java    28171 tomcat   28r   REG  202,1     76583 144349 /usr/share/java/tomcat6-jsp-2.1-api-6.0.44.jar
java    28171 tomcat   29r   REG  202,1    133917 144419 /usr/share/java/mx4j/mx4j-remote-3.0.1.jar
java    28171 tomcat   30r   REG  202,1    799981 144450 /usr/share/java/tomcat6/tomcat-coyote-6.0.44.jar
java    28171 tomcat   31r   REG  202,1     97002 144296 /usr/share/java/apache-commons-pool-1.5.6.jar
java    28171 tomcat   32r   REG  202,1     53900 144456 /usr/share/java/tomcat6/tomcat-i18n-ja-6.0.44.jar
java    28171 tomcat   33r   REG  202,1    155739 144324 /usr/share/java/apache-commons-dbcp.jar
java    28171 tomcat   34r   REG  202,1    259156 144417 /usr/share/java/mx4j/mx4j-jmx-3.0.1.jar
java    28171 tomcat   35r   REG  202,1     98229 143756 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/jce.jar
java    28171 tomcat   36r   REG  202,1    501379 143733 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/ext/localedata.jar
java    28171 tomcat   37r   REG  202,1    248596 143736 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/ext/sunpkcs11.jar
java    28171 tomcat   38r   REG  202,1    215355 143735 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/ext/sunjce_provider.jar
java    28171 tomcat   39u  IPv6  34451       0t0    TCP *:webcache (LISTEN)
java    28171 tomcat   40u  sock    0,7       0t0  34449 can't identify protocol
java    28171 tomcat   41r   CHR    1,9       0t0   5671 /dev/urandom
java    28171 tomcat   42r   CHR    1,9       0t0   5671 /dev/urandom
java    28171 tomcat   43r   CHR    1,9       0t0   5671 /dev/urandom
java    28171 tomcat   44r   CHR    1,9       0t0   5671 /dev/urandom
java    28171 tomcat   45r   CHR    1,9       0t0   5671 /dev/urandom
java    28171 tomcat   46u  IPv6  34881       0t0    TCP *:8009 (LISTEN)
java    28171 tomcat   47u  IPv6  34904       0t0    TCP localhost:mxi (LISTEN)
java    28171 tomcat   48r   REG  202,1   3534509 141580 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/charsets.jar
java    28171 tomcat   49r   REG  202,1   3534509 141580 /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.37.x86_64/jre/lib/charsets.jar

```

##### See what is listening on port 8080
	sudo lsof -ni:8080

##### Install Lynx
	sudo yum install lynx

##### Try the default Tomcat home page
	lynx localhost:8080
```
If you're seeing this page via a web browser, it means you've setup Tomcat successfully. Congratulations!
```

##### Check the external IP address
	wget http://ipinfo.io/ip -qO -

##### Open a web browser on your local machine and go to http://[ec2 ip address]:8080
```
 As you may have guessed by now, this is the default Tomcat home page.
```

##### Make a directory for the web.xml file
	mkdir -p war/WEB-INF

##### Make the web.xml file
	vim ~/git/spring-mvc/war/WEB-INF/web.xml
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
##### Make an index file
	vim ~/git/spring-mvc/war/index.jsp
```html
<html>
  <head><title>Example :: Spring Application</title></head>
  <body>
    <h1>Example - Spring Application</h1>
    <p>This is my test. Welcome to index.jsp</p>
  </body>
</html>
```

##### Create a build.xml file.
*Mind the project name, property file, and javac source and destination values.*
```
vim ~/git/spring-mvc/build.xml
```
```xml
<?xml version="1.0"?>

<project name="springapp" basedir="." default="usage">
  <!-- This build.properties file is kept in the root of your home directory -->
    <property file="${user.home}/build.properties"/>

    <property name="src.dir" value="src"/><!-- Make sure this dir exists -->
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
        	source="1.6" 
        	target="1.6" 
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
##### Create Tomcat user named 'tomcat' with 's3cret' as their password
	sudo vim /usr/share/tomcat6/conf/tomcat-users.xml
```xml
<?xml version='1.0' encoding='utf-8'?>
<tomcat-users>
  <role rolename="manager"/>
  <user username="tomcat" password="s3cret" roles="manager"/>
</tomcat-users>
```
	
##### Restart the Tomcat service to get the server to pick up the config file changes
	sudo service tomcat6 status
	sudo service tomcat6 restart
	sudo service --status-all | grep tomcat

##### Visit the default Tomcat home page to make sure it is up an running
	lynx localhost:8080
	
##### Install Ant
	sudo yum install ant

##### Check the Ant version
	ant -version
```
Apache Ant(TM) version 1.8.3 compiled on February 25 2015
```
##### Run ant
	ant -v
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

##### List the intstalled Tomcat applications
	ant -v list

##### Make a directory for Java files
	mkdir src  
	
##### Build the project
	ant -v build

##### Give your developer account permission to write to the webapps directory
	sudo chown -R [YOUR DEVELOPER ACCOUNT] /usr/share/tomcat6/webapps

##### Deploy the project
	ant -v deploy

##### Test the web app
	lynx localhost:8080/springapp

##### Find a copy of the Spring Framework 2.5 with dependencies
* https://github.com/bachmeb/spring-framework-2.5

##### Download and uppack Spring Framework 2.5 with dependencies
	cd /opt
	sudo mkdir spring-framework
	cd spring-framework/
	sudo wget http://s3.amazonaws.com/dist.springframework.org/release/SPR/spring-framework-2.5-with-dependencies.zip
	sudo unzip spring-framework-2.5-with-dependencies.zip
	
##### Make a lib directory in WEB-INF
	mkdir ~/git/spring-mvc/war/WEB-INF/lib

##### Find spring.jar, spring-webmvc.jar, and commons-logging.jar in the spring-framework package
	find /opt/spring-framework/spring-framework-2.5/ | grep spring.jar
	find /opt/spring-framework/spring-framework-2.5/ | grep spring-webmvc.jar
	find /opt/spring-framework/spring-framework-2.5/ | grep commons-logging.jar

##### Copy the libraries to WEB-INF/lib
	find /opt/spring-framework/spring-framework-2.5/ | grep commons-logging.jar | xargs cp -t ~/git/spring-mvc/war/WEB-INF/lib/
	find /opt/spring-framework/spring-framework-2.5/ | grep spring-webmvc.jar | xargs cp -t ~/git/spring-mvc/war/WEB-INF/lib/
	find /opt/spring-framework/spring-framework-2.5/ | grep spring.jar | xargs cp -t ~/git/spring-mvc/war/WEB-INF/lib/
	ls -l ~/git/spring-mvc/war/WEB-INF/lib/

##### List the contents of each jar file
	jar tf war/WEB-INF/lib/spring.jar
	jar tf war/WEB-INF/lib/spring-webmvc.jar 
	jar tf war/WEB-INF/lib/commons-logging.jar 

##### Define a [DispatcherServlet](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html) in web.xml. Map the servlet to the *.htm file pattern.
	vim ~/git/spring-mvc/war/WEB-INF/web.xml
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
  
  <servlet>
    <servlet-name>springapp</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>

  <servlet-mapping>
    <servlet-name>springapp</servlet-name>
    <url-pattern>*.htm</url-pattern>
  </servlet-mapping>

</web-app>
```
##### Build, deploy, and test the project
	ant build
	sudo ant deploy
	lynx localhost:8080/springapp

##### Create springapp-servlet.xml
	vim ~/git/spring-mvc/war/WEB-INF/springapp-servlet.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-2.5.xsd">

  <!-- the application context definition for the springapp DispatcherServlet -->

  <bean name="/hello.htm" class="springapp.web.HelloController"/>

</beans>
```

##### Build, deploy, and test the project
	ant build
	sudo ant deploy
	lynx localhost:8080/springapp
	
##### Create HelloController
	mkdir -p ~/git/spring-mvc/src/springapp/web
	vim ~/git/spring-mvc/src/springapp/web/HelloController.java
```java
package springapp.web;

import org.springframework.web.servlet.mvc.Controller; // spring-webmvc.jar
import org.springframework.web.servlet.ModelAndView; // spring-webmvc.jar

import javax.servlet.ServletException; // servlet-api.jar
import javax.servlet.http.HttpServletRequest; // servlet-api.jar
import javax.servlet.http.HttpServletResponse; // servlet-api.jar

import org.apache.commons.logging.Log; // commons-logging.jar
import org.apache.commons.logging.LogFactory; // commons-logging.jar

import java.io.IOException;

public class HelloController implements Controller {

    protected final Log logger = LogFactory.getLog(getClass());

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("Returning hello view");

        return new ModelAndView("hello.jsp");
    }

}
```
##### Update the master-classpath in build.xml to look for servlet*.jar in the spring framework 2.5 directory
	vim ~/git/spring-mvc/build.xml
```
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

        <fileset dir="/opt/spring-framework/spring-framework-2.5/lib/j2ee/">
            <include name="servlet*.jar"/>
        </fileset>

        <pathelement path="${build.dir}"/>
    </path>

```
##### Build the project
	ant build
	
##### Create a test class
	mkdir -p ~/git/spring-mvc/test/springapp/web
	vim ~/git/spring-mvc/test/springapp/web/HelloControllerTests.java
```java
package springapp.web;

import org.springframework.web.servlet.ModelAndView;
import springapp.web.HelloController;
import junit.framework.TestCase;

public class HelloControllerTests extends TestCase {

    public void testHandleRequestView() throws Exception{		
        HelloController controller = new HelloController();
        ModelAndView modelAndView = controller.handleRequest(null, null);		
        assertEquals("hello.jsp", modelAndView.getViewName());
    }
}
```
##### Add test tasks to build script
	vim ~/git/spring-mvc/build.xml
```xml
    <property name="test.dir" value="test"/>
    <!-- SET THE SOURCE AND TARGET VALUES CORRECTLY-->
    <target name="buildtests" description="Compile test tree java files">
        <mkdir dir="${build.dir}"/>
        <javac destdir="${build.dir}" 
        	source="1.6" 
        	target="1.6" 
        	debug="true"
            	deprecation="false" 
            	optimize="false" 
            	failonerror="true"
            	includeantruntime="false"
            	>
            <src path="${test.dir}"/>
            <classpath refid="master-classpath"/>
        </javac>
    </target>
    
    <target name="tests" depends="build, buildtests" description="Run tests">
        <junit printsummary="on"
            fork="false"
            haltonfailure="false"
            failureproperty="tests.failed"
            showoutput="true">
            <classpath refid="master-classpath"/>
            <formatter type="brief" usefile="false"/>
            
            <batchtest>
                <fileset dir="${build.dir}">
                    <include name="**/*Tests.*"/>
                </fileset>
            </batchtest>
            
        </junit>
        
        <fail if="tests.failed">
            tests.failed=${tests.failed}
            ***********************************************************
            ***********************************************************
            ****  One or more tests failed!  Check the output ...  ****
            ***********************************************************
            ***********************************************************
        </fail>
    </target>
```
##### Install Ant JUnit
	sudo yum search ant-junit
	sudo yum install ant-junit

##### Copy junit-3.8.2.jar to the WEB-INF/lib directory
	find /opt/spring-framework/spring-framework-2.5 
	find /opt/spring-framework/spring-framework-2.5 | grep junit-3.8.2.jar 
	find /opt/spring-framework/spring-framework-2.5 | grep junit-3.8.2.jar | xargs cp -t ~/git/spring-mvc/war/WEB-INF/lib/

##### List the contents of the WEB-INF/lib directory	
	ls -la ~/git/spring-mvc/war/WEB-INF/lib/
```
drwxrwxr-x 2 bachmeb bachmeb    4096 Feb  9 15:12 .
drwxrwxr-x 4 bachmeb bachmeb    4096 Feb  9 14:43 ..
-rw-r--r-- 1 bachmeb bachmeb   52915 Feb  9 14:37 commons-logging.jar
-rw-r--r-- 1 bachmeb bachmeb  120640 Feb  9 15:12 junit-3.8.2.jar
-rw-r--r-- 1 bachmeb bachmeb 2838649 Feb  9 14:37 spring.jar
-rw-r--r-- 1 bachmeb bachmeb  330204 Feb  9 14:37 spring-webmvc.jar
```

##### Run test tasks
	ant tests
```
Buildfile: /home/bachmeb/git/spring-mvc/build.xml

build:

buildtests:
    [javac] Compiling 1 source file to /home/bachmeb/git/spring-mvc/war/WEB-INF/classes

tests:
    [junit] Running springapp.web.HelloControllerTests
    [junit] Testsuite: springapp.web.HelloControllerTests
    [junit] Feb 8, 2016 9:17:45 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.043 sec
    [junit] Tests run: 1, Failures: 0, Errors: 0, Time elapsed: 0.043 sec
    [junit]
    [junit] ------------- Standard Error -----------------
    [junit] Feb 8, 2016 9:17:45 PM springapp.web.HelloController handleRequest
    [junit] INFO: Returning hello view
    [junit] ------------- ---------------- ---------------

BUILD SUCCESSFUL
Total time: 1 second
```
##### Make a hello.jsp file next to the index file
	vim ~/git/spring-mvc/war/hello.jsp
```html
<html>
  <head><title>Hello :: Spring Application</title></head>
  <body>
    <h1>Hello - Spring Application</h1>
    <p>Greetings. You have reached hello.jsp</p>
  </body>
</html>
```

##### Run test tasks
	ant tests

##### Compile and deploy the application
	sudo ant deploy
	ant reload

##### Test the web page in a browser
	lynx http://localhost:8080/springapp/hello.htm
```
The HTTP request goes to springapp/hello.htm
hello.htm is mapped to HelloController.java in springapp-servlet.xml
HelloController returns a ModelAndView named "hello.jsp"
The springapp/hello.jsp page is returned to the browser
```
	
##### Read the catalina.out file
	cat /usr/share/tomcat6/logs/catalina.out


* * *
[README](/README.md)
