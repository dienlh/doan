'use strict';

angular.module('hotelApp')
    .controller('EthnicDetailController', function ($scope, $rootScope, $stateParams, entity, Ethnic, User) {
        $scope.ethnic = entity;
        $scope.load = function (id) {
            Ethnic.get({id: id}, function(result) {
                $scope.ethnic = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:ethnicUpdate', function(event, result) {
            $scope.ethnic = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
