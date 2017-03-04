import java.util.*;

class Data{
    Vector<Mine> mines;
    Vector<Player> players;
    Vector<Bomb> bombs;
    Player currentPlayer;
    
    Data(){
        mines = new Vector<>();
        players = new Vector<>();
        bombs = new Vector<>();
        currentPlayer = new Player(0,0,0,0);
    }
    
    void clear(){
        players.clear();
        bombs.clear();
        for(Mine mine:mines){
            mine.current = false;
        }
    }
    
    void updateCurrentPlayer(double x, double y, double vx, double vy){
        currentPlayer.r.set(x,y);
        currentPlayer.v.set(vx,vy);
    }
    
    void addPlayer(double x, double y, double vx, double vy){
        players.add(new Player(x,y,vx,vy));
    }
    
    void addMine(int owner, double x, double y){
        for(Mine mine:mines){
            if(mine.r.equals(new V2d(x,y))){
                mine.current = true;
                return;
            }
        }
        mines.add(new Mine(owner,x,y));
    }
    
    void addBomb(double x, double y){
        bombs.add(new Bomb(x,y));
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
}