package readability;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private static final TextAnalyzer analyzer = new TextAnalyzer();

    public static void main(String[] args) throws FileNotFoundException {
        try(Scanner scanner = getScanner(args)) {
            while (scanner.hasNextLine()) {
                analyzer.analyze(scanner.nextLine());
            }
            printStats();
        }
    }

    static void printStats() {
        Statistics stats = analyzer.getAccumulatedStats();
        System.out.println();
        System.out.printf("Words: %d\n", stats.getWords());
        System.out.printf("Sentences: %d\n", stats.getSentences());
        System.out.printf("Characters: %d\n", stats.getCharacters());
        System.out.printf("Syllables: %d\n", stats.getSyllables());
        System.out.printf("Polysyllables: %d\n", stats.getPolysyllables());
        System.out.println();

        double indexARI = analyzer.calculateARI();
        double indexFK = analyzer.calculateFK();
        double indexSMOG = analyzer.calculateSMOG();
        double indexCL = analyzer.calculateCL();

        int ageARI = getAgeFor(indexARI);
        int ageFK = getAgeFor(indexFK);
        int ageSMOG = getAgeFor(indexSMOG);
        int ageCL = getAgeFor(indexCL);

        double avgAge = (ageARI + ageFK + ageSMOG + ageCL) / 4.0;

        System.out.printf("Automated Readability Index: %.2f (about %d year olds).\n",
                indexARI, ageARI);

        System.out.printf("Flesch–Kincaid readability tests: %.2f (about %d year olds).\n",
                indexFK, ageFK);

        System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d year olds).\n",
                indexSMOG, ageSMOG);

        System.out.printf("Coleman–Liau index: %.2f (about %d year olds).\n",
                indexCL, ageCL);

        System.out.println();
        System.out.printf("This text should be understood in average by %.2f year olds.\n",
                avgAge);
    }

    static int getAgeFor(double readabilityIndex) {
        int rounded = (int) Math.round(readabilityIndex);
        rounded = Math.min(13, rounded);
        rounded = Math.max(1, rounded);
        int[] ages = {6,7,9,10,11,12,13,14,15,16,17,18,24};
        return ages[rounded - 1];
    }

    static Scanner getScanner(String[] args) throws FileNotFoundException {
        if (args.length == 0) {
            return new Scanner(System.in);
        }
        return new Scanner(new FileInputStream(args[0]));
    }
}


