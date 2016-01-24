/** A class for particles moving in 3 dimensions, with methods for reading a particle from a scanner, returning kinetic energy, finding relative separation, and updating position and velocity.
 * @author A. Baker
 * @author S. Rigby
 */
 
import java.util.Scanner;

public class Particle3D 
{
 
    /**These are the constructors for this class.
     */
    // All of the values of these variables have been set to NaN to show that they have not yet been initalised
     Vector3D position;
     Vector3D velocity;
     
     private double mass;
    
    // This String will hold the label of the particle in the simulation 
    String name = new String();
    
    // This constructor will initalise the Particle3D into the state where its position and velocity are both the zero vector
    public Particle3D()
    {
        position.setX(0);
        position.setY(0);
        position.setZ(0);
        velocity.setX(0);
        velocity.setY(0);
        velocity.setZ(0);
    }
    
    //This constructor will set the particle properties to the given arguments.
    public Particle3D(Vector3D pos, Vector3D vel, double mass, String name)
    {
        position.setX(pos.getX());
        position.setY(pos.getY());
        position.setZ(pos.getZ());
        
        velocity.setX(vel.getX());
        velocity.setY(vel.getY());
        velocity.setZ(vel.getZ());
        
        this.mass=mass;
        this.name=name;
    }
    
    /*Set and Get methods for the above vectors and the properties of the particle. 
     *vector3D methods can be used as getters for components x, y, z.
     */
    public Vector3D getPosition()
    {
        return position;
    }
    
    public Vector3D getVelocity()
    {
        return velocity;
    }
    
    public double getMass()
    {
        return mass;
    }
    
    
   
    public void setPosition(double x,double y,double z)
    {
        position.setX(x);
        position.setY(y);
        position.setZ(z);
    }
    
    public void setVelocity(double x,double y,double z)
    {
        velocity.setX(x);
        velocity.setY(y);
        velocity.setZ(z);
    }
    
    public void setMass(double mass)
    {
        this.mass = mass;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**General particle methods
     */
    //toString Method to print out the states of the particle in a readable format, namely:
    //<label> <PosX> <PosY> <PosZ>
    public String toString()
    {
        return name + position.getX() + ", " + position.getY() + ", " + position.getZ();
    }
    
  //Method to read parameters from an input file and assign them to a particle
    public void readScanner(Scanner input)
    {
        position.setX(input.nextDouble());
        position.setY(input.nextDouble());
        position.setZ(input.nextDouble());
        velocity.setX(input.nextDouble());
        velocity.setY(input.nextDouble());
        velocity.setZ(input.nextDouble());
        mass=input.nextDouble();
        name = input.next();
    }
   
    /**Physical methods to represent the motion
     */
    //Method to return a particle's kinetic energy
    public double kineticEnergy()
    {
        return 0.5 * mass * velocity.magnitudeSquared();
    }
    
    //Method to return the gravitational potential energy betwen two particles
    public static double GravitationalPotential(Particle3D big,Particle3D small)
    {
        double R2 = small.particleDistance(small,big);
        return - small.getMass()*big.getMass() * 1/R2;
    }
    
    //Method to update the velocity using a given timestep dt and the force vector
    public void velocityUpdate(double dt, Vector3D force)
    {
        velocity.addScaledVector(force, dt/mass);         
    }
    
    //Method to update the position to first order terms in the Taylor Expansion
    public void positionUpdate(double dt)
    {
        position.addScaledVector(velocity, dt);         
    }
    
    //Method to update the position to second order terms in the Taylor Expansion
    public void secondOrderPositionUpdate(double dt, Vector3D force)
    {
        position.addScaledVector(velocity, dt);
        position.addScaledVector(force, Math.pow(dt, 2)/(2*mass));         
    }
    
    //Static method to find the distance between two particles
    public double particleDistance(Particle3D a, Particle3D b)
    {
    	return magnitude(vectorSubtraction(a.getPosition(),b.getPosition()));
    }
    
    
    //Method to return the gravitational force vector acting on the orbiting particle, labelled here as 'small', due to the central particle, labelled here as 'big'
    public static Vector3D GravitationalForce(Particle3D small, Particle3D big)
    {
        //Calculates the modulus squared of the distance between the particles
        double R2 = small.particleDistance(small,big);
        R2=Math.pow(R2, 2);
        Vector3D a = small.getPosition();
        Vector3D b = big.getPosition();
        //Computes the unit vector between the particles
        Vector3D rHat = vectorSubtraction(a,b);
        rHat.scalarMultiply(1/Math.sqrt(R2));
        //Computes the gravitational force vector by multiplying the unit vector by the magnitude		
        rHat.scalarMultiply((big.getMass()*small.getMass()*(-1/R2)));      
        return rHat;
    }
 
    
}
