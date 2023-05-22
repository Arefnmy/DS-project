package model;

public class Grade {
    private String semester;
    private int semesterNum;
    private final int year;
    private final int term;
    private float grade;
    private String gradeStr;
    private final Student student;
    private final Course course;

    public Grade(int year, int term, float grade, Student student, Course course) {
        this.year = year;
        this.term = term;
        semesterNum = year*10 + term;
        this.grade = grade;
        setGradeStr();
        this.student = student;
        this.course = course;
    }

    public Grade(String semester, float grade, Student student, Course course) {
        this.semester = semester;
        year = Integer.parseInt(semester.substring(0,4));
        term = semester.charAt(4) - '0';
        semesterNum = year*10 + term;
        this.grade = grade;
        setGradeStr();
        this.student = student;
        this.course = course;
    }

    public String getSemester() {
        return semester;
    }

    public int getSemesterNum(){
        return semesterNum;
    }

    public int getYear() {
        return year;
    }

    public int getTerm() {
        return term;
    }

    public float getGrade() {
        return grade;
    }

    public String getGradeStr(){
        return gradeStr;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public void setGrade(float grade) {
        this.grade = grade;
        setGradeStr();
    }

    private void setGradeStr(){
        gradeStr = grade == (int) grade ? String.valueOf((int) grade) : String.valueOf(grade);
    }
}
