package consistency_hash;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @program:
 * @description: 一致性hash算法
 * @author: wukun
 * @create: 2020-11-19
 **/
public class MyConsistencyHash {
    private static final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private final SortedMap<Integer, String> hashCircle = new TreeMap<>();
    private final int virtualNodeCount;
    private List<String> nodeList;

    public MyConsistencyHash(List<String> nodeList, int virtualNodeCount) {
        this.virtualNodeCount = virtualNodeCount;
        this.nodeList = nodeList;
        initHashCircle();
    }

    /**
     * 初始化一致性Hash环：添加虚拟节点
     */
    public void initHashCircle() {
        for (String node : nodeList) {
            for (int i = 0; i < virtualNodeCount; i++) {
                int hash = getHash(node + i);
                hashCircle.put(hash, node);
                System.out.printf("虚拟节点[ %s ] hash: %s 已添加%n", i, hash);
            }
        }
    }

    /**
     * 通过 key 获取对应的 node节点
     */
    public String getNode(String key) {
        if (key == null || hashCircle.isEmpty()) {
            return null;
        }
        int hash = getHash(key);
        // key 的 hash 是否与 虚拟节点的 hash 相同
        if (!hashCircle.containsKey(hash)) {
            // 获取 key的右子树
            SortedMap<Integer, String> tailMap = hashCircle.tailMap(hash);
            // 如果 key的右子树为空，说明到头了，就取第一个节点，否则取右子树的第一个节点
            hash = tailMap.isEmpty() ? hashCircle.firstKey() : tailMap.firstKey();
        }
        return hashCircle.get(hash);
    }

    private int getHash(String key) {
        try {
            java.security.MessageDigest md5 = java.security.MessageDigest.getInstance("MD5");
            md5.update(key.getBytes());
            byte[] data = md5.digest();
            char[] charArr = new char[32];
            for (int i = 0; i < data.length; i++) {
                charArr[i * 2] = HEX[data[i] >>> 4 & 0xF];
                charArr[i * 2 + 1] = HEX[data[i] & 0xF];
            }
            return new String(charArr).hashCode();
        } catch (Exception e) {
            return Integer.MIN_VALUE;
        }
    }

}