package vlfsoft.example.firebaseui_crud;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity_TAG";

    private Button mBtnSignInOut;
        private final View.OnClickListener mBtnSignInOut_OnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG, "mBtnSignIn_OnClickListener");
                startActivity(new Intent(getApplicationContext(), AuthActivity.class));
            }

        };

    private Button mBtnStringListEditor;
        private final View.OnClickListener mbtnStringListEditor_OnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG, "mbtnStringListEditor_OnClickListener");
                startActivity(new Intent(getApplicationContext(), StringItemListEditorActivity.class));
            }

        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnSignInOut = (Button) findViewById(R.id.btnSignInOut);
            mBtnSignInOut.setOnClickListener(mBtnSignInOut_OnClickListener);

        mBtnStringListEditor = (Button) findViewById(R.id.btnStringListEditor);
            mBtnStringListEditor.setOnClickListener(mbtnStringListEditor_OnClickListener);

        addAuthStateListener();
    }

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

        mBtnStringListEditor.setEnabled(aIsUserSignedIn);
    }

}
