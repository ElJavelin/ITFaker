package org.smirnov;

import org.apache.commons.lang3.time.StopWatch;

public class App
{
    public static void main(String[] args){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        DataGenerator dataGenerator;
        if (args.length == 2) dataGenerator = new DataGenerator(args[0], args[1]);
        else if(args.length == 3) dataGenerator = new DataGenerator(args[0], args[1], args[2]);
        else throw new IllegalArgumentException();
        dataGenerator.begin();
        stopWatch.stop();
        System.out.println("Прошло времени, мс: " + stopWatch.getTime());
    }
}