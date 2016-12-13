package vlfsoft.example.firebaseui_crud;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class StringItemModel {

    private String mValue;

    public String getValue() {
        return mValue;
    }

    public void setValue(String aValue) {
        mValue = aValue;
    }

    // com.google.firebase.database.DatabaseException: Class vlfsoft.example.firebaseui_crud.StringItemModel is missing a constructor with no arguments
    // WO:
    public StringItemModel() {
    }

    public StringItemModel(String aValue) {
        mValue = aValue;
    }

    // To support updateChildren
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("value", mValue);

        return result;
    }

}
