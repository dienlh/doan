'use strict';

angular.module('hotelApp')
    .controller('FunitureDetailController', function ($scope, $rootScope, $stateParams, entity, Funiture, User) {
        $scope.funiture = entity;
        $scope.load = function (id) {
            Funiture.get({id: id}, function(result) {
                $scope.funiture = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:funitureUpdate', function(event, result) {
            $scope.funiture = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
