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
    const songCheckboxes = document.querySelectorAll('.songCheckbox');
    const floatingButtonGroup = document.getElementById('floatingButtonGroup');
    const selectedCountDisplay = floatingButtonGroup.querySelector('.selected-count');
    const deselectAllButton = floatingButtonGroup.querySelector('.deselect-all');
    const selectAllCheckbox = document.getElementById('selectAllCheckbox');

    // 플로팅 버튼 그룹을 업데이트하는 함수
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

    // 개별 체크박스 이벤트 리스너
    songCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', updateButtonGroup);
    });

    // 선택 해제 버튼 이벤트 리스너
    deselectAllButton.addEventListener('click', function() {
        songCheckboxes.forEach(checkbox => {
            checkbox.checked = false;
        });
        selectAllCheckbox.checked = false;
        updateButtonGroup();
    });

    // 전체 선택 체크박스 이벤트 리스너
    selectAllCheckbox.addEventListener('change', function() {
        const isChecked = selectAllCheckbox.checked;
        songCheckboxes.forEach(checkbox => {
            checkbox.checked = isChecked;
        });
        updateButtonGroup();
    });

    // 플로팅 버튼 그룹에서 "듣기" 버튼 클릭 이벤트 리스너 설정
    floatingButtonGroup.querySelector('.play-selected').addEventListener('click', function() {
        const selectedSongs = getSelectedSongIds();
        if (selectedSongs.length === 0) return;
        playSelectedSongs(selectedSongs);
    });

    // 선택된 노래 ID 배열 반환 함수
    function getSelectedSongIds() {
        return Array.from(document.querySelectorAll('.songCheckbox:checked')).map(checkbox => checkbox.dataset.songId);
    }

    // 선택된 노래 재생 함수 (플로팅 버튼용)
    async function playSelectedSongs(selectedSongs) {
        if (selectedSongs.length > 0) {
            // 첫 번째 곡 재생
            await playSelectedSong(selectedSongs[0]);
            
            // 나머지 곡들을 재생목록에 추가 (첫 번째 곡 제외)
            if (selectedSongs.length > 1) {
                await addAllToPlaylist(selectedSongs.slice(1), true);
            }
            
            // 선택한 곡의 개수에 따라 알림 메시지 표시
            showAlert(`${selectedSongs.length}개의 음원을 재생합니다.`, 2000);
        }
    }

    // 플로팅 버튼 그룹에서 "재생목록에 추가" 버튼 클릭 이벤트 리스너 설정
    floatingButtonGroup.querySelector('.add-to-playlist').addEventListener('click', function() {
        const selectedSongs = getSelectedSongIds();
        if (selectedSongs.length === 0) return;
        addSelectedToPlaylist(selectedSongs);
    });

    // 선택된 노래를 재생목록에 추가하는 함수 (플로팅 버튼용)
    async function addSelectedToPlaylist(selectedSongs) {
        if (selectedSongs.length === 0) return;
        
        // 이미 재생목록에 있는 곡들을 확인
        const songsInPlaylist = await Promise.all(selectedSongs.map(songId => 
            axios.get(`/song/getCPList?songId=${songId}`)
                .then(response => ({ songId, inPlaylist: response.data }))
                .catch(() => ({ songId, inPlaylist: false }))
        ));
    
        // 재생목록에 없는 곡들만 필터링
        const songsToAdd = songsInPlaylist.filter(song => !song.inPlaylist).map(song => song.songId);
    
        // 재생목록에 이미 있는 곡들
        const alreadyInPlaylist = songsInPlaylist.filter(song => song.inPlaylist).map(song => song.songId);
    
        // 사용자에게 이미 재생목록에 있는 곡들에 대해 알림
        if (alreadyInPlaylist.length > 0) {
            const confirmAdd = confirm(`이미 재생목록에 있는 곡이 포함되어 있습니다. 그래도 추가하시겠습니까?`);
            if (confirmAdd) {
                songsToAdd.push(...alreadyInPlaylist);
            }
        }
    
        // 선택된 곡들을 재생목록에 추가
        if (songsToAdd.length > 0) {
            await addAllToPlaylist(songsToAdd, false);
            showAlert(`${songsToAdd.length}개의 곡이 재생목록에 추가되었습니다.`, 2000);
        }
    }

    // 플로팅 버튼에서 "담기" 버튼 클릭 이벤트 리스너 설정
    floatingButtonGroup.querySelector('.add-to-my-list').addEventListener('click', function() {
        const selectedSongs = getSelectedSongIds();
        if (selectedSongs.length === 0) return;
        addSelectedToMyList(selectedSongs);
    });

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

    // 플레이리스트 불러오기 및 모달 표시 함수
    function showPlayListModal(userId, songIds) {
        axios.get(`/getPlayList/${userId}`)
            .then(response => {
                if (response.status === 200) {
                    const playLists = response.data;
                    const playListsContainer = document.querySelector('.playlist-container');
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
                console.error(`재생목록에서 곡 ${id} 확인 중 오류:`, error);
                if (callback) callback();
            });
    }
    
    // 현재 재생목록에 곡 추가 함수
    function addCurrentPlayList(id, skipAlert, callback) {
        const url = `/song/addCurrentPlayList?songId=${id}`;
        console.log(url);
        axios.get(url)
            .then((response) => {
                console.log(response);
                if (sessionStorage.getItem('isAdded') === 'N') {
                    sessionStorage.setItem('index', 0);
                    sessionStorage.setItem('isAdded', 'Y');
                    if (parent && parent.songFrame) {
                        parent.songFrame.location.reload();
                    }
                }
                if (!skipAlert) {
                    showAlert('재생 목록에 추가되었습니다', 2000);
                }
                if (callback) callback();
            })
            .catch((error) => {
                console.error(`재생목록에 곡 ${id} 추가 중 오류:`, error);
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
            .catch((error) => console.error("선택된 곡 재생 중 오류:", error));
    }
    
    // 모든 선택된 노래를 재생목록에 추가하는 함수 (플로팅 버튼용)
    async function addAllToPlaylist(songIds, skipAlert = false) {
        let addedCount = 0;
        const totalSongs = songIds.length;
        
        for (const songId of songIds) {
            await new Promise((resolve) => {
                checkAndAddToPlaylist(songId, true, () => {
                    addedCount++;
                    resolve();
                });
            });
        }
    
        if (!skipAlert) {
            showAlert(`${addedCount}개의 곡이 재생목록에 추가되었습니다`, 2000);
        }
    }

    // 초기 설정 (isAdded 값이 세션 스토리지에 없을 경우 초기화)
    if (!sessionStorage.getItem('isAdded')) {
        sessionStorage.setItem('isAdded', 'N');
    }

    // 페이지 새로고침 플래그가 있을 때 처리
    if (typeof refresh !== 'undefined' && refresh === 'Y') {
        console.log(refresh);
        const uri21 = `${window.location.origin}/user/removeRefresh`
        axios.get(uri21)
            .then((response) => console.log(response))
            .catch((error) => console.error(error));
        if (parent && parent.songFrame) {
            parent.songFrame.location.reload();
        }
    }
});
