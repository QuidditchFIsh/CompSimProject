import java.io.PrintWriter;
import java.lang.Math;

public class Function 
{
	//Method to update the position of each particle, using up to the quadratic term in dt in the Taylor expansion of x(t+dt)
	public static void arrayUpdatePosition(Particle3D[] position, double dt, Vector3D[] forceArray)
	{
		//Loop over particles and update position of each
		for (int i = 0; i < forceArray.length; i++)
		{
			position[i].secondOrderPositionUpdate(dt, forceArray[i]);
		}
	}
	
	//Method to update the velocity of each particle, using the sum of all forces acting on the particle
	public static void arrayUpdateVelocity(Particle3D[] velocity, double dt, Vector3D[] forceArray, Vector3D[] preForce)
	{
				
		//Loop over particles and update velocity of each
		for (int i = 0; i < forceArray.length; i++) 
		{
			Vector3D temp = new Vector3D(Vector3D.vectorAddition(forceArray[i], preForce[i]));
			velocity[i].velocityUpdate(dt, temp.scalarDivide(2));
		}
	}
	private static double totalKinetic = 0.0;
	private static double totalGravitational = 0.0;
	//Method to calculate the total energy of the system, by summing the kinetic and gravitational potential energies of each particle
	public static double arrayTotalEnergy(Particle3D[] energy)
	{
		totalKinetic =0;
		totalGravitational=0;
		//Loop over particles and sum their kinetic energies
		for (int i = 0; i < energy.length; i++) 
		{
			totalKinetic += energy[i].kineticEnergy();
		}
		//Loop over pairs of particles and sum their potential energies
		for (int j = 0; j < energy.length; j++)
		{
			for (int k = j + 1; k < energy.length; k++)
			{
				totalGravitational += Particle3D.GravitationalPotential(energy[j], energy[k]);
			}
		}
		//Return the sum
		return totalKinetic + totalGravitational;
	}
	
	//Method to output the positions of each particle after a given iteration i to an output file
	public static void outputVMD(Particle3D[] particleArray, PrintWriter output1, int i)
	{
		int n = particleArray.length;
			//Write the number of particles to the file
			output1.printf("%d \n", n);
			//Write the iteration number to the file
			output1.printf("Point = %d \n", i);
			//Loop over the particles and print the name and position of each to the file
			for (int k = 0; k < n; k ++)
			{
				output1.printf("%s ", particleArray[k].name);
				output1.printf("%.6g %.6g %.6g \n", particleArray[k].position.getX(), particleArray[k].position.getY(), particleArray[k].position.getZ());
			}
	}
	
	//Method to update the force acting on each particle by looping over every other particle in the force array
	public static void arrayForceUpdate(Particle3D[] particle, Vector3D[] forceArray, Vector3D[] preForceArray)
	{
		Vector3D[][] tempForceArray = new Vector3D[forceArray.length][forceArray.length];
		//Loop over each particle i
		for (int i = 0; i < forceArray.length; i++)
		{
			forceArray[i] = new Vector3D();
			//Update the forces on particle i due to each particle j
			for (int j = 0; j < forceArray.length; j++)
			{
				if (i != j)
				{
					if(i<j)
					{
						tempForceArray[i][j] = new Vector3D(Particle3D.GravitationalForce(particle[i], particle[j]));
						forceArray[i] = (Vector3D.vectorAddition(forceArray[i], tempForceArray[i][j]));
					}
					if(i>j)
					{
						
						forceArray[i] = Vector3D.vectorAddition(forceArray[i], tempForceArray[j][i].scalarMultiply(-1));
					}
				}
			}
		}
	}
	
	//Method to find the perihelion of a given particle
	public static double perihelion(double initialDistance, Particle3D Sun, Particle3D orbit)
	{
		double[] peri = new double[2];
		//Zeroth element represents the shortest distance between a particle and the Sun so far
		peri[0] = initialDistance;
		//First element represents the distance between the particle and the Sun in the given iteration
		peri[1] = Vector3D.vectorSubtraction(Sun.getPosition(), orbit.getPosition()).magnitude();
		//Compare the zeroth and first elements and replace the zeroth with the first if the first is shorter
		if (peri[1] < peri[0]) 
		{
			peri[0] = peri[1];
		}
		return peri[0];
	}
	
	//Method to find the aphelion of a given particle
	//As in the perihelion method, but compare to see if the first element is longer
	public static double aphelion(double initialDistance, Particle3D Sun, Particle3D orbit)
	{
		double[] ap = new double[2];
		ap[0] = initialDistance;
		ap[1] = Vector3D.vectorSubtraction(Sun.getPosition(), orbit.getPosition()).magnitude();
		if (ap[1] > ap[0])
		{
			ap[0] = ap[1];
		}
		return ap[0];
	}
	private static double dotProduct =0;
	//Method to count the number of orbits that a particle undergoes during the simulation by calculating the fraction of a year that passes with each iteration
	public static double yearCounter(Vector3D preSeparation, Particle3D Sun, Particle3D orbit)
	{
		//The vector separating the Sun and the orbiting particle in the current iteration
		Vector3D separation = Vector3D.vectorSubtraction(Sun.getPosition(), orbit.getPosition());
		//Take the dot product of the above with the vector separating them in the previous iteration
		dotProduct = Vector3D.dotProduct(preSeparation, separation);
		//Use a.b = |a||b|cos(theta) and divide by the magnitudes to find the cosine of the angle between the vectors
		dotProduct = dotProduct / (separation.magnitude() * preSeparation.magnitude());
		//Take the arccosine of dotProduct to find the angle theta, and then divide by 2Pi to give the fraction of an orbit that passed over this iteration 
		return (Math.acos(dotProduct) / (2 * Math.PI));
	}
	
	//Method to adjust the momentum of the system to prevent the centre of mass from drifting
	private static double mass =0;
	public static void adjustMomentumOfSystem (Particle3D[] particleArray)
	{
		mass = 0;
		Vector3D momentum = new Vector3D();
		//Sum the masses and momenta of all particles
		for(int i = 0; i < particleArray.length; i++)
		{
			mass += particleArray[i].getMass();
			momentum = new Vector3D(Vector3D.vectorAddition(momentum, particleArray[i].getVelocity().scalarMultiply(particleArray[i].getMass())));
		}
		//Divide the total momentum by the total mass to give the centre of mass velocity
		momentum = momentum.scalarDivide(mass);
		//Adjust the velocity of each particle by the negative of the CoM velocity
		for(int i = 0; i < particleArray.length; i++)
		{
			particleArray[i].setVelocity(Vector3D.vectorSubtraction(particleArray[i].getVelocity(), momentum));
		}
	}
}