package game;

import java.time.LocalDateTime;

public class ManaPoint {
    Integer px;
    Integer py;
    static Integer Width = 50;
    static Integer Height = 50;
    int appearanceTimeStamp;
    int placingTimeInSeconds;
    int deletionTimeInSeconds;

    public ManaPoint(Integer px, Integer py) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("The postional coordinates of the mana is equal to : " +  px.toString() + ": " + py.toString());
        System.out.println(now);

        this.px = px;
        this.py = py;
    }
}
