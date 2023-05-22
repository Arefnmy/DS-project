package data_structure;

public class Vertex<T> {
    LinkedList<Vertex<T>> adjacent = new LinkedList<>();
    T element;
    Color color;

    public Vertex(T element) {
        this.element = element;
        color = Color.WHITE;
    }
}
