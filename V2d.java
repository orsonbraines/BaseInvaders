import java.util.*;

class V2d{
    double x,y;
    
    V2d(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    void set(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    double dist2(V2d v){
        return (x - v.x)*(x - v.x) + (y - v.y)*(y - v.y);
    }
    
    boolean equals(V2d v){
        return dist2(v) < 1e-3;
    }
}