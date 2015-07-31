'use strict';

var mission_vision_mod = angular.module('myApp.mission_vision', ['ngRoute']);

mission_vision_mod.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/mission_vision', {
    templateUrl: 'partials/mission_vision/mission_vision.html',
    controller: 'mission_visionCtrl'
  });
}]);

mission_vision_mod.controller('mission_visionCtrl', ['$scope','$http','getMissionDataService',function($scope, $http, getMissionDataService) {
        getMissionDataService.getMissionData().success(function(response){
           $scope.missions = response;
           $scope.vision = $scope.missions[0].vision;
           $scope.len = $scope.missions.length;
        });
}]);

mission_vision_mod.service('getMissionDataService', ['$rootScope','$http', function($rootScope, $http){
    this.getMissionData=function(){
        return $http.get('http://10.3.2.134:8080/project_manage_dashboard/webresources/mission/all');
       //return $http.get('m_id.json');
    };
}]);