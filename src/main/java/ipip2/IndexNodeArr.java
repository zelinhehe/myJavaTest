package ipip2;

import java.io.*;
import java.util.*;

/**
 *
 */
public class IndexNodeArr {
    public static List<List<Node>> arr = new ArrayList<>();
    static {
        for (int i = 0; i < 100; i++) {
            arr.add(new ArrayList<>());
        }
    }

    // 内部节点类 用于存储信息
    private class Node {
        private long startIP;      //起始IP
        private long endIP;        //结束IP
        private int code;          //省代码
    }

    // 组装数据
    private void buildArr(String[] message) {
        System.out.println(Arrays.toString(message));
        int firstNum = Integer.parseInt(message[0].substring(0, 2));
        int secondNum = Integer.parseInt(message[1].substring(0, 2));

        long startIP = Long.parseLong(message[0]);
        long endIP = Long.parseLong(message[1]);

        Node nod = new Node();
        nod.startIP = startIP;
        nod.endIP = endIP;
        nod.code = Integer.parseInt(message[2]);

        List<Node> list = arr.get(firstNum);
        list.add(nod);

        if (firstNum != secondNum) {  // 不相等
            List<Node> list1 = arr.get(secondNum);
            list1.add(nod);
        }
    }

    /**
     * 根据IP搜索信息
     * 先根据索引查出对应的List，然后通过折半法查找。
     *
     * @param ip 查询的ip
     * @return 返回结果为null，说明不在范围内(未查到)
     */
    public int searchMessage(long ip) {
        int result = 0;
        List<Node> list = arr.get(Integer.parseInt(("" + ip).substring(0, 2)));
        if (list.size() > 0) {
            int length = list.size();
            int start = 0;
            int end = length - 1;
            int mid = 0;
            while (start <= end) {          //折半查找索引后的List
                mid = (start + end) / 2;
                Node nod = list.get(mid);

                if (ip > nod.startIP) {     //需要去大的一半查找
                    if (ip <= nod.endIP) {  //就是当前数据
                        result = nod.code;
                        break;
                    } else {              //去大的一半查找
                        if (start == mid) {
                            result = 0;
                            break;
                        } else {
                            start = mid;
                        }
                    }
                } else if (ip < nod.startIP) {//去小的一半找
                    end = mid;
                } else if (ip == nod.startIP) {
                    result = nod.code;
                    break;
                }

            }
        }
        return result;
    }

    public static void main(String[] args) {
        IndexNodeArr nodeArr = new IndexNodeArr();
        //组装数据
        long t1 = System.currentTimeMillis();
        try {
            //从源文件读取数据
            BufferedReader br = new BufferedReader(new FileReader(new File("/Users/wukun/java/myJavaTest/src/ipip/iprange")));
            String temp;
            try {
                while ((temp = br.readLine()) != null) {
                    String[] array = temp.split("-");
                    nodeArr.buildArr(array);  // 存数组
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        long t2 = System.currentTimeMillis();
        System.out.println("组装共耗时：" + (t2 - t1));

        for (int i = 0; i < 100; i++) {
            if (arr.get(i).size() > 0) {
                System.out.println(i);
                for (Node node : arr.get(i)) {
                    System.out.println("  " + node.startIP + " - " + node.endIP + " - " + node.code);
                }
            }
        }

        // 测试查找
//        long ip=17436675L;
        long ip = 242648053L;
//        long ip = 993656831L;
        int result;

        long s1 = System.currentTimeMillis();
//        for (int i = 0; i < 100000000; i++) {
//            result = map.searchMessage(ip);
//        }
        result = nodeArr.searchMessage(ip);

        long s2 = System.currentTimeMillis();
        System.out.println("查找耗时：" + (s2 - s1));
        if (result == 0) {
            System.out.println("不在范围内");
        } else {
            System.out.println("所属省代码：" + result);
        }
    }
}

