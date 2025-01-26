package utils;

import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;

import static io.github.libsdl4j.api.render.SdlRender.SDL_SetRenderDrawColor;

public class RenderUtils {

    SDL_Renderer renderer;

    public RenderUtils(SDL_Renderer r) {
        this.renderer = r;
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
}
