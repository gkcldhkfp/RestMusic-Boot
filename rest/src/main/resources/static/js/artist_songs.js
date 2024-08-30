/**
 * artist 폴더의 songs.html에 포함
 */
document.addEventListener('DOMContentLoaded', () => {
    console.log('사용자 ID는 ' + (loginUserId !== null ? loginUserId : '로그인되지 않음'));
    const btnAddPlayLists = document.querySelectorAll('button.addPlayList');
    const playListModal = new bootstrap.Modal(document.querySelector('div#staticBackdrop3'), { backdrop: 'static' });
    
    let currentPage = 1;
    const itemsPerPage = 5;
    
    for (let a of btnAddPlayLists) {
        a.addEventListener('click', getPlayLists);
    }

    function getPlayLists(event) {
        event.stopPropagation();
        if (loginUserId == null) { // 유저아이디
            alert('로그인이 필요합니다');
            return;
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
    
    // 버튼 그룹 업데이트 함수
    function updateButtonGroup() {
        const checkedBoxes = document.querySelectorAll('.songCheckbox:checked');
        const checkedCount = checkedBoxes.length;
        
        if (checkedCount > 0) {
            floatingButtonGroup.classList.remove('d-none');
            selectedCountDisplay.textContent = `${checkedCount}개 선택됨`;
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
        selectAllCheckbox.checked = false; // 전체 선택 체크박스 해제
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
    
    // 듣기 버튼 클릭 이벤트
    floatingButtonGroup.querySelector('.play-selected').addEventListener('click', function() {
        const selectedSongs = Array.from(document.querySelectorAll('.songCheckbox:checked')).map(checkbox => checkbox.dataset.songId);
        
        if (selectedSongs.length > 0) {
            // 첫 번째 곡 재생 (재생목록에 추가하지 않음)
            playSelectedSong(selectedSongs[0]);
    
            // 나머지 곡들만 재생목록에 추가 (알림 표시 없이)
            if (selectedSongs.length > 1) {
                addAllToPlaylist(selectedSongs.slice(1), true);
            }
    
            // 알림 메시지 표시
            if (selectedSongs.length === 1) {
                showAlert('선택한 음원을 재생합니다.', 2000);
            } else {
                showAlert('선택한 곡들을 재생합니다.', 2000);
            }
        }
    });
    
    // 선택된 곡을 재생하는 함수
    function playSelectedSong(songId) {
        const url = `/song/listen?songId=${songId}`;
        axios.get(url)
            .then((response) => {
                console.log("첫 번째 곡 재생 성공");
                sessionStorage.setItem('index', 0);
                sessionStorage.setItem('isAdded', 'Y');
                parent.songFrame.location.reload();
            })
            .catch((error) => console.log(error));
    }
    
    // 재생목록에 추가 버튼 클릭 이벤트 (플로팅 버튼)
    floatingButtonGroup.querySelector('.add-to-playlist').addEventListener('click', function() {
        const selectedSongs = Array.from(document.querySelectorAll('.songCheckbox:checked')).map(checkbox => checkbox.dataset.songId);
        if (selectedSongs.length > 0) {
            // 선택된 노래 중 하나라도 재생목록에 있는지 확인
            checkAnyInPlaylist(selectedSongs).then(anyInPlaylist => {
                if (anyInPlaylist) {
                    // 재생목록에 있는 노래가 하나라도 있으면 한 번만 확인
                    if (confirm('이미 재생목록에 있는 곡이 포함되어 있습니다. 그래도 추가하시겠습니까?')) {
                        addAllToPlaylist(selectedSongs, false);
                    }
                } else {
                    // 모든 노래가 재생목록에 없으면 바로 추가
                    addAllToPlaylist(selectedSongs, false);
                }
            });
        }
    });
    
    // 선택된 노래 중 하나라도 재생목록에 있는지 확인하는 함수
    function checkAnyInPlaylist(songIds) {
        return Promise.any(songIds.map(songId => 
            axios.get(`/song/getCPList?songId=${songId}`)
                .then(response => response.data ? Promise.resolve(true) : Promise.reject(false))
        )).then(() => true).catch(() => false);
    }
    
    // 모든 선택된 노래를 재생목록에 추가하는 함수 (플로팅 버튼용)
    function addAllToPlaylist(songIds, skipAlert = false) {
        let addedCount = 0;
        const totalSongs = songIds.length;
    
        songIds.forEach((songId, index) => {
            checkAndAddToPlaylistFloating(songId, true, () => {
                addedCount++;
                if (addedCount === totalSongs && !skipAlert) {
                    showAlert('재생 목록에 추가되었습니다', 2000);
                }
            });
        });
    }
    
    // 플로팅 버튼용 재생목록 추가 함수
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
            .catch((error) => { console.log(error); });
    }
    
    // 플로팅 버튼용 재생목록에 곡을 추가하는 함수
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
            .catch((error) => { console.log(error); });
    }
    
    // 알림 메시지를 표시하는 함수
    function showAlert(message, duration) {
        // 기존 알림창이 있는지 확인
        if (document.querySelector('.custom-alert')) {
            return;
        }
    
        // 알림창 생성
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
    
        // Fade in
        setTimeout(() => {
            alertBox.style.opacity = '1';
        }, 10);
    
        // Fade out and remove
        setTimeout(() => {
            alertBox.style.opacity = '0';
            setTimeout(() => {
                document.body.removeChild(alertBox);
            }, 500);
        }, duration);
    }
    
    // 초기 설정
    if (!sessionStorage.getItem('isAdded')) {
        sessionStorage.setItem('isAdded', 'N');
    }
    
    if (typeof refresh !== 'undefined' && refresh === 'Y') {
        console.log(refresh);
        const uri21 =`${window.location.origin}/user/removeRefresh`
        axios.get(uri21)
            .then((response) => {
                console.log(response)
            })
            .catch((error) => {
                console.log(error)
            });
        parent.songFrame.location.reload();
    }
    
    // 테이블의 재생목록 추가 버튼 이벤트 리스너 설정
    document.addEventListener('DOMContentLoaded', function() {
        const addCPList = document.querySelectorAll('#addCPList');
        if (addCPList !== null) {
            for (let a of addCPList) {
                a.removeEventListener('click', addToCPList); // 기존 이벤트 리스너 제거
                a.addEventListener('click', addToCPListNew); // 새로운 이벤트 리스너 추가
            }
        }
    });
    
    // 테이블의 재생목록 추가 함수 (새 버전)
    function addToCPListNew(event) {
        event.preventDefault(); // 이벤트의 기본 동작을 막습니다.
        event.stopPropagation(); // 이벤트 버블링을 막습니다.
        const id = event.target.getAttribute('data-id');
        checkAndAddToPlaylistTable(id);
    }
    
    // 테이블용 재생목록 추가 함수
    function checkAndAddToPlaylistTable(id) {
        const url1 = `/song/getCPList?songId=${id}`;
        axios.get(url1)
            .then((response) => {
                if (!response.data) {
                    addCurrentPlayListTable(id);
                } else {
                    let result = confirm('이미 재생목록에 있는 곡입니다. 그래도 추가하시겠습니까?');
                    if (result) {
                        addCurrentPlayListTable(id);
                    }
                }
            })
            .catch((error) => { console.log(error); });
    }
    
    // 테이블용 재생목록에 곡을 추가하는 함수
    function addCurrentPlayListTable(id) {
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
                showAlert('재생 목록에 추가되었습니다', 2000);
            })
            .catch((error) => { console.log(error); });
    }

    // 담기 버튼 클릭 이벤트
    floatingButtonGroup.querySelector('.add-to-my-list').addEventListener('click', function() {
        const selectedSongs = Array.from(document.querySelectorAll('.songCheckbox:checked')).map(checkbox => checkbox.dataset.songId);
        if (selectedSongs.length > 0) {
            // 첫 번째 선택된 곡의 버튼에서 id를 가져옵니다.
            const firstSongButton = document.querySelector(`.add-to-playlist-btn[data-song-id="${selectedSongs[0]}"]`);
            const id = parseInt(firstSongButton.dataset.id);
            console.log(id);
    
            if (id === 0) { // 로그인하지 않은 경우
                
                if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) {
                    const currentPath = location.pathname.replace('/Rest', '');
                    location.href = '/user/signin?target=' + encodeURIComponent(currentPath);
                }
                return;
            }
    
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
        axios.get(`/getPlayList/${id}`)
            .then(response => {
                if (response.status === 200) {
                    const playLists = response.data;
                    const playListsContainer = document.getElementById('playLists');
                    playListsContainer.innerHTML = ''; // 기존 내용을 지움
    
                    playLists.forEach(list => {
                        const defaultImage = '/images/icon/default.png';
                        const albumImageSrc = list.albumImage ? `/images/albumcover/${list.albumImage}` : defaultImage;
    
                        const listElement = document.createElement('div');
                        listElement.classList.add('playlist-item', 'd-flex', 'align-items-center', 'mb-2');
                        
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
                        playListsContainer.appendChild(listElement);
                    });
    
                    // songIds를 hidden input으로 추가
                    const hiddenInput = document.createElement('input');
                    hiddenInput.type = 'hidden';
                    hiddenInput.id = 'selectedSongIds';
                    hiddenInput.value = JSON.stringify(songIds);  // 배열을 JSON 문자열로 변환
                    playListsContainer.appendChild(hiddenInput);
    
                    const selectPlayListModal = new bootstrap.Modal(document.getElementById('selectPlayList'));
                    selectPlayListModal.show();
    
                    // 플레이리스트 버튼 클릭 이벤트
                    document.querySelectorAll('.playlist-btn').forEach(btn => {
                        btn.addEventListener('click', function() {
                            this.classList.toggle('selected');
                        });
                    });
                }
            })
            .catch(error => {
                console.error('플레이리스트를 불러오는 중 오류가 발생했습니다:', error);
            });
    }
    
    // 곡 추가 함수
    function addSongToPlaylists() {
        // 선택된 버튼 사용
        const selectedPlaylists = document.querySelectorAll('#playLists .playlist-btn.selected');
        const selectedPlaylistIds = Array.from(selectedPlaylists).map(btn => btn.dataset.id);
    
        // hidden input에서 songIds를 가져옴
        const songIdsJson = document.getElementById('selectedSongIds').value;
        const songIds = JSON.parse(songIdsJson);  // JSON 문자열을 배열로 변환
    
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
                // 각 플레이리스트에 곡이 이미 추가되어 있는지 확인
                const checkPromise = axios.post(`/checkSongInPlayList`, {
                    plistId: parseInt(plistId),
                    songId: parseInt(songId)
                }).then(response => {
                    console.log(`플레이리스트 ${plistId} 응답:`, response.data);
                    if (response.data === false) { // 곡이 이미 있는 경우
                        alreadyAdded[plistId] = true;
                    }
                }).catch(error => {
                    console.error('플레이리스트에서 곡 확인 중 오류가 발생했습니다:', error);
                });
                promises.push(checkPromise);
            });
        });
    
        Promise.all(promises).then(() => {
            const addedPlaylists = selectedPlaylistIds.filter(plistId => alreadyAdded[plistId]);
            if (addedPlaylists.length > 0) {
                // 사용자가 확인을 누르면 곡을 추가
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
    
        Promise.all(addPromises).then(responses => {
            const allSuccessful = responses.every(response => response && response.status === 200);
            if (allSuccessful) {
                alert('선택한 플레이리스트에 곡이 추가되었습니다.');
                const selectPlayListModal = bootstrap.Modal.getInstance(document.getElementById('selectPlayList'));
                selectPlayListModal.hide();
    
                // 체크박스 해제 및 플로팅 버튼 숨기기
                document.querySelectorAll('.songCheckbox:checked, #selectAllCheckbox:checked').forEach(checkbox => {
                    checkbox.checked = false;
                });
                floatingButtonGroup.classList.add('d-none');
    
                // 모달 배경을 어둡게 하지 않도록 설정
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
    
    // 플레이리스트에 곡 추가 버튼 클릭 이벤트 핸들러
    const saveButton = document.getElementById('btnAddSong');
    saveButton.addEventListener('click', addSongToPlaylists);
    
    // 플레이리스트 추가 버튼 클릭 이벤트 핸들러
    const addToPlaylistButtons = document.querySelectorAll('.add-to-playlist-btn');
    addToPlaylistButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            const id = parseInt(this.dataset.id);
            const songId = this.dataset.songId;  // 버튼에서 songId를 가져옴
            if (id === 0) { // 로그인하지 않은 경우
                if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) {
                    const currentPath = location.pathname.replace('/Rest', '');
                    location.href = '/user/signin?target=' + encodeURIComponent(currentPath);
                }
                return;
            }
            addToPlaylistButtons.forEach(btn => btn.classList.remove('active')); // 모든 버튼의 active 클래스 제거
            this.classList.add('active'); // 현재 클릭한 버튼에 active 클래스 추가
            showPlayListModal(id, [songId]);  // songId를 배열로 전달
        });
    });
    
    // 모달 창이 나와도 다른 버튼들을 클릭할 수 있게 설정
    document.querySelectorAll('.modal-backdrop').forEach(backdrop => {
        backdrop.style.pointerEvents = 'none';
    });
    
});