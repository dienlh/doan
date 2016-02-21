'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('status_profile', {
                parent: 'entity',
                url: '/status_profiles',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_profiles'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/status_profile/status_profiles.html',
                        controller: 'Status_profileController'
                    }
                },
                resolve: {
                }
            })
            .state('status_profile.detail', {
                parent: 'entity',
                url: '/status_profile/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_profile'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/status_profile/status_profile-detail.html',
                        controller: 'Status_profileDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Status_profile', function($stateParams, Status_profile) {
                        return Status_profile.get({id : $stateParams.id});
                    }]
                }
            })
            .state('status_profile.new', {
                parent: 'status_profile',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_profile/status_profile-dialog.html',
                        controller: 'Status_profileDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    decription: null,
                                    create_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('status_profile', null, { reload: true });
                    }, function() {
                        $state.go('status_profile');
                    })
                }]
            })
            .state('status_profile.edit', {
                parent: 'status_profile',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_profile/status_profile-dialog.html',
                        controller: 'Status_profileDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Status_profile', function(Status_profile) {
                                return Status_profile.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_profile', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('status_profile.delete', {
                parent: 'status_profile',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_profile/status_profile-delete-dialog.html',
                        controller: 'Status_profileDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Status_profile', function(Status_profile) {
                                return Status_profile.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_profile', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
