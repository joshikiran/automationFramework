afApp.controller("homeController",["$scope", "$http", function($scope, $http) {

	$http.get("default/getMethods").success(function(response){
		$scope.methods = response;
	}).error(function(){
		
	})
  $scope.size = 2;
  $scope.options = [1, 5, 10];
 $scope.testCase = {};
 
  $scope.getNumber = function(size) {
    $scope.testCase.testSteps = new Array(size);
    
    return $scope.testCase.testSteps;
  }
  $scope.getNumber($scope.size); 
  
  $scope.addMore = function(opt) {
   // $scope.size = $scope.size + parseInt(opt);
    //$scope.testCase.testSteps.splice($scope.size,opt) 
   // abc = new Array(5);
  $scope.testCase.testSteps.push({stepDetails : []});
     
  }

$scope.saveTestCase= function(){
postObj={
  		testCases : [$scope.testCase]
  	}
  	
  	$http.post("default/save",postObj).success(function(data){
  		console.log(data);
  	})
}
$scope.crumbs = [{ "name" :"Suite Name",
	"href" : ""}, 
	{"name" : "Case Name",
		"href" : ""}
	
]
}])