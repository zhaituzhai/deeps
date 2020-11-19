package com.zhaojm.sort;

import java.util.Arrays;

public class TenSortMethod {

    public static void main(String[] args) {
        // System.out.println(excelColIndex(45));

        System.out.println("bubble sort ==> " + Arrays.toString(bubbleSort(new int[]{9,5,7,5,4,2,1,2})));
        System.out.println("selection sort ==> " + Arrays.toString(selectionSort(new int[]{9,5,7,5,4,2,1,2})));
        System.out.println("change select sort ==> " + Arrays.toString(selectionSortChange(new int[]{9,5,7,5,4,2,1,2})));
        System.out.println("insert sort ==> " + Arrays.toString(insertSort(new int[]{9,5,7,5,4,2,1,2})));
        System.out.println("change insert sort ==> " + Arrays.toString(insertSortChange(new int[]{9,5,7,5,4,2,1,2})));
        System.out.println("shell sort ==> " + Arrays.toString(shellSort(new int[]{9,5,7,5,4,2,1,2})));
        System.out.println("change shell sort ==> " + Arrays.toString(shellSortChange(new int[]{9,5,7,5,4,2,1,2})));
        System.out.println("merge sort ==> " + Arrays.toString(mergeSort(new int[]{9,8,7,6,5,4,3,2,1})));
        // System.out.println("quick sort ==> " + Arrays.toString(quickSort(new int[]{9,5,7,5,4,2,1,2})));
        System.out.println("quick sort ==> " + Arrays.toString(quickSort(new int[]{1,12,5,26,7,14,3,7,2})));
        System.out.println("heap sort ==> " + Arrays.toString(heapSort(new int[]{1,12,5,26,7,14,3,7,2})));
        System.out.println("heap slef sort ==> " + Arrays.toString(heapSortSelf(new int[]{1,12,5,26,7,14,3,7,2})));
    }

    /**
     * 冒泡
     * 时间复杂度 O(n<sup>2</sup>)
     * 空间复杂度 O(1)
     * 稳定
     * @param nums
     * @return
     */
    public static int[] bubbleSort(int[] nums) {
        int temp;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
        return nums;
    }

