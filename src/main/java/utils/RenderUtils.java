package utils;

import game.Actor;
import io.github.libsdl4j.api.pixels.SdlPixels;
import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.render.SDL_Texture;
import io.github.libsdl4j.api.surface.SDL_Surface;
import io.github.libsdl4j.api.surface.SdlSurface;

import java.util.HashMap;

import static io.github.libsdl4j.api.render.SdlRender.*;

public class RenderUtils {

    SDL_Renderer renderer;
    HashMap<String, SDL_Texture> textureMap;
    static String AssetsDir = "assets";

    public RenderUtils(SDL_Renderer r) {
        this.renderer = r;
        this.textureMap = new HashMap<>();
    }

    public void setRendererColor(int r, int g, int b, int a) {
        SDL_SetRenderDrawColor(renderer, (byte)r, (byte)g, (byte)b, (byte) a);
    }

    public void setRendererColor(int r,int g, int b) {
        SDL_SetRenderDrawColor(renderer, (byte)r, (byte)g, (byte)b, (byte)255);
    }

    public SDL_Rect drawRectangle(int x, int y, int w, int h) {
        SDL_Rect r= new SDL_Rect();
        r.x = x;
        r.y = y;
        r.h = h;
        r.w = w;
        return r;
    }

    // TODO: Text rendering to be added

    public void loadImage(String fileName) {
        String filepath = AssetsDir + "/" + fileName + ".bmp";

        SDL_Surface sf = SdlSurface.SDL_LoadBMP(filepath);
        int magentaKey = SdlPixels.SDL_MapRGB(sf.getFormat(), (byte) 255, (byte) 0, (byte) 255);
        SdlSurface.SDL_SetColorKey(sf, 1, magentaKey);

        SDL_Texture texture = SDL_CreateTextureFromSurface(renderer, sf);
        SdlSurface.SDL_FreeSurface(sf);

        textureMap.put(fileName, texture);
    }

    public void renderTexture(String spriteName, Actor ac) {

        SDL_Texture texture = textureMap.get(spriteName);

        if(texture == null) {
            // TODO: Add a form of logger to get this done
            System.out.println("NOT FINDING THY TEXTURE");
        }

        SDL_Rect rct = this.drawRectangle((int) (ac.px - ac.width / 2), (int) (ac.py - ac.height / 2), ac.width, ac.height);

        SDL_RenderCopy(renderer, texture, null, rct);
    }
}