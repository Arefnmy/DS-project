import data_structure.*;
import model.Course;
import model.Grade;
import model.Student;

import java.util.Iterator;

public class PTU {
    private final LinkedList<Student> studentList;
    private final LinkedList<Course> courseList;
    private GradeCollection gradeCollection;
    private GradeCollection2 gradeCollection2;
    private Graph<Course> courseGraph;
    private Graph<Student> studentGraph;
    private final int phase;

    public PTU(int a , int b , int p){
        studentList = new LinkedList<>();
        courseList = new LinkedList<>();
        if (p == 1) {
            gradeCollection = new GradeCollection();
            phase = 1;
        }
        else {
            gradeCollection2 = new GradeCollection2(a, b, p);
            gradeCollection = gradeCollection2;
            phase = 2;
        }
    }

    public void addStudent(long code , String name){
        Student student = new Student(code , name);
        studentList.addLast(student);
        gradeCollection.addStudent(student);
    }

    public void addCourse(int code , String name){
        Course course = new Course(code , name);
        courseList.addLast(course);
        gradeCollection.addCourse(course);
    }

    public void addGrade(long studentCode, int courseCode , String semester , float grade){
        Student student = getStudent(studentCode);
        Course course = getCourse(courseCode);
        Grade g = new Grade(semester , grade , student , course);
        gradeCollection.addGrade(g);
    }

    public void editStudent(long code , String name){
        Student student = getStudent(code);

        //phase2
        if (phase == 2) {
            gradeCollection2.setStudentName(code , name);
        }
        student.setName(name);
    }

    public void editCourse(int code , String name){
        Course course = getCourse(code);

        //phase2
        if (phase == 2) {
            gradeCollection2.setCourseName(code , name);
        }
        course.setName(name);
    }

    public void editGrade(long studentCode ,int courseCode , float grade){
        getGrade(studentCode , courseCode).setGrade(grade);
    }

    public void deleteStudent(long code){
        Student student = getStudent(code);
        studentList.remove(student);
        gradeCollection.removeStudent(code);
    }

    public void deleteCourse(int code){
        Course course = getCourse(code);
        courseList.remove(course);
        gradeCollection.removeCourse(code);
    }

    public void deleteGrade(long studentCode , int courseCode){
        gradeCollection.removeGrade(getGrade(studentCode , courseCode));
    }

    public void numberOfCourse(long code){
        System.out.println(gradeCollection.getSizeOfStudent(code));
    }

    public void numberOfStudent(int code){
        System.out.println(gradeCollection.getSizeOfCourse(code));
    }

    //phase 2
    public void searchStudentByName(String name){
        Pair<Iterator<Grade> , Student> pair = gradeCollection2.getGradesByStudent(name);
        Student student = pair.second;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(student.getCode()).append(" ").append(student.getName()).append(" ");
        Iterator<Grade> courses = pair.first;
        StringBuilder stringBuilder2 = new StringBuilder();
        int counter = 0;
        while (courses.hasNext()){
            Grade grade = courses.next();
            stringBuilder2.append(grade.getCourse().getCode()).append(" ").append(grade.getSemester())
                    .append(" ").append(grade.getGradeStr()).append('\n');
            counter ++;
        }
        stringBuilder1.append(counter).append('\n').append(stringBuilder2);
        System.out.print(stringBuilder1);
    }

    public void searchCourseByName(String name){
        Pair<Iterator<Grade> , Course> pair = gradeCollection2.getGradesByCourse(name);
        StringBuilder stringBuilder1 = new StringBuilder();
        Course course = pair.second;
        stringBuilder1.append(course.getCode()).append(" ").append(course.getName()).append(" ");
        Iterator<Grade> courses = pair.first;
        StringBuilder stringBuilder2 = new StringBuilder();
        int counter = 0;
        while (courses.hasNext()){
            Grade grade = courses.next();
            stringBuilder2.append(grade.getStudent().getCode()).append(" ").append(grade.getSemester())
                    .append(" ").append(grade.getGradeStr()).append('\n');
            counter ++;
        }
        stringBuilder1.append(counter).append('\n').append(stringBuilder2);
        System.out.print(stringBuilder1);
    }

