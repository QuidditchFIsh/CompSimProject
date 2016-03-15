import java.util.*;
import java.io.*;
public class Particle3DArrayVerlet 
{

	public static void main(String[] argv) throws IOException, FileNotFoundException 
	{
		System.out.println("Begin");
		
		//Read the name of an input file from the command line
		File file = new File(argv[0]);
		//Create a scanner object to read the data from the input file
		Scanner input = new Scanner(file);
		
		//Create output files with the names given in the command line
		String outfile1 = new String(argv[1]);
		String outfile2 = new String (argv[2]);
		String outfile3 = new String (argv[3]);
		PrintWriter output1 = new PrintWriter (new FileWriter(outfile1));
		PrintWriter output2 = new PrintWriter(new FileWriter(outfile2));
		PrintWriter output3 = new PrintWriter(new FileWriter(outfile3));
		
		//Read the parameters of the system from the input file
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
		//Create an array of doubles to find the perihelion
		double[] perihelion = new double[particleArray.length - 1];
		//Create an array of doubles to find the aphelion
		double[] aphelion = new double[particleArray.length - 1];
		//Create a double which will store the value of the length of a year for each orbiting particle
		double yearLength = 0;
		
		//Create a Vector3D array to hold the initial positions of the particles
		Vector3D[] initPos = new Vector3D[n];
		//Create and initialise two force arrays, for use in the velocity update method
		Vector3D[] force = new Vector3D[n];
		//preForce will be used to store the forces from the previous iteration
		Vector3D[] preForce = new Vector3D[n];
		for (int i = 0; i < n; i++)
		{
			initPos[i] = new Vector3D(particleArray[i].position);
			force[i] = new Vector3D();
			preForce[i] = new Vector3D();
		}
		
		//Calculate the initial forces acting on each particle
		Function.arrayForceUpdate(particleArray, force, preForce);
		for(int j = 0; j < n; j++)
		{
			preForce[j] = new Vector3D(force[j]);
		}
		
		//Adjust the momentum of the system to prevent the centre of mass from drifting
		Function.adjustMomentumOfSystem(particleArray);
		//Calculate the initial energy of the system
		double initalEnergy = Function.arrayTotalEnergy(particleArray);
		//Write the initial positions of the particles to the first output file
		Function.outputVMD(particleArray, output1, 0);
		
		//Perform the main loop over each particle, following the Verlet Integration Scheme to compute the trajectories, as well as energies, perihelia and aphelia
		for (int i = 0; i < iterations; i++)
		{
			//Update the position of each particle
			Function.arrayUpdatePosition(particleArray, dt, force);
			//Update the force acting on each particle
			Function.arrayForceUpdate(particleArray, force, preForce);
			//Update the velocity of each particle
			Function.arrayUpdateVelocity(particleArray, dt, force, preForce);
			//Set the elements of preForce
			for(int j = 0; j < n; j++)
			{
				preForce[j] = new Vector3D(force[j]);
			}
			//Write the positions of each particle to the first output file to be read by VMD
			Function.outputVMD(particleArray, output1, i + 1);
			
			//Write the initial energy and the energy fluctuation - the difference between energy in this iteration and initial energy - to the second output file
			output2.println(initalEnergy  + " " + (Function.arrayTotalEnergy(particleArray) - initalEnergy));
			//Loop over the particles and count the number of years that have passed
			//Start from 1 so that the Sun is not included
			for (int j = 1; j < particleArray.length; j++)
				{
					counter[j - 1] += Function.yearCounter(Vector3D.vectorSubtraction(particleArray[0].getPosition(), initPos[j]), particleArray[0], particleArray[j]);
					initPos[j] = new Vector3D(particleArray[j].position);
					//LOOK AT HOLDING THE FORCE IN THE PARTICLE ARRAY
					perihelion[j - 1] = Function.perihelion(initPos[j].magnitude(), particleArray[0], particleArray[j]);
					aphelion[j - 1] = Function.aphelion(initPos[j].magnitude(), particleArray[0], particleArray[j]);
				}
		}
		
		//Write the average length of a year, the total number of orbits completed, the perihelion distance, and the aphelion distance for each particle to the third output file 
		output3.printf("Average Year Length, total number of years, perihelion, and aphelion \n ============================ \n");
		for (int k = 0; k < counter.length; k++)
		{
			yearLength = iterations * dt / (counter[k]);
			output3.println(yearLength + " " + counter[k] + " " + perihelion[k] + " " + aphelion[k]);
		}
		output1.close();
		output2.close();
		output3.close();
		input.close();
	}
}