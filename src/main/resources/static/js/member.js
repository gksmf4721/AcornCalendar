//아이디, 닉네임 중복검사
function duplicate(id, idName){
    var chkVal = document.getElementById(id).value;
    if(chkVal == ""){
        $.alertError(idName + "을(를) 입력해주세요!! •̀ㅅ•́");
        return false;
    }
    ajaxData = {type: idName, id: chkVal};

    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : '/inputCheck.json',
        data : JSON.stringify(ajaxData),
        dataType : 'json',
        success : function(rslt){
            if(rslt.resultCd == 1){
                if(rslt.type == 'id'){
                    $.alertSuccess("아이디 중복 검사 완료");
                    $("#idChk_info").hide();
                } else if(rslt.type == 'nickname'){
                    $.alertSuccess("닉네임 중복 검사 완료");
                    $("#nickChk_info").hide();
                }
            } else {
                $.alertError("이미 사용하고 있어요. ʅ（◞‿◟）ʃ");
            }
        },
        error : function(request, status, error){
            $.alertError("javaScript error : "+ error + "request :" + request + "status : " + status);
        }
    });
}

window.onload = function(){
    const btn = document.getElementById("btn-ajax");
    btn.addEventListener('click', function(){
        joinJson();
    });
}


function joinJson(){
    if(fn_dataChk(formData)){
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
                    $.alertSuccess(rslt.resultMsg);
                    location.href = rslt.resultUrl;
                } else {
                    //실패일시
                    $.alertError(rslt.resultMsg);
                }
            },
            error : function(request, status, error){
                $.alertError("javaScript error : "+ error + "request :" + request + "status : " + status);
            }
        });
    }
}