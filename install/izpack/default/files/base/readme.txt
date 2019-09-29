This sorftware is provided under GPL license.
To use dbclient :
-- install JRE 6 or JDK 6 or later (from java.sun.com)
-- unzip the install zip file in an empty folder
-- put your jdbc drivers in the jdbcdrivers folder
-- double click on the run.jar 
   or
   on the console write : java -jar run.jar

dbclient accepts some command line arguments
--nosplash       : (or -s  ) to disable splash screen on application startup
--noautoconnect  : (or -c  ) to disable auto opening of sessions (sessions with "auto connect" flag armed)
--workdir <path> : (or -wd ) to specify path working directory
--currentworkdir : (or -cwd) to set System.getProperty('user.dir')  as working directory (current working directory)
--homeworkdir    : (or -hwd) to set a shared working directory in the user home directory)

any other arguments will be resolved as session path to open
session path is either {sessiongroup}/{sessionname} or simply {sessionname}

you cand add these arguments in the run.jex file (following {# put here application arguments} line)
when you add application arguments in the jex file ensure prefixing them with "app.arg=".
For instance you can disable splash screen by adding the following line in run.jex:
app.arg=-nosplash

I hope you find this software helpful.
Latest version of DBClient could be found at https://dbclient.java.net
For any further information, please fill free to mail me at taha.bensalah@gmail.com
