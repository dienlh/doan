'use strict';

angular.module('hotelApp')
    .controller('CustomerController', function ($scope, $state, Customer, ParseLinks) {

        $scope.customers = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Customer.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.customers = result;
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
            $scope.customer = {
                full_name: null,
                identity_card_number: null,
                identity_card_prov_date: null,
                identity_card_prov_add: null,
                email: null,
                phone_number: null,
                birtday: null,
                address: null,
                create_date: null,
                last_modified_date: null,
                id: null
            };
        };
    });
