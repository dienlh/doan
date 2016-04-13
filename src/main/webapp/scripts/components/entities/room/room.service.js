'use strict';

angular.module('hotelApp')
    .factory('Room', function ($resource, DateUtils) {
        return $resource('api/rooms/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'findAllByTypeAndStatus': { method: 'GET', isArray: true,url:'api/rooms/findAllByTypeAndStatus'},
            'findAllByRangerTimeAndMultiAttr': { method: 'GET', isArray: true,url:'api/rooms/findAllByRangerTimeAndMultiAttr'},
            'findAllByRangerTime':{ method: 'GET', isArray: true , url:'api/rooms/findAllByRangerTime'},
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
