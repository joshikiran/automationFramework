afApp.directive("myDir", function($http,$routeParams) {
  return {
    restrict: 'E',
    scope: {
      index: "@",
      testSteps: "=",
    },
    templateUrl: 'my-template.html',
    controller: function($scope, $sce) {

    	$http.get("default/getMethods?methodName=").success(function(response){
    		$scope.methods = response;
    	}).error(function(){
    		
    	})
      $scope.returnHTML = function(value) {
        return $sce.trustAsHtml(value);
      }
    },
    link: function(scope, element, attrs) {
      
      
scope.getParams = function(method) {
    	 scope.stepDetails = [];
    	/* scope.testCase.testSteps[attrs.index]={
    	 		stepDetails : stepDetails
    	 		};*/
        scope.fields = [];
        
        if(method==null)
        return scope.fields;

       /* scope.testCase.testSteps[attrs.index].stepDetails[0]={
          fieldValue: method.methodName,
          testCaseName : scope.testCase.testCaseName,
          testCaseDescription : scope.testCase.testCaseDescription,

        };*/
        scope.stepDetails[0]={
                fieldValue: method.methodName,
//                testCaseName : scope.testCase.testCaseName,
//                testCaseDescription : scope.testCase.testCaseDescription,

              };
        params = method.params
        var numberOfParams = params.length;
        angular.forEach(params, function(val, idx) {
 /* scope.testCase.testSteps[attrs.index].stepDetails.splice(parseInt(idx + 1), 0, {
            paramName: val.paramName,
            paramDescription: val.paramDescription,
            testCaseName : scope.testCase.testCaseName,
            testCaseDescription : scope.testCase.testCaseDescription,
          });*/
        	scope.stepDetails.push({
            paramName: val.paramName,
            paramDescription: val.paramDescription,
//            testCaseName : scope.testCase.testCaseName,
//            testCaseDescription : scope.testCase.testCaseDescription,
          })
        text = '<label>' + val.paramName + '</label><input ng-model="stepDetails[' + parseInt(idx + 1) + '].fieldValue" placeholder="' + val.paramDescription + '">';
          scope.fields.push(text);
          
          return scope.fields;
        })
      }

  /*    scope.remove = function() {
        debugger
        scope.testCase.testSteps.splice(attrs.index, 1);
        scope.methodModel=undefined;
        scope.getParams(null);  
      }
      
      scope.add= function(){debugger
          scope.testCase.testSteps.splice(parseInt(attrs.index+1), 0, {stepDetails : []});
        }*/
scope.saveTestStep= function(){debugger
	testStep={}
	testStep.stepDetails = scope.stepDetails
	
	postObj={
	testSteps: [testStep]
}

$http.post("default/save?testSuiteId=1&projectCode=DefaultProject&testCaseId="+$routeParams.id,postObj).success(function(data){
console.log(data);
scope.testSteps.push(testStep);
})
 }
    }
  }
})

afApp.directive('compile', function($compile, $timeout) {
  return {
    restrict: 'A',
    link: function(scope, elem, attrs) {
      $timeout(function() {

        $compile(elem.contents())(scope);
      });
    }

  }
});