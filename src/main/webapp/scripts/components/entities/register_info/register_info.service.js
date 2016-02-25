'use strict';

angular.module('hotelApp')
    .factory('Register_info', function ($resource, DateUtils) {
        return $resource('api/register_infos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date_checkin = DateUtils.convertLocaleDateFromServer(data.date_checkin);
                    data.date_checkout = DateUtils.convertLocaleDateFromServer(data.date_checkout);
                    data.create_date = DateUtils.convertDateTimeFromServer(data.create_date);
                    data.last_modified_date = DateUtils.convertDateTimeFromServer(data.last_modified_date);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.date_checkin = DateUtils.convertLocaleDateToServer(data.date_checkin);
                    data.date_checkout = DateUtils.convertLocaleDateToServer(data.date_checkout);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.date_checkin = DateUtils.convertLocaleDateToServer(data.date_checkin);
                    data.date_checkout = DateUtils.convertLocaleDateToServer(data.date_checkout);
                    return angular.toJson(data);
                }
            }
        });
    });
