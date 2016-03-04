'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('profile', {
                parent: 'app',
                url: '/profiles',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Profiles'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/profile/profiles.html',
                        controller: 'ProfileController'
                    }
                },
                resolve: {
                }
            })
            .state('profile.statistic', {
                parent: 'app',
                url: '/profile/statistics',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Profiles'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/profile/profile-statistics.html',
                        controller: 'ProfileStatisticController'
                    }
                },
                resolve: {
                	deps: ['uiLoad',
                          function( uiLoad){
                             return uiLoad.load('scripts/app/entities/profile/profile-statistic.controller.js');
                     }]
                }
            })
            .state('profile.detail', {
                parent: 'app',
                url: '/profile/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Profile'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/profile/profile-detail.html',
                        controller: 'ProfileDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Profile', function($stateParams, Profile) {
                        return Profile.get({id : $stateParams.id});
                    }]
                }
            })
            .state('profile.new', {
                parent: 'profile',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/profile/profile-dialog.html',
                        controller: 'ProfileDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    join_date: null,
                                    salary_subsidies: null,
                                    salary_basic: null,
                                    salary: null,
                                    create_date: null,
                                    last_modified_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('profile', null, { reload: true });
                    }, function() {
                        $state.go('profile');
                    })
                }]
            })
            .state('profile.edit', {
                parent: 'profile',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/profile/profile-dialog.html',
                        controller: 'ProfileDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Profile', function(Profile) {
                                return Profile.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('profile', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('profile.delete', {
                parent: 'profile',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/profile/profile-delete-dialog.html',
                        controller: 'ProfileDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Profile', function(Profile) {
                                return Profile.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('profile', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
