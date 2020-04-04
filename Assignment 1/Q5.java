public class recursiveSumFind {
    public static void main(String[] args){
        int[] testA = new int[190];
        for (int i = 0; i < testA.length; i++){
            testA[i] = i;
        }
        recurvieSum(testA, 200, 0, 1);
    }

    public static void recurvieSum(int[] x, int k, int index1, int index2){
        if (index1 == x.length - 1 || x.length == 1)return;
        if (x[index1] + x[index2] == k) {
//            System.out.println("Index " + index1 + " and " + index2);
            System.out.println( x[index1] + " and " + x[index2] + " equals " + k);
        }
        else if (index2 + 1 == x.length ){
            recurvieSum(x, k, index1 + 1, index1 + 1);
        }
        else {
            recurvieSum(x, k, index1, index2 + 1);
        }
    }
}
