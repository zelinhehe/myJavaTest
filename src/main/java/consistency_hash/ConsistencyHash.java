package consistency_hash;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

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
            int hash = getHash(node+i);
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
}