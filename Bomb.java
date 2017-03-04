import java.util.*;

class Bomb{
    V2d r;
    
    Bomb(double x, double y){
        r = new V2d(x,y);
    }
    
    public String toString(){
        return "{x: " + r.x + ", y: " + r.y + "}";
    }
}