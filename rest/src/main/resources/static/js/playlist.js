/**
 * /playlists/playlist.jsp 포함
 */
document.addEventListener('DOMContentLoaded', () => {

	const pListId = document.querySelector('h2#pListId').textContent;

	getPlayListSong();

	function getPlayListSong() {
		const uri = `../getPlayListSong/${pListId}`;

		axios
			.get(uri)
			.then((response) => {
				console.log(response.data);
				makePlayListElements(response.data);
			})
			.catch((error) => {
				console.log(error);
			});
	}

	function makePlayListElements(data) {
		// 플리 목록 HTML이 삽입될 tbody
		const tbodySongsTableBody = document.querySelector('tbody#songsTableBody');
		const songCountElement = document.getElementById('totalSongs'); // 음원 갯수를 출력할 span 요소
		const playLists = document.querySelector('div#resultTable'); // 플리에 음원이 없을 경우 출력될 요소

		if (!tbodySongsTableBody) {
			console.error('songsTableBody를 찾을 수 없습니다.');
			return;
		}

		// 플리 목록 HTML 코드
		let htmlStr = '';
		// 플리 음원 갯수 count
		let songCount = 0;

		// 가장 최근에 추가된 곡을 저장할 변수
		let recentSong = null;

		// 기본 이미지 URL 정의
		const defaultImage = '../images/icon/default.png';
		const playImage = '../images/icon/play.png'
		// 재생목록 이미지
		const playlistImage = '../images/icon/playList.png'

		for (let playlistSong of data) {

			// albumPage, songPage, artistPage로 이동할 주소 지정
			const albumPage = `/album/detail?albumId=${playlistSong.albumId}`;
			const songPage = `/song/detail?songId=${playlistSong.songId}`;

			// ${playlist.albumImage}가 null이면 기본 이미지 사용
			const albumImageSrc = playlistSong.albumImage ? `../images/albumcover/${playlistSong.albumImage}` : defaultImage;

			// 가장 최근에 추가된 곡을 설정
			// 동시에 음원을 추가할 경우 createdTime이 같은 경우도 있어서 createdTime 기준이 아닌 playlistSongId를 기준으로 잡음
			if (!recentSong || playlistSong.playlistSongId > recentSong.playlistSongId) {
				recentSong = playlistSong;
			}
			
            // 그룹 네임과 아티스트 네임을 클릭 가능한 링크로 처리
            const groupNames = playlistSong.groupName || [];
            const artistNames = playlistSong.artistName || [];

            // 그룹 네임을 문자열로 결합
            const groupNamesString = groupNames.map((name, index) => {
                return `<a href="../group/songs?groupId=${playlistSong.groupId[index]}" style="color: black; text-decoration: none;"
                        onmouseover="this.style.color='blue';" onmouseout="this.style.color='black';">${name}</a>`;
            }).join(', ');

            // 아티스트 네임을 문자열로 결합
            const artistNamesString = artistNames.map((name, index) => {
                return `<a href="../artist/songs?artistId=${playlistSong.artistId[index]}" style="color: black; text-decoration: none;"
                        onmouseover="this.style.color='blue';" onmouseout="this.style.color='black';">${name}</a>`;
            }).join(', ');

            // 그룹과 아티스트 네임을 결합
            let singerLinksHtml = '';
            if (groupNames.length > 0) {
                singerLinksHtml = groupNamesString;
                if (artistNames.length > 0) {
                    singerLinksHtml += ', ' + artistNamesString;
                }
            } else {
                singerLinksHtml = artistNamesString;
            }

			console.log(playlistSong);
			htmlStr += `
            <tr>
                <td style="text-align: center; vertical-align: middle;">
                    <input type="checkbox" class="songCheckbox" data-songId="${playlistSong.songId}" data-createdTime="${playlistSong.createdTime}">
                </td>
                <td style="text-align: left; vertical-align: middle;">
                    <a href="${albumPage}">
                        <img alt="songImg" src="${albumImageSrc}" width="80px" height="80px">
                    </a>
                </td>
                <td style="text-align: left; vertical-align: middle; font-size: 14px;" class="fs-6">
                    <a href="${songPage}" style="color: black; text-decoration: none;"
                        onmouseover="this.style.color='blue';" onmouseout="this.style.color='black';">${playlistSong.title}</a><br>
                    <span style="display: inline-block; margin-top: 5px;">
                    <a href="${albumPage}" style="color: gray; text-decoration: none;"
                    onmouseover="this.style.color='blue';" onmouseout="this.style.color='gray';">${playlistSong.albumName}</a>
                    </span>
                </td>
                <td style="text-align: left; vertical-align: middle; font-size: 14px" class="fs-6">
                    ${singerLinksHtml}
                </td>
                <td style="text-align: center;">
                    <button style="background-image: url('${playImage}'); width: 40px; height: 40px; background-size: cover; background-repeat: no-repeat;"
                    data-songId="${playlistSong.songId}" data-id="${playlistSong.songId}" class="playButton btn mt-3" id="listenBtn"></button>
                </td>
                <td style="text-align: center;">
                    <button data-id="${playlistSong.songId}"
                        id="addCPList" title="재생목록에 추가" class="btn mt-3" style="background-image: url('${playlistImage}'); 
                        width: 40px; height: 40px; background-size: cover; background-repeat: no-repeat;"></button>
                </td>
            </tr>
            `;

			songCount++;

			document.getElementById('selectAllCheckbox').addEventListener('change', function () {
				const checkboxes = document.querySelectorAll('.songCheckbox');
				checkboxes.forEach(checkbox => {
					checkbox.checked = this.checked;
				});
			});

		}

		// 작성된 HTML 코드를 tbody 영역에 삽입.
		tbodySongsTableBody.innerHTML = htmlStr;
		songCountElement.textContent = songCount; // 곡 수 업데이트

		// 추가된 곡이 없으면(플리가 비어있으면) 리스트를 출력하는 부분에 해당 텍스트, defaultListImage 출력.
		const defaultListImage = '../images/icon/defaultList.png';
		const albumCoverImg = document.querySelector('img[alt="albumCover"]'); // 플레이리스트 커버(이미지) 태그 지정

		if (songCount == 0) {
			htmlStr += `
                <div class='container' style="text-align: center;">
                    <img src="${defaultListImage}" width="80px" height="80px">
                    <h5 id="defaultList" class="mt-4" style="text-align: center; color:gray; font-size: 16px">
                        추가된 곡이 없습니다..!
                    </h5>
                </div>
                `
			albumCoverImg.src = defaultImage; // 플레이리스트에 곡이 없다면(count가 0) 플레이리스트 커버(이미지)를 defaultImage로 설정
			playLists.innerHTML = htmlStr;
		}

		// 가장 최근에 추가된 곡의 앨범 커버 이미지 설정
		if (recentSong) {
			const recentAlbumImageSrc = recentSong.albumImage ? `../images/albumcover/${recentSong.albumImage}` : defaultImage;
			albumCoverImg.src = recentAlbumImageSrc;
		}


		// 플레이리스트 곡 삭제버튼 기능
		const deleteButton = document.getElementById('deleteButton');
		if (deleteButton) {
			deleteButton.addEventListener('click', function () {
				const checkedCheckboxes = document.querySelectorAll('.songCheckbox:checked');
				if (checkedCheckboxes.length === 0) {
					alert('삭제할 곡을 선택해주세요.');
					return;
				}

				const confirmResult = confirm('선택한 곡을 플레이리스트에서 삭제할까요?');
				if (!confirmResult) {
					return;
				}

				checkedCheckboxes.forEach(checkbox => {
					const songId = checkbox.getAttribute('data-songId');
					const createdTime = checkbox.getAttribute('data-createdTime');
					console.log(songId, createdTime);
					deletePlayListSong(songId, createdTime);
				});
			});
		}

		const aPlayLists = document.querySelectorAll('a.playList');
		for (let a of aPlayLists) {
			a.addEventListener('click', () => {
				const plistId = a.getAttribute('data-songId');
				console.log('클릭한 플레이리스트의 plistId:', plistId);
				window.location.href = `../playlists/playlist?plistId=${plistId}`;
			});
		}
	}

	function deletePlayListSong(songId, createdTime) {
		console.log(pListId, songId, createdTime);
		const uri = `../deletePlayListSong/${pListId}/${songId}/${createdTime}`;
		axios
			.delete(uri)
			.then((response) => {
				console.log(response.data);
				getPlayListSong();
			})
			.catch((error) => {
				console.error(error);
				alert('곡 삭제 중 오류가 발생했습니다.');
			});
	}

	document.getElementById('editButton').addEventListener('click', function () {
		var pListNameElement = document.getElementById('pListName');
		var currentName = pListNameElement.innerText;

		// 이미 입력 필드가 있는지 확인
		if (document.getElementById('pListNameInput')) {
			return; // 이미 있다면 함수 종료
		}

		var input = document.createElement('input');
		input.type = 'text';
		input.value = currentName;
		input.className = 'form-control';
		input.id = 'pListNameInput';
		input.style.position = 'absolute';
		input.style.zIndex = '10';
		input.style.width = '700px'; // pListName 요소의 너비와 동일하게 설정
		input.maxLength = '20'; // input 내의 글자수 제한 설정

		console.log('create input');

		// 입력 필드를 pListName 요소 위에 배치
		pListNameElement.style.position = 'relative';
		pListNameElement.insertBefore(input, pListNameElement.firstChild);
		console.log('set input');

		// pListName 텍스트를 숨김
		pListNameElement.style.display = 'none';

		// input 바깥으로 포커스 아웃시키면 작업 취소
		input.addEventListener('blur', function () {
			cancelUpdatePlaylistName();
		});

		// ESC 키 다운 이벤트 발생 시 작업 취소
		document.addEventListener('keydown', function (event) {
			if (event.key === 'Escape') {
				cancelUpdatePlaylistName();
			}
		});

		// input에서 Enter 키다운 이벤트가 발생하면 input에 입력된 값을 받아 업데이트 작업(updatePlayListName 함수) 실행  
		input.addEventListener('keydown', function (event) {
			if (event.key === 'Enter') {
				updatePlayListName(input.value);
				pListNameElement.innerText = input.value; // 텍스트 업데이트
				pListNameElement.style.display = 'block'; // 원래 텍스트 보이기
				input.remove(); // 입력 필드 제거
			}
		});

		input.focus();
	});

	function cancelUpdatePlaylistName() {
		var pListNameElement = document.getElementById('pListName');
		pListNameElement.style.display = 'block'; // 원래 텍스트 보이기
		var input = document.getElementById('pListNameInput');
		// 입력 필드가 존재하고, 이를 pListName 요소의 자식 요소로서 제거
		if (input && input.parentNode === pListNameElement) {
			// setTimeout을 사용하여 비동기적으로 입력 필드를 제거
			setTimeout(function () {
				if (input.parentNode === pListNameElement) {
					pListNameElement.removeChild(input); // 입력 필드 제거
				}
			}, 0);
		}
	}

	function updatePlayListName(pListName) {
		console.log(pListId);
		console.log(pListName);

		const uri = `../updatePlayListName/${pListId}`;
		axios
			.put(uri, { plistName: pListName })
			.then((response) => {
				console.log(response);
				alert(`플레이리스트 이름이 업데이트되었습니다.`);
			})
			.catch((error) => {
				console.log(error);
			});
	}

	// 김영웅: 여기부터 플레이리스트를 재생목록에 추가 기능을 구현하기 위한 코드.
	const addCPListFromUPList = document.querySelector('#addCPListFromUPList');
	addCPListFromUPList.addEventListener('click', CPListFromUPList)
	function CPListFromUPList() {
		const pListId = addCPListFromUPList.getAttribute('data-id');
		const url = `../getPlayListSong/${pListId}`;
		axios.
			get(url).
			then((response) => {
				console.log(response);
				console.log(response.data);
				let listSong = response.data;
				let songId = listSong[0].songId;
				let url = `../song/listen?songId=${songId}`
				console.log(url);
				axios
					.get(url)
					.then((response) => {
						console.log("성공");
						sessionStorage.setItem('index', 0);
						sessionStorage.setItem('isAdded', 'Y');
						parent.songFrame.location.reload();
						// 두번째 곡 이후부터는 재생목록에 추가.
						for (let i = 1; i < listSong.length; i++) {
							let id3 = listSong[i].songId;
							let url3 = `../song/addCurrentPlayList?songId=${id3}`
							console.log(url3);
							axios.
								get(url3).
								then((response) => {
									console.log(response);
									if (sessionStorage.getItem('isAdded') === 'N') {
										sessionStorage.setItem('index', 0);
										sessionStorage.setItem('isAdded', 'Y');
										parent.songFrame.location.reload();
									}
								}).
								catch((error) => { console.log(error); });
						}
					})
					.catch((error) => console.log(error));
			}).
			catch((error) => { console.log(error); });
	}
});