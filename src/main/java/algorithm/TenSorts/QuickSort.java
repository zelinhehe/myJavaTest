package algorithm.TenSorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * quicksort
 * 最佳情况下的时间复杂度 O(nlogn)
 * 最坏情况下的时间复杂度 O(n^2)
 * 参看《算法图解》的分析
 */
public class QuickSort {

    /**
     * 空间复杂度 O(1)
     * 在原数组上交换元素
     */
    public static void quickSort1(int[] a, int left, int right) {
        // 递归终止条件：0个或1个元素，返回
        if (left >= right)
            return;

        // 选择左端点为 pivot
        int pivot = a[left];
        // 记住 pivot在本轮移动完后应该归位的位置。比pivot小的序列中index最大的位置，用于最后和left位置的pivot交换
        int j = left;
        for (int i = left + 1; i <= right; i++) {
            // 小于pivot的放到左边，剩下的自然在右边
            if (a[i] < pivot) {
                swap(a, ++j, i);
            }
        }

        // 交换左端点和pivot位置。将pivot放到中间的位置（归位）
        swap(a, left, j);
        // 递归子序列
        quickSort1(a, left, j - 1);
        quickSort1(a, j + 1, right);
    }

    public static void quickSort3(int[] a, int left, int right) {
        if (left > right)
            return;

        // 选择左端点为pivot
        int pivot = a[left];
        int i = left, j = right;
        while (i != j) {
            // 从右往左找，直到找到比 pivot小的数
            while (a[j] >= pivot && i < j) {
                j--;
            }
            // 从左往右找，直到找到比 pivot大的数
            while (a[i] <= pivot && i < j) {
                i++;
            }
            // 上面的循环结束表示找到了位置或者(i>=j)了，交换两个数在数组中的位置
            if (i < j) {
                swap(a, i, j);
            }
        }
        // 将 pivot放到中间的位置（pivot归位）
        a[left] = a[i];
        a[i] = pivot;

        // 递归，继续向基准的左右两边执行和上面同样的操作
        // i的索引处为上面已确定好的基准值的位置，无需再处理
        quickSort3(a, left, i - 1);
        quickSort3(a, i + 1, right);
    }

    public static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * 空间复杂度 O(nlogn)
     * 每次都开两个临时 list
     */
    public static List<Integer> quickSort2(List<Integer> list) {
        // 递归终止条件
        if (list.size() <= 1) {
            return list;
        }
        Integer pivot = list.get(0);

        List<Integer> less = new ArrayList<>();
        List<Integer> greater = new ArrayList<>();
        // i从1开始，pivot不参与
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) <= pivot) {
                less.add(list.get(i));
            } else {
                greater.add(list.get(i));
            }
        }

        List<Integer> result = new ArrayList<>();
        result.addAll(quickSort2(less));
        result.add(pivot);
        result.addAll(quickSort2(greater));
        return result;
    }

    public static void main(String[] args) {
        int[] ints = {5, 9, 3, 1, 2, 7, 4, 5, 3, 8};
        System.out.println(Arrays.toString(ints));
        quickSort1(ints, 0, ints.length - 1);
        System.out.println(Arrays.toString(ints));

        List<Integer> list = Arrays.asList(5, 9, 3, 1, 2, 7, 4, 5, 3, 8);
        System.out.println(list);
        System.out.println(quickSort2(list));

        int[] ints3 = {5, 9, 3, 1, 2, 7, 4, 5, 3, 8};
        System.out.println(Arrays.toString(ints3));
        quickSort3(ints3, 0, ints3.length - 1);
        System.out.println(Arrays.toString(ints3));
    }
}
