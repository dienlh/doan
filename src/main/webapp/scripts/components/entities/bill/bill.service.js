'use strict';

angular.module('hotelApp')
    .factory('Bill', function ($resource, DateUtils) {
        return $resource('api/bills/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'findAllByMultiAttr': { method: 'GET', isArray: true , url : 'api/bills/findAllByMultiAttr'},
            'findOneByReservationId': {
            	url:'api/bills/findOneByReservationId',
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.create_date = DateUtils.convertDateTimeFromServer(data.create_date);
                    return data;
                }
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.create_date = DateUtils.convertDateTimeFromServer(data.create_date);
                    return data;
                }
            },
            'createByReservationId':{
            	url: 'api/bills/createByReservationId',
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
