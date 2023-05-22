package data_structure;

import java.util.Iterator;

public class LinkedList<T> implements Iterable<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public LinkedList() {
        first = null;
        last = null;
        size = 0;
    }

    public LinkedList(Iterator<T> iterator){
        iterator.forEachRemaining(this::addLast);
    }

    public LinkedList(LinkedList<T> list){
        for (T t : list)
            addLast(t);
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void addLast(T object){
        if (isEmpty()){
            first = new Node<>(object , null , null);
            last = first;
        }
        else {
            Node<T> node = new Node<>(object , last , null);
            last.next = node;
            last = node;
        }
        size ++;
    }

    public void addFirst(T object){
        if (isEmpty()){
            first = new Node<>(object , null , null);
            last = first;
        }
        else {
            Node<T> node = new Node<>(object , null , first);
            first.prev = node;
            first = node;
        }

        size ++;
    }

    public boolean remove(T object){
        for (Node<T> i = first; i != null; i = i.next){
            if (i.element.equals(object)){
                size--;
                if (i == first)
                    first = first.next;
                if (i == last)
                    last = last.prev;

                if (i.prev != null)
                    i.prev.next = i.next;
                if (i.next != null)
                    i.next.prev = i.prev;

                return true;
            }
        }
        return false;
    }

    public boolean contains(T object){
        for (T t : this)
            if (t == object)
                return true;
        return false;
    }

    public T removeLast(){
        T object = last.element;
        last = last.prev;
        size --;
        return object;
    }

    public T removeFirst(){
        T object = first.element;
        first = first.next;
        size --;
        return object;
    }

    public Iterator<Pair<T , T>> pairIterator(){
        return new Iterator<Pair<T , T>>() {
            Pair<Node<T> , Node<T>> pair = first == null ?
                    new Pair<>(null , null) : new Pair<>(first , first.next);
            @Override
            public boolean hasNext() {
                return pair.second != null && pair.first != null;
            }

            @Override
            public Pair<T, T> next() {
                Pair<T , T> p = new Pair<>(pair.first.element , pair.second.element);
                pair.second = pair.second.next;
                if (pair.second == null){
                    pair.first = pair.first.next;
                    pair.second = pair.first.next;
                }
                return p;
            }
        };
    }

    public int getSize() {
        return size;
    }

    public T getFirst() {
        return first.element;
    }

    public T getLast() {
        return last.element;
    }

    Node<T> getFirstNode(){
        return first;
    }

    void setFirstNode(Node<T> first){
        this.first = first;
    }

    void setSize(int size){
        this.size = size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> current = first;
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T element = current.element;
                current = current.next;
                return element;
            }
        };
    }
}

class Node<T> {
    T element;
    Node<T> prev;
    Node<T> next;

    public Node( T element, Node<T> prev, Node<T> next) {
        this.element = element;
        this.next = next;
        this.prev = prev;
    }
}
