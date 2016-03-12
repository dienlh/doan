'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('metrics', {
                parent: 'app',
                url: '/metrics',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'Application Metrics'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/admin/metrics/metrics.html',
                        controller: 'MetricsController'
                    }
                },
                resolve: {
                    
                }
            });
    });
