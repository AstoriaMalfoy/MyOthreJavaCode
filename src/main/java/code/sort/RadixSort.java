package code.sort;

import java.util.Arrays;

/**
 * @author litao34
 * @ClassName RadixSort
 * @Description TODO
 * @CreateDate 2022/6/27-8:04 PM
 **/
public class RadixSort {
    public static void main(String[] args) {
        int[] arrays = new int[]{1,2,8,9,13,4,6,87,23,671,12,12,543,6234,324,1421,5236,3246,423,635,32,2,63,623,2345,
                2346,234};
        radixSort(arrays);
        System.out.println(Arrays.toString(arrays));
    }

public static void radixSort(int[] args){
    int maxValue = Integer.MIN_VALUE;
    for (int i=0;i<args.length;i++){
        if (args[i] > maxValue){maxValue = args[i];}
    }
    int maxLength = getLength(maxValue);
    for (int i = 0 ;i <= maxLength ; i++){
        refList(args,i);
    }
}

/**
 * 按照某一位进行排序
 * @param args      数组
 * @param position  位置
 */
private static void refList(int[] args, int position) {
    int[][] tempArgs = new int[10][0];
    for (int i = 0 ; i < args.length; i++){
        int positionValue = getPositionValue(args[i], position);
        tempArgs[positionValue] =  arrAppent(tempArgs[positionValue],args[i]);
    }
    int posiont = 0;
    for (int[] tempArg : tempArgs) {
        for (int i : tempArg) {
            args[posiont] = i;
            posiont++;
        }
    }
}

/**
 * 获取一个整数第position位置的数值
 * @param value     值
 * @param position  位置(个位:0 , 十位:1 , 百位:2 ,...)
 * @return
 */
public static int getPositionValue(int value,int position){
    int div = 10;
    while (position > 0){
        div *= 10;
        position--;
    }
    return (value % div) / (div / 10);
}

/**
 * 获取一个整数的位数
 * @param value
 * @return
 */
public static int getLength(int value){
    int result = 0;
    while (value > 0){
        result++;
        value/=10;
    }
    return result;
}

/**
 * 向数组中动态添加元素
 * @param arrays    数组
 * @param value     添加的元素
 * @return
 */
public static int[] arrAppent(int[] arrays,int value){
    arrays = Arrays.copyOf(arrays,arrays.length+1);
    arrays[arrays.length-1] = value;
    return arrays;
}

}
