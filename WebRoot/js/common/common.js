// 定义命名空间
Ext.namespace("dm.comm");

dm.comm.ShowInternetError = function() {
	Ext.Msg.show({
				title : '提示',
				msg : '网络错误，请联系管理员',
				buttons : Ext.Msg.OK,
				icon : Ext.Msg.ERROR
			});
};

dm.comm.comm_ConvertStringToDate = function(strTime,format){
	return Date.parseDate(strTime,format);
};

dm.comm.comm_ConvertStringToDate = function(strTime){
	return new Date(strTime).format('yyyy-MM-dd hh:mm:ss') ;
};


dm.comm.showGridError = function() {
	Ext.Msg.show({
				title : '列表查询异常',
				msg : '请联系系统管理员！',
				buttons : Ext.Msg.OK,
				icon : Ext.Msg.ERROR
			});
};
/**
 * 错误信息提示
 * 
 * @param {}
 *            retCode
 * @param {}
 *            defualtMsg
 */
dm.comm.showErrMsg = function(retuCode) {
	Ext.Msg.show({
				title : '提示',
				msg : '网路服务错误，请联系管理员',
				buttons : Ext.Msg.OK,
				icon : Ext.Msg.ERROR
			});
};

dm.comm.showErrMsgOnTitle = function(retuCode, title) {
	Ext.Msg.show({
				title : ERMS_COMM_REGIST,
				msg : "["+title + "]:" + ERMS.comm.ErrMsg(retuCode),
				buttons : Ext.Msg.OK,
				icon : Ext.Msg.ERROR
			});
};

/**
 * 操作提示信息
 * 
 * @param {}
 *            args
 */
dm.comm.comm_alert = function(args) {
	
	var config = {
		title : '提示',
		msg : '成功',
		icon : Ext.MessageBox.INFO,
		closable : false
	};
	Ext.apply(config, args);

	var alert = Ext.Msg.show(config);

	setTimeout(function() {// 此处设置为2秒后自动隐藏保存提示窗体
				alert.hide();
			}, 1000);
};

/**
 * 将字符串转化为时间
 * @param strTime 字符串时间
 * @param  format 输入的指定日期格式
 * @return 返回转化后的日期
 */
/*dm.comm.comm_ConvertStringToDate = function(strTime,format){
	return Date.parseDate(strTime,format);
};*/

/**
 * 将long类型的日期转化为yyyy-MM-dd的日期格式
 * @param {} value long类型的日期
 * @return {} 返回yyyy-mm-dd的日期
 */
dm.comm.comm_ConvertStringToDate=function renderDate(value)
{
	if(value==null){
		return value;
	}
	if(isNaN(value)){
		return null;
	}
	if(""==value){
		return null;
	}
	 return new Date(value).format('yyyy-MM-dd') ;
};

dm.comm.comm_ConvertDatetimeToDate = function(value) {
	value = new Date(value).format('yyyy-MM-dd');
	return value.substring(0,11);
};


/**
 * 检测文档是否是pdf文件
 * @param {} docName
 * @return {Boolean}
 */
isContainsPdf = function(docName){
	docName = docName.trim().toLowerCase();
	var index=docName.lastIndexOf('.')+1;
	docName=docName.substring(index);
	if(docName=="pdf"){
		return true;
	}else{
		return false;
	}
};

/**
 * 检测文档是否是dwg文件
 * @param {} docName
 * @return {Boolean}
 */
isContainsDwg = function(docName){
	docName = docName.trim().toLowerCase();
	var index=docName.lastIndexOf('.')+1;
	docName=docName.substring(index);
	if(docName=="dwg"){
		return true;
	}else{
		return false;
	}
}


/**
 * 检测文本文档格式是否支持预览
 * 
 * @param {}
 *            docName 文档名称
 * @return {Boolean}
 */
detectTextFileType = function(docName) {
	docName = docName.trim().toLowerCase();
	var index=docName.lastIndexOf('.')+1;
	docName=docName.substring(index);
	if (docName.indexOf("pdf") >= 0 || docName.indexOf("jpg") >= 0
			|| docName.indexOf("jpeg") >= 0 || docName.indexOf("png") >= 0
			|| docName.indexOf("gif") >= 0 || docName.indexOf("doc") >= 0
			|| docName.indexOf("html") >= 0 || docName.indexOf("htm") >= 0
			|| docName.indexOf("ppt") >= 0 || docName.indexOf("xls") >= 0
			|| docName.indexOf("bmp") >= 0 || docName.indexOf("txt") >= 0
			|| docName.indexOf("dwt") >= 0 || docName.indexOf("dwg") >= 0
			|| docName.indexOf("dws") >= 0 || docName.indexOf("dxf") >= 0
			|| docName.indexOf("sat") >= 0 || docName.indexOf("igs") >= 0
			|| docName.indexOf("pts") >= 0 || docName.indexOf("pkg") >= 0
			|| docName.indexOf("step") >= 0 || docName.indexOf("vda") >= 0
			|| docName.indexOf("prt") >= 0 || docName.indexOf("par") >= 0
			|| docName.indexOf("cgr") >= 0 || docName.indexOf("ipt") >= 0) {
		return true;
	}
	return false;
}
/**
 * 检查多媒体文件是否支持预览
 * 
 * @param {}
 *            docName
 */
detectMediaFileType = function(docName) {
	if (docName.indexOf("mp4") >= 0 || docName.indexOf("m4a") >= 0
			|| docName.indexOf("flv") >= 0 || docName.indexOf("mov") >= 0) {
		return true;
	}
	return false;
}

