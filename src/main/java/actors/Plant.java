package actors;

import game.Actor;
import game.Game;
import io.github.libsdl4j.api.rect.SDL_Rect;

import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderFillRect;

public class Plant extends Actor {

    long plantTime;
    boolean isInCycle;
    // let the state of the plant be planted, queued, neutral and chosen, will keep as chosen for now for all until i do support
    // for multiple screens
    String state;
    int cost;
    String name;

    void runAfterInterval(int seconds) {
        int secondsPassedSincePlanted = (int)( (this.game.timeStamp - plantTime) / 1_000_000_000.0);

        if(((secondsPassedSincePlanted % seconds) == 0) && !isInCycle) {
            ShooterBean sb = new ShooterBean(this.game, (int)(this.px + 5), (int) (this.py));
            this.game.pendingActors.add(sb);
            isInCycle = true;
        } else if((secondsPassedSincePlanted % seconds) != 0) {
            isInCycle = false;
        }
    }

    @Override
    public void updateActor(double deltime) {
        runAfterInterval(5);
    }

    @Override
    public void renderActor() {
        SDL_Rect plantRect = this.game.renderHelper.drawRectangle( (int)(this.px - this.width / 2), (int) (this.py - this.height / 2), this.width, this.height);
        SDL_RenderFillRect(this.game.renderer, plantRect);
    }

    public Plant(Game g, int px, int py, String name, int cost) {
        super(g, px, py);
        this.width = 30;
        this.height = 20;
        this.plantTime = System.nanoTime();
        this.type = "Plant";
        this.state = "Chosen";
        this.name = name;
        this.cost = cost;
    }

    public Plant(Plant other) {
        super(other.game, (int) other.px, (int) other.py);
        this.height = other.height;
        this.width = other.width;
        this.type = other.type;
        this.state = other.state;
        this.name = other.name;
        this.cost = other.cost;

        this.speed = other.speed;
        this.direction = other.direction;
    }
}
