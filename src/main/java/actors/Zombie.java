package actors;

import game.Actor;
import game.Game;
import io.github.libsdl4j.api.rect.SDL_Rect;

import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderFillRect;

public class Zombie extends Actor {

    @Override
    public  void updateActor(double deltime) {
        this.px += (float) (this.direction * deltime * this.speed);
    }

    @Override
    public void renderActor() {
        SDL_Rect zombieRekt = this.game.renderHelper.drawRectangle((int)(this.px - this.width / 2), (int) (this.py - this.height / 2) , this.width, this.height);
        SDL_RenderFillRect(this.game.renderer, zombieRekt);
    }

    public Zombie(Game g) {
        super(g);
        this.speed = 30;
        this.height = 30;
        this.direction = -1;
        this.width = 20;
    }

    public Zombie(Game g, int x, int y) {
        super(g,x,y);
        this.speed = 30;
        this.height = 30;
        this.direction = -1;
        this.width = 20;
    }
}
