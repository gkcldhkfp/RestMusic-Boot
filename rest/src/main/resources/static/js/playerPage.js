/**
 * playerPage.jsp에 포함
 */
document.addEventListener('DOMContentLoaded', () => {
	console.log("playerPage.js");
	// 재생목록 관리를 위한 인덱스 선언
	if (!sessionStorage.getItem('index')) {
		// 세션스토리지를 사용한 이유: 여기서 index=0;으로 선언하면 새로고침할 때마다 인덱스는 0이 되버림.
		sessionStorage.setItem('index', 0);
	}
	// 세션에서 가져와서 String으로 되어있는 index를 int타입으로 바꿈
	let index = parseInt(sessionStorage.getItem('index'));

	// 재생, 중지 버튼을 토글 형식으로 구현하기 위해 버튼을 찾음
	const playButton = document.querySelector('#playButton');
	const pauseButton = document.querySelector('#pauseButton');

	// 음원 버튼들 찾음
	const prevBtn = document.querySelector('#prevBtn');
	const nextBtn = document.querySelector('#nextBtn');
	const stopBtn = document.querySelector('#stopBtn');

	// 결제 
	let isPurUser = false;

	// 인덱스 변경 감지 함수를 위한 객체 선언.
	let obj = {};

	// 인덱스 변경 감지 함수
	Object.defineProperty(obj, 'index', {
		// 얘가 아래에 있으면 인덱스 변경을 감지하지 못함.
		// 변경하는 곳 위에 있어야되는 함수임.
		get: function () {
			return index;
		},
		set: function (newIndex) {
			console.log(`index가 ${index}에서 ${newIndex}로 변경되었습니다.`);
			index = parseInt(newIndex);
			sessionStorage.setItem('index', index);
			// 세션에 업데이트
			// location.href = location.href;
			parent.songFrame.location.reload();
			// 새로고침
		},
		enumerable: true,
		configurable: true
	});

	// TODO: 레스트컨트롤러 안만듬
	/* 	// 결제 회원인지 판별하기 위한 Ajax요청 호출
		if (id !== '') {
			const urlForPur = `/Rest/purchase/isPurUser?id=${id}`;
			axios
				.get(urlForPur)
				.then((response) => {
					console.log(response.data);
					isPurUser = response.data
				}).
				catch((error) => { console.log(error); });
		} */



	// ! JSON을 리스트로 만드는 코드는 playerPage.jsp의 아래부분에 선언함.
	//? 자동 새로고침 없어도 되게끔 만들어야 할듯.
	// // console.log(cPList);
	// if (!cPList) {
	// 	// 세션의 cPList가 비어있으면 새로고침해서 불러옴.
	// 	setTimeout(() => location.reload(true), 1000);
	// }
	if (cPList) {
		console.log(cPList);
		console.log('index = ' + index);
		if (cPList[index] === undefined || cPList[index] === null) {
			console.log('인덱스가 배열의 크기를 넘었어요!');
			// 객체가 비어있는 지 검사하는 자바스크립트 코드
			if (index > 0) {
				console.log("index > 0"); // 디버깅용 로그
				// 인덱스 변경 감지로 새로고침 했는데 인덱스가 커서
				// 배열 원소가 비어있는 경우
				obj.index = 0;
				console.log('index = ' + index);
				// 인덱스 초기화 => 새로고침됨
			}
			if (index <= -1) {
				console.log("index < -1"); // 디버깅용 로그
				// 인덱스 변경 감지로 새로고침 했는데 인덱스가 음수여서
				// 배열 원소가 비어있는 경우
				obj.index = cPList.length - 1;
				// 배열의 마지막 원소로 추가
				console.log('index = ' + index);
			}
		}
		console.log(cPList[index].songPath);
		// 데이터 베이스에 저장된 음악 경로를 cPList에서 꺼내서 오디오 객체를 생성함.
		var audio = new Audio('../songs/' + cPList[index].songPath);
		audio.volume = 0.5; // 초기 볼륨 설정

		// 음악이 로드되면 자동으로 실행하는 코드인데 페이지가 로드되자마자는 크롬 정책때문에 실행하지 못함
		audio.autoplay = true;
		console.log(typeof audio + " " + audio);

		// 모든 요소가 로드되었을 때 실행하는 이벤트 리스너
		// 맨 위에 있는 DomContentLoaded와의 차이점
		// 이미지같은 경우는 첫 요청에 바로 보내지지 않고
		// img태그같은 걸 만났을 때 다시 서버에 요청을 보낸다.
		// Dom은 위 같은 상황에서 다시 요청 보내기 전에 실행되는 이벤트리스너 
		// window.onload는 위 같은 상황에서 다시 요청을 보내고 
		// 응답을 받은 후 이미지가 로드되었을 때 실행되는 이벤트 리스너
		// 음악 객체를 다루기 때문에 window.onload를 사용해야 하는듯?

		//* 일반태그 로드 - (Dom이벤트리스너 실행) - img태그같은 재요청 태그 로드 - (window.onload 이벤트 리스너 실행)
		window.onload = function () {
			// songFrame의 버튼으로 mainFrame의 모달을 활성화 하기 위한 코드.
			const showModalButton = document.querySelector('#showModalButton');
			showModalButton.addEventListener('click', () => {
				console.log('mainframe의 메서드 호출');
				parent.frames['mainFrame'].showModal();
			});
			// 정보 업데이트
			// 다른 자바스크립트 파일에서 노출한 함수 실행.
			// 곡 변경 시 모달창의 하이라이팅도 업데이트
			parent.frames['mainFrame'].getCPList();

			// span 객체 얻기
			var current = document.getElementById('current');
			var total = document.getElementById('total');
			// progress 객체 얻기
			var progress = document.querySelector('#progress');
			var progressBar = document.querySelector('.progress');
			var minute1Progress = document.querySelector('#minute1');

			// 앨범 커버 사진 설정
			const albumImage = document.querySelector('#albumImage');
			// 앨범 커버 사진 등록
			albumImage.src = "../images/albumcover/" + cPList[index].albumImage;


			// 노래 제목 설정
			const musicTitle = document.querySelector('#music-title');
			musicTitle.innerHTML = cPList[index].title;
			// 노래 제목에 음원 상세페이지 연결
			musicTitle.href = '/song/detail?songId=' + cPList[index].songId;

			// 삽입할 요소 가져오기
			const artistElement = document.querySelector("#artist");

			// 링크 생성 함수
			function createLink(name, id, type) {
				return `<a href="/${type}/songs?${type}Id=${id}" target="mainFrame" style="cursor: pointer; font-weight: normal; text-decoration: none; color: black;" onmouseover="this.style.fontWeight='bold'; this.style.textDecoration='underline';" onmouseout="this.style.fontWeight='normal'; this.style.textDecoration='none'; this.style.color='black';">${name}</a>`;
			}

			// 그룹과 아티스트 텍스트를 추가할 배열
			let elements = [];

			const item = cPList[index];

			if (item) { // 해당 인덱스에 항목이 존재하는지 확인
				// 그룹 처리
				if (item.groupName && item.groupName.length > 0) {
					item.groupName.forEach((name, idx) => {
						const groupLink = createLink(name, item.groupId[idx], 'group');
						elements.push(groupLink);
					});
				}

				// 아티스트 처리
				if (item.artistName && item.artistName.length > 0) {
					// 그룹이 있었다면 구분자 추가
					if (elements.length > 0) {
						elements.push(", ");
					}

					item.artistName.forEach((name, idx) => {
						const artistLink = createLink(name, item.artistId[idx], 'artist');
						elements.push(artistLink);

						// 마지막 요소가 아니면 쉼표 추가
						if (idx < item.artistName.length - 1) {
							elements.push(", ");
						}
					});
				}
			} else {
				console.log("지정된 인덱스에 항목이 없습니다.");
			}

			// elements 배열의 내용을 연결하여 artistElement에 삽입
			artistElement.innerHTML = elements.join("");




			prevBtn.addEventListener('click', previous);
			nextBtn.addEventListener('click', next);
			stopBtn.addEventListener('click', stop);

			pauseButton.addEventListener('click', toggleAudio);
			playButton.addEventListener('click', toggleAudio);

			// Audio 객체에 timeupdate 이벤트 처리를 위한 리스너 부착
			// addEventListener() 함수 사용
			// 시간이 변경될 때 마다 실행되는 이벤트 리스너
			audio.addEventListener('timeupdate', function () {
				// 오디오의 총 재생시간 얻고 설정
				total.innerHTML = convertSecondsToMinutesSeconds(audio.duration);
				// 오디오의 현재 재생시간 얻고 설정
				current.innerHTML = convertSecondsToMinutesSeconds(audio.currentTime);
				// 타임 업데이트에 따른 프로그레스 진행정도 표시

				// 최대값 설정
				progress.setAttribute('aria-valuemax', audio.duration);
				// 진행 정도
				var ratio = audio.currentTime / audio.duration * 100;
				progress.style.width = ratio + '%';
				progress.setAttribute('aria-valuenow', ratio);
				if (id === '' || isPurUser === false) {
					total.innerHTML = '1:00';
					// TODO: 나중에 조건을 결제한 아이디로 바꾸어야함.
					// 1분 비율 계산
					var minRatio = 60 / audio.duration * 100;
					// 1분 위치를 고정시키기 위해 현재 재생 비율을 뺌
					var minBar = minRatio - ratio;
					minute1Progress.style.width = minBar + '%';
					minute1Progress.style.opacity = 0.5;
					if (audio.currentTime > 60) {
						console.log('60초 넘어가면 실행되어야함');

						// 1분을 넘어가면 실행하는 조건문
						stop();
						// 로그인 권고창 띄움
						// 일단 알러트
						if (id === '') {
							// 로그인하지 않은 경우
							needToSignIn();
						} else if (isPurUser === false) {
							needToPaid();
						}

						return;
					}
				}

			});


			// 음원이 끝까지 재생되었을 때 실행되는 이벤트 리스너
			audio.addEventListener("ended", function () {
				// 음원 하나가 끝나면 다음 곡을 재생하는 함수 호출.
				next();

			});

			// 음원이 실행 중일 때 버튼을 활성화하기 위한 이벤트 리스너
			audio.addEventListener("playing", () => {
				playButton.style.display = 'none'; // 재생 버튼 숨기기
				pauseButton.style.display = 'block'; // 정지 버튼 보이기
			});

			audio.addEventListener("puase", () => {
				playButton.style.display = 'block'; // 재생 버튼 보이기
				pauseButton.style.display = 'none'; // 정지 버튼 숨기기
			});


			// 프로그래스 바 클릭 이벤트 처리
			// 클릭했을 때 음원의 재생 위치를 변경하는 기능을 만들거임.
			progressBar.addEventListener("click", function (e) {
				var rect = progressBar.getBoundingClientRect(); // 프로그래스 바의 위치와 크기 가져오기
				var totalWidth = rect.width; // 프로그래스 바의 전체 너비
				var clickX = e.clientX - rect.left; // 클릭한 위치의 X 좌표
				var percentage = (clickX / totalWidth) * 100; // 클릭한 위치의 백분율 계산


				// 음원 재생 위치 설정
				var duration = audio.duration; // 음원 파일의 전체 재생 시간 가져오기
				var currentTime = (percentage / 100) * duration; // 클릭한 위치에 해당하는 재생 시간 계산
				audio.currentTime = currentTime; // 재생 위치 설정
				// 1분 비율 계산
				if ((id === '' || isPurUser === false) && audio.currentTime > 60) {
					console.log('60초 넘어가면 실행되어야함');
					// 1분의 위치를 넘었는 지 검사 
					// 정지 메서드 호출
					stop();
					// 음원의 현재 위치를 0으로 초기화하고 재생을 멈춤.

					// 로그인 권고창 띄움
					// 일단 알러트
					if (id === '') {
						// 로그인하지 않은 경우
						needToSignIn();
					} else if (isPurUser === false) {
						needToPaid();
					}

					return;
				}
				// 프로그래스 바 업데이트
				progress.style.width = percentage + '%';
				progress.setAttribute('aria-valuenow', percentage);
			});

			function needToSignIn() {
				// const currentUrl = window.location.href;
				if (confirm('로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠습니까?')) {
					parent.mainFrame.location.href = "/Rest/user/signin";
					// window.location.href = `/Rest/user/signin?target=${encodeURIComponent(currentUrl)}`;
				}
			}
			function needToPaid() {
				// const currentUrl = window.location.href;
				if (confirm('결제가 필요합니다. 결제 페이지로 이동하시겠습니까?')) {
					parent.mainFrame.location.href = "/Rest/purchase";
					// window.location.href = `/Rest/user/signin?target=${encodeURIComponent(currentUrl)}`;
				}
			}

			// 정지버튼시 호출하는 함수
			function stop() {
				// stop 함수는 별도로 없어서 따로 이런식으로 만들자.
				// if (audio.played) {
				// 음원을 중지하고 토글버튼 업데이트
				pauseAudio();
				audio.currentTime = 0;
				// }
			};
			// 이전 곡 버튼 클릭 시 호출하는 함수
			function previous() {
				obj.index = index - 1;
			}

			function next() {
				obj.index = index + 1;
			}
			// 시간형식 맞추는 함수
			function convertSecondsToMinutesSeconds(seconds) {
				// 소수점 이하 버리기
				seconds = Math.floor(seconds);

				// 분 계산
				var minutes = Math.floor(seconds / 60);

				// 초 계산
				var remainingSeconds = seconds % 60;

				// 결과 문자열 조합
				var result = minutes + ":" + remainingSeconds;

				return result;
			}

			// 볼륨 슬라이더 엘리먼트 가져오기
			var volumeSlider = document.getElementById('volumeSlider');
			// 음소거 버튼 엘리먼트 가져오기
			var muteButton = document.getElementById('muteButton');
			var muteIcon = document.getElementById('muteIcon');
			// 볼륨 슬라이더 값 변경 이벤트 리스너 추가
			volumeSlider.addEventListener('input', function () {
				audio.volume = volumeSlider.value;
				console.log('Volume:', audio.volume); // 디버깅용 로그
				updateMuteButton(); // 볼륨 조절 시 음소거 버튼 상태 업데이트
			});

			// 음소거 버튼 클릭 이벤트 리스너 추가
			muteButton.addEventListener('click', function () {
				if (audio.volume > 0) {
					audio.volume = 0;
					volumeSlider.value = 0;
					console.log(audio.volume);
				} else {
					audio.volume = volumeSlider.value = 0.5; // 기본 볼륨을 0.5 로 설정
					console.log(audio.volume);
				}
				updateMuteButton();
			});

			// 음소거 버튼 아이콘 업데이트 함수 정의
			function updateMuteButton() {
				if (audio.volume == 0) {
					muteIcon.classList.remove('bi-volume-up-fill');
					muteIcon.classList.add('bi-volume-mute-fill');
					// 음소거 아이콘으로 변경
				} else {
					muteIcon.classList.remove('bi-volume-mute-fill');
					muteIcon.classList.add('bi-volume-up-fill');
					// 음소거 아이콘 해제
				}
			}






			function toggleAudio() {
				if (audio.paused) {
					playAudio();
				} else {
					pauseAudio();
				}
			}

			function playAudio() {
				playButton.style.display = 'none'; // 재생 버튼 숨기기
				pauseButton.style.display = 'block'; // 정지 버튼 보이기
				audio.play();
			}

			function pauseAudio() {
				playButton.style.display = 'block'; // 재생 버튼 보이기
				pauseButton.style.display = 'none'; // 정지 버튼 숨기기
				audio.pause();
			}

			// prevBtn.addEventListener('click', previous);
			// nextBtn.addEventListener('click', next);
			// stopBtn.addEventListener('click', stop);

			// pauseButton.addEventListener('click', toggleAudio);
			// playButton.addEventListener('click', toggleAudio);
			prevBtn.addEventListener('mouseenter', scaleUp);
			prevBtn.addEventListener('mouseleave', scaleDown);

			nextBtn.addEventListener('mouseenter', scaleUp);
			nextBtn.addEventListener('mouseleave', scaleDown);

			stopBtn.addEventListener('mouseenter', scaleUp);
			stopBtn.addEventListener('mouseleave', scaleDown);

			pauseButton.addEventListener('mouseenter', scaleUp);
			pauseButton.addEventListener('mouseleave', scaleDown);

			playButton.addEventListener('mouseenter', scaleUp);
			playButton.addEventListener('mouseleave', scaleDown);

			muteButton.addEventListener('mouseenter', scaleUp);
			muteButton.addEventListener('mouseleave', scaleDown);

			muteIcon.addEventListener('mouseenter', scaleUp);
			muteIcon.addEventListener('mouseleave', scaleDown);

			volumeSlider.addEventListener('mouseenter', scaleUp);
			volumeSlider.addEventListener('mouseleave', scaleDown);

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
		};
	}

});