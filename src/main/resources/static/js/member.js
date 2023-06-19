window.onload = function(){
    const idChk = document.getElementById("idChk");
    const nickChk = document.getElementById("nickChk");
    const emailChk = document.getElementById("emailChk");
    const btn = document.getElementById("btn-ajax");

    idChk.addEventListener('click', function() {
        duplicateChk(idChk);
    });
    
    nickChk.addEventListener('click', function() {
        duplicateChk(nickChk);
    });
    
    emailChk.addEventListener('click', function() {
        duplicateChk(emailChk);
    });

    btn.addEventListener('click', function(){
        joinJson();
    });

}


function duplicateChk(chkId){
    var sibling1 = chkId.previousElementSibling;
    var sibling2 = chkId.nextElementSibling;

    if(sibling1.value == ''){
        if(chkId == emailChk){
            sibling2.innerText = "이메일 중복 체크를 해주세요";
        } else if(chkId == nickChk){
            sibling2.innerText = "닉네임 중복 체크를 해주세요";
        } else if(chkId == idChk){
            sibling2.innerText = "아이디 중복 체크를 해주세요";
        }
        sibling2.className = "chk_befo";
        return false;

    }else{
        if(chkId == nickChk){
            ajaxData = {type:'nickname',mNickname:sibling1.value}
        }else if(chkId == emailChk){
            ajaxData = {type:'email',mEmail:sibling1.value}
        }else if(chkId == idChk){
            ajaxData = {type:'id',mId:sibling1.value}
        }
    }

    $.ajax({
        type : 'POST',
        contentType : "application/json",
        url : '/inputCheck.json',
        data : JSON.stringify(ajaxData),
        dataType : 'json',
        success : function(rslt){
            if(rslt.resultCd==='1'){
                if(rslt.resultType == "email"){
                    sibling2.innerText = "이메일 중복 검사 완료"
                } else if(rslt.resultType == "nickname"){
                    sibling2.innerText = "닉네임 중복 검사 완료"
                } else if(rslt.resultType == "id"){
                    sibling2.innerText = "아이디 중복 검사 완료"
                }
                sibling2.className = "chk_after"
            } else {
                if(rslt.resultType == "email"){
                    sibling2.innerText = "이메일 중복 체크를 해주세요"
                } else if(rslt.resultType == "nickname"){
                    sibling2.innerText = "닉네임 중복 체크를 해주세요"
                } else if(rslt.resultType == "id"){
                    sibling2.innerText = "아이디 중복 체크를 해주세요"
                }
                sibling2.className = "chk_befo"
            }
        },
        error : function(request, status, error){
            alert("javaScript error : "+ error + "request :" + request + "status : " + status);
        }
    });
}


function joinJson(){
    var dataChk = fn_dataChk(formData);
    if(dataChk == true){
        var inputName = document.getElementById("mName").value;
        var inputId = document.getElementById("mId").value;
        var inputPw = document.getElementById("mPw").value;
        var inputNickname = document.getElementById("mNickname").value;
        var inputEmail = document.getElementById("mEmail").value;
        var inputBirth = document.getElementById("mBirth").value;
        var inputBirthYn =  document.querySelector('input[name="mBirthYn"]:checked').value;
        var inputPwChk = document.getElementById("mPwChk").value;
    
        ajaxData = {
            mName : inputName,
            mId : inputId,
            mPw : inputPw,
            mNickname : inputNickname,
            mEmail : inputEmail,
            mBirth : inputBirth,
            mBirthYn : inputBirthYn,
            mPwChk : inputPwChk
        };

        $.ajax({
            type : 'POST',
            contentType : "application/json",
            url : '/join.json',
            data : JSON.stringify(ajaxData),
            dataType : 'json',
            success : function(rslt){
                if(rslt.resultCd==='1'){
                    //성공일시
                    alert(rslt.resultMsg);
                    location.href = rslt.resultUrl;
                } else {
                    //실패일시
                    alert(rslt.resultMsg);
                }
            },
            error : function(request, status, error){
                alert("javaScript error : "+ error + "request :" + request + "status : " + status);
            }
        });
    }
}