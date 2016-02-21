'use strict';

angular.module('hotelApp')
    .controller('MajorDetailController', function ($scope, $rootScope, $stateParams, entity, Major, User) {
        $scope.major = entity;
        $scope.load = function (id) {
            Major.get({id: id}, function(result) {
                $scope.major = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:majorUpdate', function(event, result) {
            $scope.major = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
