package model;

public class Student extends Model{
    private final long code;

    public Student(long code, String name) {
        super(name);
        this.code = code;
    }

    public long getCode() {
        return code;
    }
}
