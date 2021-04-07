package algorithm.TenSorts;

import java.util.Arrays;

/**
 * https://sort.hust.cc/1.bubblesort
 */
public class BubbleSort {

    public static int[] sort(int[] sourceArray) {
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        for (int i = 1; i < arr.length; i++) {
            // 标记为true，表示此次循环没有交换，排序已经完成。
            boolean flag = true;

            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    flag = false;
                }
            }

            if (flag) {
                break;
            }
        }
        return arr;
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        int[] ints = {5, 9, 3, 1, 2, 7, 4, 5, 3, 8};
        System.out.println(Arrays.toString(ints));
        int[] result = BubbleSort.sort(ints);
        System.out.println(Arrays.toString(result));
    }
}
