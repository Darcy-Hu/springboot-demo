package com.springcloud.web.controller;

import com.springcloud.web.config.dataSource.DynamicDataSourceContextHolder;
import com.springcloud.web.thread.CallableTest;
import com.springcloud.web.thread.DownLatch1;
import com.springcloud.web.thread.DownLatch2;
import com.springcloud.web.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;

@Slf4j
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private List<String> list = new ArrayList<String>();

    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    private int num = 1;

    private StringUtils stringUtils;

    @PostConstruct
    private void initList(){
        list.add("a");
        list.add("b");
        list.add("c");

        Map<String,Object> map = new HashMap<>();
        map.put("list",this.list);
        stringUtils = new StringUtils(map);
    }

    public List<String> getList(){
        return Collections.synchronizedList(list);
    }

    @GetMapping("/helloWorld")
    public String helloWorld(String str){
        stringUtils.getMap();
        return "helloWorld";
    }

    @GetMapping("/unmodify")
    public List<String> testCollection(String str) throws InterruptedException {
        List<String> result = getList();
        Thread.sleep(5000);
        return result;
    }

    @GetMapping("/changeList")
    public List<String> testCollection2(String str){
        List<String> result = getList();
        result.add("d");
        return result;
    }

    @GetMapping("/executor")
    public String testExecutor() throws ExecutionException, InterruptedException, TimeoutException {
        num++;
        Future<String> future = executorService.submit(new CallableTest(num));
        String result = future.get(7,TimeUnit.SECONDS);
        return result;
    }

    @GetMapping("/countDownLatch")
    public Map<String,Object> testCountDownLatch(){
        Map<String,Object> resultMap = new HashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch downLatch = new CountDownLatch(2);
        executor.submit(new DownLatch1(downLatch,resultMap));
        executor.submit(new DownLatch2(downLatch,resultMap));
        try {
            log.info("await is start..");
            downLatch.await(1,TimeUnit.MINUTES);
            log.info("await is over");
        } catch (InterruptedException e) {
            log.error("testCountDownLatch has an InterruptedException:",e);
        }

        getResultMap(resultMap,"DownLatch1,DownLatch2");
        return resultMap;
    }

    private void getResultMap(Map<String, Object> resultMap,String threadName) {
        String[] threadArray = threadName.split(",");
        for (int i = 0; i < threadArray.length; i++) {
            Map<String,String> downLatch = (Map<String, String>) resultMap.get(threadArray[i]);
            String msg = downLatch.get("msg");
            if(downLatch == null || StringUtils.isEmpty(msg) || StringUtils.equals("fail",msg)){
                resultMap.clear();
                resultMap.put("msg","fail-->"+threadArray[i]);
                break;
            }
        }
    }


}
