package ipip;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

import java.io.*;
import java.util.*;

/**
 * 索引查找：利用Map构建索引，利用Node存储数据
 */
public class IndexNodeMap {
    public static Map<String, List<Node>> keyMap = new HashMap<>();//
    private long max = 0;//数据最大值
    private long min = 0;//数据最小值

    // 内部节点类 用于存储信息
    private class Node {
        private long startIP;      //起始IP
        private long endIP;        //结束IP
        private String message;  //信息
    }

    // 组装数据
    private void buildMap(String[] message) {
//        System.out.println(Arrays.toString(message));
        String firstNum = message[0].substring(0, 2);
        String secondNum = message[1].substring(0, 2);
        long startIP = Long.parseLong(message[0]);
        if (min == 0) {
            min = startIP;
        } else {
            if (startIP < min) {
                min = startIP;
            }
        }

        long endIP = Long.parseLong(message[1]);
        if (max == 0) {
            max = endIP;
        } else {
            if (endIP > this.max) {
                this.max = endIP;
            }
        }

        Node nod = new Node();
        nod.startIP = startIP;
        nod.endIP = endIP;
        nod.message = message[2];

        List<Node> list = keyMap.get(firstNum);
        addNode(list, nod, firstNum);
        if (!firstNum.equals(secondNum)) {  // 不相等
            List<Node> list2 = keyMap.get(secondNum);
            addNode(list2, nod, secondNum);
        }
    }

    private void addNode(List<Node> list, Node nod, String num) {
        if (list == null) {
            list = new ArrayList<>();
            list.add(nod);
            keyMap.put(num, list);
        } else {
            list.add(nod);
        }
    }
    /**
     * 根据IP搜索信息
     * 先根据索引查出对应的List，然后通过折半法查找。
     *
     * @param ip 查询的ip
     * @return 返回结果为null，说明不在范围内(未查到)
     */
    public String searchMessage(long ip) {
        if (ip > max || ip < min) {//排除错误输入的ip
            return null;
        } else {
            String result = null;
            List<Node> list = keyMap.get(("" + ip).substring(0, 2));
            if (list != null) {
                int length = list.size();
                int start = 0;
                int end = length - 1;
                int mid = 0;
                while (start <= end) {          //折半查找索引后的List
                    mid = (start + end) / 2;
                    Node nod = list.get(mid);

                    if (ip > nod.startIP) {     //需要去大的一半查找
                        if (ip <= nod.endIP) {  //就是当前数据
                            result = nod.message;
                            break;
                        } else {              //去大的一半查找
                            if (start == mid) {
                                result = null;
                                break;
                            } else {
                                start = mid;
                            }
                        }
                    } else if (ip < nod.startIP) {//去小的一半找
                        end = mid;
                    } else if (ip == nod.startIP) {
                        result = nod.message;
                        break;
                    }

                }
            }
            return result;
        }
    }

    public static void main(String[] args) {
        IndexNodeMap map = new IndexNodeMap();
        //组装数据
        long t1 = System.currentTimeMillis();
        try {
            //从源文件读取数据
            BufferedReader br = new BufferedReader(new FileReader(new File("/Users/wukun/java/myJavaTest/src/ipip/iprange1")));
            String temp;
            try {
                while ((temp = br.readLine()) != null) {
                    String[] array = temp.split("-");
                    map.buildMap(array);  // 存数组
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        long t2 = System.currentTimeMillis();
        System.out.println("组装共耗时：" + (t2 - t1));

        for (Map.Entry<String, List<Node>> entry : map.keyMap.entrySet()) {
            System.out.println(entry.getKey());
            for (Node node: entry.getValue()) {
                System.out.println("  " + node.startIP + " - " + node.endIP + " - " + node.message);
            }
        }

        System.out.println("Map: " + ObjectSizeCalculator.getObjectSize(map));

        // 测试查找
//        long ip=17436675L;
        long ip = 242648053L;
//        long ip = 993656831L;
        String result = null;

        long s1 = System.currentTimeMillis();
//        for (int i = 0; i < 100000000; i++) {
//            result = map.searchMessage(ip);
//        }
        result = map.searchMessage(ip);

        long s2 = System.currentTimeMillis();
        System.out.println("查找耗时：" + (s2 - s1));
        if (result == null) {
            System.out.println("不在范围内");
        } else {
            System.out.println("所属省代码：" + result);
        }
    }
}

