package com.itwill.rest.util;

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
}