dm.comm.IframePost = function() {
	var setFrame = function(oFrm) {
		if (!oFrm.name || oFrm.name == "")
			oFrm.name = oFrm.id;
	}, createForm = function(obj) {
		var oForm = document.createElement("form");
		oForm.id = "forPost";
		oForm.method = "post";
		oForm.action = obj.Url;
		oForm.target = obj.Target.name;
		var oIpt, arrParams;
		arrParams = obj.PostParams;
		for (var tmpName in arrParams) {
			oIpt = document.createElement("input");
			oIpt.type = "hidden";
			oIpt.name = tmpName;
			oIpt.value = arrParams[tmpName];
			oForm.appendChild(oIpt);
		}
		return oForm;
	}, deleteForm = function() {
		var oOldFrm = document.getElementById("forPost");
		if (oOldFrm) {
			document.body.removeChild(oOldFrm);
		}
	}
	return {
		// 功能：给嵌套的Iframe界面Post值
		// 参数：obj - 传递对象
		// {Url - 页面地址, Target - Iframe对象, PostParams - Post参数,{ 参数名1 : 值1, 参数名2
		// : 值2 } }
		// 例：{ Url: "ProjPlan_DcRate_Dept_Main.aspx?YearMonth=2012-01", Target:
		// appIframe, PostParams: { DeptGUID:
		// document.all["txt_DeptGUID"].value} }
		doPost : function(obj) {
			setFrame(obj.Target);
			deleteForm();
			var oForm = createForm(obj);
			document.body.appendChild(oForm);
			oForm.submit();
			deleteForm();
		}
	}
}();

/**
 * 用户信息vtype校验
 */
Ext.apply(Ext.form.VTypes, {
	documentType : function(_v) {
		if (!/^([a-z])+([a-z]*)$/.test(_v)) {
			return false;
		}
		return true;
	},
	documentTypeText : '文档类型只能输入小写字母',
	executeDay : function(_v) {
		if (_v > 100 || _v < 0) {
			return false;
		}
		return true;
	},
	executeDayText : '天数取值0～100',
	executeHour : function(_v) {
		if (_v >= 24 || _v < 0) {
			return false;
		}
		return true;
	},
	executeHourText : '小时取值0～23',

	// 验证保管期限的年
	retentionYear : function(_v) {
		if (_v >= 100 || _v < 0) {
			return false;
		}
		return true;
	},
	retentionYearText : '年数取值0～99',
	// 验证保管期限的月
	retentionMonth : function(_v) {
		if (_v >= 13 || _v < 0) {
			return false;
		}
		return true;
	},
	retentionMonthText : '月数取值0～12',
	// 验证保管期限的天
	retentionDay : function(_v) {
		if (_v >= 32 || _v < 0) {
			return false;
		}
		return true;
	},
	retentionDayText : '天数取值0～31',
	// 验证固定电话 格式
	phone : function(_v) {
		if (!/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/.test(_v)) {
			return false;
		}
		return true;
	},
	phoneText : '固定电话格式为xxxx-xxxxxxx',
	// 验证固定电话 格式
	phone : function(_v) {
		if (!/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/.test(_v)) {
			return false;
		}
		return true;
	},
	phoneText : '固定电话格式为xxxx-xxxxxxx',
	// 验证固定电话 格式
	phone : function(_v) {
		if (!/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/.test(_v)) {
			return false;
		}
		return true;
	},
	phoneText : '固定电话格式为xxxx-xxxxxxx',

	// 联系方式格式
	constact : function(_v) {
		if (!/^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}(-\d{1,4})?$|(^1[3|4|5|8][0-9]{9}$)/
				.test(_v)) {
			return false;
		}
		return true;
	},
	constactText : '固定电话格式为xxxx-xxxxxxx或xxx-xxxxxxxx-xxx，可加分机号，手机格式以1开头11位数字',

	// 验证传真格式
	fax : function(_v) {
		if (!/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/.test(_v)) {
			return false;
		}
		return true;
	},
	faxText : '传真格式为xxxx-xxxxxxx',

	// 验证备注 长度
	remark : function(_v) {
		if (_v != null || (_v.trim() == "")) {
			if (_v.length > 600) {
				return false;
			}
			return true;
		}
	},
	remarkText : '备注信息不得超过60个字符',
	// 年龄验证
	age : function(_v) {
		if (/^\d+$/.test(_v)) {// 判断必须是数字
			var _age = parseInt(_v);
			// 增加业务逻辑,小于100的数字才符合年龄
			if (0 < _age && _age < 100) {
				return true;
			}
		}
		return false;
	},
	ageText : '年龄必须为数字，并且不能超过100岁，格式为23', // 出错信息后的默认提示信息
	ageMask : /[0-9]/i, // 键盘输入时的校验
	// 手机验证
	mobilephone : function(_v) {
		if (!/^1\d{10}$/.test(_v)) {
			return false;
		}
		return true;
	},
	mobilephoneText : '手机号码填写有误',
	mobilephoneMask : /[0-9]/i,
	// 分值验证
	fraction : function(_v) {
		if (!/^\d{1,}$/.test(_v)) {
			return false;
		}
		if (_v < 0 || _v > 100) {
			return false;
		}
		return true;
	},
	fractionText : '分值填写有误',

	// 身份验证
	idcard : function(value, field) {
		return /^\d{14}(\d{4}|(\d{3}[xX]))$/.test(value);
	},
	idcardText : '身份证号码输入错误',

	// 打开次数验证
	openNum : function(_v) {
		if (_v != "无限制打开") {
			if (/^\d+$/.test(_v)) {// 判断必须是数字
				var _age = parseInt(_v);
				// 增加业务逻辑,小于100的数字才符合年龄
				if (0 < _age && _age < 99999) {
					return true;
				}
			}
			return false;
		}
		return true;
	},
	openNumText : '打开次数必须为数字，并且超过0，小于99999', // 出错信息后的默认提示信息

	deadlineNum : function(_v) {
		if (/^\d+$/.test(_v)) {// 判断必须是数字
			var _age = parseInt(_v);
			// 增加业务逻辑,小于100的数字才符合年龄
			if (0 < _age && _age < 99999) {
				return true;
			}
		}
		return false;
	},
	deadlineNumText : '有限期必须为数字，并且超过0，小于99999' // 出错信息后的默认提示信息
});

