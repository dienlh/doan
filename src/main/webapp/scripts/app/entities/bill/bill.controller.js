'use strict';

angular.module('hotelApp')
    .controller('BillController', function ($scope, $state, Bill, ParseLinks) {

        $scope.bills = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Bill.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.bills = result;
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
            $scope.bill = {
                fees_room: null,
                fees_service: null,
                fees_other: null,
                fees_bonus: null,
                total: null,
                fees_vat: null,
                total_vat: null,
                decription: null,
                create_date: null,
                id: null
            };
        };
    });
