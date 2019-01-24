# Traffic-Simulator
Graphical Traffic Simulator 
-

## What is it? 
This object-oriented Traffic Simulator works with several models of vehicles, roads and junctions. You can test different junction policies in three different styles: "batch", "gui", "race" 
The simulator maintains a collection of simulated objects (vehicles on roads connected by
junctions); and a discrete time counter that is incremented in a loop while performing the
following operations:

  - Process pre-scheduled events that can add or alter simulated objects. Example: 

              [vehicle_report]
              id = v1
              time = 5
              speed = 20
              kilometrage = 60
              faulty = 0
              location = (r1,30)

  - Advance the state of currently simulated objects, according to their behaviors. For
example, vehicles will advance if the road ahead is clear, but will have to stop and
wait for red traffic lights.

  -  Report on the current state of the simulated objects using the GUI

<h3> How does it work? </h3>

It uses MVC design pattern for a Swing GUI, and Java Threads for making the GUI responsive.

## Screenshot

![alt text](https://github.com/Zildj1an/Traffic-Simulator/blob/master/GUI.png)

## Collaborators
* **Carlos Bilbao** &lt;cbilbao@ucm.com&gt;
* **Álvaro Lopez** &lt;https://github.com/KillerKing18&gt;
