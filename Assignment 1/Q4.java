import java.util.Random;

public class recursiveSort {
    public static void main(String[] args){
        int[] testA = new int[10];
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int i = 0; i < testA.length; i++){
            testA[i] = rand.nextInt(testA.length);
        }
        System.out.println("Presort");
        printArray(testA);

        recusiveSort(testA, 0, testA.length-1);

        System.out.println("Postsort");
        printArray(testA);
    }

    public static void printArray(int[] x){
        for (int i = 0; i < x.length; i++){
            System.out.print(x[i] + ", ");
        }
        System.out.println();
    }

    public static void recusiveSort(int[] x, int left, int right){
        if (x.length == 1 || left >= right) return;
        if (x[left]%2==1 && x[right]%2==0){
            int temp = x[right];
            x[right] = x[left];
            x[left] = temp;
        }
        else {
            if (x[left]%2==0)left++;
            if (x[right]%2==1)right--;
        }
        recusiveSort(x, left, right);
    }
}
