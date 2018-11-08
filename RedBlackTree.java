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
    public boolean isInternal(RBNode<K, E> e) {
        return e.getElement() != null;
    }

    public boolean isRoot(RBNode<K, E> e) {
        return root == e;
    }

    public void inOrder(RBNode<K, E> e) {
        if (e.getLeftChild() != null) inOrder(e.getLeftChild());
        if (e.getElement() != null) System.out.print(e.getElement());
        if (e.getRightChild() != null) inOrder(e.getRightChild());
    }

    public void preOrder(RBNode<K, E> e) {
        if (e.getElement() != null) System.out.print(e.getElement());
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
//            Need to do checks here to make sure tree satisfies the rules
        }
        else updateNode(search, e);
    }

    private void add(RBNode<K, E> old, K k, E e) {
        old.setKey(k);
        old.setElement(e);
        old.setAux(1);
        old.setLeftChild(new RBNode<>(null, null, old, 0));
        old.setRightChild(new RBNode<>(null, null, old, 0));
    }

//    Just updating a node if the key already exist, won't change the Red/Black nature of the tree
    private void updateNode(RBNode<K, E> old, E e) {
        old.setElement(e);
    }
}
