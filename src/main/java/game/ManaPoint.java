package game;

import java.time.LocalDateTime;

public class ManaPoint {
    Integer px;
    Integer py;
    static Integer Width = 50;
    static Integer Height = 50;
    Integer points;
    int appearanceTimeStamp;
    int placingTimeInSeconds;
    int deletionTimeInSeconds;
    boolean collected;

    public ManaPoint(Integer px, Integer py) {
        LocalDateTime now = LocalDateTime.now();

        this.points = 100;
        this.px = px;
        this.py = py;
    }

    public void destroy(ResourceManager rm) {
        rm.manaPoints.remove(this);
    }

    public void setCollected() {
        this.collected = true;
    }
}
