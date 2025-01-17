# Set the name of the Maven project
PROJECT_NAME=waste_management_system

# Path to the target directory where the packaged JAR will be placed
TARGET_DIR=target

# Default goal is to build the project
.DEFAULT_GOAL := all

# Java version to use
JAVA_VERSION=java

# Maven commands
MAVEN=mvn

# Clean the project (remove target directory)
clean:
	$(MAVEN) clean

# Build and install the project
install:
	$(MAVEN) clean install

# Package the project (create a JAR file)
package:
	$(MAVEN) package

# Run the Spring Boot application
run:
	$(MAVEN) spring-boot:run

# Run tests in the project
test:
	$(MAVEN) test

# Build, package, and install the project
all: install

# Run the application (from the packaged JAR)
run-jar:
	$(JAVA_VERSION) -jar $(TARGET_DIR)/$(PROJECT_NAME)-1.0.0.jar
