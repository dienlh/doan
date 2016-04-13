'use strict';

angular.module('hotelApp')
    .controller('Kind_of_policyController', function ($scope, $state, Kind_of_policy, ParseLinks) {

        $scope.kind_of_policys = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Kind_of_policy.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.kind_of_policys = result;
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
            $scope.kind_of_policy = {
                name: null,
                decription: null,
                create_date: null,
                id: null
            };
        };
    });
