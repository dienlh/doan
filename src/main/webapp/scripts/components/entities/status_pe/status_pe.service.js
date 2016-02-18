'use strict';

angular.module('hotelApp')
    .factory('Status_pe', function ($resource, DateUtils) {
        return $resource('api/status_pes/:id', {}, {
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
