package boryuh8266.gmail.com.missingnumber.model;

import android.util.Log;

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
            //  若為消失的數字則加入 -1
            if ((i + 1) == missingNumber){
                initArray.add(-1);
                continue;
            }
            initArray.add(i + 1);
        }
        Collections.shuffle(initArray);

        qArray = new int[max];
        for (int i = 0; i < qArray.length; i++) {
            qArray[i] = initArray.get(i).intValue();
        }
    }

    public int[] getQArray() {
        return qArray;
    }

    public boolean isRightAnswer(int answer) {
        return answer == missingNumber ? true : false;
    }

}
