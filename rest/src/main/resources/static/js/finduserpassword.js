/*
 * findpassword.jsp에 포함
 */

document.addEventListener('DOMContentLoaded', () => {
    /// 아이디 입력 요소와 아이디 검증 결과를 표시할 요소를 선택합니다.
    const inputUserid = document.querySelector('input#userId'); // 아이디 입력 요소
    const checkUseridResult = document.getElementById('checkUseridResult'); // 아이디 검증 결과를 보여줄 요소
    inputUserid.addEventListener('change', checkUserid); // 아이디 입력 값이 변경되었을 때 checkUserid 함수 호출
    
    // 이메일 입력 요소와 이메일 검증 결과를 표시할 요소를 선택합니다.
    const inputEmail = document.querySelector('input#email');
    const checkEmailResult = document.getElementById('checkEmailResult'); // 이메일 검증 결과를 보여줄 요소
    inputEmail.addEventListener('change', checkEmail); // 이메일 입력 값이 변경되었을 때 checkEmail 함수 호출
        
    // 힌트 질문과 답변 관련 입력 요소들을 선택합니다.
    const hintQuestionInput = document.getElementById('hintQuestion');
    const hintQuestionSelect = document.getElementById('hintQuestionSelect');
    const hintAnswerInput = document.getElementById('hintAnswer');
    
    // 힌트 질문 선택이 변경되었을 때 checkHintQuestion 함수 호출
    hintQuestionSelect.addEventListener('change', checkHintQuestion);
    
    // 힌트 답변 입력이 변경되었을 때 checkHintAnswer 함수 호출
    hintAnswerInput.addEventListener('change', checkHintAnswer);
    
    // 아이디 형식 검증 함수
    function checkUserid(event) {
        const userid = inputUserid.value;

        // 아이디 검증 결과를 보여줄 요소를 선택합니다.
        const checkUseridResult = document.querySelector('div#checkUseridResult');
        
        // 아이디 입력 필드의 텍스트가 변경될 때마다 오류 메시지를 지웁니다.
        // 수정된 부분: 입력 필드의 값이 변경되면 오류 메시지를 지우고 상태를 확인합니다.
        event.target.addEventListener('input', () => {
            checkUseridResult.innerHTML = ''; // 오류 메시지를 지웁니다.
        });

        // 아이디 입력 값이 빈 경우 에러 메시지 표시
        if (userid === '') {
            useridChecked = false;
            checkUseridResult.innerHTML = '아이디를 입력해주세요.';
            checkUseridResult.classList.add('text-danger');
            checkUseridResult.classList.remove('text-success');
            return;
        }

        // 아이디 형식이 올바르지 않은 경우 에러 메시지 표시
        if (!/^[a-z0-9]{5,20}$/.test(userid)) {
            useridChecked = false;
            checkUseridResult.innerHTML = '아이디는 5~20자의 영문 소문자와 숫자만 사용 가능합니다.';
            checkUseridResult.classList.add('text-danger');
            checkUseridResult.classList.remove('text-success');
            return;
        } else {
            // 아이디 형식이 올바르면 성공 메시지 표시
            checkUseridResult.innerHTML = '';
            checkUseridResult.classList.remove('text-danger');
            checkUseridResult.classList.add('text-success');
        }
    }
    
    // 이메일 형식 검증 함수
    function checkEmail(event) {
        const email_pattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
        
        // 이메일 입력 필드의 텍스트가 변경될 때마다 오류 메시지를 지웁니다.
        // 수정된 부분: 입력 필드의 값이 변경되면 오류 메시지를 지우고 상태를 확인합니다.
        event.target.addEventListener('input', () => {
            checkEmailResult.innerHTML = ''; // 오류 메시지를 지웁니다.
        });

        // 이메일 입력 값이 빈 경우 에러 메시지 표시
        if (event.target.value === '') {
            checkEmailResult.innerHTML = '이메일을 입력해주세요.';
            checkEmailResult.classList.add('text-danger');
            checkEmailResult.classList.remove('text-success');
            return;
        }

        const email = inputEmail.value;

        // 이메일 형식이 올바르지 않은 경우 에러 메시지 표시
        if (!email.match(email_pattern)) {
            checkEmailResult.innerHTML = '이메일 형식이 올바르지 않습니다.';
            checkEmailResult.classList.add('text-danger');
            checkEmailResult.classList.remove('text-success');
            return;
        } else {
            // 이메일 형식이 올바르면 성공 메시지 표시
            checkEmailResult.innerHTML = '';
            checkEmailResult.classList.remove('text-danger');
            checkEmailResult.classList.add('text-success');
        }
    }
    
    // 힌트 질문 선택 시 선택된 질문을 힌트 질문 입력 요소에 설정합니다.
    function checkHintQuestion(event) {
        if (event.target.value === '') {
            hintQuestionInput.value = 'null'; // 선택되지 않은 경우 'null' 문자열을 기본값으로 설정
        } else {
            const selectedText = hintQuestionSelect.options[hintQuestionSelect.selectedIndex].text;
            hintQuestionInput.value = selectedText; // 선택된 질문을 힌트 질문 입력 요소에 설정
        }
    }
    
    // 힌트 답변 입력 시 공백이면 'null' 문자열을 기본값으로 설정합니다.
    function checkHintAnswer(event) {
        if (event.target.value.trim() === '') {
            hintAnswerInput.value = 'null'; // 빈 입력 값일 경우 'null' 문자열을 기본값으로 설정
        }
    }
    
    // 폼 제출 시 빈 힌트 질문과 힌트 답변을 'null' 문자열로 설정
    document.querySelector('form').addEventListener('submit', function(event) {
        const hintQuestion = hintQuestionInput.value;
        const hintAnswer = hintAnswerInput.value.trim();

        // 힌트 질문이 빈 경우 'null' 문자열로 설정
        if (hintQuestion === '') {
            hintQuestionInput.value = 'null';
        }
        // 힌트 답변이 빈 경우 'null' 문자열로 설정
        if (hintAnswer === '') {
            hintAnswerInput.value = 'null';
        }
    });
});
