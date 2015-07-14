'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'myApp.mission_vision',
  'myApp.business',
  'myApp.edit_vision_mission',
  'myApp.BranchRelationTable'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/mission_vision'});
}]);
