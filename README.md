# WeatherSimulation

Prerequisite

    1. JDK 8 or above
    2. Maven

How to run

    1. mvn clean install
    2. cd target/classes/
    3. java com.upuldi.WeatherSimulator

About

    By default simulation will run 100 times and for each run it will sleep 2 seconds.
    Test Coverage : 90% Class, 96% Method, 85% Line (Coverage Report was obtained from IDEA)
TODO

    Elavation value was not used to calculate temperature and pressure.(As I completely forgot it),
    however it could have used as a factor to change temperature,pressue and humidity. For ex after 
    calculating the tempreture we could have decrease if by some value for every 10M increase from sealevel. 
    
    
