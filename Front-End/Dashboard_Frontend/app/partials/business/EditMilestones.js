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
        $http.post('http://192.168.137.3:8084/project_manage_dashboard/webresources/business/putMstones', jsonData).success();
        var delay = 0;
        setTimeout(function(){
           window.location="#/business";
        }, delay);
    };
    
    
    $scope.uploadedFile = function(element) {
        $scope.$apply(function($scope) {
          $scope.files = element.files;         
        });
    };


var uploadfile = function(files,success,error){
 
 var url = 'http://192.168.137.3:8084/FileUpload/rest/files/upload';

 for ( var i = 0; i < files.length; i++)
 {
  var fd = new FormData();
 
  fd.append("file", files[i]);
 
  $http.post(url, fd, {
  
   withCredentials : false,
  
   headers : {
    'Content-Type' : undefined
   },
 transformRequest : angular.identity

 })
 .success(function(data)
 {
  console.log(data);
 })
 .error(function(data)
 {
  console.log(data);
 });
}
};

$scope.addFile = function() {
 uploadfile($scope.files,
   function( msg ) // success
   {
    console.log('uploaded');
   },
   function( msg ) // error
   {
    console.log('error');
   });
};
}]);


MilestonesModule.service('getMilestonesDataService', ['$rootScope','$http', function($rootScope, $http){
    this.getMilestonesData=function(){
        return $http.get('http://192.168.137.3:8084/project_manage_dashboard/webresources/business/getMstones');
    };
}]);