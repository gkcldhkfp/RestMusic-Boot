/**
 * 
 */

 // finduserpassword.js

document.addEventListener('DOMContentLoaded', function() {
    const hintQuestionSelect = document.getElementById('hintQuestionSelect');
    const hintQuestionInput = document.getElementById('hintQuestion');
    
    // 힌트 질문 선택 시 관련 입력 필드 표시
    hintQuestionSelect.addEventListener('change', function() {
        if (this.value === '') {
            hintQuestionInput.classList.add('d-none');
        } else {
            hintQuestionInput.classList.remove('d-none');
        }
    });

    // 비밀번호 및 확인 비밀번호 필드 유효성 검사
    const passwordField = document.getElementById('password');
    const confirmPasswordField = document.getElementById('confirmPassword');
    const checkPasswordResult = document.getElementById('checkPasswordResult');
    const submitButton = document.getElementById('btnSave');

    function validatePasswords() {
        if (passwordField.value === confirmPasswordField.value) {
            checkPasswordResult.textContent = '';
            submitButton.disabled = false;
        } else {
            checkPasswordResult.textContent = '비밀번호가 일치하지 않습니다.';
            submitButton.disabled = true;
        }
    }

    // 비밀번호 입력 및 확인 비밀번호 입력 시 유효성 검사
    passwordField.addEventListener('input', validatePasswords);
    confirmPasswordField.addEventListener('input', validatePasswords);
});
