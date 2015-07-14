'use strict';

var businessModule = angular.module('myApp.business', ['ngRoute']);

businessModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/business', {
    templateUrl: 'partials/business/business.html',
    controller: 'businessCtrl'
  });
}])

businessModule.controller('businessCtrl', ['$scope','$http','getTimeLineDataService', function($scope, $http, getTimeLineDataService) {
        
    $scope.timelineConfig = {};
    var colorArray = [];
    $scope.chart;    
        
    $scope.timelineConfig.bindto = '#timeline';
    $scope.timelineConfig.axis = {
                            x: {
                                type: 'timeseries',
                                tick: {
                                    format: '%Y-%m-%d'
                                },
                                label: {
                                    text: 'Dates',
                                    position: 'outer-middle'
                                }
                            },
                            y: {
                                show:true,
                                label: {
                                    text: 'Count',
                                    position: 'outer-middle'
                                }
                            }
                        };
    $scope.timelineConfig.data = {};
    $scope.timelineConfig.data.x = 'x';
    $scope.timelineConfig.data.color = function(color,d){
                                    return colorArray[d.index];    
                                }
    
    $scope.timelineConfig.data.columns = [
                                      [],
                                      [],
                                      []
                                  ];
    var i;
    
    $scope.makeTree = function(){

        var margin = {top: 10, right: 10, bottom: 10, left: 10},
        //width = parseInt(d3.select('.branchtree').style('width'), 10),
        width = 650 - margin.left - margin.right,
        height = 600 - margin.top - margin.bottom;
        //height = parseInt(d3.select('.branchtree').style('height'), 10),
        //height = height - margin.top - margin.bottom,

        var i = 0,
            duration = 750,
            root;

        var tree = d3.layout.tree()
            .size([height, width]);

        var diagonal = d3.svg.diagonal()
            .projection(function(d) { return [d.y, d.x]; });

        var svg = d3.select(".branchtree").append("svg")
            .attr("id", "btree")
            .attr("width", width + margin.right + margin.left)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        d3.json("http://localhost:8383/Dashboard_Frontend/test.json", function(error, flare) {
          if (error) throw error;

          root = flare;
          root.x0 = height / 2;
          root.y0 = 0;

          function collapse(d) {
            if (d.children) {
              d._children = d.children;
              d._children.forEach(collapse);
              d.children = null;
            }
          }

          root.children.forEach(collapse);
          update(root);
        });

        d3.select(self.frameElement).style("height", "800px");

        function update(source) {

          // Compute the new tree layout.
          var nodes = tree.nodes(root).reverse(),
              links = tree.links(nodes);

          // Normalize for fixed-depth.
          nodes.forEach(function(d) { d.y = d.depth * 180; });

          // Update the nodes…
          var node = svg.selectAll("g.node")
              .data(nodes, function(d) { return d.id || (d.id = ++i); });

          // Enter any new nodes at the parent's previous position.
          var nodeEnter = node.enter().append("g")
              .attr("class", "node")
              .attr("transform", function(d) { return "translate(" + source.y0 + "," + source.x0 + ")"; })
              .on("click", click);

          nodeEnter.append("circle")
              .attr("r", 1e-6)
              .style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; });

          nodeEnter.append("text")
              .attr("x", function(d) { return d.children || d._children ? -10 : 10; })
              .attr("dy", ".35em")
              .attr("text-anchor", function(d) { return d.children || d._children ? "end" : "start"; })
              .text(function(d) { return d.name; })
              .style("fill-opacity", 1e-6);

          // Transition nodes to their new position.
          var nodeUpdate = node.transition()
              .duration(duration)
              .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; });

          nodeUpdate.select("circle")
              .attr("r", 4.5)
              .style("fill", function(d) { return d._children ? "blue" : "#fff"; });

          nodeUpdate.select("text")
              .style("fill-opacity", 1);

          // Transition exiting nodes to the parent's new position.
          var nodeExit = node.exit().transition()
              .duration(duration)
              .attr("transform", function(d) { return "translate(" + source.y + "," + source.x + ")"; })
              .remove();

          nodeExit.select("circle")
              .attr("r", 1e-6);

          nodeExit.select("text")
              .style("fill-opacity", 1e-6);

          // Update the links…
          var link = svg.selectAll("path.link")
              .data(links, function(d) { return d.target.id; });

          // Enter any new links at the parent's previous position.
          link.enter().insert("path", "g")
              .attr("class", "link")
              .attr("d", function(d) {
                var o = {x: source.x0, y: source.y0};
                return diagonal({source: o, target: o});
              })
              .on("click", TimeLine);

          // Transition links to their new position.
          link.transition()
              .duration(duration)
              .attr("d", diagonal);

          // Transition exiting nodes to the parent's new position.
          link.exit().transition()
              .duration(duration)
              .attr("d", function(d) {
                var o = {x: source.x, y: source.y};
                return diagonal({source: o, target: o});
              })
              .remove();

          // Stash the old positions for transition.
          nodes.forEach(function(d) {
            d.x0 = d.x;
            d.y0 = d.y;
          });
        }   

        // Toggle children on click.
        function click(d) {
          if (d.children) {
            d._children = d.children;
            d.children = null;
          } else {
            d.children = d._children;
            d._children = null;
          }
          update(d);
        }  
        
        $scope.setTimeLineData = function(TimeLineData){
            colorArray = TimeLineData.colorArray;
            console.log(TimeLineData.features);
            $scope.timelineConfig.data.columns[0] = TimeLineData.dates;
            console.log($scope.timelineConfig.data.columns[1]);
            $scope.timelineConfig.data.columns[1] = TimeLineData.features;
            console.log($scope.timelineConfig.data.columns[1]);
            $scope.timelineConfig.data.columns[2] = TimeLineData.enhancements;
            $scope.timelineConfig.data.columns[0].unshift('x');
            $scope.timelineConfig.data.columns[1].unshift('features');
            $scope.timelineConfig.data.columns[2].unshift('enhancements');
            c3.generate($scope.timelineConfig);
            console.log($scope.timelineConfig.data.columns[1]);
        }
        
        function TimeLine(d){
            if(d.target.name === "Data1"){
                console.log('Clicked : Data1');
                getTimeLineDataService.getTimeLineData(1).success(function(response){
                    $scope.setTimeLineData(response);
                });  
            }
            else if(d.target.name === "Data2"){
                console.log('Clicked : Data2');
                getTimeLineDataService.getTimeLineData(2).success(function(response){
                    $scope.setTimeLineData(response);
                });  
            }
        }
    };
    
    $scope.ShowTimeLine = function(data){
        if(data === 0){
            getTimeLineDataService.getTimeLineData(1).success(function(response){
                $scope.setTimeLineData(response);
            });
        }
    };
}]);

businessModule.service('getTimeLineDataService', ['$rootScope','$http', function($rootScope, $http){
    this.getTimeLineData=function(num){
        if(num === 1){
            console.log('num = ' + num);
            return $http.get('http://localhost:8383/Dashboard_Frontend/TimeLineData.json');
        }
        else{
            console.log('num = ' + num);
            return $http.get('http://localhost:8383/Dashboard_Frontend/TimeLineData2.json');
        }
    };
}]);