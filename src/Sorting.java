import java.lang.reflect.Array;
import java.util.Comparator;

public class Sorting {

    public static <T extends Comparable<T>> T[] takeFirstElements(T[] arr, int n) throws Exception {
        return takeFirstElements(n, Comparable::compareTo, arr);
    }

    public static<T> T[] takeFirstElements(int n, Comparator<T> c, T... arr) throws Exception {
        heapSort(arr, c);
        T[] result = (T[]) Array.newInstance(arr.getClass().getComponentType(), n);
        if (n > 0) System.arraycopy(arr, 0, result, 0, n);
        else throw new Exception("N must be more than null");
        return result;
    }

    public static <T extends Comparable<T>> void heapSort(T[] arr) { heapSort(arr, Comparable::compareTo); }

    public static <T> void heapSort(T[] arr, Comparator<T> c){
        for (int i = arr.length / 2; i >= 0; i--) {
            changeArr(arr, i, arr.length, c);
        }

        int size = arr.length;
        while (size > 1) {
            T element = arr[size - 1];
            arr[size - 1] = arr[0];
            arr[0] = element;
            size--;
            changeArr(arr, 0, size, c);
        }
    }

    private static <T> void changeArr(T[] arr, int i, int size, Comparator<T> c) {
        T element = arr[i];
        while (true) {
            int child = 2 * i + 1;
            if (child >= size) break;
            if (child + 1 < size && c.compare(arr[child + 1], arr[child]) > 0) child++;
            if (c.compare(element, arr[child]) > 0) break;
            arr[i] = arr[child];
            i = child;
        }
        arr[i] = element;
    }
}
