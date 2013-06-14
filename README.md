reviewmanager
=============

---
###ReviewManager module for UMFlix

---
**Importing ReviewrManager - Dependency**

1. Install dependencies. Dependecies: modelsorage,authenticationhandler. See Insatlling 
dependencies below.  
1. git clone git@github.com:martinbomio/reviewmanager.git to target directory
2. cd target directory
3. mvn clean install
4. cd current project
5. add dependency to pom.xml
<br />
	`<dependency>`<br />
	    &nbsp;&nbsp;&nbsp;&nbsp;`<groupId>review-manager</groupId>`<br />
        &nbsp;&nbsp;&nbsp;&nbsp;`<artifactId>review-manager</artifactId>`<br />
        &nbsp;&nbsp;&nbsp;&nbsp;`<version>1.0-SNAPSHOT</version>`<br />
    `</dependency>`
6. mvn install current project

**Installing dependencies**

1. git clone git@github.com:marshhxx/modelstorage.git
2. cd modelstorage/
3. mvn clean install
4. cd..
5. git clone git@github.com:haretche2/autenticationhandler.git
6. cd authenticationhandler/
3. mvn clean install