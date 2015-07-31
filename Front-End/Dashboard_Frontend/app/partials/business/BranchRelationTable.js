'use strict';
var BranchRelationTableModule = angular.module('myApp.BranchRelationTable', ['ngRoute']);

BranchRelationTableModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/relationtable', {
    templateUrl: 'partials/business/BranchRelationTable.html',
    controller: 'BranchRelationTableCtrl'
  });
}]);

BranchRelationTableModule.controller('BranchRelationTableCtrl', ['$scope','$http','getRelationDataService','$compile', '$q',function($scope, $http, getRelationDataService, $compile, $q) {
        $scope.selected = [];
        var jsonData = {};
        jsonData.status = [];
        jsonData.id = [];
        jsonData.add = [];
        jsonData.delete = [];
        
        var BranchNames = [];
       
        getRelationDataService.getRelationData().success(function(response){
           $scope.Relations = response;
           for(var i = 0; i < $scope.Relations.length; i++)
           {
               BranchNames.push($scope.Relations[i].branchname);
           }
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
            {'value': 'false', 'label': 'Inactive'}
        ];
        
        $scope.UpdateBranchStatus = function(id) 
        {
            var x = document.getElementById(id);
            console.log(x.selectedIndex);
            var status = x.options[x.selectedIndex].value;
            console.log('status - '+status);
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
        
        
        $scope.addedBranchid = 0;

        $scope.AddBranchRelation = function()
        {
            $scope.addedBranchid--;
            var addBranchRelationcode = "<tr id=\"branchrow"+$scope.addedBranchid+"\" class=\"table-bordered\">"+
                    "<td class=\"col-xs-3\"><input id=\"branchname"+$scope.addedBranchid+"\" type=\"text\" placeholder = \"Enter Branch Name\"></td>"+
                    "<td class=\"col-xs-3\"><input id=\"parentbranchname"+$scope.addedBranchid+"\" class=\"ParentBranchClass\" type=\"text\" placeholder = \"Enter Parent Branch Name\"></td>"+
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
            
            $('.ParentBranchClass').on("focus", function(){
                $(this).autocomplete({
                 minLength: 1,
                 source: BranchNames
                  });
            });
   
        };

        $scope.SaveBranchChanges = function(){
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
                    var addedbranchdetails = {"branchname" : addedbranchname, "parentname" : addedparentbranchname, "status" : addedbranchstatus, "branchid" : $scope.addedbranches[i]};
                    jsonData.add.push(addedbranchdetails);
                }
            }
            
            var url = "http://10.3.2.134:8080/project_manage_dashboard/webresources/business/setDependency";
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

BranchRelationTableModule.service('getRelationDataService', ['$rootScope','$http', function($rootScope, $http){
    this.getRelationData=function(){
        return $http.get('http://10.3.2.134:8080/project_manage_dashboard/webresources/business/getDependency');
       //return $http.get('http://localhost:8383/Dashboard_Frontend/relations.json');
    };
}]);

