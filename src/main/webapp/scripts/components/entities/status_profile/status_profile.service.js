'use strict';

angular.module('hotelApp')
    .factory('Status_profile', function ($resource, DateUtils) {
        return $resource('api/status_profiles/:id', {}, {
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
