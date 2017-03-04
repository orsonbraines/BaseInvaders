import java.util.*;

class Data{
    Vector<Mine> mines;
    Vector<Player> players;
    Vector<Bomb> bombs;
    Player currentPlayer;
    volatile int minesOwned;
    String username;
    double mapWidth, mapHeight;
    
    Data(String username){
        mines = new Vector<>();
        players = new Vector<>();
        bombs = new Vector<>();
        currentPlayer = new Player(0,0,0,0);
        minesOwned = 0;
        this.username = username;
        this.mapWidth = 10000;
        this.mapHeight = 10000;
    }
    
    void setSize(double mapWidth, double mapHeight){
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }
    
    void clear(){
        players.clear();
        bombs.clear();
        for(Mine mine:mines){
            if(mine.state != Mine.State.OWNED) mine.state = Mine.State.UNKNOWN;
        }
    }
    
    void updateCurrentPlayer(double x, double y, double vx, double vy){
        currentPlayer.r.set(x,y);
        currentPlayer.v.set(vx,vy);
    }
    
    synchronized void addPlayer(double x, double y, double vx, double vy){
        players.add(new Player(x,y,vx,vy));
    }
    
    void addMine(String owner, double x, double y){
        for(Mine mine:mines){
            if(mine.r.equals(new V2d(x,y))){
                mine.owner = owner;
                mine.state = owner.equals(username) ? Mine.State.OWNED : Mine.State.NOT_OWNED; 
                return;
            }
        }
        mines.add(new Mine(username, owner,x,y));
    }
    
    void addBomb(double x, double y){
        bombs.add(new Bomb(x,y));
    }
    
    synchronized void setMinesOwned(int minesOwned){
        if(minesOwned < this.minesOwned){
            for(Mine mine : mines){
                mine.state = Mine.State.UNKNOWN;
            }
        }
        this.minesOwned = minesOwned;
    }
    
    public String toString(){
        String val = "CurrentPlayer ";
        val += currentPlayer + "\n";
        val += "players ";
        for(Player player: players) val += player;
        val += "\nmines ";
        for(Mine mine:mines) val += mine;
        val += "\nbombs ";
        for(Bomb bomb : bombs) val += bomb;
        val += "\n";
        return val;
    }
    
    Mine closestMine(){
        double mindist2 = Double.MAX_VALUE;
        Mine m = null;
        for(Mine mine : mines){
            if(mine.state != Mine.State.OWNED){
                double dist2 = currentPlayer.r.dist2(mine.r, mapWidth, mapHeight);
                if(dist2 < mindist2) {
                    mindist2 = dist2;
                    m = mine;
                }
            }
        }
        return m;
    }
}