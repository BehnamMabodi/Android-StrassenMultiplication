package ir.newway.strassen_multiplication.InnerClasses;

/**
 * Created by goldm on 05/12/2016.
 */

public class Matrix {

    private int[][] mData;
    private int mRowHead;
    private int mColumnHead;
    private final int mRowLimit;
    private final int mColumnLimit;
    private final int mColumnSize;
    private final int mRowSize;


    public Matrix(int rowSize, int columnSize, int rowLimit, int columnLimit) {
        mData = new int[rowSize][columnSize];
        mRowLimit = rowLimit;
        mColumnLimit = columnLimit;
        mColumnSize = columnSize;
        mRowSize = rowSize;
    }

    public void addNumber(int number) {
        if (!isFull()) {
            mData[mRowHead][mColumnHead] = number;
            increaseHead();
        }

    }

    private void increaseHead() {
        if (!isFull()) {
            mRowHead++;
            if (mRowHead >= mRowLimit) {
                mRowHead = 0;
                mColumnHead++;
            }
        }
    }

    public boolean isFull() {
        return mRowHead >= mRowLimit && mColumnHead >= mColumnLimit;
    }

    public int getRowHead() {
        return mRowHead;
    }

    public int getColumnHead() {
        return mColumnHead;
    }

    public String getLog() {
        String log = "";
        for (int i = 0; i < mRowSize; i++) {
            for (int j = 0; j < mColumnSize; j++) {
                log += mData[i][j] + "  ";
            }
            log += "\n";
        }
        log += "\n\n";

        return log;
    }

    public int[][] getArray() {
        return mData;
    }

}
