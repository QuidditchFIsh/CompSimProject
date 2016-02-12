/**
 * A class for 3D vectors, with methods for addition, subtraction, magnitude, and products.
 * @author S. Rigby
 * @author A. Baker
 * @version 10/2015
 */

public class Vector3D 
{
	

    private double x=0;
    private double y=0;
    private double z=0;

/**Returns the values of each of the components of the vector.
 */
    public double getX() {
    	return x;
    }
    public double getY()
    {
    	return y;
    }
    public double getZ() 
    {
    	return z;
    }
/**Sets the values of the components of the vector to a given value.
  * @param xx The x component
  * @param yy The y component
  * @param zz The z component
 */

    public void setX(double xx) 
    {
    	x = xx;
    }
    public void setY(double yy)
    {
    	y = yy;
    }
    public void setZ(double zz)
    {
    	z = zz;
    }
/**Sets the components of the vector to zero.
 */
    public Vector3D () 
    {
	x = 0.0;
	y = 0.0;
	z = 0.0;
    }
/**Initialises the vector to (xx,yy,zz)
 * 
 * @param xx The x component
 * @param yy The y component
 * @param zz The z component
 */
    public Vector3D (double xx,double yy,double zz) 
    {
	x = xx;
	y = yy;
	z = zz;
    }
/**Creates a copy of a vector.
 * 
 * @param original The vector to be copied
 */
	public Vector3D (Vector3D  original)
	{
		x = original.getX();
		y = original.getY();
		z = original.getZ();
	}
/**Returns the magnitude squared of the vector.
 * 	
 * @return (x^2 + y^2 + z^2)
 */
    public double magnitudeSquared()
    {
    	double mod2;
    	mod2 = Math.pow(this.getX(),2) + Math.pow(this.getY(),2) + Math.pow(this.getZ(),2);
    	return mod2;
    }
/**Returns the magnitude of the vector.
 *     
 * @return sqrt(x^2 + y^2 + z^2)
 */
    public double magnitude()
    {
    	return Math.sqrt(magnitudeSquared());
    }
 /**Returns the components of the vector.
  * 
  * @return (x,y,z)
  */
    public String toString()
    	{
    	return "(" + x + ", " + y + ", " + z + ")";
    	}
    
 /**Multiplies a vector by a scalar.
  *    
  * @param a The vector
  * @param b The scalar
  * @return a*b
  */
	public Vector3D  scalarMultiply(double b)
{
		return new Vector3D (getX()*b, getY()*b, getZ()*b);
	}
/**Divides a vector by a scalar.
 * 
 * @param a The vector
 * @param b The scalar
 * @return a/b
 */
	public Vector3D  scalarDivide(double b) 
	{
		return scalarMultiply(1/b);
	}
/**Adds two vectors.
 * 
 * @param a The first vector.
 * @param b The first vector.
 * @return The sum, a+b
 */
	public static  Vector3D  vectorAddition(Vector3D  a, Vector3D  b) 
	{
		return new Vector3D (a.getX() + b.getX(), a.getY() + b.getY(), a.getZ() + b.getZ());
	}
/**Subtracts one vector from another.
 * 	
 * @param a The first vector.
 * @param b The second vector, which is subtracted from the first.
 * @return The difference, a-b
 */
	public static Vector3D  vectorSubtraction(Vector3D  a, Vector3D  b)
	{
		return vectorAddition(a,b.scalarMultiply(-1));
	}
	
/**Takes the cross product of two vectors.
 *    		
 * @param a The first vector in the cross product.
 * @param b The second vector in the cross product.
 * @return axb
 */
    public static Vector3D  crossProduct(Vector3D  a, Vector3D  b) 
    {
    	return new Vector3D (a.getY()*b.getZ() - a.getZ()*b.getY(), a.getZ()*b.getX() - a.getX()*b.getZ(), a.getX()*b.getY() - a.getY()*b.getX());
    }
/**Takes the dot product of two vectors.
 *     
 * @param a The first vector in the product.
 * @param b The second vector in the product.
 * @return a.b
 */
    public static double dotProduct(Vector3D  a, Vector3D  b)
    {
    	double dot;
    	dot = (a.getX()*b.getX() + a.getY()*b.getY() + a.getZ()*b.getZ());
    	return dot;
    }
    	
    public static boolean compareVectors(Vector3D a, Vector3D b)
    	{
    	if (a.getX() == b.getX() && a.getY() == b.getY() && a.getZ() == b.getZ())
    		return true;
    	else 
    		return false;
    }
}
    	

	 
      
