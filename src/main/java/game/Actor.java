package game;

public abstract class Actor {

    public float px;
    public float py;
    public int direction;
    public int speed;
    public Game game;
    public int width;
    public int height;
    public String type;

    public Actor(Game g) {
//        add actor to the game
          this.game = g;
    }

//  Don't think we need a big constructor, we are bubbling down the factory to the children (the task of creating objects is given to the children
//  constructors with defaults and enums for now, and later maybe loading from a db or from a json/yml file for the  configuration

    public Actor(Game g, int x, int y) {
        this(g);
        setPosition(x,y);
    }

    public void setPosition(int x,int y) {
        this.px = x;
        this.py = y;
    }

    public abstract void updateActor(double deltime);
    public abstract void renderActor();
}
