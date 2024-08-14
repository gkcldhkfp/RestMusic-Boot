/**
 * artist 폴더의 albums.html에 포함
 */
document.addEventListener('DOMContentLoaded', () => {
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
        });
        
});