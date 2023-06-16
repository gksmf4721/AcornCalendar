function strToObj(xhr){
    let obj = JSON.parse(xhr.response);
    return obj;
}

//데이터 유효성 검사 
function fn_dataChk(formId){
    let chk = true;
    const allElements = formId.getElementsByTagName('*');
    console.log(allElements.length);
    for (let i = 0; i < allElements.length; i++) {
        const element = allElements[i];
        var type = element.getAttribute('type');    //유효성검사가 필요한 타입
        var name = element.getAttribute('name');    //유효성검사가 필요한 이름
        var className = element.getAttribute('class');
        var id = element.getAttribute('id');

        if(element.getAttribute('readonly')==false &&element.getAttribute('disabled')==false || element.hasAttribute('data-name')){
            if(type == 'checkbox'){
                if(element.checked == false){
                    alert(element.dataset.name + '을(를) 체크해주세요');
                    element.focus();
                    chk = false;
                    return false;
                }

            } else if(type == 'password'){
                if (element.value =='') {
                    alert(element.dataset.name + '을(를) 입력해주세요');
                    element.focus();
                    chk = false;
                    return false;
                }
            
            } else if(type == 'radio'){
                for(var radio of name){
                    if(!radio.checked) {
                        alert(element.dataset.name + '을(를) 선택해주세요');
                        element.focus();
                        chk = false;
                        return false;
                    }
                }

            } else if(type == 'text' || type=='date'){
                if (element.value =='') {
                    alert(element.dataset.name + '을(를) 입력해주세요');
                    element.focus();
                    chk = false;
                    return false;
                }

            } else if(element == 'select'){ //select박스일경우
                if(element.value == ''){
                    alert(element.dataset.name + '을(를) 선택해주세요');
                    element.focus();
                    chk = false;
                    return false;
                }
            } else {
                if(element.value == ''){
                    element.focus();
                    alert(element.dataset.name + '을(를) 입력해주세요');
                    chk = false;
                    return false;
                }
            }
        } else if (className == 'chk_befo'){
            alert('중복 체크를 해주세요.');
            element.focus();
            chk = false;
            return false;
        }
    }
    return chk;
}
