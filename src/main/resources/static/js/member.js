window.onload = function() {
    var httpRequest;
    /* button이 클릭되었을때 이벤트 */
    document.getElementById("submit").addEventListener('click', () => {
    
    alert('abcssaaa');
        /* textBox에 작성된 name 데이터를 가져옴 */
        var inputName = document.getElementById("mName").value;
        var inputId = document.getElementById("mId").value;
        var inputPw = document.getElementById("mPw").value;
        var inputNickname = document.getElementById("mNickname").value;
        var inputEmail = document.getElementById("mEmail").value;
        var inputBirth = document.getElementById("mBirth").value;
        var inputBirthYn = document.getElementById("mBirthYn").value;

        data = JSON.stringify({
            mName : inputName,
            mId : inputId,
            mPw : inputPw,
            mNickname : inputNickname,
            mEmail : inputEmail,
            mBirth : inputBirth,
            mBirthYn : inputBirthYn
        });
        
        console.log(data);


        /* 통신에 사용 될 XMLHttpRequest 객체 정의 */
       var httpRequest = new XMLHttpRequest();
		
        httpRequest.open('POST', '/join.json');
        /* Response Type을 Json으로 사전 정의 */
        httpRequest.responseType = "json";
        
        httpRequest.setRequestHeader('Content-Type', 'application/json');
        
        /* 정의된 서버에 요청을 전송 */
        httpRequest.send(data);

        /* httpRequest의 readyState가 변화했을때 함수 실행 */
        httpRequest.onreadystatechange = () => {
            /* readyState가 Done이고 응답 값이 200일 때, 받아온 response로 name과 age를 그려줌 */
            if (httpRequest.readyState === XMLHttpRequest.DONE) {
                  if (httpRequest.status === 200) {
                    var result = httpRequest.response;
                    alert(result.resultMsg);
					
                  } else {
                    alert('Request Error!');
                    
                  }
            }
        };

    });
}
