'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('password', {
                parent: 'app',
                url: '/password',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Password'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/account/password/password.html',
                        controller: 'PasswordController'
                    }
                },
                resolve: {
                    
                }
            });
    });
