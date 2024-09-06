/**
 * purchase.html 포함
 */
document.addEventListener('DOMContentLoaded', () => {
    
    const btnPurchase = document.querySelector('button#purchase');  
    btnPurchase.addEventListener('click', requestPay);
    
    //if(loginUserId == ''){
    //    loginUserId = 1;
    //}
    const amount = 4400;
    const IMP = window.IMP; 
    IMP.init('imp62307542'); // 'imp12345678' 대신 실제 가맹점 식별코드를 입력하세요.
       function generateMerchantUID() {
        // 현재 시간을 밀리초 단위로 가져옴
        const timestamp = Date.now();
    
        // 랜덤한 숫자를 추가하여 더 고유하게 만듦
        const randomNum = Math.floor(Math.random() * 100000);
    
        // 주문 번호 형식에 맞추어 문자열 생성
        return `ORD${timestamp}-${randomNum}`;
    }
    
    const merchant_uid = generateMerchantUID();
    console.log(merchant_uid);
    
    
    
    function requestPay() {
         if(loginUserId == ''){
            alert('로그인이 필요합니다');
            return;
        }
        
        IMP.request_pay({
          pg: "html5_inicis.INIpayTest",
          pay_method: "card",
          merchant_uid: merchant_uid,   // 주문번호
          name: "Rest 음악 MP3 정기결제이용권",
          amount: amount,                         // 숫자 타입
          buyer_email: "gildong@gmail.com",
          buyer_name: '홍길동',
          buyer_tel: "010-4242-4242",
        }, function (rsp) { // callback
             console.log(rsp.imp_uid);
             if (rsp.success) {   
                console.log(loginUserId)
                 axios.get((`./purchase/success/${loginUserId}`))
                    .then((response) => {
                       console.log(response);
                       alert('결제가 성공적으로 완료되었습니다');
                       })
                    .catch((error) => {
                        console.log(error); 
                    })
                //axios.post('/api/payment/complete', {
                //    imp_uid: rsp.imp_uid,
                 //   merchant_uid: rsp.merchant_uid,
                 //   amount: rsp.paid_amount,
                 //   buyer_email: rsp.buyer_email
                //}).then(function (response) {
                 //   console.log('결제 완료 처리:', response.data);
           
                //}).catch(function (error) {
                //    console.error('결제 완료 처리 오류:', error);
                //    // 오류 처리 코드 작성
                //});
           }      
        });
    }
    
});