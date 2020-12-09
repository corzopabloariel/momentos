package entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String _email, _name, _pass, _uid;

    public User() { }

    public User(String _email, String _name, String _pass, String _uid) {
        this._email = _email;
        this._name = _name;
        this._pass = _pass;
        this._uid = _uid;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_pass() {
        return _pass;
    }

    public void set_pass(String _pass) {
        this._pass = _pass;
    }

    public String get_uid() {
        return _uid;
    }

    public void set_uid(String _uid) {
        this._uid = _uid;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("uid", this._uid);
        result.put("name", this._name);
        result.put("email", this._email);
        result.put("pass", this._pass);

        return result;
    }
}
