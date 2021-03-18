package algorithm;

import java.util.Arrays;

/**
 * n 个长度最大为 m 的有序数组（从小到大），如何合并成一个大的有序数组？
 * 实现如下函数，并估计算法的时间复杂度。请尽量优化你的算法。
 * <p>
 * 注：不允许使用标准库的排序算法
 * <p>
 * 函数签名（Java）：List<Integer> merge(List<List<Integer>>)
 * 样例输入：{{1, 3}, {2, 4}, {5, 6}}
 * 样例输出：{1, 2, 3, 4, 5, 6}
 * <p>
 * 解法：
 * N个数组进行两两合并，合并后的数组再继续执行合并过程，最后合成 N*M 的有序数组。
 * 可以认为合并这个递归过程发生了 logN 次，每一次合并的过程都是 N*M 个数合并，
 * 所以每一次合并的时间复杂度为 N*M，总的时间复杂度就是 N*M*logN
 *
 * @author wukun
 */
public class MergeSortedArray {

    public static void main(String[] args) {
        int[][] ints = {{}, {1, 3}, {2}, {4, 6}, {7, 9}, {4, 5, 6}};
        System.out.println(Arrays.deepToString(ints));
        System.out.println(Arrays.toString(merge(ints)));
    }

    //两两合并数组
    public static int[] merge(int[][] arrays) {
        if (arrays.length == 0) {
            return new int[]{};
        }
        // 只有一个数组了，表示合并完成
        if (arrays.length == 1) {
            return arrays[0];
        }

        // 本轮两两合并后的数组个数
        int newArrayCount = arrays.length % 2 == 0 ? arrays.length / 2 : arrays.length / 2 + 1;
        System.out.println("newArrayCount：" + newArrayCount);
        // 本轮两两合并后的新数组
        int[][] result = new int[newArrayCount][];
        // 两两合并
        for (int i = 0; i < arrays.length; i += 2) {
            // 如果有奇数个数组，那么给最后一个数组配对一个空数组
            int[] lastNum = i + 1 >= arrays.length ? new int[]{} : arrays[i + 1];
            result[i / 2] = mergeTwoArray(arrays[i], lastNum);
        }
        System.out.println(Arrays.deepToString(result));
        return merge(result);
    }

    // 对两个数组进行合并，成为一个新数组
    public static int[] mergeTwoArray(int[] a1, int[] a2) {
        int[] newArray = new int[a1.length + a2.length];
        int p1 = 0, p2 = 0, index = 0;

        while (p1 < a1.length && p2 < a2.length) {
            if (a1[p1] < a2[p2]) {
                newArray[index++] = a1[p1++];
            } else {
                newArray[index++] = a2[p2++];
            }
        }
        while (p1 < a1.length) {
            newArray[index++] = a1[p1++];
        }
        while (p2 < a2.length) {
            newArray[index++] = a2[p2++];
        }
        return newArray;
    }
}
