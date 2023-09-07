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
                    $("#idChk_info").data("dupl", "Y");
                    $("#idChk_info").hide();
                } else if(rslt.type == 'nickname'){
                    $.alertSuccess("닉네임 중복 검사 완료");
                    $("#nickChk_info").data("dupl", "Y");
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

//중복체크를 하고 나서, 값을 변경했을 때 다시 중복체크 하기 위한 메소드
function inputChk(type){
    if(type == 'idDupl'){
        $("#idChk_info").show();
        $("#idChk_info").data("dupl", "N");
    } else if(type == 'nick'){
        $("#nickChk_info").show();
        $("#nickChk_info").data("dupl", "N");
    };
}

//이메일 보내기
function sendMail(){
    let email = $("#mEmail").val();

    $.ajax({
        type : "POST",
        url : "/sendMailAuth.json",
        dataType:'json',
        contentType : "application/json",
        data : JSON.stringify({
            mEmail : email,
            type : 'J'
        }),
        success : function(obj){
            //정상적으로 이메일이 발송되었을때에만, 이메일 인증번호 입력 창 생성
            if(obj.resultCd == 1){
                $.alertSuccess(obj.resultMsg);
                $('#mEmailChk').show();
                $('#confirmAuthBtn').show();
            }else {
                $.alertError(obj.resultMsg);
            }
        }
    });
}

//이메일 인증번호 확인
function confirmMail(){
    let emailChk = $("#mEmailChk").val();
    let email = $("#mEmail").val();

    $.ajax({
        type : "POST",
        url : "/confirmMailAuth.json",
        dataType:'json',
        contentType : "application/json",
        data : JSON.stringify({
            mEmail : email,
            inputAuth : emailChk,
            type : 'J'
        }),
        success : function(obj){
            //정상적으로 이메일이 발송되었을때에만, 이메일 인증번호 입력 창 생성
            if(obj.resultCd == 1){
                $.alertSuccess(obj.resultMsg);
                $("#emailChk_info").data("dupl", "Y");
                $("#emailChk_info").hide();
                $("#mEmail").prop('readonly', true);
            }else {
                $.alertError(obj.resultMsg);
            }
        }
    });
}

//회원가입 절차
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