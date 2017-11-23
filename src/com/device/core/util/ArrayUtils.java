package com.device.core.util;

/** 
 * 
 * 数组操作常用工具类
 * 
 * @author  geek
 * @version  [版本号, 2012-07-26] 
 * @see  [相关类/方法]
 * @since  version 1.0
 */
public final class ArrayUtils {

    /**
     * 交换数组中两元素
     * 
     * @param ints 需要进行交换操作的数组
     * @param x 数组中的位置1
     * @param y 数组中的位置2
     * @return ints 交换后的数组
     */
    public static int[] swap(int[] ints, int x, int y) {
        int temp = ints[x];
        ints[x] = ints[y];
        ints[y] = temp;
        return ints;
    }

    /**
     * 冒泡排序 方法：相邻两元素进行比较
     * 
     * @param source 需要进行排序操作的数组
     * @return source 排序后的数组
     */

    public static int[] bubbleSort(int[] source) {
        for (int i = 1; i < source.length; i++) {
            for (int j = 0; j < i; j++) {
                if (source[j] > source[j + 1]) {
                    swap(source, j, j + 1);
                }
            }
        }
        return source;
    }

    /**
     * 直接选择排序法 方法：每一趟从待排序的数据元素中选出最小（或最大）的一个元素， 顺序放在已排好序的数列的最后，直到全部待排序的数据元素排完。
     * 交换次数比冒泡排序少多了，由于交换所需CPU时间比比较所需的CUP时间多，所以选择排序比冒泡排序快。
     * 但是N比较大时，比较所需的CPU时间占主要地位，所以这时的性能和冒泡排序差不太多，但毫无疑问肯定要快些。
     * 
     * @param source 需要进行排序操作的数组
     * @return source 排序后的数组
     */

    public static int[] selectSort(int[] source) {
        for (int i = 0; i < source.length; i++) {
            for (int j = i + 1; j < source.length; j++) {
                if (source[i] > source[j]) {
                    swap(source, i, j);
                }
            }
        }
        return source;
    }

    /**
     * 插入排序 方法：将一个记录插入到已排好序的有序表（有可能是空表）中,从而得到一个新的记录数增1的有序表。
     * 比较次数是冒泡排序和直接选择排序的一半，而复制所需的CPU时间较交换少，所以性能上比冒泡排序提高一倍多，而比选择排序也要快.
     * 
     * @param source 需要进行排序操作的数组
     * @return 排序后的数组
     */

    public static int[] insertSort(int[] source) {
        for (int i = 1; i < source.length; i++) {
            for (int j = i; (j > 0) && (source[j] < source[j - 1]); j--) {
                swap(source, j, j - 1);
            }
        }
        return source;
    }

    /**
     * 快速排序 快速排序把一个序列（list）分为两个子序列（sub-lists）。 步骤为： 1. 从数列中挑出一个元素，称为 "基准"（pivot）
     * 2.重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。
     * 在这个分割之后，该基准是它的最后位置。这个称为分割（partition）操作。
     * 3.递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。递回的最底部情形，是数列的大小是零或一，
     * 也就是永远都已经被排序好了。
     * 虽然一直递回下去，但是这个算法总会结束，因为在每次的迭代（iteration）中，它至少会把一个元素摆到它最后的位置去。
     * 
     * @param source 需要进行排序操作的数组
     * @return 排序后的数组
     */

    public static int[] quickSort(int[] source) {
        return qsort(source, 0, source.length - 1);
    }

    /**
     * 快速排序的具体实现，排正序
     * 
     * @since 1.1
     * @param source 需要进行排序操作的数组
     * @param low 开始低位
     * @param high 结束高位
     * @return 排序后的数组
     */
    private static int[] qsort(int[] source, int low, int high) {
        int i;
        int j;
        int x;
        if (low < high) {
            i = low;
            j = high;
            x = source[i];
            while (i < j) {
                while (i < j && source[j] > x) {
                    j--;
                }
                if (i < j) {
                    source[i] = source[j];
                    i++;
                }
                while (i < j && source[i] < x) {
                    i++;
                }
                if (i < j) {
                    source[j] = source[i];
                    j--;
                }
            }
            source[i] = x;
            qsort(source, low, i - 1);
            qsort(source, i + 1, high);
        }
        return source;
    }

    /**
     * 二分法查找 查找线性表必须是有序列表
     * 
     * @param source 需要进行查找操作的数字数组
     * @param key 需要查找的值
     * @return int 需要查找的值在数组中的位置，若未查到则返回-1
     */

    public int binarySearch(int[] source, int key) {
        int low = 0, high = source.length - 1, mid;
        while (low <= high) {
            mid = (low + high) >>> 1;
            if (key == source[mid]) {
                return mid;
            } else if (key < source[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 反转数组
     * 
     * @param source 需要进行反转操作的数组
     * @return source 反转后的数组
     */

    public static int[] reverse(int[] source) {
        int length = source.length;
        int temp = 0;
        for (int i = 0; i < length / 2; i++) {
            temp = source[i];
            source[i] = source[length - 1 - i];
            source[length - 1 - i] = temp;
        }
        return source;
    }

    /**
     * 在数组中是否存在某对象
     * @param objs 查询数组
     * @param aim 查找对象
     * @return boolean true：查找到，false：未查找到
     */
    public static boolean isContainObject(Object[] objs, Object aim) {
        for (Object obj : objs) {
            if (aim.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断数组arry中是否存在String str
     * @param array
     * @param str
     * @return boolean
     */
    public static boolean isInStringArray(String[] array, String str) {
        if (null == array || array.length == 0 || null == str || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < array.length; i++) {
            if (str.equalsIgnoreCase(array[i])) {
                return true;
            }
        }
        return false;
    }

    /**
    * 判断数组array[]中是否存在Long value
    * @param array
    * @param value
    * @return boolean
    */
    public static boolean isInLongArray(Long array[], Long value) {
        if (null == array || array.length == 0) {
            return false;
        }
        for (int i = 0; i < array.length; i++) {
            if (value == array[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * String[]转化为Sting，中间用','间隔
     * @param array
     * @return String
     */
    public static String array2String(String[] array) {
        String strArray = "";
        if (null != array && array.length > 0) {
            for (int i = 0; i < array.length; i++) {
                strArray += array[i] + ",";
            }
        }
        if (strArray.length() > 0) {
            strArray = strArray.substring(0, strArray.length() - 1);
        }
        return strArray;
    }

    /**
     * String数组转化为Long型数组
     * @param strArray
     * @return Long[]
     */
    public static Long[] stringArray2LongArray(String[] strArray) {
        if (null == strArray && strArray.length == 0) {
            return null;
        }
        Long[] longArray = new Long[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            try {
                longArray[i] = Long.parseLong(strArray[i]);
            } catch (Exception e) {
                return null;
            }
        }
        return longArray;
    }
}
