package com.itwill.rest.Util;

import java.util.HashMap;
import java.util.Map;

public class ConverterUtil {

    // 영어 단어를 한글로 변환하는 매핑
    private static final Map<String, String> englishToKorean = new HashMap<>();
    
    // 한글 제목을 영어로 변환하는 매핑
    private static final Map<String, String> koreanToEnglish = new HashMap<>();
    
    // 발음 변형 매핑
    private static final Map<String, String> phoneticVariations = new HashMap<>();

    static {
        // 영어 단어를 한글로 변환하는 매핑 추가
        addEnglishToKoreanMapping("mirror", "거울");

        // 한글 제목을 영어로 변환하는 매핑 추가
        addKoreanToEnglishMapping("어텐션", "Attention");

        // 발음 변형 매핑 추가
        addPhoneticVariation("rjdnf", "거울");  // 'rjdnf'는 '거울'의 발음 변형으로 가정
    }

    // 영어 단어를 한글로 변환하는 매핑을 추가합니다.
    private static void addEnglishToKoreanMapping(String english, String korean) {
        englishToKorean.put(english.toLowerCase(), korean);
    }

    // 한글 제목을 영어로 변환하는 매핑을 추가합니다.
    private static void addKoreanToEnglishMapping(String korean, String english) {
        koreanToEnglish.put(korean, english);
    }

    // 발음 변형 매핑을 추가합니다.
    private static void addPhoneticVariation(String phonetic, String target) {
        phoneticVariations.put(phonetic.toLowerCase(), target);
    }

    // 영어 단어를 한글 제목으로 변환합니다.
    public static String convertEnglishToKorean(String englishWord) {
        return englishToKorean.getOrDefault(englishWord.toLowerCase(), englishWord);
    }

    // 한글 제목을 영어 제목으로 변환합니다.
    public static String convertKoreanToEnglish(String koreanWord) {
        return koreanToEnglish.getOrDefault(koreanWord, koreanWord);
    }

    // 발음 변형을 고려하여 변환합니다.
    public static String getPhoneticVariation(String input) {
        return phoneticVariations.getOrDefault(input.toLowerCase(), input);
    }
}
