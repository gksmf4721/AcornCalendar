//캘린더 헤더 옵션
const headerToolbar = {
    start: 'today,prevYear,nextYear',                    //왼쪽   : prevYear(작년),prev(전월),next(다음월),nextYear(내년)
    center: 'prev,title,next',                           //가운데 : 타이틀(YYYY년 MM월)
    end: 'dayGridMonth,dayGridWeek,timeGridDay'          //오른쪽 : dayGridMonth(월),week(주),day(일)
};

//캘린더 생성 옵션
const calendarOption = {
    height: '750px',                                    //높이 설정
    initialView : 'dayGridMonth',                       //초기 로드 될때 보이는 캘린더 화면 (기본 설정 : 달)
    headerToolbar : headerToolbar,                      //헤더 툴바 설정
    locale: "ko",                                       //한글로 변경
    navLinks: true,                                     //[월]과 [주]에서 해당 요일 클릭 시, [일]로 볼 수 있도록 이동함
    editable: true,                                     //일정을 들어서 옮기는 것이 가능
    selectable : true,                                  //드래그하여 날짜범위 선택 가능
    eventLimit : true,                                  //달력에 셀보다 많은 이벤트가 등록된 경우, 'more'로 표기함
    weekends : true,                                    //초기에 주말 보이기
    dayMaxEvents: true,                                 //이벤트가 많이 추가되면 높이 제한
    //날짜 클릭 시, 이벤트 추가 
    dateClick : function(info){
        //모달 열기
        parentCheck(info, "date");
    },
    eventClick : function(info){
        parentCheck(info, "event");
    },
    events: [ 
        {
            title: 'All Day Event',
            start: '2023-12-15',
        },
        {
            title: 'Long Event',
            start: '2023-12-07',
            end: '2023-12-10'
        },
        {
            title: 'Repeating Event',
            start: '2023-12-19T16:00:00'
        },
        {
            title: 'Repeating Event',            
            start: '2023-12-26T16:00:00'
        },
        {
            title: 'Conference',            
            start: '2023-12-03',            
            end: '2023-12-06'     
        },
        {
            title: 'Lunch4',
            start: '2023-12-03T12:00:00'
        },
        {
            title: 'Lunch3',
            start: '2023-12-03T13:00:00'
        },
        {
            title: 'Lunch2',
            start: '2023-12-03T14:00:00'
        },
        {
            title: 'Lunch1',
            start: '2023-12-03T15:00:00'
        },
        {
            title: '아콘하는날',
            start: '2023-12-23',
            end:'2023-12-23'
        },
        
    ],
}




//모달오픈 : 날짜 클릭시 열기
//바디 가지고와서, 바디에다가 modalAct 클래스 추가
//modalAct 클래스 추가 시, modal 창 활성화되기
function parentCheck(info, type){
    const calendarClass = document.getElementById("calendar").className;
    
    //메인화면일 경우
    if(calendarClass.indexOf("mainType") > -1){
        return false;
    }

    switch(type){
        case "date" : modalOpen(info, "N"); break;
        case "event": modalOpen(info, "E"); break;
    }
}

function modalOpen(info, type){
    const body  = document.querySelector('.container_wrap');                //body 가져오기
    const startDate = document.getElementById('cal_startDate');             //info.event.startStr
    const endDate = document.getElementById('cal_endDate');                 //info.event.endStr
    const title = document.getElementById('cal_title');                     //info.title
    const startTime = document.getElementById('cal_startTime');             //info.event.start
    const endTime = document.getElementById('cal_endTime');                 //info.event.end

    if(type == "N"){
        startDate.value = info.dateStr;
        endDate.value = info.dateStr;
    } else {
        title.value = info.event.title;
        startDate.value = dateFormat(nvl(info.event.start));
        endDate.value = dateFormat(nvl(info.event.end));
        startTime.value = hourMinFormat(nvl(info.event.start));
        endTime.value = hourMinFormat(nvl(info.event.end));
    }
    body.classList.add('modalAct');
}

function modalClose(){
    const body  = document.querySelector('.container_wrap');                //body 가져오기
    body.classList.remove('modalAct');
    setTimeout(function(){
        valueReset();
    }, 500);
}

function valueReset(){
    const startDate = document.getElementById('cal_startDate');             //info.event.startStr
    const endDate = document.getElementById('cal_endDate');                 //info.event.endStr
    const title = document.getElementById('cal_title');                     //info.title
    const startTime = document.getElementById('cal_startTime');             //info.event.start
    const endTime = document.getElementById('cal_endTime');                 //info.event.end

    title.value = ""
    startDate.value = ""
    endDate.value = ""
    startTime.value = ""
    endTime.value = ""
    
}

document.addEventListener('DOMContentLoaded', function(){
    var calendarEl = document.getElementById('calendar'); // calendar 요소 가져오기
    var calendar = new FullCalendar.Calendar(calendarEl, calendarOption);   //옵션 넣은 calendar 요소 가져오기
    calendar.render();      //calendar 렌더링
    
    var $div = document.createElement("div");                   //$div 하나 생성
    var body  = document.querySelector('.container_wrap');          
    $div.setAttribute('id', 'overlay');                         //$div id='overlay' 요소 추가
    body.prepend($div);

    $div.addEventListener('click',() => {
        modalClose();
    });
});