'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('audits', {
                parent: 'app',
                url: '/audits',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'Audits'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/admin/audits/audits.html',
                        controller: 'AuditsController'
                    }
                },
                resolve: {
                    
                }
            });
    });
