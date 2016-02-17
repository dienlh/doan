'use strict';

angular.module('hotelApp')
    .factory('Education_level', function ($resource, DateUtils) {
        return $resource('api/education_levels/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
