package ir.newway.strassen_multiplication.InnerClasses;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ir.newway.strassen_multiplication.R;

public class EnterMatrix extends AppCompatActivity {

    Matrix mMatrix1;
    Matrix mMatrix2;
    int mRowLimit;
    int mColumnLimit;
    EditText edtInput;
    TextView mTvOutputLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_matrix);
        mTvOutputLog = (TextView) findViewById(R.id.tv_matrix_output);
        edtInput = (EditText) findViewById(R.id.edt_matrixInput);

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
        } else
            doStrassen();
    }

    private void doStrassen() {
        Snackbar.make(edtInput, getString(R.string.matrix_full), Snackbar.LENGTH_LONG).show();
        Matrix result = new Matrix(Strassen.strassen(mMatrix1.getArray(), mMatrix2.getArray()));
        mTvOutputLog.setText(mMatrix1.getLog() + mMatrix2.getLog() + result.getLog());
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
