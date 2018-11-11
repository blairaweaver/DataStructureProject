public class RedBlackTree<K extends Comparable<K>, E> {
    private static class RBNode<K, E> {
        private E element;
        private K key;
        private int aux;
        private RBNode<K, E> parent;
        private RBNode<K, E> leftChild;
        private RBNode<K, E> rightChild;
        
        public RBNode(K k, E e, RBNode<K, E> p, int aux) {
            this.element = e;
            this.key = k;
            this.parent = p;
            this.aux = aux;
            this.leftChild = null;
            this.rightChild = null;
        }

        public E getElement() {
            return element;
        }

        public K getKey() {
            return key;
        }

//        Black is 0, red is 1
        public int getAux() {
            return aux;
        }

        public void setAux(int aux) {
            this.aux = aux;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public RBNode<K, E> getParent() {
            return parent;
        }

        public void setParent(RBNode<K, E> p){
            this.parent = p;
        }

        public RBNode<K, E> getLeftChild() {
            return leftChild;
        }

        public RBNode<K, E> getRightChild() {
            return rightChild;
        }

        public void setLeftChild(RBNode<K, E> l) {
            this.leftChild = l;
        }

        public void setRightChild(RBNode<K, E> r) {
            this.rightChild = r;
        }

        public RBNode<K, E> getSibling(){
            RBNode<K, E> parent = getParent();
            if (parent.rightChild == this) return parent.leftChild;
            else return parent.rightChild;
        }
    }

    private RBNode root;
    private int blackHeight = 0;

    public RedBlackTree() {
        root = null;
    }

    public RedBlackTree(K k, E e) {
        root = new RBNode(k, e, null, 0);
        root.setLeftChild(new RBNode(null, null, root, 0));
        root.setRightChild(new RBNode(null, null, root, 0));
//        This doesn't count the dummy nodes. Maybe change this later?
        blackHeight = 1;
    }

    public boolean isBlack(RBNode e) {
        return e.getAux() == 0;
    }

    public boolean isRed(RBNode e) {
        return e.getAux() == 1;
    }

    public void makeBlack(RBNode e) {
        e.setAux(0);
    }

    public void makeRed(RBNode e) {
        e.setAux(1);
    }

    public void setColor(RBNode e, boolean toRed) {
        e.setAux(toRed ? 1 : 0);
    }

    public RBNode getRoot() {
        return root;
    }

    public int getBlackHeight() {
        return blackHeight;
    }
    
    public boolean isEmpty() {
        return root == null;
    }

//    External node is considered a dummy node
    public boolean isExternal(RBNode<K, E> e){
        return e.getElement() == null;
    }

//    Internal node is any node that is not a dummy node
//    Don't know if this method is needed
    public boolean isInternal(RBNode<K, E> e) {
        return e.getElement() != null;
    }

    public boolean isRoot(RBNode<K, E> e) {
        return root == e;
    }

    public void inOrder(RBNode<K, E> e) {
        if (e.getLeftChild() != null) inOrder(e.getLeftChild());
        if (e.getElement() != null) System.out.print(e.getElement() + " ");
        if (e.getRightChild() != null) inOrder(e.getRightChild());
    }

    public void preOrder(RBNode<K, E> e) {
        if (e.getElement() != null) System.out.print(e.getElement()+ " ");
        if (e.getLeftChild() != null) preOrder(e.getLeftChild());
        if (e.getRightChild() != null) preOrder(e.getRightChild());
    }

    public void postOrder(RBNode<K, E> e) {
        if (e.getLeftChild() != null) postOrder(e.getLeftChild());
        if (e.getRightChild() != null) postOrder(e.getRightChild());
        if (e.getElement() != null) System.out.print(e.getElement());
    }

    public RBNode<K, E> treeSearch(K k, RBNode<K, E> e) {
        if (isExternal(e)) return e;
        int cmp = k.compareTo(e.getKey());
        if (cmp < 0) return treeSearch(k, e.getLeftChild());
        else if (cmp == 0) return e;
        else return treeSearch(k, e.getRightChild());
    }

    public void insert(K k, E e) {
        RBNode<K, E> search = treeSearch(k, root);
        if (isExternal(search)) {
            add(search, k, e);
            rebalancedInsert(search);
        }
        else updateNode(search, e);
    }

    public void rebalancedInsert(RBNode e) {
        if (!isRoot(e)) {
            makeRed(e);
            resolveRed(e);
        }
    }

    private void resolveRed(RBNode e) {
        RBNode parent, grand, uncle, middle;
        parent = e.getParent();
        if (isRed(parent)) {
            uncle = parent.getSibling();
            if (isBlack(uncle)) {
                middle = restucture(e);
                makeBlack(middle);
                makeRed(middle.getLeftChild());
                makeRed(middle.getRightChild());
            }
            else {
                makeBlack(parent);
                makeBlack(uncle);
                grand = parent.getParent();
                if (!isRoot(grand)) {
                    makeRed(grand);
                    resolveRed(grand);
                }
            }
        }

    }

    private RBNode<K, E> restucture(RBNode<K, E> x) {
//        return a node so that the class can continue up the tree
        RBNode<K, E> y = x.getParent();
        RBNode<K, E> z = y.getParent();
        if ((x == y.getRightChild()) == (y == z.getRightChild())) {
            rotate(y);
            return y;
        }
        else {
            rotate(x);
            rotate(x);
            return x;
        }
    }

    private void rotate(RBNode<K, E> e) {
        RBNode<K, E> x = e;
        RBNode<K, E> y = e.getParent();
        RBNode<K, E> z = y.getParent();

        if (z == null) {
            root = x;
            x.setParent(null);
        }
        else relink(z, x, y == z.getLeftChild());

        if (x == y.getLeftChild()) {
            relink(y, x.getRightChild(), true);
            relink(x, y, false);
        }
        else {
            relink(y, x.getLeftChild(), false);
            relink(x, y, true);
        }
    }

    private void relink(RBNode<K, E> parent, RBNode<K, E> child, boolean makeLeftChild) {
        child.setParent(parent);
        if (makeLeftChild) parent.setLeftChild(child);
        else parent.setRightChild(child);
    }

    private void add(RBNode<K, E> old, K k, E e) {
        old.setKey(k);
        old.setElement(e);
        old.setLeftChild(new RBNode<>(null, null, old, 0));
        old.setRightChild(new RBNode<>(null, null, old, 0));
    }

//    Just updating a node if the key already exist, won't change the Red/Black nature of the tree
    private void updateNode(RBNode<K, E> old, E e) {
        old.setElement(e);
    }

    public void delete(K k) {
//        search for node with Key k
        RBNode<K, E> e = treeSearch(k, getRoot());
//        If node doesn't exist, return without continuing
        if (isExternal(e)) {
            return;
        }
        else {
//            If the node to delete has two children, replace internal node with an external node
//            This is done by finding the maximum on the node's left side
            if (isInternal(e.getRightChild()) && isInternal(e.getLeftChild())) {
                RBNode<K, E> replacement = findMax(e.getLeftChild());
                set(e, replacement);
                e = replacement;
            }
        }
        RBNode<K, E> leaf = (isExternal(e.getLeftChild()) ? e.getLeftChild() : e.getRightChild());
        RBNode<K, E> sib = leaf.getSibling();
        remove(e,sib);
        rebalanceDelete(sib);
    }

    private void remove(RBNode<K, E> oldNode, RBNode<K, E> newNode) {
        RBNode<K, E> parent = oldNode.getParent();
        newNode.setParent(parent);
        if (parent.getRightChild() == oldNode)
            parent.setRightChild(newNode);
        else
            parent.setLeftChild(newNode);
    }

    private void rebalanceDelete(RBNode e) {
        if (isRed(e)) {
            makeBlack(e);
        }
        else if (!isRoot(e)) {
            RBNode sib = e.getSibling();
            if (isInternal(sib) && (isBlack(sib) || isInternal(sib.getLeftChild()))) {
                remedyDoubleBlack(e);
            }
        }
    }

    private void remedyDoubleBlack(RBNode e) {
        RBNode z = e.getParent();
        RBNode y = e.getSibling();
        if (isBlack(y)) {
            if (isRed(y.getLeftChild()) || isRed(y.getRightChild())) {
                RBNode x = (isRed(y.getLeftChild()) ? y.getLeftChild() : y.getRightChild());
                RBNode middle = restucture(x);
                setColor(middle, isRed(z));
                makeBlack(middle.getLeftChild());
                makeBlack(middle.getRightChild());
            }
            else {
                makeRed(y);
                if (isRed(z)) {
                    makeBlack(z);
                }
                else if (!isRoot(z)) {
                    remedyDoubleBlack(z);
                }
            }
        }
        else {
            rotate(y);
            makeBlack(y);
            makeRed(z);
            remedyDoubleBlack(e);
        }
    }

    private RBNode<K, E> findMax(RBNode<K, E> start) {
        if (isInternal(start.getRightChild())) return findMax(start.getRightChild());
        else return start;
    }

    public void set(RBNode<K, E> oldNode, RBNode<K, E> newNode) {
        oldNode.setElement(newNode.getElement());
        oldNode.setKey(newNode.getKey());
        newNode.setAux(oldNode.getAux());
    }

    public static void main(String[] args) {
        RedBlackTree test = new RedBlackTree(14, "14");
        test.insert(7, "7");
        test.insert(16, "16");
        test.insert(4, "4");
        test.insert(12, "12");
        test.insert(15, "15");
        test.insert(18, "18");
        test.insert(3, "3");
        test.insert(5, "5");
        test.insert(17, "17");
        test.inOrder(test.root);
        System.out.println();
        test.preOrder(test.root);
        System.out.println();

        test.delete(3);
        test.delete(12);
        test.delete(17);
        test.delete(18);
        test.delete(15);
        test.delete(16);

        test.inOrder(test.root);
        System.out.println();
        test.preOrder(test.root);
        System.out.println();
        System.out.println(test.getRoot().getElement());
    }
}
