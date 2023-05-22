package data_structure;

import model.Course;
import model.Grade;
import model.Student;

import java.util.Iterator;

public class GradeCollection2 extends GradeCollection{
    private BST<StudentNode> studentTree;
    private BST<CourseNode> courseTree;
    private HashTable<StudentNode> studentTable;
    private HashTable<CourseNode> courseTable;

    public GradeCollection2(int a , int b , int p){
        super();
        //studentTree = new BST<>();
        //courseTree = new BST<>();
        studentTree = new RedBlackTree<>();
        courseTree = new RedBlackTree<>();
        studentTable = new HashTable<>(a , b , p);
        courseTable = new HashTable<>(a , b , p);
    }

    @Override
    public void addStudent(Student student) {
        StudentNode studentNode = new StudentNode(student);
        students.addLast(studentNode);
        studentTree.addObject(student.getName() , studentNode);
        studentTable.addObject(student.getCode() , studentNode);
        //studentTree.printInorder();
    }

    @Override
    public void addCourse(Course course) {
        CourseNode courseNode = new CourseNode(course);
        courses.addLast(courseNode);
        courseTree.addObject(course.getName() , courseNode);
        courseTable.addObject(course.getCode() , courseNode);
    }

    @Override
    public void removeStudent(long code) {
        StudentNode studentNode = getStudentNode(code);
        students.remove(studentNode);
        for (GradeNode g = studentNode.first; g != null ; g = g.nextGradeStudent)
            removeGradeNode(g);
        studentTree.deleteObject(studentNode.student.getName());
        studentTable.deleteObject(code);
        //studentTree.printInorder();
    }

    @Override
    public void removeCourse(int code) {
        CourseNode courseNode = getCourseNode(code);
        courses.remove(courseNode);
        for (GradeNode g = courseNode.first; g!= null ; g = g.nextGradeCourse)
            removeGradeNode(g);
        courseTree.deleteObject(courseNode.course.getName());
        courseTable.deleteObject(code);
    }

    public Pair<Iterator<Grade>, Student> getGradesByStudent(String name){
        StudentNode studentNode = studentTree.getObject(name);
        return new Pair<>(studentNode.iterator() , studentNode.student);
    }

    public Pair<Iterator<Grade> , Course> getGradesByCourse(String name){
        CourseNode courseNode = courseTree.getObject(name);
        return new Pair<>(courseNode.iterator() , courseNode.course);
    }

    public void setStudentName(long code ,String name){
        StudentNode studentNode = getStudentNode(code);
        studentTree.deleteObject(studentNode.student.getName());
        studentTree.addObject(name , studentNode);
    }

    public void setCourseName(int code ,String name){
        CourseNode courseNode = getCourseNode(code);
        courseTree.deleteObject(courseNode.course.getName());
        courseTree.addObject(name , courseNode);
    }

    public Pair<Iterator<Grade> , Pair<Student , Integer>> getGradesByStudent2(long code){
        Pair<StudentNode , Integer> pair = studentTable.getObjectWithHash(code);
        return new Pair<>(pair.first.iterator() , new Pair<>(pair.first.student , pair.second));
    }

    public Pair<Iterator<Grade> , Pair<Course , Integer>> getGradesByCourse2(int code){
        Pair<CourseNode , Integer> pair = courseTable.getObjectWithHash(code);
        return new Pair<>(pair.first.iterator() , new Pair<>(pair.first.course , pair.second));
    }
}
