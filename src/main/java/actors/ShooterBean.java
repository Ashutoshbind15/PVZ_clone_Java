package actors;

import game.Actor;
import game.Game;
import io.github.libsdl4j.api.rect.SDL_Rect;
import utils.CollisionDetection;

import java.util.Objects;

import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderFillRect;

public class ShooterBean extends Actor {

    public ShooterBean(Game g) {
        super(g);
        this.direction = +1;
        this.speed = 50;
        this.width = 20;
        this.height = 10;
    }

    public ShooterBean(Game g, int x, int y) {
        super(g, x, y);
        this.direction = +1;
        this.speed = 80;
        this.width = 20;
        this.height = 10;
    }

    @Override
    public void updateActor(double deltime) {
        this.px += (float) (this.direction * this.speed * deltime);

        for(Actor ac : this.game.actors) {
            if(Objects.equals(ac.type, "Zombie")) {
                if(CollisionDetection.detectCollision(this, ac)) {

                    Zombie zombie = (Zombie) ac;
                    zombie.health -= 50;

                    this.game.deadActors.add(this);
                }
            }
        }

    }

    @Override
    public void renderActor() {
        SDL_Rect beanRect = this.game.renderHelper.drawRectangle( (int)(this.px - this.width / 2), (int) (this.py - this.height / 2), this.width, this.height);
        SDL_RenderFillRect(this.game.renderer, beanRect);
    }
}
