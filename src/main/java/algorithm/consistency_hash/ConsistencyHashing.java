package algorithm.consistency_hash;

import java.util.*;

public class ConsistencyHashing {
    private static final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static class Node {
        private final String ipAddr;
        private final int port;
        private final String nodeName;

        public Node(String ipAddr, int port, String nodeName) {
            this.ipAddr = ipAddr;
            this.port = port;
            this.nodeName = nodeName;
        }

        @Override
        public String toString() {
            return nodeName + ":<" + ipAddr + ":" + port + ">";
        }
    }

    /**
     * Hash环
     **/
    private final SortedMap<Integer, Node> nodeMap = new TreeMap<Integer, Node>();
    /**
     * Hash环放到数组中，加快访问
     */
    private int[] hashVal;
    private final int virtualNums;

    public ConsistencyHashing(Node[] nodes, int virtualNums) {
        this.virtualNums = virtualNums;
        // 初始化一致性Hash环
        for (Node node : nodes) {
            for (int i = 0; i < this.virtualNums; i++) {
                nodeMap.put(hash(node.toString() + i), node);
            }
        }
        // 将Hash环放到数组中，加快访问
        Integer[] tmp = nodeMap.keySet().toArray(new Integer[0]);
        hashVal = copy(tmp);
    }

    public void add(Node node) {
        for (int i = 0; i < this.virtualNums; i++) {
            nodeMap.put(hash(node.toString() + i), node);
        }
        Integer[] tmp = nodeMap.keySet().toArray(new Integer[0]);
        hashVal = copy(tmp);
    }

    public void remove(Node node) {
        for (int i = 0; i < this.virtualNums; i++) {
            nodeMap.remove((hash(node.toString() + i)));
        }
        Integer[] tmp = nodeMap.keySet().toArray(new Integer[0]);
        hashVal = copy(tmp);
    }

    /**
     * 环形中查找下一节点就是在有序整数数组中查找一个大于等于当前值的元素，完全的二分查找。
     * 仅是返回值的判断条件不同。
     */
    public Node getNode(String key) {
        int hash = hash(key);
        int low = 0, high = hashVal.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int midVal = hashVal[mid];
            if (midVal >= hash) {
                if (mid == 0 || hashVal[mid - 1] <= hash) {
                    return nodeMap.get(midVal);
                }
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return nodeMap.get(hashVal[0]);
    }

    private int[] copy(Integer[] src) {
        int[] tmp = new int[src.length];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = src[i];
        }
        return tmp;
    }

    /**
     * 先对key做了 MD5散列值，然后再计算hash值，这样hash值更随机，key在环上就更均衡
     */
    private int hash(String key) {
        try {
            java.security.MessageDigest md5 = java.security.MessageDigest.getInstance("MD5");
            md5.update(key.getBytes());
            byte[] data = md5.digest();
            char[] charArr = new char[32];
            for (int i = 0; i < data.length; i++) {
                charArr[i * 2] = hex[data[i] >>> 4 & 0xF];
                charArr[i * 2 + 1] = hex[data[i] & 0xF];
            }
            return new String(charArr).hashCode();
//            return key.hashCode();
        } catch (Exception e) {
            return Integer.MIN_VALUE;
        }
    }

    public static void main(String[] args) {
        Node[] nodes = new Node[10];
        Map<Node, List<String>> map = new HashMap<>();

        // make nodes
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node("10.1.33.2" + i, 8070, "mynode" + i);
        }
        ConsistencyHashing ch = new ConsistencyHashing(nodes, 200);

        // make keys
        String[] keys = new String[1000000];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = "key" + (i * 17) + "ss" + i * 19;
        }

        // make results
        for (String key : keys) {
            Node n = ch.getNode(key);
            List<String> l = map.computeIfAbsent(n, k -> new ArrayList<>());
            l.add(key);
        }

        //统计标准差，评估服务器节点的负载均衡性。标准差越小，虚拟节点在环上分布越均衡。
        double[] load = new double[nodes.length];
        int x = 0;
        for (Node key : map.keySet()) {
            List<String> l = map.get(key);
            load[x++] = l.size();
        }
        System.out.println(variance(load));
    }

    /**
     * 标准差
     */
    private static double variance(double[] data) {
        double variance = 0;
        double expect = sum(data) / data.length;
        for (double v : data) {
            variance = variance + (Math.pow((v - expect), 2));
        }
        variance = variance / data.length;
        return Math.sqrt(variance);
    }

    private static double sum(double[] data) {
        double sum = 0;
        for (double v : data) {
            sum = sum + v;
        }
        return sum;
    }
}