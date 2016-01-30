
public class Function {

	
	public static Vector3D forceSum(Vector3D[][] forceArray, int i) {
			/**	//Create a Vector3D representing total force on a particle due to all others
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
	*/
			Vector3D totalForce = new Vector3D();
			//Loop over given row in force matrix (i.e. all forces acting on one particle) and sum using vector addition
			for (int j = 0; j < forceArray.length; j++) {
				totalForce = Vector3D.vectorAddition(totalForce, forceArray[i][j]);
			}
			return totalForce;
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

	public void arrayUpdateForce(Particle3D[] particles, Vector3D[][] forceArray) {
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
	
}