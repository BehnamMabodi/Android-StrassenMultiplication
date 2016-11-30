package ir.newway.strassen_multiplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by goldm on 13/11/2016.
 */

public class IntegerList {

    private List<Integer> mIntegerList;

    public IntegerList() {
        mIntegerList = new ArrayList<>();
    }

    public void addNumber(Integer number) {
        if (number != null)
            mIntegerList.add(number);
    }

    public void clearList() {
        mIntegerList.clear();
    }

    public Integer[] getSortedArray() {
        Integer[] integerArray = mIntegerList.toArray(new Integer[mIntegerList.size()]);
        MergeSort sort = new MergeSort();
        sort.sort(integerArray);
        return integerArray;
    }

    public Integer[] getUnsortedArray() {
        return mIntegerList.toArray(new Integer[mIntegerList.size()]);
    }
}
