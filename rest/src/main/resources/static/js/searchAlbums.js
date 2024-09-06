/**
 * searchAlbums
 */
 
 document.addEventListener('DOMContentLoaded', () =>{
    
    let offset = 18;
    let debounceTimer;

    
    window.onscroll = function() {
        // 이전에 설정된 디바운스 타이머를 제거
        clearTimeout(debounceTimer);

        // 100ms 후에 스크롤 이벤트가 반응하도록 디바운스 타이머 설정
        debounceTimer = setTimeout(function() {
            if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
                loadMoreData();
            }
        }, 100); // 100ms 후에 다시 호출되지 않으면 추가 로딩 실행
    };
    
    const searchResult = document.querySelector('ul#searchResult');
    

    function loadMoreData() {
        axios.get('./rest/albums', {
            params: {
                keyword: keyword[0],
                sortType: sortType[0],
                offset: offset
            }
        })
        .then(function(response) {
            // Handle success
            const data = response.data;
            if (data.length === 0) {
                window.onscroll = null;
                return;
            }

            if (data.length > 0) {
                console.log(data);
                data.forEach(function(s) {
                    const arIds = (s.artistId || '').toString().split(',').map(part => part.trim()).filter(Boolean);
                    const arNames = (s.artistName || '').toString().split(',').map(part => part.trim()).filter(Boolean);
                    
                    let inputStr = '';
                    for(let i = 0; i < arIds.length; i++) {
                        inputStr +=`<small>
                            <a href="/artist/detail?artistId=${arIds[i]}" 
                                style="max-width: 200px; max-height: 20px">${arNames[i]}</a></small>            
                        `;
                    }

                    let htmlStr = '';
                    htmlStr += `
                        <li style="display: flex; align-items: flex-start; padding: 15px; width: 300px; box-sizing: border-box;">
                            <img src="${s.albumImage != null ? '/artist/image/' + s.albumImage : 'https://via.placeholder.com/175'}"
                                alt="이미지" style="width: 175px; height: 175px; object-fit: cover;">
                            <div style="display: flex; flex-direction: column; padding-left: 15px; flex: 1;">
                                <div style="height: 60px;">
                                    <a href="/album/detail?albumId=${s.albumId}" style="margin: 0;">${s.albumName}</a>
                                    ${s.artistType === 'artist' ? `
                                    <small style="margin: 0; font-size: 0.7em;">
                                        <a href="/artist/detail?artistId=${s.artistId}">${s.artistName}</a>
                                    </small>
                                    ` : `
                                    <small style="margin: 0; font-size: 0.7em;">
                                        <a href="/group/detail?groupId=${s.artistId}">${s.artistName}</a>
                                    </small>
                                    `}
                                </div>
                                <div>
                                    <p class="mb-2 mt-3">
                                        <small style="font-size: 0.7em;">${s.albumType}</small>
                                        <br/>
                                        <small style="font-size: 0.7em;">${s.albumReleaseDate}</small>
                                        <button style="background-image: url('../images/icon/play.png'); width: 30px; height: 30px; background-size: cover; background-repeat: no-repeat;"
                                            data-id="${s.albumId}" class="btnListen btn mt-3" id="listenBtn"></button>
                                        <button style="background-image: url('../images/icon/playList.png'); width: 40px; height: 40px; background-size: cover; background-repeat: no-repeat;"
                                            data-id="${s.albumId}" class="btn addNextPlay mt-3" id="addCPList"></button>
                                    </p>
                                </div>
                            </div>
                        </li>
                    `;

                    searchResult.innerHTML += htmlStr;
                });

                // Update offset for the next batch
                offset += 18;
            }
        })
        .catch(function(error) {
            // Handle error
            console.error(error);
        });
    }
    
 });