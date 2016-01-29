import java.lang.Math;
import java.util.Scanner;
import java.io.*;
public class Particle3DArrayVerlet 
{

	public static void main(String[] argv) throws FileNotFoundException 
	{
		// Identify a file from the comand line and use it to create the particles.
		File file = new File(argv[0]);
		Scanner input = new Scanner(file);
		// input the data from an input file 
		for (int i=0;i<noObjects;i++)
		{
		particleArray[i].setPosition(input.nextDouble(), input.nextDouble(), input.nextDouble());
		particleArray[i].setVelocity(input.nextDouble(), input.nextDouble(), input.nextDouble());
		particleArray[i].setMass(input.nextDouble());
		particleArray[i].setName(input.next());
			
		}
		
		
	}
	
	

}
