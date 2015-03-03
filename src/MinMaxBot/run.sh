#!/bin/bash

# Remove old stuff
echo "Removing old Classes..."
rm -rf *.class
echo "     Done"

# Move Shared Files Here
echo "Copying files to Directory..."
cp ../shared/BullyBot.java ../shared/Fleet.java ../shared/Planet.java .
echo "     Done"

# Compile Stuff
echo "Compiling new Files..."
javac *.java
echo "     Done"

# Remove source files
echo "Removing copied source files..."
rm BullyBot.java Fleet.java Planet.java
echo "     Done"

# Run Game
echo "Running Game..."
java -jar ../shared/tools/PlayGame.jar ../shared/tools/maps/larger/map1.txt "java MinMaxBot" "java BullyBot" serial 100 1000 | python ../shared/tools/visualizer/visualize_locally.py
