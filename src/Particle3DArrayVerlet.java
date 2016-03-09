import java.util.Scanner;
import java.io.*;
public class Particle3DArrayVerlet 
{

	public static void main(String[] argv) throws IOException,FileNotFoundException 
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
		int n = input.nextInt();
		double dt = input.nextDouble();
		int iterations = input.nextInt();
		// n will be the number of particles we are dealing with in this simulation
		// dt is the the time step and iterations is the number of iterations which this 
		// simulation will run for
		Particle3D[] particleArray = new Particle3D[n];
		//Don't want to include Sun so length of counter array is one less than length of particle array
		double[] counter = new double[particleArray.length - 1];
		// create an array of particle
		Vector3D initPos= new Vector3D();
		Vector3D initVel = new Vector3D();
		for (int i=0;i<particleArray.length;i++)
		{
		initPos.setX(input.nextDouble());
		initPos.setY(input.nextDouble());
		initPos.setZ(input.nextDouble());
		initVel.setX(input.nextDouble());
		initVel.setY(input.nextDouble());
		initVel.setZ(input.nextDouble());
		Particle3D temp = new Particle3D(initPos,initVel,input.nextDouble(),input.next());
		particleArray[i]= new Particle3D(temp);
		System.out.println(particleArray[i]);

		}
		// set the particles using the input
		Vector3D[] force = new Vector3D[n];
		for (int i=0; i<n;i++)
		{
			force[i] = new Vector3D();
		}
		// initalise the force array
		//create 3 vector3D arrays for the Verlet time intergrator
		Function.arrayForceUpdate2(particleArray,force);
		double initalEnergy = Function.arrayTotalEnergy(particleArray);
		// starts the algorithm off by calculating the forces on the planets at the starts
		Function.adjustMomentumOfSystem(particleArray);
		//To stop the COM of the simulation from drifting we have to adjust the COM of the system 
		for ( int i=0 ;i < iterations;i++)
		{
			Function.arrayUpdatePosition(particleArray, dt, force);
			// update all the positions of the particle array using functions from the function class
			Function.arrayForceUpdate2(particleArray,force);
			// updates the force array using the verlet algorithm
			Function.arrayUpdateVelocity(particleArray, dt, force);
			//updates the velocity of the particles.
			Function.outputVMD( particleArray, output1,i);
			// This method will write the results to the output file output1 in a format
			//Which can be read by VMD.
			output2.println(initalEnergy - Function.arrayTotalEnergy(particleArray));
			// output the difference in energy.	
	
			//Start from 1 because we don't want to measure the length of the Sun's year			
			for (int j = 1; j < particleArray.length; j++)
				{
					Function.yearCounter(initPos, particleArray[0], particleArray[j], counter[j]);
				}
			
			
		}
		double yearLength = 0;
		output3.printf("Average Year Length and Total number of years \n ============================ \n");
		for( int k =0;k<counter.length;k++)
		{
			yearLength = iterations*dt/(counter[k]/(2*Math.PI));
			output3.println(yearLength + " " + counter[k]/(2*Math.PI));
		}
		output1.close();
		output2.close();
		output3.close();
		input.close();
		
		
		
		
		
	}
	
	

}
