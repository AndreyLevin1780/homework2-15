import Impl.IntegerListImpl;

import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {



        IntegerListImpl testList = new IntegerListImpl(7);

        testList.add(0,1);
        testList.add(1,2);
        testList.add(2,3);
        testList.add(3,4);
        testList.add(4,5);

        testList.remove(0);
        System.out.println(testList.get(0));
        testList.set(0,23);
        System.out.println(testList.get(0));
        System.out.println(testList.size());
        System.out.println(testList.contains(234));
        System.out.println(testList.indexOf(4));
        System.out.println(Arrays.toString(testList.toArray()));

    }
}