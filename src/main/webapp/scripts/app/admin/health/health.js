'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('health', {
                parent: 'app',
                url: '/health',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'Health checks'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/admin/health/health.html',
                        controller: 'HealthController'
                    }
                },
                resolve: {
                    
                }
            });
    });
