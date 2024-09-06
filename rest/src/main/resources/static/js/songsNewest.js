/**
 * /song/newest.html에 포함
 */

document.addEventListener("DOMContentLoaded", function() {
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

    // 좋아요 아이콘 이벤트 핸들러 설정 함수
    function setupLikeIconHandlers() {
        const heartIcons = document.querySelectorAll('.heart-icon'); // 모든 좋아요 아이콘을 선택
        
        heartIcons.forEach(icon => {
            const songId = icon.dataset.songId; // 각 아이콘의 데이터 속성에서 songId를 가져옴
            let likesCountElement = icon.nextElementSibling; // 좋아요 개수를 표시하는 요소를 가져옴
            
            // 특정 사용자가 특정 노래를 좋아요 했는지 여부를 서버에 요청
            const data = { songId, loginUserId }; // 서버에 보낼 데이터 (songId와 사용자 ID)
            axios.post('/api/isLiked', data)
                .then(response => {
                    // 서버 응답에 따라 좋아요 상태를 설정
                    updateLikeIconState(icon, response.data);
    
                    // 아이콘 클릭 이벤트 리스너 추가
                    icon.addEventListener('click', function() {
                        handleLikeIconClick(icon, songId, likesCountElement);
                    });
                })
                .catch(error => {
                    console.error('좋아요 상태 가져오는 중 오류:', error);
                    // 오류 발생 시 기본 상태 설정
                    updateLikeIconState(icon, false);
                });
        });
    }

    // 좋아요 아이콘 상태 업데이트 함수
    function updateLikeIconState(icon, isLiked) {
        if (isLiked) {
            icon.classList.add('liked');
            icon.classList.remove('far');
            icon.classList.add('fas');
            icon.style.color = 'red';
        } else {
            icon.classList.remove('liked');
            icon.classList.remove('fas');
            icon.classList.add('far');
            icon.style.color = 'black';
        }
    }

    // 좋아요 아이콘 클릭 처리 함수
    function handleLikeIconClick(icon, songId, likesCountElement) {
        if (!loginUserId) {
            if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) {
                const currentPath = encodeURIComponent(location.pathname + location.search);
                location.href = `/member/signin?targetUrl=${currentPath}`;
            }
            return;
        }

        const data = { songId, loginUserId };
        if (icon.classList.contains('liked')) {
            // 좋아요 취소
            axios.delete(`/api/song/cancelLike/${songId}/${loginUserId}`)
                .then(response => {
                    if (response.status === 200) {
                        updateLikeIconState(icon, false);
                        likesCountElement.textContent = response.data;
                    }
                })
                .catch(error => console.error('좋아요 제거 중 오류:', error));
        } else {
            // 좋아요 추가
            axios.post('/api/song/addLike', data)
                .then(response => {
                    if (response.status === 200) {
                        updateLikeIconState(icon, true);
                        likesCountElement.textContent = response.data;
                    }
                })
                .catch(error => console.error('좋아요 추가 중 오류:', error));
        }
    }
    
    // 더보기 버튼 클릭 시 실행되는 함수
    function loadMoreSongs() {
        fetchSongs(currentPage);
    }
    
    // 전체 선택 체크박스 처리 함수
    function handleSelectAll() {
        const isChecked = selectAllCheckbox.checked;
        document.querySelectorAll('.songCheckbox').forEach(checkbox => {
            checkbox.checked = isChecked;
        });
        updateButtonGroup();
    }
    
    // 플로팅 버튼 그룹 클릭 처리 함수 수정
    function handleFloatingButtonClick(event) {
        const target = event.target.closest('button');
        if (!target) return;
        
        const selectedSongs = getSelectedSongIds();
        if (selectedSongs.length === 0) return;

        if (target.classList.contains('play-selected')) {
            playSelectedSongs(selectedSongs);
        } else if (target.classList.contains('add-to-playlist')) {
            addSelectedToPlaylist(selectedSongs);
        } else if (target.classList.contains('add-to-my-list')) {
            addSelectedToMyList(selectedSongs);
        } else if (target.classList.contains('deselect-all')) {
            deselectAll();
        }
    }
    
    // 선택된 노래 ID 배열 반환 함수
    function getSelectedSongIds() {
        return Array.from(document.querySelectorAll('.songCheckbox:checked')).map(checkbox => checkbox.dataset.songId);
    }
    
    // 테이블 body 클릭 이벤트 처리 함수
    function handleTableBodyClick(event) {
        const target = event.target;
        
        if (target.closest('.play-btn')) {
            const songId = target.closest('.play-btn').getAttribute('data-id');
            playSong(songId);
        } else if (target.closest('.addCPListBtn')) {
            const songId = target.closest('.addCPListBtn').getAttribute('data-id');
            addToCurrentPlaylist(songId);
        } else if (target.closest('.add-to-playlist-btn')) {
            const songId = target.closest('.add-to-playlist-btn').getAttribute('data-song-id');
            showPlaylistModal(songId);
        }
    }
    
    // 체크박스 변경 이벤트 처리 함수
    function handleCheckboxChange() {
        updateButtonGroup();
    }
    
    // 선택된 노래 재생 함수 수정
    function playSelectedSongs(selectedSongs) {
        if (selectedSongs.length === 0) return;
        
        playSelectedSong(selectedSongs[0]);
        if (selectedSongs.length > 1) {
            addAllToPlaylist(selectedSongs.slice(1), true);
        }
        showAlert(selectedSongs.length === 1 ? '선택한 음원을 재생합니다.' : '선택한 곡들을 재생합니다.', 2000);
    }
    
    // 선택된 노래를 재생목록에 추가하는 함수 수정
    function addSelectedToPlaylist(selectedSongs) {
        if (selectedSongs.length === 0) return;
        
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
    
    // 선택된 노래를 내 리스트에 추가하는 함수
    function addSelectedToMyList(selectedSongs) {
        if (selectedSongs.length === 0) return;
        
        if (!loginUserId) {
            if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) {
                const currentPath = encodeURIComponent(location.pathname + location.search);
                location.href = `/member/signin?targetUrl=${currentPath}`;
            }
            return;
        }
        
        showPlayListModal(parseInt(loginUserId), selectedSongs);
    }
    
    // 모든 선택 해제 함수
    function deselectAll() {
        document.querySelectorAll('.songCheckbox, #selectAllCheckbox').forEach(checkbox => {
            checkbox.checked = false;
        });
        updateButtonGroup();
    }
    
    // 선택된 노래 ID 배열 반환 함수
    function getSelectedSongIds() {
        return Array.from(document.querySelectorAll('.songCheckbox:checked')).map(checkbox => checkbox.dataset.songId);
    }
    
    // 노래 데이터 가져오기 함수
    function fetchSongs(page) {
        if (loadingData) return;
        loadingData = true;
        loading.style.display = 'block';
        loadMoreBtn.style.display = 'none';
    
        axios.get('/song/api/newest', {
            params: { page: page, size: pageSize }
        })
        .then(response => {
            const data = response.data;
            const songs = data.songs;
            totalSongsCount = data.totalCount;
    
            if (songs.length > 0) {
                appendSongs(songs);
                currentPage++;
                updateLoadMoreButton();
            } else {
                loadMoreBtn.style.display = 'none';
            }
            loading.style.display = 'none';
            loadingData = false;
        })
        .catch(error => {
            console.error('Error fetching songs:', error);
            loading.style.display = 'none';
            loadingData = false;
            loadMoreBtn.style.display = 'block';
        });
    }
    
    // 더보기 버튼 상태 업데이트 함수
    function updateLoadMoreButton() {
        const loadedSongsCount = songsBody.getElementsByClassName('song-row').length;
        if (loadedSongsCount >= totalSongsCount) {
            loadMoreBtn.style.display = 'none'; // 모든 노래를 로드했으면 버튼 숨기기
        } else if (loadedSongsCount % pageSize === 0 && loadedSongsCount !== 0) {
            loadMoreBtn.style.display = 'block'; // 페이지의 노래 수가 pageSize의 배수일 때 버튼 표시
        } else {
            loadMoreBtn.style.display = 'none'; // 페이지에 표시된 노래가 pageSize 미만일 때 버튼 숨기기
        }
    }
    
    // 노래 목록에 노래 추가 함수
    function appendSongs(songs) {
        songs.forEach((song, index) => {
            const row = createSongRow(song, currentPage * pageSize + index + 1);
            songsBody.appendChild(row);
        });
        // 새로 추가된 노래들에 대해 좋아요 아이콘 이벤트 핸들러 설정
        setupLikeIconHandlers();
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
        const checkedBoxes = document.querySelectorAll('.songCheckbox:checked');
        const checkedCount = checkedBoxes.length;
        
        if (checkedCount > 0) {
            floatingButtonGroup.classList.remove('d-none');
            floatingButtonGroup.querySelector('.selected-count').textContent = `${checkedCount}개 선택됨`;
        } else {
            floatingButtonGroup.classList.add('d-none');
        }
    }
    
    // 노래 재생 함수
    function playSong(songId) {
        const url = `/song/listen?songId=${songId}`;
        console.log(url);
        axios.get(url)
            .then(() => {
                console.log("성공");
                sessionStorage.setItem('index', 0);
                sessionStorage.setItem('isAdded', 'Y');
                if (parent && parent.songFrame) {
                    parent.songFrame.location.reload();
                }
                showAlert('선택한 음원을 재생합니다.', 2000);
            })
            .catch(error => console.error("Error playing song:", error));
    }
    
    // 현재 재생목록에 노래 추가 함수
    function addToCurrentPlaylist(songId) {
        checkAndAddToPlaylist(songId, false);
    }
    
    // 플레이리스트 모달 표시 함수
    function showPlaylistModal(songId) {
        if (!loginUserId) {
            if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) {
                const currentPath = encodeURIComponent(location.pathname + location.search);
                location.href = `/member/signin?targetUrl=${currentPath}`;
            }
            return;
        }
        showPlayListModal(parseInt(loginUserId), [songId]);
    }
    
    // 플레이리스트 불러오기 및 모달 표시 함수
    function showPlayListModal(userId, songIds) {
        axios.get(`/getPlayList/${userId}`)
            .then(response => {
                if (response.status === 200) {
                    const playLists = response.data;
                    const playListsContainer = document.getElementById('playLists');
                    playListsContainer.innerHTML = '';
                    const btnAddSong = document.getElementById('btnAddSong');
    
                    if (playLists.length === 0) {
                        playListsContainer.innerHTML = '<p class="text-center text-muted">플레이리스트가 없습니다.</p>';
                        btnAddSong.style.display = 'none';
                    } else {
                        btnAddSong.style.display = 'block';
                        playLists.forEach(list => {
                            const listElement = createPlaylistElement(list);
                            playListsContainer.appendChild(listElement);
                        });
                    }
    
                    const hiddenInput = document.createElement('input');
                    hiddenInput.type = 'hidden';
                    hiddenInput.id = 'selectedSongIds';
                    hiddenInput.value = JSON.stringify(songIds);
                    playListsContainer.appendChild(hiddenInput);
    
                    const selectPlayListModal = new bootstrap.Modal(document.getElementById('selectPlayList'));
                    selectPlayListModal.show();
    
                    document.querySelectorAll('.playlist-btn').forEach(btn => {
                        btn.addEventListener('click', function() {
                            this.classList.toggle('selected');
                        });
                    });
                }
            })
            .catch(error => console.error('Error fetching playlists:', error));
    }
    
    // 플레이리스트 요소 생성 함수
    function createPlaylistElement(list) {
        const listElement = document.createElement('div');
        listElement.classList.add('playlist-item', 'd-flex', 'align-items-center', 'mb-2');
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
        `;
        return listElement;
    }
    
    // 선택한 플레이리스트에 곡을 추가하는 함수
    function addSongToPlaylists() {
        const selectedPlaylists = document.querySelectorAll('#playLists .playlist-btn.selected');
        const selectedPlaylistIds = Array.from(selectedPlaylists).map(btn => btn.dataset.id);
        const songIdsJson = document.getElementById('selectedSongIds').value;
        const songIds = JSON.parse(songIdsJson);
        
        if (selectedPlaylistIds.length === 0) {
            alert('플레이리스트를 선택하세요.');
            return;
        }
        
        if (songIds.length === 0) {
            alert('곡을 선택하세요.');
            return;
        }
    
        const alreadyAdded = {};
        const promises = [];
        
        selectedPlaylistIds.forEach(plistId => {
            alreadyAdded[plistId] = false;
            songIds.forEach(songId => {
                console.log(`플레이리스트 ${plistId}에 곡 ${songId} 확인 중`);
                const checkPromise = axios.post(`/checkSongInPlayList`, {
                    plistId: parseInt(plistId),
                    songId: parseInt(songId)
                }).then(response => {
                    console.log(`플레이리스트 ${plistId} 응답:`, response.data);
                    if (response.data === false) {
                        alreadyAdded[plistId] = true;
                    }
                }).catch(error => console.error('플레이리스트에서 곡 확인 중 오류가 발생했습니다:', error));
                
                promises.push(checkPromise);
            });
        });
        
        Promise.all(promises).then(() => {
            const addedPlaylists = selectedPlaylistIds.filter(plistId => alreadyAdded[plistId]);
            if (addedPlaylists.length > 0) {
                if (confirm('선택한 플레이리스트에 이미 추가된 곡입니다. 그래도 추가하시겠습니까?')) {
                    addSongsToSelectedPlaylists(selectedPlaylistIds, songIds);
                } else {
                    const selectPlayListModal = bootstrap.Modal.getInstance(document.getElementById('selectPlayList'));
                    selectPlayListModal.hide();
                }
            } else {
                addSongsToSelectedPlaylists(selectedPlaylistIds, songIds);
            }
        });
    }
    
    // 선택된 플레이리스트에 곡 추가
    function addSongsToSelectedPlaylists(selectedPlaylistIds, songIds) {
        const addPromises = selectedPlaylistIds.flatMap(plistId => {
            return songIds.map(songId => {
                return axios.post(`/addSongToPlayList`, {
                    plistId: parseInt(plistId),
                    songId: parseInt(songId)
                });
            });
        });
        
        Promise.all(addPromises)
            .then(responses => {
                const allSuccessful = responses.every(response => response && response.status === 200);
                if (allSuccessful) {
                    alert('선택한 플레이리스트에 곡이 추가되었습니다.');
                    const selectPlayListModal = bootstrap.Modal.getInstance(document.getElementById('selectPlayList'));
                    selectPlayListModal.hide();

                    document.querySelectorAll('.songCheckbox:checked, #selectAllCheckbox:checked').forEach(checkbox => {
                        checkbox.checked = false;
                    });
                    floatingButtonGroup.classList.add('d-none');

                    const modalBackdrop = document.querySelector('.modal-backdrop');
                    if (modalBackdrop) {
                        modalBackdrop.style.opacity = '0';
                    }
                }
            })
            .catch(error => {
                console.error('Error adding songs to playlist:', error);
                alert('플레이리스트에 곡을 추가하는 중 오류가 발생했습니다.');
            });
    }
    
    // 알림 메시지 표시 함수 수정 (기존 코드 그대로 사용)
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
        const url1 = `/song/getCPList?songId=${id}`;
        axios.get(url1)
            .then((response) => {
                if (!response.data || skipConfirm) {
                    addCurrentPlayList(id, skipConfirm, callback);
                } else {
                    if (confirm('이미 재생목록에 있는 곡입니다. 그래도 추가하시겠습니까?')) {
                        addCurrentPlayList(id, skipConfirm, callback);
                    } else if (callback) {
                        callback();
                    }
                }
            })
            .catch((error) => {
                console.error(`Error checking song ${id} in playlist:`, error);
                if (callback) callback();
            });
    }
    
    // 현재 재생목록에 곡 추가 함수
    function addCurrentPlayList(id, skipAlert, callback) {
        const url2 = `/song/addCurrentPlayList?songId=${id}`;
        console.log(url2);
        axios.get(url2)
            .then((response) => {
                console.log(response);
                if (sessionStorage.getItem('isAdded') === 'N') {
                    sessionStorage.setItem('index', 0);
                    sessionStorage.setItem('isAdded', 'Y');
                    parent.songFrame.location.reload();
                }
                if (!skipAlert) {
                    showAlert('재생 목록에 추가되었습니다', 2000);
                }
                if (callback) callback();
            })
            .catch((error) => {
                console.error(`Error adding song ${id} to playlist:`, error);
                if (callback) callback();
            });
    }
    
    // 선택된 곡을 재생하는 함수
    function playSelectedSong(songId) {
        const url = `/song/listen?songId=${songId}`;
        console.log(url);
        axios.get(url)
            .then(() => {
                console.log("성공");
                sessionStorage.setItem('index', 0);
                sessionStorage.setItem('isAdded', 'Y');
                if (parent && parent.songFrame) {
                    parent.songFrame.location.reload();
                }
            })
            .catch((error) => console.error("Error playing song:", error));
    }
    
    // 모든 선택된 노래를 재생목록에 추가하는 함수
    function addAllToPlaylist(songIds, skipAlert = false) {
        let addedCount = 0;
        const totalSongs = songIds.length;
        
        // Promise.all을 사용하여 모든 노래 추가가 완료될 때까지 기다림
        Promise.all(songIds.map(songId => 
            new Promise((resolve) => {
                checkAndAddToPlaylist(songId, true, () => {
                    addedCount++;
                    resolve();
                });
            })
        )).then(() => {
            // 모든 노래가 추가된 후 알림 표시
            if (!skipAlert) {
                showAlert('재생 목록에 추가되었습니다', 2000);
            }
        });
    }
    
    // 선택된 노래 중 하나라도 재생목록에 있는지 확인하는 함수
    function checkAnyInPlaylist(songIds) {
        return Promise.any(songIds.map(songId => 
            axios.get(`/song/getCPList?songId=${songId}`)
                .then(response => response.data ? Promise.resolve(true) : Promise.reject(false))
        )).then(() => true).catch(() => false);
    }
    
    // 클릭된 'play-btn' 버튼에 대한 처리 함수
    function clickListenBtn(event) {
        const button = event.target.closest('.play-btn');
        if (button) {
            const id = button.getAttribute('data-id');
            const url = `/song/listen?songId=${id}`;
            console.log(url);
            
            axios.get(url)
                .then(response => {
                    console.log("성공");
                    sessionStorage.setItem('index', 0);
                    sessionStorage.setItem('isAdded', 'Y');
    
                    if (parent && parent.songFrame) {
                        try {
                            parent.songFrame.location.reload();
                        } catch (error) {
                            console.error("Unable to reload the song frame:", error);
                        }
                    } else {
                        console.warn("Parent songFrame is not available.");
                    }
    
                    showAlert('선택한 음원을 재생합니다.', 2000);
                })
                .catch(error => {
                    console.error("Error occurred while listening to the song:", error);
                });
        }
    }
    
    // 초기화 함수
    function init() {
        setupEventListeners();
        fetchSongs(currentPage);
    
        if (!sessionStorage.getItem('isAdded')) {
            sessionStorage.setItem('isAdded', 'N');
        }
    
        if (typeof refresh !== 'undefined' && refresh === 'Y') {
            console.log(refresh);
            const uri21 = `${window.location.origin}/user/removeRefresh`;
            axios.get(uri21)
                .then((response) => console.log(response))
                .catch((error) => console.error(error));
            parent.songFrame.location.reload();
        }
    }
    
    // 초기화 함수 호출
    init();
    
});
