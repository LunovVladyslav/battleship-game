import java.util.Arrays;

class Field {
    private String[][] myField = new String[11][11];
    private String[][] emptyField;

    Field() {
        this.emptyField = fillBattlefield();
    }

    String[][] fillBattlefield() {
        char letter = 'A';
        int number = 1;

        for (int i = 0; i < myField.length; i++) {
            for (int j = 0; j < myField.length; j++) {
                if (i == 0 && j == 0) {
                    myField[i][j] = " ";
                } else if (i == 0) {
                    myField[i][j] = String.valueOf(number);
                    number++;
                } else if (j == 0) {
                    myField[i][j] = String.valueOf(letter);
                    letter++;
                } else {
                    myField[i][j] = "~";
                }
            }
        }
        String[][] copiedArray = new String[myField.length][];

        for (int i = 0; i < myField.length; i++) {
            copiedArray[i] = Arrays.copyOf(myField[i], myField[i].length);
        }
        return copiedArray;

    }

    void drawField(String[][] arr) {
        String out = "";
        for (String[] row : arr) {
            for (String item : row) {
                out += item + " ";
            }
            out += "\n";
        }

        System.out.print(out);
    }
    public String[][] getMyField() {
        return myField;
    }
    public String[][] getEmptyField() {
        return emptyField;
    }
}

