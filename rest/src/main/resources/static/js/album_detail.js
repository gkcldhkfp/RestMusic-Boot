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

});



