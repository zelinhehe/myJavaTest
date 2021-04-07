package algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V> extends LinkedHashMap<K, V> {

    private final int maxEntries;

    public LruCache(int maxEntries) {
        super((int)Math.ceil(maxEntries / 0.75) + 1, 0.75f, true);
        this.maxEntries = maxEntries;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > maxEntries;
    }

    public static void main(String[] args) {
        LruCache<Object, Object> lruCache = new LruCache<>(3);
        lruCache.put(1, 11);
        lruCache.put(3, 33);
        lruCache.put(2, 22);
        System.out.println(lruCache);

        lruCache.put(4, 44);
        System.out.println(lruCache);

        lruCache.get(2);
        System.out.println(lruCache);
    }
}
