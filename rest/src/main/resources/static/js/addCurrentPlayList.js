/**
 * currentPlayList.jsp에 포함
 * detail.jsp에 포함
 */
document.addEventListener('DOMContentLoaded', () => {
	console.log("addCurrentPlayList.js");
	if (!sessionStorage.getItem('isAdded')) {
		sessionStorage.setItem('isAdded', 'N');
	}

	if (refresh === 'Y') {
		console.log(refresh);
		/*refresh = "N";*/
		const uri21 = `${window.location.origin}/user/removeRefresh`
		axios.get(uri21).
			then((response) => {
				console.log(response)
			})
			.catch((error) => {
				console.log(error)
			});
		parent.songFrame.location.reload();
	}

	// 음원 다음 곡으로 재생 기능
	const addCPList = document.querySelectorAll('#addCPList');
	if (addCPList !== null) {
		for (let a of addCPList) {
			console.log(a);
			a.addEventListener('click', addToCPList);
			// 새로운 커스텀 이벤트 리스너 추가(차트에서 여러 곡 재생목록에 담기 위해 사용)
			a.addEventListener('customAddToPlaylist', customAddToCPList);
		}
	}

	function addToCPList(event) {
		const id = event.target.getAttribute('data-id');
		checkAndAddToPlaylist(id, false);
	}

	function customAddToCPList(event) {
		const id = event.target.getAttribute('data-id');
		checkAndAddToPlaylist(id, true);
	}

	function checkAndAddToPlaylist(id, skipConfirm) {
		// 받은 아이디가 이미 세션에 있는 지 검사하는 컨트롤러 호출.
		const url1 = `../song/getCPList?songId=${id}`;
		axios.get(url1)
			.then((response) => {
				if (!response.data || skipConfirm) {
					// 중복된 데이터가 없는 경우 또는 확인을 건너뛰는 경우
					addCurrentPlayList(id);
				} else {
					// 중복된 데이터가 있는 경우
					// 유저에게 중복되어도 추가할거냐고 물어봄
					let result = confirm('이미 재생목록에 있는 곡입니다. 그래도 추가하시겠습니까?');
					if (result) {
						// 유저가 수락한 경우.
						// 재생목록에 추가.
						addCurrentPlayList(id);
					} else {
						// 유저가 거절한 경우
						return;
					}
				}
			})
			.catch((error) => { console.log(error); });
	}

	function addCurrentPlayList(id) {
		const url2 = `../song/addCurrentPlayList?songId=${id}`;
		console.log(url2);
		axios.get(url2)
			.then((response) => {
				console.log(response);
				if (sessionStorage.getItem('isAdded') === 'N') {
					sessionStorage.setItem('index', 0);
					sessionStorage.setItem('isAdded', 'Y');
					parent.songFrame.location.reload();
				}
				// document.location.reload();
				// alert('재생 목록에 추가되었습니다');
				showAlert('재생 목록에 추가되었습니다', 2000);
			})
			.catch((error) => { console.log(error); });
	}

	function showAlert(message, duration) {
		// 알림 표시 로직 구현
		alert(message);
	}

	// 음원 듣기 기능
	const listenBtn = document.querySelectorAll('#listenBtn');
	if (addCPList !== null) {
		for (let l of listenBtn) {
			l.addEventListener('click', clickListenBtn)
		}
		function clickListenBtn(event) {
			const id = event.target.getAttribute('data-id');
			const url = `../song/listen?songId=${id}`
			console.log(url);
			axios
				.get(url)
				.then((response) => {
					console.log("성공");
					sessionStorage.setItem('index', 0);
					sessionStorage.setItem('isAdded', 'Y');
					parent.songFrame.location.reload();
					// alert('선택한 음원을 재생합니다.');
					showAlert('선택한 음원을 재생합니다.', 2000);

				})
				.catch((error) => { });
		}
	}
	const targetNode = document.getElementById('resultTable');

	if (targetNode) {
		// MutationObserver 콜백 함수
		var observerCallback = function (mutationsList, observer) {
			var rowAddedFlag = false;
			for (var mutation of mutationsList) {
				if (mutation.type === 'childList') {
					rowAddedFlag = true;
					break;
				}
			}
			if (rowAddedFlag) {
				rowAdded();
			}
		};

		// MutationObserver 설정
		const observerConfig = { childList: true, subtree: true };
		const observer = new MutationObserver(observerCallback);

		// targetNode 감지 시작
		observer.observe(targetNode, observerConfig);
	} else {
		console.log("Target node not found");
	}

	function rowAdded() {
		console.log("New rows detected!");
		const listenBtn2 = document.querySelectorAll('#listenBtn');
		if (listenBtn2 !== null) {
			for (let l of listenBtn2) {
				l.addEventListener('click', clickListenBtn)
			}
		}
		const addCPList2 = document.querySelectorAll('#addCPList');
		if (addCPList2 !== null) {
			for (let a of addCPList2) {
				console.log(a);
				a.addEventListener('click', addToCPList);
			}
		}
	}


	// 앨범 듣기 기능
	const btnListenAlbums = document.querySelectorAll('#btnListenAlbum'); // 앨범페이지에 버튼 여러개라 반복문처리
	for (let btnListenAlbum of btnListenAlbums) {
		if (btnListenAlbum !== null) {
			btnListenAlbum.addEventListener('click', listenAlbum);
			function listenAlbum(event) {
				// console.log(id); // 정상작동: 1
				const albumId = event.target.getAttribute('data-id');
				const url1 = `/api/album?albumId=${albumId}`;
				axios.
					get(url1).
					then((response) => {
						console.log(response.data);
						// 앨범의 음원 리스트를 가져옴
						let listSong = response.data;
						let songId2 = listSong[0].songId;
						console.log(songId2);
						let url2 = `/song/listen?songId=${songId2}`
						console.log(url2);
						// 첫 곡은 바로듣기 메서드를 호출, 그 이후는 재생목록에 추가 메서드를 호출
						// 바로듣기 레스트컨트롤러 호출 일단 바로듣기 버튼 복붙해서 씀.
						setTimeout(function () {
							// 1초 후에 실행될 코드

							axios
								.get(url2)
								.then(async (response) => {
									console.log("성공");
									sessionStorage.setItem('index', 0);
									sessionStorage.setItem('isAdded', 'Y');
									parent.songFrame.location.reload();
									// 두번째 곡 이후부터는 재생목록에 추가.
									for (let i = 1; i < listSong.length; i++) {
										let id3 = listSong[i].songId;
										let url3 = `/song/addCurrentPlayList?songId=${id3}`;
										console.log(url3);
								
										try {
											let response = await axios.get(url3);
											console.log(response);
								
											if (sessionStorage.getItem('isAdded') === 'N') {
												sessionStorage.setItem('index', 0);
												sessionStorage.setItem('isAdded', 'Y');
												parent.songFrame.location.reload();
											}
										} catch (error) {
											console.log(error);
										}
								
										console.log('1초 후에 실행됩니다.');
										await new Promise(resolve => setTimeout(resolve, 50)); // 1초 대기
									}
									// alert('선택한 앨범을 재생합니다.');
									showAlert('선택한 앨범을 재생합니다.', 2000);

								})
								.catch((error) => console.log(error));
							console.log('1초 후에 실행됩니다.');
						}, 50);
					}).
					catch((error) => console.log(error));
			}
		}
	}

	// 앨범을 다음 곡으로 추가 기능
	const btnAddCPListAlbums = document.querySelectorAll('#btnAddCPListAlbum'); // 앨범페이지에 버튼 여러개라 반복문처리
	for (let btnAddCPListAlbum of btnAddCPListAlbums) {
		if (btnAddCPListAlbum !== null) {
			btnAddCPListAlbum.addEventListener('click', addCPListAlbum)

			function addCPListAlbum(event) {
				const albumId = event.target.getAttribute('data-id');
				
				// console.log(id); // 정상작동: 1
				let url = `/api/album?albumId=${albumId}`;
				axios.
					get(url).
					then((response) => {
						console.log(response.data);
						// 앨범의 음원 리스트를 가져옴
						listSong = response.data;
						let songId2 = listSong[0].songId;
						console.log(songId2);
						let url2 = `/song/listen?songId=${songId2}`
						console.log(url2);
						console.log(response);
						// 앨범의 음원 리스트를 가져옴

						// 앨범의 모든 곡을 다음 재생 목록에 추가.
						setTimeout(function () {
							// 1초 후에 실행될 코드

							axios
								.get(url2)
								.then(async (response) => {
									console.log("성공");
									sessionStorage.setItem('index', 0);
									sessionStorage.setItem('isAdded', 'Y');
									parent.songFrame.location.reload();
									// 두번째 곡 이후부터는 재생목록에 추가.
									for (let i = 1; i < listSong.length; i++) {
										let id3 = listSong[i].songId;
										let url3 = `/song/addCurrentPlayList?songId=${id3}`;
										console.log(url3);
								
										try {
											let response = await axios.get(url3);
											console.log(response);
								
											if (sessionStorage.getItem('isAdded') === 'N') {
												sessionStorage.setItem('index', 0);
												sessionStorage.setItem('isAdded', 'Y');
												parent.songFrame.location.reload();
											}
										} catch (error) {
											console.log(error);
										}
								
										console.log('1초 후에 실행됩니다.');
										await new Promise(resolve => setTimeout(resolve, 50)); // 1초 대기
									}
									// alert('선택한 앨범을 재생합니다.');
									showAlert('선택한 앨범을 재생합니다.', 2000);

								})
								.catch((error) => console.log(error));
							console.log('1초 후에 실행됩니다.');
						}, 50);
					}).
					catch((error) => console.log(error));


			}
		}
	}
	// 아래는 다른 페이지에서도 사용하는 함수

	let isModalOpen = false;

	function showModal() {
		if (isModalOpen) {
			console.log('Modal is already open.');
			return;
		}
		console.log('mainFrame showModal 호출성공');
		let cPListModal = document.querySelector('#sessionListModal');
		let myModal = new bootstrap.Modal(cPListModal);
		console.log(myModal);

		getCPList();
		// Ajax요청을 보내고 모달에 태그를 작성하는 album_detail.js의 함수를 호출
		myModal.show();

		// 모달이 열릴 때 상태 업데이트
		isModalOpen = true;
		cPListModal.addEventListener('hidden.bs.modal', () => {
			console.log('모달창 닫힐 때 실행.');
			isModalOpen = false;
		});

		// 모달창 재생목록 비우기 버튼
		const listEmptyBtn = document.querySelector('#listEmptyBtn');
		listEmptyBtn.addEventListener('click', listEmpty);
		function listEmpty() {

			const url = '/song/empty'; // 상대 경로 대신 절대 경로 사용

			axios.get(url)
				.then((response) => {
					console.log(response);
					console.log('재생목록 비우기');
					console.log(myModal);
					sessionStorage.setItem('isAdded', 'N');
					myModal.hide();
					showAlert('현재 재생목록을 비웠습니다.', 2000);
					parent.songFrame.location.reload();
				})
				.catch((error) => {
					console.log('Error:', error);
					showAlert('재생목록 비우기에 실패했습니다.', 2000);
				});

		}
	}

	// 다른 프레임에서 호출할 수 있도록 함수 노출
	window.showModal = showModal;
	// 챗지피티코드
	// 모달 버튼 클릭 시
	let openModalBtn = document.querySelectorAll('.openModalBtn');
	/* 				for (let mo of openModalBtn) {
						console.log(mo);
						mo.addEventListener('click', getCPList());
					} */
	openModalBtn.forEach((mo) => {
		mo.addEventListener('click', getCPList);

	});

	function getCPList() {
		const url = "/api/playList/cPList";
		// Ajax 요청을 보냄.
		axios
			.get(url)
			.then((response) => {
				console.log(response.data);
				// 재생 목록을 HTML로 작성 -> div#comments 영역에 출력.
				makeCPListElements(response.data);
			})
			.catch((error) => {
				console.log(error);
			});
	}
	// 외부에서 실행할 수 있도록 함수 노출
	window.getCPList = getCPList;

	function makeCPListElements(data) {
		// 모달 창에 리스트 출력하기
		let modalBody = document.getElementById('sessionListBody');
		// modal.style.display = "none";
		modalBody.innerHTML = ''; // 모달 바디 초기화

		// 재생목록 관리를 위한 인덱스 선언
		if (!sessionStorage.getItem('index')) {
			sessionStorage.setItem('index', 0);
		}
		index = parseInt(sessionStorage.getItem('index'));
		console.log(index);
		console.log(data.length);
		let ul = document.createElement('ul');
		ul.className = 'list-group';
		console.log(ul);
		if (data.length > 0) {
			// 비우기 버튼 숨김 해제
			document.querySelector('#listEmptyBtn').style.display = 'block';
			// 리스트가 있을 경우 출력
			for (let i = 0; i < data.length; i++) {
				let li = document.createElement('li');
				li.className = 'list-group-item';
				// 음원 아티스트와 음원 제목을 삽입.
				if (data[i].groupName !== null) {
					if (data[i].artistName !== null) {
						li.textContent = data[i].groupName + ', ' + data[i].artistName + ' - ' + data[i].title;
					} else {
						li.textContent = data[i].groupName + ' - ' + data[i].title;
					}
				} else {
					li.textContent = data[i].artistName + ' - ' + data[i].title;
				}
				if (i == index) { // 현재 재생중인 곡인 지 검사
					// 볼드체로 변경
					li.classList.add('text-primary');
				}
				ul.appendChild(li);
				console.log(li);
			}
		} else {
			console.log(data.length);
			document.querySelector('#listEmptyBtn').style.display = 'none';
			let li = document.createElement('li');
			li.className = 'list-group-item';
			li.classList.add('text-primary');
			li.textContent = '현재 재생목록이 없습니다.';
			ul.appendChild(li);
		}

		modalBody.appendChild(ul);

		const cPList = document.querySelectorAll('.list-group-item');

		for (let i = 0; i < cPList.length; i++) {
			cPList[i].addEventListener('click', (event) => {
				sessionStorage.setItem('index', i);
				showModal();
				parent.songFrame.location.reload();
			});
		}
	}


	// 플리에 추가 기능
	const btnAddUPList = document.querySelectorAll('#btnAddUPList');
	if (btnAddUPList === null) {
		console.log('이 페이지에는 플리 모달이 안쓰임');
		return;
	}
	const playListModal = new bootstrap.Modal(document.querySelector('div#staticBackdrop2'), { backdrop: 'static' });
	let currentPage = 1;
	const itemsPerPage = 5;
	let playlistsData = [];
	for (let a of btnAddUPList) {
		a.addEventListener('click', getPlayLists);
		function getPlayLists(event) {
			if (id == '') {
				if (confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")) {
					redirectToLogin();
					return;
				}
			}
			const uri = `../getPlayList/${id}`;
			songId = event.target.getAttribute('data-id');
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
	}
	function redirectToLogin() {
		const currentUrl = window.location.href;
		window.location.href = `/user/signin?target=${encodeURIComponent(currentUrl)}`;
	}
	// 앨범을 플리에 추가 기능
	const btnAddUPListAlbum = document.querySelector('#btnAddUPListAlbum');
	if (btnAddUPListAlbum === null) {
		console.log('앨범을 유저플레이리스트에 추가 버튼을 사용하지 않는 페이지');
		return;
	}
	btnAddUPListAlbum.addEventListener('click', getPlayListAlbum);
	function getPlayListAlbum() {
		if (id == '') {
			if (confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")) {
				redirectToLogin();
			}
			return;
		}
		const uri = `../getPlayList/${id}`;
		axios
			.get(uri)
			.then((response) => {

				playlistsData = response.data;

				displayPlayListsAlbum(currentPage);

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


	function makePlayListAlbumElements(data) {
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
				<a class="playListAlbum btn btn-outline-success form-control mt-2" data-id="${playlist.plistId}" >
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

		const aPlayLists = document.querySelectorAll('a.playListAlbum');
		for (let a of aPlayLists) {
			a.addEventListener('click', addAlbumPlayList);
		}
	}

	function addAlbumPlayList(event) {

		const plistId = event.currentTarget.getAttribute('data-id');
		let url = `../api/album?albumId=${albumId}`;
		console.log(url);
		axios.
			get(url).
			then((response) => {
				console.log(response);
				// 앨범의 음원 리스트를 가져옴
				let listSong = response.data;
				console.log(listSong);
				for (let i = 0; i < listSong.length; i++) {
					let songId = listSong[i].songId;
					console.log(songId);
					let albumData = { plistId, songId };
					console.log(albumData);
					axios.post('../checkSongInPlayList', albumData)
						.then((response) => {
							if (!response.albumData) {
								dubSong = listSong[i].title;
								if (confirm(`"${dubSong}"은 이미 추가된 곡입니다. 그래도 추가하시겠습니까?`)) {
									// 사용자가 확인을 눌렀을 때 추가 요청 보냄
									addToPlayList(albumData);
								}
							} else {
								// 데이터가 없으면 바로 추가 요청 보냄
								addToPlayList(albumData);
							}
						})
				}
			})
			.catch((error) => {
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
	function displayPlayListsAlbum(page) {
		const startIndex = (page - 1) * itemsPerPage;
		const endIndex = startIndex + itemsPerPage;
		const paginatedPlaylists = playlistsData.slice(startIndex, endIndex);
		makePlayListAlbumElements(paginatedPlaylists);
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

	function showAlert(message, duration) {
		// 알림창 생성
		const alertBox = document.createElement('div');
		alertBox.textContent = message;
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



});
