'use strict';

angular.module('hotelApp')
    .factory('Profile', function ($resource, DateUtils) {
        return $resource('api/profiles/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'findByMultiAttr': { method: 'GET', isArray: true, url: 'api/profiles/findByMultiAttr'},
            'findByMultiAttrWithRanger': { method: 'GET', isArray: true, url: 'api/profiles/findByMultiAttrWithRanger'},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.join_date = DateUtils.convertLocaleDateFromServer(data.join_date);
                    data.create_date = DateUtils.convertDateTimeFromServer(data.create_date);
                    data.last_modified_date = DateUtils.convertDateTimeFromServer(data.last_modified_date);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.join_date = DateUtils.convertLocaleDateToServer(data.join_date);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.join_date = DateUtils.convertLocaleDateToServer(data.join_date);
                    return angular.toJson(data);
                }
            }
        });
    });
