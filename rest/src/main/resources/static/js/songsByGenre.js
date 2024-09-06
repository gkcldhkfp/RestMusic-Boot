/**
 * /song/genreChart.html에 포함
 */

document.addEventListener("DOMContentLoaded", function() {
    // 로그인한 사용자 ID 로그 출력
    console.log('loginUserId= ' + loginUserId);
    
    // 전역 변수 초기화
    let currentPage = 0; // 현재 페이지 번호
    const pageSize = 30; // 한 페이지당 표시할 곡 수
    let loadingData = false; // 데이터 로딩 중 여부
    let totalSongsCount = 0; // 전체 곡 수
    let selectedGenre = new URLSearchParams(window.location.search).get('genreName') || '전체'; // 선택된 장르

    // DOM 요소 선택
    const songsBody = document.getElementById('songsBody'); // 노래 목록 테이블 본문
    const loading = document.getElementById('loading'); // 로딩 인디케이터
    const loadMoreBtn = document.getElementById('loadMoreBtn'); // 더보기 버튼
    const genreButtonsContainer = document.getElementById('genreButtons'); // 장르 버튼 컨테이너
    const noChartMessage = document.getElementById('noChartMessage'); // 차트 없음 메시지
    const chartTable = document.querySelector('.container .table'); // 차트 테이블
    const floatingButtonGroup = document.getElementById('floatingButtonGroup'); // 플로팅 버튼 그룹
    const selectAllCheckbox = document.getElementById('selectAllCheckbox'); // 전체 선택 체크박스

    // 노래 데이터 가져오기 함수
    function fetchSongs(page, genre) {
        if (loadingData) return; // 이미 로딩 중이면 함수 종료
        loadingData = true;
        loading.style.display = 'block'; // 로딩 인디케이터 표시
        loadMoreBtn.style.display = 'none'; // 더보기 버튼 숨김

        axios.get('/song/api/genreChart', {
            params: { page: page, size: pageSize, genreName: genre }
        })
        .then(response => {
            const { songs, totalSongsCount: total } = response.data;
            totalSongsCount = total;

            if (songs.length > 0) {
                appendSongs(songs); // 노래 목록에 추가
                currentPage++; // 페이지 번호 증가
                updateLoadMoreButton(); // 더보기 버튼 상태 업데이트
                noChartMessage.classList.add('d-none'); // 차트 없음 메시지 숨김
                chartTable.classList.remove('d-none'); // 차트 테이블 표시
            } else if (currentPage === 0) {
                noChartMessage.classList.remove('d-none'); // 차트 없음 메시지 표시
                chartTable.classList.add('d-none'); // 차트 테이블 숨김
            }

            loading.style.display = 'none'; // 로딩 인디케이터 숨김
            loadingData = false;
        })
        .catch(error => {
            console.error('Error fetching songs:', error);
            loading.style.display = 'none';
            loadingData = false;
            loadMoreBtn.style.display = 'block';
            alert('노래를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.');
        });
    }

    // 노래 목록에 노래 추가 함수
    function appendSongs(songs) {
        songs.forEach((song, index) => {
            const row = createSongRow(song, currentPage * pageSize + index + 1);
            songsBody.appendChild(row);
        });
        updateLoadMoreButton();
        setupEventListeners();
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

    // 더보기 버튼 상태 업데이트 함수
    function updateLoadMoreButton() {
        const loadedSongsCount = songsBody.getElementsByClassName('song-row').length;
        if (loadedSongsCount >= totalSongsCount) {
            loadMoreBtn.style.display = 'none'; // 모든 노래를 로드했으면 버튼 숨김
        } else {
            loadMoreBtn.style.display = 'block'; // 더 로드할 노래가 있으면 버튼 표시
        }
    }

    // 더보기 버튼 클릭 이벤트
    loadMoreBtn.addEventListener('click', () => fetchSongs(currentPage, selectedGenre));

    // 이벤트 리스너 설정
    function setupEventListeners() {
        setupLikeIcons();
        setupCheckboxes();
        setupFloatingButtonGroup();
        setupPlayButtons();
        setupPlaylistEvents();
    }
    
    // 좋아요 아이콘 이벤트 설정 함수
    function setupLikeIcons() {
        const heartIcons = document.querySelectorAll('.heart-icon');
        
        heartIcons.forEach(icon => {
            const songId = icon.dataset.songId;
            const likesCountElement = icon.nextElementSibling;
            
            axios.post('/api/isLiked', { songId, loginUserId })
                .then(response => {
                    updateLikeIconState(icon, response.data);
                    icon.addEventListener('click', () => handleLikeClick(icon, songId, likesCountElement));
                })
                .catch(error => {
                    console.error('Error fetching like status:', error);
                    updateLikeIconState(icon, false);
                });
        });
    }

    // 좋아요 아이콘 상태 업데이트 함수
    function updateLikeIconState(icon, isLiked) {
        icon.classList.toggle('liked', isLiked);
        icon.classList.toggle('fas', isLiked);
        icon.classList.toggle('far', !isLiked);
        icon.style.color = isLiked ? 'red' : 'black';
    }

    // 좋아요 클릭 처리 함수
    function handleLikeClick(icon, songId, likesCountElement) {
        if (!loginUserId) {
            if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) {
                const currentPath = encodeURIComponent(location.pathname + location.search);
                location.href = `/member/signin?targetUrl=${currentPath}`;
            }
            return;
        }

        const isLiked = icon.classList.contains('liked');
        const url = isLiked ? `/api/song/cancelLike/${songId}/${loginUserId}` : '/api/song/addLike';
        const method = isLiked ? 'delete' : 'post';

        axios[method](url, isLiked ? null : { songId, loginUserId })
            .then(response => {
                if (response.status === 200) {
                    updateLikeIconState(icon, !isLiked);
                    likesCountElement.textContent = response.data;
                }
            })
            .catch(error => console.error(`Error ${isLiked ? 'removing' : 'adding'} like:`, error));
    }

    // 체크박스 이벤트 설정 함수
    function setupCheckboxes() {
        const songCheckboxes = document.querySelectorAll('.songCheckbox');
        songCheckboxes.forEach(checkbox => checkbox.addEventListener('change', updateButtonGroup));
        selectAllCheckbox.addEventListener('change', handleSelectAll);
    }

    // 플로팅 버튼 그룹 이벤트 설정 함수
    function setupFloatingButtonGroup() {
        const deselectAllButton = floatingButtonGroup.querySelector('.deselect-all');
        deselectAllButton.addEventListener('click', deselectAll);
        
        floatingButtonGroup.querySelector('.play-selected').addEventListener('click', playSelectedSongs);
        floatingButtonGroup.querySelector('.add-to-playlist').addEventListener('click', addSelectedToPlaylist);
        floatingButtonGroup.querySelector('.add-to-my-list').addEventListener('click', addSelectedToMyList);
    }

    // 버튼 그룹 업데이트 함수
    function updateButtonGroup() {
        const checkedBoxes = document.querySelectorAll('.songCheckbox:checked');
        const checkedCount = checkedBoxes.length;
        
        floatingButtonGroup.classList.toggle('d-none', checkedCount === 0);
        floatingButtonGroup.querySelector('.selected-count').textContent = `${checkedCount}개 선택됨`;
    }

    // 전체 선택 처리 함수
    function handleSelectAll() {
        const isChecked = selectAllCheckbox.checked;
        document.querySelectorAll('.songCheckbox').forEach(checkbox => checkbox.checked = isChecked);
        updateButtonGroup();
    }

    // 전체 선택 해제 함수
    function deselectAll() {
        document.querySelectorAll('.songCheckbox, #selectAllCheckbox').forEach(checkbox => checkbox.checked = false);
        updateButtonGroup();
    }

    // 선택된 노래 재생 함수
    function playSelectedSongs() {
        const selectedSongs = getSelectedSongIds();
        if (selectedSongs.length > 0) {
            playSelectedSong(selectedSongs[0]);
            if (selectedSongs.length > 1) {
                addAllToPlaylist(selectedSongs.slice(1), true);
            }
            showAlert(selectedSongs.length === 1 ? '선택한 음원을 재생합니다.' : '선택한 곡들을 재생합니다.', 2000);
        }
    }

    // 선택된 노래를 재생목록에 추가하는 함수
    function addSelectedToPlaylist() {
        const selectedSongs = getSelectedSongIds();
        if (selectedSongs.length > 0) {
            checkAnyInPlaylist(selectedSongs).then(anyInPlaylist => {
                if (anyInPlaylist && confirm('이미 재생목록에 있는 곡이 포함되어 있습니다. 그래도 추가하시겠습니까?')) {
                    addAllToPlaylist(selectedSongs, false);
                } else if (!anyInPlaylist) {
                    addAllToPlaylist(selectedSongs, false);
                }
            });
        }
    }

    // 선택된 노래를 플레이리스트에 추가하는 함수
    function addSelectedToMyList() {
        const selectedSongs = getSelectedSongIds();
        if (selectedSongs.length > 0) {
            if (!loginUserId) {
                if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) {
                    const currentPath = encodeURIComponent(location.pathname + location.search);
                    location.href = `/member/signin?targetUrl=${currentPath}`;
                }
                return;
            }
            showPlayListModal(parseInt(loginUserId), selectedSongs);
            afterAddingAllSongs();
        }
    }

    // 선택된 노래 ID 배열 반환 함수
    function getSelectedSongIds() {
        return Array.from(document.querySelectorAll('.songCheckbox:checked')).map(checkbox => checkbox.dataset.songId);
    }

    // 모든 곡 추가 후 실행되는 함수
    function afterAddingAllSongs() {
        floatingButtonGroup.classList.add('d-none');
        document.querySelectorAll('.songCheckbox:checked, #selectAllCheckbox:checked').forEach(checkbox => {
            checkbox.checked = false;
        });
    }

    // 선택된 노래 중 하나라도 재생목록에 있는지 확인하는 함수
    function checkAnyInPlaylist(songIds) {
        return Promise.any(songIds.map(songId => 
            axios.get(`/song/getCPList?songId=${songId}`)
                .then(response => response.data ? Promise.resolve(true) : Promise.reject(false))
        )).then(() => true).catch(() => false);
    }

    // 모든 선택된 노래를 재생목록에 추가하는 함수
    function addAllToPlaylist(songIds, skipAlert = false) {
        let addedCount = 0;
        const totalSongs = songIds.length;
        
        Promise.all(songIds.map(songId => 
            new Promise((resolve) => {
                checkAndAddToPlaylistFloating(songId, true, () => {
                    addedCount++;
                    resolve();
                });
            })
        )).then(() => {
            if (!skipAlert) {
                showAlert('재생 목록에 추가되었습니다', 2000);
            }
        });
    }

    // 재생 버튼 이벤트 설정 함수
    function setupPlayButtons() {
        songsBody.addEventListener('click', function(event) {
            if (event.target.matches('.play-btn') || event.target.closest('.play-btn')) {
                clickListenBtn(event);
            }
            if (event.target.matches('.addCPListBtn') || event.target.closest('.addCPListBtn')) {
                addToCPList(event);
            }
        });
    }
    
    // 노래 재생 함수
    function clickListenBtn(event) {
        const button = event.target.closest('.play-btn');
        if (button) {
            const id = button.getAttribute('data-id');
            const url = `../song/listen?songId=${id}`;
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

    // 재생목록에 추가 함수
    function addToCPList(event) {
        const button = event.target.closest('.addCPListBtn');
        if (button) {
            const id = button.getAttribute('data-id');
            checkAndAddToPlaylist(id, false);
        }
    }

    // 재생목록 추가 확인 및 처리 함수
    function checkAndAddToPlaylist(id, skipConfirm, callback) {
        axios.get(`/song/getCPList?songId=${id}`)
            .then((response) => {
                if (!response.data || skipConfirm) {
                    addCurrentPlayList(id, skipConfirm, callback);
                } else if (confirm('이미 재생목록에 있는 곡입니다. 그래도 추가하시겠습니까?')) {
                    addCurrentPlayList(id, skipConfirm, callback);
                } else if (callback) {
                    callback();
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

    // 선택된 노래 재생 함수
    function playSelectedSong(songId) {
        axios.get(`/song/listen?songId=${songId}`)
            .then(() => {
                console.log("첫 번째 곡 재생 성공");
                sessionStorage.setItem('index', 0);
                sessionStorage.setItem('isAdded', 'Y');
                parent.songFrame.location.reload();
            })
            .catch((error) => console.error("Error playing selected song:", error));
    }

    // 플레이리스트 관련 이벤트 설정 함수
    function setupPlaylistEvents() {
        const addToPlaylistButtons = document.querySelectorAll('.add-to-playlist-btn');
        addToPlaylistButtons.forEach(button => {
            button.addEventListener('click', handleAddToPlaylist);
        });

        const saveButton = document.getElementById('btnAddSong');
        saveButton.addEventListener('click', addSongToPlaylists);
    }

    // 플레이리스트에 추가 버튼 클릭 처리 함수
    function handleAddToPlaylist(event) {
        if (!loginUserId) {
            if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) {
                const currentPath = encodeURIComponent(location.pathname + location.search);
                location.href = `/member/signin?targetUrl=${currentPath}`;
            }
            return;
        }

        const songId = this.dataset.songId;
        showPlayListModal(parseInt(loginUserId), [songId]);
    }

    // 플레이리스트 모달 표시 함수
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

        const checkPromises = selectedPlaylistIds.flatMap(plistId => 
            songIds.map(songId => 
                axios.post(`/checkSongInPlayList`, { plistId: parseInt(plistId), songId: parseInt(songId) })
            )
        );

        Promise.all(checkPromises)
            .then(responses => {
                const alreadyAdded = responses.some(response => response.data === false);
                if (alreadyAdded) {
                    if (confirm('선택한 플레이리스트에 이미 추가된 곡입니다. 그래도 추가하시겠습니까?')) {
                        addSongsToSelectedPlaylists(selectedPlaylistIds, songIds);
                    } else {
                        const selectPlayListModal = bootstrap.Modal.getInstance(document.getElementById('selectPlayList'));
                        selectPlayListModal.hide();
                    }
                } else {
                    addSongsToSelectedPlaylists(selectedPlaylistIds, songIds);
                }
            })
            .catch(error => console.error('Error checking songs in playlists:', error));
    }

    // 선택된 플레이리스트에 곡 추가
    function addSongsToSelectedPlaylists(selectedPlaylistIds, songIds) {
        const addPromises = selectedPlaylistIds.flatMap(plistId => 
            songIds.map(songId => 
                axios.post(`/addSongToPlayList`, { plistId: parseInt(plistId), songId: parseInt(songId) })
            )
        );
        
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

    // 장르 버튼 생성 함수
    function populateGenreButtons(genres) {
        genreButtonsContainer.innerHTML = '';
        genres.forEach(genre => {
            const button = document.createElement('button');
            button.type = 'button';
            button.className = `btn btn-genre me-2 genre-btn ${genre === selectedGenre ? 'active' : ''}`;
            button.textContent = genre;
            button.setAttribute('data-genre-name', genre);
            
            button.addEventListener('click', function() {
                selectedGenre = this.getAttribute('data-genre-name');
                currentPage = 0;
                songsBody.innerHTML = '';
                fetchSongs(currentPage, selectedGenre);

                document.querySelectorAll('.genre-btn').forEach(btn => btn.classList.remove('active'));
                this.classList.add('active');

                const newUrl = selectedGenre === '전체' 
                    ? '/song/genreChart' 
                    : `/song/genreChart?genreName=${encodeURIComponent(selectedGenre)}`;
                history.pushState(null, '', newUrl);
            });

            genreButtonsContainer.appendChild(button);
        });
    }

    // 장르 목록 가져오기 함수
    function fetchGenres() {
        axios.get('/song/api/genreChart', {
            params: { page: 0, size: 1, genreName: '전체' }
        })
        .then(response => {
            const genres = response.data.genres;
            populateGenreButtons(genres);
            fetchSongs(currentPage, selectedGenre);
        })
        .catch(error => {
            console.error('Error fetching genres:', error);
            alert('장르 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.');
        });
    }

    // 페이지 초기화 함수
    function initializePage() {
        if (!sessionStorage.getItem('isAdded')) {
            sessionStorage.setItem('isAdded', 'N');
        }
        
        if (typeof refresh !== 'undefined' && refresh === 'Y') {
            console.log(refresh);
            const uri21 = `${window.location.origin}/user/removeRefresh`;
            axios.get(uri21)
                .then((response) => console.log(response))
                .catch((error) => console.log(error));
            parent.songFrame.location.reload();
        }
    }

    // 재생목록에 곡 추가 함수 (플로팅 버튼용)
    function checkAndAddToPlaylistFloating(id, skipConfirm, callback) {
        const url1 = `/song/getCPList?songId=${id}`;
        axios.get(url1)
            .then((response) => {
                if (!response.data || skipConfirm) {
                    addCurrentPlayListFloating(id, skipConfirm, callback);
                } else {
                    let result = confirm('이미 재생목록에 있는 곡입니다. 그래도 추가하시겠습니까?');
                    if (result) {
                        addCurrentPlayListFloating(id, skipConfirm, callback);
                    } else if (callback) {
                        callback();
                    }
                }
            })
            .catch((error) => { 
                console.log(error); 
                if (callback) callback();
            });
    }

    // 곡을 재생목록에 추가하는 함수 (플로팅 버튼용)
    function addCurrentPlayListFloating(id, skipAlert, callback) {
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
                if (callback) {
                    callback();
                }
            })
            .catch((error) => { 
                console.log(error); 
                if (callback) callback();
            });
    }

    // 페이지 로드 시 실행
    initializePage();
    fetchGenres();
    
});
