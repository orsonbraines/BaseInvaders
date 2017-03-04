import java.util.*;

class Mine{
    boolean current;
    V2d r;
    int owner;
    
    Mine(int owner, double x, double y){
        this.owner = owner;
        r = new V2d(x,y);
        current = true;
    }
    
    public String toString(){
        return "{owner: " + owner + ", x: " + r.x + ", y: " + r.y + ", current: " + current +"}";
    }
}