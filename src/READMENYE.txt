N-body simulation - Author Nye Baker
(note this simulation is not optimised for large N)
This simulation uses a verlet time intergrator.
Will need to find the error in this algorithm.
The input file will be of the form 
n dt iterations
{
x-pos y-pos z-pos
x-vel y-vel z-vel
Mass
Name
}
Where n will be the number of particles which we wish to simulate in this run and dt will be the interval between 
each step of the algorithm. Iterations will be the number of 
everything between the brackets will need to be repeated in EXACTLY the same format with a space between the lines
The ouput file will be of the form
n
Point =m
s1m x1m y1m z1m
s2m x2m y2m z2m
where n is the number of particles in the simulation
m is the number of ierations which have passed
the cartesian coordinaes of the particle where s1m is a label for that specific position
and x1m y1m z1m etc are the coordinates of the point.

such that it can be passed into xm grace.

---------------------------------
CHANGES
changed structure of program
switched input file structure so there are 4 input files in argv instead of 5
changed the methods update position and velocity to static
estimateing error by monitoring the fluctuations in the energy. print out in output method.
CHANGES AFTER FEEDBACK
scrapped 2D array on recomendation from design document in favour of 1d array ( note it has made code shorter and eaiser to read though) eddited the functions class so 
that the 1D nature is compensated for.
In the design document we didnt put all the methods in the function class as static they have been all along.......
To find out the distance from the sun etc we will need to put in a loop which will determin the min seperation of the particle which we determin to be the sun
this loop would compare the separation of the planet to the sun at each loop and record the lowest one at the end.
( it would be an idea to set the sun to particle 0 by default unless we are simulating somthing without the sun)
to find the length of a year in our simulation may be difficult as each planet will not return to the same starting position each time.
Should this be automated or found out from the code.....ASK A TUTOR as we would have to determin when the particle has returned to its origional starting position
or near enough that we could concider it to have completed a full year. could possibly meausre the angle from a fixed vector and when that passes 2 PI then record
the number of iterations which have passed.
Also may be an idea to output r^2 for each of the planets for calculation of keplers law......or we could calculate it directly from the output files 
13/02/16
chnaged the outPut so it is closed ( so it writes to the output files) and fixed bug where it continued to output data until exclipse closed.
began streamlining code
got basic functionality on vmd

