package code.sort;

import java.util.Arrays;

/**
 * @author litao34
 * @ClassName CountSort
 * @Description TODO
 * @CreateDate 2022/6/27-6:21 PM
 **/
public class CountSort {
    public static void main(String[] args) {
        int[] arrays = new int[]{1,2,8,9,13,4,6,87,23,671,12,12,543,6234,324,1421,5236,3246,423,635,32,2,63,623,2345,
                2346,234};
        countSort(arrays);
        System.out.println(Arrays.toString(arrays));
    }

    public static void countSort(int[] args){
        int maxValue = getMaxValue(args);
        int[] tempArgs = new int[maxValue+1];
        for (int arg : args) {
            tempArgs[arg]++;
        }
        int point = 0;
        for (int i = 0 ; i <= maxValue ; i++){
            while (tempArgs[i] > 0){
                args[point] = i;
                point++;
                tempArgs[i]--;
            }
        }
    }

    public static int getMaxValue(int[] args){
        int maxValue = Integer.MIN_VALUE;
        for (int i = 0 ; i < args.length ; i++){
            if (args[i] > maxValue){
                maxValue = args[i];
            }
        }
        return maxValue;
    }

}
