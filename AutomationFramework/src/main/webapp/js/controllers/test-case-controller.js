afApp.controller("testCaseController",["$scope", "$http","$routeParams","$rootScope", "$location", function($scope, $http, $routeParams,$rootScope, $location) {

var getTestCases = function(){
return $http.get("getTestCases?pNo=0&pSize=10&caseName=&testSuiteId="+$routeParams.id+"&projectCode=DefaultProject");
}
 var promise = getTestCases();
 promise.then(
		 function success(response){
			 $scope.testCases = response.data.content; 
		 })
$scope.createCase = function(testCase){
	postObj={
			testCases: [testCase]
		}

		$http.post("default/save?testSuiteId="+$routeParams.id+"&projectCode="+$rootScope.selectedProject.projectCode+"&testCaseId=",postObj).success(function(data){
		$("#createCaseModal").modal('hide');
		 var promise = getTestCases();
		 promise.then(
				 function success(response){
					 $scope.testCases = response.data.content; 
				 })
		})
}

$scope.editTestCase= function(index){
	$scope.testCase= $scope.testCases[index];
}

$scope.addSteps = function(testCase){
	$scope.createCase(testCase);
	 var promise = getTestCases();
	 promise.then(
			 function success(response){
			
		var caseId = response.data.content[response.data.content.length-1].id;
		$location.url("/steps/"+caseId);
	})
}
$scope.modalClose = function(){debugger
$scope.testCase = "";
}

}])