package mytest;

import java.util.Arrays;
import java.util.Random;

public class DynamicArray<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private int size;
    private Object[] elementData;

    public DynamicArray() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    private void ensureCapacity(int minCapacity){
        int oldCapacity = elementData.length;
        if (oldCapacity >= minCapacity)
            return;
        int newCapacity = oldCapacity * 2;
        if (newCapacity < minCapacity)
            newCapacity = minCapacity;
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    public void add(E e){
        ensureCapacity(size + 1);
        elementData[size++] = e;
    }

    public E remove(int index){
        E oldValue = (E) elementData[index];
        int numMoved = size - (index + 1);
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index, numMoved);
        elementData[--size] = null;
        return oldValue;
    }

    public E get(int index){ return (E)elementData[index]; }

    public int size() { return size; }

    public E set(int index, E element){
        E oldValue = get(index);
        elementData[index] = element;
        return oldValue;
    }

    public void print(){
        for (int i = 0; i < size(); i++)
            System.out.println(elementData[i]);
    }

    public <T extends E> void addAll(DynamicArray<T> c){
        for (int i=0; i<c.size(); i++)
            add(c.get(i));
    }
    public void addAll2(DynamicArray<? extends E> c){
        for (int i=0; i<c.size(); i++)
            add(c.get(i));
    }

    public void addAll0(DynamicArray<E> c){
        for (int i = 0; i < c.size(); i++)
            add(c.get(i));
    }

    public int indexOf0(DynamicArray<E> arr, E elm){
        for (int i = 0; i < arr.size(); i++){
            if (arr.get(i).equals(elm)) return i;
        }
        return -1;
    }

    public static <T> int indexOf1(DynamicArray<T> arr, T elm){
        for (int i = 0; i < arr.size(); i++){
            if (arr.get(i).equals(elm)) return i;
        }
        return -1;
    }

    public static int indexOf(DynamicArray<?> arr, Object elm){
        for (int i = 0; i < arr.size(); i++){
            if (arr.get(i).equals(elm)) return i;
        }
        return -1;
    }

    public void copyTo0(DynamicArray<E> dest){
        for(int i = 0; i < size; i++)
            dest.add(get(i));
    }
    public void copyTo(DynamicArray<? super E> dest){
        for(int i = 0; i < size; i++)
            dest.add(get(i));
    }

    public static void test1(){
        DynamicArray<Double> arr = new DynamicArray<>();
        for (int i = 0; i < 13; i++)
            arr.add(Math.random());
        arr.set(6, 6.0);
        arr.print();

        System.out.println(arr.size);

        arr.remove(7);

        arr.print();
        System.out.println(arr.size);

        Random random = new Random();
        System.out.println(random.nextInt(10));
    }

    public static void test2(){
        DynamicArray<Number> numbers = new DynamicArray<>();
        DynamicArray<Integer> ints = new DynamicArray<>();
        DynamicArray<Integer> ints2 = new DynamicArray<>();
        DynamicArray<Double> doubles = new DynamicArray<>();
        ints.add(100);
        ints.add(34);
        doubles.add(1.1);
        doubles.add(1.2);
        numbers.addAll(ints);
        numbers.addAll2(ints);
        numbers.addAll(doubles);
        numbers.print();

        ints2.addAll0(ints);
        ints2.print();
    }

    public static void test3(){
        DynamicArray<Integer> ints = new DynamicArray<>();
        ints.add(1);
        ints.add(2);
        System.out.println(ints.indexOf1(ints, 2));
    }

    public static void testCopyTo(){
        DynamicArray<Integer> ints = new DynamicArray<>();
        DynamicArray<Integer> ints1 = new DynamicArray<>();
        DynamicArray<Number> numbers = new DynamicArray<>();
        DynamicArray<Number> numbers2 = new DynamicArray<>();
        ints.add(1);
        ints.add(2);
        ints.copyTo(numbers);
        numbers.print();
        numbers.copyTo(numbers2);
        numbers2.print();
    }

    public static void main(String[] args){
//        test1();
//        test2();
//        test3();
        testCopyTo();
    }
}