/**
 * 求js数组的交集(a和b必须是已排序好的数组)
 * 
 * @param {}
 *            a 数组a
 * @param {}
 *            b 数组b
 * @return {} 数组a和b的交集
 */
dm.comm.comm_arr_getCross = function(a, b) {
	var ai = 0, bi = 0;
	var result = new Array();

	while (ai < a.length && bi < b.length) {
		if (a[ai] < b[bi]) {
			ai++;
		} else if (a[ai] > b[bi]) {
			bi++;
		} else /* they're equal */
		{
			result.push(a[ai]);
			ai++;
			bi++;
		}
	}
	return result;
}

/**
 * 求js数组的差集(array1-array2)
 * 
 * @param {}
 *            array1
 * @param {}
 *            array2
 * @return {} array1-array2
 */
dm.comm.comm__arr_getDiff = function(array1, array2) {
	var o = {};// 转成hash可以减少运算量，数据量越大，优势越明显。
	for (var i = 0, len = array2.length; i < len; i++) {
		o[array2[i]] = true;
	}

	var result = [];
	for (i = 0, len = array1.length; i < len; i++) {
		var v = array1[i];
		if (o[v])
			continue;
		result.push(v);
	}
	return result;
};

/**
 * 设置高度
 * 
 * @param decreaseHeight
 *            减去的高度
 * @return
 */
dm.comm.comm_SetHeight = function(decreaseHeight) {
	var rtnHeight = parseFloat(window.screen.availHeight)
			- parseFloat(decreaseHeight);
	return rtnHeight;
};

/**
 * 按屏幕分辨率设置事件窗口高度
 */
dm.comm.comm_SetEnventWindowHeight = function() {
	var height = window.screen.height; // 屏幕分辨率
	switch (height) {
		case 1024 :
			return 700;
			break;
		case 960 :
			return 680;
			break;
		case 900 :
			return 620;
			break;
		case 864 :
			return 620;
			break;
		default :
			return 530;
	}
};

/**
 * 去除字符串头部和尾部空格
 * 
 * @param str
 *            输入的字符串
 * @return 返回首尾没有空格的字符串
 */
dm.comm.comm_blank = function trim(str) {
	return str.replace(/(^(\s|　)*)|((\s|　)*$)/g, "");

};

/**
 * 截取0-300位的字符串
 * 
 * @param str
 *            输入的字符串
 * @return 返回长度小于300的字符串
 */
dm.comm.comm_substring = function(str) {
	var _str = dm.comm.comm_blank(str);
	if (_str.length > 300) {
		return _str.substring(0, 300);
	}
	return _str;
};

/**
 * 设置指定列表的复选框是否全选或取消全选
 * 
 * @param bool
 *            为true设置全选，为false设置取消全选
 * @param startNode
 *            指定的列表的id
 */
dm.comm.comm_CheckboxAllSelect = function(bool, startNode) {
	if (bool) { // 复选框全选
		var temp = Ext.DomQuery.selectNode('.x-grid3-hd-checker', startNode);
		var hd = Ext.fly(temp);
		hd.addClass('x-grid3-hd-checker-on');
	} else { // 复选框取消全选
		var temp = Ext.DomQuery.selectNode('.x-grid3-hd-checker', startNode);
		var hd = Ext.fly(temp);
		hd.removeClass('x-grid3-hd-checker-on');
	};
};

/**
 * 获取分页栏
 * 
 * @param id
 *            id标识
 * @param pageSize
 *            每页记录数
 * @param store
 *            数据源
 * @return 返回分页栏
 */
dm.comm.comm_pageToolBar = function(id, pageSize, store) {
	var bar = new Ext.ux.grid.PagingToolbar({
		id : id,
		pageSize : pageSize,
		store : store,
		beforePageText : ES_COMM_BEFOREPAGETEXT, // 转到
		afterPageText : ES_COMM_AFTERPAGETEXT, // 页
		lastText : ES_COMM_LASTTEXT, // 尾页
		nextText : ES_COMM_NEXTTEXT, // 下一页
		prevText : ES_COMM_PREVTEXT, // 上一页
		firstText : ES_COMM_FIRSTTEXT, // 首页
		refreshText : ES_COMM_REFRESHTEXT, // 刷新
		displayInfo : true,
		displayMsg : ES_COMM_DISPLAYMSG, // 第 {0} - {1}条 共 {2}条记录
		emptyMsg : ES_COMM_EMPTYMSG, // 没有数据
		pageInfoText : ES_COMM_PAGEINFOTEXT, // 第{0}/{1}页
		gotoText : ES_COMM_GOTOTEXT
			// 跳转
		});
	return bar;
};

