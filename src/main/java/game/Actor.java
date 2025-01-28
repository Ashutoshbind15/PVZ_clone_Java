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
