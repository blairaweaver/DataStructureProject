//Deleted all code that pertains to the implementation of Adj List

//To Do:
//Add methods: Dijkstra's
//Done: depth traversal, breadth traversal, Kruskal's, Prim's,

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import static java.util.Arrays.fill;

public class Assignment4 {
    
    private class EdgeNode implements Comparable<EdgeNode> {
        private int origin;
        private int dest;
        private int weight;
        private EdgeNode previous;
        private EdgeNode next;

        public EdgeNode() {
            origin = -1;
            dest = -1;
            previous = null;
            next = null;
        }

        public EdgeNode(int origin, int dest, int weight) {
            this.origin = origin;
            this.dest = dest;
            this.previous = null;
            this.next = null;
            this.weight = weight;
        }

        public int getOrigin() {
            return origin;
        }

        public int getDest() {
            return dest;
        }

        public int getWeight() {
            return weight;
        }

        //        public EdgeNode getPrevious() {
//            return previous;
//        }
//
//        public EdgeNode getNext() {
//            return next;
//        }

        public void setOrigin(int origin) {
            this.origin = origin;
        }

        public void setDest(int dest) {
            this.dest = dest;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        @Override
        public int compareTo(EdgeNode edge) {
            if (this.getWeight() > edge.getWeight()) {
                return 1;
            }
            else if (this.getWeight() < edge.getWeight()) {
                return -1;
            }
            else {
                return 0;
            }
        }

//        public void setPrevious(EdgeNode previous) {
//            this.previous = previous;
//        }
//
//        public void setNext(EdgeNode next) {
//            this.next = next;
//        }
    }

    private int[][] adjMatrix;
//    -1 for grey, 0 for red, 1 for black
    private int[] color;
    private int[] visit;
    private int verticies = 0;
    private int edges = 0;

    public Assignment4(int verticies) {
        this.adjMatrix = new int[verticies][verticies];
        this.color = new int[verticies];
        this.visit = new int[verticies];
    }

    public int numVertices() {
        return verticies;
    }

    private void setColor(int vert, int color) {
        this.color[vert] = color;
    }

    private void clearColor() {
        for (int i = 0; i < color.length; i++) {
            setColor(i, -1);
        }
    }

    private void clearVisit() {
        for (int i = 0; i < visit.length; i++) {
            visit[i] = -1;
        }
    }

    private void visited(int vert) {
        int i = 0;
        while (visit[i] != -1) {
            i++;
        }
        visit[i] = vert;
    }

    public int[] getVisit() {
        return visit;
    }

    public int numEdges() {
        return edges;
    }

    public int outDegree(int vert) {
        vert--;
        int sum = 0;
        for (int i = 0; i < this.adjMatrix[vert].length; i++) {
            if (this.adjMatrix[vert][i] != -1) {
                sum += this.adjMatrix[vert][i];
            }
        }
        return sum;
    }

    public boolean areAdjacent(int vert1, int vert2) {
        return this.adjMatrix[--vert1][--vert2] == 1;
    }

    public void insertVertex() {
        int oldSize = adjMatrix[0].length;
        int[][] tempMatrix = new int[oldSize + 1][oldSize + 1];
        this.color = new int[oldSize + 1];
        this.visit = new int[oldSize + 1];

        for (int i = 0; i < adjMatrix.length; i++) {
            tempMatrix[i][i] = adjMatrix[i][i];

        }
        verticies++;
    }

    public void insertEdge(int vert1, int vert2, boolean d) {
        vert1--;
        vert2--;
        adjMatrix[vert1][vert2] = 1;
//        if the graph is directed, d = true, don't add
//        if the graph is undirected, d = false, add
        if (d == false) {
            adjMatrix[vert2][vert1] = 1;
        }
        edges++;
    }

    public void insertEdge(int vert1, int vert2, int weight) {
//        Insert function for weighted graph
        vert1--;
        vert2--;
        adjMatrix[vert1][vert2] = weight;
        adjMatrix[vert2][vert1] = weight;
        edges++;
    }

    public void removeEdge(int vert1, int vert2) {
        vert1--;
        vert2--;
        adjMatrix[vert1][vert2] = 0;
        adjMatrix[vert2][vert1] = 0;
        edges--;
    }

    public void removeVertex(int vert) {
        vert--;
        for (int i = 0; i < adjMatrix.length; i++) {
            adjMatrix[vert][i] = -1;
            adjMatrix[i][vert] = -1;
        }
        verticies--;
    }