/**
 * 将字符串转换为指定精度的浮点数
 * 
 * @param str
 *            要转换的字符串
 * @param len
 *            精度,默认小数点后两位
 */
dm.comm.comm_toFloatFn = function(str, len, returnNull) {
	len = len >= 0 ? len : 2; // 如果小数点精度小于0，则为小数点精度设置为2
	var f = parseFloat(str);
	var nf = "";
	var offset = 0;
	if (!isNaN(f)) {// 是数字
		var str = f.toString().split(".");
		if (str.length == 2) {// 该数由两部分组成，即整数和小数
			if (str[1].length > len) {// 如果小数点位数大于指定位数
				str[1] = str[1].substr(0, len);
			}
		}
		nf = str.length > 1 && len !== 0 ? str.join(".") : str[0];
		return nf;
	} else {// 不是数字
		return str;
	}
};

/**
 * 获取字符串的长度 将汉字转换为2个字符
 * 
 * @param str
 *            输入的字符串
 * @return 返回字符串长度
 */
dm.comm.comm_getStringLength = function(str) {
	if ("" == str) { // 输入为空
		return 0;
	} else {
		str = str.replace(/[^\x00-\xff]/g, "xx"); // 将汉字转为2个字符
		return str.length;
	}
};

/**
 * 将全角转化为半角
 */
dm.comm.comm_changeSBCToDBC = function() {

};

/**
 * 生成tip标签
 * 
 * @param str
 *            输入的字符串
 */
dm.comm.comm_getTip = function(str) {
	if (str == null) {
		return "";
	}
	str = dm.comm.comm_replaceSpecialChar(str);
	// 最新的版本效果
	var showTip = "<div>" + str + "</div>";
	var value1 = dm.comm.comm_replaceSpecialChar(showTip);
	var value2 = "<div style='word-break:break-all;'>" + value1 + "</div>";
	return '<div style="padding:3px;text-overflow:ellipsis;overflow:hidden;"><span ext:qtip="'
			+ value2 + '">' + str + '</span></div>';
};

dm.comm.comm_contact_getTip = function(str) {
	str = dm.comm.comm_replaceSpecialChar(str);
	var showTip = "<div>" + str + "</div>";
	var value1 = dm.comm.comm_replaceSpecialChar(showTip);
	var value2 = "<div style='word-break:break-all;'>" + value1 + "</div>";
	return '<span style="padding:3px;" ext:qtip="' + value2 + '">' + str
			+ '</span>';
};

/**
 * 字符串替换方法
 * 
 * @param str
 *            输入的字符串
 * @return 替换后的字符串
 */
dm.comm.comm_replaceSpecialChar = function(str) {
	var RexStr = /\<|\&nbsp|\>|\"|\'|\&|\s/g;
	if (typeof str != 'string' || str == null) {
		return str;
	};
	var _str = str.replace(RexStr, function(MatchStr) {
				switch (MatchStr) {
					case "<" :
						return "&lt;";
						break;
					case ">" :
						return "&gt;";
						break;
					case "\"" :
						return "&quot;";
						break;
					case "'" :
						return "&#39;";
						break;
					case "&nbsp" :
						return "&nbsp;";
						break;
					case "&" :
						return "&amp;";
						break;
					case " " :
						return "&nbsp";
						break;
					default :
						break;
				}
			});
	return _str;
};
/**
 * 数字替换方法
 * 
 * @param num
 *            输入的数字
 * @return 替换后的数字
 */
dm.comm.comm_replaceZero = function(num) {

	if (typeof num != 'number' || num == null) {
		return num;
	};
	switch (num) {
		case 0 :
			return " ";
			break;
		default :
			return num;
			break;
	}
};

/**
 * 返回月份的数据源
 * 
 * @return Ext.data.ArrayStore 返回数组数据源
 */
dm.comm.comm_getMonthStore = function() {
	var data = "";
	data = [[1, '01'], [2, '02'], [3, '03'], [4, '04'], [5, '05'], [6, '06'],
			[7, '07'], [8, '08'], [9, '09'], [10, '10'], [11, '11'], [12, '12']];
	var store = new Ext.data.ArrayStore({
				fields : ['month', 'monthLabel'],
				data : data
			});
	return store;
};

/**
 * 返回年份的数据源
 * 
 * @return Ext.data.ArrayStore 返回数组数据源
 */
dm.comm.comm_getYearStore = function() {
	var data = new Array();
	var currentYear = new Date().getYear(); // 今年
	var j = 0;

	var year = parseInt(currentYear) - parseInt(1); // 前一年
	data[j] = [year, year];
	j++;
	for (var i = 0; i < 5; i++) {
		var year = parseInt(currentYear) + parseInt(i);
		data[j] = [year, year];
		j++;
	}
	var store = new Ext.data.ArrayStore({
				fields : ['year', 'yearLabel'],
				data : data
			});
	return store;
};

/**
 * 显示文件下载对话框
 * 
 * @param url
 *            要下载的文件路径
 */
dm.comm.comm_openDownFileWindow = function(url) {
	document.getElementById('fileFrame').src = url;
};

/**
 * 去掉数组重复数据
 * 
 * @param someArray
 *            输入的数组
 * @return 去掉重复后的数组
 */
