afApp.controller("testSuiteController",["$scope", "$http", "$rootScope","$location", function($scope, $http, $rootScope, $location) {
	
	var pNo = 0;
	$scope.testSuites = [];
	$scope.last = false;
	  $scope.loadMore = function() {
		
			  var promise = getTestSuites(pNo);
				promise.then(
						function success(response) {
					
		console.log(response);
			      for (var i = 0; i < response.data.content.length; i++) {
			    	  $scope.testSuites.push(response.data.content[i]);
			    	 
			      }
				 
				 if(pNo<response.data.totalPages)
					 pNo= pNo+1;
					 else{
						 $scope.last = true;
			     pNo = 0;}	 
				},
				function error(response){
					
				});
		   }
	
		  
	 var getTestSuites = function(pNo){
		return $http.get("getTestSuites?pNo="+pNo+"&pSize=9&SuiteName=&projectCode="+$rootScope.selectedProject.projectCode)
		//.success(function(response){debugger
			//$scope.testSuites = response.content;
	//});
	
	}
	$scope.$on('projectChanged', function(){	
		pNo=0;
	$scope.testSuites = [];
	$scope.loadMore();
	})

$scope.createSuite = function(testSuite){
	postObj={
			testSuites: [testSuite]
		}

		$http.post("default/save?testSuiteId=&projectCode="+$rootScope.selectedProject.projectCode+"&testCaseId=",postObj).success(function(data){
		console.log(data);
		$("#createSuiteModal").modal('hide');
		})
}
	$scope.editTestSuite= function(index){
		$scope.testSuite= $scope.testSuites[index];
	}
	
	$scope.addCases = function(testSuite){
		$scope.createSuite(testSuite);
		var promise = getTestSuites(0);
		promise.then(function onSuccess(response){
			var suiteId = response.data.content[0].id;
			$location.url("/cases/"+suiteId);
		})
	}
	$scope.modalClose = function(){
		$scope.testSuite = "";
	}

}])
