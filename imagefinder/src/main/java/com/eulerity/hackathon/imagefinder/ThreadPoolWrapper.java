package com.eulerity.hackathon.imagefinder;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolWrapper {
    static int cores ;
    static ExecutorService executorService = null;

    /**
     * This is the threadpool wrapper with threads (current cores*2)
     */
    public ThreadPoolWrapper(){
        {
            cores = Runtime.getRuntime().availableProcessors();
            executorService = Executors.newFixedThreadPool(cores*2);
        }
    }

    /**
     * Method where parallel computation happens
     * @param fetchImagesTasks
     */
    public void computeParallely(List<FetchImagesTask> fetchImagesTasks){
        for(int i = 0; i < fetchImagesTasks.size() ; i ++){
            // System.out.println(fetchImagesTasks.get(i).url);
            Runnable task = fetchImagesTasks.get(i);
            executorService.execute(task);
        }

        executorService.shutdown();
        while(!executorService.isTerminated()) {}
        System.out.println("All threads stopped");
    }
}
