/**
 * Created by anirudhiyer on 3/3/17.
 */
public class Motion {

    V2d target;
    Player player;
    double startingDistance;
    boolean accelerating;
    double mapWidth, mapHeight;

    Motion(V2d target, Player player, double mapWidth, double mapHeight){
        this.target = target;
        this.player = player;
        startingDistance = V2d.sub(target, player.r).norm();
        accelerating = false;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    void wrapTarget(){
        if(target.x - player.r.x > mapWidth/2) target.x -= mapWidth;
        if(target.x - player.r.x < -mapWidth/2) target.x += mapWidth;
        if(target.y - player.r.y > mapHeight/2) target.y -= mapHeight;
        if(target.y - player.r.y < -mapHeight/2) target.y += mapHeight;
    }

    String move(){
        //wrapTarget();
        V2d dir = V2d.sub(target, player.r);
        if(player.v.norm() < 0.1 && player.v.norm() / dir.norm() < 0.0002){
            startingDistance = dir.norm();
            accelerating = true;
            return "ACCELERATE " + dir.angle() + " " + (dir.norm2() > 30000 ? 1 : dir.norm2()/30000);
        }
        else if(dir.norm() > startingDistance * 0.25 && Math.cos(dir.angle() - player.v.angle()) > 0.9){
            if(!accelerating){
                accelerating = true;
                return "ACCELERATE " + dir.angle() + " " + (dir.norm() > 100 ? 1 : dir.norm()/100);
            }
        }
        else{
            if(accelerating){
                accelerating = false;
                return "BRAKE";
            }
        }
        return "";
    }

    void changeTarget(V2d target){
        this.target = target;
        V2d dir = V2d.sub(target, player.r);
        startingDistance = dir.norm();
    }


    public static String move_towards(double x, double y, double dx, double dy, double px, double py) {
        double dist_x = px - x;
        double dist_y = py - y;
        double magnitude_x = dist_x - dx;
        double magnitude_y = dist_y - dy;
        double theta = Math.atan2(-magnitude_y, magnitude_x);
        double mag = Math.sqrt(Math.pow(magnitude_x, 2) + Math.pow(magnitude_y, 2));
        return "ACCELERATE " + theta + " " + mag;
    }

    public static String moveTowards(V2d r, V2d v, V2d dest){
        double farAngle = 0.01;
        double farDistance = 100;
        V2d dir = V2d.sub(dest, r);
        V2d vperp = v.perp(dir);
        V2d vproj = v.proj(dir);
        double overshoot = vperp.norm() * dir.norm() / vproj.norm();
        String res = "";
        if(v.norm2() / dir.norm() < 0.003){
            res = "ACCELERATE " + dir.angle() + " " + "1";
        }
        else{
            res =  "BRAKE";
        }
        //System.out.println(res);
        //System.out.println("dir " + dir);
        return res;
    }

    public String placeMoveBomb(V2d r, double fuse){
      return "BOMB " + r.x +" "+ r.y + " "+ fuse;
    }

}
