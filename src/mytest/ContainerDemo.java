package mytest;

import com.sun.javafx.tools.packager.PackagerException;
import proxy.proxyStatic.Hello;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ContainerDemo {

    public static void queue(){
//        Queue<String> queue = new LinkedList<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        while (queue.peek() != null)
            System.out.println("queue: " + queue.poll());
    }

    public static void stack(){
//        Deque<String> stack = new LinkedList<>();
        Deque<String> stack = new ArrayDeque<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        while (stack.peek() != null)
            System.out.println("stack: " + stack.pop());
    }

    public static void reverse(){
//        Deque<String> deque = new LinkedList<>(Arrays.asList(new String[]{"a", "b", "c"}));
        Deque<String> deque = new ArrayDeque<>(Arrays.asList(new String[]{"a", "b", "c"}));
        Iterator<String> it = deque.descendingIterator();
        while (it.hasNext())
            System.out.println("deque: " + it.next());

        deque = new ArrayDeque<>();
        deque.addFirst("a");
        deque.offerLast("b");
        deque.addLast("c");
        deque.addFirst("d");
//        Iterator<String> iterator = deque.iterator();
//        while (iterator.hasNext())
//            System.out.print(" " + iterator.next());
        for (String e : deque) {
            System.out.print(" " + e);
        }
        System.out.println();
        System.out.println(deque.getFirst());
        System.out.println(deque.peekLast());
        System.out.println(deque.removeFirst());
        System.out.println(deque.pollLast());
    }

    public static void hashMap(){
        // test HashMap
        Random random = new Random();
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int i = 0; i < 1000; i++){
            int num = random.nextInt(4);
            Integer count = countMap.get(num);
            if (count == null)
                countMap.put(num, 1);
            else
                countMap.put(num, count+1);

        }
        for (Map.Entry<Integer, Integer> kv: countMap.entrySet())
            System.out.println(kv.getKey() + ":" + kv.getValue());

        // test HashMap
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "aa");
        map.put("a", "aa2");
        map.put("b", "bb");
        map.put("c", "cc");
        System.out.println(map);
        Set<String> keys = map.keySet();  // keys
        for (String key : keys)
            System.out.print(key + " ");
        System.out.println();
        Collection<String> values = map.values();  // values
        for (String value : values)
            System.out.print(value + " ");
        System.out.println();
        Set<String> keys2 = map.keySet();
        for (String key : keys2)
            System.out.print(key + ": " + map.get(key) + " ");
        System.out.println();
        for (Map.Entry<String, String> entry : map.entrySet())
            System.out.print(entry.getKey() + ": " + entry.getValue() + " ");
    }

    public static void hashSet(){
        Set<String> set = new HashSet<>();
        set.add("set_a");
        set.add("set_b");
        set.add("set_c");
        set.addAll(Arrays.asList(new String[]{"set_a", "set_d"}));
        for (String s : set)
            System.out.print(s + " ");
    }

    public static void treeMap(){
//        Map<String, String> map = new TreeMap<>();
//        Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
//        Map<String, String> map = new TreeMap<>(new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o2.compareTo(o1);  // return o1.compareTo(o2);
//            }
//        });
//        Map<String, String> map = new TreeMap<>(Collections.reverseOrder());
        Map<String, String> map = new TreeMap<>(Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
        map.put("a", "aa");
        map.put("d", "dd");
        map.put("c", "cc");
        map.put("C", "CC");
        map.put("BB", "BB");
        for (Map.Entry<String, String> kv : map.entrySet())
            System.out.print(kv.getKey() + ": " + kv.getValue() + " ");

        System.out.println();
//        Map<String, Integer> map2 = new TreeMap<>();
        Map<String, Integer> map2 = new TreeMap<>(new Comparator<String>() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            @Override
            public int compare(String o1, String o2) {
                try {
                    return simpleDateFormat.parse(o1).compareTo(simpleDateFormat.parse(o2));
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
        map2.put("2016-7-3", 100);
        map2.put("2016-7-10", 200);
        map2.put("2016-8-1", 300);
        for (Map.Entry<String, Integer> kv : map2.entrySet())
            System.out.println(kv.getKey() + ": " + kv.getValue());
    }

    public static void treeSet(){
        Set<String> set = new TreeSet<>();
        set.addAll(Arrays.asList(new String[]{"cc", "aa", "bb", "bb"}));
        System.out.println(set);
    }

    public static void linkedHashMap(){
        Map<String, Integer> seqMap = new LinkedHashMap<>();
        seqMap.put("c", 300);
        seqMap.put("d", 400);
        seqMap.put("a", 100);
        seqMap.put("d", 500);
        System.out.println(seqMap);

        System.out.println();
        Map<String, Integer> accessMap = new LinkedHashMap<>(16, 0.75f, true);
        accessMap.put("c", 300);
        accessMap.put("d", 400);
        accessMap.put("a", 100);
        System.out.println(accessMap);
        accessMap.get("c");
        System.out.println(accessMap);
        accessMap.put("d", 500);
        System.out.println(accessMap);
    }

    public static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        private int maxEntries;
        public LRUCache(int maxEntries) {
            super(16, 0.75f, true);
            this.maxEntries = maxEntries;
        }
//        @Override
        protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
            return size() > maxEntries;
        }
    }

    public static void lruCache(){
        LRUCache<String, Object> cache = new LRUCache<>(3);
        cache.put("a", 123);
        cache.put("b", "basic");
        cache.put("c", "call");
        cache.get("a");
        cache.put("d", 456);
        System.out.println(cache);
    }

    public static void enumMap(){
        List<Clothes> clothes = Arrays.asList(new Clothes[]{
            new Clothes("C001", Size2.SMALL), new Clothes("C002", Size2.LARGE),
                new Clothes("C003", Size2.LARGE), new Clothes("C004", Size2.MEDIUM),
                new Clothes("C005", Size2.SMALL), new Clothes("C006", Size2.SMALL)
        });
        Map<Size2, Integer> map = new EnumMap<>(Size2.class);
        for (Clothes c : clothes){
            Size2 size2 = c.getSize();
            Integer count = map.get(size2);
            if (count != null)
                map.put(size2, count + 1);
            else
                map.put(size2, 1);
        }
        System.out.println(map);
    }

    public static void enumSet(){
        Worker[] workers = new Worker[]{
                new Worker("张三", EnumSet.of(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.FRIDAY)),
                new Worker("李四", EnumSet.of(Day.TUESDAY, Day.THURSDAY, Day.SATURDAY)),
                new Worker("王五", EnumSet.of(Day.TUESDAY, Day.THURSDAY))
        };
        System.out.println("哪些天一个人都不来？");
        Set<Day> days = EnumSet.allOf(Day.class);  // 满值的 set
        for (Worker w : workers)
            days.removeAll(w.getAvailableDays());  // 删除所有人的工作日
        System.out.println(days); // 没被删的日子就是都不来的

        System.out.println("哪些天至少有一人来？");
        Set<Day> days1 = EnumSet.noneOf(Day.class);  // 空的 set
        for (Worker w : workers)
            days1.addAll(w.getAvailableDays());  // add 所有人的工作日
        System.out.println(days1);

        System.out.println("哪些天所有人都来");
        Set<Day> days2 = EnumSet.allOf(Day.class);
        for (Worker w : workers)
            days2.retainAll(w.getAvailableDays());  // 交集
        System.out.println(days2);

        System.out.println("哪些人周一和周二这两天都会来");
        Set<Worker> availableworkers = new HashSet<>();
        for (Worker w : workers) {
            if (w.getAvailableDays().containsAll(EnumSet.of(Day.MONDAY, Day.TUESDAY)))
                availableworkers.add(w);
        }
        for (Worker w : availableworkers)
            System.out.println(w.getName());

        System.out.println("哪些天至少有两人来");
        Map<Day, Integer> countMap = new EnumMap<>(Day.class);
        for (Worker w : workers){
            for (Day d : w.getAvailableDays()) {
                Integer count = countMap.get(d);
                countMap.put(d, count==null ? 1 : count+1);
            }
        }
        System.out.println(countMap);
        Set<Day> days3 = EnumSet.noneOf(Day.class);
        for (Map.Entry<Day, Integer> entry : countMap.entrySet()){
            if (entry.getValue() >= 2)
                days3.add(entry.getKey());
        }
        System.out.println(days3);
    }

    public static void priorityQueue() {
//        Queue<Integer> pq = new PriorityQueue<>();
        Queue<Integer> pq = new PriorityQueue<>(11, Collections.reverseOrder());
        pq.offer(10);
        pq.add(22);
        pq.addAll(Arrays.asList(new Integer[]{
                11, 12, 34, 2, 7, 4, 15, 12, 8, 6, 19, 13
        }));
        while (pq.peek() != null){
            System.out.print(pq.poll() + " ");
        }
    }

    public static void priorityQueue2() {
        class Task {
            private int priority;
            private String name;

            public Task(int priority, String name){
                this.priority = priority;
                this.name = name;
            }
            public int getPriority() {
                return priority;
            }

            public String getName() {
                return name;
            }
        }
        Comparator<Task> taskComparator = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if (o1.getPriority() > o2.getPriority())
                    return -1;
                else if (o1.getPriority() < o2.getPriority())
                    return 1;
                return 0;
            }
        };
        Comparator<Task> taskComparator1 = (o1, o2) -> {
            if (o1.getPriority() > o2.getPriority())
                return -1;
            else if (o1.getPriority() < o2.getPriority())
                return 1;
            return 0;
        };

        Queue<Task> taskQueue = new PriorityQueue<>(11, taskComparator1);
        taskQueue.offer(new Task(20, "写日记"));
        taskQueue.offer(new Task(10, "看电视0"));
        taskQueue.offer(new Task(10, "看电视1"));
        taskQueue.offer(new Task(10, "看电视2"));
        taskQueue.offer(new Task(10, "看电视3"));
        taskQueue.offer(new Task(10, "看电视4"));
        taskQueue.offer(new Task(10, "看电视5"));
        taskQueue.offer(new Task(100, "写日记"));
        Task task = taskQueue.poll();
        while (task != null) {
            System.out.println("处理任务：" + task.getName() + "优先级：" + task.getPriority());
            task = taskQueue.poll();
        }
    }

    public static void medianTest(){
        Median median = new Median();
        median.addAll(new int[]{34, 90, 67, 45, 1, 4, 5, 6, 9, 10});
        System.out.println(median.getM());
    }

    public static void main(String[] args){
//        queue();
//        stack();
//        reverse();
//        hashMap();
//        hashSet();
//        treeMap();
//        treeSet();
//        linkedHashMap();
//        lruCache();
//        enumMap();
//        enumSet();
//        priorityQueue();
//        priorityQueue2();
        medianTest();
    }
}

