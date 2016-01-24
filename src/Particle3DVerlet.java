/**A simulation programme for a 2D orbit, using the Verlet time integration algorithm
 * 
 * @author A. Baker
 * @author S. Rigby
 */
import java.io.*;
import java.lang.Math;
import java.util.Scanner;

public class Particle3DVerlet 
{
 
     
	//For this example, take gravitational constant G = 1
    static double massBig = 1.0;
    static double massSmall = 0.0001;
    static double numTimeStep = 6000;
    static double dt = 0.1;
    static double t = 0;
    static double Energy = 0, Energypre = 0, diff = 0;
    //Energy and Energypre are used to calculate the difference in energy between two consecutve time steps.

    public static void main(String[] argv) throws IOException
    {

     //Identify input file from command line
	File file = new File(argv[0]);
	Scanner in = new Scanner(file);
	//Create a file to contain x and y positions at each timestep
	String outfile = argv[1];
	//Create a file to contain total energy at each timestep
	String outfile2 = argv[2];
	//Create a file to contain time and total energy
	String outfile3 = argv[3];
	PrintWriter output = new PrintWriter (new FileWriter(outfile));
	PrintWriter output2 = new PrintWriter(new FileWriter(outfile2));
	PrintWriter output3 = new PrintWriter(new FileWriter(outfile3));
    	
    	//Create particles representing the central and orbiting particles respectively
        Particle3D big = new Particle3D();
        Particle3D small = new Particle3D();

        //Set the mass of the central particle to the mass given above. Position and velocity are initialised to zero vectors
        big.setMass(massBig);
     
        //Properties of orbiting particle are read from an input file
        small.readScanner(in);
   
    	//Create a vector representing the gravitational force on the orbiting mass
        Vector3D force = new Vector3D();
        //Create a vector representing the gravitational force on the orbiting mass after a timestep
        Vector3D forceNew = new Vector3D();
        //Calcualte the initial force
        force = small.GravitationalForce(small,big);
        //Create a vector representing the average of the gravitational forces before and after a timestep
        Vector3D tempForce = new Vector3D();
        
        
        for (int i=0;i<numTimeStep;i++) {
        	
        //Update the position with the current velocity and the second order terms of Taylor expansion
        small.secondOrderPositionUpdate(dt, force);
        //Calculate the new force 
        forceNew =small.GravitationalForce(small,big);
        //Update the velocity using the average of the forces before and after the timestep, which is calculated in a temporary vector tempForce
        tempForce = force;
        tempForce=Vector3D.vectorAddition(forceNew,tempForce);
        tempForce.scalarMultiply(0.5);
        small.velocityUpdate(dt,tempForce); 
        //Update time
        t=t+dt;
        //Print the position and kinetic energy of the particle at this value of t to the output files
       	output.printf(" %10.8f %10.8f\n",small.getPosition().getX(),small.getPosition().getY());
	output2.printf("%10.8f %10.9f %10.9f\n",small.kineticEnergy(), small.GravitationalPotential(big, small),small.kineticEnergy()+ small.GravitationalPotential(big, small));
	output3.printf("%10.8f %10.9f\n",t,small.kineticEnergy()+small.GravitationalPotential(big,small));
	Energypre=Energy;
        force = forceNew;
         
    }
        
	output.close();
	output2.close();
	output3.close();
     
    }

     
}