dm.comm.comm_getUniqueArrayFn = function(someArray) {
	var tempArray = someArray.slice(0);// 复制数组到临时数组
	for (var i = 0; i < tempArray.length; i++) {
		for (var j = i + 1; j < tempArray.length;) {
			if (tempArray[j] == tempArray[i]) { // 后面的元素若和待比较的相同，则删除并计数；
				tempArray.splice(j, 1); // 删除后，后面的元素会自动提前，所以指针j不移动
			} else { // 不同，则指针移动
				j++;
			}
		}
	}
	return tempArray;
};

/**
 * 从全局变量中读取数据字典的数据源
 * 
 * @param method
 *            方法名称
 * @param notEmpty
 *            表示有无请选择，为true时表示无请选择；
 * @param status
 *            状态位,(0表示查找未被删除的，null表示查找所有的)
 * @return Ext.data.ArrayStore 返回数组数据源
 */
dm.comm.comm_getSysCode = function(method, notEmpty, status) {

	var jsonData = "";
	if ("getEventType" == method) { // 获取事件类型
		jsonData = ERMS.GLOBAL.EVENTTYPE_JSON;
	}
	if ("getEventGrade" == method) { // 获取事件等级
		jsonData = ERMS.GLOBAL.EVENTGRADE_JSON;
	}
	if ("getReportMethod" == method) { // 获取报送方式
		jsonData = ERMS.GLOBAL.REPORTMETHOD_JSON;
	}

	if ("getMavinType" == method) { // 获得专家类型
		jsonData = ERMS.GLOBAL.MAVINGTYPE_JSON;
	}

	var len = jsonData.length;
	var dataArray = new Array();
	if (notEmpty) { // 不显示 "请选择"
		for (var i = 0; i < len; i++) {// 将json转化为数组
			if (status == "0") {
				if (jsonData[i].status == "0") {
					dataArray
							.push([jsonData[i].codeValue, jsonData[i].codeName]);
				}
			} else {
				dataArray[i] = [jsonData[i].codeValue, jsonData[i].codeName];
			}
		}
	} else {
		dataArray.push([' ', ES_COMM_SELECTTEXT]);
		for (var i = 0; i < len; i++) {// 将json转化为数组
			if (status == "0") {
				if (jsonData[i].status == "0") {
					dataArray
							.push([jsonData[i].codeValue, jsonData[i].codeName]);
				}
			} else {
				dataArray.push([jsonData[i].codeValue, jsonData[i].codeName]);
			}
		}
	}

	var sysCodeStore = new Ext.data.ArrayStore({
				id : 0,
				fields : ['codeValue', 'codeName'],
				data : dataArray
			});
	return sysCodeStore;
};

/**
 * 检查附件的后缀
 * 
 * @param filename
 *            文件名称
 * @param prefixArray
 *            允许上传的文件的后缀
 * @return 如果后缀符合条件则返回true，如果后缀不符合条件则返回true
 */
dm.comm.comm_checkFilePrefix = function(filename, prefixArray) {
	if ("" == filename) {// 文件名称为空
		return true;
	}
	if ("" == prefixArray.length) {// 无文件后缀限制
		return true;
	}
	var prefix = filename.substring(filename.lastIndexOf('.') + 1);
	for (var i = 0; i < prefixArray.length; i++) { // 循环校验文件类型
		if (prefix == prefixArray[i] || prefix == prefixArray[i].toUpperCase()
				|| prefix == prefixArray[i].toLowerCase()) { // 文件名称的后缀符合条件
			return true;
		}
	}
	return false;
};

/**
 * 按文件路径获取文件名
 * 
 * @param filePath
 *            文件路径
 * @return 返回文件名
 */
dm.comm.comm_getFileName = function(filePath) {
	var fileName = filePath.substring(filePath.lastIndexOf("\\") + 1,
			filePath.length);
	return fileName;
};

/**
 * 根据code获得对应的中文性别
 */
dm.comm.getSexByCode = function(code) {
	if (code == "0") { // 男
		return ES_COMPONENT_MAVINMAN;
	} else if (code == "1") { // 女
		return ES_COMPONENT_MAVINWOMAN;
	} else { // 未知
		return ES_COMPONENT_WHATSEX;
	}
};

/**
 * 
 * gridPanel浮动层提示
 */
dm.comm.getShowTip = function(str) {
	str = dm.comm.comm_replaceSpecialChar(str);

	var showTip = "<div>" + str + "</div>";
	var value1 = dm.comm.comm_replaceSpecialChar(showTip);
	var value2 = "<div style='word-break:break-all;'>" + value1 + "</div>";
	return '<div style="padding:3px;text-overflow:ellipsis;overflow: hidden;"><span ext:qtip="'
			+ value2 + '">' + str + '</span></div>';

};

/**
 * 获取数据字典公共方法
 * 
 * @param {}
 *            attribute
 * @return {}
 */
dm.comm.getNormalDictionaryStore = function(attribute) {

	if (contextPath == '') {
		var updateUserUrl = 'resource/dictionaryManage/getNormalKV?attribute=';
	} else {
		var updateUserUrl = contextPath
				+ '/resource/dictionaryManage/getNormalKV?attribute=';
	}

	var jsonStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : updateUserUrl + attribute,
							method : 'GET',
							async : false
						}),
				reader : new Ext.data.JsonReader({
							root : 'dictionaryBeanList'
						}, [{
									name : 'attributeValue'
								}, {
									name : 'description'
								}, {
									name : 'seq'
								}])
			});
	sortInfo : {
		field : "seq"
	};
	jsonStore.sort('seq', 'ASC');
	jsonStore.load();
	return jsonStore;
}

