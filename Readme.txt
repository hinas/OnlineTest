Project: Online Test 
This Project has capabilities to conduct an online exam, by configuring questions and options in the database
A student can take the test and submit his/her responses.
At the end of the test, students grade is calculated and stored in database 

Steps for installation of Pre-requisites softwares are based on Windows machine
if you have linux or OSX then please refer refer interent and istall it.

----------------------------------------------------------------------------------------------------------------------------------
1)Scope of this project
	a)Test specific login page allows student to enter its credentials
	b)Student can choose the option and submit its response
	c)Student can change the question by selecting in combo box and pression GO button
	d)Student can submit the answer multiple times till the test is finished
	e)if student ends the test, a confirmation page is shown which displays total question answered
	f)On confirmation of Finish test, a thank you page is shown and students grade is stored in database (test_history table)

2) Pre-requisites to run this project:
	a) JDK 1.7 and above (download from http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
	b) Mysql 5.5 and above (download from http://dev.mysql.com/downloads/mysql/)
	c) Apache Maven 3.3 and above (download from https://maven.apache.org/download.cgi)

3) Steps for installation of prerequisites
	a)JDK 1.7 
	Follow the installer instructions and it will be successfully isntalled
	open command line and enter the following command to verify installation fy : java -version 
		
	b)Mysql 5.7.11
	Download Mysql .msi (mysql-installer-community-5.7.11.0.msi) and install (follow installer isntructions)
	when asked, set root password as :root
	when asked, set port: 3306
	add mysql bin folder to path
	 
	c)Apache Maven 3.3.9
	Extract the downloaded zip to a <location>
	add the following to PATH of environment variable : <location>\apache-maven-3.3.9\bin;
	open command line and enter the following command to verify installation fy : mvn --version 


4) Steps to run the project:
	Details steps to run the project can be found in Howto.txt

5) Requirements not covered:
	a) Front end and backend timer not implemented

6) Issues faced
	a) Configuring Spring MVC to handle static resources such as JS and CSS
