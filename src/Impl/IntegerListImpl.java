package Impl;

import Exception.IncorrectCapacityException;
import Exception.IncorrectIndexException;
import Exception.ItemIsNullException;
import Interface.IntegerList;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

public class IntegerListImpl implements IntegerList {

    private final static int defaultCapacity = 10;

    private Integer[] storage;
    private int capacity;

    public IntegerListImpl() {
        storage = new Integer[defaultCapacity];
        capacity = 0;
    }

    public IntegerListImpl(int capacity) {
        if (capacity <= 0) {
            throw new IncorrectCapacityException("Параметр capacity должен быть больше 0");
        }
        storage = new Integer[capacity];
        capacity = 0;
    }

    private void grow() {
        storage = Arrays.copyOf(storage, storage.length * 3 / 2);
    }

    @Override
    public Integer add(Integer item) {
        return add(capacity, item);
    }

    @Override
    public Integer add(int index, Integer item) {
        checkIsNull(item);
        if (capacity == storage.length) {
            grow();
        }
        checkIndex(index, false);
        if (index < capacity) {
            System.arraycopy(storage, index, storage, index + 1, capacity - index);
        }
        storage[index] = item;
        capacity++;
        return item;
    }

    @Override
    public Integer set(int index, Integer item) {
        checkIsNull(item);
        checkIndex(index, true);
        return storage[index] = item;
    }

    @Override
    public Integer remove(Integer item) {
        return remove(indexOf(item));
    }

    @Override
    public Integer remove(int index) {
        checkIndex(index, true);
        if (index < capacity - 1) {
            System.arraycopy(storage, index + 1, storage, index, capacity - index - 1);
        }
        Integer removed = storage[index];
        storage[capacity - 1] = null;
        capacity--;
        return removed;
    }

    @Override
    public boolean contains(Integer item) {
        checkIsNull(item);
        /*for (int i = 0; i < capacity; i++) {
            if (storage[i].equals(item)) {
                return true;
            }
        }
        return false;*/
        Integer[] copy = toArray();
        quickSort(copy, 0, copy.length -1);
        return contains(copy, item);
    }

    @Override
    public int indexOf(Integer item) {
        checkIsNull(item);
        for (int i = 0; i < capacity; i++) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        checkIsNull(item);
        for (int i = capacity - 1; i >= 0; i--) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        checkIndex(index, true);
        return storage[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
        if (size() != otherList.size()) {
            return false;
        }
        for (int i = 0; i < capacity; i++) {
            if (!storage[i].equals(otherList.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return capacity;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            storage[i] = null;
        }
        capacity = 0;
    }

    @Override
    public Integer[] toArray() {
        return Arrays.copyOf(storage, capacity);
    }

    private void checkIsNull(Integer item) {
        if (Objects.isNull(item)) {
            throw new ItemIsNullException();
        }
    }

    private void checkIndex(int index, boolean include) {

        if (index < 0 || include ? index >= capacity : index > capacity) {
            throw new IncorrectIndexException();
        }
    }

    /*public static void sortInsertion(Integer[] arr) {
        for (int i = 1; i < arr.length; i++) {
            Integer temp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1].compareTo(temp) >= 0) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = temp;
        }
    }*/

    private static void quickSort(Integer[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private static int partition(Integer[] arr, int begin, int end) {
        Integer pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j].compareTo(pivot) <= 0) {
                i++;

                swapElements(arr, i, j);
            }
        }

        swapElements(arr, i + 1, end);
        return i + 1;
    }

    private static void swapElements(Integer[] arr, int left, int right) {
        Integer temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    public static boolean contains(Integer[] arr, Integer element) {
        int min = 0;
        int max = arr.length - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (element.equals(arr[mid])) {
                return true;
            }

            if (element.compareTo(arr[mid]) < 0) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }

}
