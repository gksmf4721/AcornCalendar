//캘린더 헤더 옵션
const headerToolbar = {
    start: 'today,prevYear,nextYear',                    //왼쪽   : prevYear(작년),prev(전월),next(다음월),nextYear(내년)
    center: 'prev,title,next',                           //가운데 : 타이틀(YYYY년 MM월)
    end: 'dayGridMonth,dayGridWeek,timeGridDay'          //오른쪽 : dayGridMonth(월),week(주),day(일)
};

//캘린더 생성 옵션
const calendarOption = {
    height: '750px',                //높이 설정
    initialView : 'dayGridMonth',   //초기 로드 될때 보이는 캘린더 화면 (기본 설정 : 달)
    headerToolbar : headerToolbar,  //헤더 툴바 설정
    locale: "ko",                   //한글로 변경
    navLinks: true,                 //[월]과 [주]에서 해당 요일 클릭 시, [일]로 볼 수 있도록 이동함
    editable: true,                 //일정을 들어서 옮기는 것이 가능
    selectable : true,              //드래그하여 날짜범위 선택 가능
    eventLimit : true,              //달력에 셀보다 많은 이벤트가 등록된 경우, 'more'로 표기함
    weekends : true,                //초기에 주말 보이기
    events: [ 
                {
                    title: 'All Day Event',
                    start: '2023-11-05',
                    end : '2023-11-08'
                },
            ],
    //날짜 클릭 시, 이벤트 추가 
    dateClick : function(){
        //모달 열기
        toggleModal();
    }
}




//이벤트 추가
toggleModal = function(){
    let modal = document.getElementById('modalContainer');

    if (modal !== null) {
        if (modal.classList.contains('hidden')) {
            modal.classList.remove('hidden');
            document.body.classList.add('active');
        } else {
            modal.classList.add('hidden');
        }
    } else {
        console.error('Element with id "modalContainer" not found');
    }
};

document.addEventListener('DOMContentLoaded', function(){
    var calendarEl = document.getElementById('calendar'); // calendar 요소 가져오기
    var calendar = new FullCalendar.Calendar(calendarEl, calendarOption);   //옵션 넣은 calendar 요소 가져오기
    var modal = document.getElementById('modalContainer');

    calendar.render();      //calendar 렌더링
    
    if(modal){      //모달창이 열려있을 경우, 모달창 제외를 클릭한 경우, 모달창 삭제
        modal.addEventListener('click', function(){
            toggleModal();
        });
    }
});