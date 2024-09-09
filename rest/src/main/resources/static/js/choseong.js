/**
 * layout/fragments.html에 포함
 */
// DOM이 완전히 로드된 후 실행
document.addEventListener('DOMContentLoaded', function() {
    // DOM 요소 선택: 검색 입력 필드와 추천 목록을 각각 선택
    const searchInput = document.getElementById('searchInput');
    const suggestionList = document.getElementById('suggestionList');

    // 디바운스 함수 정의: 사용자의 입력 이벤트가 연속적으로 발생할 때,
    // 지정한 시간 동안 기다렸다가 마지막 입력 후에만 함수를 실행시키는 기능을 제공.
    function debounce(func, delay) {
        let timeoutId;
        return function (...args) {
            // 이전 타이머를 지우고 새 타이머 설정
            clearTimeout(timeoutId);
            timeoutId = setTimeout(() => func.apply(this, args), delay);
        };
    }

    // 초성 변환 함수: 입력된 한글 문자열을 초성으로 변환.
    // 예를 들어, '강'을 입력하면 'ㄱ'으로 변환하여 초성 검색이 가능하게 함.
    function convertToChoseong(str) {
        // 한글 초성 배열 정의
        const choseong = ['ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'];
        let result = '';
        for (let i = 0; i < str.length; i++) {
            // 입력된 문자의 유니코드 값을 이용하여 초성 추출
            const code = str.charCodeAt(i) - 44032;
            // 한글 범위에 속하는 문자인 경우 초성으로 변환
            if (code > -1 && code < 11172) result += choseong[Math.floor(code / 588)];
            else result += str.charAt(i);  // 한글이 아닌 문자는 그대로 반환
        }
        return result;
    }

    // 검색어 추천 목록 표시 함수: 서버에서 받아온 추천 검색어들을 화면에 리스트 형태로 표시.
    function displaySuggestions(suggestions) {
        // 기존 추천 목록 초기화
        suggestionList.innerHTML = '';
        suggestions.forEach(item => {
            // 각 추천 검색어를 리스트 아이템으로 추가
            const li = document.createElement('li');
            li.className = 'list-group-item';
            li.textContent = `${item.name} (${item.type})`;  // 검색어와 타입을 표시
            // 클릭 시 선택된 검색어를 입력창에 넣고 추천 목록을 닫음
            li.addEventListener('click', () => {
                searchInput.value = item.name;
                suggestionList.style.display = 'none';
            });
            suggestionList.appendChild(li);  // 리스트에 아이템 추가
        });
        // 추천 검색어가 있으면 목록을 표시하고 없으면 숨김
        suggestionList.style.display = suggestions.length > 0 ? 'block' : 'none';
    }

    // API 호출 함수: 입력된 검색어를 서버로 전송하여 추천 검색어 목록을 받아옴.
    async function fetchSuggestions(keyword) {
        try {
            // 서버 API에 keyword 파라미터를 붙여 GET 요청
            const response = await axios.get(`/api/search/suggestions?keyword=${encodeURIComponent(keyword)}`);
            return response.data;  // 서버에서 받은 데이터를 반환
        } catch (error) {
            console.error('Error fetching suggestions:', error);  // 에러 발생 시 콘솔에 로그
            return [];  // 에러가 발생하면 빈 배열을 반환
        }
    }

    // 검색어 입력 처리 함수: 사용자가 입력한 검색어를 바탕으로 추천 검색어를 가져와 화면에 표시.
    const handleInput = debounce(async () => {
        const keyword = searchInput.value.trim();  // 입력값에서 공백 제거
        if (keyword.length > 0) {
            const choseong = convertToChoseong(keyword);  // 입력값을 초성으로 변환
            const suggestions = await fetchSuggestions(choseong);  // 초성 기반으로 추천 검색어 요청
            displaySuggestions(suggestions);  // 받아온 추천 검색어 목록을 화면에 표시
        } else {
            // 검색어가 없으면 추천 목록 숨김
            suggestionList.style.display = 'none';
        }
    }, 300);  // 300ms 딜레이 적용

    // 이벤트 리스너 등록: 사용자가 검색어를 입력할 때마다 handleInput 함수 호출
    searchInput.addEventListener('input', handleInput);

    // 검색창 외부 클릭 시 추천 목록 닫기: 검색창과 추천 목록이 아닌 곳을 클릭하면 추천 목록 숨김
    document.addEventListener('click', (e) => {
        if (!searchInput.contains(e.target) && !suggestionList.contains(e.target)) {
            suggestionList.style.display = 'none';
        }
    });
    
});
