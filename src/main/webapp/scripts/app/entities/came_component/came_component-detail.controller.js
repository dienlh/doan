'use strict';

angular.module('hotelApp')
    .controller('Came_componentDetailController', function ($scope, $rootScope, $stateParams, entity, Came_component) {
        $scope.came_component = entity;
        $scope.load = function (id) {
            Came_component.get({id: id}, function(result) {
                $scope.came_component = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:came_componentUpdate', function(event, result) {
            $scope.came_component = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
