'use strict';

angular.module('hotelApp')
    .factory('Employee', function ($resource, DateUtils) {
        return $resource('api/employees/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.birthday = DateUtils.convertLocaleDateFromServer(data.birthday);
                    data.identity_card_number_prov_date = DateUtils.convertLocaleDateFromServer(data.identity_card_number_prov_date);
                    data.social_insurence_date = DateUtils.convertLocaleDateFromServer(data.social_insurence_date);
                    data.create_date = DateUtils.convertDateTimeFromServer(data.create_date);
                    data.last_modified_date = DateUtils.convertDateTimeFromServer(data.last_modified_date);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.birthday = DateUtils.convertLocaleDateToServer(data.birthday);
                    data.identity_card_number_prov_date = DateUtils.convertLocaleDateToServer(data.identity_card_number_prov_date);
                    data.social_insurence_date = DateUtils.convertLocaleDateToServer(data.social_insurence_date);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.birthday = DateUtils.convertLocaleDateToServer(data.birthday);
                    data.identity_card_number_prov_date = DateUtils.convertLocaleDateToServer(data.identity_card_number_prov_date);
                    data.social_insurence_date = DateUtils.convertLocaleDateToServer(data.social_insurence_date);
                    return angular.toJson(data);
                }
            }
        });
    });
