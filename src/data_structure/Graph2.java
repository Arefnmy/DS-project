package data_structure;

public class Graph2<T> extends Graph<T>{

    @Override
    public void addEdge(T obj1, T obj2) {
        Vertex<T> ver1 = getVertex(obj1);
        Vertex<T> ver2 = getVertex(obj2);

        ver1.adjacent.addLast(ver2);
    }
}
