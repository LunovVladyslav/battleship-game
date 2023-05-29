class Player {
    private String name;
    private Field battleField;
    private Player enemy;
    private Unit[] units = new Unit[Ships.values().length];

    Player(String name) {
        this.name = name;
        battleField = new Field();

        int i = 0;
        for(Ships ship : Ships.values()) {
            this.units[i] = new Unit(ship.NAME);
            i++;
        }
    }

    public void takeShot(Player enemy) throws Exception {
        String[][] emptyField = this.battleField.getEmptyField();
        String[][] enemyField = this.enemy.getField().getMyField();

        this.battleField.drawField(emptyField);
        for (int i = 0; i < 22; i++) {
            System.out.print("-");
        }
        System.out.println();
        this.battleField.drawField(battleField.getMyField());
        System.out.printf("%s, it's your turn:%n", this.getName());

        String input = Game.scanner.nextLine().toUpperCase().trim();
        if (input.isEmpty() || input.isBlank()) {
            throw new Exception();
        }
        int[] coordinates = Game.coordinateDecoder(input);

        if (coordinates[0] > 10 || coordinates[1] > 10 ||
                "X".equals(enemyField[coordinates[0]][coordinates[1]]) ||
                "M".equals(enemyField[coordinates[0]][coordinates[1]])) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            this.takeShot(enemy);
            return;
        }


        if ("O".equals(enemyField[coordinates[0]][coordinates[1]])) {
            enemyField[coordinates[0]][coordinates[1]] = "X";
            emptyField[coordinates[0]][coordinates[1]] = "X";
            for (Unit unit : enemy.units) {
                unit.isHitting(coordinates, enemy);
            }
        } else {
            enemyField[coordinates[0]][coordinates[1]] = "M";
            emptyField[coordinates[0]][coordinates[1]] = "M";

            System.out.println("You missed!");
        }
         Game.promptEnterKey();
    }

    int calcLives() {
        int sum = 0;
        Unit[] units = this.getUnits();
        for (Unit u : units) {
            sum += u.getLives();
        }
        return sum;
    }

    public Field getField() {
        return battleField;
    }
    public Unit[] getUnits() {
        return units;
    }
    public String getName() {
        return name;
    }
    public void setEnemy(Player player) {
        this.enemy = player;
    }

    public Player getEnemy() {
        return enemy;
    }
}
