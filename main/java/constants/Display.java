package constants;

import java.util.Arrays;

public class Display {

    private static String text = "GuMo";

    public String getText() {
        return text;
    }

    // convert (A1) to int array (0 and 1)
    public int[] convertCoordinate(String coordinates) {
        char firstNumberG = coordinates.charAt(0);
        int secondNumberAsInt = 0;

        if (coordinates.length() == 2) {
            char secondNumber = coordinates.charAt(1);
            secondNumberAsInt = Character.getNumericValue(secondNumber);
        } else if (coordinates.length() == 3){
            secondNumberAsInt = 10;
        } else {
            return null;
        }

        // use ascii to convert each letter to number (A = 65)
        char ascii = firstNumberG;
        int firstNumber = ascii-65;

        int[] coordinate = {secondNumberAsInt - 1, firstNumber};
        return coordinate;
    }

    // same as above, but the other side. (example: 0 and 1 to A and 1)
    public String convertToCoordinate(int xPos, int yPos) {
        String coordinate = "";

        switch (yPos) {
            case 0:
                coordinate = "A";
                break;
            case 1:
                coordinate = "B";
                break;
            case 2:
                coordinate = "C";
                break;
            case 3:
                coordinate = "D";
                break;
            case 4:
                coordinate = "E";
                break;
            case 5:
                coordinate = "F";
                break;
            case 6:
                coordinate = "G";
                break;
            case 7:
                coordinate = "H";
                break;
            case 8:
                coordinate = "I";
                break;
            case 9:
                coordinate = "J";
                break;
        }

        return coordinate + (xPos + 1);
    }

}
