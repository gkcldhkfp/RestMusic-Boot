/**
 * /song/newest.html에 포함
 */

document.addEventListener("DOMContentLoaded", function() {
    // 로그인한 사용자 ID 로그 출력
    console.log('loginUserId= ' + loginUserId);
    
    // DOM 요소 선택
    const songsBody = document.getElementById('songsBody'); // 노래 목록을 담을 테이블의 tbody 요소
    const loading = document.getElementById('loading'); // 로딩 스피너 요소
    const loadMoreBtn = document.getElementById('loadMoreBtn'); // 더보기 버튼 요소
    const floatingButtonGroup = document.getElementById('floatingButtonGroup'); // 플로팅 버튼 그룹
    const selectAllCheckbox = document.getElementById('selectAllCheckbox'); // 전체 선택 체크박스
    
    // 전역 변수 초기화
    let currentPage = 0; // 현재 페이지 번호
    const pageSize = 30; // 한 페이지당 로드할 노래 수
    let loadingData = false; // 데이터 로딩 중 여부
    let totalSongsCount = 0; // 서버에서 받아올 전체 노래 수
    
    // 이벤트 리스너 설정 함수
    function setupEventListeners() {
        // 더보기 버튼 클릭 이벤트
        loadMoreBtn.addEventListener('click', loadMoreSongs);
        
        // 전체 선택 체크박스 이벤트
        selectAllCheckbox.addEventListener('change', handleSelectAll);
        
        // 플로팅 버튼 그룹 이벤트 (이벤트 위임 사용)
        floatingButtonGroup.addEventListener('click', handleFloatingButtonClick);
        
        // 테이블 body에 이벤트 위임 설정
        songsBody.addEventListener('click', handleTableBodyClick);
        songsBody.addEventListener('change', handleCheckboxChange);

        // "곡 추가" 버튼 클릭 이벤트
        const saveButton = document.getElementById('btnAddSong');
        saveButton.addEventListener('click', addSongToPlaylists);

        // 좋아요 아이콘 클릭 이벤트 핸들러
        setupLikeIconHandlers();
    }

    // 좋아요 아이콘의 이벤트 핸들러 설정 함수
function setupLikeIconHandlers() {
    // 이벤트 위임을 사용하여 songsBody에 단일 이벤트 리스너 추가
    songsBody.removeEventListener('click', handleLikeIconClick);
    songsBody.addEventListener('click', handleLikeIconClick);

    // 페이지 로드 시 각 아이콘의 초기 상태 설정
    document.querySelectorAll('.heart-icon').forEach(icon => {
        const songId = icon.dataset.songId;
        updateLikeIconInitialState(icon, songId);
    });
}

// 좋아요 아이콘 초기 상태 설정 함수
function updateLikeIconInitialState(icon, songId) {
    const data = { songId, loginUserId };
    
    axios.post('/api/isLiked', data)
        .then(response => {
            updateLikeIconState(icon, response.data);
        })
        .catch(error => {
            console.error('좋아요 상태 가져오는 중 오류:', error);
            updateLikeIconState(icon, false);
        });
}
    
    // 좋아요 여부에 따라 아이콘의 상태 및 스타일을 변경
    function updateLikeIconState(icon, isLiked) {
        if (isLiked) {
            icon.classList.add('liked'); // '좋아요' 상태일 때 'liked' 클래스를 추가
            icon.classList.remove('far'); // '비어있는 하트(far)' 아이콘을 제거
            icon.classList.add('fas'); // '꽉 찬 하트(fas)' 아이콘을 추가
            icon.style.color = 'red'; // 아이콘의 색상을 빨간색으로 변경
        } else {
            icon.classList.remove('liked'); // '좋아요' 상태 해제 시 'liked' 클래스를 제거
            icon.classList.remove('fas'); // '꽉 찬 하트(fas)' 아이콘을 제거
            icon.classList.add('far'); // '비어있는 하트(far)' 아이콘을 추가
            icon.style.color = 'black'; // 아이콘의 색상을 검정색으로 변경
        }
    }
    
    // 좋아요 아이콘 클릭 이벤트 핸들러
function handleLikeIconClick(event) {
    const icon = event.target.closest('.heart-icon');
    if (!icon) return; // 클릭된 요소가 좋아요 아이콘이 아니면 무시

    const songId = icon.dataset.songId;
    const likesCountElement = icon.nextElementSibling;

    if (!loginUserId) {
        if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) {
            const currentPath = encodeURIComponent(location.pathname + location.search);
            location.href = `/member/signin?targetUrl=${currentPath}`;
        }
        return;
    }

    // 좋아요 상태 토글
    const isLiked = icon.classList.contains('liked');
    const url = isLiked ? `/api/song/cancelLike/${songId}/${loginUserId}` : '/api/song/addLike';
    const method = isLiked ? 'delete' : 'post';
    const data = isLiked ? null : { songId, loginUserId };

    // 좋아요 상태 변경 요청
    axios({
        method: method,
        url: url,
        data: data
    })
    .then(response => {
        if (response.status === 200) {
            // 서버 응답에 따라 UI 업데이트
            updateLikeIconState(icon, !isLiked);
            likesCountElement.textContent = response.data;
        }
    })
    .catch(error => {
        console.error('좋아요 상태 변경 중 오류:', error);
    });
}
    
    // 더보기 버튼 클릭 시 실행되는 함수
    function loadMoreSongs() {
        fetchSongs(currentPage); // 현재 페이지 번호에 맞춰 노래 데이터를 가져오는 함수 호출
    }
    
    // 전체 선택 체크박스 처리 함수
    function handleSelectAll() {
        const isChecked = selectAllCheckbox.checked; // 전체 선택 체크박스가 체크되어 있는지 확인
        document.querySelectorAll('.songCheckbox').forEach(checkbox => {
            checkbox.checked = isChecked; // 모든 노래 개별 체크박스를 전체 선택 체크박스와 동일한 상태로 설정
        });
        updateButtonGroup(); // 체크박스 상태에 따라 버튼 그룹의 활성화 여부를 업데이트
    }
    
    // 플로팅 버튼 그룹 클릭 처리 함수 수정
    function handleFloatingButtonClick(event) {
        const target = event.target.closest('button'); // 클릭된 요소가 버튼인지 확인
        if (!target) return; // 버튼이 아닌 경우 함수 종료
    
        const selectedSongs = getSelectedSongIds(); // 선택된 노래들의 ID 배열을 가져옴
        if (selectedSongs.length === 0) return; // 선택된 노래가 없으면 함수 종료
    
        // 버튼에 따라 각각의 기능을 실행
        if (target.classList.contains('play-selected')) {
            playSelectedSongs(selectedSongs); // 선택된 노래들을 재생
        } else if (target.classList.contains('add-to-playlist')) {
            addSelectedToPlaylist(selectedSongs); // 선택된 노래들을 재생목록에 추가
        } else if (target.classList.contains('add-to-my-list')) {
            addSelectedToMyList(selectedSongs); // 선택된 노래들을 내 리스트에 추가
        } else if (target.classList.contains('deselect-all')) {
            deselectAll(); // 모든 노래의 선택을 해제
        }
    }
    
    // 선택된 노래 ID 배열 반환 함수
    function getSelectedSongIds() {
        // 선택된 체크박스들에서 songId를 추출하여 배열로 반환
        return Array.from(document.querySelectorAll('.songCheckbox:checked')).map(checkbox => checkbox.dataset.songId);
    }
    
    // 테이블 body 클릭 이벤트 처리 함수
    function handleTableBodyClick(event) {
        const target = event.target; // 클릭된 요소를 가져옴
    
        if (target.closest('.play-btn')) {
            const songId = target.closest('.play-btn').getAttribute('data-id'); // 클릭된 버튼의 data-id 속성에서 노래 ID를 가져옴
            playSong(songId); // 해당 노래를 재생
        } else if (target.closest('.addCPListBtn')) {
            const songId = target.closest('.addCPListBtn').getAttribute('data-id'); // 클릭된 버튼의 data-id 속성에서 노래 ID를 가져옴
            addToCurrentPlaylist(songId); // 현재 재생목록에 해당 노래를 추가
        } else if (target.closest('.add-to-playlist-btn')) {
            const songId = target.closest('.add-to-playlist-btn').getAttribute('data-song-id'); // 클릭된 버튼의 data-song-id 속성에서 노래 ID를 가져옴
            showPlaylistModal(songId); // 해당 노래를 재생목록에 추가하는 모달을 표시
        }
    }
    
    // 체크박스 변경 이벤트 처리 함수
    function handleCheckboxChange() {
        updateButtonGroup(); // 체크박스 상태가 변경될 때 버튼 그룹의 상태를 업데이트
    }
    
    // 선택된 노래 재생 함수 (플로팅 버튼용)
    async function playSelectedSongs() {
        const selectedSongs = getSelectedSongIds(); // 선택된 노래 ID 배열을 가져옴
        if (selectedSongs.length > 0) {
            // 첫 번째 선택된 곡 재생
            await playSelectedSong(selectedSongs[0]);
    
            // 나머지 곡들을 재생목록에 추가 (첫 번째 곡 제외)
            if (selectedSongs.length > 1) {
                await addAllToPlaylist(selectedSongs.slice(1), true); // 재생 목록에 추가
            }
    
            // 선택한 곡의 개수에 따라 알림 메시지 표시
            showAlert(`${selectedSongs.length}개의 음원을 재생합니다.`, 2000);
        }
    }
    
    // 선택된 노래를 재생목록에 추가하는 함수
    async function addSelectedToPlaylist(selectedSongs) {
        if (selectedSongs.length === 0) return; // 선택된 노래가 없으면 함수 종료
    
        // 이미 재생목록에 있는 곡들을 확인
        const songsInPlaylist = await Promise.all(selectedSongs.map(songId =>
            axios.get(`/song/getCPList?songId=${songId}`)
                .then(response => ({ songId, inPlaylist: response.data })) // 서버로부터 재생목록에 해당 곡이 있는지 여부를 확인
                .catch(() => ({ songId, inPlaylist: false })) // 오류 발생 시 재생목록에 없는 것으로 처리
        ));
    
        // 재생목록에 없는 곡들만 필터링
        const songsToAdd = songsInPlaylist.filter(song => !song.inPlaylist).map(song => song.songId);
    
        // 재생목록에 이미 있는 곡들
        const alreadyInPlaylist = songsInPlaylist.filter(song => song.inPlaylist).map(song => song.songId);
    
        // 이미 재생목록에 있는 곡들에 대해 사용자에게 알림
        if (alreadyInPlaylist.length > 0) {
            const confirmAdd = confirm('이미 재생목록에 있는 곡이 포함되어 있습니다. 그래도 추가하시겠습니까?');
            if (confirmAdd) {
                songsToAdd.push(...alreadyInPlaylist); // 사용자 확인 후 재생목록에 있는 곡도 추가
            }
        }
    
        // 선택된 곡들을 재생목록에 추가
        if (songsToAdd.length > 0) {
            await addAllToPlaylist(songsToAdd, false);
            showAlert(`${songsToAdd.length}개의 곡이 재생목록에 추가되었습니다.`, 2000); // 추가 완료 메시지
        }
    }
    
    // 선택된 노래를 내 리스트에 추가하는 함수
    function addSelectedToMyList(selectedSongs) {
        if (selectedSongs.length === 0) return; // 선택된 노래가 없으면 함수 종료
    
        if (!loginUserId) { // 로그인하지 않은 경우
            if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) { // 로그인 유도 메시지 표시
                const currentPath = encodeURIComponent(location.pathname + location.search); // 현재 경로 인코딩
                location.href = `/member/signin?targetUrl=${currentPath}`; // 로그인 페이지로 리다이렉트
            }
            return;
        }
    
        showPlayListModal(parseInt(loginUserId), selectedSongs); // 선택된 곡을 내 리스트에 추가하는 모달 표시
    }
    
    // 모든 선택 해제 함수
    function deselectAll() {
        // 모든 체크박스 선택 해제
        document.querySelectorAll('.songCheckbox, #selectAllCheckbox').forEach(checkbox => {
            checkbox.checked = false;
        });
        updateButtonGroup(); // 체크박스 상태에 따라 버튼 그룹 상태 업데이트
    }
    
    // 노래 데이터 가져오기 함수
    function fetchSongs(page) {
        if (loadingData) return; // 이미 데이터를 로딩 중이면 중복 요청 방지
        loadingData = true; // 로딩 상태 표시
        loading.style.display = 'block'; // 로딩 중 아이콘 표시
        loadMoreBtn.style.display = 'none'; // 더보기 버튼 숨기기
    
        // 서버로부터 노래 목록을 가져오는 요청
        axios.get('/song/api/newest', {
            params: { page: page, size: pageSize }
        })
        .then(response => {
            const data = response.data; // 서버로부터 받은 데이터
            const songs = data.songs; // 노래 목록
            totalSongsCount = data.totalCount; // 전체 노래 개수
    
            if (songs.length > 0) {
                appendSongs(songs); // 노래 목록을 화면에 추가
                currentPage++; // 페이지 번호 증가
                updateLoadMoreButton(); // 더보기 버튼 상태 업데이트
            } else {
                loadMoreBtn.style.display = 'none'; // 더 이상 로드할 노래가 없으면 더보기 버튼 숨기기
            }
            loading.style.display = 'none'; // 로딩 완료 후 로딩 아이콘 숨기기
            loadingData = false; // 로딩 상태 해제
        })
        .catch(error => {
            console.error('Error fetching songs:', error); // 오류 발생 시 메시지 출력
            loading.style.display = 'none'; // 오류 발생 시 로딩 아이콘 숨기기
            loadingData = false; // 로딩 상태 해제
            loadMoreBtn.style.display = 'block'; // 오류 발생 시 더보기 버튼 다시 표시
        });
    }
    
    // 더보기 버튼 상태 업데이트 함수
    function updateLoadMoreButton() {
        const loadedSongsCount = songsBody.getElementsByClassName('song-row').length; // 로드된 노래 개수 확인
        if (loadedSongsCount >= totalSongsCount) {
            loadMoreBtn.style.display = 'none'; // 모든 노래가 로드되면 더보기 버튼 숨기기
        } else if (loadedSongsCount % pageSize === 0 && loadedSongsCount !== 0) {
            loadMoreBtn.style.display = 'block'; // 노래가 페이지 크기만큼 로드되었을 때 더보기 버튼 표시
        } else {
            loadMoreBtn.style.display = 'none'; // 그 외의 경우에는 버튼 숨기기
        }
    }
    
    // 노래 목록에 노래 추가 함수 수정
