package entity;

import java.util.Date;

public class Service {

    private String _title, _text;
    private Date _onCreate;
    private Category _category;

    public Service(String title, String text, Date onCreate, Category category) {
        this._category = category;
        this._onCreate = onCreate;
        this._text = text;
        this._title = title;
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
}
