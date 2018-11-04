public class AVL<K extends Comparable<K>, E> {
    private static class AVLNode<K, E>{
        private E element;
        private K key;
        private int aux;
        private AVLNode<K, E> parent;
        private AVLNode<K, E> leftChild;
        private AVLNode<K, E> rightChild;
        public AVLNode(K k, E e, AVLNode<K, E> p) {
            this.element = e;
            this.key = k;
            this.parent = p;
            this.aux = 0;
            this.leftChild = null;
            this.rightChild = null;
        }

        public E getElement() {
            return element;
        }

        public K getKey() {
            return key;
        }

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

        public AVLNode<K, E> getParent() {
            return parent;
        }

        public void setParent(AVLNode<K, E> p){
            this.parent = p;
        }

        public AVLNode<K, E> getLeftChild() {
            return leftChild;
        }

        public AVLNode<K, E> getRightChild() {
            return rightChild;
        }

        public void setLeftChild(AVLNode<K, E> l) {
            this.leftChild = l;
        }

        public void setRightChild(AVLNode<K, E> r) {
            this.rightChild = r;
        }

        public AVLNode<K, E> getSibling(){
            AVLNode<K, E> parent = getParent();
            if (parent.rightChild == this) return parent.leftChild;
            else return parent.rightChild;
        }

    }

    private AVLNode<K, E> root;
    private int size = 0;
    private int height = 0;
    public AVL() {
        root = null;
    }
    public AVL(K k, E e){
        this.root = new AVLNode<>(k, e, null);
        root.setLeftChild(new AVLNode<>(null, null, root));
        root.setRightChild(new AVLNode<>(null, null, root));
        root.setAux(1);
        size++;
    }

    public AVLNode getRoot() {
        return this.root;
    }

    public int getHeight(AVLNode<K, E> e) {
        return e.getAux();
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean isExternal(AVLNode<K, E> e){
        return e.getElement() == null;
    }
    
    public boolean isInternal(AVLNode<K, E> e) {
        return e.getElement() != null;
    }

    public boolean isRoot(AVLNode<K, E> e) {
        return root == e;
    }

    public void inOrder(AVLNode<K, E> e) {
        if (e.getLeftChild() != null) inOrder(e.getLeftChild());
        if (e.getElement() != null) System.out.print(e.getElement());
        if (e.getRightChild() != null) inOrder(e.getRightChild());
    }

    public void preOrder(AVLNode<K, E> e) {
        if (e.getElement() != null) System.out.print(e.getElement());
        if (e.getLeftChild() != null) preOrder(e.getLeftChild());
        if (e.getRightChild() != null) preOrder(e.getRightChild());
    }

    public void postOrder(AVLNode<K, E> e) {
        if (e.getLeftChild() != null) postOrder(e.getLeftChild());
        if (e.getRightChild() != null) postOrder(e.getRightChild());
        if (e.getElement() != null) System.out.print(e.getElement());
    }
    
    public void set(AVLNode<K, E> oldNode, AVLNode<K, E> newNode) {
        oldNode.setElement(newNode.getElement());
        oldNode.setKey(newNode.getKey());
    } 

    public void recomputeHeight(AVLNode<K, E> e) {
        e.setAux(1 + Math.max(getHeight(e.getRightChild()), getHeight(e.getLeftChild())));
    }

    public boolean isBalanced(AVLNode<K, E> e) {
        return Math.abs(getHeight(e.getRightChild()) - getHeight(e.getLeftChild())) <= 1;
    }

    private void relink(AVLNode<K, E> parent, AVLNode<K, E> child, boolean makeLeftChild) {
        child.setParent(parent);
        if (makeLeftChild) parent.setLeftChild(child);
        else parent.setRightChild(child);
    }

    private void rotate(AVLNode<K, E> e) {
        AVLNode<K, E> x = e;
        AVLNode<K, E> y = e.getParent();
        AVLNode<K, E> z = y.getParent();

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

    private AVLNode<K, E> restucture(AVLNode<K, E> x) {
//        return a node so that the class can continue up the tree
        AVLNode<K, E> y = x.getParent();
        AVLNode<K, E> z = y.getParent();
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

    private void rebalance(AVLNode<K, E> e) {
        int oldHeight, newHeight;
        do {
            oldHeight = getHeight(e);
            if (!isBalanced(e)) {
//                find x since e is z and restructure
                e = restucture(tallerChild(tallerChild(e)));
                recomputeHeight(e.getLeftChild());
                recomputeHeight(e.getRightChild());
            }
            recomputeHeight(e);
            newHeight = getHeight(e);
            e = e.getParent();
        } while (oldHeight != newHeight && e != null);
    }

    private void rebalanceInsert(AVLNode<K, E> e) {
        rebalance(e);
    }

    private void rebalanceDelete(AVLNode<K, E> e) {
        if (!isRoot(e) && isInternal(e)) rebalance(e);
        if (isExternal(e) && !isRoot(e)) rebalance(e.getParent());
    }

    public AVLNode<K, E> tallerChild(AVLNode<K, E> e) {
        if (getHeight(e.getLeftChild()) < getHeight(e.getRightChild())) return e.getRightChild();
        if (getHeight(e.getLeftChild()) > getHeight(e.getRightChild())) return e.getLeftChild();
        if (isRoot(e)) return e.getLeftChild();
        if (e == e.getParent().getLeftChild()) return e.getLeftChild();
        else return e.getRightChild();
    }

    public AVLNode<K, E> treeSearch(K k, AVLNode<K, E> e) {
        if (isExternal(e)) return e;
        int cmp = k.compareTo(e.getKey());
        if (cmp < 0) return treeSearch(k, e.getLeftChild());
        else if (cmp == 0) return e;
        else return treeSearch(k, e.getRightChild());
    }

    public void insert(K k, E e) {
        AVLNode<K, E> search = treeSearch(k, root);
        if (isExternal(search)) {
            add(search, k, e);
            rebalanceInsert(search.parent);
        }
        else updateNode(search, e);
    }
    
    public void delete(K k) {
        AVLNode<K, E> e = treeSearch(k, getRoot());
        if (isExternal(e)) {
            return;
        }
        else {
            if (isInternal(e.getRightChild()) && isInternal(e.getLeftChild())) {
                AVLNode<K, E> replacement = findMax(e.getLeftChild());
                set(e, replacement);
                e = replacement;
            }
        }
        AVLNode<K, E> leaf = (isExternal(e.getLeftChild()) ? e.getLeftChild() : e.getRightChild());
        AVLNode<K, E> sib = leaf.getSibling();
        remove(e,sib);
        rebalanceDelete(sib);
    }

    private void remove(AVLNode<K, E> oldNode, AVLNode<K, E> newNode) {
        AVLNode<K, E> parent = oldNode.getParent();
        newNode.setParent(parent);
        if (parent.getRightChild() == oldNode)
            parent.setRightChild(newNode);
        else
            parent.setLeftChild(newNode);
    }

    private void add(AVLNode<K, E> old, K k, E e) {
        old.setKey(k);
        old.setElement(e);
        old.setAux(1);
        old.setLeftChild(new AVLNode<>(null, null, old));
        old.setRightChild(new AVLNode<>(null, null, old));
    }

    private void updateNode(AVLNode<K, E> old, E e) {
        old.setElement(e);
    }

    private AVLNode<K, E> findMax(AVLNode<K, E> start) {
        if (isInternal(start.getRightChild())) return findMax(start.getRightChild());
        else return start;
    }

    private AVLNode<K, E> findMin(AVLNode<K, E> start) {
        if (isInternal(start.getLeftChild())) return findMin(start.getLeftChild());
        else return start;
    }

    public static void main(String[] args){
        AVL test2 = new AVL(1, '1');
        test2.insert(3, '3');
        test2.insert(2, '2');
        test2.insert(8, '8');
        test2.insert(9, '9');
        test2.insert(6, '6');
        test2.insert(5, '5');
        test2.inOrder(test2.root);
        test2.delete(3);
        System.out.println();
        test2.inOrder(test2.root);
        test2.delete(8);
        System.out.println();
        test2.inOrder(test2.root);
    }
}
