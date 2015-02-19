package co.mobilemakers.expensesmanager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class LoginActivity extends Activity {

    private static final String USERI_ID = "USER_ID";
    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    Bitmap mUserAvatar;
    public static User user;

    public class User{

        private String mUsername;
        private Bitmap mImage;
        private Friend mFriend;

        public User(){
        }

        @Override
        public String toString() {
            return "User{" +
                    "mUsername='" + mUsername + '\'' +
                    '}';
        }

        public String getUsername() {
            return mUsername;
        }

        public void setUsername(String username) {
            mUsername = username;
        }

        public Bitmap getImage() {
            return mImage;
        }

        public void setImage(Bitmap image) {
            mImage = image;
        }

        public Friend getFriend() {
            return mFriend;
        }

        public void setFriend(Friend friend) {
            mFriend = friend;
        }
    }

    private AboutMeLogin mAuthTask = null;
    private String mUserToken = "";
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = new User();
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    public void attemptLogin() {

        if (mAuthTask != null) {
            return;
        }

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new AboutMeLogin();
            mAuthTask.execute(email, password);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private class AboutMeLogin extends AsyncTask<String,Void,Integer> {

        private Bitmap downloadImage(String stringURL){
            Bitmap bitmap = null;
            try {
                URL url = new URL(stringURL);
                URI uri = new URI(url.getProtocol(), url.getHost(),url.getPath(), url.getQuery(), null);
                HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int len;
                while ((len = input.read(buffer)) != -1) {
                    byteBuffer.write(buffer, 0, len);
                }
                byte[] img = byteBuffer.toByteArray();
                byteBuffer.flush();
                byteBuffer.close();
                input.close();
                bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            }  catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected Integer doInBackground(String... params) {
            String username="",password="";

            int result = 0;

            switch (params.length){
                case 2:
                    if(params[0] != null){
                        username = params[0];
                        password = params[1];
                    }
                    break;
                default:
                    return result;
            }

            URL query;

            try {
                query = constructURLQuery(username,password);
                HttpURLConnection httpConnection = (HttpURLConnection)query.openConnection();
                try {
                    String response = readFullResponse(httpConnection.getInputStream());
                    result = parseResponse(response, true);

                    if(result==200){
                        if(!mUserToken.isEmpty()){
                            query = constructURLQueryForUser(username, mUserToken);
                            httpConnection = (HttpURLConnection)query.openConnection();
                            response = readFullResponse(httpConnection.getInputStream());
                            result = parseResponse(response, false);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    httpConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        private URL constructURLQueryForUser(String username,String token) throws MalformedURLException {
            //https://api.about.me/api/v2/json/user/view/<username>
            final String SCHEME = "https";
            final String API_URL = "api.about.me";
            final String API = "api";
            final String API_VERSION = "v2";
            final String ABOUT_ME_ENDPOINT = "json";
            final String ABOUT_ME_ENDPOINT_LOGIN = "user";
            final String ABOUT_ME_ENDPOINT_TYPE = "view";
            final String CLIENT_ID = "client_id";
            final String ABOUT_ME_TOKEN = "03cb6c6984adb3c21f8b0a3278a9bbab9d5119e6";
            final String GRANT_TYPE = "token";
            final String EXTENDED = "extended";
            final String EXTENDED_VALUE = "true";

            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme(SCHEME).authority(API_URL).
                    appendPath(API).
                    appendPath(API_VERSION).
                    appendPath(ABOUT_ME_ENDPOINT).
                    appendPath(ABOUT_ME_ENDPOINT_LOGIN).
                    appendPath(ABOUT_ME_ENDPOINT_TYPE).
                    appendPath(username).
                    appendQueryParameter(CLIENT_ID, ABOUT_ME_TOKEN).
                    appendQueryParameter(EXTENDED, EXTENDED_VALUE).
                    appendQueryParameter(GRANT_TYPE, token);
            Uri uri = uriBuilder.build();
            Log.d(LOG_TAG, "Built view URI: " + uri.toString());
            return new URL(uri.toString());
        }

        private URL constructURLQuery(String username, String password) throws MalformedURLException {

            final String SCHEME = "https";
            final String API_URL = "api.about.me";
            final String API = "api";
            final String API_VERSION = "v2";
            final String PROJECTS_ENDPOINT = "json";
            final String PROJECTS_ENDPOINT_LOGIN = "user";
            final String ABOUTME_ENDPOINT_TYPE = "login";
            final String CLIENT_ID = "client_id";
            final String ABOUT_ME_TOKEN = "03cb6c6984adb3c21f8b0a3278a9bbab9d5119e6";
            final String GRANT_TYPE = "grant_type";
            final String PASSWORD = "password";

            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme(SCHEME).authority(API_URL).
                    appendPath(API).
                    appendPath(API_VERSION).
                    appendPath(PROJECTS_ENDPOINT).
                    appendPath(PROJECTS_ENDPOINT_LOGIN).
                    appendPath(ABOUTME_ENDPOINT_TYPE).
                    appendPath(username).
                    appendQueryParameter(CLIENT_ID, ABOUT_ME_TOKEN).
                    appendQueryParameter(GRANT_TYPE, PASSWORD).
                    appendQueryParameter(PASSWORD, password);
            Uri uri = uriBuilder.build();
            Log.d(LOG_TAG, "Built URI: " + uri.toString());
            return new URL(uri.toString());
        }

        private String readFullResponse(InputStream inputStream) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String response = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            if (stringBuilder.length() > 0) {
                response = stringBuilder.toString();
            }
            return response;
        }

        private int parseResponse(String response, Boolean isLogin) {
            String REPO_STATUS = "status";
            String REPO_TOKEN  = "access_token";
            String ABOUT_ME_IMAGE  = "thumbnail4";//avatar
            String USERNAME  = "user_name";

            int status = 0;

            try {
                JSONObject reposJsonObject = new JSONObject(response);
                status = reposJsonObject.getInt(REPO_STATUS);
                if(status == 200){
                    if(isLogin){
                        mUserToken = reposJsonObject.getString(REPO_TOKEN);
                    }else{
                        String avatarURL = reposJsonObject.getString(ABOUT_ME_IMAGE);

                        DataBaseManager.init(getBaseContext());
                        Friend userLogged = DataBaseManager.getInstance().getFriendById(reposJsonObject.getString(USERNAME));

                        if(userLogged != null){
                            user.setFriend(userLogged);
                        }

                        if(!avatarURL.isEmpty()){
                            mUserAvatar = downloadImage(avatarURL);
                            if(mUserAvatar != null)
                            user.setImage(mUserAvatar);
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return status;
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

        @Override
        protected void onPostExecute(Integer success) {
            super.onPostExecute(success);

            mAuthTask = null;

            switch (success){
                case 200:
                    Intent mainActivity = new Intent(getBaseContext(),MainActivity.class);
                    mainActivity.putExtra(USERI_ID,mEmailView.getText().toString());
                    startActivity(mainActivity);
                    finish();
                    break;
                case 205:
                    mPasswordView.setError(getString(R.string.string_login_status205));
                    mPasswordView.requestFocus();
                    break;
                case 304:
                    mPasswordView.setError(getString(R.string.string_login_status304));
                    mPasswordView.requestFocus();
                    break;
                case 401:
                    mPasswordView.setError(getString(R.string.string_login_status401));
                    mPasswordView.requestFocus();
                    break;
                case 403:
                    mPasswordView.setError(getString(R.string.string_login_status403));
                    mPasswordView.requestFocus();
                    break;
                case 404:
                    mPasswordView.setError(getString(R.string.string_login_status404));
                    mPasswordView.requestFocus();
                    break;
                case 406:
                    mPasswordView.setError(getString(R.string.string_login_status406));
                    mPasswordView.requestFocus();
                    break;
                case 500:
                    mPasswordView.setError(getString(R.string.string_login_status500));
                    mPasswordView.requestFocus();
                    break;
                case 501:
                    mPasswordView.setError(getString(R.string.string_login_status501));
                    mPasswordView.requestFocus();
                    break;
                case 502:
                    mPasswordView.setError(getString(R.string.string_login_status502));
                    mPasswordView.requestFocus();
                    break;
                case 503:
                    mPasswordView.setError(getString(R.string.string_login_status503));
                    mPasswordView.requestFocus();
                    break;
                default:
                    mPasswordView.setError(getString(R.string.string_login_status_default_error));
                    mPasswordView.requestFocus();
                    break;
            }
            showProgress(false);

        }
    }

}



