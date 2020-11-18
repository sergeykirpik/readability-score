package readability;

public class StringUtils {
    public static String[] splitIntoSentences(String text) {
        return text.split("[.?!]");
    }

    public static String[] splitIntoWords(String sentence) {
        return sentence.split("\\s+");
    }
}