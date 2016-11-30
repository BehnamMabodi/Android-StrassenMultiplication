package ir.newway.strassen_multiplication;

/**
 * Created by goldm on 13/11/2016.
 */

public class MergeSort {
    private Integer[] mArray;
    private Integer[] mTempArray;

    public void sort(Integer[] values) {
        mArray = values;
        mTempArray = new Integer[mArray.length];
        mergeSort(0, mArray.length - 1);
    }

    private void mergeSort(int low, int high) {
        if (low < high) {
            int middle = low + (high - low) / 2;

            mergeSort(low, middle);
            mergeSort(middle + 1, high);
            merge(low, middle, high);
        }
    }

    private void merge(int low, int middle, int high) {

        for (int i = low; i <= high; i++) {
            mTempArray[i] = mArray[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;

        while (i <= middle && j <= high) {
            if (mTempArray[i] <= mTempArray[j])
                mArray[k++] = mTempArray[i++];
            else
                mArray[k++] = mTempArray[j++];
        }
        if (i > middle)
            for (; j <= high; j++)
                mArray[k++] = mTempArray[j];
        else
            for (; i <= middle; i++)
                mArray[k++] = mTempArray[i];

    }
}
