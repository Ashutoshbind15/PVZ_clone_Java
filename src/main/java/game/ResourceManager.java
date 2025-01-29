package game;

import actors.Plant;
import actors.ShooterBean;
import actors.Zombie;
import io.github.libsdl4j.api.event.SDL_Event;
import io.github.libsdl4j.api.rect.SDL_Rect;

import java.util.ArrayList;
import java.util.Queue;

import static io.github.libsdl4j.api.event.SDL_EventType.SDL_KEYDOWN;
import static io.github.libsdl4j.api.event.SDL_EventType.SDL_MOUSEBUTTONUP;
import static io.github.libsdl4j.api.keycode.SDL_Keycode.SDLK_A;
import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderFillRect;

public class ResourceManager {

    int playerMana;
    ArrayList<ManaPoint> manaPoints;

    ArrayList<Plant> plantOptions;
    ArrayList<Plant> plantQueue;

    Game g;
    long startingEpoch;
    boolean isInCycle;

    boolean isSelectedPlant;
    Plant selectedPlant;

    void fillPlantOptions() {
        Plant bs = new Plant(g, g.ScreenWidth, 20, "BeanShooter", 100);
        Plant thrower = new Plant(g, g.ScreenWidth, 20, "BallThrower", 200);

        bs.speed = 30;
        bs.direction = -1;

        thrower.speed = 30;
        thrower.direction = -1;

        plantOptions.add(bs);
        plantOptions.add(thrower);
    }

    void runAfterInterval(int seconds) {
        int secondsPassedSincePlanted = (int)( (this.g.timeStamp - startingEpoch) / 1_000_000_000.0);

        if(((secondsPassedSincePlanted % seconds) == 0) && !isInCycle) {
            spawnResources();
            isInCycle = true;
        } else if((secondsPassedSincePlanted % seconds) != 0) {
            isInCycle = false;
        }
    }

    public ResourceManager(Game g) {

        this.startingEpoch = System.nanoTime();
        ManaPoint mp = new ManaPoint(50, 50);
        ManaPoint mp1 = new ManaPoint(100, 100);

        plantOptions = new ArrayList<>();
        plantQueue = new ArrayList<>();


        this.g = g;
        this.playerMana = 100;

        fillPlantOptions();

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

        for(Plant p: plantQueue) {
            SDL_Rect rect = g.renderHelper.drawRectangle((int) (p.px - p.width / 2), (int) (p.py - p.height / 2), p.width, p.height);
            SDL_RenderFillRect(g.renderer, rect);
        }
    }

    // Called periodically, every 5-10 secs
    public void spawnResources() {
        int sz = plantOptions.size();
        double v = Math.random() * sz;
        int randomInt = (int) v;

        Plant randomPlant = plantOptions.get(randomInt);
        System.out.println(randomPlant.height);
        System.out.println(randomPlant.game);
        plantQueue.add(new Plant(randomPlant));

        int randomLane = (int) (Math.random() * this.g.gameGrid.getGridRows());
        int[] zombiePosition = this.g.gameGrid.getBlock(randomLane, 9);

        Zombie spawnedZombie = new Zombie(this.g, this.g.ScreenWidth, zombiePosition[1]);
        this.g.pendingActors.add(spawnedZombie);
    }

    public void updateResources(double deltime) {
        ArrayList<ManaPoint> remainingMana = new ArrayList<>();
        for(ManaPoint mp: manaPoints) {
            if(!mp.collected) {
                remainingMana.add(mp);
            }
        }
        manaPoints = remainingMana;

        for(Plant p : plantQueue) {
            p.px += (float) (p.direction * p.speed * deltime);
        }

        runAfterInterval(5);
    }

    public void handleResourceInput(SDL_Event ev) {
        if (ev.type == SDL_MOUSEBUTTONUP) {

            int mx = ev.button.x;
            int my = ev.button.y;

            boolean touchedAnything = false;

            for(ManaPoint mp: manaPoints) {
                int xleft = mp.px - ManaPoint.Width / 2;
                int xright = mp.px + ManaPoint.Width / 2;

                int yup = mp.py - ManaPoint.Height / 2;
                int ydown = mp.py + ManaPoint.Height / 2;

                if (mx >= xleft && mx <= xright && my >= yup && my <= ydown) {
                    playerMana += mp.points;
                    mp.setCollected();
                    touchedAnything = true;
                }
            }

            for(Plant p : plantQueue) {
                int xleft = (int) (p.px - p.width / 2);
                int xright = (int) (p.px + p.width / 2);

                int yup = (int) (p.py - p.height / 2);
                int ydown = (int) (p.py + p.height / 2);

                if (mx >= xleft && mx <= xright && my >= yup && my <= ydown) {
                    if(!isSelectedPlant) {
                        isSelectedPlant = true;
                        selectedPlant = p;
                        touchedAnything = true;
                    }
                }
            }

            if(!touchedAnything) {

                if(isSelectedPlant) {
                    Plant plantToBePlanted = new Plant(selectedPlant);
                    plantToBePlanted.type = "Planted";
                    plantToBePlanted.direction = +1;
                    plantToBePlanted.speed = 0;
                    plantToBePlanted.px = mx;
                    plantToBePlanted.py = my;
                    System.out.println(selectedPlant.height);
                    System.out.println(selectedPlant.game);
                    this.g.pendingActors.add(plantToBePlanted);
                }

                selectedPlant = null;
                isSelectedPlant = false;
            }

        } else if(ev.type == SDL_KEYDOWN) {
            if(ev.key.keysym.sym == SDLK_A) {
                System.out.println(playerMana);
            }
        }
    }

}
