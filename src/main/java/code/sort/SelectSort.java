package code.sort;

import java.util.Arrays;

/**
 * @author litao34
 * @ClassName SelectSort
 * @Description TODO
 * @CreateDate 2022/6/24-3:30 PM
 **/
public class SelectSort {
    public static void main(String[] args) {
        int[] arrays = new int[]{1,2,8,9,13,4,6,87,23,671,12};
        selectSort(arrays);
        System.out.println(Arrays.toString(arrays));
    }

    public static void selectSort(int[] args){
        for (int i = 0 ; i < args.length; i++){
            int minValue = Integer.MAX_VALUE , minValueIndex = -1;
            for (int j = i; j<args.length; j++){
                if (args[j] < minValue){
                    minValue = args[j];
                    minValueIndex = j;
                }
            }
            args[minValueIndex] = args[i];
            args[i] = minValue;
        }
    }
}
