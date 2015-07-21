'use strict';
var BranchRelationTableModule = angular.module('myApp.BranchRelationTable', ['ngRoute']);

BranchRelationTableModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/relationtable', {
    templateUrl: 'partials/business/BranchRelationTable.html',
    controller: 'BranchRelationTableCtrl'
  });
}]);

BranchRelationTableModule.controller('BranchRelationTableCtrl', ['$scope','$http','getRelationDataService','$compile',function($scope, $http, getRelationDataService, $compile) {
        $scope.selected = [];
        var jsonData = {};
        jsonData.status = [];
        jsonData.id = [];
        jsonData.add = [];
        jsonData.delete = [];
       
        getRelationDataService.getRelationData().success(function(response){
           $scope.Relations = response;
           for(var i = 0; i < $scope.Relations.length; i++)
           {
               if($scope.Relations[i].status === "true"){
                    $scope.selected.push("Active");
                }
               else{
                   $scope.selected.push("Inactive");
               }
           }
        });
        
        $scope.units = [
            {'value': 'true', 'label': 'Active'},
            {'id': 'false', 'label': 'Inactive'}
        ]
        
        $scope.UpdateBranchStatus = function(id) 
        {
            var x = document.getElementById(id).selectedIndex;
            var status = document.getElementsByTagName("option")[x].value;
            jsonData.status.push(status);
            jsonData.id.push(id);
        };
        
        $scope.addedbranches = [];
        
        $scope.DeleteBranch = function(id)
        {
            jsonData.delete.push(id);
            $("#branchrow"+id+"").remove();
            var position = $scope.addedbranches.indexOf(id);
            if ( ~position ) $scope.addedbranches.splice(position, 1);
        };
        
        $scope.SaveBranchChanges = function()
        {
            for(var i = 0; i < $scope.addedbranches.length; i++)
            {
                var addedbranchname = document.getElementById('branchname'+$scope.addedbranches[i]).value;
                var addedparentbranchname = document.getElementById('parentbranchname'+$scope.addedbranches[i]).value;
                var branchstatus = document.getElementById("activestatus"+$scope.addedbranches[i]).selectedIndex;
                var addedbranchstatus;
                if(branchstatus === 0){
                    addedbranchstatus = "true";
                }
                else{
                    addedbranchstatus = "false";
                }
                
                if(addedbranchname !== "" && addedparentbranchname !== "")
                {
                    var addedbranchdetails = {"branchname" : addedbranchname, "parentbranchname" : addedparentbranchname, "status" : addedbranchstatus};
                    jsonData.add.push(addedbranchdetails);
                }
            }
            
            $http.post('', jsonData).success();
            var delay = 500;
            setTimeout(function(){
               window.location="#/business";
            }, delay);
            
        };   
        
        $scope.addedBranchid = 0;
        
        $scope.AddBranchRelation = function()
        {
            $scope.addedBranchid--;
            var addBranchRelationcode = "<tr id=\"branchrow"+$scope.addedBranchid+"\" class=\"table-bordered\">"+
                    "<td class=\"col-xs-3\"><input id=\"branchname"+$scope.addedBranchid+"\" type=\"text\" placeholder = \"Enter Branch Name\"></td>"+
                    "<td class=\"col-xs-3\"><input id=\"parentbranchname"+$scope.addedBranchid+"\" type=\"text\" placeholder = \"Enter Parent Branch Name\"></td>"+
                    "<td class=\"col-xs-3\">"+
                        "<select id=\"activestatus"+$scope.addedBranchid+"\">"+
                            "<option value=\"true\" selected>Active</option>"+
                            "<option value=\"false\" >Inactive</option>"+
                        "</select>"+
                    "</td>"+
                    "<td class=\"col-xs-3\">"+
                        "<div style=\"clear:right; width:20%; cursor: pointer;\">"+
                            "<span><a href=\"\" style=\"\" ng-click=\"DeleteBranch("+$scope.addedBranchid+")\"><img src=\"assets/img/delete.png\" style=\"width: 20%; height: 20%;\"></a></span>"+
                        "</div>"+
                    "</td>"+
                "</tr>";
            $scope.addedbranches.push($scope.addedBranchid);
        
            var compiledeHTML = $compile(addBranchRelationcode)($scope);
            $("tbody").append(compiledeHTML);
        };
}]);

BranchRelationTableModule.service('getRelationDataService', ['$rootScope','$http', function($rootScope, $http){
    this.getRelationData=function(){
       // return $http.get('http://10.10.24.238:8080/project_manage_dashboard/webresources/mission/all');
       return $http.get('http://localhost:8383/Dashboard_Frontend/relations.json');
    };
}]);

