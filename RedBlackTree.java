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
}
