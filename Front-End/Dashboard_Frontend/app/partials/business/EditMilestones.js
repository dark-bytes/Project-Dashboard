'use strict';

var MilestonesModule = angular.module('myApp.milestones', ['ngRoute']);

MilestonesModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/editmilestones', {
    templateUrl: 'partials/business/EditMilestones.html',
    controller: 'milestonesCtrl'
  });
}]);

MilestonesModule.controller('milestonesCtrl', ['$scope', 'getMilestonesDataService', '$compile', '$http','$q', function($scope, getMilestonesDataService, $compile, $http, $q){
    $scope.milestonesdata;
    $scope.dates;
    
    var jsonData={};
    jsonData.dates = [];
    jsonData.branch = [];
    jsonData.features = [];
    jsonData.defects = [];
    jsonData.milestoneId = [];
    jsonData.branchList = [];
    var BranchNames;
    
    getMilestonesDataService.getMilestonesData().success(function(response){
        $scope.milestonesdata = response;
        BranchNames = response.branchList;
    });
    
    $scope.addedMilestones = [];
     
    $scope.addedMilestoneid = 0;
     
    $scope.AddMilestone = function(){
        $scope.addedMilestoneid--;
        var addMilestonecode = "<tr id=\"milestonerow"+$scope.addedMilestoneid+"\" class=\"table-bordered\">"+
                    "<td class=\"col-xs-3\"><input id=\"milestonesDate"+$scope.addedMilestoneid+"\" type=\"date\" placeholder = \"Enter Date\"></td>"+
                    "<td class=\"col-xs-3\"><input id=\"milestonesBranch"+$scope.addedMilestoneid+"\" type=\"text\" class=\"BranchClass\" placeholder = \"Enter Branch Name\"></td>"+
                    "<td class=\"col-xs-3\"><input id=\"milestonesFeatures"+$scope.addedMilestoneid+"\" type=\"text\" placeholder = \"Enter Features\"></td>"+
                    "<td class=\"col-xs-3\"><input id=\"milestonesDefects"+$scope.addedMilestoneid+"\" type=\"text\" placeholder = \"Enter Defects\"></td>"+
                "</tr>";
        $scope.addedMilestones.push($scope.addedMilestoneid);
        
        var compiledeHTML = $compile(addMilestonecode)($scope);
        $("tbody").append(compiledeHTML);
        
        $('.BranchClass').on("focus", function(){
                $(this).autocomplete({
                 minLength: 1,
                 source: BranchNames
                  });
            });
    };
    
    $scope.saveMilestonesChanges = function(){
        for(var i = 0; i < $scope.addedMilestones.length; i++){
            var addedMilestoneDate = document.getElementById('milestonesDate'+$scope.addedMilestones[i]).value;
            var addedMilestoneBranch = document.getElementById('milestonesBranch'+$scope.addedMilestones[i]).value;
            var addedMilestoneFeatures = document.getElementById('milestonesFeatures'+$scope.addedMilestones[i]).value;
            var addedMilestonedefects = document.getElementById('milestonesDefects'+$scope.addedMilestones[i]).value;
            addedMilestoneFeatures.replace(/ /g,'');
            addedMilestonedefects.replace(/ /g,'');
            if(addedMilestoneDate !== "" && addedMilestoneBranch !== "" && addedMilestoneFeatures !== "" && addedMilestonedefects !== ""){ 
                jsonData.dates.push(addedMilestoneDate);
                jsonData.branch.push(addedMilestoneBranch);
                jsonData.defects.push(addedMilestonedefects);
                jsonData.features.push(addedMilestoneFeatures);
            }
        }
       /* $http.post('http://192.168.137.3:8084/project_manage_dashboard/webresources/business/putMstones', jsonData).success();
        var delay = 0;
        setTimeout(function(){
           window.location="#/business";
        }, delay); */
        var url = "http://10.3.2.134:8080/project_manage_dashboard/webresources/business/putMstones";
        $scope.block = 0;
        $scope.send = 1;
    $.blockUI({ css: { 
            border: 'none', 
            padding: '15px', 
            backgroundColor: '#000', 
            '-webkit-border-radius': '10px', 
            '-moz-border-radius': '10px', 
            opacity: .5, 
            color: '#fff' 
        } });
       $scope.callXhrAsynchronous(jsonData, url); 
    };

    $scope.myXhr = function(Data, link){
        
        console.log('callAsync');
        $http({
            url : link,
            //url: 'http://10.10.24.98:8084/project_manage_dashboard/webresources/business/putMstones',
            method: 'POST',
            //data: jsonData,
            data : Data,
            headers: {'Content-Type': 'text/plain'}
            })
            //if request is successful
            .success(function(data,status,headers,config){

                //resolve the promise
                //alert('Data Update Successful!!');
                if($scope.block === 0){
                    $scope.block = 1;
                    $.unblockUI();
                }
                $.growlUI('Data Update Successful!');
                console.log(data);
                if($scope.send === 1)
                    window.location="#/business"; 
            })
            //if request is not successful
            .error(function(data,status,headers,config){
                //reject the promise
        
                if($scope.block === 0){
                    $scope.block = 1;
                    $.unblockUI();
                }
                alert('Error while updating data, Please try Again!!');
                //deferred.reject('ERROR');
            });

        //return the promise
        return deferred.promise;
    };

    $scope.callXhrAsynchronous = function(Data, link){

        console.log('callAsync');
        var myPromise = $scope.myXhr(Data ,link);

        // wait until the promise return resolve or eject
        //"then" has 2 functions (resolveFunction, rejectFunction)
        myPromise.then(function(resolve){
            alert(resolve);
            }, function(reject){
            alert(reject)      
        });
    };
    
$scope.uploadedFile = function(element) {
        $scope.$apply(function($scope) {
          $scope.files = element.files;         
        });
    };
    
$scope.block = 1;
    
var uploadfile = function(files,success,error){
 
 var url = 'http://10.3.2.134:8080/FileUpload/rest/files/upload';

    try{
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
         if($scope.block === 0){
          // $scope.block = 1;
          // $.unblockUI();
          // $.growlUI('File Uploaded Successfully!');
         }
         console.log('data - ' + data);
         //alert('File Successfully uploaded!!');
         var url = "http://10.3.2.134:8080/project_manage_dashboard/webresources/business/upload";
           $scope.block = 0;
           $.blockUI({ css: { 
                   border: 'none', 
                   padding: '15px', 
                   backgroundColor: '#000', 
                   '-webkit-border-radius': '10px', 
                   '-moz-border-radius': '10px', 
                   opacity: .5, 
                   color: '#fff' 
               } });
           $.growlUI('Updating Data!');
           $scope.send = 0;
         $scope.callXhrAsynchronous($scope.uploadedDate, url);   
        })
        .error(function(data)
        {
         if($scope.block === 0){ 
           $.unblockUI();
           $scope.block = 1;
         }
         alert('Error in uploading File!');
         console.log(data);
        });
       }
    }
    catch(err){
        if($scope.block === 0){ 
           $.unblockUI();
           $scope.block = 1;
       }
        alert('Please Select File!');
    }
};

