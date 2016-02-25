'use strict';

angular.module('hotelApp')
    .controller('RoomController', function ($scope, $state, Room, ParseLinks) {

        $scope.rooms = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Room.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.rooms = result;
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
            $scope.room = {
                code: null,
                key_code: null,
                title: null,
                is_pet: false,
                is_bed_kid: false,
                number_of_livingroom: null,
                number_of_bedroom: null,
                number_of_toilet: null,
                number_of_kitchen: null,
                number_of_bathroom: null,
                floor: null,
                orientation: null,
                surface_size: null,
                max_adults: null,
                max_kids: null,
                hourly_price: null,
                daily_price: null,
                monthly_price: null,
                create_date: null,
                last_modified_date: null,
                id: null
            };
        };
    });
