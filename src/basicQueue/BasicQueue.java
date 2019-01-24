package basicQueue;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;

public class BasicQueue {
    // 队列最多消息个数
    private static final int MAX_MSG_NUM = 3;

    // 消息体最大长度
    private static final int MAX_MSG_BODY_SIZE = 10;

    // 每条消息占用的空间
    private static final int MSG_SIZE = MAX_MSG_BODY_SIZE + 4;

    // 队列消息体数据文件大小
    private static final int DATA_FILE_SIZE = MAX_MSG_NUM * MSG_SIZE;

    // 队列元数据文件大小 (head + tail)
    private static final int META_SIZE = 8;

    private MappedByteBuffer dataBuf;
    private MappedByteBuffer metaBuf;

    public BasicQueue(String path, String queueName) throws IOException {
        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }
        RandomAccessFile dataFile = null;
        RandomAccessFile metaFile = null;
        try {
            dataFile = new RandomAccessFile(path + queueName + ".data", "rw");
            metaFile = new RandomAccessFile(path + queueName + ".meta", "rw");

            dataBuf = dataFile.getChannel().map(MapMode.READ_WRITE, 0, DATA_FILE_SIZE);
            metaBuf = metaFile.getChannel().map(MapMode.READ_WRITE, 0, META_SIZE);
        } finally {
            if (dataFile != null) {
                dataFile.close();
            }
            if (metaFile != null) {
                metaFile.close();
            }
        }
    }

    private int head() {  // 当前 head的值
        int head = metaBuf.getInt(0);
        System.out.println("== head(): " + head);
        return head;
    }

    private void head(int newHead) {  // 设置 head的值
        System.out.println("== head(int newHead): " + newHead);
        metaBuf.putInt(0, newHead);
    }

    private int tail() {  // 当前 tail的值
        int tail = metaBuf.getInt(4);
        System.out.println("== tail(): " + tail);
        return tail;
    }

    private void tail(int newTail) {  // 设置 tail的值
        System.out.println("== tail(int newTail): " + newTail);
        metaBuf.putInt(4, newTail);
    }

    private boolean isEmpty(){  // head == tail 标识为空队列，不一定都是0
        int head = head();
        int tail = tail();
        System.out.println("== isEmpty() head: " + head + " tail: " + tail);
        return head == tail;
    }

    /**
     * 如果再插一条，是不是就满了？如果是，就表示满了。
     * 实际上还是有一条信息的空间，为什么不让插呢？是为了和"空队列"做区分，如果真的插满了，那么 tail == head，这是标识空队列的
     */
    private boolean isFull(){
        int tail = tail();
        int head = head();
        System.out.println("== isFull() head: " + head + " tail: " + tail);
        System.out.println("== isFull() ((tail + " + MSG_SIZE + ") % " + DATA_FILE_SIZE + " " + (tail + MSG_SIZE) % DATA_FILE_SIZE);
        return ((tail + MSG_SIZE) % DATA_FILE_SIZE) == head;
    }

    public void enqueue(byte[] data) throws IOException {
        if (data.length > MAX_MSG_BODY_SIZE) {
            throw new IllegalArgumentException("msg size is " + data.length
                    + ", while maximum allowed length is " + MAX_MSG_BODY_SIZE);
        }
        if (isFull()) {
            throw new IllegalStateException("queue is full");
        }
        int tail = tail();
        dataBuf.position(tail);
        dataBuf.putInt(data.length);
        dataBuf.put(data);

        if (tail + MSG_SIZE >= DATA_FILE_SIZE) {  // 如果插这一条后就满了，那么 tail再只向起始位置，否则往后移动 MSG_SIZE个字节
            tail(0);
        } else {
            tail(tail + MSG_SIZE);
        }
    }

    public byte[] dequeue() throws IOException {
        if (isEmpty()) {
            return null;
        }
        int head = head();
        dataBuf.position(head);
        int length = dataBuf.getInt();
        byte[] data = new byte[length];
        dataBuf.get(data);

        if (head + MSG_SIZE >= DATA_FILE_SIZE) {
            head(0);
        } else {
            head(head + MSG_SIZE);
        }
        return data;
    }
}
