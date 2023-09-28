import java.util.Random;
import java.util.Scanner;

public class Main {

    private static int attempts;
    private static String word;
    private static String wrongLetters;
    private static String[] underscores;
    private static boolean alreadyGuessed;


    public static void main(String[] args) {
        System.out.println("\nVälkommen till hänga gubbe!");
        Scanner scan = new Scanner(System.in);
        String[] wordList = createWordList();

        boolean playAgain;
        do {
            word = getRandomWord(wordList);
            startGame();

            do {
                alreadyGuessed = false;
                String currentGuess = readLetter();
                boolean correctGuess = checkGuess(currentGuess);
                printGamePlan(correctGuess, currentGuess);

            } while (attempts > 0 && !checkWordCompleted());

            if (attempts == 0) {
                System.out.println("Tyvärr, du dog! :(");
                System.out.println("Det rätta ordet var " + word);
            } else {
                System.out.println("Grattis! Du listade ut ordet!");
                drawHappyGuy();
            }
            System.out.println("Vill du spela igen? [j/n]");
            playAgain = scan.nextLine().equalsIgnoreCase("j");
        } while (playAgain);
    }

    private static String[] createWordList() {
        String[] wordList = {"djungelvrål", "nutella", "hockeypulver", "marabou", "kexchoklad", "marianne", "kinderägg", "ferraribilar", "ahlgrens", "dajmstrut", "delicato", "sandwich", "piggelin", "twister", "kinapuffar", "marshmallows", "snöbollar", "snickers", "cloetta", "kolaremmar", "gummibjörnar", "trillingnöt", "skittles", "toblerone", "smarties"};
        return wordList;
    }

    private static String getRandomWord(String[] wordList) {
        Random random = new Random();
        int wordIndex = random.nextInt(1, 25);
        return wordList[wordIndex];
    }

    private static void startGame() {
        underscores = new String[word.length() * 2];
        for (int i = 0; i < underscores.length; i += 2) {
            underscores[i] = " ";
            underscores[i + 1] = "_";
        }
        attempts = 7;
        wrongLetters = "";

        printGamePlan();
    }

    private static void printGamePlan() {
        System.out.print("Lista ut det hemliga ordet: ");
        printArrayValues();
        System.out.println("Antal försök kvar - " + attempts);
        System.out.println("Felaktiga gissningar - " + wrongLetters);
        drawHangMan(attempts);
    }

    private static void printArrayValues() {
        for (int i = 0; i < underscores.length; i++) {
            System.out.print(underscores[i]);
            //some comment
        }
        System.out.println();
    }

    private static String readLetter() {
        System.out.println("Gissa: ");
        Scanner scan = new Scanner(System.in);
        String letter = scan.nextLine();
        while (letter.length() != 1 || !Character.isLetter(letter.charAt(0))) {
            System.out.println("Inte en giltig bokstav. Försök igen.");
            letter = scan.next();
        }
        return letter.toUpperCase();
    }

    private static boolean checkGuess(String currentGuess) {
        boolean correctGuess = fillInBlanks(currentGuess);
        if (!correctGuess) {
            if (wrongLetters.isEmpty()) {
                wrongLetters += currentGuess;
                attempts--;
            } else {
                if (wrongLetters.contains(currentGuess)) {
                    alreadyGuessed = true;
                } else {
                    wrongLetters += ", " + currentGuess;
                    attempts--;
                }
            }
        }
        return correctGuess;
    }

    private static boolean fillInBlanks(String currentGuess) {
        boolean correctGuess = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.toUpperCase().charAt(i) == currentGuess.charAt(0)) {
                correctGuess = true;
                if (underscores[i * 2 + 1].equals(currentGuess)) {
                    alreadyGuessed = true;
                    break;
                } else {
                    underscores[i * 2 + 1] = currentGuess;
                }
            }
        }
        if (wrongLetters.contains(currentGuess)) {
            alreadyGuessed = true;
        }
        return correctGuess;
    }

    private static void printGamePlan(boolean correctGuess, String currentGuess) {
        if (alreadyGuessed) {
            System.out.println("Du har redan gissat på " + currentGuess + "!");
        } else if (correctGuess) {
            System.out.println("Rätt gissat!");
        } else {
            System.out.println("Fel gissat!");
        }
        printGamePlan();
    }

    private static boolean checkWordCompleted() {
        boolean hasMoreUnderscores = false;
        for (int i = 0; i < underscores.length; i++) {
            if (underscores[i].equals("_")) {
                hasMoreUnderscores = true;
                break;
            }
        }
        return !hasMoreUnderscores;
    }

    private static void drawHangMan(int attempt) {
        switch (attempt) {
            case 4:
                drawStage3();
            case 5:
                drawStage2();
            case 6:
                drawStage1();
                break;
            case 3:
                drawStage4();
                break;
            case 2:
                drawStage5();
                break;
            case 1:
                drawStage6();
                break;
            case 0:
                drawStage7();
                break;
        }
    }

    private static void drawStage1() {
        System.out.println("   -----------");
    }

    private static void drawStage2() {
        System.out.println("        |/");
        System.out.println("        |");
        System.out.println("        |");
        System.out.println("        |");
        System.out.println("        |");
        System.out.println("        |");
    }

    private static void drawStage3() {
        System.out.println("        _________");
    }

    private static void drawStage4() {
        System.out.println("        _________");
        System.out.println("        |/      |");
        System.out.println("        |");
        System.out.println("        |");
        System.out.println("        |");
        System.out.println("        |");
        System.out.println("        |");
        System.out.println("   -----------");
    }

    private static void drawStage5() {
        System.out.println("        _________");
        System.out.println("        |/      |");
        System.out.println("        |      (_)");
        System.out.println("        |");
        System.out.println("        |");
        System.out.println("        |");
        System.out.println("        |");
        System.out.println("   -----------");
    }

    private static void drawStage6() {
        System.out.println("        _________");
        System.out.println("        |/      |");
        System.out.println("        |      (_)");
        System.out.println("        |      /|\\");
        System.out.println("        |");
        System.out.println("        |");
        System.out.println("        |");
        System.out.println("   -----------");
    }

    private static void drawStage7() {
        System.out.println("        _________");
        System.out.println("        |/      |");
        System.out.println("        |      (_)");
        System.out.println("        |      /|\\");
        System.out.println("        |      / \\");
        System.out.println("        |");
        System.out.println("        |");
        System.out.println("   -----------");
    }

    private static void drawHappyGuy() {
        System.out.println("     *   *   *   *   *");
        System.out.println("       *  " + word + "  *");
        System.out.println("     *   *   *   *   *");
        System.out.println("       *   \\(_)/   *");
        System.out.println("     *   *   |   *   *");
        System.out.println("            / \\");
    }
}