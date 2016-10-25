afApp.controller("loginController", ["$scope", "$rootScope", "$http", "$location", function($scope, $rootScope, $http, $location){
	
$scope.authenticate = function(){
		$rootScope.authenticated=false;
		$rootScope.isUser=false;
		$rootScope.isAdmin=false;
	
		if($scope.userName && $scope.password)
		var headers= {'Authorization': "Basic " + btoa($scope.userName + ":" + $scope.password)};
		else
			headers={};
		  $http.get("usermanagement/login",{
			  headers:headers
		  }).success(function (data) {
		  	if(data && data!="")
		  	{
		  		 user = {"userName" : "vysh",
		  				 "password" : "vyshu"};
		  		 //$http.post("usermanagement/changePassword?newPassword=vyshu",user)
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

		
		$scope.authenticate();
	
	
	//$http.post("logout");
}])