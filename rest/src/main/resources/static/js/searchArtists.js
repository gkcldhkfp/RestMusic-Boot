/**
 *  search artists
 */
document.addEventListener('DOMContentLoaded', () => {

    let offset = 15;
    let debounceTimer;


    window.onscroll = function() {
        // 이전에 설정된 디바운스 타이머를 제거
        clearTimeout(debounceTimer);

        // 100ms 후에 스크롤 이벤트가 반응하도록 디바운스 타이머 설정
        debounceTimer = setTimeout(function() {
            if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
                loadMoreData();
                console.log('load');
            }
        }, 100); // 100ms 후에 다시 호출되지 않으면 추가 로딩 실행
    };

    const searchResult = document.querySelector('ul#searchResult');


    function loadMoreData() {
        axios.get('./rest/artists', {
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
                        let inputStr = '';

                        if (s.type == 'artist') {
                            const arIds = (s.id || '').toString().split(',').map(part => part.trim()).filter(Boolean);
                            const arNames = (s.name || '').toString().split(',').map(part => part.trim()).filter(Boolean);

                            for (let i = 0; i < arIds.length; i++) {
                                inputStr += `<small>
                            <a href="/artist/detail?artistId=${arIds[i]}" 
                                style="max-width: 200px; max-height: 20px">${arNames[i]}</a></small>            
                        `;
                            }
                        }

                        if (s.type == 'group') {
                            const arIds = (s.id || '').toString().split(',').map(part => part.trim()).filter(Boolean);
                            const arNames = (s.name || '').toString().split(',').map(part => part.trim()).filter(Boolean);

                            for (let i = 0; i < arIds.length; i++) {
                                inputStr += `<small>
                            <a href="/group/detail?artistId=${arIds[i]}" 
                                style="max-width: 200px; max-height: 20px">${arNames[i]}</a></small>            
                        `;
                            }
                        }



                        let htmlStr = '';
                        htmlStr += `
                        <li style="display: flex; flex-direction: column; align-items: center; width: 150px; box-sizing: border-box;">
                            ${a.type === 'artist' ? `
                            <a href="/artist/detail?artistId=${a.id}" class="mt-2">
                                <img src="${a.image != null ? '/artist/image/' + a.image : '/images/profileimage/profile.jpg'}" 
                                    alt="이미지" style="width: 150px; height: 150px; object-fit: cover;" class="rounded-circle">
                            </a>
                            ` : `
                            <a href="/group/detail?groupId=${a.id}" class="mt-2">
                                <img src="${a.image != null ? '/artist/image/' + a.image : '/images/profileimage/profile.jpg'}" 
                                    alt="이미지" style="width: 150px; height: 150px; object-fit: cover;" class="rounded-circle">
                            </a>
                            `}
                            
                            <div style="text-align: center; margin-top: 10px;">
                                ${a.type === 'artist' ? `
                                <small>
                                    <a href="/artist/detail?artistId=${a.id}" class="mt-2" style="font-weight: bold;">${a.name}</a>
                                </small>
                                ` : `
                                <small>
                                    <a href="/group/detail?groupId=${a.id}" class="mt-2" style="font-weight: bold;">${a.name}</a>
                                </small>
                                `}
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