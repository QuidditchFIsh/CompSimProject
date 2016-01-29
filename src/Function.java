
public class Function {

	
	public static void arrayUpdatePosition(Particle3D[] position, double dt, Vector3D[][] forceArray) {
		//Create a Vector3D representing total force on a particle due to all others
		Vector3D totalForce = new Vector3D();
		//Create a Vector3D array to keep a running total of the sum of the forces; the final element represents the total force on one particle
		Vector3D tempForce[] = new Vector3D[forceArray.length];
		int i;
		int j = 0;
		//Loop over particles and update position of each
		for (i = 0; i < forceArray[0].length; i++) {
			//Set the 0th element of the array to the 0th element of the ith row in the force array
			tempForce[0] = forceArray[i][0];
			
			//Loop over rows of force matrix to calculate total force on a particle
			for (j = 0; j < forceArray.length - 1; j++) {
				//Set the total force to the sum of the jth element of tempForce (i.e. the sum of all previous elements of the force array) and the next element of the force array
				totalForce = Vector3D.vectorAddition(tempForce[j],forceArray[i][j+1]);
				//Set the (j+1)th element of tempForce to the sum of forces 0 to j
				tempForce[j+1] = totalForce;
		}
			position[i].secondOrderPositionUpdate(dt, totalForce);
		}
	}