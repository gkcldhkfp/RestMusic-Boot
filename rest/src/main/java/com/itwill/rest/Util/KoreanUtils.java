package com.itwill.rest.Util;

import java.util.HashMap;
import java.util.Map;

public class KoreanUtils {
	// 초성 배열
	private static final char[] CHOSUNG = {
			'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
	};

	// 문자가 한글인 지 검사하는 메서드
	public static boolean isHangul(char c) {
		return c >= '\uAC00' && c <= '\uD7A3';
	}

	public static String getInitialSound(String str) {
		StringBuilder sb = new StringBuilder();
		for (char ch : str.toCharArray()) {
			if (isHangul(ch)) {
				int unicode = ch - 0xAC00;
				int chosungIndex = unicode / (21 * 28); // 초성 계산
				sb.append(CHOSUNG[chosungIndex]);
			} else {
				// 한글이 아닌 경우 처리 (예: 영문, 숫자 등)
				sb.append(ch);
			}
		}
		return sb.toString();
	}
	
	// QWERTY 키보드 영문자를 한글 자모로 매핑하는 사전
    private static final Map<String, String> QWERTY_TO_HANGUL = new HashMap<>();
    static {
        QWERTY_TO_HANGUL.put("r", "ㄱ");
        QWERTY_TO_HANGUL.put("R", "ㄲ");
        QWERTY_TO_HANGUL.put("s", "ㄴ");
        QWERTY_TO_HANGUL.put("e", "ㄷ");
        QWERTY_TO_HANGUL.put("E", "ㄸ");
        QWERTY_TO_HANGUL.put("f", "ㄹ");
        QWERTY_TO_HANGUL.put("a", "ㅁ");
        QWERTY_TO_HANGUL.put("q", "ㅂ");
        QWERTY_TO_HANGUL.put("Q", "ㅃ");
        QWERTY_TO_HANGUL.put("t", "ㅅ");
        QWERTY_TO_HANGUL.put("T", "ㅆ");
        QWERTY_TO_HANGUL.put("d", "ㅇ");
        QWERTY_TO_HANGUL.put("w", "ㅈ");
        QWERTY_TO_HANGUL.put("W", "ㅉ");
        QWERTY_TO_HANGUL.put("c", "ㅊ");
        QWERTY_TO_HANGUL.put("z", "ㅋ");
        QWERTY_TO_HANGUL.put("x", "ㅌ");
        QWERTY_TO_HANGUL.put("v", "ㅍ");
        QWERTY_TO_HANGUL.put("g", "ㅎ");
        QWERTY_TO_HANGUL.put("k", "ㅏ");
        QWERTY_TO_HANGUL.put("o", "ㅐ");
        QWERTY_TO_HANGUL.put("i", "ㅑ");
        QWERTY_TO_HANGUL.put("O", "ㅒ");
        QWERTY_TO_HANGUL.put("j", "ㅓ");
        QWERTY_TO_HANGUL.put("p", "ㅔ");
        QWERTY_TO_HANGUL.put("u", "ㅕ");
        QWERTY_TO_HANGUL.put("P", "ㅖ");
        QWERTY_TO_HANGUL.put("h", "ㅗ");
        QWERTY_TO_HANGUL.put("y", "ㅛ");
        QWERTY_TO_HANGUL.put("n", "ㅜ");
        QWERTY_TO_HANGUL.put("b", "ㅠ");
        QWERTY_TO_HANGUL.put("m", "ㅡ");
        QWERTY_TO_HANGUL.put("l", "ㅣ");
    }

    // 사전 기반으로 영어를 한글로 변환하는 메서드
    public static String convertToKorean(String englishTitle) {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("Domino", "도미노");
        mapping.put("That's What I Like", "댓츠 왓 아이 라이크");
        mapping.put("IU", "아이유");
        mapping.put("iu", "아이유");
        mapping.put("Iu", "아이유");
        // 잘못된 키보드 입력의 경우
        mapping.put("dkdldb", "아이유");
        mapping.put("DKDLDB", "아이유");

        return mapping.getOrDefault(englishTitle, englishTitle);
    }

    // QWERTY 키보드로 입력된 영문을 한글 자모로 변환하는 메서드
    public static String convertQwertyToHangul(String input) {
        StringBuilder result = new StringBuilder();
        for (char ch : input.toCharArray()) {
            String hangulChar = QWERTY_TO_HANGUL.get(String.valueOf(ch));
            if (hangulChar != null) {
                result.append(hangulChar);
            } else {
                result.append(ch); // 매핑되지 않는 문자는 그대로 추가
            }
        }
        return result.toString();
    }

    // 사전 기반 변환, 발음 변환, 키보드 잘못 입력된 경우를 모두 포함한 변환 메서드
    public static String convertToKoreanWithQwertySupport(String input) {
        String result = convertToKorean(input); // 먼저 사전에서 변환 시도
        if (result.equals(input)) {
            result = convertQwertyToHangul(input); // 사전에 없으면 QWERTY 변환 시도
        }
        return result;
    }
    
    // 영어 검색어를 한글로 변환하는 메서드
    public static String convertToEnglish(String koreanTitle) {
        Map<String, String> reverseMapping = new HashMap<>();
        reverseMapping.put("도미노", "Domino");
        reverseMapping.put("댓츠 왓 아이 라이크", "That's What I Like");
        reverseMapping.put("아이유", "IU");

        return reverseMapping.getOrDefault(koreanTitle, koreanTitle);
    }
	
}
