package entry;

import game.Game;

public class Main {
    public static void main(String[] args) {
        Game g = new Game();

        while(g.isRunning) {
            g.gameLoop();
        }
    }
}