package model;

public class Course extends Model{
    private final int code;

    public Course(int code, String name) {
        super(name);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
