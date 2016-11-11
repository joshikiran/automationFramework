
var afApp = angular.module("afApp",["ngRoute"]);

afApp.config(function($routeProvider,$httpProvider){
	 $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	$routeProvider.when("/login",{
		templateUrl:"login.html",
	}).when("/home",{
		templateUrl:"home.html",
	}).when("/suite",{
		templateUrl:"test-suite.html",
	}).when("/cases",{
		templateUrl:"test-case.html",
	}).when("/cases/:id",{
		templateUrl:"test-case.html",
	}).when("/steps/:id",{
		templateUrl:"test-step.html",
	}).otherwise("/")
})

afApp.run(function($rootScope,$http,$location,$timeout){

	$rootScope.$on('alertProjectChange', function(ev, data){debugger
		 $timeout(function(){
	$rootScope.$broadcast('projectChanged',data);
		  });
	})

	
	
/*	$rootScope.testCases=[{ 
			"active" : true,
			"template" : true,
			"order" : 1,
			"testCaseDescription" : "Sample Description",
			"testCaseName" : "Test Case Title 1",
			"testCaseReference" : "df",
		},
		{ 
			"active" : true,
			"template" : true,
			"order" : 1,
			"testCaseDescription" : "Sample Description",
			"testCaseName" : "Test Case Title 2",
			"testCaseReference" : "df",
		},
		{ 
			"active" : true,
			"template" : true,
			"order" : 1,
			"testCaseDescription" : "Sample Description",
			"testCaseName" : "Test Case Title 3",
			"testCaseReference" : "df",
		}]*/
/*	$rootScope.testsuite=[{ 
		"id" : "0aa3ccb2-03ff-45aa-8ce1-41c837912975",
		"active" : true,
		"suiteDescription" : "Sample Description",
		"suiteName" : "New Test Suite",
		"testSuiteReference" : "",
		"testCases" : $rootScope.testCases
	},
	{ 
		"id" : "0aa3ccb2-03ff-45aa-8ce1-41c837912975",
		"active" : true,
		"suiteDescription" : "Sample Description",
		"suiteName" : "New Test Suite 2",
		"testSuiteReference" : "",
		
	},
	{ 
		"id" : "0aa3ccb2-03ff-45aa-8ce1-41c837912975",
		"active" : true,
		"suiteDescription" : "Sample Description",
		"suiteName" : "New Test Suite 3",
		"testSuiteReference" : "",
		
	},
	{ 
		"id" : "0aa3ccb2-03ff-45aa-8ce1-41c837912975",
		"active" : true,
		"suiteDescription" : "Sample Description",
		"suiteName" : "New testSuite3",
		"testSuiteReference" : "",
		
	}];*/
	

	

	
	$rootScope.menuItems = [
	                    {
	                    	"menuItemName" : "SUITE",
	                    	"href" : "suite",
	                    	"id" : "Suite",
	                    	isValid : true	
	                    },
	                    {
	                    	"menuItemName" : "CASES",
	                    	"href" : "cases",
	                    	"id" : "Cases",
	                    		isValid : true		
	                    },
	                    {
	                    	"menuItemName" : "EXECUTOR",
	                    	"href" : "executor",
	                    	"id" : "executor",
	                    		isValid : true		
	                    }]
	$rootScope.quickLinks = [{
    	"menuItemName" : "Create Test Suite",
    	"href" : "createSuite",
    	"id" : "Suite",
    	isValid : true	
    },
    {
    	"menuItemName" : "Create Test Case",
    	"href" : "createCase",
    	"id" : "Cases",
    		isValid : true		
    },
    {
    	"menuItemName" : "Create Test Step",
    	"href" : "createStep",
    	"id" : "executor",
    		isValid : true		
    }];
	 $rootScope.currentPath = $location.path().substring(1);                  
	$rootScope.highlightRow = function(link){
		$rootScope.currentPath = link;
	}
});

