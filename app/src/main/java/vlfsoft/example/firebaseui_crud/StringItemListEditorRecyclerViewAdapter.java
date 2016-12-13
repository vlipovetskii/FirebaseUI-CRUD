package vlfsoft.example.firebaseui_crud;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import java.util.Map;

public class StringItemListEditorRecyclerViewAdapter extends FirebaseRecyclerAdapter<StringItemModel, StringItemListEditorRecyclerViewAdapter.ViewHolder> {

    private static String TAG = "StringListEditorRecyclerViewAdapter_TAG";

    public StringItemListEditorRecyclerViewAdapter(Query aQuery) {
        super(StringItemModel.class, R.layout.string_list_editor_item, StringItemListEditorRecyclerViewAdapter.ViewHolder.class, aQuery);
    }

    // java.lang.RuntimeException: java.lang.NoSuchMethodException: <init> [class android.view.View]
    // at com.firebase.ui.database.FirebaseRecyclerAdapter.onCreateViewHolder(FirebaseRecyclerAdapter.java:165)
    // WO:
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.string_list_editor_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void populateViewHolder(StringItemListEditorRecyclerViewAdapter.ViewHolder holder, StringItemModel aModel, int position) {
        holder.getEdtString().setText(aModel.getValue());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mEdtString;

        private Button mBtnUpdate;
            private final View.OnClickListener mbtnUpdate_OnClickListener = new View.OnClickListener() {
                public void onClick(View v) {
                    Log.i(TAG, "mbtnUpdate_OnClickListener");

                    int position = getAdapterPosition();
                    DatabaseReference databaseReference = getRef(position);

                    StringItemModel stringItemModel = new StringItemModel(mEdtString.getText().toString());
                    Map<String, Object> childUpdates = stringItemModel.toMap();

                    databaseReference.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference reference) {
                            if (databaseError != null) {
                                Log.e(TAG, "Failed to update string to Firebase", databaseError.toException());
                            } else {
                                Log.e(TAG, "Succeded to update string to Firebase");
                            }
                        }
                    });

                }

            };

        private Button mBtnDelete;
            private final View.OnClickListener mbtnDelete_OnClickListener = new View.OnClickListener() {
                public void onClick(View v) {
                    Log.i(TAG, "mbtnDelete_OnClickListener");

                    int position = getAdapterPosition();
                    DatabaseReference databaseReference = getRef(position);

                    databaseReference.removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference reference) {
                            if (databaseError != null) {
                                Log.e(TAG, "Failed to delete string to Firebase", databaseError.toException());
                            } else {
                                Log.e(TAG, "Succeded to delete string to Firebase");
                            }
                        }
                    });

                }

            };

        public ViewHolder(View itemView) {
            super(itemView);

            mEdtString = (TextView) itemView.findViewById(R.id.edtString);

            mBtnUpdate = (Button) itemView.findViewById(R.id.btnUpdate);
            mBtnUpdate.setOnClickListener(mbtnUpdate_OnClickListener);

            mBtnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            mBtnDelete.setOnClickListener(mbtnDelete_OnClickListener);

        }

         public TextView getEdtString() {
            return mEdtString;
        }


    }

}
