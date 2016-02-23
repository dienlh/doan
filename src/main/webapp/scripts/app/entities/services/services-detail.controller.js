'use strict';

angular.module('hotelApp')
    .controller('ServicesDetailController', function ($scope, $rootScope, $stateParams, entity, Services, Status_service, User) {
        $scope.services = entity;
        $scope.load = function (id) {
            Services.get({id: id}, function(result) {
                $scope.services = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:servicesUpdate', function(event, result) {
            $scope.services = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