function appendSongs(songs) {
    songs.forEach((song, index) => {
        const row = createSongRow(song, currentPage * pageSize + index + 1);
        songsBody.appendChild(row);
        
        // 새로 추가된 행의 좋아요 아이콘 초기 상태 설정
        const icon = row.querySelector('.heart-icon');
        updateLikeIconInitialState(icon, song.songId);
    });
}
    
    // 노래 행 생성 함수
    function createSongRow(song, index) {
        const row = document.createElement('tr');
        row.className = 'song-row';
        row.innerHTML = `
            <td><input type="checkbox" class="songCheckbox" data-song-id="${song.songId}" /></td>
            <td>${index}</td>
            <td class="song-info">
                <a href="/album/detail?albumId=${song.albumId}" class="album-link">
                  <img alt="앨범표지" src="/images/albumcover/${song.albumImage}" class="img-fluid" />
                </a>
                <div>
                    <a href="/song/detail?songId=${song.songId}" style="font: inherit; color: inherit; text-decoration: none;">
                        <span>${song.title}</span><br>
                    </a>
                    ${song.groupNames && song.groupNames.length > 0 ? 
                        song.groupNames.map((groupName, index) => `
                        <a href="/group/songs?groupId=${song.groupIds[index]}"
                            style="color: gray; text-decoration: none;"
                            onmouseover="this.style.color='#007bff';"
                            onmouseout="this.style.color='gray';">
                            ${groupName.trim()}
                        </a>${index < song.groupNames.length - 1 ? ', ' : ''}
                        `).join('') :
                        song.artistNames && song.artistNames.length > 0 ?
                        song.artistNames.map((artistName, index) => `
                            <a href="/artist/songs?artistId=${song.artistIds[index]}"
                            style="color: gray; text-decoration: none;"
                            onmouseover="this.style.color='#007bff';"
                            onmouseout="this.style.color='gray';">
                            ${artistName.trim()}
                            </a>${index < song.artistNames.length - 1 ? ', ' : ''}
                        `).join('') :
                        '<span>정보 없음</span>'
                    }
                </div>
            </td>
            <td>
                <a href="/album/detail?albumId=${song.albumId}"
                    style="color: gray; text-decoration: none;"
                    onmouseover="this.style.color='#007bff';"
                    onmouseout="this.style.color='gray';" class="album-link">
                    <span class="album-name">${song.albumName}</span>
                </a>
            </td>
            <td>
                <i class="fas fa-heart heart-icon ${song.likes && song.likes > 0 ? 'liked' : ''}"
                   data-song-id="${song.songId}"></i>
                <span class="likes-count">${song.likes || 0}</span>
            </td>
            <td>
                <button class="btn btn-primary btn-sm play-btn icon-button" 
                    id="listenBtn"
                    data-song-path="/songs/${song.songPath}"
                    data-song-id="${song.songId}" 
                    data-id="${song.songId}">
                    <img alt="듣기" src="/images/icon/play.png" />
                </button>
            </td>
            <td>
                <button type="button" class="icon-button addCPListBtn"
                        data-id="${song.songId}">
                    <img alt="재생목록" src="/images/icon/playList.png" />
                </button>
            </td>
            <td>
                <button type="button" class="btn btn-secondary btn-sm add-to-playlist-btn icon-button" 
                    data-song-id="${song.songId}">
                    <img alt="담기" src="/images/icon/myPlayList.png" />
                </button>
            </td>
            <td>
                ${song.videoLink ? `
                    <a href="${song.videoLink}" target="_blank" class="icon-button video-link">
                        <i class="fas fa-video"></i>
                    </a>
                ` : '<i class="fa-solid fa-video-slash video-link"></i>'}
            </td>
        `;
        return row;
    }
    
    // 플로팅 버튼 그룹 업데이트 함수
    function updateButtonGroup() {
        const checkedBoxes = document.querySelectorAll('.songCheckbox:checked'); // 선택된 체크박스를 모두 가져옴
        const checkedCount = checkedBoxes.length; // 선택된 체크박스의 개수를 계산
        
        if (checkedCount > 0) {
            floatingButtonGroup.classList.remove('d-none'); // 선택된 노래가 있으면 플로팅 버튼 그룹을 표시
            floatingButtonGroup.querySelector('.selected-count').textContent = `${checkedCount}개 선택됨`; // 선택된 개수를 플로팅 버튼 그룹에 표시
        } else {
            floatingButtonGroup.classList.add('d-none'); // 선택된 노래가 없으면 플로팅 버튼 그룹을 숨김
        }
    }
    
    // 노래 재생 함수
    function playSong(songId) {
        const url = `/song/listen?songId=${songId}`; // 선택한 노래의 재생 URL 생성
        console.log(url);
        axios.get(url) // 서버에 요청을 보내 노래 재생
            .then(() => {
                console.log("성공"); // 요청 성공 시 로그 출력
                sessionStorage.setItem('index', 0); // 세션 스토리지에 재생 시작 인덱스를 0으로 설정
                sessionStorage.setItem('isAdded', 'Y'); // 재생 목록에 곡이 추가되었음을 세션 스토리지에 저장
                if (parent && parent.songFrame) { // 부모 프레임이 있는 경우 (내장된 재생 프레임이 존재할 때)
                    parent.songFrame.location.reload(); // 재생 프레임 새로고침
                }
                showAlert('선택한 음원을 재생합니다.', 2000); // 알림 표시
            })
            .catch(error => console.error("Error playing song:", error)); // 요청 실패 시 오류 메시지 출력
    }
    
    // 현재 재생목록에 노래 추가 함수
    function addToCurrentPlaylist(songId) {
        checkAndAddToPlaylist(songId, false); // 재생 목록에 곡 추가 전 체크하는 함수 호출
    }
    
    // 플레이리스트 모달 표시 함수
    function showPlaylistModal(songId) {
        if (!loginUserId) { // 로그인하지 않은 경우
            if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) { // 로그인 유도 알림창 표시
                const currentPath = encodeURIComponent(location.pathname + location.search); // 현재 경로를 인코딩하여 저장
                location.href = `/member/signin?targetUrl=${currentPath}`; // 로그인 페이지로 리다이렉트
            }
            return;
        }
        showPlayListModal(parseInt(loginUserId), [songId]); // 로그인 상태라면 플레이리스트 모달을 표시하여 곡을 추가
    }
    
    // 플레이리스트 불러오기 및 모달 표시 함수
    function showPlayListModal(userId, songIds) {
        axios.get(`/getPlayList/${userId}`) // 서버에서 사용자의 플레이리스트를 가져옴
            .then(response => {
                if (response.status === 200) {
                    const playLists = response.data; // 응답 데이터에서 플레이리스트 정보 추출
                    const playListsContainer = document.getElementById('playLists'); // 플레이리스트를 표시할 컨테이너 요소 가져오기
                    playListsContainer.innerHTML = ''; // 기존의 내용을 초기화
                    const btnAddSong = document.getElementById('btnAddSong'); // 곡 추가 버튼 가져오기
    
                    if (playLists.length === 0) { // 플레이리스트가 없을 경우
                        playListsContainer.innerHTML = '<p class="text-center text-muted">플레이리스트가 없습니다.</p>'; // 빈 플레이리스트 안내 메시지 표시
                        btnAddSong.style.display = 'none'; // 곡 추가 버튼 숨김
                    } else {
                        btnAddSong.style.display = 'block'; // 곡 추가 버튼 표시
                        playLists.forEach(list => {
                            const listElement = createPlaylistElement(list); // 각 플레이리스트의 요소를 생성
                            playListsContainer.appendChild(listElement); // 컨테이너에 추가
                        });
                    }
    
                    const hiddenInput = document.createElement('input'); // 숨겨진 input 요소 생성
                    hiddenInput.type = 'hidden';
                    hiddenInput.id = 'selectedSongIds'; // 곡 ID들을 저장할 ID 설정
                    hiddenInput.value = JSON.stringify(songIds); // 선택된 곡 ID들을 JSON으로 변환하여 저장
                    playListsContainer.appendChild(hiddenInput); // 컨테이너에 숨겨진 input 추가
    
                    const selectPlayListModal = new bootstrap.Modal(document.getElementById('selectPlayList')); // 모달 객체 생성
                    selectPlayListModal.show(); // 모달 표시
    
                    document.querySelectorAll('.playlist-btn').forEach(btn => { // 모든 플레이리스트 버튼에 클릭 이벤트 추가
                        btn.addEventListener('click', function() {
                            this.classList.toggle('selected'); // 클릭 시 선택/해제 토글
                        });
                    });
                }
            })
            .catch(error => console.error('Error fetching playlists:', error)); // 오류 처리
    }
    
    // 플레이리스트 요소 생성 함수
    function createPlaylistElement(list) {
        const listElement = document.createElement('div'); // 새로운 div 요소 생성
        listElement.classList.add('playlist-item', 'd-flex', 'align-items-center', 'mb-2'); // 클래스 추가하여 스타일 설정
        listElement.innerHTML = `
            <div class="playlist-button-container">
                <button class="playlist-btn btn btn-outline-success w-100" data-id="${list.plistId}">
                    <div class="d-flex align-items-center">
                        <div class="playlist-image">
                            <img src="${list.albumImage ? `/images/albumcover/${list.albumImage}` : '/images/icon/default.png'}" alt="Album cover" class="rounded">
                        </div>
                        <div class="playlist-name">${list.plistName}</div>
                    </div>
                </button>
            </div>
        `; // 플레이리스트의 이미지와 이름을 포함한 HTML 구조 생성
        return listElement; // 생성된 요소 반환
    }
    
    // 선택한 플레이리스트에 곡을 추가하는 함수
    function addSongToPlaylists() {
        const selectedPlaylists = document.querySelectorAll('#playLists .playlist-btn.selected'); // 선택된 플레이리스트 버튼들 가져오기
        const selectedPlaylistIds = Array.from(selectedPlaylists).map(btn => btn.dataset.id); // 선택된 플레이리스트들의 ID 배열로 변환
        const songIdsJson = document.getElementById('selectedSongIds').value; // 숨겨진 input에서 선택된 곡 ID들 가져오기
        const songIds = JSON.parse(songIdsJson); // JSON 형식의 곡 ID를 다시 배열로 변환
    
        if (selectedPlaylistIds.length === 0) { // 선택된 플레이리스트가 없는 경우
            alert('플레이리스트를 선택하세요.'); // 경고 메시지 표시
            return;
        }
    
        if (songIds.length === 0) { // 선택된 곡이 없는 경우
            alert('곡을 선택하세요.'); // 경고 메시지 표시
            return;
        }
    
        const alreadyAdded = {}; // 이미 추가된 곡 여부를 저장할 객체
        const promises = []; // Promise 배열 생성
    
        selectedPlaylistIds.forEach(plistId => { // 각 플레이리스트 ID에 대해
            alreadyAdded[plistId] = false; // 초기값을 false로 설정
            songIds.forEach(songId => { // 각 곡에 대해
                console.log(`플레이리스트 ${plistId}에 곡 ${songId} 확인 중`);
                const checkPromise = axios.post(`/checkSongInPlayList`, {
                    plistId: parseInt(plistId), // 재생목록 ID
                    songId: parseInt(songId) // 곡 ID
                }).then(response => {
                    console.log(`플레이리스트 ${plistId} 응답:`, response.data);
                    if (response.data === false) { // 재생목록에 없는 곡이라면
                        alreadyAdded[plistId] = true; // 추가 가능 상태로 변경
                    }
                }).catch(error => console.error('플레이리스트에서 곡 확인 중 오류가 발생했습니다:', error)); // 오류 처리
                
                promises.push(checkPromise); // 각 확인 작업을 Promise로 처리하여 배열에 저장
            });
        });
    
        Promise.all(promises).then(() => {
            const addedPlaylists = selectedPlaylistIds.filter(plistId => alreadyAdded[plistId]); // 추가된 곡들 필터링
            if (addedPlaylists.length > 0) { // 이미 추가된 곡이 있을 경우
                if (confirm('선택한 플레이리스트에 이미 추가된 곡입니다. 그래도 추가하시겠습니까?')) { // 확인 메시지
                    addSongsToSelectedPlaylists(selectedPlaylistIds, songIds); // 확인되면 재생목록에 추가
                } else {
                    const selectPlayListModal = bootstrap.Modal.getInstance(document.getElementById('selectPlayList')); // 모달 객체 가져오기
                    selectPlayListModal.hide(); // 모달 숨기기
                }
            } else {
                addSongsToSelectedPlaylists(selectedPlaylistIds, songIds); // 이미 추가된 곡이 없다면 그대로 추가
            }
        });
    }
    
    // 선택된 플레이리스트에 곡 추가
    function addSongsToSelectedPlaylists(selectedPlaylistIds, songIds) {
        const addPromises = selectedPlaylistIds.flatMap(plistId => { // 모든 플레이리스트에 대해
            return songIds.map(songId => { // 모든 곡에 대해
                return axios.post(`/addSongToPlayList`, { // 서버에 곡 추가 요청
                    plistId: parseInt(plistId),
                    songId: parseInt(songId)
                });
            });
        });
    
        Promise.all(addPromises) // 모든 추가 작업이 완료되면
            .then(responses => {
                const allSuccessful = responses.every(response => response && response.status === 200); // 모든 요청이 성공했는지 확인
                if (allSuccessful) {
                    alert('선택한 플레이리스트에 곡이 추가되었습니다.'); // 성공 메시지
                    const selectPlayListModal = bootstrap.Modal.getInstance(document.getElementById('selectPlayList')); // 모달 객체 가져오기
                    selectPlayListModal.hide(); // 모달 닫기
    
                    document.querySelectorAll('.songCheckbox:checked, #selectAllCheckbox:checked').forEach(checkbox => { // 모든 체크박스 해제
                        checkbox.checked = false;
                    });
                    floatingButtonGroup.classList.add('d-none'); // 플로팅 버튼 그룹 숨기기
    
                    const modalBackdrop = document.querySelector('.modal-backdrop'); // 모달 배경 요소 가져오기
                    if (modalBackdrop) {
                        modalBackdrop.style.opacity = '0'; // 배경 투명화
                    }
                }
            })
            .catch(error => {
                console.error('Error adding songs to playlist:', error); // 오류 처리
                alert('플레이리스트에 곡을 추가하는 중 오류가 발생했습니다.'); // 오류 메시지
            });
    }
    
    // 알림 메시지 표시 함수
    function showAlert(message, duration) {
        const existingAlert = document.querySelector('.custom-alert');
        if (existingAlert) {
            existingAlert.remove();
        }
    
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
    
        setTimeout(() => {
            alertBox.style.opacity = '1';
        }, 10);
    
        setTimeout(() => {
            alertBox.style.opacity = '0';
            setTimeout(() => {
                document.body.removeChild(alertBox);
            }, 500);
        }, duration);
    }
    
    // 재생목록에 곡 추가 함수
    function checkAndAddToPlaylist(id, skipConfirm, callback) {
        const url1 = `/song/getCPList?songId=${id}`; // 해당 곡이 재생목록에 있는지 확인하기 위한 API 호출 URL 설정
        axios.get(url1) // 서버에 해당 곡이 재생목록에 있는지 요청
            .then((response) => {
                if (!response.data || skipConfirm) { // 재생목록에 곡이 없거나, 확인 절차를 건너뛸 경우
                    addCurrentPlayList(id, skipConfirm, callback); // 곡을 재생목록에 추가하는 함수 호출
                } else {
                    // 이미 재생목록에 있는 경우 사용자에게 확인
                    if (confirm('이미 재생목록에 있는 곡입니다. 그래도 추가하시겠습니까?')) {
                        addCurrentPlayList(id, skipConfirm, callback); // 사용자가 확인하면 재생목록에 추가
                    } else if (callback) {
                        callback(); // 취소한 경우에도 콜백 함수 호출
                    }
                }
            })
            .catch((error) => {
                console.error(`재생목록에서 곡 ${id} 확인 중 오류:`, error); // 오류 발생 시 콘솔에 메시지 출력
                if (callback) callback(); // 오류가 발생해도 콜백 함수 호출
            });
    }
    
    // 현재 재생목록에 곡 추가 함수
    function addCurrentPlayList(id, skipAlert, callback) {
        const url = `/song/addCurrentPlayList?songId=${id}`; // 곡을 재생목록에 추가하는 API URL 설정
        console.log(url); // 요청 URL을 콘솔에 출력
        axios.get(url) // 서버에 재생목록 추가 요청
            .then((response) => {
                console.log(response); // 성공 응답 로그 출력
                if (sessionStorage.getItem('isAdded') === 'N') { // 재생목록에 곡이 추가되지 않았던 상태면
                    sessionStorage.setItem('index', 0); // 재생 시작 인덱스를 0으로 설정
                    sessionStorage.setItem('isAdded', 'Y'); // 재생목록에 곡이 추가되었음을 세션 스토리지에 저장
                    if (parent && parent.songFrame) { // 재생 프레임이 존재할 경우
                        parent.songFrame.location.reload(); // 재생 프레임을 새로고침
                    }
                }
                if (!skipAlert) { // 경고 메시지를 건너뛰지 않을 경우
                    showAlert('재생 목록에 추가되었습니다', 2000); // 알림 표시
                }
                if (callback) callback(); // 콜백 함수가 존재하면 호출
            })
            .catch((error) => {
                console.error(`재생목록에 곡 ${id} 추가 중 오류:`, error); // 오류 발생 시 콘솔에 메시지 출력
                if (callback) callback(); // 오류 발생 시에도 콜백 함수 호출
            });
    }
    
    // 선택된 곡을 재생하는 함수
    function playSelectedSong(songId) {
        const url = `/song/listen?songId=${songId}`; // 선택한 곡을 재생하는 API URL 설정
        console.log(url); // 요청 URL을 콘솔에 출력
        axios.get(url) // 서버에 곡 재생 요청
            .then(() => {
                console.log("성공"); // 성공 로그 출력
                sessionStorage.setItem('index', 0); // 재생 시작 인덱스를 0으로 설정
                sessionStorage.setItem('isAdded', 'Y'); // 곡이 재생목록에 추가되었음을 세션 스토리지에 저장
                if (parent && parent.songFrame) { // 재생 프레임이 존재할 경우
                    parent.songFrame.location.reload(); // 재생 프레임을 새로고침
                }
            })
            .catch((error) => console.error("선택된 곡 재생 중 오류:", error)); // 오류 발생 시 콘솔에 메시지 출력
    }
    
    // 모든 선택된 노래를 재생목록에 추가하는 함수 (플로팅 버튼용)
    async function addAllToPlaylist(songIds, skipAlert = false) {
        let addedCount = 0; // 추가된 곡의 개수를 저장할 변수
        const totalSongs = songIds.length; // 선택된 곡의 총 개수
    
        for (const songId of songIds) { // 선택된 각 곡에 대해
            await new Promise((resolve) => {
                checkAndAddToPlaylist(songId, true, () => { // 각 곡을 재생목록에 추가
                    addedCount++; // 추가된 곡의 개수를 증가
                    resolve(); // Promise 해결
                });
            });
        }
    
        if (!skipAlert) { // 경고 메시지를 건너뛰지 않을 경우
            showAlert(`${addedCount}개의 곡이 재생목록에 추가되었습니다`, 2000); // 추가된 곡의 수를 알림
        }
    }
    
    // 선택된 노래 중 하나라도 재생목록에 있는지 확인하는 함수
    function checkAnyInPlaylist(songIds) {
        return Promise.any(songIds.map(songId => 
            axios.get(`/song/getCPList?songId=${songId}`) // 각 곡에 대해 서버에 재생목록 포함 여부 확인 요청
                .then(response => response.data ? Promise.resolve(true) : Promise.reject(false)) // 곡이 있으면 true, 없으면 false 반환
        )).then(() => true).catch(() => false); // 곡이 재생목록에 있으면 true, 없으면 false 반환
    }
    
    // 클릭된 'play-btn' 버튼에 대한 처리 함수
    function clickListenBtn(event) {
        const button = event.target.closest('.play-btn'); // 클릭된 요소가 'play-btn' 버튼인지 확인
        if (button) {
            const id = button.getAttribute('data-id'); // 버튼의 곡 ID 속성 가져오기
            const url = `/song/listen?songId=${id}`; // 해당 곡을 재생하는 API URL 설정
            console.log(url); // 요청 URL을 콘솔에 출력
            
            axios.get(url) // 서버에 곡 재생 요청
                .then(response => {
                    console.log("성공"); // 성공 로그 출력
                    sessionStorage.setItem('index', 0); // 재생 시작 인덱스를 0으로 설정
                    sessionStorage.setItem('isAdded', 'Y'); // 곡이 재생목록에 추가되었음을 세션 스토리지에 저장
    
                    if (parent && parent.songFrame) { // 재생 프레임이 존재할 경우
                        try {
                            parent.songFrame.location.reload(); // 재생 프레임 새로고침
                        } catch (error) {
                            console.error("Unable to reload the song frame:", error); // 재생 프레임 새로고침 오류 처리
                        }
                    } else {
                        console.warn("Parent songFrame is not available."); // 재생 프레임이 없을 때 경고 출력
                    }
    
                    showAlert('선택한 음원을 재생합니다.', 2000); // 곡 재생 알림 표시
                })
                .catch(error => {
                    console.error("Error occurred while listening to the song:", error); // 곡 재생 중 오류 처리
                });
        }
    }
    
    // 초기화 함수
    function init() {
        if (!sessionStorage.getItem('isAdded')) { // 세션 스토리지에 'isAdded' 값이 없으면
            sessionStorage.setItem('isAdded', 'N'); // 'isAdded' 값을 'N'으로 설정 (재생목록에 추가되지 않은 상태)
        }
    
        if (typeof refresh !== 'undefined' && refresh === 'Y') { // 'refresh' 변수가 'Y'로 설정된 경우
            console.log(refresh); // 로그에 refresh 값 출력
            const uri21 = `${window.location.origin}/user/removeRefresh`; // 리프레시 제거 API 호출 URL 설정
            axios.get(uri21) // 서버에 리프레시 제거 요청
                .then((response) => console.log(response)) // 응답 성공 시 로그 출력
                .catch((error) => console.error(error)); // 오류 발생 시 콘솔에 메시지 출력
            parent.songFrame.location.reload(); // 재생 프레임 새로고침
        }
        
        setupEventListeners(); // 이벤트 리스너 설정 함수 호출
        fetchSongs(currentPage); // 현재 페이지의 노래 목록을 가져오는 함수 호출
        setupLikeIconHandlers(); // 좋아요 아이콘 핸들러 설정
    }
    
    // 초기화 함수 호출
    init();
    
});
