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
                perman_resid: null,
                birthplace: null,
                father_name: null,
                mother_name: null,
                phone_number: null,
                homephone: null,
                identity_card_number: null,
                identity_card_number_prov_date: null,
                identity_card_number_prov_add: null,
                bank_account: null,
                social_insurence_number: null,
                social_insurence_date: null,
                create_date: null,
                last_modified_date: null,
                id: null
            };
        };
    });
