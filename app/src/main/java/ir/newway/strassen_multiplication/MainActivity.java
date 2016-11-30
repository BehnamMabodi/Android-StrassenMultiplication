package ir.newway.strassen_multiplication;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.newway.newlib.innerClasses.Tools.MyAnimationUtils;

public class MainActivity extends AppCompatActivity {

    private EditText mInputEditText;
    private TextView mResultTextView;
    private TextView mResultBackground;
    private TextView mLogTextView;

    private boolean isResultHidden;
    private IntegerList mList;
    private Button mSortButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mList = new IntegerList();
        mSortButton = (Button) findViewById(R.id.button_sort);
        mResultTextView = (TextView) findViewById(R.id.results);
        mLogTextView = (TextView) findViewById(R.id.log_textview);
        mInputEditText = (EditText) findViewById(R.id.MatrixHorizontal);
        mInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hideResults();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mInputEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT)
                    if (!mInputEditText.getText().toString().equals(""))
                        addNumber(Integer.parseInt(mInputEditText.getText().toString()));
                    else
                        Snackbar.make(mLogTextView, R.string.emptyEditText, Snackbar.LENGTH_SHORT).show();
                return false;
            }
        });
        //  mInputEditText.setTextC
        mResultBackground = (TextView) findViewById(R.id.result_background_Accepted);
        //    mResultBackground.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mInputEditText.getText().toString().equals(""))
                    addNumber(Integer.parseInt(mInputEditText.getText().toString()));
                else
                    Snackbar.make(mLogTextView, R.string.emptyEditText, Snackbar.LENGTH_SHORT).show();
            }
        });

        mSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResults();
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

    protected void addNumber(Integer number) {
        mList.addNumber(number);
        mInputEditText.setText("");
        String unSortedArrayText = getResources().getString(R.string.unsorted_array);
        mLogTextView.setText(unSortedArrayText);
        Integer[] unSortedArray = mList.getUnsortedArray();
        for (Integer integer : unSortedArray) {
            mLogTextView.setText(mLogTextView.getText().toString() + integer + "  ");
        }
    }

    protected void showResults() {
        isResultHidden = false;
        mResultTextView.setText(getResources().getString(R.string.log));
        Integer[] integers = mList.getSortedArray();
        mLogTextView.setText(mLogTextView.getText().toString() + "\n\n" + getString(R.string.sorted_array) + "\n");
        for (Integer integer : integers) {
            mLogTextView.setText(mLogTextView.getText().toString() + integer + "  ");
        }

        mResultTextView.setTextColor(getResources().getColor(R.color.colorAcceptedDark));
        mResultBackground.setBackgroundColor(getResources().getColor(R.color.colorAcceptedLight));
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorAcceptedDark));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAcceptedDark));
        }
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.colorAccepted));
        MyAnimationUtils.enterReveal(mResultBackground, 800, 0, mResultTextView.getWidth() / 2, mResultTextView.getHeight() / 2);

    }

    protected void hideResults() {
        if (!isResultHidden) {
            isResultHidden = true;
            mList.clearList();
            mResultTextView.setText(getResources().getString(R.string.result_text_enter_string));
            mLogTextView.setText(getResources().getString(R.string.input_hint_full));
            mResultTextView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            mResultBackground.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            }
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.colorPrimary));
            MyAnimationUtils.enterReveal(mResultBackground, 800, 0, mResultTextView.getWidth() / 2, mResultTextView.getHeight() / 2);
        }
    }

    @Override
    public void onBackPressed() {
        if (isResultHidden)
            super.onBackPressed();
        else {
            mInputEditText.setText("");
            hideResults();
            hideSoftKey();
        }
    }

    protected void hideSoftKey() {
        mInputEditText.clearFocus();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mInputEditText.getWindowToken(), 0);
    }
}
