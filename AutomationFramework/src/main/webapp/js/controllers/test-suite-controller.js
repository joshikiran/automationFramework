afApp.controller("testSuiteController",["$scope", "$http", "$rootScope", function($scope, $http, $rootScope) {
	$scope.$on('projectChanged', function(ev, data){debugger
	$http.get("getTestSuites?pNo=0&pSize=10&SuiteName=&projectCode="+data).success(function(response){debugger
			$scope.testSuites = response.content;
	});
	})
	
	

$scope.createSuite = function(testSuite){debugger
	postObj={
			testSuites: [testSuite]
		}

		$http.post("default/save?testSuiteId=&projectCode="+$rootScope.selectedProject.projectCode+"&testCaseId=",postObj).success(function(data){
		console.log(data);
		$("#createSuiteModal").modal('hide');
		})
}
	

}])