    //todo use one function for build string
    public void searchStudentByCode(long code){
        Pair<Iterator<Grade> , Pair<Student , Integer>> pair = gradeCollection2.getGradesByStudent2(code);
        StringBuilder stringBuilder1 = new StringBuilder();
        Student student = pair.second.first;
        stringBuilder1.append(pair.second.second).append('\n').append(student.getCode()).append(" ")
                .append(student.getName()).append(" ");
        Iterator<Grade> courses = pair.first;
        StringBuilder stringBuilder2 = new StringBuilder();
        int counter = 0;
        while (courses.hasNext()){
            Grade grade = courses.next();
            stringBuilder2.append(grade.getCourse().getCode()).append(" ").append(grade.getSemester())
                    .append(" ").append(grade.getGradeStr()).append('\n');
            counter ++;
        }
        stringBuilder1.append(counter).append('\n').append(stringBuilder2);
        System.out.print(stringBuilder1);
    }

    public void searchCourseByCode(int code){
        Pair<Iterator<Grade> , Pair<Course , Integer>> pair = gradeCollection2.getGradesByCourse2(code);
        StringBuilder stringBuilder1 = new StringBuilder();
        Course course = pair.second.first;
        stringBuilder1.append(pair.second.second).append('\n').append(course.getCode()).append(" ")
                .append(course.getName()).append(" ");
        Iterator<Grade> courses = pair.first;
        StringBuilder stringBuilder2 = new StringBuilder();
        int counter = 0;
        while (courses.hasNext()){
            Grade grade = courses.next();
            stringBuilder2.append(grade.getStudent().getCode()).append(" ").append(grade.getSemester())
                    .append(" ").append(grade.getGradeStr()).append('\n');
            counter ++;
        }
        stringBuilder1.append(counter).append('\n').append(stringBuilder2);
        System.out.print(stringBuilder1);
    }

    public void buildCourseGraph(){
        courseGraph = new Graph<>();
        courseList.forEach( c -> courseGraph.addVertex(c));
        /*Iterator<Course> iterator = courseList.iterator();
        for (int i = 0; i < courseList.getSize(); i++) {
            Course c1 = iterator.next();
            Iterator<Course> it = courseList.iterator();
            for (int j = 0; j < i + 1; j++) {
                it.next();
            }
            while (it.hasNext()) {
                Course course = it.next();
                if (areAdjacent(c1, course))
                    courseGraph.addEdge(c1, course);
            }
        }*/
        Iterator<Pair<Course , Course>> pairIterator = courseList.pairIterator();
        pairIterator.forEachRemaining( p-> {
            if (areAdjacent(p.first , p.second))
                courseGraph.addEdge(p.first , p.second);
        });
    }

    public void buildStudentGraph() {
        studentGraph = new Graph2<>();
        studentList.forEach(s -> studentGraph.addVertex(s));
        /*Iterator<Student> iterator = studentList.iterator();
        for (int i = 0; i < studentList.getSize(); i++) {
            Student s1 = iterator.next();
            Iterator<Student> it = studentList.iterator();
            for (int j = 0; j < i + 1; j++) {
                it.next();
            }
            while (it.hasNext()) {
                Student student = it.next();
                int state = areAdjacent(s1 , student);
                if (state == 1)
                    studentGraph.addEdge(s1 , student);
                else if (state == 2)
                    studentGraph.addEdge(student , s1);
            }
        }*/
        Iterator<Pair<Student , Student>> pairIterator = studentList.pairIterator();
        pairIterator.forEachRemaining( p -> {
            int state = areAdjacent(p.first , p.second);
            if (state == 1)
                studentGraph.addEdge(p.first , p.second);
            else if (state == 2)
                studentGraph.addEdge(p.second , p.first);
        });
    }

    private boolean areAdjacent(Course c1 , Course c2){
        int count = 0;
        int counter1 = 0; // size of course1
        int counter2 = 0; // size of course2
        Iterator<Grade> grades1 = gradeCollection.getGradesByCourse(c1.getCode());
        Iterator<Grade> g = gradeCollection.getGradesByCourse(c2.getCode());
        LinkedList<Grade> grades2 = new LinkedList<>(g);
        while (grades1.hasNext()){
            counter1 ++;
            Student student = grades1.next().getStudent();
            for (Grade grade : grades2){
                if (grade.getStudent() == student){
                    count ++;
                    break;
                }
            }
        }
        counter2 = grades2.getSize();
        return count > counter1/2 && count > counter2/2;
    }

