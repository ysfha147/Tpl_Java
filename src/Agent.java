import java.awt.*;
import java.util.Vector;


public class Agent extends Vector2d{
    private Vector2d acc;
    private Vector2d velocity;
    private Vector2d velocityOrientation = new Vector2d(1,1);

    public Agent(int x, int y, int Vx, int Vy){
        super(x,y);
        this.velocity = new Vector2d(Vx, Vy);
        this.acc = new Vector2d();
    }



    public void updateAgent(Vector2d... Vectors){
        for (Vector2d v: Vectors){
            this.acc.add(v);
        }
       // this.acc.normalize();
        this.velocity.add(acc);

        this.LimiteVelocity(10);
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

//    public void BoundPosition(Point MinPoint ,Point MaxPoint){
//        Vector2d v = new Vector2d(0,0);
//        Vector2d xAxisNormalVectorUP = new Vector2d(1,0);
//        Vector2d xAxisNormalVectorDown = new Vector2d(-1,0);
//        Vector2d yAxisNormalVectorLeft = new Vector2d(0,1);
//        Vector2d yAxisNormalVectorRight = new Vector2d(0,-1);
//
//        //30 is the radius
//        if (this.getX() - 30 < MinPoint.getX()){
////            double dotProduct = velocity.dotProduct(xAxisNormalVectorUP);
////            this.velocity.setX(velocity.getX() - 2*dotProduct*xAxisNormalVectorUP.getX());
////            this.velocity.setY(velocity.getY() - 2*dotProduct*xAxisNormalVectorUP.getY());
////            this.add(velocity);
//            velocityOrientation.setX(-velocityOrientation.getX());
//            this.velocity.setX(-this.velocity.getX());
//            this.add()
//        }
//        else if (this.getX() + 30> MaxPoint.getX()) {
//            double dotProduct = velocity.dotProduct(xAxisNormalVectorDown);
//            this.velocity.setX(velocity.getX() - 2*dotProduct*xAxisNormalVectorDown.getX());
//            this.velocity.setY(velocity.getY() - 2*dotProduct*xAxisNormalVectorDown.getY());
//            this.add(velocity);
//        }
//        if (this.getY() - 30< MinPoint.getY()){
//            double dotProduct = velocity.dotProduct(yAxisNormalVectorLeft);
//            this.velocity.setX(velocity.getX() - 2*dotProduct*yAxisNormalVectorLeft.getX());
//            this.velocity.setY(velocity.getY() - 2*dotProduct*yAxisNormalVectorLeft.getY());
//            this.add(velocity);
//        }
//        else if (this.getY() + 30> MaxPoint.getY()){
//            double dotProduct = velocity.dotProduct(yAxisNormalVectorRight);
//            this.velocity.setX(velocity.getX() - 2*dotProduct*yAxisNormalVectorLeft.getX());
//            this.velocity.setY(velocity.getY() - 2*dotProduct*yAxisNormalVectorLeft.getY());
//            this.add(velocity);
//        }
//    }


    public void setVelocity(Vector2d velocity) {
        this.velocity = velocity;
    }

}
