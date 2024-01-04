    //URL 이동
    function forwardURL(url){
        var answer = "";
        switch(url){
            case 'findId':
                answer = "/find.do?type=I";
                break;
            case 'findPw':
                answer = "/find.do?type=P";
                break;
            case 'join':
                answer = "/join.do";
                break;
            case 'login' || '/':
                answer = "/";
                break;
        }
        return window.location.href = answer;
    }

    //로그인 화면 => 로그인 처리
    function login(){
        let inputId = document.getElementById('mId').value;
        let inputPw = document.getElementById('mPw').value;
        let chk = document.getElementById('saveId').checked;

        ajaxData = {mId:inputId, mPw:inputPw}

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
                    }else{
                        $.alertError(rslt.resultMsg);
                    }
                },
                error : function(request, status, error){
                    $.alertError("javaScript error : "+ error + "request :" + request + "status : " + status);
                }
            });
        }
    }

	//아이디찾기&비밀번호찾기&회원가입 => 이메일 보내기
	function sendMail(emailId, mtype){
		let email = document.getElementById(emailId).value;
		if(email === ""){ $.alertError("이메일을 입력해주세요"); return;}

        ajaxData = {mEmail:email, type:mtype}

		$.ajax({
			type : "POST",
			url : "/sendMailAuth.json",
			dataType:'json',
			contentType : "application/json",
			data : JSON.stringify(ajaxData),
			success : function(rslt){
				if(rslt.resultCd == 1){
                    if(mtype == 'J'){
                        document.getElementById("mEmailChk").style.display = '';
                        document.getElementById("confirmAuthBtn").style.display = '';
                        document.getElementById(emailId).readOnly = true;
                    }
					$.alertSuccess(rslt.resultMsg);
				} else {
					$.alertError(rslt.resultMsg);
				}
			}
		});
	}

    //아이디찾기&비밀번호찾기&회원가입 => 인증번호 확인
	function confirmMail(emailId, mtype){
		let email = document.getElementById(emailId).value;
		let emailChk = document.getElementById("mEmailChk").value;
        let emailChk_info = document.getElementById("emailChk_info");
		if(emailChk === ""){ $.alertError("인증번호를 입력해주세요"); return;}

        ajaxData = {mEmail:email, inputAuth:emailChk, type:mtype}

		$.ajax({
			type : "POST",
			url : "/confirmMailAuth.json",
			dataType:'json',
			contentType : "application/json",
			data : JSON.stringify(ajaxData),
			success : function(rslt){
				if(rslt.resultCd == 1){
                    if(mtype == 'I'){
                        document.getElementById("form").style.display = 'none';
                        document.getElementById("result_div").style.display = 'block';
                        document.getElementById("result").innerText = rslt.resultData;
                        document.getElementById("result").className = 'result_tag';
                    } else if (mtype == 'P'){
                        document.getElementById("form").style.display = 'none';
                        document.getElementById("inputPw").style.display = 'block';
                    } else if (mtype == 'J'){
                        emailChk_info.dataset.dupl = 'Y';                       //이메일 인증 Y로 변경
                        emailChk_info.style.display = 'none';                   //이메일 인증 문구 삭제
                        document.getElementById("mEmailChk").readOnly = true;   //이메일 인증 readonly 처리
                        document.getElementById("sendBtn").disabled = true;     //인증번호전송 버튼 diabled 처리
                        document.getElementById("confirmAuthBtn").disabled = true;  //인증확인 버튼 disabled 처리
                    }
                    $.alertSuccess(rslt.resultMsg);
				}else{
					$.alertError(rslt.resultMsg);
				}
			}
		});
	}

    //비밀번호_찾기 => 비밀번호 변경
    function modifyPw(){
        let email = document.getElementById("mEmail").value;
        let pw = document.getElementById("mPw").value;
        let pwChk = document.getElementById("mPwChk").value;
        if(pw == '' || pwChk == '' || pw != pwChk){
            $.alertError("비밀번호를 확인해주세요"); return;
        }
        ajaxData = {mPw:pw, mPwChk:pwChk, mEmail:email}
        $.ajax({
            type : "POST",
            url : "/pwModify.json",
            dataType:'json',
            contentType : "application/json",
            data : JSON.stringify(ajaxData),
            success : function(rslt){
                if(rslt.resultCd == 1){
                    $.alertSuccessRtn(rslt.resultMsg, rslt.resultUrl);
                } else {
                    $.alertError(rslt.resultMsg);
                }
            }
        });
    } 

    //회원가입 => 아이디, 닉네임 중복검사
    function duplicate(id, idName, type){
        var chkVal = document.getElementById(id).value;
        var idChk_info = document.getElementById(idChk_info);
        var nickChk_info = document.getElementById(nickChk_info);

        if(chkVal == ""){ $.alertError(idName + "을(를) 입력해주세요!! •̀ㅅ•́"); return;}
        
        if(type == 'id') ajaxData = {mId: chkVal, type:type}
        else if(type == 'nickname') ajaxData = {mNickname: chkVal, type:type}

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

    //회원가입 => 중복체크 후, 값 변경되면 다시 중복체크
    function inputChk(type){
        if(type == 'idDupl'){
            $("#idChk_info").show();
            $("#idChk_info").data("dupl", "N");
        } else if(type == 'nick'){
            $("#nickChk_info").show();
            $("#nickChk_info").data("dupl", "N");
        };
    }

    //회원가입 절차
    function joinJson(){
        // if(fn_dataChk(formData)){
            var inputName = document.getElementById("mName").value;
            var inputId = document.getElementById("mId").value;
            var inputPw = document.getElementById("mPw").value;
            var inputNickname = document.getElementById("mNickname").value;
            var inputEmail = document.getElementById("mEmail").value;
            var inputBirth = document.getElementById("mBirth").value;
            var inputBirthYn =  document.querySelector('input[name="mBirthYn"]:checked').value;
            var inputPwChk = document.getElementById("mPwChk").value;

            var inputJoinCompDt = document.getElementById("mJoinCompDt").value;
            var inputVactCnt = document.getElementById("mVactCnt").value;

            $.ajax({
                type : 'POST',
                contentType : "application/json",
                url : '/join.json',
                data : JSON.stringify({
                    mName : inputName,
                    mId : inputId,
                    mPw : inputPw,
                    mNickname : inputNickname,
                    mEmail : inputEmail,
                    mBirth : inputBirth,
                    mBirthYn : inputBirthYn,
                    mPwChk : inputPwChk,
                    mJoinCompDt : inputJoinCompDt,
                    mVactCnt : inputVactCnt
                }),
                dataType : 'json',
                success : function(rslt){
                    if(rslt.resultCd==='1'){
                        $.alertSuccessRtn(rslt.resultMsg, rslt.resultUrl);
                    } else {
                        //실패일시
                        $.alertError(rslt.resultMsg);
                    }
                },
                error : function(request, status, error){
                    $.alertError("javaScript error : "+ error + "request :" + request + "status : " + status);
                }
            });
        // }
    }