/**
 * Created by DHUser on 2017/2/27.
 */
/**
 * Created by DHUser on 2016/10/24.
 */

/**
 * @author  iechenyb
 * @param type 校验类型
 * @param length  文本长度
 * @param express 正则表达式
 */
function  validate(value,type,length,express,name){
    if(type=='text'||type==0){
        return text(value,length,express,name);
    }
    if(type=='mobile'||type==1){
        return mobile(value,11,express,name);
    }
    if(type=='telphone'||type==2){
        return telphone(value,length,express,name);
    }
    if(type=='email'||type==3){
        return email(value,length,express,name);
    }
    if(type=='hanzi'||type==4){
        return hanzi(value,length,express,name);
    }
    if(type=='number'||type==5){
        return num(value,length,express,name);
    }
    if(type=='idcard'||type==6){
        return idcard(value,length,express,name);
    }
}
//校验省份证号
function idcard(value,length,express,name){
    var express = /(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)/;
    return len(value,length,name)&&exp(value,express,name);
}
//校验汉字
function hanzi(value,length,express,name){
    var express = /^[\\u4e00-\\u9fa5]+$/;
    return commValidate(value,length,express,name);
}
//校验数字
function num(value,length,express,name){
    if(isEmpty(value,name)){
        return false;
    }else{
        if(!isNumber(value,name)){
            return false;
        }
    }
    return true;
}
//校验文本
function text(value,length,express,name){
    return commValidate(value,length,express,name);
}
//校验手机号码
function mobile(value,length,express,name){
    var express = /^1(3|4|5|7|8)\d{9}$/;
    if(isEmpty(value,name)){
        return false;
    }else{
        if(!express.test(value)){
            alert(name+"必须为长度为11的有效数字！");
            return false;
        }
    }
    return true;
}
//校验座机号码
function telphone(value,length,express,name){
    return true;
}
//校验电子邮件
function email(value,length,express,name){
    var express = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
    return commValidate(value,length,express,name);
}
function commValidate(value,length,express,name){
    if(isEmpty(value,name)){
        return false;
    }else{
        if(!len(value,length,name)){
            return false;
        }else if(!exp(value,express,name)){
            return false;
        }
    }
    return true;
}
//是否为空
function isEmpty(value,name){
    if(typeof value=="undefined"||value==null||value=='null'||value==''){
        alert(name+"不能为空！");
        return true;
    }
    return false;
}
function isNotEmpty(value,name){
    return !isEmpty();
}
function len(value,length,name){
    if(value.length>length){
        alert(name+'超出最大长度'+length+"!");
        return false;
    }
    return true;
}
function exp(value,express,name){
    if(express!=null&&express!=''){
        var check = express.test(value);
        if(!check){
            alert(name+"格式有误！");
            return false;
        }
    }
    return true;
}
//校验数字
function isNumber(_str,name){
    var tmp_str = _str;
    var pattern = /^[0-9]+$/;
    if(!pattern.test(tmp_str)){
        alert(name+"必须为数字！");
        return false;
    }
    return true ;
}
//验证数字(整数、浮点数都可以通过)
function isfloat(oNum){
    if(!oNum) return false;
    var strP=/^\d+(\.\d+)?$/;
    if(!strP.test(oNum)) return false;
    try{
        if(parseFloat(oNum)!=oNum) return false;
    }catch(ex){
        return false;
    }
    return true;
}
/*
 * 判断图片类型
 *
 * @param ths
 * type="file"的javascript对象
 * @return true-符合要求,false-不符合
 */
function checkImgType(value,name){
    if (value == "") {
        alert("请上传图片");
        return false;
    } else {
        if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(value)) {
            alert(name+"类型必须是.gif,jpeg,jpg,png中的一种!");
            return false;
        }
    }
    return true;
}
/*
 * 判断图片大小
 *
 * @param ths
 * type="file"的JavaScript对象
 * @param width
 * 需要符合的宽
 * @param height
 * 需要符合的高
 * @return true-符合要求,false-不符合
 */
function checkImgPX(ths, width, height) {
    var img = null;
    img = document.createElement("img");
    document.body.insertAdjacentElement("beforeEnd", img); // firefox不行
    img.style.visibility = "hidden";
    img.src = ths.value;
    var imgwidth = img.offsetWidth;
    var imgheight = img.offsetHeight;
    alert(imgwidth + "," + imgheight);
    if(imgwidth != width || imgheight != height) {
        alert("图的尺寸应该是" + width + "x"+ height);
        ths.value = "";
        return false;
    }
    return true;
}