    public void print(){
        for (int i = 0; i < adjMatrix.length; i++) {
            int vert1 = i + 1;
            System.out.print(vert1 + ": ");
            for (int j = 0; j < adjMatrix.length; j++) {
                if (adjMatrix[i][j] == 1) {
                    int vert2 = j + 1;
                    System.out.print(vert2 + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printVisited() {
        for (int i = 0; i < visit.length; i++) {
            System.out.print(visit[i] + " ");
        }
        System.out.println();
    }

    public void printSpan(LinkedList<EdgeNode> span) {
        for (int i = 0; i < span.size(); i++){
            EdgeNode current = span.get(i);
            if (i == span.size() - 1) {
                System.out.println((char)(current.getOrigin() + 65) + "-" + (char)(current.getDest()+65));
            }
            else {
                System.out.print((char)(current.getOrigin()+65) + "-" + (char)(current.getDest()+65) + ", ");
            }
        }
    }

    public void DFS(int vert) {
        clearColor();
        clearVisit();
        DFSvisit(vert);
    }

    private void DFSvisit(int vert) {
        setColor(vert-1, 0);
        visited(vert);
        for (int i = 0; i < this.adjMatrix[vert-1].length; i++) {
            if (areAdjacent(vert, i+1) && this.color[i] == -1) {
                DFSvisit(i+1);
            }
        }
        setColor(vert-1, 1);
    }

    public void BFS(int vert) {
        clearColor();
        clearVisit();
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(vert);
        visited(vert);
        while (!queue.isEmpty()) {
            int current = queue.removeFirst();
            for (int i = 0; i < adjMatrix[current-1].length; i++) {
                if (areAdjacent(current, i+1) && color[i] == -1) {
                    setColor(i,0);
                    visited(i+1);
                    queue.add(i + 1);
                }
            }
            setColor(current-1, 1);
        }
    }

    public LinkedList<EdgeNode> Prim(int vert) {
//        Will use visit array to keep track of the vertices that are currently in the tree
//        -1 for not visited, 1 for visited
        clearVisit();
//        span stores the edges that are in the tree
        PriorityQueue<EdgeNode> queue = new PriorityQueue<>();
        LinkedList<EdgeNode> span = new LinkedList<>();

//        Add edges connected to vert given
        for (int i = 0; i < adjMatrix[vert].length; i++) {
            if (adjMatrix[vert][i] != 0) {
                queue.add(new EdgeNode(vert, i, adjMatrix[vert][i]));
            }
        }

        while (!queue.isEmpty()) {
            EdgeNode current = queue.poll();
            if (visit[current.getOrigin()] == -1 || visit[current.getDest()] == -1) {
                span.add(current);
                visit[current.getOrigin()] = 1;
                visit[current.getDest()] = 1;
                for (int i = 0; i < adjMatrix[current.getDest()].length; i++) {
                    if (adjMatrix[current.getDest()][i] != 0 && visit[i] != 1) {
                        queue.add(new EdgeNode(current.getDest(), i, adjMatrix[current.getDest()][i]));
                    }
                }
            }
        }
        return span;
    }

    public LinkedList<EdgeNode> Kruskal() {
//        span stores the edges that are in the tree
        PriorityQueue<EdgeNode> queue = new PriorityQueue<>();
        LinkedList<EdgeNode> span = new LinkedList<>();

//        keep track of clusters (subtrees)
        ArrayList<ArrayList<Integer>> clusters = new ArrayList<>(adjMatrix.length);

//        Creating and adding EdgeNodes to queue
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = i; j < adjMatrix.length; j++) {
                if (adjMatrix[i][j] != 0) {
                    queue.add(new EdgeNode(i, j, adjMatrix[i][j]));
                }
            }
        }

        while (!queue.isEmpty()) {
            EdgeNode current = queue.poll();
//            checks if this edge should be added
            if (toAdd(current, clusters)) {
//                adds edge to the span and then adjust the subtrees
                span.add(current);
                adjustSubtrees(current, clusters);
            }
        }
        return span;
    }

    private boolean toAdd(EdgeNode x, ArrayList<ArrayList<Integer>> subTrees) {
        int originIndex = -1, destIndex = -1;
        for (int i =0; i < subTrees.size(); i++) {
            if (subTrees.get(i).indexOf(x.getOrigin()) != -1) {
                originIndex = i;
            }
            if (subTrees.get(i).indexOf(x.getDest()) != -1) {
                destIndex = i;
            }
        }

        if (originIndex == destIndex && originIndex != -1) {
            return false;
        }
        else {
            return true;
        }
    }

    private void adjustSubtrees(EdgeNode x, ArrayList<ArrayList<Integer>> subTrees) {
        int originIndex = -1, destIndex = -1;
        for (int i =0; i < subTrees.size(); i++) {
            if (subTrees.get(i).indexOf(x.getOrigin()) != -1) {
                originIndex = i;
            }
            if (subTrees.get(i).indexOf(x.getDest()) != -1) {
                destIndex = i;
            }
        }

        if (originIndex == -1 && destIndex == -1) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(x.getOrigin());
            temp.add(x.getDest());
            subTrees.add(temp);
        }
        else if (originIndex != -1 && destIndex != -1) {
            if (originIndex < destIndex) {
                for (int i = 0; i < subTrees.get(destIndex).size(); i++) {
                    subTrees.get(originIndex).add(subTrees.get(destIndex).get(i));
                }
                subTrees.remove(destIndex);
            }
            else {
                for (int i = 0; i < subTrees.get(originIndex).size(); i++) {
                    subTrees.get(destIndex).add(subTrees.get(originIndex).get(i));
                }
                subTrees.remove(originIndex);
            }
        }
        else if (originIndex == -1) {
            subTrees.get(destIndex).add(x.getOrigin());
        }
        else {
            subTrees.get(originIndex).add(x.getDest());
        }
    }

    public int[] Dijkstra(int vert) {
//        use visited to keep track of path, dist to keep track of distances from source and left to keep track of if the vert is in the path
        clearVisit();
        int[] dist = new int[adjMatrix.length];
        fill(dist, Integer.MAX_VALUE);
        Boolean[] left = new Boolean[adjMatrix.length];
        fill(left, false);

        dist[vert] = 0;
        
        for (int count = 0; count < adjMatrix.length; count++) {
            int minIndex = minDist(dist, left);

            left[minIndex] = true;
            visited(minIndex);
            updateDist(minIndex, dist);
        }
        return dist;
    }
    
    private int minDist(int[] dist, Boolean[] left) {
        int min = Integer.MAX_VALUE, minIndex = -1;
        for (int i = 0; i < adjMatrix.length; i++) {
            if (left[i] == false && dist[i] <= min) {
                min = dist[i];
                minIndex = i;
            }
        }
        return minIndex;
    }
    
    private void updateDist(int vert, int[] dist) {
        for (int i = 0; i < adjMatrix[vert].length; i++) {
            if (adjMatrix[vert][i] != 0 && dist[vert] + adjMatrix[vert][i] < dist[i]) {
                dist[i] = dist[vert] + + adjMatrix[vert][i];
            }
        }
    }

    public void printDist(int[] dist) {
        System.out.println("Vertex      Distance from Source");
        for (int i = 0; i < adjMatrix.length; i++) {
            System.out.println((char)(visit[i] + 65) + " \t\t\t " + dist[visit[i]]);
        }
    }

    public static void main(String[] args) {
//        Generating Adj Matrix for Question 1
        Assignment4 Q1 = new Assignment4(8);
        Q1.insertEdge(1,2,true);
        Q1.insertEdge(1,4,true);
        Q1.insertEdge(2,3,true);
        Q1.insertEdge(2,5,true);
        Q1.insertEdge(3,2,true);
        Q1.insertEdge(3,4,true);
        Q1.insertEdge(3,5,true);
        Q1.insertEdge(4,1,true);
        Q1.insertEdge(4,3,true);
        Q1.insertEdge(5,2,true);
        Q1.insertEdge(5,3,true);
        Q1.insertEdge(5,6,true);
        Q1.insertEdge(6,5,true);
        Q1.insertEdge(6,7,true);
        Q1.insertEdge(6,8,true);
        Q1.insertEdge(7,6,true);
        Q1.insertEdge(7,8,true);
        Q1.insertEdge(8,6,true);
        Q1.insertEdge(8,7,true);

        Q1.print();

        System.out.println("Depth First Travesal");

        Q1.DFS(1);

        Q1.printVisited();

        System.out.println("Breadth First Travesal");

        Q1.BFS(1);

        Q1.printVisited();

//        Generating Adj matrix for Q2 where the value stored is the weight
        Assignment4 Q2 = new Assignment4(9);
        Q2.insertEdge(1,2,22);
        Q2.insertEdge(1,3,9);
        Q2.insertEdge(1,4,12);
        Q2.insertEdge(2,3,35);
        Q2.insertEdge(2,6,36);
        Q2.insertEdge(2,8,34);
        Q2.insertEdge(3,6,42);
        Q2.insertEdge(3,5,65);
        Q2.insertEdge(3,4,4);
        Q2.insertEdge(4,5,33);
        Q2.insertEdge(4,9,30);
        Q2.insertEdge(5,6,18);
        Q2.insertEdge(5,7,23);
        Q2.insertEdge(6,7,39);
        Q2.insertEdge(6,8,24);
        Q2.insertEdge(7,8,25);
        Q2.insertEdge(7,9,21);
        Q2.insertEdge(8,9,19);

        System.out.println("Prim's Algorithm");

        Q2.printSpan(Q2.Prim(5));

        System.out.println("Kruskal's Algorithm");

        Q2.printSpan(Q2.Kruskal());

//        Generating Adj matrix for Q4 where the value stored is the weight
        Assignment4 Q3 = new Assignment4(8);
        Q3.insertEdge(1,2,5);
        Q3.insertEdge(1,5,4);
        Q3.insertEdge(2,3,6);
        Q3.insertEdge(2,5,3);
        Q3.insertEdge(2,6,14);
        Q3.insertEdge(2,7,10);
        Q3.insertEdge(3,4,1);
        Q3.insertEdge(3,7,9);
        Q3.insertEdge(3,8,17);
        Q3.insertEdge(4,8,12);
        Q3.insertEdge(5,6,11);
        Q3.insertEdge(6,7,7);
        Q3.insertEdge(7,8,15);

        System.out.println("Dijkstra's Algorithm");

        Q3.printDist(Q3.Dijkstra(0));
    }
}
