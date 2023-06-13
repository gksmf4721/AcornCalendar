function strToObj(xhr){
    let obj = JSON.parse(xhr.response);
    return obj;
}