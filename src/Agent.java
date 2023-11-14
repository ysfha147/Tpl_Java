import java.awt.*;
import java.util.Vector;


public class Agent extends Vector2d{
    private Vector2d acc;
    private Vector2d velocity;

    public Agent(int x, int y, int Vx, int Vy){
        super(x,y);
        this.velocity = new Vector2d(Vx, Vy);
        this.acc = new Vector2d();
    }


    public void updateAgent(Vector2d... Vectors){
        for (Vector2d v: Vectors){
            this.acc.add(v);
        }
//      this.acc.normalize();
        this.velocity.add(acc);
        this.add(velocity);
    }

    public Vector2d getVelocity() {
        return velocity;
    }


    public void LimiteVelocity(int Vlim){
        double magnitudeVelocity = this.velocity.getMagnitude();
        if (magnitudeVelocity > Vlim){
            this.velocity.multiply(Vlim/this.velocity.getMagnitude());
        }
    }

    public Vector2d BoundPosition(Point MinPoint, Point MaxPoint){
        Vector2d v = new Vector2d(0,0);

        if (this.getX() < MinPoint.getX()){
            v.setX(10);
        }
        else if (this.getX() > MaxPoint.getX()) {
            v.setX(-10);
        }
        if (this.getY() < MinPoint.getY()){
            v.setY(10);
        }
        else if (this.getY()> MaxPoint.getY()){
            v.setY(-10);
        }
        return v;
    }

    public void setVelocity(Vector2d velocity) {
        this.velocity = velocity;
    }

}
