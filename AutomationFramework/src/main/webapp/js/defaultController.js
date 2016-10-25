
var afApp = angular.module("afApp",["ngRoute"]);

afApp.config(function($routeProvider,$httpProvider){
	 $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	$routeProvider.when("/login",{
		templateUrl:"login.html",
		controller:"loginController"
	}).when("/home",{
		templateUrl:"home.html",
		controller:"homeController"
	})
	.when("/suite",{
		templateUrl:"TestSuite.html",
		controller:"homeController"
	}).when("/cases",{
		templateUrl:"TestCase.html",
		controller:"homeController"
	}).otherwise("/")
})

afApp.run(function($rootScope,$http,$location){

	$rootScope.testCases=[{ 
			"active" : true,
			"template" : true,
			"order" : 1,
			"testCaseDescription" : "bla bla",
			"testCaseName" : "testSuite",
			"testCaseReference" : "df",
		},
		{ 
			"active" : true,
			"template" : true,
			"order" : 1,
			"testCaseDescription" : "bla bla",
			"testCaseName" : "testSuite",
			"testCaseReference" : "df",
		},
		{ 
			"active" : true,
			"template" : true,
			"order" : 1,
			"testCaseDescription" : "bla bla",
			"testCaseName" : "testSuite",
			"testCaseReference" : "df",
		}]
	$rootScope.testsuite=[{ 
		"id" : "0aa3ccb2-03ff-45aa-8ce1-41c837912975",
		"active" : true,
		"suiteDescription" : "bla bla bla bla bla",
		"suiteName" : "New testSuite",
		"testSuiteReference" : "",
		"testCases" : $rootScope.testCases
	},
	{ 
		"id" : "0aa3ccb2-03ff-45aa-8ce1-41c837912975",
		"active" : true,
		"suiteDescription" : "bla bla bla bla bla",
		"suiteName" : "New testSuite2",
		"testSuiteReference" : "",
		
	},
	{ 
		"id" : "0aa3ccb2-03ff-45aa-8ce1-41c837912975",
		"active" : true,
		"suiteDescription" : "bla bla bla bla bla",
		"suiteName" : "New testSuite2",
		"testSuiteReference" : "",
		
	},
	{ 
		"id" : "0aa3ccb2-03ff-45aa-8ce1-41c837912975",
		"active" : true,
		"suiteDescription" : "bla bla bla bla bla",
		"suiteName" : "New testSuite3",
		"testSuiteReference" : "",
		
	}];
	

	
	/*postObj={
			testSuites : testsuite,
		testCases :testCase
	}*/
	
	/*$http.post("default/save?testSuiteId=729e5d1d-0f07-4388-85b4-8725a8f67ab4&projectCode=TnE",postObj).success(function(data){
		console.log(data);
	})*/
	
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

