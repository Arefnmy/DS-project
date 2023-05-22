import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int t = scanner.nextInt();
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int p = scanner.nextInt();
        PTU ptu = new PTU(a , b , p);

        while (t -- > 0){
            String op = scanner.next();
            switch (op){
                case "ADDS" :
                    ptu.addStudent(scanner.nextLong() , scanner.next());
                    break;
                case "ADDC" :
                    ptu.addCourse(scanner.nextInt() , scanner.next());
                    break;
                case "ADDG" :
                    ptu.addGrade(scanner.nextLong() , scanner.nextInt() , scanner.next() , scanner.nextFloat());
                    break;
                case "EDITS" :
                    ptu.editStudent(scanner.nextLong(), scanner.next());
                    break;
                case "EDITC" :
                    ptu.editCourse(scanner.nextInt() , scanner.next());
                    break;
                case "EDITG" :
                    ptu.editGrade(scanner.nextLong() , scanner.nextInt() , scanner.nextFloat());
                    break;
                case "DELETES" :
                    ptu.deleteStudent(scanner.nextLong());
                    break;
                case "DELETEC" :
                    ptu.deleteCourse(scanner.nextInt());
                    break;
                case "DELETEG" :
                    ptu.deleteGrade(scanner.nextLong() , scanner.nextInt());
                    break;
                case "NUMBERC" :
                    ptu.numberOfCourse(scanner.nextLong());
                    break;
                case "NUMBERS" :
                    ptu.numberOfStudent(scanner.nextInt());
                    break;
                case "SEARCHSN" :
                    ptu.searchStudentByName(scanner.next());
                    break;
                case "SEARCHCN" :
                    ptu.searchCourseByName(scanner.next());
                    break;
                case "SEARCHSC" :
                    ptu.searchStudentByCode(scanner.nextLong());
                    break;
                case "SEARCHCC" :
                    ptu.searchCourseByCode(scanner.nextInt());
                    break;
                case "ISRELATIVE" :
                    ptu.isRelative(scanner.nextInt() , scanner.nextInt());
                    break;
                case "ALLRELATIVE" :
                    ptu.allRelative(scanner.nextInt());
                    break;
                case "COMPARE" :
                    ptu.compare(scanner.nextLong() , scanner.nextLong());
                    break;
                case "MINRISK" :
                    ptu.minRisk(scanner.nextLong());
                    break;
            }

        }
        //ptu.print();
    }
}
