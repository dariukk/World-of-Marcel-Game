import java.util.Objects;

public class Position {
    public int xCoordinate;
    public int yCoordinate;
    private int hashCode;

    public Position(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.hashCode = Objects.hash(xCoordinate, yCoordinate);
    }

    public String toString() {
        return this.xCoordinate + " " + this.yCoordinate;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;

        if (object == null)
            return false;

        if (this.getClass() != object.getClass())
            return false;

        Position positionObject = (Position) object;
        if (this.xCoordinate != positionObject.xCoordinate ||
                this.yCoordinate != positionObject.yCoordinate)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
