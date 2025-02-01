package actors;

import game.Actor;
import game.Game;
import io.github.libsdl4j.api.rect.SDL_Rect;

import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderFillRect;

// TODO: Let the shooters be as components to the plant objects, so that we can have custom trajectories, speeds, and damages, possible powerups to the shooters/ plants....

public class Plant extends Actor {

    public enum PlantType {
        SUNFLOWER,
        BEANSHOOTER,
        BALLTHROWER
    }

    long plantTime;
    boolean isInCycle;
    // let the state of the plant be planted, queued, neutral and chosen, will keep as chosen for now for all until i do support
    // for multiple screens
    String state;
    int cost;
    String name;

    static int PlantHeight = 20;
    static int PlantWidth = 30;

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

        this.plantTime = System.nanoTime();
        this.isInCycle = false;
    }

    public Plant(Game g, PlantType type, boolean isInQueue) {

        super(g);

        this.width = PlantWidth;
        this.height = PlantHeight;
        this.plantTime = System.nanoTime();
        this.type = "Plant";
        this.state = "Chosen";

        if(isInQueue) {
            this.speed = 30;
            this.direction = -1;
        }

        this.name = type.name();

        switch (type) {
            case BALLTHROWER -> {
                this.cost = 150;
            }

            case BEANSHOOTER -> {
                this.cost = 100;
            }

            case SUNFLOWER -> {
                this.cost = 100;
            }
        }
    }
}
