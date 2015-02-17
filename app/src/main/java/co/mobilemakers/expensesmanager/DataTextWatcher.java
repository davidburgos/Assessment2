package co.mobilemakers.expensesmanager;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by diany_000 on 2/17/2015.
 */
public class DataTextWatcher implements TextWatcher {

    EditText mEditText;
    Button mButton;

    public DataTextWatcher(EditText editText, Button button){
        mEditText = editText;
        mButton = button;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(!TextUtils.isEmpty(mEditText.getText().toString())){
            mButton.setEnabled(true);
        }else{
            mButton.setEnabled(false);
        }
    }
}
