afApp.controller("testCaseController",["$scope", "$http","$routeParams", function($scope, $http, $routeParams) {


$http.get("getTestCases?pNo=0&pSize=10&caseName=&testSuiteId="+$routeParams.id+"&projectCode=DefaultProject").success(function(response){debugger
		$scope.testCases = response.content;
	});

}])