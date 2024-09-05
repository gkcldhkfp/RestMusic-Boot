/**
 * /user/mypage.html 에 포함
 */

document.addEventListener('DOMContentLoaded', () => {

    const id = document.querySelector('h3#id').textContent;
    const userId = document.querySelector('h3#userId').textContent;

    const itemsPerPage = 5; // 페이지당 표시할 아이템 수
    let currentPage = 1; // 현재 페이지

    getPlayLists();
    getUserLike();

    function getPlayLists() {
        const uri = `../getPlayList/${id}`;
        console.log(uri);
        axios
            .get(uri)
            .then((response) => {
                console.log(response);
                console.log(response.data);
                makePlayListElements(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
    }
    const btnAddPlaylist = document.querySelector('button#btnAddPlaylist');
    btnAddPlaylist.addEventListener('click', addPlaylist);

    // input#playlistName 엘리먼트에 keydown 이벤트 리스너 추가
    const playlistNameInput = document.querySelector('input#playlistName');
    playlistNameInput.addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            event.preventDefault(); // 엔터 키 기본 동작(폼 제출) 막기
            addPlaylist(); // addPlaylist 함수 호출
        }
    });

    var modal = document.getElementById('addPlaylistModal');
    const bootstrapModal = new bootstrap.Modal(modal);

    function addPlaylist() {
        console.log(id);

        const plistName = document.querySelector('input#playlistName').value;
        console.log(plistName);

        if (plistName === '') {
            alert('플레이리스트 이름을 입력하세요.');
            return;
        }

        if (plistName.length > 20) {
            alert('플레이리스트 이름은 20자 이하여야 합니다.');
            return;
        }

        const data = { id, plistName };
        console.log(data);

        // POST 방식의 Ajax 요청을 보냄. 응답 성공/실패 콜백을 등록.
        axios
            .post('../addPlayList', data)
            .then((response) => {
                // console.log(response);
                console.log(response.data); // RestController에서 보낸 응답 데이터
                alert('플레이리스트를 등록하였습니다!');
                document.querySelector('input#playlistName').value = '';
                getPlayLists();
                bootstrapModal.hide();
            })
            .catch((error) => {
                console.log('서버 오류:', error);
                alert('플레이리스트 추가 중 오류가 발생했습니다.');
            });
        /*        var playlistName = document.getElementById('playlistName').value;
                console.log('추가할 플레이 리스트 제목:', playlistName); // 플레이리스트 이름 콘솔 입력*/
    }

    var cancelButton = document.querySelector('#addPlaylistModal .btn-secondary');
    cancelButton.addEventListener('click', function() {
        bootstrapModal.hide();
    });
    function makePlayListElements(data) {
        // 플리 목록 HTML이 삽입될 div
        const divPlayLists = document.querySelector('div#playLists');

        // 플리 목록 HTML 코드
        let htmlStr = '';
        // 플리 목록을 카운트
        let playlistCount = 0;

        for (let playlist of data) {
            // 기본 이미지 URL 정의
            const defaultImage = '../images/icon/default.png';
            // 삭제 이미지 URL 정의
            const deleteImage = '../images/icon/delete.png';

            // ${playlist.albumImage}가 null이면 기본 이미지 사용
            const albumImageSrc = playlist.albumImage ? `../images/albumcover/${playlist.albumImage}` : defaultImage;

            console.log(playlist);
            htmlStr += `
            <div class="d-flex align-items-center">
            <a class="playList btn btn-outline-success form-control mt-2" data-id="${playlist.plistId}">
                <div class="d-flex align-items-center">
                    <div class="flex-shrink-0">
                        <img src="${albumImageSrc}" alt="..." width="50px" height="50px">
                    </div>
                    <div class="flex-grow-1 ms-3">
                        ${playlist.plistName}
                    </div>
                </div>
            </a>
                <button class="deleteButton btn btn-primary ms-2 mt-2" data-id="${playlist.plistId}"
                style="position: relative; width: 40px; height: 40px; overflow: hidden; background-color:white; border:none;">
                    <img alt="deleteImage" src="${deleteImage}" 
                    style="position: absolute; top: 0; left: 0; width: 100%; height: 100%;">
                </button>
            </div>`;

            playlistCount++;
        }

        // 작성된 HTML 코드를 div 영역에 삽입.
        divPlayLists.innerHTML = htmlStr;

        // 플리가 비어있으면 플레이리스트를 출력하는 부분에 해당 텍스트, defaultListImage 출력.
        const defaultListImage = '../images/icon/myPlayListEmpty.png';
        console.log(playlistCount);
        if (playlistCount == 0) {
            htmlStr += `
                <div class='container' style="text-align: center;">
                    <img src="${defaultListImage}" width="80px" height="80px">
                    <h5 id="defaultList" class="mt-4" style="text-align: center; color:gray; font-size: 16px">
                        추가된 플레이리스트가 없습니다..!
                    </h5>
                </div>
                `
            divPlayLists.innerHTML = htmlStr;
        }

        const deleteList = document.querySelectorAll('button.deleteButton'); // htmlStr로 추가된 html 영역의 button 태그의 클래스 이름을 지정
        for (let button of deleteList) {
            button.addEventListener('click', deletePlayList);
        }

        const aPlayLists = document.querySelectorAll('a.playList');
        for (let a of aPlayLists) {
            a.addEventListener('click', () => {
                const plistId = a.getAttribute('data-id');
                console.log('클릭한 플레이리스트의 plistId:', plistId);
                window.location.href = `../playlists/playlist?plistId=${plistId}`;
            });
        }
    }

    function deletePlayList(event) {

        const plistId = event.currentTarget.getAttribute('data-id');
        console.log('userid = ' + id);
        console.log('plistId = ' + plistId);

        // 삭제 여부 확인
        const result = confirm('해당 플레이리스트를 정말 삭제할까요?');
        if (!result) { // 사용자가 [취소]를 선택했을 때
            return; // 함수 종료
        }
        // Ajax 요청을 보낼 URI
        const uri = `../deletePlayList/${plistId}`;

        axios
            .delete(uri)
            .then((response) => {
                console.log(response.data);
                alert(`플레이 리스트를 삭제하였습니다.`);
                getPlayLists();
            })
            .catch((error) => {
                console.log(error);
            });

    }

    function getUserLike() {
        const uri = `../member/getUserLike/${id}`;
        console.log(uri);
        axios
            .get(uri)
            .then((response) => {
                console.log(response);
                console.log(response.data);
                const { data } = response;
                const totalPages = Math.ceil(data.length / itemsPerPage);
                makeUserLikeElements(data.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage));
                setupPagination(totalPages);
            })
            .catch((error) => {
                console.log(error);
            });
    }

    function makeUserLikeElements(data) {
        // 좋아요 목록 HTML이 삽입될 tbody
        const likeTableBody = document.querySelector('tbody#likeTableBody');
        const likeCardBody = document.querySelector('div#resultTable');

        if (!likeTableBody) {
            console.error('songsTableBody를 찾을 수 없습니다.');
            return;
        }

        // 좋아요 목록 HTML 코드
        let htmlStr = '';
        // 좋아요 목록을 카운트
        let likeCount = 0;

        // 플레이 이미지
        const playImage = '../images/icon/play.png';
        // 재생목록 이미지
        const playlistImage = '../images/icon/playList.png';

        for (let like of data) {
            // 기본 이미지 URL 정의
            const defaultImage = '../images/icon/default.png';

            // albumPage, songPage, artistPage로 이동할 주소 지정
            const albumPage = `/album/detail?albumId=${like.albumId}`;
            const songPage = `/song/detail?songId=${like.songId}`;

            // ${like.albumImage}가 null이면 기본 이미지 사용
            const albumImageSrc = like.albumImage ? `../images/albumcover/${like.albumImage}` : defaultImage;

            // 그룹 네임과 아티스트 네임을 클릭 가능한 링크로 처리
            const groupNames = like.groupName || [];
            const artistNames = like.artistName || [];

            // 그룹 네임을 문자열로 결합
            const groupNamesString = groupNames.map((name, index) => {
                return `<a href="../group/songs?groupId=${like.groupId[index]}" style="color: black; text-decoration: none;"
                        onmouseover="this.style.color='blue';" onmouseout="this.style.color='black';">${name}</a>`;
            }).join(', ');

            // 아티스트 네임을 문자열로 결합
            const artistNamesString = artistNames.map((name, index) => {
                return `<a href="../artist/songs?artistId=${like.artistId[index]}" style="color: black; text-decoration: none;"
                        onmouseover="this.style.color='blue';" onmouseout="this.style.color='black';">${name}</a>`;
            }).join(', ');

            // 그룹과 아티스트 네임을 결합
            let singerLinksHtml = '';
            if (groupNames.length > 0) {
                singerLinksHtml = groupNamesString;
                if (artistNames.length > 0) {
                    singerLinksHtml += ', ' + artistNamesString;
                }
            } else {
                singerLinksHtml = artistNamesString;
            }

            htmlStr += `
                        <tr>
                            <td style="text-align: left; vertical-align: middle;">
                                <a href="${albumPage}">
                                    <img alt="songImg" src="${albumImageSrc}" width="80px" height="80px">
                                </a>
                            </td>
                            <td style="text-align: left; vertical-align: middle; font-size: 14px;" class="fs-6">
                                <a href=${songPage} style="color: black; text-decoration: none;"
                                    onmouseover="this.style.color='blue';" onmouseout="this.style.color='black';">${like.title}</a><br>
                                <span style="display: inline-block; margin-top: 5px;">
                                <a href="${albumPage}" style="color: gray; text-decoration: none;"
                                onmouseover="this.style.color='blue';" onmouseout="this.style.color='gray';">${like.albumName}</a>
                                </span>                    
                            </td>
                            <td style="text-align: left; vertical-align: middle; font-size: 14px;" class="fs-6">
                                ${singerLinksHtml}
                            </td>
                            <td style="text-align: center;">
                                <button style="background-image: url('${playImage}'); width: 40px; height: 40px; background-size: cover; background-repeat: no-repeat;"
                                data-songId="${like.songId}" data-id="${like.songId}" class="playButton btn mt-3" id="listenBtn"></button>
                            </td>
                            <td style="text-align: center;">
                                <button data-id="${like.songId}"
                                    id="addCPList" title="재생목록에 추가" class="btn mt-3" style="background-image: url('${playlistImage}'); 
                                    width: 40px; height: 40px; background-size: cover; background-repeat: no-repeat;"></button>
                            </td>
                        </tr>
                        `;

            likeCount++;
        }

        // 작성된 HTML 코드를 div 영역에 삽입.
        likeTableBody.innerHTML = htmlStr;

        // 좋아요 목록이 비어있으면 목록 출력하는 부분에 해당 텍스트, defaultListImage 출력.
        const defaultListImage = '../images/icon/defaultList.png';
        console.log(likeCount);
        if (likeCount === 0) {
            htmlStr += `
            <div class='container' style="text-align: center;">
                <img src="${defaultListImage}" width="80px" height="80px">
                <h5 id="defaultList" class="mt-4" style="text-align: center; color:gray; font-size: 16px">
                    추가한 좋아요 음원이 없습니다..!
                </h5>
            </div>
            `
            likeCardBody.innerHTML = htmlStr;
        }
    }

    function setupPagination(totalPages) {
        const pagination = document.querySelector('#pagination');
        if (!pagination) {
            console.error('pagination을 찾을 수 없습니다.');
            return;
        }

        pagination.innerHTML = '';

        // 이전 페이지 버튼 추가
        let htmlStr = `<li class="page-item ${currentPage === 1 ? 'disabled' : ''}">
                        <a class="page-link" href="#" onclick="changePage(${currentPage - 1})">이전</a>
                      </li>`;

        // 페이지 번호 버튼 추가
        for (let i = 1; i <= totalPages; i++) {
            htmlStr += `<li class="page-item ${currentPage === i ? 'active' : ''}">
                            <a class="page-link" href="#" onclick="changePage(${i})">${i}</a>
                        </li>`;
        }

        // 다음 페이지 버튼 추가
        htmlStr += `<li class="page-item ${currentPage === totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="#" onclick="changePage(${currentPage + 1})">다음</a>
                    </li>`;

        pagination.innerHTML = htmlStr;
    }

    window.changePage = function(page) {
        currentPage = page;
        getUserLike();
        scrollToUserLikes();
    };

    function scrollToUserLikes() {
        const userLikesSection = document.getElementById('userLikesSection');
        if (userLikesSection) {
            userLikesSection.scrollIntoView({ behavior: 'smooth', block: 'start' });
        }
    }

    // ID/PW 변경 버튼과 모달 창 관련 요소들을 가져옵니다.
    const updateInfoBtn = document.getElementById('updateInfoBtn');
    const passwordConfirmModalElement = document.getElementById('passwordConfirmModal');
    const passwordConfirmModal = new bootstrap.Modal(passwordConfirmModalElement);
    const confirmPasswordBtn = document.getElementById('confirmPasswordBtn');
    const passwordInput = document.getElementById('password');
    const userPassword = document.getElementById('userPassword').value; // userPassword 값 가져오기

    // ID/PW 변경 버튼 클릭 시 비밀번호 확인 모달을 보여줍니다.
    updateInfoBtn.addEventListener('click', function(e) {
        e.preventDefault();
        passwordConfirmModal.show();
    });

    // 엔터 키를 누를 때 아무런 이벤트가 발생하지 않도록 설정합니다.
    passwordInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
        }
    });

    // 확인 버튼 클릭 시 비밀번호 검증을 수행합니다.
    confirmPasswordBtn.addEventListener('click', function() {
        const password = passwordInput.value;
        
        if (password === '') {
            alert('비밀번호를 입력하세요.');
            return;
        }

        // 서버 측 비밀번호 검증 API 호출
        axios.get(`/member/checkPwd?checkPassword=${encodeURIComponent(password)}`)
            .then(response => {
                const isValid = response.data; // 서버에서 반환된 결과 (true 또는 false)
                
                if (isValid) {
                    passwordConfirmModal.hide();
                    location.href = `../member/update?userId=${userId}`;
                } else {
                    alert('비밀번호가 올바르지 않습니다.');
                }
            })
            .catch(error => {
                console.error('서버 오류:', error);
                alert('비밀번호 검증 중 오류가 발생했습니다.');
            });
    });

});
