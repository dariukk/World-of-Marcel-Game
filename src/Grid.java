import java.util.ArrayList;
import java.util.HashMap;

public class Grid extends ArrayList {
    public int length;
    public int width;
    public Character selectedcharacter;
    public Cell currentCell;

    private Grid(int length, int width, Character character) {
        this.length = length;
        this.width = width;
        this.selectedcharacter = character;
        this.currentCell = new Cell(new Position(0, 0), Cell.CellEnum.EMPTY);
        this.currentCell.visitedCell = true;
    }

    public static Grid generateMap(int length, int width, Character selectedCharacter,
                                   HashMap<Position, Cell.CellEnum> specialCells) {
        Grid gameBoard = new Grid(length, width, selectedCharacter);

        for (int i = 0; i < gameBoard.length; ++i) {
            ArrayList<Cell> line = new ArrayList<Cell>();

            for (int j = 0; j < gameBoard.width; ++j) {
                Cell.CellEnum cellType = Cell.CellEnum.EMPTY;

                if (specialCells.get(new Position(i, j)) != null)
                    cellType = specialCells.get(new Position(i, j));

                Cell cell = new Cell(new Position(i, j), cellType);
                line.add(cell);
            }

            gameBoard.add(line);
        }
        ((ArrayList<Cell>) gameBoard.get(0)).get(0).visitedCell = true;
        return gameBoard;
    }

    public static void goNorth(Grid gameBoard) {
        if (gameBoard.currentCell.position.xCoordinate == 0) {
            System.out.println("Invalid move");
            return;
        }

        ((ArrayList<Cell>) gameBoard.get(gameBoard.currentCell.position.xCoordinate - 1)).
                get(gameBoard.currentCell.position.yCoordinate).visitedCell = true;
        gameBoard.currentCell = ((ArrayList<Cell>) gameBoard
                .get(gameBoard.currentCell.position.xCoordinate - 1))
                .get(gameBoard.currentCell.position.yCoordinate);
    }

    public static void goSouth(Grid gameBoard) {
        if (gameBoard.currentCell.position.xCoordinate == gameBoard.length - 1) {
            System.out.println("Invalid move");
            return;
        }

        ((ArrayList<Cell>) gameBoard.get(gameBoard.currentCell.position.xCoordinate + 1)).
                get(gameBoard.currentCell.position.yCoordinate).visitedCell = true;
        gameBoard.currentCell = ((ArrayList<Cell>) gameBoard
                .get(gameBoard.currentCell.position.xCoordinate + 1))
                .get(gameBoard.currentCell.position.yCoordinate);
    }

    public static void goWest(Grid gameBoard) {
        if (gameBoard.currentCell.position.yCoordinate == 0) {
            System.out.println("Invalid move");
            return;
        }

        ((ArrayList<Cell>) gameBoard.get(gameBoard.currentCell.position.xCoordinate)).
                get(gameBoard.currentCell.position.yCoordinate - 1).visitedCell = true;
        gameBoard.currentCell = ((ArrayList<Cell>) gameBoard
                .get(gameBoard.currentCell.position.xCoordinate))
                .get(gameBoard.currentCell.position.yCoordinate - 1);
    }

    public static void goEast(Grid gameBoard) {
        if (gameBoard.currentCell.position.yCoordinate == gameBoard.width - 1) {
            System.out.println("Invalid move");
            return;
        }

        ((ArrayList<Cell>) gameBoard.get(gameBoard.currentCell.position.xCoordinate)).
                get(gameBoard.currentCell.position.yCoordinate + 1).visitedCell = true;
        gameBoard.currentCell = ((ArrayList<Cell>) gameBoard
                .get(gameBoard.currentCell.position.xCoordinate))
                .get(gameBoard.currentCell.position.yCoordinate + 1);
    }

    public static void printMap(Grid gameBoard) {
        for (int i = 0; i < gameBoard.length; ++i) {
            for (int j = 0; j < gameBoard.width; ++j)
                if (((ArrayList<Cell>) gameBoard.get(i)).get(j).visitedCell) {

                    if (gameBoard.currentCell.position.equals(new Position(i, j)))
                        System.out.print("P");
                    if (!gameBoard.currentCell.position.equals(new Position(i, j)) ||
                            !(((ArrayList<Cell>) gameBoard.get(i)).get(j).cellType).equals(Cell.CellEnum.EMPTY))
                        System.out.print(((ArrayList<Cell>) gameBoard.get(i)).get(j));
                    System.out.print(" ");
                } else
                    System.out.print("? ");
            System.out.println();
        }
    }
}
