package code.sort;

import com.sun.org.apache.bcel.internal.generic.INEG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author litao34
 * @ClassName BucketSort
 * @Description TODO
 * @CreateDate 2022/6/27-7:14 PM
 **/
public class BucketSort {
    public static void main(String[] args) {
        int[] arrays = new int[]{1,2,8,9,13,4,6,87,23,671,12,12,543,6234,324,1421,5236,3246,423,635,32,2,63,623,2345,
                2346,234};
        bucketSort(arrays);
        System.out.println(Arrays.toString(arrays));
    }


    private static final Integer bucketSize = 10;

    public static void bucketSort(int[] args){
        args =  bucketSort(args,bucketSize);
    }

    public static int[] bucketSort(int[] args,int bucketSize){
        int[][] buckets = new int[bucketSize][0];
        int minValue = Integer.MAX_VALUE,maxValue = Integer.MIN_VALUE;
        for (int arg : args) {
            if (arg < minValue){minValue = arg;}
            if (maxValue < arg){maxValue = arg;}
        }
        for (int arg : args) {
            int bucketInext = getBucketInext(minValue, maxValue, bucketSize, arg);
            buckets[bucketInext] =  arrAppent(buckets[bucketInext],arg);
        }
        for (int[] bucket : buckets) {
            QuickSort.quickSort(bucket);
        }
        int[] merge = merge(buckets,args);
        return merge;
    }

    private static int[] merge(int[][] buckets,int[] args) {
        int[] result = new int[args.length];
        int flat = 0;
        for (int[] bucket : buckets) {
            for (int i : bucket) {
                args[flat] = i;
                flat++;
            }
        }
        return result;
    }

    /**
     * array自动扩容
     * @param arrays
     * @param value
     * @return
     */
    public static int[] arrAppent(int[] arrays,int value){
        arrays = Arrays.copyOf(arrays,arrays.length+1);
        arrays[arrays.length-1] = value;
        return arrays;
    }

    /**
     * 散列函数
     * @param minValue
     * @param maxValue
     * @param bucketSize
     * @param value
     * @return
     */
    public static int getBucketInext(int minValue,int maxValue,int bucketSize,int value){
        int result = 0;
        int step = ((maxValue - minValue) / bucketSize) + 1;
        while(minValue + step < value){
            result++;
            minValue+=step;
        }
        return result;
    }



}
