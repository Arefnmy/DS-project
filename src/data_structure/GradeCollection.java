package data_structure;

import model.Course;
import model.Grade;
import model.Student;

import java.util.Iterator;

public class GradeCollection implements Iterable<Grade>{
    private GradeNode last;
    private int size;
    protected LinkedList<StudentNode> students;
    protected LinkedList<CourseNode> courses;

    public GradeCollection(){
        students = new LinkedList<>();
        courses = new LinkedList<>();
        size = 0;
    }

    public void addGrade(Grade grade){
        GradeNode gradeNode = new GradeNode(grade);
        StudentNode studentNode = getStudentNode(grade.getStudent().getCode());
        CourseNode courseNode = getCourseNode(grade.getCourse().getCode());
        gradeNode.studentNode = studentNode;
        gradeNode.courseNode = courseNode;
        assert studentNode != null;
        assert courseNode != null;

        if (studentNode.first == null)
            studentNode.first = gradeNode;
        if (courseNode.first == null)
            courseNode.first = gradeNode;

        GradeNode studentLast = studentNode.last;
        GradeNode courseLast = courseNode.last;

        if (studentLast != null) {
            studentLast.nextGradeStudent = gradeNode;
            gradeNode.preGradeStudent = studentLast;
        }
        studentNode.last = gradeNode;
        if (courseLast != null) {
            courseLast.nextGradeCourse = gradeNode;
            gradeNode.preGradeCourse = courseLast;
        }
        courseNode.last = gradeNode;

        if (!isEmpty()) {
            last.nextGrade = gradeNode;
            gradeNode.preGrade = last;
        }
        last = gradeNode;

        size ++;
        courseNode.size ++;
        studentNode.size ++;
    }

    public void addStudent(Student student){
        StudentNode studentNode = new StudentNode(student);
        students.addLast(studentNode);
    }

    public void addCourse(Course course){
        CourseNode courseNode = new CourseNode(course);
        courses.addLast(courseNode);
    }

    public boolean isEmpty(){
        return size == 0;
    }

    protected StudentNode getStudentNode(long code){
        for (StudentNode s : students)
            if (s.student.getCode() == code)
                return s;
        return null;
    }

    protected CourseNode getCourseNode(int code){
        for (CourseNode c : courses){
            if (c.course.getCode() == code)
                return c;
        }
        return null;
    }

    private GradeNode getGradeNode(Grade grade){
        for (GradeNode g = last; g != null; g = g.preGrade){
            if (g.grade == grade)
                return g;
        }
        return null;
    }

    //return student
    public void removeStudent(long code){
        StudentNode studentNode = getStudentNode(code);
        students.remove(studentNode);
        for (GradeNode g = studentNode.first; g!= null ; g = g.nextGradeStudent)
            removeGradeNode(g);
    }

    public void removeCourse(int code){
        CourseNode courseNode = getCourseNode(code);
        courses.remove(courseNode);
        for (GradeNode g = courseNode.first; g!= null ; g = g.nextGradeCourse)
            removeGradeNode(g);
    }

    public void removeGrade(Grade grade){
        GradeNode gradeNode = getGradeNode(grade);
        removeGradeNode(gradeNode);
    }

    protected void removeGradeNode(GradeNode gradeNode){
        StudentNode studentNode = gradeNode.studentNode;
        CourseNode courseNode = gradeNode.courseNode;

        if (last == gradeNode)
            last = gradeNode.preGrade;
        if (studentNode.first == gradeNode)
            studentNode.first = gradeNode.nextGradeStudent;
        if (courseNode.first == gradeNode)
            courseNode.first = gradeNode.nextGradeCourse;
        if (studentNode.last == gradeNode)
            studentNode.last = gradeNode.preGradeStudent;
        if (courseNode.last == gradeNode)
            courseNode.last = gradeNode.preGradeCourse;

        if (gradeNode.preGrade != null)
            gradeNode.preGrade.nextGrade = gradeNode.nextGrade;
        if (gradeNode.nextGrade != null)
            gradeNode.nextGrade.preGrade = gradeNode.preGrade;
        if (gradeNode.preGradeStudent != null)
            gradeNode.preGradeStudent.nextGradeStudent = gradeNode.nextGradeStudent;
        if (gradeNode.nextGradeStudent != null)
            gradeNode.nextGradeStudent.preGradeStudent = gradeNode.preGradeStudent;
        if (gradeNode.preGradeCourse != null)
            gradeNode.preGradeCourse.nextGradeCourse = gradeNode.nextGradeCourse;
        if (gradeNode.nextGradeCourse != null)
            gradeNode.nextGradeCourse.preGradeCourse = gradeNode.preGradeCourse;

        size--;
        courseNode.size--;
        studentNode.size--;
    }

    @Override
    public Iterator<Grade> iterator() {
        return new Iterator<Grade>() {
            GradeNode current = last;
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Grade next() {
                Grade grade = current.grade;
                current = current.preGrade;
                return grade;
            }
        };
    }

    public int getSize() {
        return size;
    }


    public int getSizeOfStudent(long code){
        return getStudentNode(code).size;
    }

    public Iterator<Grade> getGradesByStudent(long code){
        return getStudentNode(code).iterator();
    }

    public int getSizeOfCourse(int code){
        return getCourseNode(code).size;
    }

    public Iterator<Grade> getGradesByCourse(int code){
        return getCourseNode(code).iterator();
    }
}

class GradeNode{
    Grade grade;
    GradeNode nextGrade;
    GradeNode preGrade;
    GradeNode nextGradeCourse;
    GradeNode preGradeCourse;
    GradeNode nextGradeStudent;
    GradeNode preGradeStudent;
    StudentNode studentNode;
    CourseNode courseNode;

    public GradeNode(Grade grade) {
        this.grade = grade;
    }
}

class StudentNode implements Iterable<Grade>{
    Student student;
    int size;
    GradeNode first;
    GradeNode last;

    public StudentNode(Student student) {
        this.student = student;
        size = 0;
    }

    @Override
    public Iterator<Grade> iterator() {
        return new Iterator<Grade>() {
            GradeNode current = first;
            @Override
            public boolean hasNext() {
                return current != null;
            }
            @Override
            public Grade next() {
                Grade grade = current.grade;
                current = current.nextGradeStudent;
                return grade;
            }
        };
    }
}

class CourseNode implements Iterable<Grade>{
    Course course;
    int size;
    GradeNode first;
    GradeNode last;

    public CourseNode(Course course) {
        this.course = course;
        size = 0;
    }

    @Override
    public Iterator<Grade> iterator() {
        return new Iterator<Grade>() {
            GradeNode current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }
            @Override
            public Grade next() {
                Grade grade = current.grade;
                current = current.nextGradeCourse;
                return grade;
            }
        };
    }
}
