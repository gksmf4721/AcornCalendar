const body  = document.querySelector('.container_wrap');        //body
const startDate = document.getElementById('cal_startDate');     //info.event.startStr -시작날짜
const endDate = document.getElementById('cal_endDate');         //info.event.endStr - 마감날짜
const title = document.getElementById('cal_title');             //info.title - 제목
const startTime = document.getElementById('cal_startTime');     //info.event.start - 시작시간
const endTime = document.getElementById('cal_endTime');         //info.event.end - 마감시간
const content = document.getElementById('divTextarea');			//내용
const alldayCheck = document.getElementById('cal_allDay');

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

function save(){
    var a = startDate.value;
    var b = endDate.value;
    var c = title.value;
    var d = startTime.value;
    var e = endTime.value;
    var f = alldayCheck.checked == true ? "Y" : "N";
}