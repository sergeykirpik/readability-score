package readability;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Statistics {
    private static final Pattern SYLLABLES = Pattern.compile(
        "[^aeiyouAEIYOU\\W]([aiyou]|e\\B)|" +
        "\\b[aeiyouAEIYOU]|" +
        "\\b[^aeiyouAEIYOU\\W]+[aeiyouAEIYOU]\\b|" +
        "\\b\\d+,?\\d+\\b"
    );

    private int characters;
    private int words;
    private int sentences;
    private int syllables;
    private int polysyllables;

    public static Statistics calculate(String text) {
        Statistics s = new Statistics();
        s.sentences = countSentences(text);
        s.words = countWords(text);
        s.characters = countChars(text);
        s.syllables = countSyllables(text);
        s.polysyllables = countPolysyllables(text);
        return s;
    }

    public int getCharacters() {
        return characters;
    }

    public int getWords() {
        return words;
    }

    public int getSentences() {
        return sentences;
    }

    public int getSyllables() {
        return syllables;
    }

    public int getPolysyllables() {
        return polysyllables;
    }

    public Statistics() { }

    public void add(Statistics s) {
        this.characters += s.getCharacters();
        this.words += s.getWords();
        this.sentences += s.getSentences();
        this.syllables += s.getSyllables();
        this.polysyllables += s.getPolysyllables();
    }

    public static int countChars(String text) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isWhitespace(text.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    public static int countSentences(String text) {
        String[] sentences = StringUtils.splitIntoSentences(text);
        int count = 0;
        for (String sentence: sentences) {
            if (!sentence.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    public static int countWords(String text) {
        String[] words = StringUtils.splitIntoWords(text);
        int count = 0;
        for (String word: words) {
            if (!word.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    public static int countSyllables(String text) {
        String[] words = StringUtils.splitIntoWords(text);
        int count = 0;
        for (String word: words) {
            count += countSyllablesInWord(word);
        }
        return count;
    }

    public static int countPolysyllables(String text) {
        String[] words = StringUtils.splitIntoWords(text);
        int count = 0;
        for (String word: words) {
            int syllablesInWord = countSyllablesInWord(word);
            if (syllablesInWord > 2) {
                count++;
            }
        }
        return count;
    }

    public static int countSyllablesInWord(String word) {
        int count = 0;
        Matcher matcher = SYLLABLES.matcher(word);
        while(matcher.find()) {
            count++;
        }
        return count == 0 ? 1 : count;
    }
}

