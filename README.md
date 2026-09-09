# Frequency Allocator

A Java command-line application that generates a frequency allocation plan for cellular sites.

The application assigns frequencies from 110–115 while ensuring that geographically close cells do not share the same frequency.

## Requirements

- Java
- Make

## Running the Program - (make sure you are in the src folder)

Compile the program:
make

Run the program:
make run

- The program will ask for the number of cells and then details for each cell in the following format: ID Easting Northing Longitude Latitude
- Example:
  A 536660 183800 -0.03098 51.53657
  B 537032 184006 -0.02554 51.53833
- The program then will display:
    - The frequency allocation to each cell
    - The network connections between each cell
    - A network visualisation saved as Network.png

Cleaning (To remove compiled files and generated visualisation):
make clean

