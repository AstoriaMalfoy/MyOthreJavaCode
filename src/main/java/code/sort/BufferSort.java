package code.sort;

import javax.swing.*;
import java.util.Arrays;

/**
 * @author litao34
 * @ClassName BufferSort
 * @Description TODO
 * @CreateDate 2022/6/24-10:56 AM
 **/
public class BufferSort {
    public static void main(String[] args) {
        int[] arrays = new int[]{1,2,8,9,13,4,6,87,23,671,12};
        bufferSort(arrays);
        System.out.println(Arrays.toString(arrays));
    }

    public static void bufferSort(int[] arrays){
        for (int  i = 0 ; i < arrays.length ; i ++){
            for (int  j = 0 ; j < arrays.length-1; j++){
                if (arrays[j] > arrays[j+1]){
                    arrays[j] = arrays[j] + arrays[j+1];
                    arrays[j+1] = arrays[j] - arrays[j+1];
                    arrays[j] = arrays[j] - arrays[j+1];
                }
            }
        }
    }
}
