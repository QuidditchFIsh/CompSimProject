import java.util.Scanner;
import java.io.*;
public class Particle3DArrayVerlet 
{

	public static void main(String[] argv) throws IOException, FileNotFoundException 
	{
		System.out.println("welp");
		//Read the name of an input file from the command line and read the parameters of the system from there
		File file = new File(argv[0]);
		//Create a scanner object to read the data from the input file
		Scanner input = new Scanner(file);
		
		//Create output files with names given in the command line
		String outfile1 = new String(argv[1]);
		String outfile2 = new String (argv[2]);
		String outfile3 = new String (argv[3]);
		PrintWriter output1 = new PrintWriter (new FileWriter(outfile1));
		PrintWriter output2 = new PrintWriter(new FileWriter(outfile2));
		PrintWriter output3 = new PrintWriter(new FileWriter(outfile3));

		//Set n, which will be the number of particles in the simulation
		int n = input.nextInt();
		//Set dt, which will be the timestep used in the simulation
		double dt = input.nextDouble();
		//Set iterations, which will be the number of iterations for which the simulation will run
		int iterations = input.nextInt();
		
		//Create a particle array to contain all n bodies
		Particle3D[] particleArray = new Particle3D[n];
		//Create an array of doubles that will count the number of years that have passed for each particle, not including the Sun
		double[] counter = new double[particleArray.length - 1];
		
		//Set the initial positions of each particle from data given in the input file
		Vector3D initPos = new Vector3D();
		for (int i = 0; i < particleArray.length; i++)
		{
			
		particleArray[i] = new Particle3D("Temp");
		particleArray[i].readScanner(input);
		
		}
		
		//Create and initialise the force array
		Vector3D[] force = new Vector3D[n];
		Vector3D[] preForce = new Vector3D[n];
		for (int i = 0; i < n; i++)
		{
			force[i] = new Vector3D();
			preForce[i] = new Vector3D();
		}
		//Calculate the initial forces acting on each particle
		Function.arrayForceUpdate(particleArray, force,preForce);
		for(int j=0;j<n;j++)
		{
			preForce[j] = new Vector3D(force[j]);
			//System.out.println(particleArray[j]);
		}
		//Adjust the momentum of the system to prevent the centre of mass from drifting
		Function.adjustMomentumOfSystem(particleArray);
		//Calculate the initial energy of the system
		double initalEnergy = Function.arrayTotalEnergy(particleArray);
		//Loop over the iterations, performing the Verlet Time Integration Scheme
		for (int i = 0; i < iterations; i++)
		{
			//Update the position of each particle
			Function.arrayUpdatePosition(particleArray, dt, force);
			//Update the force acting on each particle
			//System.out.println(particleArray[0] + " " + particleArray[1] + " " + particleArray[2]);
			Function.arrayForceUpdate(particleArray, force, preForce);
			//System.out.println(force[0] +" "+ force[1]);
			//Update the velocity of each particle
			Function.arrayUpdateVelocity(particleArray, dt, force,preForce);
			for(int j=0;j<n;j++)
			{
				preForce[j] = new Vector3D(force[j]);
				//System.out.println(particleArray[j]);
			}
			//Write the positions of each particle to the first output f1ile to be read by VMD
			Function.outputVMD(particleArray, output1, i);
			
			//Write the energy fluctuation - the difference between energy in this iteration and initial energy - to the second output file
			output2.println(initalEnergy - Function.arrayTotalEnergy(particleArray) + " " + Function.arrayTotalEnergy(particleArray));
			//System.out.println(initalEnergy + " " + Function.arrayTotalEnergy(particleArray));
			//Loop over the particles and count the number of years that have passed
			//Start from 1 so that the Sun is not included
			for (int j = 1; j < particleArray.length; j++)
				{
					Function.yearCounter(initPos, particleArray[0], particleArray[j], counter[j-1]);
				}
		}
		
		//Create a double which will store the value of the length of a year for each orbiting particle
		double yearLength = 0;
		//Write the average length of a year and the total number of orbits completed for each particle to the third output file 
		output3.printf("Average Year Length and Total number of years \n ============================ \n");
		for (int k = 0; k < counter.length; k++)
		{
			yearLength = iterations * dt / (counter[k] / (2 * Math.PI));
			output3.println(yearLength + " " + counter[k] / (2 * Math.PI));
		}
		output1.close();
		output2.close();
		output3.close();
		input.close();
	}
}