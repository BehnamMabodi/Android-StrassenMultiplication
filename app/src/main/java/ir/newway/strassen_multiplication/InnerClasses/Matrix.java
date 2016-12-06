package ir.newway.strassen_multiplication.InnerClasses;

import java.util.Random;

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

    public Matrix(int[][] data) {
        mData = data;
        mRowLimit = data[0].length;
        mColumnLimit = data[1].length;
        mRowSize = data[0].length;
        mColumnSize = data[1].length;
        mRowHead = mRowLimit;
        mColumnHead = mColumnLimit;
    }

    public void addNumber(int number) {
        if (!isFull()) {
            mData[mRowHead][mColumnHead] = number;
            increaseHead();
        }

    }

    private void increaseHead() {
        if (!isFull()) {
            mColumnHead++;
            if (mColumnHead >= mColumnLimit) {
                mRowHead++;
                if (!isFull())
                    mColumnHead = 0;
            }
        }
    }

    public void randomFill(int maxRange){
        Random random = new Random();
        while (!isFull()){
            addNumber(random.nextInt(maxRange));
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
                log += mData[i][j] + "   ";
                // TODO: Remove Extra Chars If Two Number Inserted
            }
            log += "\n";
        }
        log += "\n";
        return log;
    }

    public int[][] toArray() {
        return mData;
    }

}
