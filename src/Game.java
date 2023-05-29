import java.io.IOException;
import java.util.Scanner;

class Game {
    static Scanner scanner = new Scanner(System.in);
    static Player[] players = new Player[2];
    static final int MAX_INDEX = 10;


    Game (Player player1, Player player2) {
        players[0] = player1;
        players[1] = player2;
    }

    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame() throws Exception {
        for (Player player : players) {
            System.out.printf("%s, place your ships on the game field%n", player.getName());
            Field battleField = player.getField();
            battleField.drawField(battleField.getMyField());
            placeShips(player);
            promptEnterKey();
        }

        boolean gameOver = false;

        while (!gameOver) {
            for (Player player : players) {
                try {
                    player.takeShot(player.getEnemy());
                    if (player.getEnemy().calcLives() == 0) {
                        gameOver = true;
                    }
                } catch (Exception e) {
                    System.out.println("Error! Wrong coordinates!");
                }
            }
        }
    }

    void placeShips(Player player) {
        Unit[] units = player.getUnits();

        for (Unit unit : units) {
            boolean done = false;
            System.out.printf("Enter the coordinates of the %s (%d cells):%n", unit.getName(), unit.getLives());
            while (!done) {
                try {
                    String[] coordinates = scanner.nextLine().toUpperCase().trim().split(" ");
                    placeOneShip(coordinates[0], coordinates[coordinates.length - 1], player, unit);
                    done = true;
                } catch (ShipLengthException | ShipLocationException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println("Error! Wrong ship location! Try again:");
                }
            }
        }
    }

    void placeOneShip(String x, String y, Player player, Unit unit) throws Exception {
        int[] myX = coordinateDecoder(x);
        int[] myY = coordinateDecoder(y);

        String[][] myField = player.getField().getMyField();
        Field playerField = player.getField();

        int startLetter = Math.min(myX[0], myY[0]);
        int endLetter = Math.max(myX[0], myY[0]);

        int startNumber = Math.min(myX[1], myY[1]);
        int endNumber = Math.max(myX[1], myY[1]);

        if (startLetter == endLetter) {
            if (endNumber - startNumber != unit.getLives() - 1) {
                throw new ShipLengthException(String.format("Error! Wrong length of the %s! Try again:%n", unit.getName()));
            }
            drawShipHorizontal(startLetter, startNumber, endNumber, player, unit);
        } else if (startNumber == endNumber) {
            if (endLetter - startLetter != unit.getLives() - 1) {
                throw new ShipLengthException(String.format("Error! Wrong length of the %s! Try again:%n", unit.getName()));
            }

            drawShipVertical(startNumber, startLetter, endLetter, player, unit);
        } else {
            throw new Exception();
        }

        playerField.drawField(myField);
    }

    void drawShipVertical(int constant, int start, int end, Player player, Unit unit) throws ShipLocationException {
        String[][] field = player.getField().getMyField();

        if (field[start - 1][constant].equals("O") || field[Math.min(end + 1, MAX_INDEX)][constant].equals("O")) {
            throw new ShipLocationException();
        }

        for (int i = start; i <= end; i++) {
            if (field[i][Math.min(constant + 1, MAX_INDEX)].equals("O") || field[i][constant - 1].equals("O")) {
                throw new ShipLocationException();
            }
        }

        for (int i = start, j = 0; i <= end; i++, j++) {
            field[i][constant] = "O";
            unit.setCoordinates(j ,new int[]{i, constant});
        }
    }

    void drawShipHorizontal(int constant, int start, int end, Player player, Unit unit) throws ShipLocationException {
        String[][] field = player.getField().getMyField();

        if (field[constant][start - 1].equals("O") || field[constant][Math.min(MAX_INDEX, end + 1)].equals("O")) {
            throw new ShipLocationException();
        }

        for (int i = start; i <= end; i++) {
            if (field[constant - 1][i].equals("O") || field[Math.min(constant + 1, MAX_INDEX)][i].equals("O")) {
                throw new ShipLocationException();
            }
        }

        for (int i = start, j = 0; i <= end; i++, j++) {
            field[constant][i] = "O";
            unit.setCoordinates(j, new int[]{constant, i});
        }
    }

    public static int[] coordinateDecoder(String coordinate) {
        int letter = coordinate.charAt(0) - 64;
        int number = Integer.parseInt(String.valueOf(coordinate.charAt(1)));

        if (coordinate.length() > 2) {
            number = Integer.parseInt(String.valueOf(coordinate.charAt(1)) + String.valueOf(coordinate.charAt(2)));
        }
        return new int[] {letter, number};
    }

}
