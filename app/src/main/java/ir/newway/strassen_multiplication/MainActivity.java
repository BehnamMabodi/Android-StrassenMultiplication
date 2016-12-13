package ir.newway.strassen_multiplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import ir.newway.strassen_multiplication.Matrix.EnterMatrixActivity;

public class MainActivity extends AppCompatActivity {

    private EditText mInputEditText_MatrixRow;
    private EditText mInputEditText_MatrixColumn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mInputEditText_MatrixRow = (EditText) findViewById(R.id.MatrixRow);
        mInputEditText_MatrixColumn = (EditText) findViewById(R.id.MatrixColumn);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mInputEditText_MatrixRow.getText().toString().equals("") && !mInputEditText_MatrixColumn.getText().toString().equals(""))
                    showResults();
                else
                    Snackbar.make(mInputEditText_MatrixRow, R.string.emptyEditText, Snackbar.LENGTH_SHORT).show();
            }
        });

        hideSoftKey();
        hideResults();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void showResults() {
        Intent intent = new Intent(MainActivity.this, EnterMatrixActivity.class);
        intent.putExtra("matrix_row", Integer.parseInt(mInputEditText_MatrixRow.getText().toString()));
        intent.putExtra("matrix_column", Integer.parseInt(mInputEditText_MatrixColumn.getText().toString()));
        startActivity(intent);
    }

    protected void hideResults() {
    }

    protected void hideSoftKey() {
        mInputEditText_MatrixRow.clearFocus();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mInputEditText_MatrixRow.getWindowToken(), 0);
    }
}
