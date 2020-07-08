public class Test {
    public static void main(String[] args) throws Exception {
        Integer[] arr = {5, 7, 8, 1, 4, 6, 7, 4};
        Sorting.heapSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
