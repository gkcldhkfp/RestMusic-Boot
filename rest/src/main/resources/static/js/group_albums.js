/**
 * group 폴더의 albums.html에 포함
 */
document.addEventListener('DOMContentLoaded', () => {
    console.log('사용자 ID는 ' + (loginUserId !== null ? loginUserId : '로그인되지 않음'));
    
    // group 좋아요 관련 코드
    const btnLike = document.querySelector('button#btnLike');
    const data = { groupId, id:loginUserId };
    
    if(loginUserId != ''){
    axios
        .post('/api/group/isLiked', data)
        .then((response) => {
            if (response.data) {
                btnLike.textContent = '♥';
            } else {
                btnLike.textContent = '♡';
            }
        }
        )
        .catch((error) => {
            console.log(error);
        });
    } else {
        btnLike.textContent = '♡';
    }
    
    btnLike.addEventListener('click', () => {
    if(loginUserId == null) {
        if(confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")){
            redirectToLogin();
        }
        return;
        }
        axios
            .put('/api/group/like', data)
            .then((response) => {
                if (response.data) {
                    btnLike.textContent = '♥';
                } else {
                    btnLike.textContent = '♡';
                }
            }
            )
            .catch((error) => {
                console.log(error);
            });

    });
    
    // artist 상세정보가 포함된 txt 파일 불러오기.
    // artistDescription 값을 가져옵니다.
    var descriptionFile = document.getElementById('artist-info').getAttribute('data-artist-description');

    // 파일 경로를 생성합니다.
    var filePath = '/artist/description/' + encodeURIComponent(descriptionFile);
    console.log(filePath);

    // axios를 사용하여 파일 내용을 가져옵니다.
    axios.get(filePath)
        .then(function(response) {
            // 파일 내용이 성공적으로 로드되었으면 <p> 요소에 내용을 삽입합니다.
            document.getElementById('artistDescription').innerText = response.data;
        })
        .catch(function(error) {
            // 오류가 발생하면 오류 메시지를 출력합니다.
            console.error('파일을 읽는 중 오류 발생:', error);
            
            // 기본 텍스트를 설정합니다.
            document.getElementById('artistDescription').innerText = '등록된 정보가 없습니다.';
        });
        
});