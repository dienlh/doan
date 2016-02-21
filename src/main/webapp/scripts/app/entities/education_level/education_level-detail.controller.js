'use strict';

angular.module('hotelApp')
    .controller('Education_levelDetailController', function ($scope, $rootScope, $stateParams, entity, Education_level, User) {
        $scope.education_level = entity;
        $scope.load = function (id) {
            Education_level.get({id: id}, function(result) {
                $scope.education_level = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:education_levelUpdate', function(event, result) {
            $scope.education_level = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
