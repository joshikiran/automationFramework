afApp.controller("testStepController",["$scope", "$http","$routeParams", function($scope, $http, $routeParams) {debugger

 var getTestSteps = function(){
$http.get("getTestSteps?pNo=0&pSize=10&testCaseId="+$routeParams.id+"&projectCode="+$rootScope.selectedProject.projectCode+"&searchParam=").success(function(response){debugger
		$scope.testSteps = response.content;
	});
}


//$scope.size = 1;
 /* $scope.options = [1, 5, 10];
 $scope.testCase = {id : $routeParams.id};
 $scope.testStep={
		 
		 "createdOn": null,
		 "createdBy": null,
		 "modifiedOn": null,
		 "modifiedBy": null,
		 "additional1": null,
		 "additional2": null,
		 "additional3": null,
		 "additional4": null,
		 "additional5": null,
	
		 "order": 1,
		 "testCaseName": "ScriptGenerated",
		 "testCaseDescription": "ScriptGenerated",
		 "testStepRef": null,
		 "stepDetails": [
		 {
		
		 "createdOn": null,
		 "createdBy": null,
		 "modifiedOn": null,
		 "modifiedBy": null,
		 "additional1": null,
		 "additional2": null,
		 "additional3": null,
		 "additional4": null,
		 "additional5": null,
		 "paramName": "ScriptGenerated",
		 "paramDescription": "ScriptGenerated",
		 "fieldValue": "openUrl",
		 "order": 2,
		 "className": null,
		 "active": true
		 },
		 {
		
		 "createdOn": null,
		 "createdBy": null,
		 "modifiedOn": null,
		 "modifiedBy": null,
		 "additional1": null,
		 "additional2": null,
		 "additional3": null,
		 "additional4": null,
		 "additional5": null,
		 "paramName": "ScriptGenerated",
		 "paramDescription": "ScriptGenerated",
		 "fieldValue": "http://219.65.70.150/trex/#/login?redirect=%2F",
		 "order": 3,
		 "className": null,
		 "active": true
		 }
		 ],
		 "active": true
		 };*/
//  $scope.getNumber = function(size) {
//    $scope.testCase.testSteps = new Array(size);
//    
//    return $scope.testCase.testSteps;
//  }
//  $scope.getNumber($scope.size); 
//  
//  $scope.addMore = function(opt) {
//   // $scope.size = $scope.size + parseInt(opt);
//    //$scope.testCase.testSteps.splice($scope.size,opt) 
//   // abc = new Array(5);
//  $scope.testCase.testSteps.push({stepDetails : []});
//     
//  }
 $scope.saveTestStep= function(){
	postObj={
	testSteps: [$scope.testStep]
}

$http.post("default/save?testSuiteId=&projectCode="+$rootScope.selectedProject.projectCode+"&testCaseId="+$routeParams.id,postObj).success(function(data){
	$('testStepModal').modal('hide');
	getTestSteps();
	
})
 }
}])