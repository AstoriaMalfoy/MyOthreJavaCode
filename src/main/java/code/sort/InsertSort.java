package code.sort;

import java.util.Arrays;

/**
 * @author litao34
 * @ClassName InsertSort
 * @Description TODO
 * @CreateDate 2022/6/24-4:47 PM
 **/
public class InsertSort {
    public static void main(String[] args) {
        int[] arrays = new int[]{1,2,8,9,13,4,6,87,23,671,12};
        insertSort(arrays);
        System.out.println(Arrays.toString(arrays));
    }

    public static void insertSort(int[] arrays){
        for (int i = 0 ; i < arrays.length; i++){
            int temp = arrays[i];
            int flag = i-1;
            while(flag >= 0 && arrays[flag] > temp){
                arrays[flag+1 ] = arrays[flag];
                arrays[flag] = temp;
                flag--;
            }
        }
    }
}
