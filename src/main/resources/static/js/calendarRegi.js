const body  = document.querySelector('.container_wrap');                //body
const startDate = document.getElementById('cal_startDate');             //info.event.startStr
const endDate = document.getElementById('cal_endDate');                 //info.event.endStr
const title = document.getElementById('cal_title');                     //info.title
const startTime = document.getElementById('cal_startTime');             //info.event.start
const endTime = document.getElementById('cal_endTime');                 //info.event.end

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
            valueReset();
        }, 500);
    }
}

function valueReset(){
    title.value = ""
    startDate.value = ""
    endDate.value = ""
    startTime.value = ""
    endTime.value = ""
}

function save(){
    const startDate = document.getElementById('cal_startDate');             //info.event.startStr
    const endDate = document.getElementById('cal_endDate');                 //info.event.endStr
    const title = document.getElementById('cal_title');                     //info.title
    const startTime = document.getElementById('cal_startTime');             //info.event.start
    const endTime = document.getElementById('cal_endTime');                 //info.event.end
}