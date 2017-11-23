var app = angular.module('evaluationApp', []);

app.filter('entityFilter',function(){
	return function(input,evaluationType){
		if(undefined==input){
			return;
		}
		var output=[];
		for(i in input){
			if(input[i].evaluationType == evaluationType){
				output.push(input[i]);
			}
		}
		return output;
	}
});
app.controller('evaluationCtrl', function($scope,$http,$q) {
	
    $scope.load = function(){
      $http({
        method:"POST",
        url:"/elab/resource/EvaluationService/query"
      }).success(function(data, status, headers, config){
    	  $scope.record1 = data.list[0];
          $scope.record2 = data.list[1];
          $scope.record3 = data.list[2];
          $scope.record4 = data.list[3];
          
      }).error(function(data, status, headers, config){
          //alert("获取数据失败");
      })
    };
    
    $scope.modifyEval=function(record){
    	$http({
	        method:"POST",
	        url:"/elab/resource/EvaluationService/modify",
	        params:record
	      }).success(function(data, status, headers, config){
	    	  
	    	  alert("修改成功！");
	      }).error(function(data, status, headers, config){
	          //alert("获取数据失败");
	      })
    };
    
    $scope.modifyEntity=function(record){
    	$http({
	        method:"POST",
	        url:"/elab/resource/EvaluationService/modifyEntity",
	        params:record
	      }).success(function(data, status, headers, config){
	    	 
	    	  alert("修改成功！");
	      }).error(function(data, status, headers, config){
	          //alert("获取数据失败");
	      })
    };
    
    $scope.load();
});