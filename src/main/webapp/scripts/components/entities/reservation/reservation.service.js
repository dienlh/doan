'use strict';

angular.module('hotelApp')
    .factory('Reservation', function ($resource, DateUtils) {
        return $resource('api/reservations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'findReservationNotCheckout': { method: 'GET', isArray: true,url:'api/reservations/findReservationNotCheckout'},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.time_checkin = DateUtils.convertDateTimeFromServer(data.time_checkin);
                    data.time_checkout = DateUtils.convertDateTimeFromServer(data.time_checkout);
                    data.create_date = DateUtils.convertDateTimeFromServer(data.create_date);
                    data.last_modified_date = DateUtils.convertDateTimeFromServer(data.last_modified_date);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
