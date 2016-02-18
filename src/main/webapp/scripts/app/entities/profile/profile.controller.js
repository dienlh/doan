'use strict';

angular.module('hotelApp')
    .controller('ProfileController', function ($scope, $state, Profile, ParseLinks) {

        $scope.profiles = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Profile.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.profiles = result;
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
            $scope.profile = {
                join_date: null,
                salary_subsidies: null,
                salary_basic: null,
                salary: null,
                create_date: null,
                last_modified_date: null,
                id: null
            };
        };
    });
