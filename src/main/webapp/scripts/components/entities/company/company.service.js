'use strict';

angular.module('hotelApp')
    .factory('Company', function ($resource, DateUtils) {
        return $resource('api/companys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.create_date = DateUtils.convertDateTimeFromServer(data.create_date);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
