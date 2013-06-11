reviewmanager
=============

---
###ReviewManager module for UMFlix

---
**Importing ReviewrManager - Dependency**

1. git pull git@github.com:martinbomio/reviewmanager.git to target directory
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