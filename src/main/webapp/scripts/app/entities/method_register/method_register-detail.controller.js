'use strict';

angular.module('hotelApp')
    .controller('Method_registerDetailController', function ($scope, $rootScope, $stateParams, entity, Method_register, User) {
        $scope.method_register = entity;
        $scope.load = function (id) {
            Method_register.get({id: id}, function(result) {
                $scope.method_register = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:method_registerUpdate', function(event, result) {
            $scope.method_register = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
