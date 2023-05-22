package data_structure;

public class RedBlackTree<T> extends BST<T> {

    @Override
    public void addObject(String name, T pointer) {
        TreeNode<T> node = new TreeNode<>(name, pointer);
        node.color = Color.WHITE;
        insert(node);
    }

    private TreeNode<T> uncle(TreeNode<T> node) {
        TreeNode<T> p = node.parent;
        TreeNode<T> gp = p.parent;
        if (p == gp.leftChild)
            return gp.rightChild;
        else
            return gp.leftChild;
    }

    private void leftRotate(TreeNode<T> node) {
        TreeNode<T> r = node.rightChild;
        node.rightChild = r.leftChild;
        if (r.leftChild != NULL_NODE)
            r.leftChild.parent = node;
        r.parent = node.parent;
        if (node.parent == NULL_NODE)
            root = r;
        else if (node == node.parent.leftChild)
            node.parent.leftChild = r;
        else
            node.parent.rightChild = r;
        r.leftChild = node;
        node.parent = r;
    }

    private void rightRotate(TreeNode<T> node) {
        TreeNode<T> l = node.leftChild;
        node.leftChild = l.rightChild;
        if (l.rightChild != NULL_NODE)
            l.rightChild.parent = node;
        l.parent = node.parent;
        if (node.parent == NULL_NODE)
            root = l;
        else if (node == node.parent.leftChild)
            node.parent.leftChild = l;
        else
            node.parent.rightChild = l;
        l.rightChild = node;
        node.parent = l;
    }

    protected void insert(TreeNode<T> node) {
        super.insert(node);
        insertFixUp(node);
    }

    protected void insertFixUp(TreeNode<T> node) {
        /*if (node == root) {
            node.color = Color.BLACK;
            return;
        }
        if (node.parent.color == Color.BLACK)
            return;

        TreeNode<T> u = uncle(node);
        TreeNode<T> gp = node.parent.parent;
        if (u.color == Color.WHITE) {
            node.parent.color = Color.BLACK;
            u.parent.color = Color.WHITE;
            u.color = Color.BLACK;
            node = u.parent;
        } else if (u == gp.rightChild) {
            if (node == node.parent.rightChild) {
                node = node.parent;
                leftRotate(node);
            }
            node.parent.color = Color.BLACK;
            node.parent.parent.color = Color.WHITE;
            rightRotate(node.parent.parent);
        } else {
            if (node == node.parent.leftChild) {
                node = node.parent;
                rightRotate(node);
            }
            node.parent.color = Color.BLACK;
            node.parent.parent.color = Color.WHITE;
            leftRotate(node.parent.parent);
        }
        insertFixUp(node);*/
        while (node.parent.color == Color.WHITE) {
            TreeNode<T> uncle;
            if (node.parent == node.parent.parent.leftChild) {
                uncle = node.parent.parent.rightChild;
                if (uncle.color == Color.WHITE) {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.WHITE;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.rightChild) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.WHITE;
                    rightRotate(node.parent.parent);
                }
            } else {
                uncle = node.parent.parent.leftChild;
                if (uncle.color == Color.WHITE) {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.WHITE;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.leftChild) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.WHITE;
                    leftRotate(node.parent.parent);
                }
            }
        }
        root.color = Color.BLACK;
    }

    protected void delete(TreeNode<T> node) {
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
        else if (y == y.parent.leftChild) {
            y.parent.leftChild = x;
        } else
            y.parent.rightChild = x;

        if (y != node) {
            node.name = y.name;
            node.pointer = y.pointer;
        }

        if (y.color == Color.BLACK)
            deleteFixUp(x);
    }

    private void deleteFixUp(TreeNode<T> node) {
        while (/*node != NULL_NODE &&*/ node != root && node.color == Color.BLACK) {
            TreeNode<T> w;
            if (node == node.parent.leftChild) {
                w = node.parent.rightChild;
                if (w.color == Color.WHITE) {
                    w.color = Color.BLACK;
                    node.parent.color = Color.WHITE;
                    leftRotate(node.parent);
                    w = node.parent.rightChild;
                }
                if (w.leftChild.color == Color.BLACK && w.rightChild.color == Color.BLACK) {
                    w.color = Color.WHITE;
                    node = node.parent;
                } else {
                    if (w.rightChild.color == Color.BLACK) {
                        w.leftChild.color = Color.BLACK;
                        w.color = Color.WHITE;
                        rightRotate(w);
                        w = node.parent.rightChild;
                    }
                    w.color = node.parent.color;
                    node.parent.color = Color.BLACK;
                    w.rightChild.color = Color.BLACK;
                    leftRotate(node.parent);
                    node = root;
                }
            } else {
                w = node.parent.leftChild;
                if (w.color == Color.WHITE) {
                    w.color = Color.BLACK;
                    node.parent.color = Color.WHITE;
                    rightRotate(node.parent);
                    w = node.parent.leftChild;
                }
                if (w.rightChild.color == Color.BLACK && w.leftChild.color == Color.BLACK) {
                    w.color = Color.WHITE;
                    node = node.parent;
                } else {
                    if (w.leftChild.color == Color.BLACK) {
                        w.rightChild.color = Color.BLACK;
                        w.color = Color.WHITE;
                        leftRotate(w);
                        w = node.parent.leftChild;
                    }
                    w.color = node.parent.color;
                    node.parent.color = Color.BLACK;
                    w.leftChild.color = Color.BLACK;
                    rightRotate(node.parent);
                    node = root;
                }
            }
        }
        node.color = Color.BLACK;
    }
}
