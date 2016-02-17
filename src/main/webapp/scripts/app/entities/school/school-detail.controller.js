'use strict';

angular.module('hotelApp')
    .controller('SchoolDetailController', function ($scope, $rootScope, $stateParams, entity, School) {
        $scope.school = entity;
        $scope.load = function (id) {
            School.get({id: id}, function(result) {
                $scope.school = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:schoolUpdate', function(event, result) {
            $scope.school = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
