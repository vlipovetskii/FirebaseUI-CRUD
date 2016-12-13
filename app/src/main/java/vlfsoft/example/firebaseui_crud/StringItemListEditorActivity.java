package vlfsoft.example.firebaseui_crud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StringItemListEditorActivity extends AppCompatActivity {

    private static String TAG = "StringListEditorActivity_TAG";

    private RecyclerView mRecyclerView;
    private EditText mEdtNewString;

    private Button btnNewString;
        private final View.OnClickListener mNewString_OnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG, "mNewString_OnClickListener");

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                StringItemModel stringItemModel = new StringItemModel(mEdtNewString.getText().toString());
                databaseReference.push().setValue(stringItemModel, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference reference) {
                        if (databaseError != null) {
                            Log.e(TAG, "Failed to add string to Firebase", databaseError.toException());
                        } else {
                            Log.e(TAG, "Succeded to add string to Firebase");
                        }
                    }
                });
            }

        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_list_editor);

        mEdtNewString = (EditText) findViewById(R.id.edtNewString);
        btnNewString = (Button) findViewById(R.id.btnNewString);
        btnNewString.setOnClickListener(mNewString_OnClickListener);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRecyclerView.setAdapter(new StringItemListEditorRecyclerViewAdapter(FirebaseDatabase.getInstance().getReference()));

        // Use in xml layout app:layoutManager="LinearLayoutManager" instead of:
        // LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // mRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerView.setItemAnimator(itemAnimator);

    }

}
