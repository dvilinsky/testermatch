## Running the project:
1. Install [postgres](https://www.postgresql.org/download/)
2. Run the command `psql -U USERNAME - c "create database applause_test"
3. In `src/main/resources/applicaton.properties` you will find the following two lines:
      `spring.datasource.username=postgres`
     `spring.datasource.password=pw`
Set the username and password to your database's username and password. Do the same for the `spring.flyway.user` and `spring.flyway.password` properties
This is, admittedly, the least ideal part of the build process, and definitely needs improvement. 
If I knew how to package up a postgres instance, I would do that instead.
4. Install [gradle](https://gradle.org/install/)
5. Run `gradlew clean build`. If you are on Linux, you may get the error: "./gradlew: Permission denied". If so, change the file
permissions with `chmod +x gradlew`
6. This will produce the jar in `build/libs/testermatch-1.0-SNAPSHOT.jar`
Run this jar with `java -jar build/libs/testermatch-1.0-SNAPSHOT.jar`.
The first time you run the project, Flyway will automatically migrate the database
and populate it with the application data.
7. Service will be available at localhost:8080

# Discussion
## Endpoints
The way I understood the search requirements, there are essentially four cases for 
what kind of search a user can perform:
1. Country=ALL and Device=All
2. Country=ALL and Device=[SOME DEVICE(s)]
3. Country=[SOME COUNTRY(s)] and Device=All
4. Country=[SOME COUNTRY(s)] and Device[SOME DEVICE(s)]

With that in mind, the app exposes the following four endpoints, each one corresponding
to one of the cases descrbed above. They are, respectively:
1. `/testers/all`
2. `/testers/device` This endpoint requires a query parameter `devices` which is the device(s) for which you want to search. At first
glance, it may seem that this endpoint is oddly named. However, when country=ALL, I understood that
to mean that the end user does not care what country the tester is in, and is only searching by
device. Hence, the name.
3. `/testers/country` This endpoint also requires a query parameter `countries`, which is the country(s) for which you want to search.
4. `/testers` This endpoint requires two query parameters `countries` and `devices`, which follow the same pattern as the parameters above.

## Returned data
Each endpoint returns a JSON array where each object is of the form:
`{firstName: firstName, lastName:lastName, numBugs:numBugs`}. The array is sorted in order of numBugs, descending. 

## Areas for improvement 
I think the biggest area for improvement is that the way the API has been defined is a little bit wonky. For example, the most 
natural interpretation of the endpoint `/testers`, to me, is that it would return all testers. But that is not the case; we have a second endpoint for this. 
The other way it needs improvement is that it is not particulary extensible. It works well enough for the requirements as specified, but what if there were a third 
search dimension, say "age." Following this pattern, there would then be 8 endpoints. And if there were still another search 
dimension, we'd have 16 endpoints. So clearly, this is not ideal. However, for now it works well.

The build process also needs work. A user of this service should not have to run postgres commands on their commandline to use the API.

The other major avenue for improvement would be the SQL queries in `com.dvilinsk.testermatch.repository.TesterRepository`.
They work well, but they don't take advantage of any built-in JPA interfaces or JPQL queries. 

## Other notes
In `src/main/resources` there is a quick and dirty python script in `csv_to_sql.py` which I used to generate the migration insertion files. It is not necessary to use this service,
but is included in case you were interested in seeing it.
