package game;

import io.github.libsdl4j.api.event.SDL_Event;
import io.github.libsdl4j.api.rect.SDL_Rect;

import java.util.ArrayList;

import static io.github.libsdl4j.api.event.SDL_EventType.SDL_KEYDOWN;
import static io.github.libsdl4j.api.event.SDL_EventType.SDL_MOUSEBUTTONUP;
import static io.github.libsdl4j.api.keycode.SDL_Keycode.SDLK_A;
import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderFillRect;

public class ResourceManager {

    int playerMana;
    ArrayList<ManaPoint> manaPoints;
    Game g;
    long startingEpoch;


    public ResourceManager(Game g) {
        ManaPoint mp = new ManaPoint(50, 50);
        ManaPoint mp1 = new ManaPoint(100, 100);
        this.g = g;
        this.playerMana = 100;

        startingEpoch = System.nanoTime();
        manaPoints = new ArrayList<>();

        manaPoints.add(mp);
        manaPoints.add(mp1);
    }

    public void RenderResources() {
        g.renderHelper.setRendererColor(255,255,0);

        for (ManaPoint mp : manaPoints) {
            SDL_Rect rect = g.renderHelper.drawRectangle(mp.px - ManaPoint.Width / 2, mp.py - ManaPoint.Height / 2, ManaPoint.Width, ManaPoint.Height);
            SDL_RenderFillRect(g.renderer, rect);
        }
    }

//    Called periodically, every 5-10 secs
    public void spawnResources() {

    }

    public void updateResources() {
        ArrayList<ManaPoint> remainingMana = new ArrayList<>();
        for(ManaPoint mp: manaPoints) {
            if(!mp.collected) {
                remainingMana.add(mp);
            }
        }
        manaPoints = remainingMana;
    }

    public void handleResourceInput(SDL_Event ev) {
        if (ev.type == SDL_MOUSEBUTTONUP) {

            int mx = ev.button.x;
            int my = ev.button.y;

            for(ManaPoint mp: manaPoints) {
                int xleft = mp.px - ManaPoint.Width / 2;
                int xright = mp.px + ManaPoint.Width / 2;

                int yup = mp.py - ManaPoint.Height / 2;
                int ydown = mp.py + ManaPoint.Height / 2;

                if (mx >= xleft && mx <= xright && my >= yup && my <= ydown) {
                    playerMana += mp.points;
                    mp.setCollected();
                }
            }
        } else if(ev.type == SDL_KEYDOWN) {
            if(ev.key.keysym.sym == SDLK_A) {
                System.out.println(playerMana);
            }
        }
    }

}
