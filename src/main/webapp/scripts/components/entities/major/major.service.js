'use strict';

angular.module('hotelApp')
    .factory('Major', function ($resource, DateUtils) {
        return $resource('api/majors/:id', {}, {
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
