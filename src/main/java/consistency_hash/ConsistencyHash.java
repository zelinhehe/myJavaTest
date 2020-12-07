package consistency_hash;

import java.util.*;
import java.util.stream.Collectors;

public class ConsistencyHash {

    private final SortedMap<Integer, String> hashCircle = new TreeMap<>();
    private final int virtualNodeCount;
    private List<String> nodeList;

    public ConsistencyHash(List<String> nodeList, int virtualNodeCount) {
        this.virtualNodeCount = virtualNodeCount;
        this.nodeList = nodeList;
        nodeList.forEach(x -> addNode(x));
    }

    private Long getHash(byte[] digest, int nTime) {
        long rv = ((long) (digest[3 + nTime * 4] & 0xFF) << 24)
                | ((long) (digest[2 + nTime * 4] & 0xFF) << 16)
                | ((long) (digest[1 + nTime * 4] & 0xFF) << 8)
                | (digest[0 + nTime * 4] & 0xFF);
        /* Truncate to 32-bits */
        return rv & 0xffffffffL;
    }

    /**
     * FNV1_32_HASH算法
     */
    private int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        // 如果算出来的值为负数则取其绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }

    /**
     * 添加节点
     */
    public void addNode(String node) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < virtualNodeCount; i++) {
            int hash = getHash(node + i);
            hashCircle.put(hash, node);
            System.out.printf("虚拟节点[ %s ] hash: %s 已添加%n", i, hash);
        }
    }

    /**
     * 获取node节点
     */
    public String getNode(String key) {
        if (key == null) {
            return null;
        }
        if (hashCircle.isEmpty()) {
            return null;
        }
        int hash = getHash(key);
        if (!hashCircle.containsKey(hash)) {
            //未命中对应的节点
            SortedMap<Integer, String> tailMap = hashCircle.tailMap(hash);
            hash = tailMap.isEmpty() ? hashCircle.firstKey() : tailMap.firstKey();
        }
        return hashCircle.get(hash);
    }

    public static void main(String[] args) {

        Map<String, Integer> nodeKeyCount = new HashMap<>();
        int virtualNodeCount = 100;
        List<String> nodeList = new LinkedList<>();
        nodeList.add("192.168.1.1");
        nodeList.add("192.168.1.2");
        nodeList.add("192.168.1.3");
        nodeList.add("192.168.1.4");
        nodeList.add("192.168.1.5");
        nodeList.add("192.168.1.6");
        nodeList.add("192.168.1.7");
        nodeList.add("192.168.1.8");
        nodeList.add("192.168.1.9");
        nodeList.add("192.168.1.10");
        ConsistencyHash consistencyHash = new ConsistencyHash(nodeList, virtualNodeCount);
        for (int i = 0; i < 1000000; i++) {
            String key = consistencyHash.getNode("key" + i);
            int count = 0;
            if (nodeKeyCount.containsKey(key)) {
                count = nodeKeyCount.get(key);
            }
            nodeKeyCount.put(key, count + 1);
        }
        nodeKeyCount.forEach((key, val) -> System.out.printf("node:%s,总数：%s%n", key, val));
        System.out.printf("标准差:%s%n",
                standardDiviation(nodeKeyCount.values().stream()
                        .map(x -> (double) x).collect(Collectors.toList())));
    }

    public static double Variance(List<Double> x) {
        int m = x.size();
        double sum = 0;
        //求和
        for (Double value : x) {
            sum += value;
        }
        //求平均值
        double dAve = sum / m;
        double dVar = 0;
        //求方差
        for (Double aDouble : x) {
            dVar += (aDouble - dAve) * (aDouble - dAve);
        }
        return dVar / m;
    }

    //标准差σ=sqrt(s^2)
    public static double standardDiviation(List<Double> x) {
        int m = x.size();
        double sum = 0;
        //求和
        for (Double aDouble : x) {
            sum += aDouble;
        }
        //求平均值
        double dAve = sum / m;
        double dVar = 0;
        //求方差
        for (Double aDouble : x) {
            dVar += (aDouble - dAve) * (aDouble - dAve);
        }
        // return Math.sqrt(dVar/(m-1));
        return Math.sqrt(dVar / m);
    }
}