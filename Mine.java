import java.util.*;

class Mine{
    static enum State{
        UNKNOWN, OWNED, NOT_OWNED 
    }
    State state;
    V2d r;
    String owner;
    
    Mine(String user, String owner, double x, double y){
        this.owner = owner;
        r = new V2d(x,y);
        state = user.equals(owner) ? State.OWNED : State.NOT_OWNED;
        
    }
    
    public String toString(){
        return "{owner: " + owner + ", x: " + r.x + ", y: " + r.y + ", state: " + state +"}";
    }
}