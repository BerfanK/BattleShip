package constants;

import java.util.Random;

public class Spielfeld {

    Feld[][][] felder = new Feld[10][10][2];
    String[][] fields = new String[10][10];

    private int position_x = 0;
    private int position_y = 0;

    public Spielfeld() {
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++){
                 felder[x][y][0] = new Feld(x, y, 0); // set for player
                 felder[x][y][1] = new Feld(x, y, 1); // set for computer
            }
        }
    }

    public void setHit(int x, int y, int owner){
        felder[x][y][owner].setHit(true);
    } // hit a field
    public boolean isShip(int x, int y, int owner){ // check if ship on coordinate
        return felder[x][y][owner].ship;
    }
    public void setShip(int x, int y, int owner){ // set ship on coordinate
        felder[x][y][owner].setShip(true);
    }

    public void computerPlace(){ // randomly place computers ships
        Random rn = new Random();
        for(int i = 0; i < 5; i++ ){
            int yPos = rn.nextInt(10);
            int xPos = rn.nextInt(10);
            char[] himmelsrichtungen = {'N', 'O', 'S', 'W'};
            char richtung = himmelsrichtungen[rn.nextInt(himmelsrichtungen.length - 1)]; // pick if north, east, south or west
            int low = 2;
            int high = 6;
            int shipLength = rn.nextInt(high-low) + low; // pick between 1 and 6
            switch (richtung){
                case 'N':
                    for(int x = 0; x <= shipLength; x++){
                        if(xPos + x < 10){

                            this.setShip(xPos + x, yPos, 1); // place ship and go on to north
                        } else {
                            if(x == 1){
                                i = i - 1;
                            }
                        }
                    }
                    break;

                case 'O':
                    for(int x = 0; x <= shipLength; x++){
                        if(yPos + x < 10){

                            this.setShip(xPos, yPos + x, 1); // same like above, but east side
                        } else {
                            if(x == 1){

                                i = i - 1;
                            }
                        }
                    }
                    break;

                case 'S':
                    for(int x = 0; x <= shipLength; x++){
                        if(xPos - x >= 0){

                            this.setShip(xPos - x, yPos, 1); // same but south side
                        } else {
                            if(x == 1){

                                i = i - 1;
                            }
                        }
                    }
                    break;

                case 'W':
                    for(int x = 0; x <= shipLength; x++){
                        if(yPos - x >= 1){

                            this.setShip(xPos, yPos - x, 1);
                        } else {
                            if(x == 1){

                                i = i - 1; // same but west side
                            }
                        }
                    }
                    break;
            }
        }
    }

    public boolean computerShoot(){
        Random rn = new Random();
        int yPos = rn.nextInt(10);
        int xPos = rn.nextInt(10);

        this.setPosition_x(xPos);
        this.setPosition_y(yPos);
        this.setHit(xPos, yPos, 0);

        return this.isShip(xPos, yPos, 0);
    }

    public int getPosition_x() {
        return position_x;
    } // get position x (required to check where the computer shot)
    public int getPosition_y() {
        return position_y;
    } // same like above, but position y
    public void setPosition_x(int position_x) {
        this.position_x = position_x;
    } // set position x
    public void setPosition_y(int position_y) {
        this.position_y = position_y;
    } // set position y

    public int isWon(){ // get the winner

        int shipsLeftPC = 0;
        int shipsLeftPlayer = 0;

        for(int i = 0; i < 10; i++){
            for(int x = 0; x < 10; x++){

                //check if Player Won
                if(felder[i][x][0].getShip()){
                    if(!felder[i][x][0].isHit()){
                        shipsLeftPlayer += 1;
                    }
                }

                if(felder[i][x][1].getShip()){
                    if(!felder[i][x][1].isHit()){
                        shipsLeftPC += 1;
                    }
                }

            }
        }

        if(shipsLeftPC == 0){
            return 1;
        }
        if(shipsLeftPlayer == 0){
            return 0;
        }

        return 2;
    }


    public void drawSpielfeld(int owner) { // draw the field

        for(int i = 0; i < 10; i++){
            for(int x = 0; x < 10; x++){
                fields[i][x] = felder[i][x][owner].getChar(); // place a char in each field
            }
        }

        System.out.println("# \t|\t" + Colors.BLUE + "A" + Colors.RESET + "\t|\t" + Colors.BLUE + "B" + Colors.RESET + "\t|\t" + Colors.BLUE + "C" + Colors.RESET + "\t|\t" + Colors.BLUE + "D" + Colors.RESET + "\t|\t" + Colors.BLUE + "E" + Colors.RESET + "\t|\t" + Colors.BLUE + "F" + Colors.RESET + "\t|\t" + Colors.BLUE + "G" + Colors.RESET + "\t|\t" + Colors.BLUE + "H" + Colors.RESET + "\t|\t" + Colors.BLUE + "I" + Colors.RESET + "\t|\t" + Colors.BLUE + "J" + Colors.RESET + "\t|");
        System.out.println("———\t|———————|———————|———————|———————|———————|———————|———————|———————|———————|———————|");
        System.out.printf(Colors.BLUE + "1" + Colors.RESET + "\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\n", fields[0][0], fields[0][1], fields[0][2], fields[0][3], fields[0][4], fields[0][5], fields[0][6], fields[0][7], fields[0][8], fields[0][9]);
        System.out.println("———\t|———————|———————|———————|———————|———————|———————|———————|———————|———————|———————|");
        System.out.printf(Colors.BLUE + "2" + Colors.RESET + "\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\n", fields[1][0], fields[1][1], fields[1][2], fields[1][3], fields[1][4], fields[1][5], fields[1][6], fields[1][7], fields[1][8], fields[1][9]);
        System.out.println("———\t|———————|———————|———————|———————|———————|———————|———————|———————|———————|———————|");
        System.out.printf(Colors.BLUE + "3" + Colors.RESET + "\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\n", fields[2][0], fields[2][1], fields[2][2], fields[2][3], fields[2][4], fields[2][5], fields[2][6], fields[2][7], fields[2][8], fields[2][9]);
        System.out.println("———\t|———————|———————|———————|———————|———————|———————|———————|———————|———————|———————|");
        System.out.printf(Colors.BLUE + "4" + Colors.RESET + "\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\n", fields[3][0], fields[3][1], fields[3][2], fields[3][3], fields[3][4], fields[3][5], fields[3][6], fields[3][7], fields[3][8], fields[3][9]);
        System.out.println("———\t|———————|———————|———————|———————|———————|———————|———————|———————|———————|———————|");
        System.out.printf(Colors.BLUE + "5" + Colors.RESET + "\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\n", fields[4][0], fields[4][1], fields[4][2], fields[4][3], fields[4][4], fields[4][5], fields[4][6], fields[4][7], fields[4][8], fields[4][9]);
        System.out.println("———\t|———————|———————|———————|———————|———————|———————|———————|———————|———————|———————|");
        System.out.printf(Colors.BLUE + "6" + Colors.RESET + "\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\n", fields[5][0], fields[5][1], fields[5][2], fields[5][3], fields[5][4], fields[5][5], fields[5][6], fields[5][7], fields[5][8], fields[5][9]);
        System.out.println("———\t|———————|———————|———————|———————|———————|———————|———————|———————|———————|———————|");
        System.out.printf(Colors.BLUE + "7" + Colors.RESET + "\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\n", fields[6][0], fields[6][1], fields[6][2], fields[6][3], fields[6][4], fields[6][5], fields[6][6], fields[6][7], fields[6][8], fields[6][9]);
        System.out.println("———\t|———————|———————|———————|———————|———————|———————|———————|———————|———————|———————|");
        System.out.printf(Colors.BLUE + "8" + Colors.RESET + "\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\n", fields[7][0], fields[7][1], fields[7][2], fields[7][3], fields[7][4], fields[7][5], fields[7][6], fields[7][7], fields[7][8], fields[7][9]);
        System.out.println("———\t|———————|———————|———————|———————|———————|———————|———————|———————|———————|———————|");
        System.out.printf(Colors.BLUE + "9" + Colors.RESET + "\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\n", fields[8][0], fields[8][1], fields[8][2], fields[8][3], fields[8][4], fields[8][5], fields[8][6], fields[8][7], fields[8][8], fields[8][9]);
        System.out.println("———\t|———————|———————|———————|———————|———————|———————|———————|———————|———————|———————|");
        System.out.printf(Colors.BLUE + "10" + Colors.RESET + "\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\n", fields[9][0], fields[9][1], fields[9][2], fields[9][3], fields[9][4], fields[9][5], fields[9][6], fields[9][7], fields[9][8], fields[9][9]);
        System.out.println("———\t|———————|———————|———————|———————|———————|———————|———————|———————|———————|———————|");
    }
}
