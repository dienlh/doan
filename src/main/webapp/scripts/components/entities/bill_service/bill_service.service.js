'use strict';

angular.module('hotelApp')
    .factory('Bill_service', function ($resource, DateUtils) {
        return $resource('api/bill_services/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'findAllByMultiAttr': { method: 'GET', isArray: true , url:"api/bill_services/findAllByMultiAttr"},
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
