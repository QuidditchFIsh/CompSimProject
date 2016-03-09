import java.io.PrintWriter;
import java.lang.Math;

public class Function 
{

	
	public static void arrayUpdatePosition(Particle3D[] position, double dt, Vector3D[] forceArray) {
		
		int i;
		//Loop over particles and update position of each
		for (i = 0; i < forceArray.length; i++) {		
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
	
	public static double arrayTotalEnergy(Particle3D[] energy) {
		int i;
		double totalKinetic = 0.0;
		//Loop over particles and sum their kinetic energies
		for (i = 0; i < energy.length; i++) {
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
		if (peri[1] < peri[0]) {
			peri[0] = peri[1];
		}
		return peri[0];
	}
	
	public static double aphelion(double initialDistance, Particle3D Sun, Particle3D orbit) {
		double[] ap = new double[2];
		ap[0] = initialDistance;
		ap[1] = Vector3D.vectorSubtraction(Sun.getPosition(),orbit.getPosition()).magnitude();
		if (ap[1] > ap[0]) {
			ap[0] = ap[1];
		}
		return ap[0];
	}
	
	
	public static void yearCounter(Vector3D preSeparation, Particle3D Sun, Particle3D orbit,double counter)
	{
		Vector3D separation = Vector3D.vectorSubtraction(Sun.getPosition(),orbit.getPosition());
		double dotProduct = Vector3D.dotProduct(preSeparation,separation);
		dotProduct = dotProduct/(separation.magnitude()*preSeparation.magnitude());
		counter += Math.acos(dotProduct)/(2*Math.PI);
	}
	public static void adjustMomentumOfSystem ( Particle3D[] particleArray)
	{
		double mass=0;
		Vector3D momentum = new Vector3D();
		@SuppressWarnings("unused")
		Vector3D velocity = new Vector3D();
		for(int i=0;i<particleArray.length;i++ )
		{
			mass += particleArray[i].getMass();
			momentum = new Vector3D(Vector3D.vectorAddition(momentum,momentum.scalarMultiply(particleArray[i].getMass())));
		}
		momentum = momentum.scalarDivide(mass);
		for( int i=0;i<particleArray.length;i++)
		{
			velocity = Vector3D.vectorSubtraction(particleArray[i].getVelocity(),momentum );
		}
		
	}
		

	
}
	
