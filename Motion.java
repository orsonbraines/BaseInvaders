/**
 * Created by anirudhiyer on 3/3/17.
 */
public class Motion {

    public void move_towards(double x, double y, double dx, double dy, double px, double py) {
        double dist_x = px - x;
        double dist_y = py - y;
        double magnitude_x = dist_x - dx;
        double magnitude_y = dist_y - dy;
        double theta = Math.atan2(-magnitude_y, magnitude_x);
        double mag = Math.sqrt(Math.pow(magnitude_x, 2) + Math.pow(magnitude_y, 2));
        System.out.println("ACCELERATE " + theta + " " + mag);
    }

    

}
