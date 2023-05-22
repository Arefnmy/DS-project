package data_structure;

import model.Grade;

import java.util.Iterator;
import java.util.function.BiPredicate;

public class Algorithms {


    public static<T> void BFSVisit(Vertex<T> vertex , Graph<T> graph){
        for (Vertex<T> v : graph.getVertices())
            v.color = Color.WHITE;
        vertex.color = Color.GRAY;
        LinkedList<Vertex<T>> queue = new LinkedList<>();
        queue.addFirst(vertex);
        while (!queue.isEmpty()){
            Vertex<T> u = queue.removeLast();
            for (Vertex<T> v : u.adjacent){
                if (v.color == Color.WHITE){
                    v.color = Color.GRAY;
                    //v.d , v.pi
                    queue.addFirst(v);
                }
            }
            u.color = Color.BLACK;
        }
    }

    public static<T> boolean havePath(Vertex<T> v1 , Vertex<T> v2 , Graph<T> graph){
        BFSVisit(v1 , graph);
        return v2.color == Color.BLACK;
    }

    public static<T> LinkedList<T> allPath(Vertex<T> vertex , Graph<T> graph){
        LinkedList<T> vertices = new LinkedList<>();
        /*BFSVisit(vertex , graph);
        for (Vertex<T> v : graph.getVertices())
            if (v.color == Color.BLACK)
                vertices.addLast(v);*/
        // add in main loop
        for (Vertex<T> v : graph.getVertices())
            v.color = Color.WHITE;
        vertex.color = Color.GRAY;
        LinkedList<Vertex<T>> queue = new LinkedList<>();
        queue.addFirst(vertex);
        while (!queue.isEmpty()){
            Vertex<T> u = queue.removeLast();
            for (Vertex<T> v : u.adjacent){
                if (v.color == Color.WHITE){
                    v.color = Color.GRAY;
                    //v.d , v.pi
                    queue.addFirst(v);
                }
            }
            u.color = Color.BLACK;
            vertices.addLast(u.element);
        }
        //remove first vertex
        vertices.removeFirst();
        return vertices;
    }

    /*//todo prev pointers
    static<T> Pair<Node<T> , Node<T>> split(Node<T> first){
        if (first == null)
            return new Pair<>(null , null);
        if (first.next == null)
            return new Pair<>(first , null);//todo
        Node<T> n2 = first.next;
        Pair<Node<T> , Node<T>> pair = split(n2.next);
        first.next = pair.first;
        n2.next = pair.second;
        return new Pair<>(first, n2);
    }

    static<T> Node<T> merge(Node<T> n1 , Node<T> n2 , BiPredicate<T,T> predicate){
        if (n1 == null)
            return n2;
        if (n2 == null)
            return n1;
        if (predicate.test(n1.element , n2.element)){
            n1.next = merge(n1.next , n2 , predicate);
            return n1;
        }
        n2.next = merge(n1 , n2.next , predicate);
        return n2;
    }

    //todo void
    public static<T> LinkedList<T> mergeSort(LinkedList<T> list , BiPredicate<T,T> predicate){
        if (list.getSize() <= 1)
            return list;
        Pair<Node<T> , Node<T>> pair = split(list.getFirstNode());
        LinkedList<T> l1 = new LinkedList<>();
        l1.setFirstNode(pair.first);
        LinkedList<T> l2 = new LinkedList<>();
        l2.setFirstNode(pair.second);
        l1 = mergeSort(l1 , predicate);
        l2 = mergeSort(l2 , predicate);
        LinkedList<T> l = new LinkedList<>();
        l.setFirstNode(merge(l1.getFirstNode() , l2.getFirstNode() , predicate));
        l.setSize(list.getSize());
        return l;
    }*/

    public static<T> LinkedList<T> mergeSort(LinkedList<T> list , BiPredicate<T,T> predicate){
        if (list.getSize() <= 1)
            return new LinkedList<>(list);
        //split
        LinkedList<T> l1 = new LinkedList<>();
        LinkedList<T> l2 = new LinkedList<>();
        Node<T> node = list.getFirstNode();
        for (int i = 0; i < list.getSize()/2; i++) {
            l1.addLast(node.element);
            node = node.next;
        }
        for (int i = list.getSize()/2 ; i < list.getSize(); i++) {
            l2.addLast(node.element);
            node = node.next;
        }

        l1 = mergeSort(l1 , predicate);
        l2 = mergeSort(l2 , predicate);

        LinkedList<T> l = new LinkedList<>();
        //merge
        Node<T> n1 = l1.getFirstNode();
        Node<T> n2 = l2.getFirstNode();
        while (n1 != null || n2 != null){
            if (n1 == null){
                l.addLast(n2.element);
                n2 = n2.next;
                continue;
            }
            if (n2 == null){
                l.addLast(n1.element);
                n1 = n1.next;
                continue;
            }
            if (predicate.test(n1.element , n2.element)){
                l.addLast(n1.element);
                n1 = n1.next;
                continue;
            }
            l.addLast(n2.element);
            n2 = n2.next;
        }
        return l;
    }

    public static LinkedList<Integer> mergeSort(LinkedList<Integer> list){
        return mergeSort(list , (i1,i2) -> i1 < i2);
    }

    /*public static float risk(int code , int semester , LinkedList<Grade> grades){
        int num = 0;
        float sum = 0;
        for (Grade g : grades){
            if (g.getSemesterNum() == semester && g.getCourse().getCode() == code){
                num ++;
                sum += g.getGrade();
            }
        }
        return num == 0 ? 0 : sum/num;
    }*/

