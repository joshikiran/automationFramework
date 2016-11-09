afApp.controller("homeController",["$scope", "$http","$routeParams", function($scope, $http, $routeParams) {


	
/*  $scope.size = 1;
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
}*/
$scope.crumbs = [{ "name" :"Suite Name",
	"href" : ""}, 
	{"name" : "Case Name",
		"href" : ""}
	
]
$scope.setActive = function(index){
	$scope.clicked = index;
}

$scope.testSteps=[
        {
            "id": "0db6a9bd-e781-44b4-85e1-cd8088995795",
            "createdOn": null,
            "createdBy": null,
            "modifiedOn": null,
            "modifiedBy": null,
            "additional1": null,
            "additional2": null,
            "additional3": null,
            "additional4": null,
            "additional5": null,
            "order": 4,
            "testCaseName": "ScriptGenerated",
            "testCaseDescription": "ScriptGenerated",
            "testStepRef": null,
            "stepDetails": [
              {
                "id": "03ebdc80-cfab-434b-bca2-c55b14624286",
                "createdOn": null,
                "createdBy": null,
                "modifiedOn": null,
                "modifiedBy": null,
                "additional1": null,
                "additional2": null,
                "additional3": null,
                "additional4": null,
                "additional5": null,
                "paramName": "methodName",
                "paramDescription": "methodDescp",
                "fieldValue": "myId2",
                "order": 1,
                "className": null,
                "active": true
              },
              {
                "id": "07c2610c-3bd8-41b0-bff0-f6d0590ddd9a",
                "createdOn": null,
                "createdBy": null,
                "modifiedOn": null,
                "modifiedBy": null,
                "additional1": null,
                "additional2": null,
                "additional3": null,
                "additional4": null,
                "additional5": null,
                "paramName": "paramname",
                "paramDescription": "param desc",
                "fieldValue": "clickElementById",
                "order": 2,
                "className": null,
                "active": true
              },
              {
                  "id": "07c2610c-3bd8-41b0-bff0-f6d0590dd435a",
                  "createdOn": null,
                  "createdBy": null,
                  "modifiedOn": null,
                  "modifiedBy": null,
                  "additional1": null,
                  "additional2": null,
                  "additional3": null,
                  "additional4": null,
                  "additional5": null,
                  "paramName": "paramname1",
                  "paramDescription": "param desc1",
                  "fieldValue": "clickElementById",
                  "order": 2,
                  "className": null,
                  "active": true
                }
            ],
            "active": true
          }
        ]
}])