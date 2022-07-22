package code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author litao34
 * @ClassName Sort
 * @Description TODO
 * @CreateDate 2022/6/23-7:04 PM
 **/
public class Sort {
    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(6);
        arrayList.add(8);
        arrayList.add(0);
        arrayList.add(10);
        List<Integer> integers = sortList(arrayList);
        for (Integer integer : integers) {
            System.out.print(integer + " ");
        }

    }


    public static List<Integer> sortList(List<Integer> list){
        return sort(list,0,list.size());
    }

    public static List<Integer> sort(List<Integer> list,int start,int end){

        Integer a = new Integer(100);
        if (start == end || start + 1 == end){
            return Collections.singletonList(list.get(start));
        }
        int middle = list.size() / 2;
        List<Integer> left = sort(list, start, middle);
        List<Integer> right = sort(list, middle, end);
        List<Integer> merge = merge(left, right);
        return merge;
    }

    private static List<Integer> merge(List<Integer> left, List<Integer> right) {
        List<Integer> result = new ArrayList<>();
        int p1 = 0 , p2 = 0;
        while(p1<left.size() || p2 < right.size()){
            if (p1 < left.size() && p2 < right.size()  && (right.get(p2) < left.get(p1)) ){
                result.add(right.get(p2));
                p2++;
            }

            if (p1 < left.size() && p2 < right.size()  && ( left.get(p1) < right.get(p2)) ){
                result.add(right.get(p1));
                p1++;
            }

            if (left.size()<=p1){
                result.add(right.get(p2));
                p2++;
            }

            if (right.size()<=p2){
                result.add(left.get(p1));
                p1++;
            }
        }
        return result;
    }


}
