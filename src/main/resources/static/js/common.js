    //데이터 유효성 검사 
    function fn_dataChk(formId){
        let chk = true;
        const allElements = formId.getElementsByTagName('*');
        for (let i = 0; i < allElements.length; i++) {
            const element = allElements[i];
            console.log()
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

    //확인용 alert
    $.confirm = function(){

    }

    //빈값인지 체크하는 메서드
    var isEmpty = function(value){
        if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){
            return true
        }else{
            return false
        }
    };
    // 출처: https://sanghaklee.tistory.com/3 [이상학의 개발블로그:티스토리]


