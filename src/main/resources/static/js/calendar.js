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
    showNonCurrentDates : true,                         //전달까지 보이기
    fixedWeekCount : false,
    //날짜 클릭 시, 이벤트 추가 
    dateClick : function(info){
        //모달 열기 :: 신규
        modalSlide(info, "N", "Y");
    },
    eventClick : function(info){
        //모달 열기 :: 기존
        modalSlide(info, "E", "Y");
    },
    //기존 이벤트 가져오기
    events: function (info, successCallback, failureCallback) {
        $.ajax({
            type : "get",
            url : "/json/calendarEvent.json",
            success : function(response){
                successCallback(response);
            },
            error : function(err){
                failureCallback(err);
            }
        });
    },
}

//캘린더 생성 작은 버전
const s_calendarOption = {
    height: '300px',                                    //높이 설정
    initialView : 'dayGridMonth',                       //초기 로드 될때 보이는 캘린더 화면 (기본 설정 : 달)
    locale: "ko",                                       //한글로 변경
    dayMaxEvents: true,                                 //이벤트가 많이 추가되면 높이 제한
}

document.addEventListener('DOMContentLoaded', function(){
    var calendarEl = document.getElementById('calendar'); // calendar 요소 가져오기
    var s_calendarEl = document.getElementById('smallCalendar'); // calendar 요소 가져오기
    var calendar = new FullCalendar.Calendar(calendarEl, calendarOption);   //옵션 넣은 calendar 요소 가져오기
    var s_calendar = new FullCalendar.Calendar(s_calendarEl, s_calendarOption);   //옵션 넣은 calendar 요소 가져오기
    calendar.render();      //calendar 렌더링
    s_calendar.render();      //작은 calendar 렌더링
    
    var $div = document.createElement("div");                   //$div 하나 생성
    var body  = document.querySelector('.container_wrap');          
    $div.setAttribute('id', 'overlay');                         //$div id='overlay' 요소 추가
    body.prepend($div);

    $div.addEventListener('click',() => {
        modalSlide('','', 'N');
    });

     // 왼쪽,오른쪽 버튼을 클릭하였을 경우        
    $("button.fc-prev-button, button.fc-next-button, button.fc-today-button, button.fc-prevYear-button, button.fc-nextYear-button").click(function() {
        firstLastDay(calendar);
    });
});

function firstLastDay(calendar){
    var currentDate = calendar.getDate();
    
    var firstDayOfMonth = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1);  //현재월의 1일을 가져와서 Date 타입으로 변경
    var firstDayOfDay = firstDayOfMonth.getDay() - 1;    //현재1일의 요일을 가져오기(일:0 ~ 토:6) 거기서 -1 하기 (시작이 0이기 때문)
    var daysAgo = new Date(firstDayOfMonth);
    daysAgo.setDate(firstDayOfMonth.getDate() - firstDayOfDay);   //현재월의 1일에서 해당하는 요일만큼 빼기 :: 캘린더의 첫번째 일요일 날짜가 나옴
    var formattedDate = daysAgo.toISOString().split('T')[0];    //포맷팅
    console.log('캘린더에서 보이는 진짜 첫번째 날짜 ::' + formattedDate);

    var lastDayOfMonth = new Date(firstDayOfMonth.getFullYear(), firstDayOfMonth.getMonth() + 1, 0);
    var lastDayOfDay = 7 - lastDayOfMonth.getDay();
    var daysAhead = new Date(lastDayOfMonth);
    daysAhead.setDate(lastDayOfMonth.getDate() + lastDayOfDay);
    var formattedLastDayOfMonth = daysAhead.toISOString().split('T')[0];
    console.log("캘린더에서 보이는 진짜 마지막 날짜 :: " + formattedLastDayOfMonth)
}


