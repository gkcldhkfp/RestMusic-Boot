/**
 *  searchAll
 */

document.addEventListener('DOMContentLoaded', () => {

    console.log(firstObj);
    console.log(songs);
    console.log(albums);
    console.log(artists);
    console.log(first);

    if (first == 'song') {
        const artistid = firstObj.artistId;
        const artistname = firstObj.artistName;

        const artistids = (artistid || '').toString().split(',').map(part => part.trim()).filter(Boolean);
        const artistnames = (artistname || '').toString().split(',').map(part => part.trim()).filter(Boolean);

        const grIds = (firstObj.groupId || '').toString().split(',').map(part => part.trim()).filter(Boolean);
        const grNames = (firstObj.groupName || '').toString().split(',').map(part => part.trim()).filter(Boolean);


        let htmlStr = ''; // div에 삽입할 html 코드(댓글 목록)
        for (let i = 0; i < artistids.length; i++) {
            htmlStr += `<small>
                <a href="/artist/songs?artistId=${artistids[i]}" 
                                    style="max-width: 200px; max-height: 20px">${artistnames[i]}</a></small>            
            `
        }
        for (let i = 0; i < grIds.length; i++) {
            htmlStr += `<small>
                <a href="/artist/songs?artistId=${grIds[i]}" 
                                    style="max-width: 200px; max-height: 20px">${grNames[i]}</a></small>            
            `
        }
        const firstArdiv = document.querySelector('div#firstSongArtist');
        firstArdiv.innerHTML += htmlStr;

    }

    let currentPage = 1;
    const itemsPerPage = 5;
    const btnAddPlayList = document.querySelectorAll('button.addPlayList');
    const playListModal = new bootstrap.Modal(document.querySelector('div#staticBackdrop'), { backdrop: 'static' });
    btnAddPlayList.forEach(btn => btn.addEventListener('click', getPlayLists));
    console.log(authUser);
    function getPlayLists(event) {
        if (authUser == null) {
            if (confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")) {
                redirectToLogin();
            }
            return
        }
        songId = event.target.closest('button').getAttribute('data-songId');
        
        const uri = `../getPlayList/${authUser}`;
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
                console.log(error);
            });

        function addToPlayList(data) {
            axios
                .post('../addSongToPlayList', data)
                .then((response) => {
                    console.log(response.data);
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

    function redirectToLogin() {
        const currentUrl = window.location.href;
        window.location.href = `/member/signin?target=${encodeURIComponent(currentUrl)}`;
    }
    console.log(songs);
    for (let s of songs) {
        const songId = s.songId;

        let inputStr = '';
        const input = document.querySelector(`td#singers_${songId}`)

        const ids = (s.artistId || '').toString().split(',').map(part => part.trim()).filter(Boolean);
        const names = (s.artistName || '').toString().split(',').map(part => part.trim()).filter(Boolean);

        const grIds = (s.groupId || '').toString().split(',').map(part => part.trim()).filter(Boolean);
        const grNames = (s.groupName || '').toString().split(',').map(part => part.trim()).filter(Boolean);

        for (let i = 0; i < ids.length; i++) {
            inputStr += `<small>
                <a href="/artist/songs?artistId=${ids[i]}" 
                                    style="max-width: 200px; max-height: 20px">${names[i]}</a></small>            
            `
        }

        for (let i = 0; i < grIds.length; i++) {
            inputStr += `<small>
                            <a href="/group/songs?groupId=${grIds[i]}" 
                                style="max-width: 200px; max-height: 20px">${grNames[i]}</a></small>            
                        `;
        }

        input.innerHTML += inputStr;


        console.log(inputStr);
        console.log(input);
    }




});