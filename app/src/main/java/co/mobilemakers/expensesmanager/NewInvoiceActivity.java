package co.mobilemakers.expensesmanager;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
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

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class NewInvoiceActivity extends ActionBarActivity {
    public final String LOG_TAG = getClass().getSimpleName();
    public static final int REQUEST_CODE = 0;
    public final static String PRICE = "PRICE";
    public final static String SERVICE = "SERVICE";
    public final static String EVENT_NAME ="EVENT_NAME";
    public final static String EVENT_ID ="EVENT_ID";

    private String eventName;
    private int eventId;
    private EditText mService,mPrice;
    private Button mButtonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_invoice);
        eventName = (String)getIntent().getExtras().get(EVENT_NAME);
        eventId = getIntent().getExtras().getInt(EVENT_ID);
        customizeActionBar(eventName);
        wireUpViewElements();
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseManager.init(getBaseContext());
                Invoice invoice = addInvoiceToDB();

                Payment payment1 = new Payment(invoice,1,123,false);
                DataBaseManager.getInstance().addPayment(payment1);
                Payment payment2 = new Payment(invoice,2,345,false);
                DataBaseManager.getInstance().addPayment(payment2);
                Intent intent = new Intent();
                intent.putExtra(PRICE,invoice.getPrice());
                intent.putExtra(SERVICE ,invoice.getName());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        checkFieldsForEmptyValues();
    }

    private Invoice addInvoiceToDB() {
        Invoice invoice = new Invoice();
        invoice.setName(mService.getText().toString());
        invoice.setPrice(Integer.parseInt(mPrice.getText().toString()));
        DataBaseManager.getInstance().addInvoice(invoice);
        return invoice;
    }


    private void customizeActionBar(String eventName) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(eventName);
        actionBar.setIcon(R.drawable.ic_principal);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void wireUpViewElements() {
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

}
