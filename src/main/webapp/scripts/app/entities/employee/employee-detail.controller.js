'use strict';

angular.module('hotelApp')
    .controller('EmployeeDetailController', function ($scope, $rootScope, $stateParams, entity, Employee, Gender, Ethnic, Religion, Job, Education_level, Major, School, Marital_status, Came_component, Bank, User) {
        $scope.employee = entity;
        $scope.load = function (id) {
            Employee.get({id: id}, function(result) {
                $scope.employee = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:employeeUpdate', function(event, result) {
            $scope.employee = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
