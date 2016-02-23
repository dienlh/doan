'use strict';

angular.module('hotelApp')
    .controller('CompanyDetailController', function ($scope, $rootScope, $stateParams, entity, Company, User) {
        $scope.company = entity;
        $scope.load = function (id) {
            Company.get({id: id}, function(result) {
                $scope.company = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:companyUpdate', function(event, result) {
            $scope.company = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
