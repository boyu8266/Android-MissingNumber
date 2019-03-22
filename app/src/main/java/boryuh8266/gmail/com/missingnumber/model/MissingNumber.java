package boryuh8266.gmail.com.missingnumber.model;

import java.util.Collections;
import java.util.LinkedList;

public class MissingNumber {

    private int missingNumber;
    private int[] qArray;

    public MissingNumber(int max) {
        setNewMissingNumber(max);
    }

    public void setNewMissingNumber(int max) {
        missingNumber = (int) (Math.random() * max + 1);
        LinkedList<Integer> initArray = new LinkedList<Integer>();
        for (int i = 0; i < max; i++) {
            if ((i + 1) == missingNumber) continue;
            initArray.add(i + 1);
        }
        Collections.shuffle(initArray);

        qArray = new int[max - 1];
        for (int i = 0; i < qArray.length; i++) {
            qArray[i] = initArray.get(i).intValue();
        }
    }

    public int[] getQArray() {
        return qArray;
    }

    public int getMissingNumber() {
        return missingNumber;
    }

}