/**
 * 获取数据字典公共方法
 * 
 * @param {}
 *            attribute
 * @return {}
 */
dm.comm.getDictionaryStore = function(attribute) {

	/*if (contextPath && contextPath == '') {
		var updateUserUrl = 'resource/dictionaryManage/getKV?attribute=';
	} else {
		var updateUserUrl = contextPath
				+ '/resource/dictionaryManage/getKV?attribute=';
	}*/
	var updateUserUrl = 'resource/dictionaryManage/getKV?attribute=';
	var jsonStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : updateUserUrl + attribute,
							async : false
						}),
				reader : new Ext.data.JsonReader({
							root : 'dictionaryBeanList'
						}, [{
									name : 'attr_key'
								}, {
									name : 'attr_value'
								}, {
									name : 'remark'
								}, {
									name : 'seq'
								}])
			});
	sortInfo : {
		field : "seq"
	};
	jsonStore.sort('seq', 'ASC');
	//jsonStore.load();
	return jsonStore;
}


/*
 * 处理过长的字符串，截取并添加省略号 注：半角长度为1，全角长度为2
 * 
 * pStr:字符串 pLen:截取长度
 * 
 * return: 截取后的字符串
 */
dm.comm.autoAddEllipsis = function(pStr, pLen) {

	var _ret = dm.comm.cutString(pStr, pLen);
	var _cutFlag = _ret.cutflag;
	var _cutStringn = _ret.cutstring;

	if ("1" == _cutFlag) {
		return _cutStringn + "...";
	} else {
		return _cutStringn;
	}
}

/*
 * 取得指定长度的字符串 注：半角长度为1，全角长度为2 pStr:字符串 pLen:截取长度 return: 截取后的字符串
 */
dm.comm.cutString = function(pStr, pLen) {
	// 原字符串长度
	var _strLen = pStr.length;
	var _tmpCode;
	var _cutString;
	// 默认情况下，返回的字符串是原字符串的一部分
	var _cutFlag = "1";
	var _lenCount = 0;
	var _ret = false;
	if (_strLen <= pLen / 2) {
		_cutString = pStr;
		_ret = true;
	}
	if (!_ret) {
		for (var i = 0; i < _strLen; i++) {
			if (dm.comm.isFull(pStr.charAt(i))) {
				_lenCount += 2;
			} else {
				_lenCount += 1;
			}
			if (_lenCount > pLen) {
				_cutString = pStr.substring(0, i);
				_ret = true;
				break;
			} else if (_lenCount == pLen) {
				_cutString = pStr.substring(0, i + 1);
				_ret = true;
				break;
			}
		}
	}

	if (!_ret) {
		_cutString = pStr;
		_ret = true;
	}

	if (_cutString.length == _strLen) {
		_cutFlag = "0";
	}

	return {
		"cutstring" : _cutString,
		"cutflag" : _cutFlag
	};
}

/*
 * 判断是否为全角
 * 
 * pChar:长度为1的字符串 return: true:全角 false:半角
 * 
 */
dm.comm.isFull = function(pChar) {
	for (var i = 0; i < pChar.strLen; i++) {
		if ((pChar.charCodeAt(i) > 128)) {
			return true;
		} else {
			return false;
		}
	}
}

dm.comm.checkUserAgent = function(userAgent) {
	if (/msie/.test(userAgent)) {
		if (/msie 7/.test(userAgent)) {
			return 'IE7';
		} else if (/msie 8/.test(userAgent)) {
			return 'IE8';
		} else if (/msie 9/.test(userAgent)) {
			return 'IE9';
		} else if (/msie 10/.test(userAgent)) {
			return 'IE10';
		} else if (/msie 6/.test(userAgent)) {
			return 'IE6';
		} else {
			return 'IE';
		}
	} else if (/firefox/.test(userAgent)) {
		return 'Firefox';
	} else if (/chrome/.test(userAgent)) {
		return 'Chrome';
	} else if (/opera/.test(userAgent)) {
		return 'Opera';
	} else if (/safari/.test(userAgent)) {
		return 'Safari';
	}

};

dm.comm.hexToDec = function(str) {
	str = str.replace(/\\/g, "%");
	return unescape(str);
}

dm.comm.JsonToStr = function(o) {
	var str = '&';
	if (typeof o == 'object') {
		for (var p in o) {
			str = str + p + "=" + o[p] + "&"
		}
	}
	return str.substring(1, str.length - 1);
}

/**
 * 打印表单的公共方法 formType
 * 
 * @param {formType}1.不分栏2.上边分两栏下面不分栏3.上面不分栏中间分两栏下面不分栏
 * @param {thedivid}打印的form的子节点的前一个父节点
 * @param {firstLongNum}第一次出现长的的最后一个
 * @param {lastLongNum}第二次出现长的的第一个
 * @param {printFormFlag}打印标识
 * @param {formName}表单显示的名称
 * @return {}
 */
