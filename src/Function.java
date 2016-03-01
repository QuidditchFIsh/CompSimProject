import java.io.PrintWriter;
import java.lang.Math;

public class Function {

	//THE BELOW CODE IS REDUDENT NOW WE ARE USING 1D ARRAYS
	
	/*public static Vector3D forceSum(Vector3D[][] forceArray, int i) {
				//Create a Vector3D representing total force on a particle due to all others
				Vector3D totalForce = new Vector3D();
				//Create a Vector3D array to keep a running total of the sum of the forces: the final element represents the total force on one particle
				Vector3D tempForce[] = new Vector3D[forceArray.length];
				//Set the 0th element of the array to the 0th element of the ith row in the force array
				tempForce[0] = forceArray[i][0];
				
				//Loop over rows of force matrix to calculate total force on a particle
				for (int j = 0; j < forceArray.length - 1; j++) {
					//Set the total force to the sum of the jth element of tempForce (i.e. the sum of all previous elements of the force array) and the next element of the force array
					totalForce = Vector3D.vectorAddition(tempForce[j],forceArray[i][j+1]);
					//Set the (j+1)th element of tempForce to the sum of forces 0 to j of the force array
					tempForce[j+1] = totalForce;
	}
	return tempForce[forceArray.length];
	}
	
			Vector3D totalForce = new Vector3D();
			//Loop over given row in force matrix (i.e. all forces acting on one particle) and sum using vector addition
			for (int j = 0; j < forceArray.length; j++) {
				totalForce = Vector3D.vectorAddition(totalForce, forceArray[i][j]);
			}
			return totalForce;
	}
*/
	public static void arrayUpdatePosition(Particle3D[] position, double dt, Vector3D[] forceArray) {
		
		
		//Loop over particles and update position of each
		for (int i = 0; i < forceArray.length; i++)
		{
			//force = forceSum(forceArray, i);
		
			position[i].secondOrderPositionUpdate(dt, forceArray[i]);
		}
	}
	
	public static void arrayUpdateVelocity(Particle3D[] velocity, double dt, Vector3D[] forceArray) {
		
		
		//Loop over particles and update velocity of each
		for (int i = 0; i < forceArray.length; i++) 
		{
			velocity[i].velocityUpdate(dt, forceArray[i]);
		}
	}
	
	public static double arrayTotalEnergy(Particle3D[] energy)
	{
		
		double totalKinetic = 0.0, totalGravitational = 0.0;
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
				double potential = Particle3D.GravitationalPotential(energy[j],energy[k]);
				totalGravitational += potential;
			}
		}
		return totalKinetic + totalGravitational;
		
	}
/*
 *not using 2d array anymore so class not needed
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
	*/
	public static void outputVMD(Particle3D[] particleArray,PrintWriter output1, int i)
	{
		int n = particleArray.length;
		
		
			output1.printf("%d \n",n);
			output1.printf("Point = %d \n",i);
			for ( int k=0;k<n;i++)
			{
				output1.print(particleArray[k].position);
			}
		
	}
	public static void arrayForceUpdate2(Particle3D[] particle, Vector3D[] forceArray)
	{
		
		Vector3D[] tempForceArray = new Vector3D[forceArray.length];
		for (int i=0; i<forceArray.length;i++)
			for ( int j=0; j<forceArray.length;j++)
				
			{
				if ( i!= j)
				{
					
					tempForceArray[i] = new Vector3D(Particle3D.GravitationalForce(particle[i], particle[j]));
					
					forceArray[i]= (Vector3D.vectorAddition(forceArray[i],tempForceArray[i] ));
					forceArray[i]= (forceArray[i].scalarDivide(2));
				}
			}
			
		
	}
	//Takes in the initial distance between Sun and body- use initial conditions?
	public static double perihelion(double initialDistance, Particle3D Sun, Particle3D orbit) {
		double[] peri = new double[2];
		peri[0] = initialDistance;
		peri[1] = Vector3D.vectorSubtraction(Sun.getPosition(),orbit.getPosition()).magnitude();
		if (peri[1] < peri[0]) 
		{
			peri[0] = peri[1];
		}
		return peri[0];
	}
	
	public static double aphelion(double initialDistance, Particle3D Sun, Particle3D orbit) {
		double[] ap = new double[2];
		ap[0] = initialDistance;
		ap[1] = Vector3D.vectorSubtraction(Sun.getPosition(),orbit.getPosition()).magnitude();
		if (ap[1] > ap[0])
		{
			ap[0] = ap[1];
		}
		return ap[0];
	}
	// Define arbitrary small delta as error in angle; double iteration represents number of iterations which have passed, i.e. if looping over i, iterations = i in ith loop
	//Returns -1 if year is not completed
	public static double yearLength(Vector3D initialSeparation, Particle3D Sun, Particle3D orbit, double dt, double iteration, double delta) {
		Vector3D separation = Vector3D.vectorSubtraction(Sun.getPosition(),orbit.getPosition());
		double dotProduct = Vector3D.dotProduct(initialSeparation,separation);
		dotProduct = dotProduct/(separation.magnitude()*initialSeparation.magnitude());
		if (1.0 - delta < dotProduct && dotProduct < 1.0 + delta) {
			return iteration*dt;
		}
		else return -1.0;
		}
	
}