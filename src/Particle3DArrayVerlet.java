import java.lang.Math;
import java.util.Scanner;
import java.io.*;
public class Particle3DArrayVerlet 
{

	public static void main(String[] argv) throws FileNotFoundException 
	{
		// Identify a file from the comand line and use it to create the particles.
		File file = new File(argv[0]);
		Scanner input = new Scanner(file);
		//create a scanner object to input the 
		String outfile1 = new String(argv[1]);
		String outfile2 = new String (argv[2]);
		String outfile3 = new String (argv[3]);
		// creates the three out put files for this program
		int n = input.nextInt(), dt = input.nextInt(), iterations = input.nextInt();
		// n will be the number of particles we are dealing with in this simulation
		// dt is the the time step and iterations is the number of iterations which this 
		// simulation will run for
		Particle3D[] particleArray = new Particle3D[n];
		// create an array of particles
		for (int i=0;i<particleArray.length;i++)
		{
		particleArray[i].setPosition(input.nextDouble(), input.nextDouble(), input.nextDouble());
		particleArray[i].setVelocity(input.nextDouble(), input.nextDouble(), input.nextDouble());
		particleArray[i].setMass(input.nextDouble());
		particleArray[i].setName(input.next());
		
		}
		// set the particles using the input
		Vector3D[][] force,forceNew,tempForce = new Vector3D[n][n];
		//create 3 vector3D arrays for the Verlet time intergrator
		
		force= Function.arrayUpdateForce();
		// starts the algorithm off by calculating the forces on the planets at the starts
		for ( int i=0 ;i< iterations;i++)
		{
			Function.arrayUpdatePosition(particleArray, dt, force);
			// update all the positions of the particle arra using functions from the function class
			Functions.arrayUpdateForce(forceNew);
			//create a new force array which is the force after the planets have moved dt in time
			
		}
		
		
		
		
		
	}
	
	

}
