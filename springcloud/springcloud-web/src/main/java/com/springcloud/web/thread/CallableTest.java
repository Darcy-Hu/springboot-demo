package com.springcloud.web.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

@Slf4j
public class CallableTest implements Callable<String> {

    private int num;

    public CallableTest(int num){
        this.num = num;
    }

    @Override
    public String call() throws Exception {
        log.info(Thread.currentThread().getName()+" start.."+num);
        String result = "null";
        boolean flag = false;
        if(num == 2){
            result = "success";
            Thread.sleep(10000);
        }else{
            result = "fail";
        }

        log.info(Thread.currentThread().getName()+" end.."+num);

        return result;
    }
}
