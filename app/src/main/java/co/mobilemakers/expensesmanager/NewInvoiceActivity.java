package co.mobilemakers.expensesmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NewInvoiceActivity extends ActionBarActivity {
    public final String LOG_TAG = getClass().getSimpleName();
    public static final int REQUEST_CODE = 0;
    public final static String PRICE = "PRICE";
    public final static String SERVICE = "SERVICE";
    public final static String EVENT_NAME ="EVENT_NAME";

    private String eventName;
    private EditText mService,mPrice;
    private Button mButtonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_invoice);
        SetActionBarTitle();
        WireViewElements();
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(PRICE, mPrice.getText().toString());
                intent.putExtra(SERVICE ,mService.getText().toString());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        checkFieldsForEmptyValues();
    }

    private void WireViewElements() {
        mService = (EditText) findViewById(R.id.edit_text_product);
        mPrice= (EditText) findViewById(R.id.edit_text_price);
        mButtonAdd=(Button)findViewById(R.id.button_add);

        mPrice.addTextChangedListener(mTextWatcher);
        mService.addTextChangedListener(mTextWatcher);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_invoice, menu);
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

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();
        }
    };

    void checkFieldsForEmptyValues(){
        String s1 = mService.getText().toString();
        String s2 = mPrice.getText().toString();

        if(s1.equals("")|| s2.equals("")){
            mButtonAdd.setEnabled(false);
        } else {
            mButtonAdd.setEnabled(true);
        }
    }
    private void SetActionBarTitle() {

        String eventTitle = "DefaultTitle2";
        if((String) getIntent().getSerializableExtra(EVENT_NAME)!=null)
            eventTitle = getIntent().getStringExtra(EVENT_NAME);
        Log.d(LOG_TAG, eventTitle);
        setTitle(eventTitle);
    }

}