    private int areAdjacent(Student s1 , Student s2){ // 1 : s1->s2 , 2 : s2->s1
        int courseNumber = 0; // common courses
        int counter1 = 0;// score s1 > s2
        int counter2 = 0;// score s2 > s1
        Iterator<Grade> grades1 = gradeCollection.getGradesByStudent(s1.getCode());
        Iterator<Grade> g = gradeCollection.getGradesByStudent(s2.getCode());
        LinkedList<Grade> grades2 = new LinkedList<>(g);
        while (grades1.hasNext()){
            Grade grade1 = grades1.next();
            for (Grade grade : grades2) {
                if (grade1.getCourse() == grade.getCourse()) {
                    courseNumber++;
                    if (grade1.getGrade() > grade.getGrade())
                        counter1++;
                    else if (grade1.getGrade() < grade.getGrade())
                        counter2++;
                    break;
                }
            }
        }
        if (counter1 > courseNumber/2)
            return 1;
        if (counter2 > courseNumber/2)
            return 2;
        return 0;
    }

    public void isRelative(int code1 , int code2){
        if (courseGraph == null)
            buildCourseGraph();
        Course course1 = getCourse(code1);
        Course course2 = getCourse(code2);

        System.out.println(Algorithms.havePath(courseGraph.getVertex(course1) , courseGraph.getVertex(course2) , courseGraph) ?
                "yes" : "no");
    }

    public void allRelative(int code){
        if (courseGraph == null)
            buildCourseGraph();

        Course course = getCourse(code);
        LinkedList<Course> courses = Algorithms.allPath(courseGraph.getVertex(course) , courseGraph);
        //sort
        courses = Algorithms.mergeSort(courses , (c1,c2) -> c1.getCode() < c2.getCode());
        StringBuilder stringBuilder = new StringBuilder();
        //stringBuilder.append(courses.getSize()).append(" ");
        courses.forEach( c -> stringBuilder.append(c.getCode()).append(" "));//todo size?
        System.out.println(stringBuilder);
    }

    public void compare(long code1 , long code2){
        if (studentGraph == null)
            buildStudentGraph();
        Student student1 = getStudent(code1);
        Student student2 = getStudent(code2);

        boolean firstPath = Algorithms.havePath(studentGraph.getVertex(student1) , studentGraph.getVertex(student2) , studentGraph);
        boolean secondPath = Algorithms.havePath(studentGraph.getVertex(student2) , studentGraph.getVertex(student1) , studentGraph);
        if (firstPath == secondPath)
            System.out.println("?");
        else if (firstPath)
            System.out.println(">");
        else
            System.out.println("<");
    }

    public void minRisk(long code){
        LinkedList<Grade> gradeList = new LinkedList<>(gradeCollection.getGradesByStudent(code));
        if (Algorithms.riskTable == null) {
            LinkedList<Grade> totalGrade = new LinkedList<>(gradeCollection.iterator());
            Algorithms.buildRiskTable(totalGrade);
        }
        Pair<LinkedList<Integer> ,HashTable<LinkedList<Integer>>> pair = Algorithms.minRisk(gradeList);
        StringBuilder stringBuilder = new StringBuilder();
        LinkedList<Integer> semesters = Algorithms.mergeSort(pair.first);
        for (Integer semester : semesters){
            stringBuilder.append(semester).append(' ');
            LinkedList<Integer> courses = Algorithms.mergeSort(pair.second.getObject(semester));
            for (Integer course : courses)
                stringBuilder.append(course).append(' ');
            stringBuilder.append('\n');
        }
        System.out.print(stringBuilder);
    }

    public Student getStudent(long code){
        for (Student s : studentList){
            if (s.getCode() == code)
                return s;
        }
        return null;
    }

    public Course getCourse(int code){
        for (Course c : courseList){
            if (c.getCode() == code)
                return c;
        }
        return null;
    }

    public Grade getGrade(long studentCode , int courseCode){
        for (Grade g : gradeCollection)
            if (g.getStudent().getCode() == studentCode && g.getCourse().getCode() == courseCode)
                return g;
        return null;
    }

    public void print(){
        System.out.println("Students : " + studentList.getSize());
        for (Student s : studentList)
            System.out.println(s.getName() + " " + s.getCode());
        System.out.println("Courses : " + courseList.getSize());
        for (Course c : courseList)
            System.out.println(c.getName() + " " + c.getCode());
        System.out.println("Grades : " + gradeCollection.getSize());
        for (Grade g : gradeCollection)
            System.out.println(g.getStudent().getName() + " " + g.getCourse().getName() + " " + g.getSemester() + " " + g.getGrade());
    }

}
