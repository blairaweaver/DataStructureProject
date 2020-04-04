import java.util.Random;

public class Unique_Integer {
    public static void main(String[] args){
//        Variables to hold time values
        long startTime, finalTime, overallTime;
//        All of the Arrays used for testing
        int[] tenA = new int[10];
        int[] tenB = new int[10];
        int[] hundredA = new int[100];
        int[] hundredB = new int[100];
        int[] thousandA = new int[1000];
        int[] thousandB = new int[1000];
        int[] tenthousandA = new int[10000];
        int[] tenthousandB = new int[10000];
        int n, m;
//        Psuedo-randonm number generator and seed set to current time
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());

//        Fill the Arrays with Random Numbers
        fillArray(tenA, rand);
        fillArray(tenB, rand);
        fillArray(hundredA, rand);
        fillArray(hundredB, rand);
        fillArray(thousandA, rand);
        fillArray(thousandB, rand);
        fillArray(tenthousandA, rand);
        fillArray(tenthousandB, rand);

        startTime = System.nanoTime();
        n = findDuplicates(tenB);
        m = uniqueNumber(tenA, tenB, n);
        finalTime = System.nanoTime();
        System.out.println("There are " + m + " unigue numbers for the second Arrays with 10");
        overallTime = finalTime - startTime;
        System.out.println("Time for Arrays with 10 numbers in nanoseconds: " + overallTime);

        startTime = System.nanoTime();
        n = findDuplicates(hundredB);
        m = uniqueNumber(hundredA, hundredB, n);
        finalTime = System.nanoTime();
        System.out.println("There are " + m + " unigue numbers for the second Arrays with 100");
        overallTime = finalTime - startTime;
        System.out.println("Time for Arrays with 100 numbers in nanoseconds: " + overallTime);

        startTime = System.nanoTime();
        n = findDuplicates(thousandB);
        m = uniqueNumber(thousandA, thousandB, n);
        finalTime = System.nanoTime();
        System.out.println("There are " + m + " unigue numbers for the second Arrays with 1000");
        overallTime = finalTime - startTime;
        System.out.println("Time for Arrays with 1000 numbers in nanoseconds: " + overallTime);

        startTime = System.nanoTime();
        n = findDuplicates(tenthousandB);
        m = uniqueNumber(tenthousandA, tenthousandB, n);
        finalTime = System.nanoTime();
        System.out.println("There are " + m + " unigue numbers for the second Arrays with 10000");
        overallTime = finalTime - startTime;
        System.out.println("Time for Arrays with 10000 numbers in nanoseconds: " + overallTime);
    }

    //    Method to fill array with Random numbers
    public static void fillArray(int[] x, Random r){
        for(int i = 0; i < x.length; i++){
            x[i] = r.nextInt(x.length);
        }
    }

//  Method to find duplicates in the second Array
    public static int findDuplicates(int[] x){
        Boolean match = false;
        int n = x.length;
        for (int i = 0; i < n-1; i++){
            for (int j = i+1; j < n; j++){
                if (x[i] == x[j]){
                    deleteElement(x, j, n);
                    match = true;
                    j--;
                    n--;
                }
            }
            if (match == true){
                deleteElement(x,i,n);
                i--;
                n--;
                match = false;
            }

        }
        return n;
    }

//    Method to find numbers that are unique to the second Array
    public static int uniqueNumber(int[] y, int[] x, int n){
        boolean match = false;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < y.length; j++){
                if (x[i] == y[j]){
                    match = true;
                    break;
                }
            }
            if (match == true){
                deleteElement(x,i,n);
                i--;
                n--;
                match=false;
            }
        }
        return n;
    }

//    Method to remove unwanted elements from the array
    public static void deleteElement(int[] x, int j, int n){
        for (int i = j; i < n - 1; i++){
            x[i] = x[i+1];
        }
    }
}
