'use strict';

angular.module('hotelApp')
    .factory('Event', function ($resource, DateUtils) {
        return $resource('api/events/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'findAllByRangerDateAndStatus': {
            	method: 'GET', 
            	isArray: true,
            	url:'api/events/findAllByRangerDateAndStatus',
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.start_date = DateUtils.convertLocaleDateFromServer(data.start_date);
                    data.end_date = DateUtils.convertLocaleDateFromServer(data.end_date);
                    data.create_date = DateUtils.convertDateTimeFromServer(data.create_date);
                    data.last_modified_date = DateUtils.convertDateTimeFromServer(data.last_modified_date);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.start_date = DateUtils.convertLocaleDateToServer(data.start_date);
                    data.end_date = DateUtils.convertLocaleDateToServer(data.end_date);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.start_date = DateUtils.convertLocaleDateToServer(data.start_date);
                    data.end_date = DateUtils.convertLocaleDateToServer(data.end_date);
                    return angular.toJson(data);
                }
            }
        });
    });
