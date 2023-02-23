package core.utils;

import java.util.Random;

public class RandomNumberUtils {
    public static int getRandomNumber(int length){
        Random random = new Random();
        return (10*length) + random.nextInt(9000);
    }
}
