package basicDB;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.Queue;

public class BasicDB {

    private static final int MAX_DATA_LENGTH = 1020;
    // 空白字节
    private static final byte[] ZERO_BYTES = new byte[MAX_DATA_LENGTH];
    // 数据文件后缀
    private static final String DATA_SUFFIX = ".data";
    // 元数据文件后缀，包括索引和空白空间数据
    private static final String META_SUFFIX = ".meta";

    // 索引信息，键->值在.data文件中的位置
    Map<String, Long> indexMap;
    // 空白空间，值为在.data文件中的位置
    Queue<Long> gaps;

    // 值数据文件
    RandomAccessFile db;
    // 元数据文件
    File metaFile;

    public BasicDB() {

    }

    /**
     * private method
     */
    private void loadMeta() {

    }

    private void saveMeta() {

    }

    private void loadIndex() {

    }

    private void saveIndex() {

    }

    private void loadGaps() {

    }

    private void saveGaps() {

    }

    private byte[] getData() {

        return new byte[1];
    }

    private void writeData() {

    }

    private long nextAvailablePos() {
        return 1;
    }

    /**
     * public method
     */
    public void put() {

    }

    public byte[] get() {
        return new byte[1];
    }

    public void remove() {

    }

    public void flush() {

    }

    public void close() {

    }
}
