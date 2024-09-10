document.addEventListener('DOMContentLoaded', () => {
    const inputEmail = document.querySelector('input#email');
    inputEmail.addEventListener('change', checkEmail);


    function checkEmail(event) {
        const email_pattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;

        if (event.target.value === '') {
            emailChecked = false;
        }
        
        // 이메일 입력 필드의 텍스트가 변경될 때마다 오류 메시지를 지웁니다.
        // 수정된 부분: 입력 필드의 값이 변경되면 오류 메시지를 지우고 상태를 확인합니다.
        event.target.addEventListener('input', () => {
            checkEmailResult.innerHTML = ''; // 오류 메시지를 지웁니다.
        });

        const email = inputEmail.value;

        // 이메일 형식 체크
        if(!email.match(email_pattern)) {
            checkEmailResult.innerHTML = '이메일 형식이 올바르지 않습니다.';
            checkEmailResult.classList.add('text-danger');
            checkEmailResult.classList.remove('text-success');
            return;
        } else {
            checkEmailResult.innerHTML = '';
            checkEmailResult.classList.remove('text-danger');
        }
    }

});
