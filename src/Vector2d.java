public class Vector2d {

    private double x;
    private double y;

    public Vector2d() {
        this.x = 0.0;
        this.y = 0.0;
    }

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public double getDistance(Vector2d other){
        double dx = (this.getX() - other.getX()); double dy = (this.getY() - other.getY());
        return Math.sqrt(dx*dx + dy*dy);
    }

    public void normalize() {
        double magnitude = getMagnitude();
        this.x = x / magnitude;
        this.y = y / magnitude;
    }

    public void add(Vector2d other) {
        this.x = x + other.x;
        this.y = y + other.y;
    }


    public void subtract(Vector2d other) {
        this.x = x - other.x;
        this.y = y - other.y;
    }

    public void multiply(double scalar) {
        this.x = x * scalar; this.y = y * scalar;
    }

    public Vector2d getMultiply(double scalar){
        return new Vector2d(x*scalar, y*scalar);
    }

    public double dotProduct(Vector2d other) {
        return x * other.x + y * other.y;
    }

    public double crossProduct(Vector2d other) {
        return x * other.y - y * other.x;
    }

    public double angleTo(Vector2d other) {
        return Math.atan2(crossProduct(other), dotProduct(other));
    }

    public void rotate(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        this.x = x * cos - y * sin; this.y = x * sin + y * cos;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Vector2d)) {
            return false;
        }

        Vector2d otherVector = (Vector2d) other;

        return this.x == otherVector.x && this.y == otherVector.y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
