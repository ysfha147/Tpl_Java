import java.awt.*;
import java.util.Vector;

// Represents a boid agent with position and velocity.
public class Agent extends Vector2d{
    private Vector2d acc;
    private Vector2d velocity;

    private int limitVelocity;

    public Agent(double  x, double y, double Vx, double Vy, int limitVelocity){
        super(x,y);
        this.velocity = new Vector2d(Vx, Vy);
        this.acc = new Vector2d();
        this.limitVelocity = limitVelocity;
    }

    public int getLimitVelocity() {
        return limitVelocity;
    }

    public void updateAgent(Vector2d... Vectors){
        this.acc = new Vector2d(0,0);
        for (Vector2d v: Vectors){
            this.acc.add(v);
        }
       // this.acc.normalize();
        this.velocity.add(acc);

        this.LimiteVelocity(limitVelocity);
        this.add(velocity);
    }


    public Vector2d getVelocity() {
        return new Vector2d(this.velocity.getX(), this.velocity.getY());
    }


    public void LimiteVelocity(int Vlim){
        double magnitudeVelocity = this.velocity.getMagnitude();
        if (magnitudeVelocity > Vlim){
            this.velocity.multiply(Vlim/this.velocity.getMagnitude());
        }
    }



    public void setVelocity(Vector2d velocity) {
        this.velocity = velocity;
    }

}
