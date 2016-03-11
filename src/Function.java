import java.io.PrintWriter;
import java.lang.Math;

public class Function 
{

	//Method to update the position of each particle using up to the quadratic term in dt in the Taylor expansion of x(t+dt)
	public static void arrayUpdatePosition(Particle3D[] position, double dt, Vector3D[] forceArray)
	{
		
		//Loop over particles and update position of each
		for (int i = 0; i < forceArray.length; i++) {		
			position[i].secondOrderPositionUpdate(dt, forceArray[i]);
		}
	}
	
	//Method to update the velocity of each particle by using the sum of all forces acting on the particle
	public static void arrayUpdateVelocity(Particle3D[] velocity, double dt, Vector3D[] forceArray)
	{
				
		//Loop over particles and update velocity of each
		for (int i = 0; i < forceArray.length; i++) 
		{
			velocity[i].velocityUpdate(dt, forceArray[i]);
		}
	}
	
	//Method to calculate the total energy of the system, by summing the kinetic and gravitational potential energies of each particle
	public static double arrayTotalEnergy(Particle3D[] energy)
	{
		double totalKinetic = 0.0;
		//Loop over particles and sum their kinetic energies
		for (int i = 0; i < energy.length; i++) {
			totalKinetic = totalKinetic + energy[i].kineticEnergy();
		}
		double totalGravitational = 0.0;
		//Loop over pairs of particles and sum their potential energies
		for (int j = 0; j < energy.length; j++) {
			for (int k = j + 1; k < energy.length; k++) {
				double potential = Particle3D.GravitationalPotential(energy[j],energy[k]);
				totalGravitational = totalGravitational + potential;
			}
		}
		return totalKinetic + totalGravitational;
	}
	
	//NYE do we need both of the array force methods? Because we don't use this one
	//If not let's rename the other one arrayForceUpdate
	public static void arrayUpdateForce(Particle3D[] particles, Vector3D[][] forceArray)
	{
		Vector3D[][] tempForceArray = new Vector3D[forceArray.length][forceArray.length];
		for (int i = 0; i < forceArray.length; i++) {
			for (int j = i + 1; j < forceArray.length; j++) {
				tempForceArray[i][j] = Particle3D.GravitationalForce(particles[i],particles[j]);
				forceArray[i][j] = Vector3D.vectorAddition(forceArray[i][j], tempForceArray[i][j]);
				forceArray[i][j] = forceArray[i][j].scalarDivide(2);
			}
			for (int k = 0; k < i; k++) {
				forceArray[i][k] = forceArray[k][i].scalarMultiply(-1);
			}
		}
	}
	
	//Method to output the positions of each particle after a given iteration i to an output file
	public static void outputVMD(Particle3D[] particleArray, PrintWriter output1, int i)
	{
		int n = particleArray.length;
		
			output1.printf("%d \n",n);
			output1.printf("Point = %d \n",i);
			for (int k = 0; k < n; i++)
			{
				output1.print(particleArray[k].position);
			}
	}
	
	//Method to update the force acting on each particle due to every other particle in the force array, using the average of F(t) and F(t+dt)
	public static void arrayForceUpdate2(Particle3D[] particle, Vector3D[] forceArray)
	{
		Vector3D[] tempForceArray = new Vector3D[forceArray.length];
		for (int i = 0; i < forceArray.length; i++)
			for (int j = 0; j < forceArray.length; j++)
				
			{
				if (i != j)
				{
					
					tempForceArray[i] = new Vector3D(Particle3D.GravitationalForce(particle[i], particle[j]));
					
					forceArray[i]= (Vector3D.vectorAddition(forceArray[i],tempForceArray[i]));
					forceArray[i]= (forceArray[i].scalarDivide(2));
				}
			}
	}
	
	//Method to find the perihelion of a given particle
	public static double perihelion(double initialDistance, Particle3D Sun, Particle3D orbit)
	{
		double[] peri = new double[2];
		peri[0] = initialDistance;
		peri[1] = Vector3D.vectorSubtraction(Sun.getPosition(), orbit.getPosition()).magnitude();
		if (peri[1] < peri[0]) {
			peri[0] = peri[1];
		}
		return peri[0];
	}
	
	//Method to find the aphelion of a given particle
	public static double aphelion(double initialDistance, Particle3D Sun, Particle3D orbit)
	{
		double[] ap = new double[2];
		ap[0] = initialDistance;
		ap[1] = Vector3D.vectorSubtraction(Sun.getPosition(), orbit.getPosition()).magnitude();
		if (ap[1] > ap[0]) {
			ap[0] = ap[1];
		}
		return ap[0];
	}
	
	//Method to count the number of orbit that a particle undergoes during the simulation by calculating the fraction of a year that passes with each iteration
	public static void yearCounter(Vector3D preSeparation, Particle3D Sun, Particle3D orbit, double counter)
	{
		Vector3D separation = Vector3D.vectorSubtraction(Sun.getPosition(), orbit.getPosition());
		double dotProduct = Vector3D.dotProduct(preSeparation, separation);
		dotProduct = dotProduct/(separation.magnitude() * preSeparation.magnitude());
		counter += Math.acos(dotProduct)/(2 * Math.PI);
	}
	
	//Method to adjust the momentum of the system to prevent the centre of mass from drifting
	public static void adjustMomentumOfSystem (Particle3D[] particleArray)
	{
		double mass = 0;
		Vector3D momentum = new Vector3D();
		@SuppressWarnings("unused")
		Vector3D velocity = new Vector3D();
		for(int i = 0; i < particleArray.length; i++)
		{
			mass += particleArray[i].getMass();
			//NYE: Why do you multiply momentum by mass here?
			//Also I think this line wins the prize for 'Most brackets in a row'
			momentum = new Vector3D(Vector3D.vectorAddition(momentum, momentum.scalarMultiply(particleArray[i].getMass())));
		}
		momentum = momentum.scalarDivide(mass);
		for(int i = 0; i < particleArray.length; i++)
		{
			velocity = Vector3D.vectorSubtraction(particleArray[i].getVelocity(), momentum);
		}
	}
}