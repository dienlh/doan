'use strict';

angular.module('hotelApp')
    .factory('Customer', function ($resource, DateUtils) {
        return $resource('api/customers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.identity_card_prov_date = DateUtils.convertLocaleDateFromServer(data.identity_card_prov_date);
                    data.birtday = DateUtils.convertLocaleDateFromServer(data.birtday);
                    data.create_date = DateUtils.convertDateTimeFromServer(data.create_date);
                    data.last_modified_date = DateUtils.convertDateTimeFromServer(data.last_modified_date);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.identity_card_prov_date = DateUtils.convertLocaleDateToServer(data.identity_card_prov_date);
                    data.birtday = DateUtils.convertLocaleDateToServer(data.birtday);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.identity_card_prov_date = DateUtils.convertLocaleDateToServer(data.identity_card_prov_date);
                    data.birtday = DateUtils.convertLocaleDateToServer(data.birtday);
                    return angular.toJson(data);
                }
            }
        });
    });
