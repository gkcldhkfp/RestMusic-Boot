/**
 * artist 폴더의 songs.jsp에 포함
 */
document.addEventListener('DOMContentLoaded', () => {
    const btnAddPlayLists = document.querySelectorAll('button.addPlayList');
    const playListModal = new bootstrap.Modal(document.querySelector('div#staticBackdrop3'), { backdrop: 'static' });
    
    let currentPage = 1;
    const itemsPerPage = 5;
    
    for (let a of btnAddPlayLists) {
        a.addEventListener('click', getPlayLists);
    }

    function getPlayLists(event) {
        event.stopPropagation();
        if (loginUserId == '') { // 유저아이디
            alert('로그인이 필요합니다');
            return;
        }
        songId = event.target.closest('button').getAttribute('data-songId');

        const uri = `../getPlayList/${loginUserId}`;
        axios
            .get(uri)
            .then((response) => {

                playlistsData = response.data;

                displayPlayLists(currentPage);

                setupPagination();

                playListModal.show();
            })
            .catch((error) => {
                console.log(error);
            });

    }

    function makePlayListElements(data) {
        // 플리 목록 HTML이 삽입될 div
        const divPlayLists = document.querySelector('div#playLists');

        // 플리 목록 HTML 코드
        let htmlStr = '';
        for (let playlist of data) {
            // 기본 이미지 URL 정의
            const defaultImage = '../images/icon/default.png';

            // ${playlist.albumImage}가 null이면 기본 이미지 사용
            const albumImageSrc = playlist.albumImage ? `../images/albumcover/${playlist.albumImage}` : defaultImage;


            htmlStr += `
            <a class="playList btn btn-outline-success form-control mt-2" data-id="${playlist.plistId}" >
            <div class="d-flex align-items-center">
                <div class="flex-shrink-0">
                    <img src="${albumImageSrc}" alt="..." width="50px" height="50px">
                  </div>
                    <div class="flex-grow-1 ms-3">
                    ${playlist.plistName}
                  </div>
                </div>
            </a>`;
        }

        // 작성된 HTML 코드를 div 영역에 삽입.
        divPlayLists.innerHTML = htmlStr;

        const aPlayLists = document.querySelectorAll('a.playList');
        for (let a of aPlayLists) {
            a.addEventListener('click', addSongPlayList);
        }


    }

    function addSongPlayList(event) {

        const plistId = event.currentTarget.getAttribute('data-id');


        const data = { plistId, songId };

        axios.post('../checkSongInPlayList', data)
            .then((response) => {
                if (!response.data) {
                    if (confirm('이미 추가된 곡입니다. 그래도 추가하시겠습니까?')) {
                        // 사용자가 확인을 눌렀을 때 추가 요청 보냄
                        addToPlayList(data);
                    }
                } else {
                    // 데이터가 없으면 바로 추가 요청 보냄
                    addToPlayList(data);
                }
            })
            .catch((error) => {
                console.log(data);
                console.log(error);
            });

        function addToPlayList(data) {
            axios
                .post('../addSongToPlayList', data)
                .then((response) => {
                    alert(`추가 성공`);
                    playListModal.hide();
                })
                .catch((error) => {
                    console.log(error);
                });
        }

    }

    function displayPlayLists(page) {
        const startIndex = (page - 1) * itemsPerPage;
        const endIndex = startIndex + itemsPerPage;
        const paginatedPlaylists = playlistsData.slice(startIndex, endIndex);
        makePlayListElements(paginatedPlaylists);
    }

    function setupPagination() {
        const totalPages = Math.ceil(playlistsData.length / itemsPerPage);
        const paginationElement = document.getElementById('pagination');
        let paginationHtml = '';

        for (let i = 1; i <= totalPages; i++) {
            if (i === currentPage) {
                paginationHtml += `
                    <li class="page-item active" aria-current="page">
                        <span class="page-link">${i}</span>
                    </li>
                `;
            } else {
                paginationHtml += `
                    <li class="page-item">
                        <a class="page-link" href="#" data-page="${i}">${i}</a>
                    </li>
                `;
            }
        }

        paginationElement.innerHTML = paginationHtml;

        // 기존 이벤트 리스너 제거
        paginationElement.removeEventListener('click', handlePaginationClick);

        // 이벤트 리스너 등록
        paginationElement.addEventListener('click', handlePaginationClick);
    }

    function handlePaginationClick(event) {
        event.preventDefault(); // 기본 동작 방지
        if (event.target.tagName === 'A') {
            const page = parseInt(event.target.getAttribute('data-page'));
            changePage(page);
        }
    }

    function changePage(page) {
        currentPage = page;
        displayPlayLists(currentPage);
        setupPagination(); // 이 부분에서 이벤트 리스너를 다시 등록하지 않아도 됨
    }
    
    // HTML 요소에서 artistDescription 데이터 속성을 가져옵니다.
    const artistInfoElement = document.getElementById('artist-info');
    const artistDescriptionFileName = artistInfoElement ? artistInfoElement.getAttribute('data-artist-description') : '';

    if (artistDescriptionFileName) {
        // 파일 경로를 설정합니다. .txt 확장자가 이미 포함되어 있을 경우
        const filePath = `/artist/description/${encodeURIComponent(artistDescriptionFileName)}`;
        console.log('파일 경로:', filePath);

        // Fetch API를 사용하여 파일을 읽습니다.
        fetch(filePath)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`파일을 읽을 수 없습니다. 상태 코드: ${response.status}`);
                }
                return response.text();
            })
            .then(data => {
                document.getElementById('artistDescription').innerHTML = data;
            })
            .catch(error => {
                document.getElementById('artistDescription').innerHTML = '파일을 읽는 도중 오류가 발생했습니다.';
                console.error('파일 읽기 오류:', error);
            });
    } else {
        console.error('artistDescription 데이터 속성이 없습니다.');
    }
    
});