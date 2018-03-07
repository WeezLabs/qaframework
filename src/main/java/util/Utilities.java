package util;

public class Utilities {
    static void waitUntil(Integer milisec){
        try {
            Thread.sleep(milisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
