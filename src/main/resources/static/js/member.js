    //URL 이동
    function forwardURL(url){
        if(url === 'kakao'){
            $.alertError("준비중입니다!");
        } else if(url === 'findId'){
            window.location.href = "/find.do?type=I";
        } else if(url === 'findPw'){
            window.location.href = "/find.do?type=P";
        } else if(url === 'join'){
            window.location.href = "/join.do";
        }
    }

    //로그인 화면 => 로그인 처리
    function login(){
        let inputId = document.getElementById('mId').value;
        let inputPw = document.getElementById('mPw').value;
        let chk = document.getElementById('saveId').checked;
        
        ajaxData = {
            mId: inputId,
            mPw: inputPw
        };

        if(fn_dataChk(formData)){
            $.ajax({
                type : "POST",
                contentType : "application/json",
                url : '/loginCheck.json',
                data : JSON.stringify(ajaxData),
                dataType : 'json',
                success : function(rslt){
                    if(rslt.resultCd == 1){
                        if(chk){
                            setCookie('id', inputId, 7);
                        }else{
                            setCookie('id',0,0);
                        }
                        sessionStorage.setItem("id",inputId);
                        location.href = rslt.resultUrl;
                    }
                },
                error : function(request, status, error){
                    $.alertError("javaScript error : "+ error + "request :" + request + "status : " + status);
                }
            });
        }
    }

	//아이디찾기 => 이메일 보내기
	function sendMail(mtype){
		let email = document.getElementById("mEmail").value;

		ajaxData = {
			mEmail: email,
			type: mtype
		};

		if(email == ''){
			$.alertError("이메일을 입력해주세요"); return false;
		}

		$.ajax({
			type : "POST",
			url : "/sendMailAuth.json",
			dataType:'json',
			contentType : "application/json",
			data : JSON.stringify({ajaxData}),
			success : function(rslt){
				console.log(rslt);
				if(rslt.resultCd == 1){
					$.alertSuccess(rslt.resultMsg);
				} else {
					$.alertError(rslt.resultMsg);
				}
			}
		});
	}

    //아이디 찾기 => 인증번호 확인
	function confirmMail(mtype){
		let email = document.getElementById("mEmail").value;
		let emailChk = document.getElementById("mEmailChk").value;

		ajaxData = {
			mEmail : email,
			inputAuth : emailChk,
			type : mtype
		};

		if(emailChk = ''){
			$.alertError("인증번호를 입력해주세요"); return false;
		}

		$.ajax({
			type : "POST",
			url : "/confirmMailAuth.json",
			dataType:'json',
			contentType : "application/json",
			data : JSON.stringify({ajaxData}),
			success : function(rslt){
				console.log(rslt);
				if(rslt.resultCd == 1){
                    if(mtype == 'I'){
                        document.getElementById("form").style.display = 'none';
                        document.getElementById("result_div").style.display = 'block';
                        document.getElementById("result").innerText = rslt.resultData;
                    } else if (mtype == 'P'){
                        document.getElementById("form").style.display = 'none';
                        document.getElementById("inputPw").style.display = 'block';
                    }
				}else{
					$.alertError(rslt.resultMsg);
				}
			}
		});
	}

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