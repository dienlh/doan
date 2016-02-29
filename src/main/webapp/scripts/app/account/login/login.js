'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('login', {
//                parent: 'account',
                url: '/login',
                data: {
                    authorities: [], 
                    pageTitle: 'Sign in'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/account/login/login.html',
                        controller: 'LoginController'
                    }
                },
                resolve: {
                    
                }
            });
    });
