# Traffic-Simulator : A Graphical Traffic Simulator  [![GPL Licence](https://badges.frapsoft.com/os/gpl/gpl.png?v=103)](https://opensource.org/licenses/GPL-3.0/)

 <p align="center">
  <span>Language:</span> 
  <a href="https://github.com/Zildj1an/Traffic-Simulator/blob/master/LEEME.md">Español</a> |
  <a href="https://github.com/Zildj1an/Traffic-Simulator">English</a> 
</p>

## How does it work?

It uses the MVC design pattern for a Swing GUI, and Java Threads for making the GUI responsive. you can find a <a href = "https://github.com/Zildj1an/Traffic-Simulator#screenshot">screenshot</a> and collaborators too.

## Main idea 
This object-oriented Traffic Simulator works with several models of vehicles, roads and junctions. You can test different junction policies in three different styles: "batch", "gui", "race" 
The simulator maintains a collection of simulated objects (vehicles on roads connected by
junctions); and a discrete time counter that is incremented in a loop while performing the
following operations:

  - Process pre-scheduled events that can add or alter simulated objects. Many example report files can be found at 
<a href="https://github.com/Zildj1an/Traffic-Simulator/tree/master/resources">resources folder </a>.Event example: 

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

## Screenshot

![alt text](https://github.com/Zildj1an/Traffic-Simulator/blob/master/GUI.png)

## Collaborators
* **Carlos Bilbao** &lt;cbilbao@ucm.com&gt;
* **Álvaro Lopez** &lt;https://github.com/KillerKing18&gt;
