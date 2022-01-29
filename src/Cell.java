public class Cell {
    enum CellEnum {
        EMPTY,
        ENEMY,
        SHOP,
        FINISH
    }

    public Cell(Position position, CellEnum cellType) {
        this.position = position;
        this.cellType = cellType;

        if (cellType.equals(CellEnum.SHOP))
            this.cellElement = new Shop();

        if (cellType.equals(CellEnum.ENEMY))
            this.cellElement = new Enemy();

        this.visitedCell = false;
    }

    public String toString() {
        switch (cellType) {
            case EMPTY:
                return "N";

            case FINISH:
                return "F";
        }
        return this.cellElement.toCharacter();
    }

    public Position position;
    public CellEnum cellType;
    public CellElement cellElement;
    public boolean visitedCell;
}
