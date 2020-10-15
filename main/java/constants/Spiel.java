package constants;

import java.util.Scanner;

public class Spiel {

    public void runGame() throws InterruptedException {

        Spielfeld spielfeld = new Spielfeld();
        Display display = new Display();
        spielfeld.computerPlace();

        boolean running = true;
        while (running) { // used to re-run code after errors
            Scanner sc = new Scanner(System.in);
            int schiffePlaced = 0;
            spielfeld.drawSpielfeld(0);

            while (schiffePlaced < 5) { // allow up to 5 ships
                System.out.print(Colors.YELLOW + "Geben Sie die Koordinaten des " + (schiffePlaced + 1) + ". Schiffs ein (Bsp. A1:C1): " + Colors.RESET);
                String eingabe = sc.next();
                if (isCorrectFormat(eingabe)) { // check if user is using the correct format (A1:C1)
                    String[] parts = eingabe.split(":"); // split the coordinates
                    String firstCoordinate = parts[0]; // define the first coordinate
                    String secondCoordinate = parts[1]; // define the second coordinate

                    int[] firstC = display.convertCoordinate(firstCoordinate); // convert the coordinate to int array
                    int[] secondC = display.convertCoordinate(secondCoordinate); // ^

                    // check if coordinates are correct (A-J and 1-10)
                    if(!isValid(firstCoordinate.charAt(0), Integer.parseInt(firstCoordinate.replace(String.valueOf(firstCoordinate.charAt(0)), ""))) || !isValid(secondCoordinate.charAt(0), Integer.parseInt(secondCoordinate.replace(String.valueOf(secondCoordinate.charAt(0)), "")))){
                        System.err.println("Es existiert kein Feld mit dieser Bezeichnung.");
                        running = false;
                        continue;
                    }
                    boolean validPlace = true;

                    for(int x = 1; x <= 2; x++){

                        // check if placements are horizontal
                        if (firstC[0] == secondC[0]) {
                            //Horizontal
                            int difference = secondC[1] - firstC[1];
                            if (difference < 1 || difference > 4) { // check if ship size is between 1 and 6
                                System.err.println("Sie können nur Schiffe der Grösse 2 bis 5 setzen. Bitte versuchen Sie es erneut.");
                                running = false;
                                break;
                            } else {
                                for (int i = 0; i <= difference; i++) {
                                    int[] cordToTakeActionWith = {firstC[0], firstC[1] + i};

                                    // check if there is already a ship on the spot
                                    if(x == 1){
                                        if(spielfeld.isShip(cordToTakeActionWith[0], cordToTakeActionWith[1], 0)){
                                            System.err.println("Sie können keine Schiffe übereinander platzieren.");
                                            running = false;
                                            validPlace = false;
                                            break;
                                        }
                                    }else {
                                        if(validPlace){
                                            // place the ships
                                            spielfeld.setShip(cordToTakeActionWith[0], cordToTakeActionWith[1], 0);

                                        }

                                    }
                                }
                            }

                            // check if placements are vertically
                        } else if (firstC[1] == secondC[1]) {

                            // same as above

                            int difference = secondC[0] - firstC[0];
                            if (difference < 1 || difference > 4) {
                                System.err.println("Sie können nur Schiffe der Grösse 2 bis 5 setzen. Bitte versuchen Sie es erneut.");
                                running = false;
                                break;
                            } else {
                                for (int i = 0; i <= difference; i++) {
                                    int[] cordToTakeActionWith = {secondC[1], firstC[0] + i};
                                    if(x == 1){
                                        if(spielfeld.isShip(cordToTakeActionWith[1], cordToTakeActionWith[0], 0)){
                                            System.err.println("Sie können keine Schiffe übereinander platzieren.");
                                            running = false;
                                            validPlace = false;
                                            break;
                                        }
                                    }else {
                                        if(validPlace){
                                            spielfeld.setShip(cordToTakeActionWith[1], cordToTakeActionWith[0], 0);

                                        }
                                    }
                                }
                            }

                            // else: placements are diagonally: not valid
                        } else {
                            System.err.println("Sie können keine Diagonalen Schiffe platzieren. Bitte versuchen Sie es erneut.");
                            running = false;
                            break;
                        }
                    }

                    // show the new looking field
                    spielfeld.drawSpielfeld(0);
                    schiffePlaced++;
                } else {
                    // format incorrect
                    System.err.println("Bitte beachten Sie das Format! (Bsp.: A1:B1)");
                    running = false;
                }
            }


            while (spielfeld.isWon() == 2) {
                System.out.print(Colors.YELLOW + "Geben Sie die Koordinaten des Schusses ein (Bsp.: A1): " + Colors.RESET);

                int[] coordinates = display.convertCoordinate(sc.next());
                spielfeld.setHit(coordinates[0], coordinates[1], 1);
                System.out.println(Colors.BLUE + "Computerspielfeld: ");
                spielfeld.drawSpielfeld(1);
                Thread.sleep(4000);

                System.out.println(Colors.RED + "Der Computer schießt, bitte warten..." + Colors.RESET);
                boolean compResult = spielfeld.computerShoot();
                Thread.sleep(2000);

                System.out.println(Colors.BLUE + "Ihr Spielfeld: " + Colors.RESET);
                spielfeld.drawSpielfeld(0);

                if (compResult) {
                    System.out.println(Colors.YELLOW + "Der Computer hat auf " + Colors.BLUE + display.convertToCoordinate(spielfeld.getPosition_x(), spielfeld.getPosition_y()) + Colors.YELLOW + " geschossen und " + Colors.GREEN + "getroffen" + Colors.YELLOW + ".");
                } else {
                    System.out.println(Colors.YELLOW + "Der Computer hat auf " + Colors.BLUE + display.convertToCoordinate(spielfeld.getPosition_x(), spielfeld.getPosition_y()) + Colors.YELLOW + " geschossen und " + Colors.RED + "nicht getroffen" + Colors.YELLOW + ".");
                }
            }

            if (spielfeld.isWon() == 0) {
                System.out.println(Colors.RED + "Du hast verloren!" + Colors.RESET);
                return;
            } else {
                System.out.println(Colors.GREEN + "Du hast gewonnen!" + Colors.RESET);
                return;
            }
        }
    }

    private boolean isCorrectFormat(String stringToCheck) {
        return stringToCheck.matches("[A-Z]\\d:[A-Z]\\d") || stringToCheck.matches("[A-Z]\\d{2}:[A-Z]\\d") || stringToCheck.matches("[A-Z]\\d:[A-Z]\\d{2}") || stringToCheck.matches("[A-Z]\\d{2}:[A-Z]\\d{2}");
    }

    private boolean isValid(char letter, int number) {
        boolean valid = false;
        if (letter == 'A' || letter == 'B' || letter == 'C' || letter == 'D' || letter == 'E' || letter == 'F' || letter == 'G' || letter == 'H' || letter == 'I' || letter == 'J') {
            if (number >= 1 && number <= 10) {
                valid = true;
            }
        }

        return valid;
        }
    }

