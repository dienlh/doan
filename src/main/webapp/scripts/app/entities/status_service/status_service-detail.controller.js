'use strict';

angular.module('hotelApp')
    .controller('Status_serviceDetailController', function ($scope, $rootScope, $stateParams, entity, Status_service, User) {
        $scope.status_service = entity;
        $scope.load = function (id) {
            Status_service.get({id: id}, function(result) {
                $scope.status_service = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:status_serviceUpdate', function(event, result) {
            $scope.status_service = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
