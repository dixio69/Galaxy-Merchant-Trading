package com.hehe.gmt.utils;

import com.hehe.gmt.enumerations.RomanEnum;
import com.hehe.gmt.exceptions.UnknownRomanNumberException;
import com.hehe.gmt.exceptions.InvalidRomanNumberException;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RomanConverter {
    public static long romanToAlphabetNumber(String roman) throws UnknownRomanNumberException, InvalidRomanNumberException {
        validateRoman(roman);
        long res = 0;
        for (int i = 0; i < roman.length(); i++) {
            RomanEnum s1 = RomanEnum.getByLetter(String.valueOf(roman.charAt(i)));
            if (s1 == null)
                throw new UnknownRomanNumberException();

            if (i + 1 < roman.length()) {
                RomanEnum s2 = RomanEnum.getByLetter(String.valueOf(roman.charAt(i + 1)));
                if (s2 == null)
                    throw new UnknownRomanNumberException();
                if (s1.getValue() >= s2.getValue()) {
                    res = res + s1.getValue();
                } else {
                    if (s2.getSubtractor() != s1) {
                        throw new InvalidRomanNumberException();
                    }
                    res = res + s2.getValue() - s1.getValue();
                    i++;
                }
            } else {
                res = res + s1.getValue();
            }
        }
        return res;
    }

    private static void validateRoman(String roman) throws InvalidRomanNumberException {
        RomanEnum romanEnum;
        int charCount;
        List<RepeatedLetter> repeatedLetters = new ArrayList<>();
        for (int i = 0; i < roman.length(); i++) {
            System.out.println("Validating roman : " + roman.charAt(i));
            romanEnum = RomanEnum.getByLetter(String.valueOf(roman.charAt(i)));
            int[] indexes = getFirstLastRepeatableCharIndex(roman, roman.charAt(i), i);
            String subStr = indexes[1] != -1 ? roman.substring(indexes[0], indexes[1] + 1) : "";
            charCount = subStr.length();
            if ((charCount > 1 && !romanEnum.isRepeatable()) || charCount > 3) {
                throw new InvalidRomanNumberException();
            }
            if (indexes[1] > 0) {
                if (!isLetterRegistered(repeatedLetters, roman.charAt(i), i)) {
                    repeatedLetters.add(RepeatedLetter.builder()
                            .startIndex(indexes[0])
                            .endIndex(indexes[1])
                            .letter(roman.charAt(i))
                            .build());
                } else if (!isLetterInIndex(repeatedLetters, roman.charAt(i), i)) {
                    throw new InvalidRomanNumberException();
                }
            }
//            if (charCount <= 4) {
//                if (subStr.length() > 4) {
//                    throw new InvalidRomanNumberException();
//                }
//                if (charCount == 4 && !String.valueOf(subStr.charAt(3)).equalsIgnoreCase(romanEnum.getSubtractor().name())) {
//                    throw new InvalidRomanNumberException();
//                }
//                subStr = subStr.replace(romanEnum.name(), "");
//                if (!StringUtils.isBlank(subStr) && romanEnum.getSubtractor() != null && !romanEnum.getSubtractor().name().equals(subStr)) {
//                    throw new InvalidRomanNumberException();
//                }
//            }
        }
    }

    private static boolean isLetterRegistered(List<RepeatedLetter> list, char c, int index) {
        for (RepeatedLetter rl : list) {
            if (rl.getLetter() == c) {
                return true;
            }
        }
        return false;
    }

    private static boolean isLetterInIndex(List<RepeatedLetter> list, char c, int index) {
        for (RepeatedLetter rl : list) {
            if (rl.getLetter() == c && index >= rl.getStartIndex() && index <= rl.getEndIndex()) {
                return true;
            }
        }
        return false;
    }

    @Data
    @Builder
    private static class RepeatedLetter {
        private char letter;
        private int startIndex;
        private int endIndex;
    }

    private static int[] getFirstLastRepeatableCharIndex(String word, char c, int startAt) {
        int firstIndex = -1;
        int lastIndex = -1;
        for (int i = startAt; i < word.length(); i++) {
            if (word.charAt(i) == c) {
                if (firstIndex == -1) {
                    firstIndex = i;
                }
            }
            if (i > 0 && word.charAt(i) == word.charAt(i - 1) && word.charAt(i) == c) {
                lastIndex = i;
            } else if (word.charAt(i) != c)
                break;
            else continue;
        }
        return new int[]{firstIndex, lastIndex};
    }

    public static void main(String[] args) {
        try {
            System.out.println(romanToAlphabetNumber("MMMCMXCIX"));
        } catch (UnknownRomanNumberException | InvalidRomanNumberException e) {
            e.printStackTrace();
        }
    }
}
