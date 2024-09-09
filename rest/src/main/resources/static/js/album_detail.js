/**
 * detail.jsp에 포함
 */
document.addEventListener('DOMContentLoaded', () => {
	// 앨범 개수 요소 찾기
	const albumLikeCount = document.querySelector('#albumLikeCount');

	// 앨범 커버 hover 애니메이션등록
	const albumImage = document.querySelector(".mouseScale-up");
	albumImage.addEventListener('mouseenter', scaleUp);
	albumImage.addEventListener('mouseleave', scaleDown);

	// 앨범 좋아요 버튼 호버 애니메이션 등록
	const btnLike = document.querySelector('button#btnLike');
	btnLike.addEventListener('mouseenter', scaleUp);
	btnLike.addEventListener('mouseleave', scaleDown);

	// 앨범 재생 버튼 호버 애니메이션 등록
	const btnListenAlbum = document.querySelector('#btnListenAlbum');
	btnListenAlbum.addEventListener('mouseenter', scaleUp);
	btnListenAlbum.addEventListener('mouseleave', scaleDown);

	// 앨범 재생목록 추가 버튼 호버 애니메이션 등록
	const btnAddCPListAlbum = document.querySelector('#btnAddCPListAlbum');
	btnAddCPListAlbum.addEventListener('mouseenter', scaleUp);
	btnAddCPListAlbum.addEventListener('mouseleave', scaleDown);

	// 앨범 플리 추가 버튼 호버 애니메이션 등록
	const btnAddUPListAlbum = document.querySelector('#btnAddUPListAlbum');
	btnAddUPListAlbum.addEventListener('mouseenter', scaleUp);
	btnAddUPListAlbum.addEventListener('mouseleave', scaleDown);

	// 음원 재생 버튼 호버 애니메이션 등록
	const listenBtn = document.querySelectorAll('#listenBtn');
	for (let a of listenBtn) {
		a.addEventListener('mouseenter', scaleUp);
		a.addEventListener('mouseleave', scaleDown);
	}
	// 음원 재생목록 추가 버튼 호버 애니메이션 등록
	const addCPList = document.querySelectorAll('#addCPList');
	for (let a of addCPList) {
		a.addEventListener('mouseenter', scaleUp);
		a.addEventListener('mouseleave', scaleDown);
	}
	// 음원 플리 추가 버튼 호버 애니메이션 등록
	const btnAddUPList = document.querySelectorAll('#btnAddUPList');
	for (let a of btnAddUPList) {
		a.addEventListener('mouseenter', scaleUp);
		a.addEventListener('mouseleave', scaleDown);
	}

	function scaleUp(event) {
		target = event.target;
		target.style.transform = "scale(1.1)";
		target.style.transition = "all 0.5s";
	}

	function scaleDown(event) {
		target = event.target;
		target.style.transform = "scale(1)";
		target.style.transition = "all 0.5s";
	}

	// jsp에서 전달한 변수로 객체 생성
	const data = { albumId, id: authUser };
	console.log(data);
	if (authUser !== 'null') {
		axios
			// postmapping controller 호출.
			//  로그인한 사용자가 이 음원에 눌렀는 지 검사하고 그에 따른 표현 문자열을 다르게 해줌
			// 생성한 객체 전달
			.post('/api/isAlbumLiked', data)
			.then((response) => {
				console.log(authUser);
				console.log(response.data);
				if (response.data) {
					btnLike.textContent = '♥';
					console.log('찬하트');
				} else {
					btnLike.textContent = '♡';
					console.log('빈하트');
				}
			}
			)
			.catch((error) => {
				console.log(error);
			});
	}
	axios
		.post('/api/album/getLikeCount', data)
		.then((response) => {
			console.log(response.data);
			albumLikeCount.innerHTML = response.data;
		})
		.catch((error) => {
			console.log(error);
		})


	// 버튼 클릭 이벤트 리스너 등록
	btnLike.addEventListener('click', () => {
		// putmapping controller 호출. => 좋아요버튼 토글 기능임.
		if (authUser == null) {
			// alert('로그인이 필요합니다.');
			// return
			// 생성한 객체 전달
			if (confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")) {
				redirectToLogin();
			}
			return;
		}


		if (btnLike.textContent === '♡') {
			axios
			.post('/api/album/addLike', data)
			.then((response) => {
					console.log('좋아요 안눌렀으면 실행');
					btnLike.textContent = '♥';
					albumLikeCount.innerHTML = response.data;
				}
				)
				.catch((error) => {
					console.log(error);
				});
		} else {
			axios
			.post('/api/album/cancelLike', data)
			.then((response) => {
					console.log('좋아요 버튼 해제 실행');
					btnLike.textContent = '♡';
					albumLikeCount.innerHTML = response.data;
				}
				)
				.catch((error) => {
					console.log(error);
				});
		}

	});

	function redirectToLogin() {
		const currentUrl = window.location.href;
		window.location.href = `/member/signin?target=${encodeURIComponent(currentUrl)}`;
	}
	
	
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
        if (authUser == null) { // 로그인하지 않은 경우
            if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) {
                // 현재 페이지의 경로를 인코딩하여 로그인 후 다시 돌아오도록 처리
                const currentPath = encodeURIComponent(location.pathname + location.search);
                location.href = `/member/signin?targetUrl=${currentPath}`; // 로그인 페이지로 이동
            }
            return; // 로그인하지 않으면 함수 종료
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

});



