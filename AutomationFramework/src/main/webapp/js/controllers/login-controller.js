afApp.controller("loginController", ["$scope", "$rootScope", "$http", "$location", function($scope, $rootScope, $http, $location){
	
$scope.authenticate = function(credentials){
		$rootScope.authenticated=false;
		$rootScope.isUser=false;
		$rootScope.isAdmin=false;
	
		if(credentials && credentials.userName && credentials.password)
		var headers= {'Authorization': "Basic " + btoa(credentials.userName + ":" + credentials.password)};
		else
			headers={};
		  $http.get("usermanagement/login",{
			  headers:headers
		  }).success(function (data) {
		  	if(data && data!="")
		  	{
		  		
		  		$rootScope.authenticated=true;
	    	  userRoles=data.userRoles;
	    	  angular.forEach(userRoles,function(value,index){
	    		  if(value.role=="ROLE_ADMIN")
	    			  $rootScope.isAdmin=true; 
	    		  else if(value.role=="ROLE_USER")
	    			  $rootScope.isUser=true;
		  	})
		if($location.path()=='/login')
			$location.url("/suite");
			else
		  	$location.path();
		  	}
	    	  else
	    	  { 
	    		$rootScope.authenticated=false;
	    		$rootScope.isUser=false;
	    		$rootScope.isAdmin=false;
	    		$location.url("/login")
	    	  }
	    	  
		  }).error(function(data){
			  $rootScope.authenticated=false;
	    		$rootScope.isUser=false;
	    		$rootScope.isAdmin=false;
	    		$location.url("/login")

		  })
		}

		
		$scope.authenticate(null);
	
		
		/*$scope.getTestSuites= function(){
			$http.get("getTestSuites?pNo=0&pSize=10&SuiteName=&projectCode="+$scope.selectedProject.projectCode).success(function(response){debugger
					$scope.testSuites = response.content;
			});
		}*/
	
/*	$http.post("logout").success(function(data){
		$location.url("/login");
		debugger
	}).error(function(data){
		debugger
	});*/
}])