class Worker {
    private String name;
    private Set<Day> availableDays;

    public Worker(String name, Set<Day> availableDays){
        this.name = name;
        this.availableDays = availableDays;
    }

    public String getName() {
        return name;
    }

    public Set<Day> getAvailableDays() {
        return availableDays;
    }
}

enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

enum Size2 {
    SMALL, MEDIUM, LARGE
}

class Clothes {
    String id;
    Size2 size2;

    public Clothes(String id, Size2 size2){
        this.id = id;
        this.size2 = size2;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setSize(Size2 size) {
        this.size2 = size;
    }

    public Size2 getSize() {
        return size2;
    }

    public String getId() {
        return id;
    }
}

class Median {
    private PriorityQueue<Integer> minP;  // minHeap
    private PriorityQueue<Integer> maxP;  // 最大堆
    private Integer m; // 当前中值

    public Median(){
        minP = new PriorityQueue<>();
        maxP = new PriorityQueue<>(11, Collections.reverseOrder());
    }

    public void add(int e){
        if (m == null){
            m = e;
            return;
        }
        if (e < m){
            maxP.add(e);
        } else {
            minP.add(e);
        }

        if (minP.size() - maxP.size() >=2){
            maxP.add(m);
            m = minP.poll();
        } else if (maxP.size() - minP.size() >= 2){
            minP.add(m);
            m = maxP.poll();
        }
    }

    public void addAll(int[] c){
        for (Integer e : c){
            add(e);
        }
    }

    public Integer getM() {
        return m;
    }
}
