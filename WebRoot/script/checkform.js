/**
 * 说明：主要提供一些输入数据的合法性校验 作者：Junsan.Jin 创建日期：2010.03.17
 */

//Used 2010.05.21 Junsan.Jin modify
// 比对起止日期
// objs--开始日期控件
// objs--截止日期控件
// 日期必须具有标准格式yyyy-mm-dd
function checksedate(objs,obje)
{	
	var std=objs;
	var etd=obje;
	if(std!=""&&etd!=""&&std!=" "&&etd!=" ")
	{
		var sd=new Date(new Number(std.substr(0,4)),new Number(std.substr(5,2)),new Number(std.substr(8,2)));
		var ed=new Date(new Number(etd.substr(0,4)),new Number(etd.substr(5,2)),new Number(etd.substr(8,2)));
		if(sd>ed)
		{
			return false;
		}
	}
	
	return true;
}

// 输入的代码只能为字母和数字的组合
function checkCode(str)
{
	var reg=/^[a-zA-Z0-9]*$/;
	if((arr=str.match(reg)) && str != "")
	{
		return true;		
	}
	else
	{
		alert("代码只能为半角字母和数字的组合");
		return false;
	}
}

// 输入的名称中英文数字
function checkInputString(str)
{

	var reg=/[']/;
	if((arr=str.match(reg))||str == "")
	{
		alert("不能输入为空或者包含单双引号字符");
		return false;
	}
	else
	{
		return true;
	}	
	
}
//校验输入名称
function checkPName(str)
{

//	var reg=/[']/;
	var reg=/^[a-zA-Z0-9\u4e00-\u9fa5]*$/;
	if(reg.test(str) && str != "")	{	
		return true;
	}
	else
	{	alert("付款人只能为中英文半角字符，不能包含空格！");
		return false;
	}	
	
}

// 输入串是否为空，已经使用请不要轻易修改
function checkEmpty(str)
{
   	var reg = /^\s+$/;
   	if((arr=str.match(reg)) || str == "")
   	{
      	return false;
   	}
   	else
   	{    
	   return true;
   	}
}

// 输入串中是否有非法字符(可以是汉字)
function checkString(str)
{
 
    var reg = /^(\w|[\u4E00-\u9FA5])+$/;
   	if(arr=str.match(reg))
   	{
      		return true;
   	}
   	else
   	{
     		alert("输入串为空或包含有非法字符");
      		return false;
   	}
}

//Used 2010.05.25 Junsan.Jin modify
//输入串是否是正正数
function checkPlusInt(str)
{
   	var reg = /^[1-9]\d*$/;
   	if(arr=str.match(reg))
   	{
      		return true;
   	}
   	else
   	{
     	alert("输入必须为正整数！");
      	return false;
   	}
}

// 输入的代码只能为字母或数字和“.”的组合（白名单名称）
function checkNameCode(str)
{
	var reg=/^[a-zA-Z0-9.]*$/;
	if((arr=str.match(reg)) && str != "")
	{		
			return true;		
	}
	else
	{
		alert("白名单名称只能为半角字母、数字和.的组合");
		return false;
	}
}

// 输入的中英文，数字，下划线的组合（应用领域名称）
function checkInputAppName(str)
{
	var reg=/^[a-zA-Z0-9_\u4e00-\u9fa5]*$/;
	if(reg.test(str) && str!=""){		
			return true;		
	}
	else
	{
		alert("应用领域名称只能为中英文、数字、下划线的组合");
		return false;
	}
}

// 输入的中英文，数字的组合（合同号）
function checkInputContract(str)
{
	var reg=/^[a-zA-Z0-9.\u4e00-\u9fa5]*$/;
	if((arr=str.match(reg)) && str != "")
	{
		return true;
		
	}
	else
	{
		alert("合同号只能为中英文、数字的组合");
		return false;
	}
}

// 检查输入的串是否在0到9之间的字符组成 不是则返回true，如果是则返回false
function checkZInt(str){
 	var reg = /^\d+$/;
 	if(arr=str.match(reg))
 	{
	// 全部是数字
    	return false;
 	}
 	else
 	{
	// 含有其他字符
    	return false;
 	}
}

//Used 2010.05.24 Junsan.Jin modify
// 字符串去掉左右空格的方法
String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

//Used 2010.05.24 Junsan.Jin modify
// 校验字符长度
function getByteLength()
{
        var intCount = 0;
        for(var i = 0;i < this.length;i ++)
        {
                // Ascii码255
                if(this.charCodeAt(i) > 255)intCount += 2;
                else intCount += 1;
        }
        return intCount;
}
String.prototype.getByteLength = getByteLength;

// 多选选择校验
function checkSelect(src){
  var j=0;
	if(src.length != undefined){
		for(var i=0;i<src.length;i++){
			if(src[i].checked == true){
				j=j+1;
			} 
		}
	} else {
		if(src.checked == true){j=1;}
	}
	if(j>0){
		return true;
	} else {
		alert("请选择要处理的记录!");
		return false;
	}			
}

//Used 2010.05.21 Junsan.Jin modify
//日期格式校验 yyyy-MM-dd
function checkDate(str){
	
var reg=/^\d{4}-((0{1}[1-9])|(1[0-2]))-((0{1}[1-9])|((1|2)[0-9])|(3(0|1)))$/;	
	
	if((arr=str.match(reg)) && str != "" && checkRightDate(str.substr(0,4),str.substr(5,2),str.substr(8,2)))
	{
		return true;
	}
	else
	{
		return false;
	}
}


// 主要提供对日期的精确校验，验证日期是否合法
// 非法返回false,合法返回true

function checkRightDate(year,month,day){		
	var flag=true;
	
	var time=new Date(year,month-1,day);
	// alert(time);
	
	var e_year=time.getFullYear();
	// alert(e_year);
	var e_month=time.getMonth()+1;
	// alert(e_month);
	var e_day=time.getDate();
	// alert(e_day);

	if(year!=e_year||month!=e_month||day!=e_day)
	{
		flag=false;
	}
	return flag;
}

//Used 2010.05.24 Junsan.Jin modify
//输入的中英文，数字，下划线的组合（OID名称）
function checkOIDName(str)
{
	var reg=/^[a-zA-Z0-9_\u4e00-\u9fa5]*$/;
	if(reg.test(str)){		
			return true;		
	}
	else
	{
		return false;
	}
}

//Used 2010.05.24 Junsan.Jin modify
//输入只能为数字和.（OID代码）
function checkOIDCode(str)
{
	var reg=/^[0-9.]*$/;
	if(reg.test(str) && str!=""){		
			return true;		
	}
	else
	{
		return false;
	}
}

//Used 2010.05.24 Junsan.Jin modify
//输入的中英文，数字，下划线的组合（OIDVALUE）
function checkOidValue(str)
{
	var reg=/^[a-zA-Z0-9_\u4e00-\u9fa5]*$/;
	if(reg.test(str)){			
		return true;	
	}
	else
	{
		//alert("OID值只能为中英文、数字、下划线的组合");
		return false;
	}
}

//Used 2010.05.21 Junsan.Jin modify
// 检查多行信息时是否同列有重复的输入内容
function chksamcol(prefix,rownum,col,msg){
	var sresult="";
	var rows = document.getElementById(rownum);
	var iCount = rows.value;
	var selObj1=null;
	var selObj2=null;
	for(var i=1;i<=iCount;i++){		
		selObj1=document.getElementById(prefix+i);	
		if(selObj1.value.trim()!=""){
			for(var j=i+1;j<=iCount;j++){
				selObj2=document.getElementById(prefix+j);
				if(selObj1.value.trim()==selObj2.value.trim()){
					sresult = "OID信息：第"+col+"列的第"+i+"行与第"+j+"行的 "+msg+" 重复！";
				}
			}
		}	
	}
	return sresult;
}

//Used 2010.05.21 Junsan.Jin modify
// 检查多行多列信息时是否同列存在没有输入的必填项
//同时检查格式是否符合规范
function chkrownull(prefix,rownum){
	var rows = document.getElementById(rownum);
	var iCount = rows.value;

	var countNull;
	
	for(var i=1;i<=iCount;i++){
		countNull=0;
		for(var j=0;j<prefix.length;j++){
			if(document.getElementById(prefix[j]+i).value.trim()==""){
				countNull++;
			}else{
				if(prefix[j]=="oidName_"){
					if(!checkOIDName(document.getElementById(prefix[j]+i).value)){
						return "OID信息：第"+i+"行OID名称只能为中英文、数字、下划线的组合";
					}else if(document.getElementById(prefix[j]+i).value.getByteLength()>30){
						return "OID信息：第"+i+"行OID名称的长度不能超过30（单个汉字是2个字节）"
					}
				}else if(prefix[j]=="oidCode_"){
					if(!checkOIDCode(document.getElementById(prefix[j]+i).value)){
						return "OID信息：第"+i+"行OID代码只能为数字和.的组合";
					}else if(document.getElementById(prefix[j]+i).value.getByteLength()>20){
						return "OID信息：第"+i+"行OID代码的长度不能超过20（单个汉字是2个字节）"
					}
				}
			}
		}
		if(countNull>0&&countNull!=prefix.length){
			return "OID信息：第"+i+"行输入信息不完整，请补充完整，或者删除该行，或全行留空";
		}
	}
	return "";
}

//Used 2010.05.25 Junsan.Jin Add
//检查OIDValue格式是否符合规范
function chkRowsOidValue(prefix,rownum){
	var rows = document.getElementById(rownum);
	var iCount = rows.value;	
	
	for(var i=1;i<=iCount;i++){
		if(document.getElementById(prefix+i).value.trim()!=""){
			if(!checkOidValue(document.getElementById(prefix+i).value)){
				return "OID信息：第"+i+"行OID值只能为中英文、数字、下划线的组合";
			}else if(document.getElementById(prefix+i).value.getByteLength()>20){
				return "OID信息：第"+i+"行OID值的长度不能超过20（单个汉字是2个字节）"
			}
		}
	}
	return "";
}

//校验翻页是否合法
function checkPage(obj,total){
	if(obj.value>total){
		obj.value=total;
	}
}

//角色应用名称
//角色名称只能为中英文、数字、下划线的组合
function checkRoleName(str){
	var reg=/^[a-zA-Z0-9_\u4e00-\u9fa5]*$/;
	if((arr=str.match(reg)) && str != "")
	{
		return true;		
	}
	else
	{
		return false;
	}
}
