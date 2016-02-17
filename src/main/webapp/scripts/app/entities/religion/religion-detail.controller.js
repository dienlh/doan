'use strict';

angular.module('hotelApp')
    .controller('ReligionDetailController', function ($scope, $rootScope, $stateParams, entity, Religion) {
        $scope.religion = entity;
        $scope.load = function (id) {
            Religion.get({id: id}, function(result) {
                $scope.religion = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:religionUpdate', function(event, result) {
            $scope.religion = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
