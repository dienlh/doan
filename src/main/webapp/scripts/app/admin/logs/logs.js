'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('logs', {
                parent: 'app',
                url: '/logs',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'Logs'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/admin/logs/logs.html',
                        controller: 'LogsController'
                    }
                },
                resolve: {
                    
                }
            });
    });