dm.comm.printForm = function(formType, thedivid, firstLongNum, lastLongNum,
		printFormFlag, formName) {
	if (formType == 3) {
		thedivid = thedivid.firstChild.firstChild.firstChild.firstChild;
	} else if (formType == 2) {
		thedivid = thedivid.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild;
	}
	LODOP = getLodop(document.getElementById('LODOP_OB'), document
					.getElementById('LODOP_EM'));
	LODOP.PRINT_INIT(printFormFlag + "form");
	LODOP.ADD_PRINT_TEXT(15, 35, 220, 20, "系统名称：生生世世");
	LODOP.ADD_PRINT_TEXT(15, 265, 230, 20, "打印时间："
					+ new Date().format("yyyy-MM-dd hh:mm:ss"));
	LODOP.ADD_PRINT_TEXT(15, 500, 150, 20, "打印人:" + ERMS.GLOBAL.STAFFNAME);
	LODOP.SET_PRINT_STYLEA(1, "FontSize", 8);
	LODOP.SET_PRINT_STYLEA(2, "FontSize", 8);
	LODOP.SET_PRINT_STYLEA(3, "FontSize", 8);
	LODOP.ADD_PRINT_TEXT(40, 300, 250, 20, formName);
	LODOP.SET_PRINT_STYLEA(4, "FontSize", 13);
	LODOP.SET_PRINT_STYLEA(4, "Bold", 1);
	var strStyleCSS = "<link href='ext/resources/css/ext-all.css' type='text/css' rel='stylesheet'>";
	if (firstLongNum == lastLongNum) {
		var strBodyStyle = strStyleCSS
				+ document.getElementById(thedivid.id).innerHTML;
		LODOP.ADD_PRINT_HTM(88, 40, "100%", "100%", strBodyStyle);
	} else {
		lastLongNum = lastLongNum - 1;
		for (var i = 0; i < thedivid.childNodes.length - 1; i++) {
			if (i < firstLongNum) {
				// thedivid.childNodes[i].style.cssFloat = "left";

				var strBodyStyle = strStyleCSS
						+ document.getElementById(thedivid.childNodes[i].id).innerHTML;
				LODOP.ADD_PRINT_HTM(88 + 35 * i, 40, "100%", "100%",
						strBodyStyle);
			} else if (i >= firstLongNum && (i - firstLongNum) % 2 == 0
					&& i < lastLongNum) {
				// thedivid.childNodes[i].style.cssFloat = "left";
				var strBodyStyle = strStyleCSS
						+ document.getElementById(thedivid.childNodes[i].id).innerHTML;
				LODOP.ADD_PRINT_HTM(88 + 30 * (i - firstLongNum) / 2 + 35
								* firstLongNum, 40, "50%", "50%", strBodyStyle);
			} else if (i >= lastLongNum
					&& (lastLongNum - firstLongNum) % 2 == 1) {
				// thedivid.childNodes[i].style.cssFloat = "left";
				var strBodyStyle = strStyleCSS
						+ document.getElementById(thedivid.childNodes[i].id).innerHTML;
				LODOP.ADD_PRINT_HTM(88 + 30 * (lastLongNum - firstLongNum + 1)
								/ 2 + 35 * firstLongNum + 50
								* (i - lastLongNum), 40, "100%", "100%",
						strBodyStyle);
			} else if (i >= lastLongNum
					&& (lastLongNum - firstLongNum) % 2 == 0) {
				// thedivid.childNodes[i].style.cssFloat = "left";
				var strBodyStyle = strStyleCSS
						+ document.getElementById(thedivid.childNodes[i].id).innerHTML;
				LODOP.ADD_PRINT_HTM(88 + 30 * (lastLongNum - firstLongNum) / 2
								+ 35 * firstLongNum + 50 * (i - lastLongNum),
						40, "100%", "100%", strBodyStyle);
			} else if (i > firstLongNum && (i - firstLongNum) % 2 == 1
					&& i < lastLongNum) {
				// thedivid.childNodes[i].style.cssFloat = "left";
				var strBodyStyle = strStyleCSS
						+ document.getElementById(thedivid.childNodes[i].id).innerHTML;
				LODOP
						.ADD_PRINT_HTM(88 + 30 * (i - 1 - firstLongNum) / 2
										+ 35 * firstLongNum, 295, "50%", "50%",
								strBodyStyle);
			}
		}
	}
	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4")
	// LODOP.PRINT_DESIGN();
	LODOP.PREVIEW();

}

/**
 * 打印表格的公共方法
 * 
 * @param {thedivid}打印的表格的div的id
 * @param {printGridFlag}标识。相当于表格的id，随便写，但每类的不要重复
 * @param {gridName}表格的显示名称
 * @return {}
 */
