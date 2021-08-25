package com.springcloud.web.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class DownLatch1 implements Runnable{

    private CountDownLatch downLatch;

    private Map<String,Object> resultMap;

    public DownLatch1(CountDownLatch downLatch,Map<String,Object> resultMap){
        this.downLatch = downLatch;
        this.resultMap = resultMap;
    }

    @Override
    public void run() {
        Map<String,String> map = new HashMap<>();
        try {
            log.info(Thread.currentThread().getName()+" DownLatch1 start..");
            Thread.sleep(5000);
            log.info(Thread.currentThread().getName()+" DownLatch1 end..");
            map.put("msg","success");
        }catch (Exception e){
            map.put("msg","fail");
            log.error("DownLatch1 has an error: ",e);
        }finally {
            resultMap.put("DownLatch1",map);
            downLatch.countDown();
        }

    }
}
