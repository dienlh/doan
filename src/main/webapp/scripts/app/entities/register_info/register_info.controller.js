'use strict';

angular.module('hotelApp')
    .controller('Register_infoController', function ($scope, $state, Register_info, ParseLinks) {

        $scope.register_infos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Register_info.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.register_infos = result;
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
            $scope.register_info = {
                date_checkin: null,
                date_checkout: null,
                number_of_adults: null,
                number_of_kids: null,
                other_request: null,
                deposit_value: null,
                create_date: null,
                last_modified_date: null,
                id: null
            };
        };
    });
