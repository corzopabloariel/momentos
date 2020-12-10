package entity;

import androidx.annotation.NonNull;

import com.example.momentos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapters.ServiceAdapter;

public class Service {

    public static final ArrayList<Service> ITEMS = new ArrayList<Service>();
    public static final ArrayList<Service> MyITEMS = new ArrayList<Service>();
    static DatabaseReference mDatabase;

    private String _uid, _title, _text, _userId;
    private Date _onCreate;
    private Category _category;
    private ArrayList<String> _images;

    public Service() {}

    public Service(String uid, String title, String text, Date onCreate) {
        this._onCreate = onCreate;
        this._uid = uid;
        this._text = text;
        this._title = title;
        this._images = new ArrayList<>();
    }

    static {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().getReference("service").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            String title = ds.child("title").getValue().toString();
                            String text = ds.child("text").getValue().toString();
                            String uid = ds.getKey();
                            Date onCreate = ds.child("onCreate").getValue(Date.class);
                            ITEMS.add(new Service(uid, title, text, onCreate));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
        if (mAuth.getCurrentUser() != null) {
            FirebaseDatabase.getInstance().getReference("services").orderByChild("userId").equalTo(mAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for(DataSnapshot ds: dataSnapshot.getChildren()) {
                                String title = ds.child("title").getValue().toString();
                                String text = ds.child("text").getValue().toString();
                                String uid = ds.getKey();
                                Date onCreate = ds.child("onCreate").getValue(Date.class);
                                MyITEMS.add(new Service(uid, title, text, onCreate));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });
        }
    }

    public String get_uid() {
        return _uid;
    }

    public void set_uid(String _uid) {
        this._uid = _uid;
    }

    public String get_userId() {
        return _userId;
    }

    public void set_userId(String _userId) {
        this._userId = _userId;
    }

    public ArrayList<String> get_images() {
        return _images;
    }

    public void set_images(ArrayList<String> _images) {
        this._images = _images;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_text() {
        return _text;
    }

    public void set_text(String _text) {
        this._text = _text;
    }

    public Date get_onCreate() {
        return _onCreate;
    }

    public void set_onCreate(Date _onCreate) {
        this._onCreate = _onCreate;
    }

    public Category get_category() {
        return _category;
    }

    public void set_category(Category _category) {
        this._category = _category;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("userId", this._userId);
        result.put("title", this._title);
        result.put("text", this._text);
        result.put("onCreate", this._onCreate);
        result.put("images", this._images);

        return result;
    }
}
