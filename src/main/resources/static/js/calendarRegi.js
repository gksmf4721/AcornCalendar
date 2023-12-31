const body  = document.querySelector('.container_wrap');        //body
const title = document.getElementById('cal_title');             //info.title - 제목
const alldayCheck = document.getElementById('cal_allDay');      //종일여부 체크
const startDate = document.getElementById('cal_startDate');     //info.event.startStr -시작날짜
const endDate = document.getElementById('cal_endDate');         //info.event.endStr - 마감날짜
const startTime = document.getElementById('cal_startTime');     //info.event.start - 시작시간
const endTime = document.getElementById('cal_endTime');         //info.event.end - 마감시간
const content = document.getElementById('divTextarea');			//내용


/* ****************************************
 *  Modal function :: modalSlide
 *  날짜.이벤트 클릭 시 우측 모달창 열림
 *  body에 modalAct 클래스 추가하여 활성화
 *  날짜 클릭 시, 값을 없앰
 *  이벤트 클릭 시, 기존값 매칭하여 적용
 * ************************************** */
function modalSlide(info, type, openYn){
    if(openYn == "Y"){
        if(type == "N"){
            startDate.value = info.dateStr;
            endDate.value = info.dateStr;
        } else if(type == "E"){
            title.value = info.event.title;
            startDate.value = dateFormat(nvl(info.event.start));
            endDate.value = dateFormat(nvl(info.event.end));
            startTime.value = hourMinFormat(nvl(info.event.start));
            endTime.value = hourMinFormat(nvl(info.event.end));
        }
        body.classList.add('modalAct');
    } else {
        body.classList.remove('modalAct');
        setTimeout(function(){
            valueReset("every");
        }, 500);
    }
}

function valueReset(type){
	if(type == "every"){
		title.value = ""
	    startDate.value = ""
	    endDate.value = ""
	} 
    startTime.value = ""
    endTime.value = ""
}

function regiEvent(){
    //필수입력 검사 => 나중에 if문 안에 전부 집어넣기
    //if(fn_dataChk(formData)){console.log('a')}
    let inputcalSeq = document.getElementById("P_CalSeq").value;        //캘린더 시퀀스
    let inputmSeq = document.getElementById("P_mSeq").value;            //일정 등록 회원 시퀀스
    let inputcontCont = document.getElementById("P_contCont").innerHTML;    //일정 내용
    let inputcontTitle = title.value;                                   //제목
    let inputcontStartDt = startDate.value;                             //시작 날짜
    let inputcontEndDt = endDate.value;                                 //마감 날짜
    let inputcontStartTm = startTime.value;                             //일정 시작 시각
    let inputcontEndTm = endTime.value;                                 //일정 마감 시각
    let cateTypeId = document.getElementById("cal_category");
    let inputcalDetailType = (cateTypeId.options[cateTypeId.selectedIndex].value);    //카테고리 상세 종류
    let inputcontAlldayYn = alldayCheck.checked == true ? "Y" : "N";                    //종일 여부

    if(inputcontAlldayYn == "Y"){
        inputcontStartTm = null;
        inputcontEndTm = null;
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
        contAlldayYn : inputcontAlldayYn
    }
    console.log(JSON.stringify(ajaxData));

    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : '/cont.json',
        data : JSON.stringify(ajaxData),
        dataType : 'json',
        success : function(rslt){
            console.log(rslt);
        },
        error : function(request, status, error){
            $.alertError("javaScript error : "+ error + "request :" + request + "status : " + status);
        }
    });

}