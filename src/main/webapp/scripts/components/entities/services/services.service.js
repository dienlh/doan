'use strict';

angular.module('hotelApp')
    .factory('Services', function ($resource, DateUtils) {
        return $resource('api/servicess/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'findAllByNameAndStatus': { method: 'GET', isArray: true, url: 'api/servicess/findAllByNameAndStatus' },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.create_date = DateUtils.convertDateTimeFromServer(data.create_date);
                    data.last_modified_date = DateUtils.convertDateTimeFromServer(data.last_modified_date);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
