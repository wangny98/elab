function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
};

Date.prototype.format = function(format){
    var o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(), //day
        "h+" : this.getHours(), //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3), //quarter
        "S" : this.getMilliseconds() //millisecond
    };

    if(/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }

    for(var k in o) {
        if(new RegExp("("+ k +")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
        }
    }
    return format;
};

function authority(){
	$.ajax( {
        url:'login/getUserInfo.do',// 跳转到 action
        async:false,
        type:'post',
        data:{
            dayNum:0
        },
        cache:false,
        dataType:'json',
        success:function(data) {
            if(data.isSuper=='true'){
                window.location.href = "charts2.html";
            }else{
                window.location.href = "charts3.html";
            }
        },
        error : function() {
        	
        }
    });
};

function formateLine(category,list){
    var lineCategory = new Array();
    var bdName =[];
    var bdObj = [];
    for(var i=0;i<list.length;i++){
        if(bdName.indexOf(list[i].bdname)<0){
            bdName.push(list[i].bdname);
            var obj = new Object();
            obj.name = list[i].bdname;
            obj.water=[],obj.cement=[],obj.zz=[];
            obj.water.push(list[i].water/1000);
            obj.cement.push(list[i].cement/1000);
            obj.zz.push(list[i].zz/1000);
            bdObj.push(obj);
        }else{
            //console.info("index : ",category.indexOf(list[i].jbTimeStr))
            //console.info("last + 1 : ",bdObj[bdObj.length-1].water.length)
            if(lineCategory.indexOf(list[i].jbTimeStr)==bdObj[bdObj.length-1].water.length){
                bdObj[bdObj.length-1].water.push(list[i].water/1000);
                bdObj[bdObj.length-1].cement.push(list[i].cement/1000);
                bdObj[bdObj.length-1].zz.push(list[i].zz/1000);
            }else{
                bdObj[bdObj.length-1].water.push("-");
                bdObj[bdObj.length-1].cement.push("-");
                bdObj[bdObj.length-1].zz.push("-");
            }
        }
    }
};

function createLineSeries(dataArray,name,color){
    var series = new Object();
    series.name = name;
    series.type="line";
    series.data = dataArray;
    var lineStyle = new Object();
    lineStyle.color=color;
    series.lineStyle=lineStyle;
    return series;
};

function refreshLocation()
{
    window.location.reload();
};

function array2Map(array,time,value){
    var obj = new Object();
    for(var i=0;i<array.length;i++){
        obj[array[i][time]] = array[i][value];
    }
    return obj;
};


function formatLine(data){
	var obj = new Object();
	var theoryline = [];
    var truthline = [];
    var category = [];
    
    category = data.category;

    var  mapTheory = array2Map(data.theory,"jbTimeStr","cement");
     for(var i=0;i<data.category.length;i++){
         if(mapTheory[data.category[i]]){
             theoryline.push((mapTheory[data.category[i]]));
         }else{
             theoryline.push(0);
         }
     }

     for(var j=0;j<data.truth.length;j++){
         truthline.push(data.truth[j].cement);
     }
     obj.bdName = data.bdName;
     obj.category = category;
     obj.theory = theoryline;
     obj.truth = truthline;
     return obj;
}