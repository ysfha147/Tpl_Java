import java.awt.*;
import java.security.PublicKey;
import java.util.function.Function;

public class Agent extends Point{
    private Point position;
    private Point speed;
    private Point acc;

    public Agent(int x, int y, int Vx, int Vy){
        super(x,y);
        this.speed = new Point(Vx, Vy);
        this.acc = new Point(0,0);
    }

    public void setAcc(Point acc) {
        this.acc = acc;
    }

    public Point getSpeed() {
        return speed;
    }

    public void setSpeed(Point speed) {
        this.speed = speed;
    }
}
