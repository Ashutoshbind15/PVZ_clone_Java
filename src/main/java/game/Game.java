package game;

import actors.Plant;
import actors.Zombie;
import io.github.libsdl4j.api.event.SDL_Event;
import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.video.SDL_Window;
import utils.GameGrid;
import utils.RenderUtils;

import java.util.ArrayList;

import static io.github.libsdl4j.api.event.SDL_EventType.*;
import static io.github.libsdl4j.api.event.SdlEvents.SDL_PollEvent;

import static io.github.libsdl4j.api.Sdl.SDL_Init;
import static io.github.libsdl4j.api.SdlSubSystemConst.SDL_INIT_EVERYTHING;
import static io.github.libsdl4j.api.error.SdlError.SDL_GetError;
import static io.github.libsdl4j.api.render.SDL_RendererFlags.SDL_RENDERER_ACCELERATED;
import static io.github.libsdl4j.api.render.SdlRender.*;
import static io.github.libsdl4j.api.video.SDL_WindowFlags.SDL_WINDOW_RESIZABLE;
import static io.github.libsdl4j.api.video.SDL_WindowFlags.SDL_WINDOW_SHOWN;
import static io.github.libsdl4j.api.video.SdlVideo.SDL_CreateWindow;
import static io.github.libsdl4j.api.video.SdlVideoConst.SDL_WINDOWPOS_CENTERED;

public class Game {

    public boolean isRunning = true;
    SDL_Window window;
    public SDL_Renderer renderer;
    public RenderUtils renderHelper;
    public ResourceManager resourceManager;
    public long timeStamp;
    public ArrayList<Actor> actors;
    public ArrayList<Actor> pendingActors;
    public ArrayList<Actor> deadActors;
    public GameGrid gameGrid;

    static final int ScreenWidth = 1024;
    static final int ScreenHeight = 768;

    public Game() {
        int result = SDL_Init(SDL_INIT_EVERYTHING);
        if (result != 0) {
            throw new IllegalStateException("Unable to initialize SDL library (Error code " + result + "): " + SDL_GetError());
        }

        window = SDL_CreateWindow("Demo SDL2", SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, ScreenWidth, ScreenHeight, SDL_WINDOW_SHOWN | SDL_WINDOW_RESIZABLE);
        if (window == null) {
            throw new IllegalStateException("Unable to create SDL window: " + SDL_GetError());
        }

        renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);
        if (renderer == null) {
            throw new IllegalStateException("Unable to create SDL renderer: " + SDL_GetError());
        }

        this.renderHelper = new RenderUtils(renderer);
        this.resourceManager = new ResourceManager(this);
        this.gameGrid = new GameGrid(ScreenWidth, ScreenHeight, 5, 10);
        this.actors = new ArrayList<>();
        this.pendingActors = new ArrayList<>();
        this.deadActors = new ArrayList<>();

        Zombie z = new Zombie(this, 500,100);
        this.actors.add(z);
        Plant p = new Plant(this, 20, 100, "BeanShooter", 100);
        this.actors.add(p);
        this.timeStamp = System.nanoTime();
    }

    void renderBackGround() {
        renderHelper.setRendererColor(0,200,20,255);
        SDL_RenderClear(renderer);

        renderHelper.setRendererColor(128,128,128,255);

        SDL_Rect r = renderHelper.drawRectangle( ScreenWidth, 0,  (int)(0.2  * ScreenWidth), ScreenHeight);
        SDL_RenderFillRect(renderer, r);

        renderHelper.setRendererColor(0,250,20);

        for(int i = 0; i < 5; i++) {
            for(int j = 0;j < 10;j ++) {
                if ( (i+j)%2 == 0) {
                    int[] position = gameGrid.getBlock(i,j);
                    int blockWidth = gameGrid.getBlockWidth();
                    int blockHeight = gameGrid.getBlockHeight();

                    SDL_Rect darkTileRect = renderHelper.drawRectangle(position[0] - blockWidth / 2, position[1] - blockHeight / 2, blockWidth, blockHeight);
                    SDL_RenderFillRect(renderer, darkTileRect);
                }
            }
        }

    }

    void processInput() {
        SDL_Event ev = new SDL_Event();

        while(SDL_PollEvent(ev) != 0) {

            resourceManager.handleResourceInput(ev);

            if (ev.type == SDL_QUIT) {
                isRunning = false;
            }
        }
    }

    void update(double deltime) {
        resourceManager.updateResources(deltime);

        for(Actor ac: actors) {
            ac.updateActor(deltime);
        }

        actors.addAll(pendingActors);
        pendingActors.clear();

        for(Actor ac: deadActors) {
            actors.remove(ac);
        }

        deadActors.clear();
    }

    void processOutput() {
        renderBackGround();
        resourceManager.RenderResources();

        for(Actor ac: actors) {
            ac.renderActor();
        }

        SDL_RenderPresent(renderer);
    }

    public void gameLoop() {
        long currentTime = System.nanoTime();
        long deltime = currentTime - this.timeStamp;
        this.timeStamp = currentTime;

        processInput();
        update(deltime / 1_000_000_000.0);
        processOutput();
    }
}
