'use strict';

var MilestonesModule = angular.module('myApp.milestones', ['ngRoute']);

MilestonesModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/editmilestones', {
    templateUrl: 'partials/business/EditMilestones.html',
    controller: 'milestonesCtrl'
  });
}]);

MilestonesModule.controller('milestonesCtrl', ['$scope', 'getMilestonesDataService', '$compile', '$http', function($scope, getMilestonesDataService, $compile, $http){
    $scope.milestonesdata;
    $scope.dates;
    
    var jsonData={};
    jsonData.dates = [];
    jsonData.branch = [];
    jsonData.features = [];
    jsonData.defects = [];
    jsonData.milestoneId = [];
    
    getMilestonesDataService.getMilestonesData().success(function(response){
        $scope.milestonesdata = response;
    });
    
    $scope.addedMilestones = [];
     
    $scope.addedMilestoneid = 0;
     
    $scope.AddMilestone = function(){
        $scope.addedMilestoneid--;
        var addMilestonecode = "<tr id=\"milestonerow"+$scope.addedMilestoneid+"\" class=\"table-bordered\">"+
                    "<td class=\"col-xs-3\"><input id=\"milestonesDate"+$scope.addedMilestoneid+"\" type=\"date\" placeholder = \"Enter Date\"></td>"+
                    "<td class=\"col-xs-3\"><input id=\"milestonesBranch"+$scope.addedMilestoneid+"\" type=\"text\" placeholder = \"Enter Branch Name\"></td>"+
                    "<td class=\"col-xs-3\"><input id=\"milestonesFeatures"+$scope.addedMilestoneid+"\" type=\"text\" placeholder = \"Enter Features\"></td>"+
                    "<td class=\"col-xs-3\"><input id=\"milestonesDefects"+$scope.addedMilestoneid+"\" type=\"text\" placeholder = \"Enter Defects\"></td>"+
                "</tr>";
        $scope.addedMilestones.push($scope.addedMilestoneid);
        
        var compiledeHTML = $compile(addMilestonecode)($scope);
        $("tbody").append(compiledeHTML);
    };
    
    $scope.saveMilestonesChanges = function(){
        for(var i = 0; i < $scope.addedMilestones.length; i++){
            var addedMilestoneDate = document.getElementById('milestonesDate'+$scope.addedMilestones[i]).value;
            var addedMilestoneBranch = document.getElementById('milestonesBranch'+$scope.addedMilestones[i]).value;
            var addedMilestoneFeatures = document.getElementById('milestonesFeatures'+$scope.addedMilestones[i]).value;
            var addedMilestonedefects = document.getElementById('milestonesDefects'+$scope.addedMilestones[i]).value;
            if(addedMilestoneDate !== "" && addedMilestoneBranch !== "" && addedMilestoneFeatures !== "" && addedMilestonedefects !== ""){ 
                jsonData.dates.push(addedMilestoneDate);
                jsonData.branch.push(addedMilestoneBranch);
                jsonData.defects.push(addedMilestonedefects);
                jsonData.features.push(addedMilestoneFeatures);
            }
        }
        console.log(jsonData.dates);
        $http.post('', jsonData).success();
        var delay = 500;
        setTimeout(function(){
           window.location="#/business";
        }, delay);
    };
}]);

MilestonesModule.service('getMilestonesDataService', ['$rootScope','$http', function($rootScope, $http){
    this.getMilestonesData=function(){
       // return $http.get('http://10.10.24.238:8080/project_manage_dashboard/webresources/mission/all');
       return $http.get('http://localhost:8383/Dashboard_Frontend/editmilestonesdata.json');
    };
}]);