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
    
    double dist2(V2d v, double mapWidth, double mapHeight){
        return ((x - v.x)%mapWidth) * ((x - v.x)%mapWidth) + ((y - v.y) % mapHeight) * ((x - v.x) % mapHeight);
    }
    
    boolean equals(V2d v){
        return dist2(v) < 1e-3;
    }
    
    boolean equals(V2d v, double mapWidth, double mapHeight){
        return dist2(v, mapWidth, mapHeight) < 1e-5;
    }
    
    double norm2(){
        return x*x + y*y;
    }
    
    double norm(){
        return Math.sqrt(norm2());
    }
    
    V2d toUnit(){
        double n = norm();
        x /= n;
        y /= n;
        return this;
    }
    
    V2d scale(double s){
        x *= s;
        y *= s;
        return this;
    }
    
    double angle(){
        return Math.atan2(y,x);
    }
    
    
    double dot(V2d v){
        return x * v.x + y * v.y;
    }
    
    static V2d sub(V2d v1, V2d v2){
        return new V2d(v1.x - v2.x, v1.y - v2.y);
    }
    
    V2d proj(V2d v){
        return new V2d(v.x,v.y).scale(dot(v) / v.norm2());
    }
    
    V2d perp(V2d v){
        V2d p = proj(v);
        return new V2d(x - p.x, y - p.y);
    }
    
    public String toString(){
        return "{x: " + x + ", y: " + y + "}";
    }
}