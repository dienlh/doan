'use strict';

angular.module('hotelApp')
    .controller('PolicyController', function ($scope, $state, Policy, ParseLinks) {

        $scope.policys = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Policy.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.policys = result;
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
            $scope.policy = {
                title: null,
                decription: null,
                start_date: null,
                end_date: null,
                create_date: null,
                last_modified_date: null,
                id: null
            };
        };
    });
