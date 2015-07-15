'use strict';
var BranchRelationTableModule = angular.module('myApp.BranchRelationTable', ['ngRoute']);

BranchRelationTableModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/relationtable', {
    templateUrl: 'partials/business/BranchRelationTable.html',
    controller: 'BranchRelationTableCtrl'
  });
}]);

BranchRelationTableModule.controller('BranchRelationTableCtrl', ['$scope','$http','getRelationDataService',function($scope, $http, getRelationDataService) {
        getRelationDataService.getRelationData().success(function(response){
           $scope.relations = response;
        });
}]);

BranchRelationTableModule.service('getRelationDataService', ['$rootScope','$http', function($rootScope, $http){
    this.getRelationData=function(){
       // return $http.get('http://10.10.24.238:8080/project_manage_dashboard/webresources/mission/all');
       return $http.get('relations.json');
    };
}]);

