/**
 * /member/update.html에 포함
 */

document.addEventListener('DOMContentLoaded', () => {
    // 프로필 변경 모달 객체 생성
    const changeProfileModal = new bootstrap.Modal(document.getElementById('changeProfileModal'));

    // '변경' 버튼 클릭 시 프로필 이미지 업데이트 요청
    document.getElementById('btnChangeProfile').addEventListener('click', (event) => {
        event.preventDefault(); // 폼 제출 방지
        
        const profileImageInput = document.getElementById('profileImageInput');
        const hiddenUserId = document.getElementById('hiddenUserId');
        const profileImagePreview = document.getElementById('profileImagePreview');
    
        if (profileImageInput && hiddenUserId) {
            const file = profileImageInput.files[0];
            const userId = hiddenUserId.value;
    
            if (file) {
                const formData = new FormData();
                formData.append('userId', userId);
                formData.append('profileImage', file);
    
                axios.post('/member/updateProfileImage', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                })
                .then(response => {
                    console.log(response);
                    if (response.data.success) {
                        alert('프로필 이미지가 성공적으로 변경되었습니다!');
                        
                        // 이미지 미리보기 업데이트
                        const imageUrl = response.data.imageUrl;
                        profileImagePreview.src = imageUrl;
                        
                        // 모달 창 닫기
                        changeProfileModal.hide();
                    } else {
                        alert('프로필 이미지 변경에 실패했습니다.');
                    }
                })
                .catch(error => {
                    console.error('프로필 이미지 변경 중 오류 발생:', error);
                    alert('이미지 업로드 중 오류가 발생했습니다.');
                });
            } else {
                alert('이미지를 선택해 주세요.');
            }
        } else {
            console.error('Required elements are missing in the HTML.');
        }
    });
    
    // 파일 선택 시 미리보기 업데이트
    document.getElementById('profileImageInput').addEventListener('change', (event) => {
        const file = event.target.files[0];
        const profileImagePreview = document.getElementById('profileImagePreview');
        
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                profileImagePreview.src = e.target.result;
            }
            reader.readAsDataURL(file);
        }
    });
    
    // '삭제' 버튼 클릭 시 프로필 이미지 삭제 요청
    document.getElementById('btnDeleteProfile').addEventListener('click', () => {
        const hiddenUserId = document.getElementById('hiddenUserId');
        const profileImagePreview = document.getElementById('profileImagePreview');
        const defaultImageUrl = '/images/profileimage/profile.jpg'; // 기본 이미지 URL
    
        if (hiddenUserId) {
            const userId = hiddenUserId.value;
    
            axios.delete(`/member/deleteProfileImage/${userId}`)
                .then(response => {
                    if (response.data.success) {
                        alert('프로필 이미지가 성공적으로 삭제되었습니다!');
                        profileImagePreview.src = defaultImageUrl; // 기본 이미지로 변경
                    } else {
                        alert('프로필 이미지 삭제에 실패했습니다.');
                    }
                })
                .catch(error => {
                    console.error('프로필 이미지 삭제 중 오류 발생:', error);
                    alert('이미지 삭제 중 오류가 발생했습니다.');
                });
        } else {
            console.error('Required elements are missing in the HTML.');
        }
    });
    
    // 초기 상태 변수 설정
    let nicknameChecked = false; // 닉네임 중복 검사 상태
    let emailChecked = false; // 이메일 검증 상태
    let authNumberChecked = false; // 인증번호 검증 상태
    let passwordChecked = false; // 비밀번호 유효성 검사 상태
    let confirmPasswordChecked = false; // 비밀번호 확인 상태
    let hintAnswerChecked = true; // 힌트 답변 유효성 검사 상태
    btnModify.disabled = true; // 회원 정보 변경 비활성화
    
    // 버튼 활성화/비활성화 상태 변경 함수
    function changeButtonState() {
        mailCheckBtn.disabled = !emailChecked;  // 이메일 검증이 완료되었는지에 따라 '인증번호 발송' 버튼 활성화/비활성화
        verifyCodeBtn.disabled = !emailChecked || emailVerificationCodeInput.value.length !== 6;  // 이메일 검증과 인증번호 입력 여부에 따라 '인증번호 확인' 버튼 활성화/비활성화
    }
    
    // '회원 정보 변경' 버튼의 활성화/비활성화 상태를 변경
    function changeModifyButtonState() {
        const btnModify = document.getElementById('btnModify');
              
        // 모든 필요한 필드가 유효한지 확인
        const isFormValid = nicknameChecked && 
                            emailChecked && 
                            authNumberChecked && 
                            passwordChecked &&
                            confirmPasswordChecked &&
                            hintAnswerChecked;
    
        // 버튼 상태 변경
        if (isFormValid) {
            btnModify.classList.remove('disabled');
            btnModify.disabled = false;
        } else {
            btnModify.classList.add('disabled');
            btnModify.disabled = true;
        }
    }
     
    // 닉네임 관련 요소 선택
    // let nicknameChecked = false;   // 닉네임 중복 검사 상태
    const nicknameInput = document.getElementById('nickname');  // 닉네임 입력 필드
    const checkNicknameResult = document.getElementById('checkNicknameResult');  // 닉네임 유효성 검사 결과 표시 요소

    // 닉네임 유효성 검사 함수
    function validateNickname(nickname) {
        // 기본 유효성 검사: 닉네임은 2~20자 사이의 영문, 숫자, 한글만 허용
        const nicknamePattern = /^[a-zA-Z0-9가-힣]{2,20}$/;
        return nicknamePattern.test(nickname);
    }

    // 닉네임 입력 필드의 값이 변경될 때마다 유효성 검사 및 중복 확인
    nicknameInput.addEventListener('change', function(event) {
        const nickname = this.value.trim();  // 닉네임 입력 값 가져오기
        const currentUserNickname = this.getAttribute('value'); // 현재 사용자의 닉네임
        
        // 닉네임 입력 필드의 값이 변경될 때마다 오류 메시지를 지우고 상태를 확인합니다.
        event.target.addEventListener('input', () => {
            checkNicknameResult.innerHTML = ''; // 오류 메시지를 지웁니다.
        });

        if (nickname === '') {
            // 닉네임이 비어있는 경우
            checkNicknameResult.textContent = '';
            checkNicknameResult.className = 'form-text';
            nicknameChecked = false;
            changeModifyButtonState();
            return;
        }
        
        // 현재 사용자의 닉네임과 동일한 경우
        if (nickname === currentUserNickname) {
            nicknameChecked = true;
            checkNicknameResult.textContent = '사용 가능한 닉네임입니다.';
            checkNicknameResult.className = 'form-text text-success';
            changeButtonState();
            changeModifyButtonState();
            return;
        }

        if (!validateNickname(nickname)) {
            // 닉네임 형식이 올바르지 않은 경우
            checkNicknameResult.textContent = '닉네임은 2~20자의 영문, 숫자, 한글만 사용 가능합니다';
            checkNicknameResult.className = 'form-text text-danger';
            nicknameChecked = false;
            changeModifyButtonState();
            return;
        }

        // 서버에 닉네임 중복 확인 요청
        const uri = `/member/checknickname?nickname=${nickname}`;
        axios.get(uri)
            .then((response) => {
                if (response.data === 'Y') {
                    checkNicknameResult.textContent = '사용 가능한 닉네임입니다.';
                    checkNicknameResult.className = 'form-text text-success';
                    // 닉네임 검증 상태를 true로 설정
                    nicknameChecked = true;
                } else {
                    checkNicknameResult.textContent = '이미 사용 중인 닉네임입니다.';
                    checkNicknameResult.className = 'form-text text-danger';
                    // 닉네임 검증 상태를 false로 설정
                    nicknameChecked = false;
                }
                changeModifyButtonState();
            })
            .catch((error) => {
                console.error('닉네임 중복 확인 중 오류 발생:', error);
                checkNicknameResult.textContent = '닉네임 중복 확인 중 오류가 발생했습니다.';
                checkNicknameResult.className = 'form-text text-danger';
                nicknameChecked = false;
                changeModifyButtonState();
            });
    });

    // 이메일 관련 요소 선택
    const emailInput = document.getElementById('email');  // 이메일 입력 필드
    const checkEmailResult = document.getElementById('checkEmailResult');  // 이메일 유효성 검사 결과 표시 요소
    const emailVerificationCodeInput = document.getElementById('emailVerificationCode');  // 이메일 인증번호 입력 필드
    const emailVerificationResult = document.getElementById('emailVerificationResult');  // 이메일 인증 결과 표시 요소
    const mailCheckBtn = document.getElementById('mailCheckBtn');  // 이메일 인증번호 발송 버튼
    const verifyCodeBtn = document.getElementById('verifyCodeBtn');  // 이메일 인증번호 확인 버튼

    let authNumber = '';  // 서버에서 받은 인증번호를 저장할 변수
    mailCheckBtn.disabled =  true;
    verifyCodeBtn.disabled = true;

    // 이메일 유효성 검사 함수
    function validateEmail(email) {
        const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
        return emailPattern.test(email);
    }
    
    // 이메일 입력 필드의 값이 변경될 때마다 유효성 검사 및 중복 확인
    emailInput.addEventListener('change', function(event) {
        const email = this.value.trim();  // 이메일 입력 값 가져오기
        const currentUserEmail = this.getAttribute('value'); // 현재 사용자의 이메일
        
        // 이메일 입력 필드의 값이 변경될 때마다 오류 메시지를 지우고 상태를 확인합니다.
        event.target.addEventListener('input', () => {
            checkEmailResult.innerHTML = ''; // 오류 메시지를 지웁니다.
        });

        if (email === '') {
            // 이메일이 비어있는 경우
            emailChecked = false;
            checkEmailResult.textContent = '이메일을 입력해주세요.';
            checkEmailResult.className = 'form-text text-danger';
            changeButtonState();
            changeModifyButtonState();
            return;
        }

        if (!validateEmail(email)) {
            // 이메일 형식이 올바르지 않은 경우
            emailChecked = false;
            checkEmailResult.textContent = '이메일 형식이 올바르지 않습니다.';
            checkEmailResult.className = 'form-text text-danger';
            changeButtonState();
            changeModifyButtonState();
            return;
        }
        
        // 현재 사용자의 이메일과 동일한 경우
        if (email == currentUserEmail) {
            emailChecked = true;
            checkEmailResult.textContent = '사용 가능한 이메일입니다.';
            checkEmailResult.className = 'form-text text-success';
            changeButtonState();
            changeModifyButtonState();
            return;
        }

        // 서버에 이메일 중복 확인 요청
        const uri = `/member/checkemail?email=${email}`;
        axios.get(uri)
            .then((response) => {
                if (response.data === 'Y') {
                    emailChecked = true;
                    checkEmailResult.textContent = '사용 가능한 이메일입니다.';
                    checkEmailResult.className = 'form-text text-success';
                } else {
                    emailChecked = false;
                    checkEmailResult.textContent = '이미 사용 중인 이메일입니다.';
                    checkEmailResult.className = 'form-text text-danger';
                }
                changeButtonState();  // 버튼 상태 업데이트
                changeModifyButtonState();
            })
            .catch((error) => {
                console.error('이메일 중복 확인 중 오류 발생:', error);
                emailChecked = false;
                checkEmailResult.textContent = '이메일 중복 확인 중 오류가 발생했습니다.';
                checkEmailResult.className = 'form-text text-danger';
                changeButtonState();  // 버튼 상태 업데이트
                changeModifyButtonState();
            });
    });

    // 이메일 인증번호 발송 함수
    mailCheckBtn.addEventListener('click', function() {
        const email = emailInput.value.trim();
        if (email && emailChecked) {
            axios.get('/member/sendEmailAuth', { params: { email: email } })
                .then((response) => {
                    if (response.data) {
                        authNumber = response.data;  // 서버에서 받은 인증번호를 전역 변수에 저장
                        emailVerificationCodeInput.disabled = false;  // 인증번호 입력 필드를 활성화
                        emailVerificationCodeInput.addEventListener('input', changeButtonState);  // 입력 값 변경 시 버튼 상태 업데이트
                        verifyCodeBtn.disabled = true;  // 인증번호 확인 버튼을 비활성화
                        alert('인증번호가 이메일로 발송되었습니다.');
                    } else {
                        alert('이메일 발송에 실패했습니다.');
                    }
                })
                .catch((error) => {
                    console.error('이메일 발송 중 오류 발생:', error);
                    alert('이메일 발송 중 오류가 발생했습니다.');
                });
        } else {
            alert('이메일을 입력해주세요.');
        }
    });

    // 인증번호 확인 함수
    verifyCodeBtn.addEventListener('click', function() {
        const inputAuthNumber = emailVerificationCodeInput.value.trim();  // 입력된 인증번호 가져오기
        if (inputAuthNumber == authNumber) {
            authNumberChecked = true;
            emailVerificationResult.textContent = '인증번호가 일치합니다.';
            emailVerificationResult.className = 'form-text text-success';
        } else {
            authNumberChecked = false;
            emailVerificationResult.textContent = '인증번호가 일치하지 않습니다.';
            emailVerificationResult.className = 'form-text text-danger';
        }
        changeButtonState();  // 버튼 상태 업데이트
        changeModifyButtonState();
    });

    // 비밀번호 입력 필드의 input 이벤트 리스너
    document.getElementById('password').addEventListener('change', checkPassword);
    
    // 비밀번호 유효성 검사 함수
    function checkPassword(event) {
        const password = event.target.value;
        const checkPasswordResult = document.getElementById('checkPasswordResult');  // 비밀번호 유효성 검사 결과 표시 요소
        const passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,30}$/;  // 비밀번호 패턴: 8자 이상의 영문 대/소문자와 숫자
        
        // 비밀번호 필드가 비어있는 경우
        if (password === '') {
            passwordChecked = false;
            checkPasswordResult.innerHTML = '비밀번호를 입력해주세요.';
            checkPasswordResult.classList.add('text-danger');
            checkPasswordResult.classList.remove('text-success');
        } else if (!passwordPattern.test(password)) {
            // 비밀번호 패턴이 유효하지 않은 경우
            passwordChecked = false;
            checkPasswordResult.innerHTML = '비밀번호는 8자~30자의 영문 대/소문자와 숫자를 포함해야 합니다.';
            checkPasswordResult.classList.add('text-danger');
            checkPasswordResult.classList.remove('text-success');
        } else {
            // 비밀번호 패턴이 유효한 경우
            passwordChecked = true;
            checkPasswordResult.innerHTML = '';
            checkPasswordResult.classList.remove('text-danger');
        }
        changeModifyButtonState();
    }
    
    // 비밀번호 확인 입력 필드의 input 이벤트 리스너
    document.getElementById('confirmPassword').addEventListener('change', checkConfirmPassword);
    
    // 비밀번호 확인 유효성 검사 함수
    function checkConfirmPassword(event) {
        const password = document.querySelector('input#password').value;
        const confirmPassword = event.target.value;
        const checkPasswordResult = document.querySelector('div#checkPasswordResult'); 
        const passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,30}$/;
        
        // 비밀번호 확인 입력 필드의 값이 변경될 때마다 오류 메시지를 지우고 상태를 확인합니다.
        event.target.addEventListener('input', () => {
            checkPasswordResult.innerHTML = ''; // 오류 메시지를 지웁니다.
        });

        
        // 비밀번호 확인 필드가 비어있는 경우
        if (confirmPassword === '') {
            confirmPasswordChecked = false;
            checkPasswordResult.innerHTML = '비밀번호를 입력해주세요.';
            checkPasswordResult.classList.add('text-danger');
            checkPasswordResult.classList.remove('text-success');
        } else if (!passwordPattern.test(password)) {
            // 비밀번호 패턴이 유효하지 않은 경우
            passwordChecked = false;
            confirmPasswordChecked = false;
            checkPasswordResult.innerHTML = '비밀번호는 8자~30자의 영문 대/소문자와 숫자를 포함해야 합니다.';
            checkPasswordResult.classList.add('text-danger');
            checkPasswordResult.classList.remove('text-success');
            return;
        } else if (password !== confirmPassword) {
            // 비밀번호와 비밀번호 확인이 일치하지 않는 경우
            confirmPasswordChecked = false;
            checkPasswordResult.innerHTML = '비밀번호가 일치하지 않습니다.';
            checkPasswordResult.classList.add('text-danger');
            checkPasswordResult.classList.remove('text-success');
            return;
        } else {
            // 비밀번호 패턴이 유효하고 비밀번호와 비밀번호 확인이 일치하는 경우
            passwordChecked = true;
            confirmPasswordChecked = true;
            checkPasswordResult.innerHTML = '비밀번호가 일치합니다.';
            checkPasswordResult.classList.add('text-success');
            checkPasswordResult.classList.remove('text-danger');
        }
        changeModifyButtonState();
    }
    
    // 힌트 질문 관련 로직
    const hintQuestionInput = document.getElementById('hintQuestion');  // 힌트 질문 입력 필드
    const hintQuestionSelect = document.getElementById('hintQuestionSelect');  // 힌트 질문 선택 필드
    const hintAnswerInput = document.getElementById('hintAnswer');  // 힌트 답변 입력 필드

    // 기존 힌트 질문이 있는 경우
    if (hintQuestionInput.value) {
        const option = Array.from(hintQuestionSelect.options).find(opt => opt.value === hintQuestionInput.value);
        if (option) {
            // 기존 힌트 질문이 선택지에 있는 경우
            option.selected = true;
        } else {
            // 기존 힌트 질문이 선택지에 없는 경우, 새 옵션 추가
            const newOption = new Option(hintQuestionInput.value, hintQuestionInput.value);
            hintQuestionSelect.add(newOption);
            newOption.selected = true;
        }
    }
    
    // 힌트 질문 선택 이벤트
    function checkHintQuestion(event) {
        if (event.target.value === '') {
            hintQuestionInput.value = 'null';  // 기본값 설정
        } else {
            const selectedText = hintQuestionSelect.options[hintQuestionSelect.selectedIndex].text;
            hintQuestionInput.value = selectedText;
        }
    }
    hintQuestionSelect.addEventListener('change', checkHintQuestion);

    // 힌트 답변 유효성 검사 및 기본값 설정 함수
    function checkHintAnswer(event) {
        const hintAnswerInput = event.target;
        const checkHintAnswerResult = document.querySelector('div#checkHintAnswerResult');
        
        // 입력 필드의 텍스트가 변경될 때마다 오류 메시지를 지웁니다.
        hintAnswerInput.addEventListener('input', () => {
            checkHintAnswerResult.innerHTML = ''; // 오류 메시지를 지웁니다.
            checkHintAnswerResult.classList.remove('text-danger');
        });
    
        // 입력 필드가 비어있으면 기본값을 'null'로 설정합니다.
        if (hintAnswerInput.value.trim() === '') {
            hintAnswerInput.value = 'null'; // 기본값 설정
            return;
        }
        
        // 힌트 답변이 0~50자 범위 내의 영문, 숫자, 한글만 포함하는지 검사합니다.
        if (!/^[a-zA-Z0-9가-힣]{0,50}$/.test(hintAnswerInput.value)) {
            checkHintAnswerResult.innerHTML = '힌트 답변은 50자 이하의 영문, 숫자, 한글만 사용 가능합니다.';
            checkHintAnswerResult.classList.add('text-danger');
            checkHintAnswerResult.classList.remove('text-success');
            hintAnswerChecked = false;
            changeModifyButtonState();
        } else {
            checkHintAnswerResult.innerHTML = ''; // 유효한 입력일 경우 오류 메시지 제거
            hintAnswerChecked = true;
            changeModifyButtonState()
        }
    }
    
    // 이벤트 리스너를 추가하여 입력 필드가 변경될 때마다 유효성 검사를 실행합니다.
    const hintAnswerInputField = document.querySelector('#hintAnswer');
    hintAnswerInputField.addEventListener('input', checkHintAnswer);
    
    // '회원 정보 변경' 버튼 클릭 시 사용자 정보 업데이트 요청
    document.getElementById('updateUserForm').addEventListener('submit', (event) => {
        event.preventDefault();  // 기본 폼 제출 동작을 방지합니다.

        const formData = new FormData(event.target);  // 폼 데이터 객체 생성
        
        const profileImageInput = document.getElementById('profileImageInput');
        const file = profileImageInput.files[0];  // 선택된 파일을 가져옵니다. 
        // 프로필 이미지가 선택된 경우 폼 데이터에 추가  
        if (file) {
            formData.append('profileImage', file);  // 폼 데이터에 프로필 이미지 추가
        }
        
        const nickname = nicknameInput.value.trim();
        if (!validateNickname(nickname)) {
            // 닉네임 형식이 올바르지 않은 경우
            alert('유효하지 않은 닉네임 형식입니다. 2~20자의 영문, 숫자, 한글을 사용해주세요.');
            return;
        }
    
        const email = emailInput.value.trim();
        // 이메일 유효성 재확인
        if (!validateEmail(email)) {
            alert('올바른 이메일 주소를 입력해주세요.');
            return;
        }
        
        const password = document.getElementById('password').value;
        const passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,30}$/;
        // 비밀번호가 입력되었지만 유효하지 않은 경우
        if (password !== '' && !passwordPattern.test(password)) {
            event.preventDefault();
            alert('유효하지 않은 비밀번호 형식입니다. 비밀번호는 8자~30자의 영문 대/소문자와 숫자를 포함해야 합니다.');
            return;
        }
        
        const hintQuestion = hintQuestionInput.value;
        const hintAnswer = hintAnswerInput.value.trim();

        if (hintQuestion === '') {
            hintQuestionInput.value = 'null';  // 기본값 설정
        }
        if (hintAnswer === '') {
            hintAnswerInput.value = 'null';  // 기본값 설정
        }
        
        // 서버에 사용자 정보 업데이트 요청
        axios.post(event.target.action, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
            .then(response => {
                if (response.status === 200) {
                    // 업데이트 성공 시 처리
                    alert('변경 사항이 성공적으로 저장되었습니다!');
                    location.href = "/member/mypage?userId=" + formData.get('userId');  // 업데이트 후 마이페이지로 이동
                }
            })
            .catch(error => {
                // 오류 발생 시 처리
                console.error('변경 사항 저장 중 오류 발생:', error);
                alert('변경 사항 저장 중 오류가 발생했습니다.');
            });
    });
    
    // 페이지 로드 시 초기 버튼 상태 설정
    document.addEventListener('DOMContentLoaded', function() {
        changeModifyButtonState();
    });
    
    // '취소' 버튼 클릭 시 이전 페이지로 이동
    document.querySelector('.btn-cancel').addEventListener('click', () => {
        history.back();
    });
    
    document.querySelector('#updateUserForm').addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            event.preventDefault();  // Enter 키를 눌렀을 때 폼 제출을 방지합니다.
        }
    });
    
});
