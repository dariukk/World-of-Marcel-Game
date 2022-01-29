import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static class InvalidCommandException extends Exception {
    }

    public static void verifyGameMode(String gameOption) throws InvalidCommandException {
        if (!(gameOption.equals("terminal") || gameOption.equals("graphic interface")))
            throw new InvalidCommandException();
    }

    public static void main(String args[]) throws IOException {
        System.out.println("Choose game option: terminal / graphic interface");

        Scanner obj = new Scanner(System.in);
        String gameOption = obj.nextLine();

        try {
            verifyGameMode(gameOption);
        } catch (InvalidCommandException e) {
            System.out.println("Choose one of these two options: terminal / graphic interface");
            e.printStackTrace();
        }

        Game newGame = Game.getInstance();
        newGame.run(gameOption);
    }
}
