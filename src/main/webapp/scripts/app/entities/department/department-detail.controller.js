'use strict';

angular.module('hotelApp')
    .controller('DepartmentDetailController', function ($scope, $rootScope, $stateParams, entity, Department, User) {
        $scope.department = entity;
        $scope.load = function (id) {
            Department.get({id: id}, function(result) {
                $scope.department = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:departmentUpdate', function(event, result) {
            $scope.department = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
