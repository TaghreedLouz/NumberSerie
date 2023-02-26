package com.devtaghreed.numberseries;

public class num_processing {
    public static int Number;

    public static Question generateQuestion() {
        String[][] x = new String[3][3];
        int startNumber = (int) (Math.random() * 10) + 1;
        int incStartNumber = (int) (Math.random() * 5) + 1;
        int stredNumber;
        Number = -1;
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {
                stredNumber = startNumber + incStartNumber;

                if (i == 1 && j == 1) {
                    x[i][j] = "??";
                    Number = stredNumber;
                } else {
                    x[i][j] = stredNumber + "";
                }
                incStartNumber += 2;
                startNumber = stredNumber;
            }
        }
        return new Question(x, Number);
    }
}