package ir.newway.strassen_multiplication.Matrix;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ir.newway.strassen_multiplication.R;

public class EnterMatrixActivity extends AppCompatActivity {

    Matrix mMatrix1;
    Matrix mMatrix2;
    Matrix mResult;
    int mRowLimit;
    int mColumnLimit;
    EditText edtInput;
    TextView mTvOutputLog;
    Button mRandomFill;
    AsyncTask mBackgroundTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_matrix);
        mTvOutputLog = (TextView) findViewById(R.id.tv_matrix_output);
        edtInput = (EditText) findViewById(R.id.edt_matrixInput);
        mRandomFill = (Button) findViewById(R.id.btn_random_fill);

        mRowLimit = getIntent().getExtras().getInt("matrix_row");
        mColumnLimit = getIntent().getExtras().getInt("matrix_column");
        int rowSize = roundToNextPow2(mRowLimit);
        int columnSize = roundToNextPow2(mColumnLimit);

        mMatrix1 = new Matrix(rowSize, columnSize, mRowLimit, mColumnLimit);
        mMatrix2 = new Matrix(rowSize, columnSize, mRowLimit, mColumnLimit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtInput.getText().toString().equals("")) {
                    Snackbar.make(view, getString(R.string.emptyEditText), Snackbar.LENGTH_LONG).show();
                    return;
                }
                addNumber(Integer.parseInt(edtInput.getText().toString()));
                edtInput.setText("");
            }
        });

        mRandomFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMatrix1.randomFill(10);
                mMatrix2.randomFill(10);
                Snackbar.make(edtInput, getString(R.string.matrix_full), Snackbar.LENGTH_LONG).show();
                doStrassen();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addNumber(int number) {
        if (!mMatrix1.isFull()) {
            mMatrix1.addNumber(number);
            mTvOutputLog.setText(mMatrix1.getLog() + mMatrix2.getLog());
        } else if (!mMatrix2.isFull()) {
            mMatrix2.addNumber(number);
            mTvOutputLog.setText(mMatrix1.getLog() + mMatrix2.getLog());
            if (mMatrix2.isFull())
                doStrassen();
        } else {

            doStrassen();
        }
    }

    private void doStrassen() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle(R.string.wait);
        dialog.setCancelable(false);
        dialog.show();
        mBackgroundTask = new AsyncTask() { // This is not the best idea (MAY CAUSE MEMORY LEAK) but we don't care now! because it's just a practice app and this is quickest way to implement (and also dirty)
            @Override
            protected Object doInBackground(Object[] objects) {
                mResult = new Matrix(Strassen.strassen(mMatrix1.toArray(), mMatrix2.toArray()));
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                mTvOutputLog.setText(mMatrix1.getLog() + mMatrix2.getLog() + mResult.getLog());
                dialog.dismiss();
            }
        };
        mBackgroundTask.execute();

    }

    @Override
    protected void onDestroy() {
        if (mBackgroundTask != null)
            mBackgroundTask.cancel(true);
        super.onDestroy();
    }

    private int roundToNextPow2(int number) {
        return (int) Math.pow(2, Math.ceil(Math.log(number) / Math.log(2)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
