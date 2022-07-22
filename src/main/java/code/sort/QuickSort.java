package code.sort;


import java.util.Arrays;

/**
 * @author litao34
 * @ClassName QuickSort
 * @Description TODO
 * @CreateDate 2022/6/26-7:36 PM
 **/
public class QuickSort {
    public static void main(String[] args) {
        int[] arrays = new int[]{1,2,8,9,13,4,6,87,23,671,12,12,543,6234,324,1421,5236,3246,423,635,32,2,63,623,2345,
                2346,234};
        quickSort(arrays);
        System.out.println(Arrays.toString(arrays));
    }

    public static void quickSort(int[] args){
        quickSort(args,0,args.length);
    }

    public static void quickSort(int[] args,int start,int end){
        if (start + 1 >= end){
            return;
        }
        int tempValue = args[start];
        int middlePoint = start + 1, currentPoint = start + 1;
        while(currentPoint < end){
            if (args[currentPoint]  < tempValue){
                swap(args,middlePoint,currentPoint);
                middlePoint++;
            }
            currentPoint++;
        }
        swap(args,middlePoint-1,start);
        System.out.println("start is :" + start + " the middle is : " + middlePoint + " the end is : " + end);
        System.out.println(" the arrays is " + Arrays.toString(args));
        quickSort(args,start,middlePoint);
        quickSort(args,middlePoint,end);
    }

    public static void swap(int[] args,int swapA,int swapB){
        if (swapA == swapB){
            return;
        }
        args[swapA] += args[swapB];
        args[swapB] = args[swapA] - args[swapB];
        args[swapA] = args[swapA] - args[swapB];
    }
}