dm.comm.printGrid = function(thedivid, printGridFlag, gridName, direct) {
	if (direct && direct == true) {

	} else {
		thedivid = thedivid.firstChild.childNodes[1].firstChild.firstChild;
	}
	var dayin1 = thedivid.firstChild.id;

	var dayin2 = thedivid.lastChild.firstChild;

	LODOP = getLodop(document.getElementById('LODOP_OB'), document
					.getElementById('LODOP_EM'));
	var strStyleCSS = "<link href='ext/resources/css/ext-all.css' type='text/css' rel='stylesheet'>";
	var strBodyStyle = "<style>table,td,th { border: 1 solid #000000;border-collapse:collapse }</style>";

	LODOP.PRINT_INIT(printGridFlag + "grid");
	LODOP.ADD_PRINT_TEXT(45, "35%", "80%", "80%", gridName);
	LODOP.SET_PRINT_STYLEA(1, "FontSize", 13);
	LODOP.SET_PRINT_STYLEA(1, "Bold", 1);
	if (dayin2.childNodes.length >= 40) {
		for (var j = 0; j < dayin2.childNodes.length / 40; j++) {

			var strFormHtml1 = strBodyStyle
					+ document.getElementById(dayin1).innerHTML;
			for (var i = 40 * j; i < (40 + 40 * j)
					&& (i < dayin2.childNodes.length); i++) {
				dayin2.childNodes[i].id = "dayin2" + i;
				strFormHtml1 = strFormHtml1
						+ document.getElementById(dayin2.childNodes[i].id).innerHTML;
			}
			LODOP.ADD_PRINT_HTM(85, "2%", "100%", "100%", strFormHtml1);
			LODOP.ADD_PRINT_TEXT(15, 35, 220, 20, "系统名称：sss ");
			LODOP.ADD_PRINT_TEXT(15, 265, 230, 20, "打印时间："
							+ new Date().format("yyyy-MM-dd hh:mm:ss"));
			LODOP.ADD_PRINT_TEXT(15, 500, 150, 20, "打印人:"
							+ ERMS.GLOBAL.STAFFNAME);
			LODOP.NEWPAGEA();
		}
	} else {
		var strFormHtml1 = strBodyStyle
				+ document.getElementById(dayin1).innerHTML;
		strFormHtml1 = strFormHtml1
				+ document.getElementById(dayin2.id).innerHTML;
		LODOP.ADD_PRINT_HTM(85, "2%", "100%", "100%", strFormHtml1);
		LODOP.ADD_PRINT_TEXT(15, 35, 220, 20, "系统名称：ss");
		LODOP.ADD_PRINT_TEXT(15, 265, 230, 20, "打印时间："
						+ new Date().format("yyyy-MM-dd hh:mm:ss"));
		LODOP.ADD_PRINT_TEXT(15, 500, 150, 20, "打印人:" + ERMS.GLOBAL.STAFFNAME);
	}
	// LODOP.ADD_PRINT_HTM(150, "5%", "100%", "100%", strFormHtml1);

	LODOP.SET_PRINT_MODE("PRINT_PAGE_PERCENT", "Auto-Width");
	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4")
	// LODOP.SET_PREVIEW_WINDOW(0, 0, 0, 0, 0, "");
	LODOP.PREVIEW();
}

/**
 * 
 * @param {}
 *            nodeCount 打印节点数量 小于0，表示向上打印 大于0表示向下打印 如-1 表示向上打印1节点，1表示向下打印1节点
 * @param {}
 *            formObj 打印from对象
 * @param {}
 *            printFormFlag 标示
 * @param {}
 *            formName 打印名称
 * @param {}
 *            specialFlag 特殊处理元数据标准属性表格
 */
dm.comm.viewForm2Print = function(nodeCount, formObj, printFormFlag,
		formName, specialFlag) {
	// 查找子节点
	if (nodeCount > 0) {
		for (var i = 0; i < nodeCount; i++) {
			formObj = formObj.firstChild;
		}
	}
	// 查找父节点
	if (nodeCount < 0) {
		for (var i = 0; i < Math.abs(nodeCount); i++) {
			formObj = formObj.parentNode;
		}
	}
	// 获取需要打印的HTML内容
	var printHTML = "";
	if (specialFlag) {
		printHTML = ERMS.recordsAttributes.printSpecialForm(formObj);
	} else {
		printHTML = document.getElementById(formObj.id).outerHTML;
	}
	// 默认添加Extjs的css
	var cssStyle = "<link href='ext/resources/css/ext-all.css' type='text/css' rel='stylesheet'>";
	// 拼装打印的HTML内容(包含样式)
	var printStr = cssStyle + printHTML;
	// 获取当前时间
	var currentTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	var systemName = "系统名称：sss";
	LODOP = getLodop(document.getElementById('LODOP_OB'), document
					.getElementById('LODOP_EM'));
	LODOP.PRINT_INIT(printFormFlag + "form");
	LODOP.ADD_PRINT_TEXT(15, 35, 220, 20, systemName);
	LODOP.ADD_PRINT_TEXT(15, 265, 230, 20, "打印时间：" + currentTime);
	LODOP.ADD_PRINT_TEXT(15, 500, 150, 20, "打印人:" + ERMS.GLOBAL.STAFFNAME);
	LODOP.SET_PRINT_STYLEA(1, "FontSize", 8);
	LODOP.SET_PRINT_STYLEA(2, "FontSize", 8);
	LODOP.SET_PRINT_STYLEA(3, "FontSize", 8);
	LODOP.ADD_PRINT_TEXT(40, 300, 250, 20, formName);
	LODOP.SET_PRINT_STYLEA(4, "FontSize", 13);
	LODOP.SET_PRINT_STYLEA(4, "Bold", 1);
	LODOP.ADD_PRINT_HTM(88, 40, "100%", "100%", printStr);
	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4")
	// LODOP.PRINT_DESIGN();
	LODOP.PREVIEW();
}

Date.prototype.format = function(format) {
    /*
     * eg:format="yyyy-MM-dd hh:mm:ss";
     */
    var o = {
        "M+" :this.getMonth() + 1, // month
        "d+" :this.getDate(), // day
        "h+" :this.getHours(), // hour
        "m+" :this.getMinutes(), // minute
        "s+" :this.getSeconds(), // second
        "q+" :Math.floor((this.getMonth() + 3) / 3), // quarter
        "S" :this.getMilliseconds()
    }

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
    }

    for ( var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                    : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

