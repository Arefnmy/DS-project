package data_structure;

public class BST<T> {
    protected TreeNode<T> root;
    static TreeNode NULL_NODE;

    public BST(){
        if (NULL_NODE == null) {
            NULL_NODE = new TreeNode<>(null , null);
            NULL_NODE.parent = NULL_NODE;
            NULL_NODE.leftChild = NULL_NODE;
            NULL_NODE.rightChild = NULL_NODE;
            NULL_NODE.color = Color.BLACK;
        }
        root = NULL_NODE;
    }

    public void addObject(String name , T pointer){
        TreeNode<T> node = new TreeNode<>(name , pointer);
        insert(node);
    }

    public void deleteObject(String name){
        delete(findNode(name));
    }

    public T getObject(String name){
        return findNode(name).pointer;
    }

    protected void insert(TreeNode<T> node){
        if (root == NULL_NODE){
            root = node;
            return;
        }
        TreeNode<T> prep = NULL_NODE;
        TreeNode<T> p = root;
        while (p != NULL_NODE){
            prep = p;
            int cmp = node.name.compareTo(p.name);
            if (cmp < 0)
                p = p.leftChild;
            else if (cmp > 0)
                p = p.rightChild;
            else return;
        }
        node.parent = prep;
        if (node.name.compareTo(prep.name) < 0)
            prep.leftChild = node;
        else
            prep.rightChild = node;
    }

    protected TreeNode<T> successor(TreeNode<T> node){
        if (node.rightChild != NULL_NODE)
            return minimum(node.rightChild);
        TreeNode<T> x = node.parent;
        while (x != NULL_NODE && x.rightChild == node){
            node = x;
            x = node.parent;
        }
        return x;
    }

    protected TreeNode<T> minimum(TreeNode<T> root){
        while (root.leftChild != NULL_NODE)
            root = root.leftChild;
        return root;
    }

    protected void delete(TreeNode<T> node){
        TreeNode<T> y;
        if (node.leftChild == NULL_NODE || node.rightChild == NULL_NODE)
            y = node;
        else
            y = successor(node);
        TreeNode<T> x;
        if (y.leftChild == NULL_NODE)
            x = y.rightChild;
        else
            x = y.leftChild;
        if (x != NULL_NODE)
            x.parent = y.parent;
        if (y.parent == NULL_NODE)
            root = x;
        else if (y == y.parent.leftChild){
            y.parent.leftChild = x;
        }
        else
            y.parent.rightChild = x;

        if (y != node){
            node.name = y.name;
            node.pointer = y.pointer;
            node.color = y.color;
        }
    }

    protected TreeNode<T> findNode(String name){
        TreeNode<T> root = this.root;
        while (root != NULL_NODE){
            int cmp = name.compareTo(root.name);
            if (cmp == 0)
                return root;
            if (cmp < 0)
                root = root.leftChild;
            else
                root = root.rightChild;
        }
        return null;
    }

    public void printInorder(){
        System.out.print("root : " + root.name + "  ");
        printInorder(root);
        System.out.println();
    }
    public void printInorder(TreeNode<T> node){
        if (node.leftChild != NULL_NODE)
            printInorder(node.leftChild);
        System.out.print( node.name + "\\" + node.color + " ");
        if (node.rightChild != NULL_NODE)
            printInorder(node.rightChild);
    }
}

class TreeNode<T>{
    String name;
    T pointer;
    TreeNode<T> parent;
    TreeNode<T> leftChild;
    TreeNode<T> rightChild;
    Color color;

    public TreeNode(String name, T pointer) {
        this.name = name;
        this.pointer = pointer;
        parent = BST.NULL_NODE;
        leftChild = BST.NULL_NODE;
        rightChild = BST.NULL_NODE;
        color = Color.WHITE;
    }
}
