/**
 * /member/deactivateUser.html에 포함
 */

document.addEventListener('DOMContentLoaded', () => {
    console.log('회원탈퇴 페이지');
    // 회원 탈퇴 버튼 클릭 이벤트
    document.getElementById('deactivateButton').addEventListener('click', () => {
        const password = document.getElementById('password').value.trim();
        const confirmPassword = document.getElementById('confirmPassword').value.trim();
        const id = document.getElementById('id').value; // hidden 필드에서 사용자 ID 가져오기

        // 비밀번호와 비밀번호 확인이 일치하는지 확인
        if (password !== confirmPassword) {
            alert('비밀번호가 일치하지 않습니다.');
            return;
        }

        // 비밀번호 입력 확인
        if (!password) {
            alert('비밀번호를 입력해주세요.');
            return;
        }

        // 확인을 위한 사용자 입력
        if (!confirm('정말로 회원 탈퇴를 진행하시겠습니까?')) {
            return;
        }

        // axios를 사용하여 서버에 요청
        axios.post('/member/deactivateUser', {
            id: id,
            password: password
        })
        .then(response => {
            alert(response.data); // 응답 데이터의 message 필드로 가정
            location.href = '/home'; // 메인 페이지로 리다이렉트
        })
        .catch(error => {
            alert(error.response.data || '알 수 없는 오류가 발생했습니다.'); // 오류 메시지 표시
        });
    });
});