    /**
     * 选择排序
     * 时间复杂度 O(n<sup>2</sup>)
     * 空间复杂度 O(1)
     * 稳定
     * @param nums
     * @return
     */
    public static int[] selectionSort(int[] nums) {
        int temp;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
        }
        return nums;
    }

    /**
     * 选择排序
     * 时间复杂度 O(n<sup>2</sup>)
     * 空间复杂度 O(1)
     * 稳定
     * @param nums
     * @return
     */
    public static int[] selectionSortChange(int[] nums) {
        int minIndex;
        int temp;
        for (int i = 0; i < nums.length; i++) {
            minIndex = i;
            for (int j = i + 1; j < nums.length; j++) {
                if(nums[minIndex] > nums[j]) {
                    minIndex = j;
                }
            }
            temp = nums[i];
            nums[i] = nums[minIndex];
            nums[minIndex] = temp;
        }
        return nums;
    }

    /**
     * 插入排序
     * @param nums
     * @return
     */
    public static int[] insertSort(int[] nums) {
        int temp;
        for (int i = 1; i < nums.length; i++) {
            for (int j = i; j > 0; j--) {
                if(nums[j] < nums[j - 1]) {
                    temp = nums[j];
                    nums[j] = nums[j - 1];
                    nums[j - 1] = temp;
                }
            }
        }
        return nums;
    }

    /**
     * 插入排序-定位
     * @param nums
     * @return
     */
    public static int[] insertSortChange(int[] nums) {
        int preIndex, current;
        for (int i = 1; i < nums.length; i++) {
            preIndex = i - 1;
            current = nums[i];
            while (preIndex >= 0 && nums[preIndex] > current) {
                nums[preIndex + 1] = nums[preIndex];
                preIndex--;
            }
            nums[preIndex + 1] = current;
        }
        return nums;
    }

    /**
     * 希尔排序：for循环
     * @param nums
     * @return
     */
    public static int[] shellSort(int[] nums) {
        int len = nums.length;
        for (int step = len / 2; step > 0; step = step / 2) {
            int temp;
            for(int start = step; start < len; start++) {
                for (int i = start - step + 1; i > 0; i--) {
                    if(nums[i] < nums[i + step - 2]) {
                        temp = nums[i];
                        nums[i] = nums[i + step - 2];
                        nums[i + step - 2] = temp;
                    } else break;
                }
            }
        }
        return nums;
    }

    /**
     * 希尔排序：while
     * @param nums
     * @return
     */
    public static int[] shellSortChange(int[] nums) {
        int len = nums.length;
        for (int setp = len / 2; setp > 0; setp = setp / 2) {
            int preIndex, current;
            for (int start = setp; start < len; start++) {
                preIndex = start;
                current = nums[start];
                while (preIndex - setp >= 0 && current < nums[preIndex - setp]) {
                    nums[preIndex] = nums[preIndex - setp];
                    preIndex = preIndex - setp;
                }
                nums[preIndex] = current;
            }
        }
        return nums;
    }

    /**
     * 归并排序，迭代版
     * @param nums
     * @return
     */
    public static int[] mergeSort(int[] nums) {
        int len = nums.length;
        if(len < 2) return nums;
        int[] result = new int[nums.length];
        mergeSort(nums, result, 0, len - 1);
        return nums;
    }

    private static void mergeSort(int[] nums, int[] result, int start, int end) {
        // 1、 拆
        if (start >= end) return;
        int len = end - start, mid = (len >> 1) + start;
        int start1 = start, end1 = mid;
        int start2 = mid + 1, end2 = end;
        mergeSort(nums, result, start1, end1);
        mergeSort(nums, result, start2, end2);
        // 2、合并
        int k = start;
        //
        while (start1 <= end1 && start2 <= end2) {
            result[k++] = nums[start1] < nums[start2] ? nums[start1++] : nums[start2++];
        }
        while (start1 <= end1) {
            result[k++] = nums[start1++];
        }
        while (start2 <= end2) {
            result[k++] = nums[start2];
        }
        for (k = start; k <= end; k++) {
            nums[k] = result[k];
        }
    }

    /*public static int[] mergeSort(int[] nums) {
        int len = nums.length;
        if(len < 2) {
            return nums;
        }
        int mid = len / 2;
        int[] result = new int[len];
        mergeSort(nums, result, 0, len - 1);


        return nums;
    }

    private static void mergeSort(int[] nums, int[] result, int start, int end) {
        if (start >= end) return;
        int len = end - start, mid = (len >> 1) + start;
        int start1 = start, end1 = mid;
        int start2 = mid + 1, end2 = end;
        mergeSort(nums, result, start1, end1);
        mergeSort(nums, result, start2, end2);
        int k = start;
        while (start1 <= end1 && start2 <= end2) {
            result[k++] = nums[start1] < nums[start2] ? nums[start1++] : nums[start2++];
        }
        while (start1 <= end1) {
            result[k++] = nums[start1++];
        }
        while (start2 <= end2) {
            result[k++] = nums[start2++];
        }
        for (k = start; k <= end; k++) {
            nums[k] = result[k];
        }
    }*/

    public static String excelColIndex(int col){
        if(col <= 0) return null;
        StringBuilder column = new StringBuilder();
        col--;
        do {
            if(column.length() > 0) {
                col--;
            }
            column.insert(0, (char) (col % 26 + 'A'));
            col = (col - col % 26) / 26;
        } while (col > 0);
        return column.toString();
    }


    public static int[] quickSort(int[] nums) {
        int len = nums.length;

        quickSort(nums, 0, len - 1);

        return nums;
    }

    public static void quickSort(int[] nums, int left, int right) {
        int partition = partition(nums, left, right);
        if (left < partition - 1) {
            quickSort(nums, left, partition - 1);
        }
        if (partition < right) {
            quickSort(nums, partition, right);
        }
    }

    public static int partition(int[] nums, int left, int right) {
        int i = left, j = right;
        int temp;
        int pivot = nums[(left + right) / 2];

        while (i <= j) {
            while (nums[i] < pivot) {
                i++;
            }
            while (nums[j] > pivot) {
                j--;
            }
            if (i <= j) {
                temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                i++;
                j--;
            }
        }

        return i;
    }

    /**
     * 堆排序
     * @param nums
     * @return
     */
    public static int[] heapSort (int[] nums) {
        int len = nums.length;
        // 创建堆
        for (int i = (len - 1) / 2; i >= 0; i--) {
            // 从第一个非叶子节点从下至上，从右至左调整结构
            adjustHeap(nums, i, len);
        }

        for (int i = len - 1; i > 0; i--) {
            int temp = nums[i];
            nums[i] = nums[0];
            nums[0] = temp;

            adjustHeap(nums, 0, i);
        }
        return nums;
    }

    /**
     * 调整堆
     * @param nums 待排序数组
     * @param parent 父节点
     * @param len 待排序列尾元素索引
     */
    private static void adjustHeap(int[] nums, int parent, int len) {
        int temp = nums[parent];
        // 左孩子
        int lChild = 2 * parent + 1;

        while (lChild < len) {
            // 右孩子
            int rChild = lChild + 1;
            if(rChild < len && nums[lChild] < nums[rChild]) {
                lChild++;
            }
            if(temp >= nums[lChild]) {
                break;
            }

            nums[parent] = nums[lChild];
            parent = lChild;
            lChild = 2 * lChild + 1;
        }
        nums[parent] = temp;
    }


    public static int[] heapSortSelf(int[] arr) {
        int len = arr.length;
        // 最后一个非叶子节点 建立堆
        for (int i = (len - 1) / 2; i >= 0; i--) {
            adjustHeapSelf(arr, i, len);
        }
        // 把大根堆的root（最大值）与最后一个节点交换位置，无序位减
        for (int i = len - 1; i > 0; i--) {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;

            adjustHeapSelf(arr, 0, i);
        }
        return arr;
    }

    private static void adjustHeapSelf(int[] arr, int parant, int len) {
        int temp = arr[parant];
        // 获取当前节点的左子节点
        int lChild = 2 * parant + 1;

        // 比较子节点父节点的区别
        while (lChild < len) {
            int rChild = lChild + 1;
            if(rChild < len && arr[lChild] < arr[rChild]) {
                lChild++; // 定位父节点的字节点哪个值大一点
            }
            if (temp > arr[lChild]) {
                break; // 如果父节点的值大于两个字节点，则停止循环
            }
            arr[parant] = arr[lChild];  // 把较小的值往下移
            // 已此节点为根节点继续于子节点比对
            parant = lChild;
            lChild = 2 * parant + 1;
        }
        arr[parant] = temp;
    }

}