    public static HashTable<HashTable<Float>> riskTable;

    public static void buildRiskTable(LinkedList<Grade> grades){ //todo p
        riskTable = new HashTable<>();
        HashTable<HashTable<LinkedList<Float>>> temp = new HashTable<>();
        LinkedList<Integer> semesterList = new LinkedList<>();
        HashTable<LinkedList<Integer>> courseList = new HashTable<>();
        for (Grade g : grades){
            int sem = g.getSemesterNum();
            int cn = g.getCourse().getCode();
            float gr = g.getGrade();
            Pair<Boolean , HashTable<LinkedList<Float>> > t = temp.addObjectIfAbsent(sem , new HashTable<>());
            if (t.first) {
                semesterList.addLast(sem);
                courseList.addObject(sem , new LinkedList<>());
                riskTable.addObject(sem , new HashTable<>());
            }
            Pair<Boolean , LinkedList<Float>> f = t.second.addObjectIfAbsent(cn , new LinkedList<>());
            if (f.first) {
                courseList.getObject(sem).addLast(cn);
            }
            f.second.addLast(gr);
        }
        for (Integer s : semesterList){
            for (Integer c : courseList.getObject(s)){
                float sum = 0;
                LinkedList<Float> scores = temp.getObject(s).getObject(c);
                for (Float f : scores)
                    sum += f;
                float risk = scores.getSize() == 0 ? 0 : sum / scores.getSize();
                riskTable.getObject(s).addObject(c , risk);
            }
        }
    }

    public static float getRisk(int semester , int course){
        Float f = riskTable.getObject(semester).getObject(course);
        return f == null ? 0 : f;
    }

    public static Pair<LinkedList<Integer> , HashTable<LinkedList<Integer>>> minRisk(LinkedList<Grade> gradeList){
        HashTable<Integer> semesterSizeTable = new HashTable<>();
        HashTable<LinkedList<Integer>> semesterCoursesTable = new HashTable<>();
        LinkedList<Integer> semesterList = new LinkedList<>();
        for (Grade g : gradeList){
            /*Integer i = null;
            if (semesterCoursesTable.getNum() > 0)
                i  = semesterSizeTable.getObject(g.getSemesterNum());
            if (i == null) {
                semesterSizeTable.addObject(g.getSemesterNum(), 1);
                semesterList.addLast(g.getSemesterNum());
                semesterCoursesTable.addObject(g.getSemesterNum() , new LinkedList<>());
            }
            else
                semesterSizeTable.setObject(g.getSemesterNum() , i + 1);*/

            Pair<Boolean , Integer> p = semesterSizeTable.addObjectIfAbsent(g.getSemesterNum() , 1);
            if (p.first){
                semesterList.addLast(g.getSemesterNum());
                semesterCoursesTable.addObject(g.getSemesterNum() , new LinkedList<>());
            }
            else
                semesterSizeTable.setObject(g.getSemesterNum() , p.second + 1);
        }
        Pair<Float ,HashTable<LinkedList<Integer>>> pair =
                perm(semesterSizeTable , semesterCoursesTable , semesterList , gradeList.getFirstNode());
        return new Pair<>(semesterList , pair.second);
    }

    static Pair<Float ,HashTable<LinkedList<Integer>>> perm(HashTable<Integer> sizes , HashTable<LinkedList<Integer>> courses,
                                                             LinkedList<Integer> semesterList , Node<Grade> gradeNode){
        HashTable<LinkedList<Integer>> courseTable = null;
        float totalScore = -1;
        if(gradeNode == null) {
            //courseTable = courses.clone();
            courseTable = new HashTable<>();
            for (Integer i : semesterList){
                courseTable.addObject(i , new LinkedList<>());
                for (Integer j : courses.getObject(i))
                    courseTable.getObject(i).addLast(j);
            }
            return new Pair<>(0f, courseTable);
        }
        for (Integer i : semesterList){
            int s = sizes.getObject(i);
            if (s == 0)
                continue;
            sizes.setObject(i , s - 1);
            courses.getObject(i).addLast(gradeNode.element.getCourse().getCode());
            Pair<Float ,HashTable<LinkedList<Integer>>> pair =
                    perm(sizes , courses , semesterList , gradeNode.next);
            float score = pair.first;
            float t = score + getRisk(i , gradeNode.element.getCourse().getCode());
            if (totalScore < t){
                totalScore = t;
                courseTable = pair.second;
            }
            else if (totalScore == t){//todo check
                if (morePriority(semesterList , pair.second , courseTable))
                    courseTable = pair.second;
            }
            sizes.setObject(i , s);
            courses.getObject(i).removeLast();
        }
        return new Pair<>(totalScore , courseTable);
    }

    public static boolean morePriority(LinkedList<Integer> keys ,
                                       HashTable<LinkedList<Integer>> l1 , HashTable<LinkedList<Integer>> l2){
        for (Integer i : keys){ //todo iterator on table , table.keys
            LinkedList<Integer> list1 = l1.getObject(i);
            LinkedList<Integer> list2 = l2.getObject(i);
            list1 = mergeSort(list1);
            list2 = mergeSort(list2);
            Iterator<Integer> it2 = list2.iterator();
            for (Integer j1 : list1){
                Integer j2 = it2.next();
                if (j1 < j2)
                    return true;
                else if (j1 > j2)
                    return false;
            }
        }
        return false;
    }
}

