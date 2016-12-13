package vlfsoft.example.firebaseui_crud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {

    private static String TAG = "AuthActivity_TAG";

    private Button mBtnSignIn;
        private final View.OnClickListener mBtnSignIn_OnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG, "mBtnSignIn_OnClickListener");
                signIn();
            }

        };

    private Button mBtnSignOut;
        private final View.OnClickListener mBtnSignOut_OnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG, "mBtnSignOut_OnClickListener");
                signOut();
            }

        };

    private static final String GOOGLE_TOS_URL =
            "https://www.google.com/policies/terms/";
    private static final String FIREBASE_TOS_URL =
            "https://www.firebase.com/terms/terms-of-service.html";

    private static final int REQUEST_CODE_SIGN_IN = 100;

    private void addAuthStateListener() {
        // https://firebase.google.com/docs/auth/web/manage-users
        // The recommended way to get the current user is by setting an observer on the Auth object:
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.i(TAG, "onAuthStateChanged");
                updateUI(firebaseAuth.getCurrentUser() != null);
            }
        });

    }

    private void updateUI(boolean aIsUserSignedIn) {
        Log.i(TAG, "updateUI");

        mBtnSignIn.setEnabled(!aIsUserSignedIn);
        mBtnSignOut.setEnabled(aIsUserSignedIn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auth);

        mBtnSignIn = (Button) findViewById(R.id.btnSignIn);
        mBtnSignIn.setOnClickListener(mBtnSignIn_OnClickListener);

        mBtnSignOut = (Button) findViewById(R.id.btnSignOut);
        mBtnSignOut.setOnClickListener(mBtnSignOut_OnClickListener);

        // updateUI() is redundant, since addAuthStateListener calls onAuthStateChanged
        addAuthStateListener();

    }

    public void signIn() {

        Log.i(TAG, "signIn");

        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setTheme(AuthUI.getDefaultTheme())
                        .setLogo(AuthUI.NO_LOGO)
                        //.setProviders(AuthUI.GOOGLE_PROVIDER, AuthUI.FACEBOOK_PROVIDER, AuthUI.EMAIL_PROVIDER)
                        .setProviders(AuthUI.GOOGLE_PROVIDER)
                        .setTosUrl(GOOGLE_TOS_URL)
                        .build(),
                REQUEST_CODE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                handleSignInResponse(resultCode, data);
                break;
        }

    }

    private void handleSignInResponse(int resultCode, Intent data) {
        Log.i(TAG, "handleSignInResponse(" + resultCode + ")");

        switch (resultCode) {
            case RESULT_OK:
                userSignedIn();
                break;
            case RESULT_CANCELED:
                // Something to do
                break;
        }
    }

    private void userSignedIn() {
        Log.i(TAG, "userSignedIn");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.i(TAG, "DisplayName: " + currentUser.getDisplayName());
        Log.i(TAG, "Email: " + currentUser.getEmail());
        Log.i(TAG, "ProviderId: " + currentUser.getProviderId());
        Log.i(TAG, "Uid: " + currentUser.getUid());
        Log.i(TAG, "PhotoUrl: " + currentUser.getPhotoUrl());

        // Obtain in a separate thread ?
        // Log.i(TAG, "Token: " + currentUser.getToken(true));

    }

    public void signOut() {
        showProgressDialog();

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            Log.i(TAG, "signOut succeded");
                        } else {
                            Log.i(TAG, "signOut failed");
                        }
                    }
                });
    }

    private ProgressDialog mProgressDialog;

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
