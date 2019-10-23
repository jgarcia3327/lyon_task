# lyon_task
Programming Task

To run:
1. Download or clone repo
2. Download PeppolDirectory XML at https://directory.peppol.eu/public
3. Copy downloaded XML(directory-export-business-cards.xml) to cloned repo to src/main/resources/peppol/
4. Machine should have atleast Java 1.8 and Maven. To install Maven, please see https://www.baeldung.com/install-maven-on-windows-linux-mac
5. From the cloned repo parent directory, type 'mvn clean install' to pull Maven dependencies and build script
6. Run output jar 'java -jar target/peppoldirectory-api-0.0.1-SNAPSHOT.jar'


API Method Testing:

getById http://localhost:8080/businesscard/participant/{ICD}/{EnterpriseNumber}
e.g: http://localhost:8080/businesscard/participant/9956/0724486664

searchByName http://localhost:8080/businesscard/entity/name/search/{nameToSearch}
e.g: http://localhost:8080/businesscard/entity/name/search/LUND%20AS

getByName http://localhost:8080/businesscard/entity/name/get/{name}
e.g: http://localhost:8080/businesscard/entity/name/get/GULENG%20AS


*Will provide Postman collection for testing later
