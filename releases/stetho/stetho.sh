#!/bin/bash

# Set the path to your config.yml
CONFIG_PATH=config.yml

# Run the Spring Boot application with the specified configuration file
java -jar stetho.jar --spring.config.location=$CONFIG_PATH

# Pause to keep the terminal window open after execution (optional)
read -p "Press [Enter] to exit..."
