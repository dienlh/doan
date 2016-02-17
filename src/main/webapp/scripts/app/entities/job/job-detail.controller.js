'use strict';

angular.module('hotelApp')
    .controller('JobDetailController', function ($scope, $rootScope, $stateParams, entity, Job) {
        $scope.job = entity;
        $scope.load = function (id) {
            Job.get({id: id}, function(result) {
                $scope.job = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:jobUpdate', function(event, result) {
            $scope.job = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
