'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('register_info', {
                parent: 'entity',
                url: '/register_infos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Register_infos'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/register_info/register_infos.html',
                        controller: 'Register_infoController'
                    }
                },
                resolve: {
                }
            })
            .state('register_info.detail', {
                parent: 'entity',
                url: '/register_info/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Register_info'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/register_info/register_info-detail.html',
                        controller: 'Register_infoDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Register_info', function($stateParams, Register_info) {
                        return Register_info.get({id : $stateParams.id});
                    }]
                }
            })
            .state('register_info.new', {
                parent: 'register_info',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/register_info/register_info-dialog.html',
                        controller: 'Register_infoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    date_checkin: null,
                                    date_checkout: null,
                                    number_of_adults: null,
                                    number_of_kids: null,
                                    other_request: null,
                                    deposit_value: null,
                                    create_date: null,
                                    last_modified_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('register_info', null, { reload: true });
                    }, function() {
                        $state.go('register_info');
                    })
                }]
            })
            .state('register_info.edit', {
                parent: 'register_info',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/register_info/register_info-dialog.html',
                        controller: 'Register_infoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Register_info', function(Register_info) {
                                return Register_info.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('register_info', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('register_info.delete', {
                parent: 'register_info',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/register_info/register_info-delete-dialog.html',
                        controller: 'Register_infoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Register_info', function(Register_info) {
                                return Register_info.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('register_info', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
