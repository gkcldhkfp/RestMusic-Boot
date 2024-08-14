document.addEventListener('DOMContentLoaded', () => {
    const saveIdYn = document.getElementById('saveIdYn');
    // saveIdYn.addEventListener('change', saveId);

    const inputUserid = document.querySelector('input#userId');

    document.getElementById("loginForm").addEventListener('submit', saveId);

    getCookieId();
    
    // URL 파라미터에서 'result' 값을 가져와서 처리하는 함수
    handleResultParameter();
    
    /* -------------------- 함수 선언 -------------------- */

    // 아이디저장(쿠키설정)
    function saveId(event) {

        const is_checked = saveIdYn.checked;
        const userId = inputUserid.value;

        if (is_checked) {
            document.cookie =`userId=${userId}; path=/; max-age=3600`;
        } else {
            document.cookie = `userId=${userId}; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
        }

    }

    // 쿠키아이디 조회
    function getCookieId() {
        const cookieName = "userId=";
        let cookieData = document.cookie;

        let cookieValue = "";
        let start = cookieData.indexOf(cookieName);

        if (start !== -1) {
            start += cookieName.length;
            let end = cookieData.indexOf(";", start);
            if (end === -1) end = cookieData.length;
            cookieValue = cookieData.substring(start, end);

            inputUserid.value = cookieValue;
            saveIdYn.checked = true;

        }
    }
    
    // URL 파라미터 'result'를 처리하여 사용자에게 알림
    function handleResultParameter() {
        const urlParams = new URLSearchParams(window.location.search);  // URL 쿼리 파라미터 가져오기
        const result = urlParams.get('result');  // 'result' 파라미터 값 가져오기

        if (result) {  // 'result' 파라미터가 존재하는 경우
            if (result === 'inactive') {
                alert("해당 계정은 탈퇴된 상태입니다.");  // 계정 비활성화 알림
            } else if (result === 'deactivated') {
                alert("해당 계정으로 30일 동안 로그인할 수 없습니다.");  // 계정 비활성화 기간 알림
            } else if (result === 'f') {
                /*alert("아이디 또는 비밀번호가 일치하지 않습니다."); */ // 아이디/비밀번호 불일치 알림
            }
        }
    }
    
});
