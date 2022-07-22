package code.sort;

import java.util.Arrays;

/**
 * @author litao34
 * @ClassName ShellSort
 * @Description TODO
 * @CreateDate 2022/6/24-5:22 PM
 **/
public class ShellSort {
    public static void main(String[] args) {
        int[] arrays = new int[]{1,2,8,9,13,4,6,87,23,671,12,12,543,6234,324,1421,5236,3246,423,635,32,2,63,623,2345,
                2346,234};
        shellSort(arrays);
        System.out.println(Arrays.toString(arrays));
    }

// 定义初始间隔
private static final int CAPE = 5;

public static void shellSort(int[] arrays){
    int atomCape = CAPE;
    while(atomCape > 0){
        for (int i = 0 ; i < arrays.length; i+=atomCape){
            int minValue = Integer.MAX_VALUE , minValueIndex = -1;
            for (int j = i; j<arrays.length; j+= atomCape){
                if (arrays[j] < minValue){
                    minValue = arrays[j];
                    minValueIndex = j;
                }
            }
            arrays[minValueIndex] = arrays[i];
            arrays[i] = minValue;
        }
        // 增量递减
        atomCape--;
    }
}
}
