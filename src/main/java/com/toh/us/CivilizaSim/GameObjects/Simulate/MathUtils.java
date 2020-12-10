package com.toh.us.CivilizaSim.GameObjects.Simulate;

public class MathUtils {

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
