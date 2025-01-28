package actors;

import game.Actor;
import game.Game;
import io.github.libsdl4j.api.rect.SDL_Rect;

import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderFillRect;

public class Plant extends Actor {

    long plantTime;
    boolean isACycleOfFive;

    @Override
    public void updateActor(double deltime) {
        long timePassed = this.game.timeStamp - plantTime;
        int secondsPassed = (int) (timePassed / 1_000_000_000.0);

        if ((secondsPassed % 5) == 0 && !isACycleOfFive) {
            // Create a shooter bean actor and add to the game, just ahead of the plant actor !!!
            ShooterBean sb = new ShooterBean(this.game, (int)(this.px + 5), (int) (this.py));
            this.game.pendingActors.add(sb);
            isACycleOfFive = true;
        } else if ((secondsPassed % 5) != 0) {
            isACycleOfFive = false;
        }
    }

    @Override
    public void renderActor() {
        SDL_Rect plantRect = this.game.renderHelper.drawRectangle( (int)(this.px - this.width / 2), (int) (this.py - this.height / 2), this.width, this.height);
        SDL_RenderFillRect(this.game.renderer, plantRect);
    }

    public Plant(Game g, int px, int py) {
        super(g, px, py);
        this.width = 30;
        this.height = 20;
        this.plantTime = System.nanoTime();
        this.type = "Plant";
    }

}
