#!/bin/bash

# Remove old stuff
echo "Removing old Classes..."
rm -rf *.class
echo "     Done"

# Move shared files here
echo "Copying files to Directory..."
cp ../shared/BullyBot.java ../shared/Fleet.java ../shared/Planet.java ../shared/PlanetWars.java .
echo "     Done"

# Compile Stuff
echo "Compiling new Files..."
javac *.java
echo "     Done"

# Remove source files
echo "Removing copied source files..."
rm BullyBot.java Fleet.java Planet.java PlanetWars.java
echo "     Done"

# Run Game
echo "Running Game..."
java -jar ../shared/tools/PlayGame.jar ../shared/tools/maps/8planets/map1.txt "java DFSBot" "java BullyBot" | python ../shared/tools/visualizer/visualize_locally.py
