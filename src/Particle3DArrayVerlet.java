import java.lang.Math;
import java.util.Scanner;
import java.io.*;
public class Particle3DArrayVerlet 
{

	public static void main(String[] argv) throws IOException 
	{
		// Identify a file from the comand line and use it to create the particles.
		File file = new File(argv[0]);
		Scanner input = new Scanner(file);
		//create a scanner object to input the 
		String outfile1 = new String(argv[1]);
		String outfile2 = new String (argv[2]);
		String outfile3 = new String (argv[3]);
		
		PrintWriter output1 = new PrintWriter (new FileWriter(outfile1));
		PrintWriter output2 = new PrintWriter(new FileWriter(outfile2));
		PrintWriter output3 = new PrintWriter(new FileWriter(outfile3));
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
		Vector3D[][] force = new Vector3D[n][n];
		force = null;
		//create 3 vector3D arrays for the Verlet time intergrator
		double [][] energyArray = new double[iterations][n+1];
		//this array will store the energy of the individual particles and the total energy
		// of the system so it can be outputted and evaluated
		Function.arrayUpdateForce(particleArray,force);
		// starts the algorithm off by calculating the forces on the planets at the starts
		for ( int i=0 ;i< iterations;i++)
		{
			Function.arrayUpdatePosition(particleArray, dt, force);
			// update all the positions of the particle arra using functions from the function class
			Function.arrayUpdateForce(particleArray,force);
			// updates the force array using the verlet algorithm
			Function.arrayUpdateVelocity(particleArray, dt, force);
			//updates the velocity of the particles.
			Function.outputVMD( particleArray, output1,i);
			// THis method will write the results to the output file output1 in a format
			//WHich can be read by VMD.
			for (int j=0;j<n;j++)
			{
				energyArray[i][j] = particleArray[j].kineticEnergy();
			}
			for (int j=0;j<n; j++)
				for(int k=0;k<n ; k++)
				{
					if( j!= k)
					{
						energyArray[i][j] = Particle3D.GravitationalPotential(particleArray[j], particleArray[k]);
					}
				}
			
		}
	
		
		
		
		
		
	}
	
	

}
