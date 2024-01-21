const body  = document.querySelector('.container_wrap');        //body
const title = document.getElementById('cal_title');             //info.title - 제목
const alldayCheck = document.getElementById('cal_allDay');      //종일여부 체크
const startDate = document.getElementById('cal_startDate');     //info.event.startStr -시작날짜
const endDate = document.getElementById('cal_endDate');         //info.event.endStr - 마감날짜
const startTime = document.getElementById('cal_startTime');     //info.event.start - 시작시간
const endTime = document.getElementById('cal_endTime');         //info.event.end - 마감시간
const content = document.getElementById("P_contCont");          //내용
const cateTypeId = document.getElementById("cal_category");     //카테고리
const contSeq = document.getElementById("cont_seq");            //내용 시퀀스

/* ****************************************
 *  Modal function :: modalSlide
 *  날짜.이벤트 클릭 시 우측 모달창 열림
 *  body에 modalAct 클래스 추가하여 활성화
 *  날짜 클릭 시, 값을 없앰
 *  이벤트 클릭 시, 기존값 매칭하여 적용
 * ************************************** */
function modalSlide(info, type, openYn){
    //모달 열림
    if(openYn == "Y"){
        //새로 등록인 경우
        if(type == "N"){
            let t_hour = new Date();
            t_hour.setHours(t_hour.getHours() + 1);

            startDate.value = info.dateStr;
            endDate.value = info.dateStr;
            startTime.value = hourMinFormat(new Date());
            endTime.value = hourMinFormat(t_hour);
            contSeq.value = "";

            document.getElementById("cAddBtn1").style.display = "";
            document.getElementById("cAddBtn2").style.display = "none";
            document.getElementById("cAddBtn3").style.display = "none";
        //수정인 경우
        } else if(type == "E"){
            console.log(info.event.allDay);
            title.value = info.event.title;
            startDate.value = dateFormat(nvl(info.event.start));
            endDate.value = dateFormat(nvl(info.event.extendedProps.contEndDt));
            startTime.value = hourMinFormat(nvl(info.event.start));
            endTime.value = hourMinFormat(nvl(info.event.end));
            alldayCheck.checked = info.event.allDay;
            content.innerHTML = info.event.extendedProps.contCont;
            cateTypeId.value = info.event.extendedProps.calDetailType;
            contSeq.value = info.event.extendedProps.contSeq;

            document.getElementById("cAddBtn1").style.display = "none";
            document.getElementById("cAddBtn2").style.display = "";
            document.getElementById("cAddBtn3").style.display = "";
            document.getElementById("cAddBtn3").dataset.delt = info.event.extendedProps.contSeq;
            allDayCheck();
            categoryChange();
        }
        body.classList.add('modalAct');
    } else {
        body.classList.remove('modalAct');
        setTimeout(function(){
            //모달창 닫을때, 값 리셋시키기 => 모달창이 slide되기 때문에, 바로 없어지는게 보이지 않게 하기 위해 setTimeout사용함
            valueReset("modalClose");
        }, 500);
    }
}
function timeChange(){
    let opt_val = cateTypeId.value;

    if(opt_val == 'S2'){
        endDate.value = startDate.value
    }
    categoryChange();
}
function categoryChange(){
    let opt_val = cateTypeId.value;
    console.log(opt_val)
    if(opt_val == 'S1' || opt_val == 'S2' || alldayCheck.checked == true){
        if(opt_val == 'S1' || opt_val == 'S2'){
            alldayCheck.onclick = function(){
                return false;
            }
            if(opt_val == 'S2'){
                endDate.value = startDate.value;
                endDate.setAttribute("min", startDate.value);
                endDate.setAttribute("max", startDate.value);
            }
           
        }else{
            alldayCheck.onclick = function(){
                return true;
            }
            allDayCheck("Y");
            endDate.setAttribute("min", "");
            endDate.setAttribute("max", "");
        }
        alldayCheck.checked = true;
    }else{
        alldayCheck.onclick = function(){
            return true;
        }
        alldayCheck.checked = false;
        endDate.setAttribute("min", "");
        endDate.setAttribute("max", "");
    }
    allDayCheck();
}

/* ****************************************
 * 값 리셋시키기
 * ************************************** */
function valueReset(type){
    //모달창 닫을때 값 리셋
	if(type == "modalClose"){
		title.value = ""
	    startDate.value = ""
	    endDate.value = ""
        alldayCheck.checked = false;
        content.innerHTML = "";
        cateTypeId.value = "";
        startTime.disabled = false;
        endTime.disabled = false;
        document.getElementById("cAddBtn3").dataset.delt = "";  //삭제할때 넣을 일정 시퀀스 값
	}
    //종일체크버튼 클릭 시, 시간만 리셋되도록 만들기
    startTime.value = ""
    endTime.value = ""
}

/* ****************************************
 *  일정 등록하기
 * ************************************** */
