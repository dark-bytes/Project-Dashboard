    
(function(angular){
'use strict';
var edit_vision_mission = angular.module('myApp.edit_vision_mission', ['ngRoute', 'myApp.mission_vision']);

edit_vision_mission.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/edit_vision_mission', {
    templateUrl: 'partials/edit_vision_mission/edit_vision_mission.html',
    controller: 'edit_vision_missionCtrl'
  });
}]);

edit_vision_mission.controller('edit_vision_missionCtrl',['$scope','$http','getMissionDataService','$compile', function($scope, $http, getMissionDataService, $compile) {
    getMissionDataService.getMissionData().success(function(response){
       $scope.missions = response;
       $scope.vision = $scope.missions[0].vision;
       console.log('Vision is ' + $scope.vision);
       $scope.len = $scope.missions.length;
    });
    
    var jsonData = {};
    var flag = 0;
    jsonData.vision = null;
    jsonData.deleteMissionId = [];
    jsonData.deletePointId = [];
    jsonData.Update = [];
    jsonData.addmId = [];
    jsonData.addpValue = [];
    jsonData.AddMissionInfo = [];

    $scope.updatevision = function(vision){
        if(vision != null){
            flag = 1;
            $scope.vision = this.vision;
            jsonData.vision = this.vision;
        }
    };
    
    $scope.deletemissionpoints = function(missionpointid, missionid){
        alert(missionid+" "+missionpointid);
       // alert("Are You Sure");
        jsonData.deletePointId.push(missionpointid);
        $("#mission-"+missionid+"").find("#"+missionpointid+"").remove();
    };
    
    $scope.deleteMission = function(missionid)
    {
        alert(missionid);
        //alert("Are You Sure");
        jsonData.deleteMissionId.push(missionid);
        $("#mission-"+missionid+"").remove();
    };
    $scope.updatemission = function(missionid, info, val){
        if(info !== null){
         // alert(info +', '+missionid +', '+val );
            var updateinfo = {"what" : "mission", "id" : missionid,  "value" : info, "mid" : missionid, "add" : val};
            jsonData.Update.push(updateinfo);
        }
    };
    $scope.updatemissionpoints = function(missionpointid, info, missionid, val){
        if(info !== null){
          // alert(missionpointid+', '+info +', '+missionid +', '+val );
            var updateinfo = {"what" : "point", "id" : missionpointid, "value" : info, "mid" : missionid, "add" : val};
            jsonData.Update.push(updateinfo);
        }
    };

    $scope.addedmissionpointid = 0;  
    $scope.missionpointdata = [];
    $scope.indexmissionpointdata = -1;
    
    $scope.addmissionpoint = function(missionid){
        $scope.addedmissionpointid--;
        $scope.missionpointdata.push("");
        $scope.indexmissionpointdata++;
        var addpointcode = "<li id=\""+$scope.addedmissionpointid+"\" style=\"padding: 2px; width: 100%;\">"+
                            "<div style=\"float:right; clear:right; width:20%;\">"+
                            "<span><a style=\"\" ng-click=\"deletemissionpoints("+$scope.addedmissionpointid+","+missionid+")\"><img src=\"assets/img/delete.png\" style=\"width: 20%; height: 20%;\"></a></span>"+"</div>"+
                            "<div><input type=\"text\" class=\"form-control\" style=\"background-color: #e8e8e8; width: 80%;\" ng-model =\"missionpointdata[" + $scope.indexmissionpointdata + "]\" ng-blur=\"updatemissionpoints("+$scope.addedmissionpointid+",missionpointdata["+$scope.indexmissionpointdata+"],"+missionid+", 1)\"></div>"+
                            "</li></ul></div>";
        var compiledeHTML = $compile(addpointcode)($scope);
        $("#mission-"+missionid+"").find("ul").append(compiledeHTML);
    }; 
    
    $scope.addmissionid = 0;
    $scope.addpointid = -1;     
    $scope.missionnamedata = [];
    $scope.indexmissionname = -1;
    $scope.fistmissionpoint = [];
    $scope.indexfirstmissionpoint = -1;
       
    $scope.addmission = function(){
        $scope.missionnamedata.push("");
        $scope.fistmissionpoint.push("");
        $scope.indexmissionname++;
        $scope.indexfirstmissionpoint++;
        console.log("index is" + $scope.indexmissionname + "data is " + $scope.missionnamedata);
        $scope.addmissionid--;
        $scope.addpointid--;
        $scope.addedmissionpointid--;
           
        var s = "<div id=\"mission-"+$scope.addmissionid+"\" class=\"contentBox box effectmission\">"+
                "<div class=\"boxheader\">"+"<div style=\"float:right; clear:right; width:20%;\">"+
                "<span><a ng-click=\"deleteMission("+$scope.addmissionid+")\"><img src=\"assets/img/delete.png\" style=\"width: 20%; height: 20%;\"></a></span>"+
                "</div>"+"<span style=\"font-size: large; font-family: monospace; font-weight: bold;\">EDIT MISSION NAME</span><input ng-model =\"missionnamedata[" + $scope.indexmissionname + "]\" ng-blur=\"updatemission("+$scope.addmissionid+",missionnamedata["+$scope.indexmissionname+"], 1)\" type=\"text\" class=\"form-control\" style=\"background-color: #e8e8e8\">"+
                "</div><div style=\"padding-top: 10px;\"> <span style=\"font-size: large; font-family: monospace; font-weight: bold; margin-left:5%;\">EDIT MISSION POINTS</span><br /></div>"
                + "<div style=\"padding-top: 10px;\">" + 
                "<ul id=\"beforeul\" style=\"float:left; width:100%;\">" + 
                "<li id=\"-1\" style=\"padding: 2px; width: 100%;\">"+
                "<div style=\"float:right; clear:right; width:20%;\">"+
                "<span><a href=\"\" style=\"\" ng-click=\"deletemissionpoints("+$scope.addedmissionpointid+","+$scope.addmissionid+")\"><img src=\"assets/img/delete.png\" style=\"width: 20%; height: 20%;\"></a></span>"+"</div>"+
                "<div><input id=\"ip\" ng-model =\"firstmissionpoint[" + $scope.indexfirstmissionpoint + "]\" ng-blur=\"updatemissionpoints("+$scope.addedmissionpointid+",firstmissionpoint["+$scope.indexfirstmissionpoint+"],"+$scope.addmissionid+", 1)\" type=\"text\" value=\"Enter Mission Point\" class=\"form-control\" style=\"background-color: #e8e8e8; width: 80%;\"></div>"+
                "</li></ul></div>"+" <div style=\"padding-top: 35px;\"><span><a href=\"\" ng-click=\"addmissionpoint("+$scope.addmissionid+")\"><img id=\"addmissionpoint\" src=\"assets/img/add.png\" alt=\"addmissionpoint\" style=\"width: 7%; height: 12%; float:right; bottom:0;\"></a></span>"+
                "</div></div>";

        var compiledeHTML = $compile(s)($scope);
        $("#missioncontainer").append(compiledeHTML); 
    };

    $scope.savechanges = function(){
        if(flag == 0)
            jsonData.vision = $scope.vision;
        $http.post('http://10.3.2.134:8080/project_manage_dashboard/webresources/mission/edit', jsonData).success();
        var delay = 500;
        setTimeout(function(){
               window.location="#/mission_vision";
        }, delay); 
    };     
}]);

})(window.angular);