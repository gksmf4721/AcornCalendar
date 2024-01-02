    //데이터 유효성 검사 
    function fn_dataChk(formId){
        let chk = true;
        const allElements = formId.getElementsByTagName('*');
        for (let i = 0; i < allElements.length; i++) {
            const element = allElements[i];
            var type = element.getAttribute('type');    //유효성검사가 필요한 타입
            var name = element.getAttribute('name');    //유효성검사가 필요한 이름
            var className = element.getAttribute('class');
            var id = element.getAttribute('id');
            var chk_befo = $("."+className).data('dupl');

            if(element.getAttribute('readonly')==false &&element.getAttribute('disabled')==false || element.hasAttribute('data-name')){
                if(type == 'checkbox'){
                    if(element.checked == false){
                        $.alertError(element.dataset.name + '을(를) 체크해주세요');
                        element.focus();
                        chk = false;
                        return false;
                    }
                } else if(type == 'password'){
                    if (element.value =='') {
                        $.alertError(element.dataset.name + '을(를) 입력해주세요');
                        element.focus();
                        chk = false;
                        return false;
                    }
                } else if(type == 'radio'){
                    if($("input:radio[name='" + name +"']:checked").length == 0){
                        $.alertError(element.dataset.name + '을(를) 선택해주세요');
                        element.focus();
                        chk = false;
                        return false;
                    }
                } else if(type == 'text' || type=='date'){
                    if (element.value =='') {
                        $.alertError(element.dataset.name + '을(를) 입력해주세요');
                        element.focus();
                        chk = false;
                        return false;
                    }
                } else if(element == 'select'){ //select박스일경우
                    if(element.value == ''){
                        $.alertError(element.dataset.name + '을(를) 선택해주세요');
                        element.focus();
                        chk = false;
                        return false;
                    }
                } else {
                    if(element.value == ''){
                        element.focus();
                        $.alertError(element.dataset.name + '을(를) 입력해주세요');
                        chk = false;
                        return false;
                    }
                }
            } else if(chk_befo == 'N'){
                $.alertError("중복 체크를 해주세요");
                chk = false;
                return false;
            }
        }
        return chk;
    }

    //실패시 뜨는 alert
    $.alertError = function(text){
        swal({
            text : text,
            icon : "error",
            button : "닫기",
            closeOnClickOutside : false,
        });
    };

    //성공시 뜨는 alert
    $.alertSuccess = function(text){
        swal({
            text : text,
            icon : "success",
            button : "닫기",
            closeOnClickOutside : false,
        });
    }

    //성공시 alert뜨고 난 후, return 받은 url로 보내는 alert
    $.alertSuccessRtn = function(text, url){
        swal({
            text : text,
            icon : "success",
            button : "닫기",
            closeOnClickOutside : false,
        }).then(result =>{
            window.location.href = url;
        });
    }

    //확인용 alert\
    $.confirm = function( title, text){
        return swal({
            title : title,
            text : text,
            icon: 'warning',
            buttons: true,
            buttons: {
                cancel : {
                    text : "취소",
                    value : false,
                    visible : true,
                    className : "btn-cancle",
                },
                confirm: {
                    text : "확인",
                    value : true,
                    visible : true,
                    className : "btn-success"
                },
            },
        });
    }


    //날짜포맷변경함수 Date => YYYY-MM-DD로 변경
    function dateFormat(date) {
        let dateFormat1 = "";
        if(date == ""){
            dateFormat1 = "";
        } else {
            const d1 = new Date(date);
            dateFormat1= d1.getFullYear() +
                '-' + ( (d1.getMonth()+1) < 9 ? "0" + (d1.getMonth()+1) : (d1.getMonth()+1) )+
                '-' + ( (d1.getDate()) < 9 ? "0" + (d1.getDate()) : (d1.getDate()) );
        }
        return dateFormat1;
    }

    //시간포맷변경함수 Date => HH:MM로 변경
    function hourMinFormat(date){
        let hourFormat1 = "";
        let hourFormat2 = "";

        if(date == ""){
            hourFormat1 = "";
        } else {
            const d2 = new Date(date);
            hourFormat1 = d2.toTimeString().split(' ')[0];
            hourFormat2 = hourFormat1.slice(0, -3);
        }
        return hourFormat1;
    }
    
    //문자열 빈 문자열인지 체크해서 기본값으로 문자열 리턴
    function nvl(str, defaultStr){
        if(typeof str === "undefined" || str === null || str === ""){
            if(typeof defaultStr === "undefined" || defaultStr === null || defaultStr === "")
                str = '';
            else
                str = defaultStr;
        }
        return str;
    }

    function sideActEvent(){
        let active = document.getElementById("actSidebarData").dataset.sidebar;
        let activeTarget = document.getElementById(active);
        activeTarget.classList.add("main_selected");
    }