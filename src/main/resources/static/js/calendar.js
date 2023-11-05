document.addEventListener('DOMContentLoaded', function(){
    // calendar Element 취득
    var calendarEl = document.getElementById('calendar'); 
    // full-calendar 생성
    var calendar = new FullCalendar.Calendar(calendarEl, {
        // plugins: ['dayGrid', 'timeGrid', 'list' ],
        initialView : 'dayGridMonth',    //초기 로드 될때 보이는 캘린더 화면 (기본 설정 : 달)
        headerToolbar: {    //달력 헤더 부분
            start: 'dayGridMonth,timeGridWeek,timeGridDay', //왼쪽 : 월별, 주별, 일별
            center: 'title',    //가운데 타이틀 => titleFormat으로 YYYY년 MM월로 변경함
            end: 'prev,next'    //오른쪽 : prevYear(전년), nextYear(내년), prev(바로전), next(바로후)
          },
        titleFormat:function(date){
            return `${date.date.year}년 ${date.date.month + 1}월`;
        },
        navLinks: true, // can click day/week names to navigate views
        editable: true,
        selectable: true,
        dragScroll: true,
        // height: "59%",    //전체길이
        // height: inherit,
        contentHeight: 600,
        aspectRatio: 1.35,
        events: [
            {
              title: 'All Day Event',
              start: '2023-11-05'
            },
        ]
    });
    calendar.render();
});