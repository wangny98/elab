/**
 * 说明：扩展Jquery，主要提供一些输入数据的合法性校验 作者：Junsan.Jin 最后修改日期：2010.08.30
 */
//校验字符串长度
//使用字节校验
$.validator.methods.xmaxlength = function(value, element, param) {
	if(value=="")
		return true;
	
	if(value.getByteLength()>param)
		return false;

	return true;
};

//校验字符串长度
//使用字节校验,固定长度
$.validator.methods.xfixedlength = function(value, element, param) {
	if(value=="")
		return false;
	
	if(value.getByteLength()!=param)
		return false;

	return true;
};

//校验角色代码
//角色代码格式“ROLE_ISS_角色名”，角色名只能是大写字母
$.validator.methods.xrolecode = function(value, element, param) {
	if(value=="")
		return true;
	
	var regex = /^ROLE_ISS_[A-Z]+$/;
	return regex.test(value);
};

//校验角色名称
//角色名称只能为中英文、数字、下划线的组合
$.validator.methods.xrolename = function(value, element, param) {
	if(value=="")
		return true;
	
	var regex = /^[a-zA-Z0-9_\u4e00-\u9fa5]*$/;
	return regex.test(value);
};

//校验服务站、大区等等的编码
//编码只能是数字的组合
$.validator.methods.xcode = function(value, element, param) {
	if(value=="")
		return true;
	
	var regex = /^[0-9]*$/;
	return regex.test(value);
};

//校验合同号
//合同号只能为中英文，数字的组合
$.validator.methods.xcontractno = function(value, element, param) {
	if(value=="")
		return true;
	
	var regex = /^[a-zA-Z0-9\u4e00-\u9fa5]*$/;
	return regex.test(value);
};

//校验姓名
//姓名只能为中英文半角字符的组合
$.validator.methods.xusername = function(value, element, param) {
	if(value=="")
		return true;
	
	var regex = /^[a-zA-Z0-9\u4e00-\u9fa5]*$/;
	return regex.test(value);
};
//校验续费月数
//新续费月数输入必须为正整数！
$.validator.methods.plusint = function(value, element, param) {
	if(value=="")
		return true;
	
	var regex  = /^[1-9]\d*$/;
	return regex.test(value);
};
//校发布时间
//发布时间不能小于当前时间！
$.validator.methods.publishdate = function(value, element, param) {
	issuecheck();	
	return true;
};
//校验发布频率上限
//发布频率上限不能超过720小时
$.validator.methods.frequenceylimit = function(value, element, param) {
	if(value=="")
		return true;	
	if(parseFloat(value)>720){
	 	return false;
	  }else{
	  	return true;
	  }
};
//校验电话号码
//格式为 区号-号码
$.validator.methods.phonenum = function(value, element, param) {
	if(value=="")
		return true;
	
	var regex = /^\d{3,4}-\d+$/;
	return regex.test(value);
};
//校验手机号码
//匹配格式：
//11位手机号码
//3-4位区号，7-8位直播号码，1－4位分机号
//如：12345678901、1234-12345678-1234
$.validator.methods.moblephonenum = function(value, element, param) {
	if(value=="")
		return true;
	
	var regex = /(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
	return regex.test(value);
};
//正整数
$.validator.methods.plusinteger = function(value, element, param) {
	if(value=="")
		return true;
	
	var regex = /^[1-9]\d*$/;
	return regex.test(value);
};
//检查证书可输入值
//只能为中英文、数字、_()*.-的组合
$.validator.methods.xcertinfo = function(value, element, param) {
	if(value=="")
		return true;
	
	var regex = /^[a-zA-Z0-9_()\-\.\*\u4e00-\u9fa5]*$/;
	return regex.test(value);
};
//检查邮政编码
//邮政编码的验证（开头不能为0，共6位）
$.validator.methods.zipcode = function(value, element, param) {
	if(value=="")
		return true;
	
	var regex = /^[1-9][0-9]{5}$/;
	return regex.test(value);
};
//检查组织机构代码
//组织机构代码号为9位
$.validator.methods.entno = function(value, element, param) {
	if(value=="")
		return true;
	
	if(value.length==9)
		return true;

	return false;
};
//检查税务登记证号
//税务登记证号为15位
$.validator.methods.revenueno = function(value, element, param) {
	if(value=="")
		return true;
	
	if(value.length==15)
		return true;

	return false;
};
