package utils;

import game.Actor;

public class CollisionDetection {
    public static boolean detectCollision(Actor a, Actor b) {
        // Calculate the half-widths and half-heights of the rectangles
        float halfWidthA = (float) a.width / 2;
        float halfHeightA = (float) a.height / 2;
        float halfWidthB = (float) b.width / 2;
        float halfHeightB = (float) b.height / 2;

        // Calculate the edges of rectangle A
        float leftA = a.px - halfWidthA;
        float rightA = a.px + halfWidthA;
        float topA = a.py - halfHeightA;
        float bottomA = a.py + halfHeightA;

        // Calculate the edges of rectangle B
        float leftB = b.px - halfWidthB;
        float rightB = b.px + halfWidthB;
        float topB = b.py - halfHeightB;
        float bottomB = b.py + halfHeightB;

        // Check for collision using AABB (Axis-Aligned Bounding Box) logic
        return !(leftA > rightB ||  // A is completely to the right of B
                rightA < leftB ||  // A is completely to the left of B
                topA > bottomB ||  // A is completely below B
                bottomA < topB);   // A is completely above B
    }
}
