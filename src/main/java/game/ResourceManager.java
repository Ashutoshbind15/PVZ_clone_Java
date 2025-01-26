package game;

import io.github.libsdl4j.api.event.SDL_Event;
import io.github.libsdl4j.api.rect.SDL_Rect;

import java.util.ArrayList;

import static io.github.libsdl4j.api.event.SDL_EventType.SDL_MOUSEBUTTONUP;
import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderFillRect;

public class ResourceManager {

    int playerMana;
    ArrayList<ManaPoint> manaPoints;
    Game g;


    public ResourceManager(Game g) {
        ManaPoint mp = new ManaPoint(50, 50);
        ManaPoint mp1 = new ManaPoint(100, 100);
        this.g = g;
        this.playerMana = 100;

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

    public void handleResourceInput(SDL_Event ev) {
        if (ev.type == SDL_MOUSEBUTTONUP) {
            System.out.println(ev.button.x);
            System.out.println(ev.button.y);

            int mx = ev.button.x;
            int my = ev.button.y;

            for(ManaPoint mp: manaPoints) {
                int xleft = mp.px - ManaPoint.Width / 2;
                int xright = mp.px + ManaPoint.Width / 2;

                int yup = mp.py - ManaPoint.Height / 2;
                int ydown = mp.py + ManaPoint.Height / 2;

                if (mx >= xleft && mx <= xright && my >= yup && my <= ydown) {
                    System.out.println("Mana clicked");
                }
            }
        }
    }

}
