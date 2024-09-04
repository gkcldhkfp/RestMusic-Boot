/**
 * /song/genreChart.html에 포함
 */

document.addEventListener("DOMContentLoaded", function() {
    console.log('loginUserId= ' + loginUserId);
    
    function addEventListeners() {
        // 좋아요 아이콘 클릭 이벤트 핸들러
        const heartIcons = document.querySelectorAll('.heart-icon'); // 모든 좋아요 아이콘을 선택
        
        heartIcons.forEach(icon => {
            const songId = icon.dataset.songId; // 각 아이콘의 데이터 속성에서songId를 가져옴
            let likesCountElement = icon.nextElementSibling; // 좋아요 개수를 표시하는 요소를 가져옴
            
            // 특정 사용자가 특정 노래를 좋아요 했는지 여부를 서버에 요청
            const data = { songId, loginUserId }; // 서버에 보낼 데이터 (songId와 사용자 ID)
            axios.post('/api/isLiked', data)
                .then(response => {
                    // 서버 응답에 따라 좋아요 상태를 설정
                    if (response.data) { // 사용자가 이미 좋아요를 눌렀다면
                        icon.classList.add('liked'); // liked 클래스를 추가
                        icon.classList.remove('far'); // 비어있는 하트 아이콘 클래스를 제거
                        icon.classList.add('fas'); // 채워진 하트 아이콘을 추가
                        icon.style.color = 'red'; // 아이콘 색상을 빨간색으로 설정
                    } else { // 사용자가 좋아요를 누르지 않았다면
                        icon.classList.remove('liked'); // liked 클래스를 제거
                        icon.classList.remove('fas'); // 채워진 하트 아이콘을 제거
                        icon.classList.add('far'); // 비어있는 하트 아이콘을 추가
                        icon.style.color = 'black'; // 아이콘 색상을 검은색으로 설정
                    }
    
                    // 아이콘 클릭 이벤트 리스너 추가
                    icon.addEventListener('click', function() {
                        if (!loginUserId) { // 로그인하지 않은 경우
                            if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) {
                                // 현재 페이지의 경로를 인코딩하여 로그인 후 다시 돌아오도록 처리
                                const currentPath = encodeURIComponent(location.pathname + location.search);
                                location.href = `/member/signin?targetUrl=${currentPath}`; // 로그인 페이지로 이동
                            }
                            return; // 로그인하지 않으면 함수 종료
                        }
    
                        let likesCount = parseInt(likesCountElement.textContent); // 현재 좋아요 개수를 가져옴
    
                        if (icon.classList.contains('liked')) { // 이미 좋아요 상태인 경우
                            // 좋아요 취소 요청을 서버로 보냄
                            axios.delete(`/api/song/cancelLike/${songId}/${loginUserId}`)
                                .then(response => {
                                    if (response.status === 200) { // 서버 응답이 성공적인 경우
                                        icon.classList.remove('liked'); // liked 클래스를 제거
                                        icon.classList.remove('fas'); // 채워진 하트 아이콘을 제거
                                        icon.classList.add('far'); // 비어있는 하트 아이콘을 추가
                                        icon.style.color = 'black'; // 아이콘 색상을 검은색으로 설정
                                        likesCount -= 1; // 좋아요 개수를 1 감소
                                        likesCountElement.textContent = response.data; // 최신 좋아요 개수로 업데이트
                                    }
                                })
                                .catch(error => {
                                    console.error('좋아요 제거 중 오류:', error); // 오류 처리
                                });
                        } else { // 좋아요 상태가 아닌 경우
                            // 좋아요 추가 요청을 서버로 보냄
                            axios.post('/api/song/addLike', data)
                                .then(response => {
                                    if (response.status === 200) { // 서버 응답이 성공적인 경우
                                        icon.classList.add('liked'); // liked 클래스를 추가
                                        icon.classList.remove('far'); // 비어있는 하트 아이콘을 제거
                                        icon.classList.add('fas'); // 채워진 하트 아이콘을 추가
                                        icon.style.color = 'red'; // 아이콘 색상을 빨간색으로 설정
                                        likesCount += 1; // 좋아요 개수를 1 증가
                                        likesCountElement.textContent = response.data; // 최신 좋아요 개수로 업데이트
                                    }
                                })
                                .catch(error => {
                                    console.error('좋아요 추가 중 오류:', error); // 오류 처리
                                });
                        }
                    });
                })
                .catch(error => {
                    console.error('좋아요 상태 가져오는 중 오류:', error); // 오류 처리
                    // 오류 발생 시 기본 상태 설정
                    icon.classList.remove('liked');
                    icon.classList.remove('fas');
                    icon.classList.add('far');
                    icon.style.color = 'black';
                });
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
            const uri21 =`${window.location.origin}/user/removeRefresh` // 새로고침 플래그 제거 요청
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
    
    }
    
    // 전역 변수 초기화
    let currentPage = 0; // 현재 페이지 번호
    const pageSize = 30; // 한 페이지당 노래 수
    let loadingData = false; // 데이터를 로딩 중인지 여부
    let totalSongsCount = 0; // 총 노래 수
    let selectedGenre = "전체"; // 현재 선택된 장르
    
    // DOM 요소 선택
    const songsBody = document.getElementById('songsBody'); // 노래 목록을 표시할 테이블의 본문
    const loading = document.getElementById('loading'); // 로딩 인디케이터
    const loadMoreBtn = document.getElementById('loadMoreBtn'); // 더보기 버튼
    const genreButtonsContainer = document.getElementById('genreButtons'); // 장르 버튼을 포함하는 컨테이너
    const noChartMessage = document.getElementById('noChartMessage'); // 노래가 없을 때 표시할 메시지
    const chartTable = document.querySelector('.container .table'); // 노래 목록을 포함하는 테이블

    // URL에서 현재 선택된 장르 가져오기
    const urlParams = new URLSearchParams(window.location.search); // URL의 쿼리 파라미터를 파싱
    selectedGenre = urlParams.get('genreName') || '전체'; // URL에서 genreName 파라미터를 가져오거나 '전체'로 설정

    // 장르 버튼을 동적으로 생성하는 함수
    function populateGenreButtons(genres) {
        genreButtonsContainer.innerHTML = ''; // 기존 장르 버튼 제거

        genres.forEach(genre => {
            // 각 장르에 대해 버튼을 생성
            const button = document.createElement('button');
            button.type = 'button';
            button.className = `btn btn-genre me-2 genre-btn ${genre === selectedGenre ? 'active' : ''}`;
            button.textContent = genre;
            button.setAttribute('data-genre-name', genre);
            
            // 버튼 클릭 시 장르 변경 및 데이터 새로고침
            button.addEventListener('click', function() {
                const genreName = this.getAttribute('data-genre-name');
                selectedGenre = genreName;
                currentPage = 0; // 페이지 초기화
                songsBody.innerHTML = ''; // 기존 노래 목록 초기화
                fetchSongs(currentPage, selectedGenre); // 새 장르로 노래 데이터 가져오기

                // 버튼 활성화 상태 변경
                document.querySelectorAll('.genre-btn').forEach(btn => btn.classList.remove('active'));
                this.classList.add('active');

                // URL 업데이트
                const newUrl = genreName === '전체' 
                    ? '/song/genreChart' 
                    : `/song/genreChart?genreName=${encodeURIComponent(genreName)}`;
                history.pushState(null, '', newUrl); // 브라우저의 히스토리와 URL 업데이트
            });

            genreButtonsContainer.appendChild(button); // 버튼을 DOM에 추가
        });
    }

    // 노래 데이터 가져오기 함수
    function fetchSongs(page, genre) {
        if (loadingData) return; // 데이터 로딩 중이면 함수 종료
        loadingData = true;
        loading.style.display = 'block'; // 로딩 인디케이터 표시
        loadMoreBtn.style.display = 'none'; // 더보기 버튼 숨김

        axios.get('/song/api/genreChart', {
            params: { page: page, size: pageSize, genreName: genre } // API 요청 파라미터 설정
        })
        .then(response => {
            const data = response.data;
            const songs = data.songs;
            totalSongsCount = data.totalSongsCount;

            if (songs.length > 0) {
                appendSongs(songs); // 노래 목록을 테이블에 추가
                currentPage++; // 페이지 번호 증가
                updateLoadMoreButton(); // 더보기 버튼 상태 업데이트
                noChartMessage.classList.add('d-none'); // 노래 없음 메시지 숨김
                chartTable.classList.remove('d-none'); // 테이블 표시
            } else if (currentPage === 0) {
                noChartMessage.classList.remove('d-none'); // 첫 페이지에서 노래가 없으면 메시지 표시
                chartTable.classList.add('d-none'); // 테이블 숨김
            }
    
            loading.style.display = 'none'; // 로딩 인디케이터 숨김
            loadingData = false;
        })
        .catch(error => {
            console.error('Error fetching songs:', error);
            loading.style.display = 'none'; // 로딩 인디케이터 숨김
            loadingData = false;
            loadMoreBtn.style.display = 'block'; // 오류 발생 시 더보기 버튼 표시
            alert('노래를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.'); // 오류 알림
        });
    }

    // 더보기 버튼 상태 업데이트 함수
    function updateLoadMoreButton() {
        const loadedSongsCount = songsBody.getElementsByClassName('song-row').length;
        if (loadedSongsCount >= totalSongsCount) {
            loadMoreBtn.style.display = 'none'; // 모든 노래가 로드되었으면 버튼 숨김
        } else {
            loadMoreBtn.style.display = 'block'; // 더 로드할 노래가 있으면 버튼 표시
        }
    }

    // 더보기 버튼 클릭 이벤트
    loadMoreBtn.addEventListener('click', () => {
        fetchSongs(currentPage, selectedGenre); // 현재 페이지와 장르로 노래 데이터 추가 로드
    });
    
    // 노래 목록에 노래 추가 함수
    function appendSongs(songs) {
        songs.forEach((song, index) => {
            const row = document.createElement('tr');
            row.className = 'song-row';
    
            // 노래 정보를 포함한 HTML 생성
            row.innerHTML = `
                <td><input type="checkbox" class="songCheckbox" data-song-id="${song.songId}" /></td>
                <td>${currentPage * pageSize + index + 1}</td>
                <td class="song-info">
                    <a href="/album/detail?albumId=${song.albumId}" class="album-link">
                      <img alt="앨범표지" src="/images/albumcover/${song.albumImage}" class="img-fluid" />
                    </a>
                    <div>
                        <a href="/song/details?songId=${song.songId}" style="font: inherit; color: inherit; text-decoration: none;">
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
    
            songsBody.appendChild(row); // 생성된 노래 정보를 테이블에 추가
        });
        
        // 더보기 버튼 상태 업데이트
        updateLoadMoreButton();
    
        // 이벤트 리스너 추가
        addEventListeners(); // 함수에 추가된 이벤트 리스너를 활성화
    }
    
    // 장르 버튼과 페이지 로드를 위한 초기화 함수
    function initialize() {
        // 장르 목록을 가져와서 장르 버튼을 생성
        axios.get('/song/api/genreChart', {
            params: { page: 0, size: 1, genreName: '전체' } // 초기 로드 시에는 장르 리스트만 가져옴.
        })
        .then(response => {
            const genres = response.data.genres;
            populateGenreButtons(genres); // 장르 버튼을 생성
            fetchSongs(currentPage, selectedGenre); // 현재 장르로 노래 데이터 가져오기
        })
        .catch(error => {
            console.error('Error initializing genres:', error);
            alert('장르 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.'); // 오류 알림
        });
    }

    // 페이지 초기화
    initialize(); // 페이지 로드 시 초기화 함수 호출
    
    // 클릭된 'play-btn' 버튼에 대한 처리 함수
    function clickListenBtn(event) {
        // 클릭된 버튼이 'play-btn' 클래스를 가진 가장 가까운 부모 요소를 찾음
        const button = event.target.closest('.play-btn');
        if (button) {
            // 버튼에서 songId를 가져옴
            const id = button.getAttribute('data-id');
            // 요청할 URL을 구성함
            const url = `../song/listen?songId=${id}`;
            console.log(url);  // 요청할 URL을 콘솔에 로그로 출력
            
            // axios를 사용하여 GET 요청을 보냄
            axios.get(url)
                .then(response => {
                    console.log("성공");  // 요청 성공 시 콘솔에 로그 출력
                    sessionStorage.setItem('index', 0);  // sessionStorage에 index를 0으로 설정
                    sessionStorage.setItem('isAdded', 'Y');  // sessionStorage에 isAdded를 'Y'로 설정
    
                    // 부모 프레임이 있는지 확인
                    if (parent && parent.songFrame) {
                        try {
                            // 부모 프레임의 songFrame을 리로드 시도
                            parent.songFrame.location.reload();
                        } catch (error) {
                            // 리로드 실패 시 에러를 콘솔에 로그로 출력
                            console.error("Unable to reload the song frame:", error);
                        }
                    } else {
                        // 부모 프레임이 없거나 songFrame이 없을 경우 경고 메시지 출력
                        console.warn("Parent songFrame is not available.");
                    }
    
                    // showAlert 함수를 호출하여 사용자에게 알림을 표시
                    showAlert('선택한 음원을 재생합니다.', 2000);
                })
                .catch(error => {
                    // 요청 실패 시 에러를 콘솔에 로그로 출력
                    console.error("Error occurred while listening to the song:", error);
                });
        }
    }
    
    // 클릭된 'addCPListBtn' 버튼에 대한 처리 함수
    function addToCPList(event) {
        // 클릭된 버튼이 'addCPListBtn' 클래스를 가진 가장 가까운 부모 요소를 찾음
        const button = event.target.closest('.addCPListBtn');
        if (button) {
            // 버튼에서 songId를 가져옴
            const id = button.getAttribute('data-id');
            // checkAndAddToPlaylist 함수를 호출하여 플레이리스트에 추가
            checkAndAddToPlaylist(id, false);
        }
    }
    
    // 이벤트 리스너를 설정하는 부분
    const songBody = document.querySelector('#songsBody');
    if (songBody) {
        // songBody가 존재하는 경우 클릭 이벤트를 처리
        songBody.addEventListener('click', function(event) {
            // 클릭된 요소가 'play-btn' 또는 그 자식 요소일 경우 clickListenBtn 함수 호출
            if (event.target.matches('.play-btn') || event.target.closest('.play-btn')) {
                clickListenBtn(event);
            }
            // 클릭된 요소가 'addCPListBtn' 또는 그 자식 요소일 경우 addToCPList 함수 호출
            if (event.target.matches('.addCPListBtn') || event.target.closest('.addCPListBtn')) {
                addToCPList(event);
            }
        });
    } else {
        // songBody가 존재하지 않을 경우 에러 메시지 출력
        console.error("SongsBody element not found.");
    }
    
    // 사용자에게 알림을 표시하는 함수
    function showAlert(message, duration) {
        // 알림창 요소를 생성
        const alertBox = document.createElement('div');
        alertBox.textContent = message;  // 알림 메시지 설정
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
        
        // 알림창을 문서에 추가
        document.body.appendChild(alertBox);
    
        // 10ms 후에 알림창을 불투명하게 하여 표시
        setTimeout(() => {
            alertBox.style.opacity = '1';
        }, 10);
    
        // 지정된 시간(duration) 후에 알림창을 서서히 사라지게 하고 제거
        setTimeout(() => {
            alertBox.style.opacity = '0';
            setTimeout(() => {
                document.body.removeChild(alertBox);
            }, 500);
        }, duration);
        
    }
    
});
