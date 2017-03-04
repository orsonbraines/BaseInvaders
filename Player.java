import java.util.*;

class Player{
    V2d r;
    V2d v;
    
    Player(double x, double y, double vx, double vy){
        r = new V2d(x,y);
        v = new V2d(vx,vy);
    }
    
    public String toString(){
        return "{x: " + r.x + ", y: " + r.y + ", vx: " + v.x + ", vy: " + v.y + "}";
    }
}