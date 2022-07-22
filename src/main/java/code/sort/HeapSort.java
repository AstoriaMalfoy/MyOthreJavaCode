package code.sort;

import java.util.Arrays;

/**
 * @author litao34
 * @ClassName HeapSort
 * @Description TODO
 * @CreateDate 2022/6/27-3:35 PM
 **/
public class HeapSort {
    public static void main(String[] args) {
        int[] arrays = new int[]{1,2,8,9,13,4,6,87,23,671,12,12,543,6234,324,1421,5236,3246,423,635,32,2,63,623,2345,
                2346,234};
        heapSort(arrays);
        System.out.println(Arrays.toString(arrays));
    }

    public static void heapSort(int[] args){
        int length = args.length;
        while (length > 0){
            buildHead(args,length);
            swapPoint(args,0,--length);
        }
    }

    public static void buildHead(int[] args,int length){
        int position = length / 2;
        while( position >= 0 ){
            maintainHeap(args , position,length);
            position--;
        }
    }

    public static void maintainHeap(int[] args,int point,int length){
        if (2 * ( point + 1 ) - 1 >= length){
            return;
        }
        int leftPoint,rihtPoint;
        boolean containRight = (rihtPoint = 2 * ( point + 1 ))  < length;
        leftPoint = rihtPoint - 1;
        if (containRight && !(args[leftPoint] < args[point] && args[rihtPoint] < args[point])){
            if (args[leftPoint] < args[rihtPoint]){
                swapPoint(args,rihtPoint,point);
                maintainHeap(args,rihtPoint,length);
            }else{
                swapPoint(args,leftPoint,point);
                maintainHeap(args,leftPoint,length);
            }
        }else{
            if (args[point] < args[leftPoint]){
                swapPoint(args,point,leftPoint);
            }
        }

    }

    private static void swapPoint(int[] args, int swapA, int swapB) {
        if (swapA == swapB){
            return;
        }
        if (swapA > args.length || swapB > args.length){
            return;
        }
        args[swapA] += args[swapB];
        args[swapB] = args[swapA] - args[swapB];
        args[swapA] = args[swapA] - args[swapB];
    }


    /**
     *
     *      0
     *      1   2
     *      3 4 5 length = 6
     *
     */
}
