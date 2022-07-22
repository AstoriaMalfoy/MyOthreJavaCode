package code.sort;

import java.util.Arrays;

/**
 * @author litao34
 * @ClassName MergeSort
 * @Description TODO
 * @CreateDate 2022/6/24-6:23 PM
 **/
public class MergeSort {
    public static void main(String[] args) {
        int[] arrays = new int[]{1,2,8,9,13,4,6,87,23,671,12,12,543,6234,324,1421,5236,3246,423,635,32,2,63,623,2345,
                2346,234};
        mergeSort(arrays);
        System.out.println(Arrays.toString(arrays));
    }

public static void mergeSort(int[] args){
    mergeSort(args,0,args.length);
}
// 左闭右开
public static void mergeSort(int[] rags,int start,int end){
    // 区间临界条件
    if (start + 1 >= end){
        return;
    }
    int middle = (start + end) / 2;
    mergeSort(rags,start,middle);
    mergeSort(rags,middle,end);
    merge(rags,start,middle,middle,end);

}
public static void merge(int[] args,int leftStart,int leftEnd,int rightStart,int rightEnd){
    System.out.println("marge [" + leftStart + "," + leftEnd + "] to [" + rightStart + "," + rightEnd + "]  " +
            "\nbefore : " + Arrays.toString(args));
    int curse = leftStart;
    int[] leftTemp = new int[leftEnd - leftStart];
    for (int i = leftStart ; i < leftEnd ; i ++){
        leftTemp[i - leftStart] = args[i];
    }
    leftEnd -= leftStart;leftStart = 0;
    while(leftStart < leftEnd || rightStart < rightEnd){
        if (leftStart < leftEnd && rightStart < rightEnd){
            if (args[rightStart] < leftTemp[leftStart]){
                args[curse] = args[rightStart];
                rightStart++;
                curse++;
            }else{
                args[curse] =  leftTemp[leftStart];
                leftStart++;
                curse++;
            }
        }else{
            if (leftEnd <= leftStart){
                args[curse] = args[rightStart];
                rightStart++;
                curse++;
            }else{
                args[curse] = leftTemp[leftStart];
                leftStart++;
                curse++;
            }
        }
    }
    System.out.println("after :  " + Arrays.toString(args));
}

    public static void swap(int[]args,int swapA,int swapB){
        if (swapA == swapB){
            return;
        }
        args[swapA] += args[swapB];
        args[swapB] = args[swapA] - args[swapB];
        args[swapA] = args[swapA] - args[swapB];
    }
}