function regiEvent(type){
    //필수입력 검사 => 나중에 if문 안에 value값 체크하는것 집어넣기
    if(fn_dataChk(formData)){
        let inputcalSeq = document.getElementById("P_CalSeq").value;            //캘린더 시퀀스
        let inputmSeq = document.getElementById("P_mSeq").value;                //일정 등록 회원 시퀀스
        let inputcontCont = document.getElementById("P_contCont").innerHTML;    //일정 내용
        let inputcontTitle = title.value;                                       //제목
        let inputcontStartDt = startDate.value;                                 //시작 날짜
        let inputcontEndDt = endDate.value;                                     //마감 날짜
        let inputcontStartTm = startTime.value;                                 //일정 시작 시각
        let inputcontEndTm = endTime.value;                                     //일정 마감 시각
        let inputcalDetailType = document.getElementById("cal_category").value; //카테고리 종류
        let inputcontAlldayYn = alldayCheck.checked == true ? "Y" : "N";        //종일 여부
        let inputcontSeq = contSeq.value;
        let w_vact_cnt =  document.getElementById('w_vact_cnt').innerText;

        if(inputcalDetailType === 'S1'){

            let startDate = new Date(inputcontStartDt);
            let endDate = new Date(inputcontEndDt);

            var timeDiff = Math.abs(endDate.getTime() - startDate.getTime());
            var dayDiff = Math.ceil(timeDiff / (1000 * 3600 * 24)) + 1;

            if(w_vact_cnt < dayDiff){
                $.alertError('휴가 잔여일수가 없습니다.');
                return;
            }
        } 
        if(inputcalDetailType === 'S2' && w_vact_cnt - 0.5 < 0){
            $.alertError('휴가 잔여일수가 없습니다.');
            return;
        }
    
        //종일버튼 체크 시, 시작시각&종료시각 null로 넣기
        if(inputcontAlldayYn == "Y"){
            inputcontStartTm = '00:01:00.0';
            inputcontEndTm = '00:01:00.0';
        }
    
        ajaxData = {
            calSeq : inputcalSeq,
            mSeq : inputmSeq,
            contCont : inputcontCont,
            contTitle : inputcontTitle,
            contStartDt : inputcontStartDt,
            contEndDt : inputcontEndDt,
            contStartTm : inputcontStartTm,
            contEndTm : inputcontEndTm,
            calDetailType : inputcalDetailType,
            contAlldayYn : inputcontAlldayYn,
            contSeq : inputcontSeq
        }

        console.log("캘린더 저장할 때 넘기는 값::");
        console.log(JSON.stringify(ajaxData));

        let ajaxUrl = "";
        let text = "";
        if(type == 'N'){
            ajaxUrl = "/cont.json";
            text = "저장되었습니다!"
        } else {
            //수정할 때 넘길 url만 추가하면 됨
            ajaxUrl = "/contUpdate.json";
            text = "수정되었습니다!";
        }   
        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : ajaxUrl,
            data : JSON.stringify(ajaxData),
            dataType : 'json',
            success : function(rslt){
                document.getElementById('w_vact_cnt').innerText = rslt.data;
                document.getElementById('w_vact_cnt').value = rslt.data;
                $.alertSuccess(text);
                calendarRender(inputcontStartDt);
                modalSlide('', '', 'N');
            },
            error : function(request, status, error){
                $.alertError("javaScript error : "+ error + "request :" + request + "status : " + status);
            }
        });
    }
    

    
}

function deltEvent(){
    let ajaxUrl = "/contDelete.json";
    let inputContSeq = document.getElementById("cAddBtn3").dataset.delt;
    let inputcontStartDt = startDate.value;                                 //시작 날짜
    let text = "삭제되었습니다!";

    ajaxData = {contSeq : inputContSeq}
    console.log(JSON.stringify(ajaxData));

    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : ajaxUrl,
        data : JSON.stringify(ajaxData),
        dataType : 'json',
        success : function(rslt){
            document.getElementById('w_vact_cnt').innerText = rslt.data;
            $.alertSuccess(text);
            calendarRender(inputcontStartDt);
            modalSlide('', '', 'N');
        },
        error : function(request, status, error){
            $.alertError("javaScript error : "+ error + "request :" + request + "status : " + status);
        }
    });
}

/* ****************************************
 *  JSON으로 일정 넘기기
 * ************************************** */
/*
[{
"contCont" 	: "일정내용"					//일정 내용(CONT_CONT) => info.event.extendedProps.content로 가져오기
"title" 	: "내생일"					    //*제목(CONT_TITLE)
"start" 	: "2024-01-09" + "T09:00"	  //*시작날짜(CONT_START_DT) + "T" + 일정시작시각(CONT_START_TM)
"end"   	: "2024-01-10" + "T09:00"	//*마감날짜(CONT_END_DT) + "T" + 일정마감시각(CONT_END_TM)
"allDay"	: true or false				//종일여부(CONT_ALLDAY_YN)
										//Y일 경우 :: true로 보내주세여
										//N일 경우 :: false로 보내주세여
										//false일 경우 :: 시간이 정의되어 있지 않을 때 자동으로 오전12시가 됨
"calDetailType" : "S2"					//카테고리 상세 종류(CAL_DETAIL_TYPE)
}]
*/



