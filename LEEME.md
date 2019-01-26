# Traffic-Simulator : Un simulador de Tráfico Gráfico [![GPL Licence](https://badges.frapsoft.com/os/gpl/gpl.png?v=103)](https://opensource.org/licenses/GPL-3.0/)

 <p align="center">
  <span>Language:</span> 
  <a href="https://github.com/Zildj1an/Traffic-Simulator/blob/master/LEEME.md">Español</a> |
  <a href="https://github.com/Zildj1an/Traffic-Simulator">English</a> 
</p>

## 👨‍🔧 ¿Cómo funciona?

Emplea el patrón de diseño MVC para el Swing GUI, y Java Threads para ser GUI responsive. Puedes encontrar una captura de pantalla y los colaboradores al fondo.

## Idea Principal
Este simulador de Tráfico orientado a objetos trabaja con varios modelos de vehículos, carreteras y cruces. Puedes testear diferentes políticas de cruces en tres estilos distintos: "batch", "gui", "race" 
El simulador mantiene una colección de objetos simulados (vehículos y carreteras conectadas por cruces); y un contador de tiempo que incrementa en un bucle mientras realiza las siguientes operaciones:

  - Procesa eventos pre-planificados que pueden añadir o modificar objetos simulados. Muchos archivos de reporte de ejemplo pueden encontrarse en <a href="https://github.com/Zildj1an/Traffic-Simulator/tree/master/resources">resources folder </a>.Event example: 

              [vehicle_report]
              id = v1
              time = 5
              speed = 20
              kilometrage = 60
              faulty = 0
              location = (r1,30)

  - Avanza el estado de los objetos actualmente simulados, de acuerdo con sus comportamientos. Por ejemplo, los vehiculos avanzarán si la carretera que les espera está libre, pero se detendrán por semáforos en rojo.

  -  Reporte del actual estado del simulador usando el GUI

## Screenshot

![alt text](https://github.com/Zildj1an/Traffic-Simulator/blob/master/GUI.png)

## Colaboradores
* **Carlos Bilbao** &lt;cbilbao@ucm.com&gt;
* **Álvaro Lopez** &lt;https://github.com/KillerKing18&gt;

## Licencia
Este proyecto tiene la licencia GNU-GPL - ver <a href="https://github.com/Zildj1an/Graphic-Traffic-Simulator/blob/master/LICENSE">LICENSE.md</a> para más detalles.
