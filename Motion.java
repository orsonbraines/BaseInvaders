/**
 * Created by anirudhiyer on 3/3/17.
 */
public class Motion {

    public void move_towards(V2d r,V2d v, Mine mine) {
        double px = mine.r.x -
        double dist_y = py - y;
        double magnitude_x = dist_x - dx;
        double magnitude_y = dist_y - dy;
        double theta = Math.atan2(-magnitude_y, magnitude_x);
        double mag = Math.sqrt(Math.pow(magnitude_x, 2) + Math.pow(magnitude_y, 2));
        System.out.println("ACCELERATE " + theta + " " + mag);
    }



}
