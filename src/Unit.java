import java.util.Arrays;
class Unit {
    private String name;
    private int lives;
    private int unitXY[][];

    Unit(String name) {
        this.name = name;

        for (Ships ship : Ships.values()) {
            if (name.equals(ship.NAME)) {
                this.lives = ship.CELLS;
                break;
            }
        }

        unitXY = new int[getLives()][];
    }

    String getName() {
        return this.name;
    }

    int getLives() {
        return this.lives;
    }

    void setLives() {
        this.lives = this.lives - 1;
    }

    void setCoordinates(int index, int[] coordinates) {
        this.unitXY[index] = coordinates;

    }

    void isHitting(int[] hit, Player enemy) {
        for (int[] c : unitXY) {
            if (Arrays.equals(hit, c)) {
                setLives();

                if (enemy.calcLives() == 0) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                }

                if (this.getLives() == 0) {
                    System.out.println("You sank a ship!");
                    break;
                } else {
                    System.out.println("You hit a ship!");
                }
            }
        }
    }
}
