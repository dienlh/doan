'use strict';

angular.module('hotelApp')
    .controller('ImagesDetailController', function ($scope, $rootScope, $stateParams, entity, Images) {
        $scope.images = entity;
        $scope.load = function (id) {
            Images.get({id: id}, function(result) {
                $scope.images = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:imagesUpdate', function(event, result) {
            $scope.images = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
