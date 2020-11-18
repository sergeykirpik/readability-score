package readability;

public class TextAnalyzer {

    private final Statistics accumulatedStats = new Statistics();

    public void analyze(String text) {
        Statistics textStats = Statistics.calculate(text);
        accumulatedStats.add(textStats);
    }

    public Statistics getAccumulatedStats() {
        return accumulatedStats;
    }

    public double calculateARI() {
        double characters = accumulatedStats.getCharacters();
        double words = accumulatedStats.getWords();
        double sentences = accumulatedStats.getSentences();
        return 4.71 * (characters / words) + 0.5 * (words / sentences) - 21.43;
    }

    public double calculateFK() {
        double words = accumulatedStats.getWords();
        double sentences = accumulatedStats.getSentences();
        double syllables = accumulatedStats.getSyllables();
        return 0.39 * (words / sentences) + 11.8 * (syllables / words) - 15.59;
    }

    public double calculateSMOG() {
        double polysyllables = accumulatedStats.getPolysyllables();
        double sentences = accumulatedStats.getSentences();
        return 1.043 * Math.sqrt(polysyllables * (30 / sentences)) + 3.1291;
    }

    public double calculateCL() {
        double letters = accumulatedStats.getCharacters();
        double words = accumulatedStats.getWords();
        double sentences = accumulatedStats.getSentences();
        double L = letters / words * 100;
        double S = sentences / words * 100;
        return 0.0588 * L - 0.296 * S - 15.8;
    }

}
