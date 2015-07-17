'use strict';

var businessModule = angular.module('myApp.business', ['ngRoute']);

businessModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/business', {
    templateUrl: 'partials/business/business.html',
    controller: 'businessCtrl'
  });
}]);

businessModule.controller('businessCtrl', ['$scope','$http','getTimeLineDataService', 'getGraphDataService', function($scope, $http, getTimeLineDataService, getGraphDataService) {
    $scope.config={};
    $scope.typeOptions=["pie","bar","spline","step","area","area-step","area-spline"];
    $scope.config.type2=$scope.typeOptions[1];
    $scope.showGraph = function(number) {
        getGraphDataService.async(number).then(function(d) {
            var config = {};
            config.bindto = '#chart';
            config.data = {};
            config.data.json = {}
            config.zoom = {enabled:true};
            config.data.json.open = d.open;
            console.log(d.open);
            config.data.json.Clonedopen = d.cloned;
            console.log(d.cloned);
            config.axis = {"y":{"label":{"text":"Number of bugs","position":"outer-middle"}}};
            config.data.types={"open":$scope.config.type2,"Clonedopen":$scope.config.type2};
            $scope.chart = c3.generate(config);
            config = {};
            config.data = {};
            config.pie = {};
            config.data.type='pie';
            config.pie.label = {};
            config.pie.label.format = function(value,ratio,id){
                return value;
            }
            config.legend = {};
            config.legend.show = false;

            config.bindto = '#assigned';
            config.data.columns = [];
            for(var i = 0;i < d.assignedopenedBugs.length;i++){
                config.data.columns.push([]);
                config.data.columns[i].push(d.assignee[i]);
                config.data.columns[i].push(d.assignedopenedBugs[i]);
            }
            c3.generate(config);

            config.bindto = '#customer';
            config.data.columns = [];
            for(var i = 0;i < d.customeropenedBugs.length;i++){
                config.data.columns.push([]);
                config.data.columns[i].push(d.customers[i]);
                config.data.columns[i].push(d.customeropenedBugs[i]);
            }        
            c3.generate(config);

            config.bindto = '#components';
            config.data.columns = [];
            for(var i = 0;i < d.componentopenedBugs.length;i++){
                config.data.columns.push([]);
                config.data.columns[i].push(d.components[i]);
                config.data.columns[i].push(d.componentopenedBugs[i]);
            }
            c3.generate(config);

            config.bindto = '#assignedcloned';
            config.data.columns = [];
            for(var i = 0;i < d.assignedclonedBugs.length;i++){
                config.data.columns.push([]);
                config.data.columns[i].push(d.assignee[i]);
                config.data.columns[i].push(d.assignedclonedBugs[i]);
            }
            c3.generate(config);

            config.bindto = '#customercloned';
            config.data.columns = [];
            for(var i = 0;i < d.customerclonedBugs.length;i++){
                config.data.columns.push([]);
                config.data.columns[i].push(d.customers[i]);
                config.data.columns[i].push(d.customerclonedBugs[i]);
            }
            c3.generate(config);

            config.bindto = '#componentscloned';
            config.data.columns = [];
            for(var i = 0;i < d.componentclonedBugs.length;i++){
                config.data.columns.push([]);
                config.data.columns[i].push(d.components[i]);
                config.data.columns[i].push(d.componentclonedBugs[i]);
            }
            c3.generate(config); 
        });   
    }
    
    $scope.timelineConfig = {};
    var colorArray = [];
    var features = [];
    var enhancements = [];
    var dates = [];
    var milestones = [];
    
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
                                show:false,
                                padding: {
                                    top:300
                                }
                            }
                        };
    $scope.timelineConfig.grid = {
                                    y: {
                                        show: true
                                    },
                                    x: {
                                        show: true
                                    }
                                 };
    $scope.timelineConfig.data = {};
    $scope.timelineConfig.subchart = {
                                        show : true
                                     };
    $scope.timelineConfig.zoom = {
                                    enabled : true
                                 };
    $scope.timelineConfig.data.x = 'x';
    $scope.timelineConfig.data.color = function(color,d){
                                    return colorArray[d.index];    
                                };
    $scope.timelineConfig.data.onmouseover = function (d, i) { console.log('onmouseover' + features[d.index]); }
    
    $scope.timelineConfig.tooltip = {
        format: {
            title: function (d) {
                var format = d3.time.format('%d/%m/%Y');
                return format(d)
            }
        },
        grouped: false,
        position: function (data, width, height, element) {
            var chartOffsetX = document.querySelector("#timeline").getBoundingClientRect().left;
            var graphOffsetX = document.querySelector("#timeline g.c3-axis-y").getBoundingClientRect().right;
            var tooltipWidth = document.getElementById('tooltip').parentNode.clientWidth;
            var x = (parseInt(element.getAttribute('cx')) ) + graphOffsetX - chartOffsetX - Math.floor(tooltipWidth/2);
            var y = element.getAttribute('cy');
            var y = y - height - 14;
            return {top: y, left: x};
          }
    };
    
    $scope.timelineConfig.tooltip.contents = function (data, defaultTitleFormat, defaultValueFormat, color) {
        var $$ = this, config = $$.config,
        titleFormat = config.tooltip_format_title || defaultTitleFormat,
        nameFormat = config.tooltip_format_name || function (name) { return name; },
        valueFormat = config.tooltip_format_value || defaultValueFormat,
        text, i, title, value;
        text = "<div id='tooltip' class='d3-tip'>"; 
        title = dates[data[0].index];
        text += "<span class='info'><b><u>Date</u></b></span><br>";
        text += "<span class='info'>"+ title +"</span><br>";
        text += "<span class='info'><b><u>Features</u> : </b> " + features[data[0].index] + "</span><br>";
        text += "<span class='info'><b><u>Enhancements</u> : </b> " + enhancements[data[0].index] + "</span><br>";
        text += "<span class='info'><b><u>Milestones</u></b></span><br>";
        for(i = 0; i < milestones[data[0].index].length; i++)
        {
            text += "<span class = 'info'>" + milestones[data[0].index][i] + "</span><br>";
        }
        text += "</div>";
        return text;
    };   
    0
    $scope.timelineConfig.data.columns = [
                                      [],
                                      []
                                  ];
    var i;
    
    $scope.makeTree = function(){

        var margin = {
            top: 20,
            right: 120,
            bottom: 20,
            left: 120
        },
        width = 960 - margin.right - margin.left,
        height = 800 - margin.top - margin.bottom;

        var i = 0,
            duration = 750,
            root;

        var tree = d3.layout.tree()
            .size([height, width]);

        var diagonal = d3.svg.diagonal()
            .projection(function (d) {
            return [d.y, d.x];
        });

        var svg = d3.select(".branchtree").append("svg")
            .attr("width", width + margin.right + margin.left)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    
        d3.json("https://gist.githubusercontent.com/mbostock/1093025/raw/a05a94858375bd0ae023f6950a2b13fac5127637/flare.json", function(error, flare) {
          if (error) throw error;

          root = flare;
          console.log(flare);
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
        
        function zoomed() {
            svg.attr("transform", "translate(" + d3.event.translate[0] + ",0)scale(" + d3.event.scale + ")");
        }
        
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
              .text(function(d) {
                  var res = 'Branch - ' + d.name + ', ActiveCount - ' + d.activecount;
                  return res; 
                })
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
                    .data(links, function (d) {
                    return d.target.id;
                });

          // Enter any new links at the parent's previous position.
                link.enter().insert("path", "g")
                  .attr("class", function (d) {
                      var myClass = (d.target.name.length > 7 ? "" : " link2");
                      return "link" + myClass;
                  })
                  .attr("d", function (d) {
                  var o = {
                      x: source.x0,
                      y: source.y0
                  };
                  return diagonal({
                      source: o,
                      target: o
                  });
              });

              // Transition links to their new position.
                link.transition()
                    .duration(duration)
                    .attr("d", diagonal);

                // Transition exiting nodes to the parent's new position.
                link.exit().transition()
                    .duration(duration)
                    .attr("d", function (d) {
                    var o = {
                        x: source.x,
                        y: source.y
                    };
                    return diagonal({
                        source: o,
                        target: o
                    });
                })
                    .remove();

                // Stash the old positions for transition.
                nodes.forEach(function (d) {
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

        $scope.branchName;
        $scope.setTimeLineData = function(TimeLineData){
            milestones = [];
            colorArray = TimeLineData.colorArray;
            features = TimeLineData.features;
            enhancements = TimeLineData.enhancements;
            dates = TimeLineData.dates;
            for(i = 0; i < dates.length; i++)
            {
                milestones.push(TimeLineData.milestones[i]);
            }
            $scope.timelineConfig.data.columns[0] = TimeLineData.dates;
            $scope.timelineConfig.data.columns[1] = [];
            for(i = 0; i < features.length; i++){
                $scope.timelineConfig.data.columns[1].push(30);
            }
            $scope.timelineConfig.data.columns[0].unshift('x');
            $scope.timelineConfig.data.columns[1].unshift('TimeLine');
            c3.generate($scope.timelineConfig);
        };
        
        function TimeLine(d){
            alert(d.target.active);
            console.log(d.target.branchid);
            $scope.showGraph(d.target.branchid);
            $scope.branchName = d.target.name;
            if(d.target.name === "MTAS-9.1"){
                console.log('Clicked : Data1');
                getTimeLineDataService.getTimeLineData(1).success(function(response){
                    $scope.setTimeLineData(response);
                });  
            }
            else if(d.target.name === "MTAS-10.4"){
                console.log('Clicked : Data2');
                getTimeLineDataService.getTimeLineData(2).success(function(response){
                  $scope.setTimeLineData(response);
                });  
            }
        }
    };
    $scope.ShowTimeLine = function(data){
        $scope.branchName = "MTAS-9.1";
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
            return $http.get('http://localhost:8383/Dashboard_Frontend/TimeLineData.json');
        }
        else{
            return $http.get('http://localhost:8383/Dashboard_Frontend/TimeLineData2.json');
        }
    };
}]);

businessModule.factory('getGraphDataService', function($http) {
  var urlString = "http://10.10.25.195:8084/project_manage_dashboard/webresources/business/monthlyBar/6";
    var getGraphDataService = {
    async: function(num) {
      var promise = $http.get(urlString+'/'+num).then(function (response) {
        console.log(response);
        return response.data;
      });
      return promise;
    }
  };
  return getGraphDataService;
});