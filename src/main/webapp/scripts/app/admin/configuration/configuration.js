'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('configuration', {
                parent: 'app',
                url: '/configuration',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'Configuration'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/admin/configuration/configuration.html',
                        controller: 'ConfigurationController'
                    }
                },
                resolve: {
                    
                }
            });
    });
