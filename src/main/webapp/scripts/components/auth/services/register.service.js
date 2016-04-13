'use strict';

angular.module('hotelApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


