package com.eulerity.hackathon.imagefinder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolWrapper {
    static int cores ;
    static ExecutorService executorService = null;

    public ThreadPoolWrapper(){
        {
            cores = Runtime.getRuntime().availableProcessors();
            Executors.newFixedThreadPool(cores*2);
        }
    }

    public void computeParallely(List<FetchImagesTask> fetchImagesTasks){

        for(int i = 0; i < fetchImagesTasks.size() ; i ++){
            Runnable task = fetchImagesTasks.get(i);
            executorService.execute(task);
        }
    }


}
