/**
 *  songDetail.jsp 포함
 */
document.addEventListener('DOMContentLoaded', () => {
    
    console.log(writers);
    console.log(songId);

    const btnAddPlayList = document.querySelector('button#btnAddPlayList');

    const btnLike = document.querySelector('button#btnLike');

     const playListModal = new bootstrap.Modal(document.querySelector('div#staticBackdrop'), { backdrop: 'static' });
    btnAddPlayList.addEventListener('click', getPlayLists);
    
    if(authUser == ''){
        const commnetRegistForm = document.querySelector('div#commnetRegistForm')
        commnetRegistForm.classList.add('d-none');
    }
    
    /*console.log(writerIds);
    console.log(writers);*/
    
    const data = { songId, id:authUser };
    let currentPage = 1;
    const itemsPerPage = 5;
    let playlistsData = [];
    if(authUser != ''){
    axios
        .post('/api/song/isLiked', data)
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
    
    // 장르 작성 & 링크
    const splitgenere = genres.split(',');
    const genreSpace = document.querySelector('p#genre');
    let genreHtml= genreSpace.innerHTML; // 기존 내용 유지
        for(let i = 0; i < splitgenere.length; i++) {
            const trimmedName = splitgenere[i].trim();
            const genrePage = `../song/genreChart?genreName=${trimmedName}`;
            
            if (i === 0) {
            genreHtml += `<span class='text-center ms-2' style='cursor: pointer;' onmouseover="this.style.color='blue';" onmouseout="this.style.color='black';" 
                            onclick="location.href='${genrePage}'">
                            ${trimmedName}
                          </span>`;
            } else {
            // 이후 링크들은 쉼표와 함께 추가
            genreHtml += `,<span class='text-center ms-2' style='cursor: pointer;' onmouseover="this.style.color='blue';" onmouseout="this.style.color='black';"
                             onclick="location.href='${genrePage}'">
                            ${trimmedName}
                          </span>`;
        }
    }
    genreSpace.innerHTML = genreHtml;
    
    
    // 아티스트 작성 & 링크
    const writersSpace = document.querySelector('p#writers');
    const splitWriters = writers.split(',');
    const splitWriterIds = writerIds.split(',');
    artistPageLinked(writersSpace, splitWriters, splitWriterIds);
    
    const composersSpace = document.querySelector('p#composers');
    const splitComposers = composers.split(',');
    const splitComposerIds = composerIds.split(',');
    artistPageLinked(composersSpace, splitComposers, splitComposerIds);
    
    const arrangersSpace = document.querySelector('p#arrangers');
    const splitArrangers = arrangers.split(',');
    const splitArrangerIds = arrangerIds.split(',');
    artistPageLinked(arrangersSpace, splitArrangers, splitArrangerIds);
    
    const singerNameSpace = document.querySelector('p#singerNames');
    const splitsingerName = singers.split(',');
    const splitsingerIds = singerIds.split(',');
    artistPageLinked(singerNameSpace, splitsingerName, splitsingerIds);
    
    
    
    function artistPageLinked (writersSpace, artists, artistIds) {
    
        const length = Math.min(artists.length, artistIds.length);
      /*  console.log(artists.length);
        console.log(artistIds.length);
        console.log(length);*/
        let linksHtml= writersSpace.innerHTML; // 기존 내용 유지
        for(let i = 0; i < length; i++) {
            const trimmedName = artists[i].trim();
            const trimmedId = artistIds[i].trim();
            const artistPage = `../artist/songs?artistId=${trimmedId}`;
            
            if (i === 0) {
            linksHtml += `<span class='text-center ms-2' style='cursor: pointer;' 
            onmouseover="this.style.color='blue';" onmouseout="this.style.color='black';" 
            onclick="location.href='${artistPage}'">
                            ${trimmedName}
                          </span>`;
            } else {
            // 이후 링크들은 쉼표와 함께 추가
            linksHtml += `,<span class='text-center ms-2' style='cursor: pointer;' 
            onmouseover="this.style.color='blue';" onmouseout="this.style.color='black';" 
            onclick="location.href='${artistPage}'">
                            ${trimmedName}
                          </span>`;
            }
        }
        writersSpace.innerHTML = linksHtml;
    }
    
    
    function redirectToLogin() {
        const currentUrl = window.location.href;
        window.location.href = `/Rest/user/signin?target=${encodeURIComponent(currentUrl)}`;
    }
    
    
    
    

    btnLike.addEventListener('click', () => {
    if(authUser == '') {
        if(confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")){
            redirectToLogin();
        }
        return;
        }
        axios
            .put('/api/song/like', data)
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

    function getPlayLists() {
        if(authUser == '' ) {
        if(confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")){
            redirectToLogin();
        }
        return
        }
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
                    log.info(response.data);
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


});

