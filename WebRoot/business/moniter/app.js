
Date.prototype.format = function(fmt){
  var o = {
    "M+" : this.getMonth()+1,                 //月份
    "d+" : this.getDate(),                    //日
    "h+" : this.getHours(),                   //小时
    "m+" : this.getMinutes(),                 //分
    "s+" : this.getSeconds(),                 //秒
    "q+" : Math.floor((this.getMonth()+3)/3), //季度
    "S"  : this.getMilliseconds()             //毫秒
  };
  if(/(y+)/.test(fmt))
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
  for(var k in o)
    if(new RegExp("("+ k +")").test(fmt))
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
  return fmt;
};

function formatSeconds(value) {
    var theTime = parseInt(value);// 秒
    var theTime1 = 0;// 分
    var theTime2 = 0;// 小时
    if(theTime > 60) {
        theTime1 = parseInt(theTime/60);
        theTime = parseInt(theTime%60);
            if(theTime1 > 60) {
            theTime2 = parseInt(theTime1/60);
            theTime1 = parseInt(theTime1%60);
            }
    }
        var result = ""+parseInt(theTime)+"秒";
        if(theTime1 > 0) {
        result = ""+parseInt(theTime1)+"分"+result;
        }
        if(theTime2 > 0) {
        result = ""+parseInt(theTime2)+"小时"+result;
        }
    return result;
}
var app = angular.module('pileMoniter', []);


app.directive('repeatDone', function() {
    return function(scope, element, attrs) {
        if (scope.$last) { // all are rendered
            scope.$eval(attrs.repeatDone);
        }
    }
});

app.controller('pileCtrl', function($scope,$http,$q) {
	
    $scope.load = function(){
      var deferred = $q.defer();
      var pileDriverNumber =getQueryString("pileDriverNumber");
      $http({
        method:"GET",
        url:"/elab/resource/PileMoniterService/querySectionAmount",
        params:{
        	pileDriverNumber:pileDriverNumber
        }
      }).success(function(data, status, headers, config){
          var output= [];
          
          var drivers = [];
          if(data.list!=null){
          for(var i=0;i<data.list.length;i++){
            var o = data.list[i];
            o.startTime = new Date(o.startTime).format("yyyy-MM-dd hh:mm:ss");
            o.gunitingSecond = formatSeconds(o.gunitingSecond);
            o.averageLiquid = o.totalLiquid/o.pileLength;
            o.averageCement = o.totalCement/o.pileLength;
            o.averageLiquid = toDecimal(o.averageLiquid);
            o.averageCement = toDecimal(o.averageCement);
            o.status==1?o.status='下钻':o.status='提钻';

            o.chart1=o.pileNumber+'_'+1;
            o.chart2=o.pileNumber+'_'+2;
            o.chart3=o.pileNumber+'_'+3;
            o.chart4=o.pileNumber+'_'+4;
            output.push(o);
            drivers.push(o.pileDriverNumber);
            console.info(o.pileDriverNumber+" ; "+o.pileNumber+" ; "+o.speed+" ; "+o.flow+" ; "+o.maxOutsidePower+" ; "+o.lean);
          }
          }
          $scope.records = angular.copy(output);
          try{
	          for(var j=0;j<$scope.records.length;j++){
	  	        printEcharts($scope.records[j]);
	  	      }
          }catch(e){
        	  
          };
          deferred.resolve(output);
          return  deferred.promise;
      }).error(function(data, status, headers, config){
          //alert("获取数据失败");
      })
    }
    $scope.load();
    setInterval(function () {
        $scope.load();
    },10000);
    $scope.load();
    /*$scope.layoutDone = function(){
    	for(var j=0;j<$scope.records.length;j++){
	        printEcharts($scope.records[j]);
	     }
    }*/
});
