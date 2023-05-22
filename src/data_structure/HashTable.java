package data_structure;

public class HashTable<T>{
    private int a;
    private int b;
    private int p;

    private int size;
    private int num;
    private LinkedList<TableNode<T>>[] array;

    public HashTable(int a, int b, int p) {
        this.a = a;
        this.b = b;
        this.p = p;

        size = 0;
        num = 0;
        array = new LinkedList[0];
    }

    public HashTable(){
        this(5 , 4 , 101);
    }

    private int hashFunction(long k){
        return (int)( ( (a*k + b) % p ) % size );
    }

    public void addObject(long code , T pointer){
        TableNode<T> node = new TableNode<>(code , pointer);
        insert(node);
    }

    public Pair<Boolean , T> addObjectIfAbsent(long code , T pointer){
        T o = getObject(code);
        if (o == null) {
            addObject(code, pointer);
            return new Pair<>(true , pointer);
        }
        return new Pair<>(false , o);
    }

    public void deleteObject(long code){
        delete(code);
    }

    public void setObject(long code , T object){
        int hash = hashFunction(code);
        LinkedList<TableNode<T>> list = array[hash];
        for (TableNode<T> t : list)
            if (t.code == code){
                t.pointer = object;
                break;
            }
    }

    public Pair<T , Integer> getObjectWithHash(long code){
        if (size == 0)
            return new Pair<>(null , null);
        int hash = hashFunction(code);
        Pair<T , Integer> pair = new Pair<>(null , hash);
        LinkedList<TableNode<T>> list = array[hash];
        for (TableNode<T> t : list)
            if (t.code == code){
                pair.first = t.pointer;
                break;
            }
        return pair;
    }

    public T getObject(long code){
        return getObjectWithHash(code).first;
    }

    private void insert(TableNode<T> node){
        if (size == 0) {
            array = new LinkedList[1];
            array[0] = new LinkedList<>();
            size = 1 ;
        }
        else if (size == num){
            size *= 2;
            LinkedList<TableNode<T>>[] newArray = new LinkedList[size];
            for (int i = 0; i < size; i++) {
                newArray[i] = new LinkedList<>();
            }
            for (int i = 0; i < size/2; i++) {
                for (TableNode<T> t : array[i]){
                    int index = hashFunction(t.code);
                    newArray[index].addLast(t);
                }
            }
            array = newArray;
        }
        array[hashFunction(node.code)].addLast(node);
        num ++;
    }

    private void delete(long code){
        if (num <= size/4){
            size /= 2;
            LinkedList<TableNode<T>>[] newArray = new LinkedList[size];
            for (int i = 0; i < size; i++) {
                newArray[i] = new LinkedList<>();
            }
            for (int i = 0; i < size*2; i++) {
                for (TableNode<T> t : array[i]){
                    int in = hashFunction(t.code);
                    newArray[in].addLast(t);
                }
            }
            array = newArray;
        }
        int index = hashFunction(code);
        TableNode<T> node = null;
        for (TableNode<T> t : array[index]){
            if (t.code == code){
                node = t;
                break;
            }
        }
        if (node == null)
            return;

        array[index].remove(node);
        num --;
    }

    public int getSize() {
        return size;
    }

    public int getNum() {
        return num;
    }

    /*@Override
    public HashTable<T> clone() {
        try {
            HashTable<T> clone = (HashTable<T>) super.clone();
            clone.array = new LinkedList[size];
            for (int i = 0; i < size; i++) {
              if (array[i] != null){
                  clone.array[i] = new LinkedList<>();
                  for (TableNode<T> t : array[i]) {
                      TableNode<T> t1 = new TableNode<>(t.code , t.pointer);
                      clone.array[i].addLast(t1);
                  }
              }
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }*/
}

class TableNode<T>{
    long code;
    T pointer;

    public TableNode(long code, T pointer) {
        this.code = code;
        this.pointer = pointer;
    }
}
