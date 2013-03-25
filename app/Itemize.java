
import play.mvc.BodyParser;

import java.util.*;

public class Itemize implements Runnable{
    private static int ONE_SECOND = 1000; //millis
    private static List<String> LANGUAGES = Arrays.asList("Clojure", "Groovy", "Java", "JRuby", "Scala");

    @Override
    public void run(){
        //Math.pow(43276544,1421276532);
        for(String language : LANGUAGES){
            wait(ONE_SECOND);
            System.out.println(String.format("JVM language: %s",language));
        }
    }
    private void wait(int timeInMillis){
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException e){
            throw new RuntimeException("Thread interrupted", e);
        }
    }
}
