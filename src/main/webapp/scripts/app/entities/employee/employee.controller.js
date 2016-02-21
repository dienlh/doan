'use strict';

angular.module('hotelApp')
    .controller('EmployeeController', function ($scope, $state, Employee, ParseLinks) {

        $scope.employees = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Employee.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.employees = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.employee = {
                full_name: null,
                birthday: null,
                address: null,
                birthplace: null,
                perman_resid: null,
                father_name: null,
                mother_name: null,
                telephone: null,
                homephone: null,
                email: null,
                ic_number: null,
                ic_prov_date: null,
                ic_prov_add: null,
                bank_account: null,
                si_number: null,
                si_prov_date: null,
                create_date: null,
                last_modified_date: null,
                id: null
            };
        };
    });
