package Part1;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class Experiment1 {
    public static void main(String[] args) {
        Random random = new Random();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array size: ");
        int arraySize = scanner.nextInt();

        int[] arr = new int[arraySize];
        int i;
        for ( i = 0; i < arraySize; i++)
            arr[i] = Math.abs(random.nextInt(10000));
        printArray(arr);
        System.out.println("\nChoose Kth smallest element: ");
        int kElement = scanner.nextInt();
        long kStart = System.nanoTime();
        System.out.println("---------Naive----------");
        System.out.println("Kth smallest element is: " + kthSmallest(arr,kElement));
        long kEnd = System.nanoTime();
        long kTime = kEnd - kStart;
        System.out.println("Time taken is "+ kTime + " ns" );

        System.out.println("\n---------MOM----------");
        long start= System.nanoTime();
        int median=Median(arr);
        long end = System.nanoTime();
        long time = end- start;
        System.out.println("Median = "+ median);
        System.out.println("Time elapsed = "+ time+" ns");

        System.out.println("\n---------RandomizedS----------");

        long rStart = System.nanoTime();
        System.out.println("Median is: " + randomized_select(arr, 0, i - 1, (arraySize/2)+1));
        long rEnd = System.nanoTime();
        long rTime = rEnd - rStart;
        System.out.println("Time taken is "+ rTime + " ns" );

    }

    private static int Median(int[] arr){
       int median= findMedian(arr,(arr.length)/2+1,0,arr.length-1);
        return(median);
    }

    private static int arrpartition(int[] arr,int low,int high){
        int pivot=getPivot(arr,low,high);
        while(low < high){
            while(arr[low] < pivot)
                low++;
            while(arr[high]>pivot)
                high--;

            if(arr[low] == arr[high])
                low++;
                //swapping
            else if(low < high){
                int temp = arr[low];
                arr[low] = arr[high];
                arr[high] = temp;
            }
        }
        return high;
    }


    private static int findMedian(int[] arr,int k,int low, int high){
        int p = arrpartition(arr,low,high);

        int length = p-low+1;

        if(length == k){
            return arr[p];
        }
        if(length>k)
            return findMedian(arr,k,low,p-1);
        else
            return findMedian(arr,k-length,p+1,high);
    }


    private static int getPivot(int[] arr,int low,int high){
        if(high-low+1<=9){
           // Arrays.sort(arr);
            quickSort(arr,0,arr.length-1);
            return arr[arr.length/2];
        }
        int[] t=null;
        int med[] = new int[(int) Math.ceil((double) (high-low+1)/5)];
        int medIndex =0;
        while (high>= low){
            t = new int[Math.min(5,high-low+1)];

            for(int i = 0; i<t.length&& low<=high;i++){
                t[i] = arr[low];
                low++;
            }
            //Arrays.sort(t);
            quickSort(t,0,t.length-1);
            med[medIndex] = t[t.length/2];
            medIndex++;
        }
        return getPivot(med,0,med.length-1);
    }



//            quickSort(arr,0,(arr.length-1));

        static void swap(int[] arr, int i, int j)
        {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        static int partition(int[] arr, int low, int high)
        {


            int pivot = arr[high];

            int i = (low - 1);

            for(int j = low; j <= high - 1; j++)
            {


                if (arr[j] < pivot)
                {

                    i++;
                    swap(arr, i, j);
                }
            }
            swap(arr, i + 1, high);
            return (i + 1);
        }


        static void quickSort(int[] arr, int low, int high)
        {
            if (low < high)
            {

                int pi = partition(arr, low, high);
                quickSort(arr, low, pi - 1);
                quickSort(arr, pi + 1, high);
            }
        }

    private static void printArray(int[] arr) {
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public static int kthSmallest(int[] arr,
                                  int k)
    {
        quickSort(arr,0,(arr.length-1));
        return arr[k - 1];
    }


//    private static int partition(int[] A, int p, int r)
//    {
//        int temp;
//        int x = A[r];
//        int i = p - 1;
//        for(int j = p; j < r; j++)
//        {
//            if(A[j] < x)
//            {
//                i = i + 1;
//                temp = A[i];
//                A[i] = A[j];
//                A[j] = temp;
//            }
//        }
//        temp = A[i + 1];
//        A[i + 1] = A[r];
//        A[r] = temp;
//        return i + 1;
//    }

    private static int randomized_partition(int[] A, int p, int r)
    {
        Random rand = new Random();
        int i = rand.nextInt(r - p) + p;
        int temp = A[r];
        A[r] = A[i];
        A[i] = temp;
        return partition(A, p, r);
    }

    private static int randomized_select(int[] A, int p, int r, int i)
    {
        if(p == r)
            return A[p];
        int q = randomized_partition(A, p, r);
        int k = q - p + 1;
        if (i == k) // the pivot value is the answer
            return A[q];
        else if(i < k)
            return randomized_select(A, p, q - 1, i);
        else
            return randomized_select(A, q + 1, r, i - k);
    }


}