$scope.addFile = function() {
    
    $scope.uploadedDate = "";
    if(document.getElementById('trendline').checked){
        $scope.uploadedDate = document.getElementById('uploadedDate').value;
        if($scope.uploadedDate === ""){    
            alert('Please Enter Date!');
        }
        else{
            $scope.block = 0;
            $.blockUI({ css: { 
                    border: 'none', 
                    padding: '15px', 
                    backgroundColor: '#000', 
                    '-webkit-border-radius': '10px', 
                    '-moz-border-radius': '10px', 
                    opacity: .5, 
                    color: '#fff' 
                } }); 
            console.log('Date - ' + $scope.uploadedDate);
            uploadfile($scope.files,
                function( msg ) // success
                {
                 console.log('uploaded');
                },
                function( msg ) // error
                {
                 console.log('error');
                }
            );
        }
    }
    else{
        $scope.block = 0;
        $.blockUI({ css: { 
                border: 'none', 
                padding: '15px', 
                backgroundColor: '#000', 
                '-webkit-border-radius': '10px', 
                '-moz-border-radius': '10px', 
                opacity: .5, 
                color: '#fff' 
            } }); 
        $scope.uploadedDate = "";
        uploadfile($scope.files,
            function( msg ) // success
            {
             console.log('uploaded');
            },
            function( msg ) // error
            {
             console.log('error');
            }
        );
    }
};
}]);


MilestonesModule.service('getMilestonesDataService', ['$rootScope','$http', function($rootScope, $http){
    this.getMilestonesData=function(){
        return $http.get('http://10.3.2.134:8080/project_manage_dashboard/webresources/business/getMstones');
    };
}]);