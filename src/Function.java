
public class Function {

	
	public static Vector3D forceSum(Vector3D[][] forceArray, int i) {
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
	
	public static void arrayUpdatePosition(Particle3D[] position, double dt, Vector3D[][] forceArray) {
		
		int i;
		Vector3D force = new Vector3D();
		//Loop over particles and update position of each
		for (i = 0; i < forceArray[0].length; i++) {
			force = forceSum(forceArray, i);
		
			position[i].secondOrderPositionUpdate(dt, force);
		}
	}
	
	public static void arrayUpdateVelocity(Particle3D[] velocity, double dt, Vector3D[][] forceArray) {
		
		int i;
		Vector3D force = new Vector3D();
		//Loop over particles and update velocity of each
		for (i = 0; i < forceArray[0].length; i++) {
			force = forceSum(forceArray, i);
		
			velocity[i].velocityUpdate(dt, force);
		}
	}
	
	public double arrayTotalEnergy(Particle3D[] energy) {
		int i;
		double totalKinetic;
		double[] tempKinetic = new double[energy.length];
		tempKinetic[0] = energy[0].kineticEnergy();
		for (i = 0; i < energy.length - 1; i++) {
			totalKinetic = tempKinetic[i] + energy[i+1].kineticEnergy();
			tempKinetic[i+1] = totalKinetic;
		}
		//Only returning this to stop eclipse from complaining
		return tempKinetic[energy.length];
	}
	
	
	
	
	
	
}