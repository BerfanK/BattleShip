package constants;

public class Feld {


    private int owner = 0;
    public boolean ship = false;

    private int xPos = 0;
    private int yPos = 0;
    public Feld(int x, int y, int owner){
        this.owner = owner;
        this.yPos = y;
        this.xPos = x;
    }

    private  boolean isHit = false;

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public String getChar() {

        // place specific char on each field

        // default: empty (no ship and not hit)
        String response = " ";
        if(this.ship){
            if(this.isHit){
                // if ship has been hitted:
                response = Colors.GREEN + "@" + Colors.RESET;
            } else if (this.owner == 0) { // only show your own ships
                // if ship has not been hitted:
                response = Colors.CYAN + "#" + Colors.RESET;
            }
        } else {

            // if you didnt hit a ship
           if (this.isHit){
               response = Colors.RED + "x" + Colors.RESET;

           }
        }

        return response;

    }
    public boolean isHit() {
        return this.isHit;
    }
    public boolean getShip() {
        return this.ship;
    }
    public void setOwner(int owner) {
        this.owner = owner;
    }
    public void setHit(boolean hit) {
        this.isHit = hit;
    }
    public void setShip(boolean hit) {
        this.ship = hit;
    }


}
