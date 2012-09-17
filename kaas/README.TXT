Most-improtant: run 
"mvn install:install-file -DgroupId=org.hardcode -DartifactId=juf -Dversion=1.0 -Dpackaging=jar -Dfile=kaas-daemon/external_lib/juf-1.0.jar"
to install the external lib -- juf framework.

The project of "kaas" is the parent maven project of the other projects.
The tester could use "mvn clean" "mvn package" "mvn assembly" and 
enter into the project of "kaas-server" to use "mvn jetty-run" to start the server.
  
kaas-service: webservice project


