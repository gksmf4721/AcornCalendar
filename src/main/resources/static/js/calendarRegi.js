const body  = document.querySelector('.container_wrap');        //body
const title = document.getElementById('cal_title');             //info.title - 제목
const alldayCheck = document.getElementById('cal_allDay');      //종일여부 체크
const startDate = document.getElementById('cal_startDate');     //info.event.startStr -시작날짜
const endDate = document.getElementById('cal_endDate');         //info.event.endStr - 마감날짜
const startTime = document.getElementById('cal_startTime');     //info.event.start - 시작시간
const endTime = document.getElementById('cal_endTime');         //info.event.end - 마감시간
const content = document.getElementById("P_contCont");


/* ****************************************
 *  Modal function :: modalSlide
 *  날짜.이벤트 클릭 시 우측 모달창 열림
 *  body에 modalAct 클래스 추가하여 활성화
 *  날짜 클릭 시, 값을 없앰
 *  이벤트 클릭 시, 기존값 매칭하여 적용
 * ************************************** */
function modalSlide(info, type, openYn){
    // console.log(info.event.extendedProps.contCont);
    // console.log(info.event.allDay);
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
            alldayCheck.checked = info.event.allDay;
            content.innerHTML = info.event.extendedProps.contCont;
        }
        body.classList.add('modalAct');
    } else {
        body.classList.remove('modalAct');
        setTimeout(function(){
            //모달창 닫을때, 값 리셋시키기 => 모달창이 slide되기 때문에, 바로 없어지는게 보이지 않게 하기 위해 settimeout사용함
            valueReset("modalClose");
        }, 500);
    }
}

function valueReset(type){
	if(type == "modalClose"){
		title.value = ""
	    startDate.value = ""
	    endDate.value = ""
	}
    //종일체크버튼 클릭 시, 시간만 리셋되도록 만들기
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



// json 넘기는 방식
// "contCont" 	: "일정내용"					//일정 내용(CONT_CONT) => info.event.extendedProps.content로 가져오기
// "title" 	: "내생일"					//*제목(CONT_TITLE)
// "start" 	: "2024-01-09" + "T09:00"	//*시작날짜(CONT_START_DT) + "T" + 일정시작시각(CONT_START_TM)
// "end"   	: "2024-01-10" + "T09:00"	//*마감날짜(CONT_END_DT) + "T" + 일정마감시각(CONT_END_TM)
// "allDay"	: true or false				//종일여부(CONT_ALLDAY_YN)
// 										//Y일 경우 :: true로 보내주세여
// 										//N일 경우 :: false로 보내주세여
// 										//false일 경우 :: 시간이 정의되어 있지 않을 때 자동으로 오전12시가 됨
// "calDetailType" : "S2"					//카테고리 상세 종류(CAL_DETAIL_TYPE)

// CONT_DEL_YN  		일정 삭제 여부
// CAL_DETAIL_TYPE  	카테고리 상세 종류
// CONT_ALLDAY_YN  	종일여부



