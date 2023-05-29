public class Main {
    public static void main(String[] args) throws Exception {
        // Write your code here
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        player1.setEnemy(player2);
        player2.setEnemy(player1);

        Game game = new Game(player1, player2);
        game.startGame();
    }
}
enum Ships {
    AIRCRAFT("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    final String NAME;
    final int CELLS;

    Ships(String name, int cells) {
        this.NAME = name;
        this.CELLS = cells;
    }
}
class ShipLengthException extends Exception {
    ShipLengthException(String message) {
        super(message);
    }
}
class ShipLocationException extends Exception {
    ShipLocationException() {
        super("Error! You placed it too close to another one. Try again:\n");
    }
}