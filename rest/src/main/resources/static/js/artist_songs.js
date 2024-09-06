/**
 * artist 폴더의 songs.html에 포함
 */
document.addEventListener('DOMContentLoaded', () => {
    console.log('사용자 ID는 ' + (loginUserId !== null ? loginUserId : '로그인되지 않음'));
    
    // artist 좋아요 관련 코드
    const btnLike = document.querySelector('button#btnLike');
    const data = { artistId, id:loginUserId };
    
    if(loginUserId != ''){
    axios
        .post('/api/artist/isLiked', data)
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
            .put('/api/artist/like', data)
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
    
    // playlist 작동 관련 코드
    const btnAddPlayLists = document.querySelectorAll('button.addPlayList');
    const playListModal = new bootstrap.Modal(document.querySelector('div#staticBackdrop3'), { backdrop: 'static' });

    let currentPage = 1;
    const itemsPerPage = 5;

    for (let a of btnAddPlayLists) {
        a.addEventListener('click', getPlayLists);
    }

    function getPlayLists(event) {
        event.stopPropagation();
        if (loginUserId == null) { // 로그인하지 않은 경우
            if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) {
                // 현재 페이지의 경로를 인코딩하여 로그인 후 다시 돌아오도록 처리
                const currentPath = encodeURIComponent(location.pathname + location.search);
                location.href = `/member/signin?targetUrl=${currentPath}`; // 로그인 페이지로 이동
            }
            return; // 로그인하지 않으면 함수 종료
        }
        songId = event.target.closest('button').getAttribute('data-songId');

        const uri = `../getPlayList/${loginUserId}`;
        console.log(loginUserId);
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

        // 플리 목록을 카운트
        let playlistCount = 0;

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
            
            playlistCount++;
        }

        // 작성된 HTML 코드를 div 영역에 삽입.
        divPlayLists.innerHTML = htmlStr;
        
        console.log(playlistCount);
        
        // 플레이리스트가 존재하지 않을 경우 문구 출력.
        if (playlistCount == 0) {
            divPlayLists.innerHTML = `
                <p class="text-center text-muted">플레이리스트가 없습니다.</p>
                `;
        }

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

    // 플로팅 버튼 그룹 요소 선택
    const songCheckboxes = document.querySelectorAll('.songCheckbox'); // 각 곡의 선택을 위한 체크박스 요소들을 선택
    const floatingButtonGroup = document.getElementById('floatingButtonGroup'); // 선택된 곡 수에 따라 표시되는 플로팅 버튼 그룹
    const selectedCountDisplay = floatingButtonGroup.querySelector('.selected-count'); // 선택된 곡 수를 표시하는 요소
    const deselectAllButton = floatingButtonGroup.querySelector('.deselect-all'); // 모든 곡 선택 해제 버튼
    const selectAllCheckbox = document.getElementById('selectAllCheckbox'); // 전체 선택 체크박스

    // 플로팅 버튼 그룹을 업데이트하는 함수
    function updateButtonGroup() {
        const checkedBoxes = document.querySelectorAll('.songCheckbox:checked'); // 현재 선택된 곡 체크박스들을 선택
        const checkedCount = checkedBoxes.length; // 선택된 체크박스의 수를 계산

        if (checkedCount > 0) {
            // 선택된 곡이 하나 이상인 경우 플로팅 버튼 그룹을 표시
            floatingButtonGroup.classList.remove('d-none');
            selectedCountDisplay.textContent = `${checkedCount}개 선택됨`; // 선택된 곡 수를 표시
        } else {
            // 선택된 곡이 없을 경우 플로팅 버튼 그룹을 숨김
            floatingButtonGroup.classList.add('d-none');
        }
    }

    // 개별 체크박스 이벤트 리스너
    songCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', updateButtonGroup); // 체크박스 상태가 변경될 때마다 updateButtonGroup 함수 호출
    });

    // 선택 해제 버튼 이벤트 리스너
    deselectAllButton.addEventListener('click', function() {
        // 모든 곡 체크박스를 선택 해제
        songCheckboxes.forEach(checkbox => {
            checkbox.checked = false;
        });
        // 전체 선택 체크박스를 선택 해제
        selectAllCheckbox.checked = false; // 전체 선택 체크박스 해제
        // 플로팅 버튼 그룹 업데이트 (선택된 항목이 없으므로 숨겨짐)
        updateButtonGroup();
    });

    // 전체 선택 체크박스 이벤트 리스너
    selectAllCheckbox.addEventListener('change', function() {
        const isChecked = selectAllCheckbox.checked; // 전체 선택 체크박스의 현재 상태를 확인
        // 모든 곡 체크박스의 상태를 전체 선택 체크박스와 동일하게 설정
        songCheckboxes.forEach(checkbox => {
            checkbox.checked = isChecked;
        });
        // 플로팅 버튼 그룹 업데이트 (전체 선택 시 모든 곡이 선택됨)
        updateButtonGroup();
    });

    // 플로팅 버튼 그룹에서 "듣기" 버튼 클릭 이벤트 리스너 설정
    floatingButtonGroup.querySelector('.play-selected').addEventListener('click', function() {
        // 선택된 노래들의 ID를 배열로 수집
        const selectedSongs = Array.from(document.querySelectorAll('.songCheckbox:checked')).map(checkbox => checkbox.dataset.songId);
        console.log('selectedSongs = ' + selectedSongs);

        // 선택된 곡이 하나 이상일 때만 실행
        if (selectedSongs.length > 0) {
            // 첫 번째 곡 재생 (재생목록에 추가하지 않음)
            playSelectedSong(selectedSongs[0]);

            // 나머지 곡들만 재생목록에 추가 (알림 표시 없이)
            if (selectedSongs.length > 1) {
                addAllToPlaylist(selectedSongs.slice(1), true);
            }

            // 알림 메시지 표시 (곡이 1개인지 여러 개인지에 따라 다른 메시지 출력)
            if (selectedSongs.length === 1) {
                showAlert('선택한 음원을 재생합니다.', 2000);
            } else {
                showAlert('선택한 곡들을 재생합니다.', 2000);
            }
        }
    });

    // 선택된 곡을 재생하는 함수
    function playSelectedSong(songId) {
        const url = `/song/listen?songId=${songId}`; // 곡 재생 요청 URL 생성
        axios.get(url)
            .then((response) => {
                console.log("첫 번째 곡 재생 성공");
                // 세션 스토리지에 재생 상태 관련 정보 저장
                sessionStorage.setItem('index', 0);
                sessionStorage.setItem('isAdded', 'Y');
                // 부모 프레임의 곡 재생 프레임 새로고침
                parent.songFrame.location.reload();
            })
            .catch((error) => console.log(error));
    }

    // 플로팅 버튼 그룹에서 "재생목록에 추가" 버튼 클릭 이벤트 리스너 설정
    floatingButtonGroup.querySelector('.add-to-playlist').addEventListener('click', function() {
        // 선택된 노래들의 ID를 배열로 수집
        const selectedSongs = Array.from(document.querySelectorAll('.songCheckbox:checked')).map(checkbox => checkbox.dataset.songId);
        // 선택된 곡이 하나 이상일 때만 실행
        if (selectedSongs.length > 0) {
            // 선택된 노래 중 하나라도 재생목록에 있는지 확인
            checkAnyInPlaylist(selectedSongs).then(anyInPlaylist => {
                if (anyInPlaylist) {
                    // 재생목록에 이미 있는 곡이 하나라도 있으면 사용자에게 추가 여부 확인
                    if (confirm('이미 재생목록에 있는 곡이 포함되어 있습니다. 그래도 추가하시겠습니까?')) {
                        addAllToPlaylist(selectedSongs, false); // 확인 시 재생목록에 추가
                    }
                } else {
                    // 재생목록에 없는 곡들만 추가
                    addAllToPlaylist(selectedSongs, false);
                }
            });
        }
    });

    // 선택된 노래 중 하나라도 재생목록에 있는지 확인하는 함수
    function checkAnyInPlaylist(songIds) {
        return Promise.any(songIds.map(songId =>
            axios.get(`/song/getCPList?songId=${songId}`) // 각 곡의 재생목록 포함 여부 확인
                .then(response => response.data ? Promise.resolve(true) : Promise.reject(false))
        )).then(() => true).catch(() => false);
    }

    // 모든 선택된 노래를 재생목록에 추가하는 함수 (플로팅 버튼용)
    function addAllToPlaylist(songIds, skipAlert = false) {
        let addedCount = 0; // 추가된 곡의 수를 추적
        const totalSongs = songIds.length; // 전체 곡 수

        // 각 곡을 재생목록에 추가
        songIds.forEach((songId, index) => {
            checkAndAddToPlaylistFloating(songId, true, () => {
                addedCount++; // 추가된 곡 수 증가
                if (addedCount === totalSongs && !skipAlert) {
                    // 모든 곡이 추가되었을 때 알림 표시
                    showAlert('재생 목록에 추가되었습니다', 2000);
                }
            });
        });
    }

    // 재생목록에 곡을 추가하는 함수 (플로팅 버튼용)
    function checkAndAddToPlaylistFloating(id, skipConfirm, callback) {
        const url1 = `/song/getCPList?songId=${id}`; // 곡의 재생목록 포함 여부 확인 URL 생성
        axios.get(url1)
            .then((response) => {
                // 곡이 재생목록에 없거나 확인 없이 추가할 경우
                if (!response.data || skipConfirm) {
                    addCurrentPlayListFloating(id, skipConfirm, callback);
                } else {
                    // 재생목록에 이미 있는 곡일 때 사용자에게 추가 여부 확인
                    let result = confirm('이미 재생목록에 있는 곡입니다. 그래도 추가하시겠습니까?');
                    if (result) {
                        addCurrentPlayListFloating(id, skipConfirm, callback); // 확인 시 추가
                    } else if (callback) {
                        callback(); // 콜백 함수 실행
                    }
                }
            })
            .catch((error) => { console.log(error); });
    }

    // 곡을 재생목록에 추가하는 함수 (플로팅 버튼용)
    function addCurrentPlayListFloating(id, skipAlert, callback) {
        const url2 = `/song/addCurrentPlayList?songId=${id}`; // 재생목록에 곡 추가 요청 URL 생성
        console.log(url2);
        axios.get(url2)
            .then((response) => {
                console.log(response);
                // 세션 스토리지에 재생 상태 관련 정보 저장 및 프레임 새로고침
                if (sessionStorage.getItem('isAdded') === 'N') {
                    sessionStorage.setItem('index', 0);
                    sessionStorage.setItem('isAdded', 'Y');
                    parent.songFrame.location.reload();
                }
                if (!skipAlert) {
                    // 알림 메시지 표시
                    showAlert('재생 목록에 추가되었습니다', 2000);
                }
                if (callback) {
                    callback(); // 콜백 함수 실행
                }
            })
            .catch((error) => { console.log(error); });
    }

    // 알림 메시지를 표시하는 함수
    function showAlert(message, duration) {
        // 기존 알림창이 있는지 확인 (중복 표시 방지)
        if (document.querySelector('.custom-alert')) {
            return;
        }

        // 알림창 생성 및 스타일 적용
        const alertBox = document.createElement('div');
        alertBox.textContent = message;
        alertBox.className = 'custom-alert';
        alertBox.style.cssText = `
            position: fixed;
            bottom: 20px;
            left: 50%;
            transform: translateX(-50%);
            background-color: #333;
            color: white;
            padding: 15px 20px;
            border-radius: 5px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
            z-index: 1000;
            opacity: 0;
            transition: opacity 0.5s ease-in-out;
        `;

        document.body.appendChild(alertBox);

        // 알림창 페이드 인 효과
        setTimeout(() => {
            alertBox.style.opacity = '1';
        }, 10);

        // 지정된 시간 후 페이드 아웃 및 제거
        setTimeout(() => {
            alertBox.style.opacity = '0';
            setTimeout(() => {
                document.body.removeChild(alertBox);
            }, 500);
        }, duration);
    }

    // 초기 설정 (isAdded 값이 세션 스토리지에 없을 경우 초기화)
    if (!sessionStorage.getItem('isAdded')) {
        sessionStorage.setItem('isAdded', 'N');
    }

    // 페이지 새로고침 플래그가 있을 때 처리
    if (typeof refresh !== 'undefined' && refresh === 'Y') {
        console.log(refresh);
        const uri21 = `${window.location.origin}/user/removeRefresh` // 새로고침 플래그 제거 요청
        axios.get(uri21)
            .then((response) => {
                console.log(response)
            })
            .catch((error) => {
                console.log(error)
            });
        parent.songFrame.location.reload(); // 부모 프레임 새로고침
    }

    // 테이블의 각 "재생목록" 버튼에 이벤트 리스너를 설정
    const addCPListButtons = document.querySelectorAll('.addCPListBtn');

    if (addCPListButtons !== null) {
        // 모든 버튼에 대해 기존의 이벤트 리스너를 제거하고 새로운 이벤트 리스너를 추가
        addCPListButtons.forEach(button => {
            button.removeEventListener('click', addToCPListNew); // 기존 이벤트 리스너 제거
            button.addEventListener('click', addToCPListNew); // 새로운 이벤트 리스너 추가
        });
    }

    // 테이블의 "재생목록" 버튼 클릭 시 실행될 함수
    function addToCPListNew(event) {
        event.preventDefault(); // 버튼 클릭 시 발생하는 기본 동작(예: 페이지 리로드)을 방지
        event.stopPropagation(); // 이벤트가 상위 요소로 전파되지 않도록 방지
        const id = event.target.getAttribute('data-id'); // 클릭된 버튼의 'data-id' 속성에서 곡 ID를 가져옴
        checkAndAddToPlaylistTable(id); // 가져온 ID를 사용해 재생목록 추가 과정을 처리
    }

    // 테이블용으로 "재생목록"을 처리하는 함수
    function checkAndAddToPlaylistTable(id) {
        const url1 = `/song/getCPList?songId=${id}`; // 곡이 이미 재생목록에 있는지 확인하기 위한 API URL 생성
        axios.get(url1)
            .then((response) => {
                if (!response.data) {
                    // 곡이 재생목록에 없는 경우, 재생목록에 추가
                    addCurrentPlayListTable(id);
                } else {
                    // 곡이 이미 재생목록에 있는 경우, 사용자에게 추가 여부 확인
                    let result = confirm('이미 재생목록에 있는 곡입니다. 그래도 추가하시겠습니까?');
                    if (result) {
                        // 사용자가 확인을 누른 경우, 재생목록에 추가
                        addCurrentPlayListTable(id);
                    }
                }
            })
            .catch((error) => { console.log(error); });
    }

    // 테이블용으로 곡을 재생목록에 추가하는 함수
    function addCurrentPlayListTable(id) {
        const url2 = `/song/addCurrentPlayList?songId=${id}`; // 재생목록에 곡을 추가하기 위한 API URL 생성
        console.log(url2);
        axios.get(url2)
            .then((response) => {
                console.log(response);
                // 첫 번째 곡이 재생목록에 추가되었는지 여부 확인 및 처리
                if (sessionStorage.getItem('isAdded') === 'N') {
                    sessionStorage.setItem('index', 0); // 재생 목록 인덱스를 0으로 초기화
                    sessionStorage.setItem('isAdded', 'Y'); // 재생목록에 추가된 상태로 변경
                    parent.songFrame.location.reload(); // 곡 재생 프레임 새로고침
                }
                // 곡이 재생목록에 추가되었다는 알림 표시
                showAlert('재생 목록에 추가되었습니다', 2000);
            })
            .catch((error) => { console.log(error); });
    }

    // 플로팅 버튼에서 "담기" 버튼 클릭 이벤트 리스너 설정
    floatingButtonGroup.querySelector('.add-to-my-list').addEventListener('click', function() {
        // 선택된 곡들의 ID를 배열로 수집
        const selectedSongs = Array.from(document.querySelectorAll('.songCheckbox:checked')).map(checkbox => checkbox.dataset.songId);

        if (selectedSongs.length > 0) {
            // 첫 번째 선택된 곡에 해당하는 "담기" 버튼을 찾음
            const firstSongButton = document.querySelector(`.add-to-playlist-btn[data-song-id="${selectedSongs[0]}"]`);

            if (!loginUserId) { // 로그인하지 않은 경우
                if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) {
                    // 현재 페이지의 경로를 인코딩하여 로그인 후 다시 돌아오도록 처리
                    const currentPath = encodeURIComponent(location.pathname + location.search);
                    location.href = `/member/signin?targetUrl=${currentPath}`; // 로그인 페이지로 이동
                }
                return; // 로그인하지 않으면 함수 종료
            }

            const id = parseInt(loginUserId); // loginUserId로 id를 설정

            // 플레이리스트 모달 창을 띄움
            showPlayListModal(id, selectedSongs);

            // 모든 곡이 추가된 후 실행될 함수
            function afterAddingAllSongs() {
                // 1. 플로팅 그룹 숨기기
                floatingButtonGroup.classList.add('d-none');

                // 2. 모든 체크박스 해제 (전체 선택 체크박스 포함)
                document.querySelectorAll('.songCheckbox:checked, #selectAllCheckbox:checked').forEach(checkbox => {
                    checkbox.checked = false;
                });
            }

            // 모든 곡 추가 후 실행
            afterAddingAllSongs();
        }
    });

    // 플레이리스트 불러오기 및 모달 표시 함수
    function showPlayListModal(id, songIds) {  // songIds를 배열로 받음
        // 사용자의 플레이리스트를 가져오는 API 요청
        axios.get(`/getPlayList/${id}`)
            .then(response => {
                if (response.status === 200) {
                    const playLists = response.data; // 가져온 플레이리스트 데이터
                    const playListsContainer = document.getElementById('playLists'); // 플레이리스트를 표시할 컨테이너
                    playListsContainer.innerHTML = ''; // 기존 플레이리스트 목록을 초기화

                    if (playLists.length === 0) {
                        // 플레이리스트가 없는 경우 메시지 표시
                        playListsContainer.innerHTML = '<p class="text-center text-muted">플레이리스트가 없습니다.</p>';
                    } else {
                        // 각 플레이리스트를 화면에 표시
                        playLists.forEach(list => {
                            const defaultImage = '/images/icon/default.png'; // 기본 앨범 이미지 경로
                            // 앨범 이미지 설정
                            const albumImageSrc = list.albumImage ? `/images/albumcover/${list.albumImage}` : defaultImage;

                            // 플레이리스트 항목을 생성
                            const listElement = document.createElement('div');
                            listElement.classList.add('playlist-item', 'd-flex', 'align-items-center', 'mb-2');

                            // 플레이리스트 항목의 HTML 구성
                            listElement.innerHTML = `
                                    <div class="playlist-button-container">
                                        <button class="playlist-btn btn btn-outline-success w-100" data-id="${list.plistId}">
                                            <div class="d-flex align-items-center">
                                                <div class="playlist-image">
                                                    <img src="${albumImageSrc}" alt="Album cover" class="rounded">
                                                </div>
                                                <div class="playlist-name">${list.plistName}</div>
                                            </div>
                                        </button>
                                    </div>
                                `;
                            playListsContainer.appendChild(listElement); // 생성된 플레이리스트 항목을 컨테이너에 추가
                        });
                    }

                    // songIds를 hidden input으로 추가
                    const hiddenInput = document.createElement('input');
                    hiddenInput.type = 'hidden';
                    hiddenInput.id = 'selectedSongIds';
                    hiddenInput.value = JSON.stringify(songIds);  // 배열을 JSON 문자열로 변환
                    playListsContainer.appendChild(hiddenInput);

                    // 플레이리스트 선택 모달을 표시
                    const selectPlayListModal = new bootstrap.Modal(document.getElementById('selectPlayList'));
                    selectPlayListModal.show();

                    // 각 플레이리스트 버튼에 클릭 이벤트를 추가하여 선택 상태를 토글
                    document.querySelectorAll('.playlist-btn').forEach(btn => {
                        btn.addEventListener('click', function() {
                            this.classList.toggle('selected'); // 버튼의 선택 상태를 토글
                        });
                    });
                }
            })
            .catch(error => {
                console.error('플레이리스트를 불러오는 중 오류가 발생했습니다:', error);
            });
    }

    // 선택한 플레이리스트에 곡을 추가하는 함수
    function addSongToPlaylists() {
        // 선택된 버튼 사용
        const selectedPlaylists = document.querySelectorAll('#playLists .playlist-btn.selected');
        const selectedPlaylistIds = Array.from(selectedPlaylists).map(btn => btn.dataset.id); // 선택된 플레이리스트의 ID 배열 생성

        // hidden input에서 songIds를 가져옴
        const songIdsJson = document.getElementById('selectedSongIds').value;
        const songIds = JSON.parse(songIdsJson);  // JSON 문자열을 배열로 변환

        // 플레이리스트가 선택되지 않았을 경우 경고 메시지 표시
        if (selectedPlaylistIds.length === 0) {
            alert('플레이리스트를 선택하세요.');
            return;
        }

        // 곡이 선택되지 않았을 경우 경고 메시지 표시
        if (songIds.length === 0) {
            alert('곡을 선택하세요.');
            return;
        }

        const alreadyAdded = {}; // 곡이 이미 추가된 플레이리스트를 추적할 객체
        const promises = []; // 모든 비동기 작업을 추적할 배열

        // 각 플레이리스트에 대해 곡이 이미 있는지 확인
        selectedPlaylistIds.forEach(plistId => {
            alreadyAdded[plistId] = false; // 초기 상태는 추가되지 않은 상태로 설정

            songIds.forEach(songId => {
                console.log(`플레이리스트 ${plistId}에 곡 ${songId} 확인 중`);
                // 각 플레이리스트에 곡이 이미 추가되어 있는지 확인
                const checkPromise = axios.post(`/checkSongInPlayList`, {
                    plistId: parseInt(plistId),
                    songId: parseInt(songId)
                }).then(response => {
                    console.log(`플레이리스트 ${plistId} 응답:`, response.data);
                    if (response.data === false) { // 곡이 이미 있는 경우
                        alreadyAdded[plistId] = true; // 이미 추가된 것으로 표시
                    }
                }).catch(error => {
                    console.error('플레이리스트에서 곡 확인 중 오류가 발생했습니다:', error);
                });
                promises.push(checkPromise); // 비동기 작업을 추적 배열에 추가
            });
        });

        // 모든 곡 확인 작업이 완료되면 실행
        Promise.all(promises).then(() => {
            // 이미 추가된 곡이 있는 플레이리스트 필터링
            const addedPlaylists = selectedPlaylistIds.filter(plistId => alreadyAdded[plistId]);
            if (addedPlaylists.length > 0) {
                // 사용자가 확인을 누르면 곡을 추가
                if (confirm('선택한 플레이리스트에 이미 추가된 곡입니다. 그래도 추가하시겠습니까?')) {
                    addSongsToSelectedPlaylists(selectedPlaylistIds, songIds); // 확인 시 곡 추가
                } else {
                    // 모달 창 닫기
                    const selectPlayListModal = bootstrap.Modal.getInstance(document.getElementById('selectPlayList'));
                    selectPlayListModal.hide();
                }
            } else {
                addSongsToSelectedPlaylists(selectedPlaylistIds, songIds); // 중복된 곡이 없으면 바로 추가
            }
        });
    }

    // 선택된 플레이리스트에 곡 추가
    function addSongsToSelectedPlaylists(selectedPlaylistIds, songIds) {
        // 선택된 각 플레이리스트에 곡을 추가하기 위한 API 요청을 생성
        const addPromises = selectedPlaylistIds.flatMap(plistId => {
            return songIds.map(songId => {
                return axios.post(`/addSongToPlayList`, {
                    plistId: parseInt(plistId),
                    songId: parseInt(songId)
                });
            });
        });

        // 모든 곡 추가 요청이 완료되면 실행
        Promise.all(addPromises).then(responses => {
            // 모든 요청이 성공했는지 확인
            const allSuccessful = responses.every(response => response && response.status === 200);
            if (allSuccessful) {
                alert('선택한 플레이리스트에 곡이 추가되었습니다.'); // 성공 메시지 표시
                const selectPlayListModal = bootstrap.Modal.getInstance(document.getElementById('selectPlayList'));
                selectPlayListModal.hide(); // 모달 창 닫기

                // 체크박스 해제 및 플로팅 버튼 숨기기
                document.querySelectorAll('.songCheckbox:checked, #selectAllCheckbox:checked').forEach(checkbox => {
                    checkbox.checked = false;
                });
                floatingButtonGroup.classList.add('d-none');

                // 모달 배경의 투명도 조정
                const modalBackdrop = document.querySelector('.modal-backdrop');
                if (modalBackdrop) {
                    modalBackdrop.style.opacity = '0';
                }
            }
        }).catch(error => {
            console.error('플레이리스트에 곡 추가 중 오류가 발생했습니다:', error);
            alert('플레이리스트에 곡을 추가하는 중 오류가 발생했습니다.');
        });
    }

    // "곡 추가" 버튼 클릭 시 곡을 플레이리스트에 추가하는 함수
    const saveButton = document.getElementById('btnAddSong');
    saveButton.addEventListener('click', addSongToPlaylists);

    // 플레이리스트 추가 버튼 클릭 이벤트 핸들러
    const addToPlaylistButtons = document.querySelectorAll('.add-to-playlist-btn');
    addToPlaylistButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            if (!loginUserId) { // 로그인하지 않은 경우
                if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) {
                    // 현재 페이지의 경로를 인코딩하여 로그인 후 다시 돌아오도록 처리
                    const currentPath = encodeURIComponent(location.pathname + location.search);
                    location.href = `/member/signin?targetUrl=${currentPath}`; // 로그인 페이지로 이동
                }
                return; // 로그인하지 않으면 함수 종료
            }

            const id = parseInt(loginUserId); // loginUserId로 id를 설정
            const songId = this.dataset.songId;  // 버튼에서 songId를 가져옴

            addToPlaylistButtons.forEach(btn => btn.classList.remove('active')); // 모든 버튼의 active 클래스 제거
            this.classList.add('active'); // 현재 클릭한 버튼에 active 클래스 추가

            showPlayListModal(id, [songId]);  // songId를 배열로 전달
        });
    });

    // 모달 창이 나와도 다른 버튼들을 클릭할 수 있게 설정
    document.querySelectorAll('.modal-backdrop').forEach(backdrop => {
        backdrop.style.pointerEvents = 'none'; // 모달 배경이 클릭을 막지 않도록 설정
    });

});