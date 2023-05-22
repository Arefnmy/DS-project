package data_structure;

public class Graph<T> {
    private LinkedList<Vertex<T>> vertices = new LinkedList<>();

    public Vertex<T> getVertex(T object){
        for (Vertex<T> v : vertices)
            if (v.element == object)
                return v;
        return null;
    }

    public void addVertex(T object){
        vertices.addLast(new Vertex<>(object));
    }

    public void addEdge(T obj1 , T obj2){
        Vertex<T> ver1 = getVertex(obj1);
        Vertex<T> ver2 = getVertex(obj2);

        ver1.adjacent.addLast(ver2);
        ver2.adjacent.addLast(ver1);
    }

    public LinkedList<Vertex<T>> getVertices() {
        return vertices;
    }
}

