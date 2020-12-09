import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description: 压测
 * @author: zhanglin16
 * @create: 2020-07-13 18:27
 **/
public class PressureTest {
    private static final String USER_AGENT = "HttpClient/3.1/PP";
    private static final String CHARSET_UTF = "UTF-8";
    private final MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
    private final HttpClient httpClient = new org.apache.commons.httpclient.HttpClient(httpConnectionManager);
    private final ExecutorService executor;
    /**
     * 测试url
     */
    private final String testUrl;
    /**
     * 请求总次数
     */
    private final Integer testLength;
    /**
     * 并发数
     */
    private Integer currentCount;

    private final SortedMap<Long, Integer> testResult = new TreeMap<>();

    public PressureTest(String testUrl, Integer testLength, Integer currentCount) {
        this.testUrl = testUrl;
        this.currentCount = currentCount;
        this.testLength = testLength;
        this.executor = Executors.newFixedThreadPool(currentCount);
    }

    /**
     * 发起测试
     */
    public void testing(Integer percentTime) {
        for (int i = 0; i < testLength; i++) {
            executor.execute(() -> {
                long startTime = System.currentTimeMillis();
                Integer result = null;
                try {
                    result = httpGet(testUrl);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                long endTime = System.currentTimeMillis();
                long time = endTime - startTime;
                synchronized (testResult.getClass()) {
                    Integer getVal = testResult.get(time);
                    if (getVal == null) {
                        getVal = 0;
                    }
                    testResult.put(time, ++getVal);
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //计算响应时长
        testResultTime(percentTime);
    }

    /**
     * 响应时常
     */
    public void testResultTime(Integer percentTime) {
        //总时长
        long timeCount = 0L;
        // 95总时长
        long timeCount95 = 0L;
        // 统计95的数量
        double count95 = 0.0;
        //用百分比转化为需要查看的请求数的平均响应时常
        double countTime = testLength * percentTime * 0.01;
        for (HashMap.Entry<Long, Integer> entry : testResult.entrySet()) {
            timeCount += entry.getKey() * entry.getValue();
            if (count95 < countTime) {
                if ((count95 = count95 + entry.getValue() * 1.0) >= countTime) {
                    timeCount95 = timeCount;
                }
            }

        }
        System.out.println("平均响应时间：" + (timeCount / testLength));
        System.out.println("95平均响应时间：" + (timeCount95 / countTime));
    }

    public Integer httpGet(String fullUrl) {
        GetMethod method = new GetMethod(fullUrl);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        method.addRequestHeader("User-Agent", USER_AGENT);
        try {
            return httpClient.executeMethod(method);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return 0;

    }

}

