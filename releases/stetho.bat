@echo off
REM Set the path to your application.yml
set CONFIG_PATH=application.yml

REM Run the Spring Boot application with the specified configuration file
java -jar stetho.jar --spring.config.location=%CONFIG_PATH%

REM Pause to keep the window open after execution
pause
